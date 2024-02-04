package com.ww.spring.transactional.moreThreadTransaction.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ww.frame.spring.transactional.moreThreadTransaction.ExecutorConfig;
import com.ww.spring.transactional.moreThreadTransaction.Employee;
import com.ww.spring.transactional.moreThreadTransaction.SqlContext;
import com.ww.spring.transactional.moreThreadTransaction.mapper.SpringTransactionalMapper;
import com.ww.spring.transactional.moreThreadTransaction.service.ISpringTransactionalService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Slf4j
@Service
public class SpringTransactionalServiceImpl extends ServiceImpl<SpringTransactionalMapper, Employee> implements ISpringTransactionalService {

    static volatile int num = 0;

    @Autowired
    SqlContext sqlContext;

    @Autowired
    SpringTransactionalMapper transactionalMapper;

    @Override
    public void saveThread2(List<Employee> EmployeeList) throws SQLException {
        // 获取数据库连接,获取会话(内部自有事务)
        SqlSession sqlSession = sqlContext.getSqlSession();
        //mapper层必须绑定对应的sqlSession
        SpringTransactionalMapper mapper = sqlSession.getMapper(SpringTransactionalMapper.class);
        Connection connection = sqlSession.getConnection();
        try {
            // 设置手动提交
            connection.setAutoCommit(false);
//            //获取mapper
//            EmployeeMapper employeeMapper = sqlSession.getMapper(EmployeeMapper.class);
//            //先做删除操作
//            employeeMapper.delete(null);
            //获取执行器
            ExecutorService service = ExecutorConfig.getThreadPool();
            List<Callable<Integer>> callableList = new ArrayList<>();
            //拆分list
            List<List<Employee>> lists = averageAssign(EmployeeList, 5);
            for (int i = 0; i < lists.size(); i++) {
                List<Employee> list = lists.get(i);
                //使用返回结果的callable去执行,
                Callable<Integer> callable = () -> {
                    //最后一个线程抛出异常
//                    if (num == 3) {
//                        throw new RuntimeException("001出现异常");
//                    }
//                    num ++;
                    int res = mapper.saveBatch1(list);
                    return res;
                };
                callableList.add(callable);
            }
            //执行子线程
            List<Future<Integer>> futures = service.invokeAll(callableList);
            for (Future<Integer> future : futures) {
                //如果有一个执行不成功,则全部回滚
                Thread.sleep(200);
                if (future.get() <= 0) {
                    connection.rollback();
                    return;
                }
            }
            connection.commit();
            System.out.println("添加完毕");
        } catch (Exception e) {
            connection.rollback();
            log.info("error", e);
            throw new RuntimeException("异常");
        } finally {
            connection.close();
        }
    }

    @Override
    @Transactional
    public void saveThread(List<Employee> employeeList) {
        Thread.setDefaultUncaughtExceptionHandler(new ChildExceptionHandler());
        try {
            //先做删除操作,如果子线程出现异常,此操作不会回滚
//            this.getBaseMapper().delete(null);
            //获取线程池
            ExecutorService service = ExecutorConfig.getThreadPool();
            //拆分数据,拆分5份
            List<List<Employee>> lists = averageAssign(employeeList, 5);
            //执行的线程
            Thread[] threadArray = new Thread[lists.size()];
            //监控子线程执行完毕,再执行主线程,要不然会导致主线程关闭,子线程也会随着关闭
            CountDownLatch countDownLatch = new CountDownLatch(lists.size());
            for (int i = 0; i < lists.size(); i++) {
                List<Employee> list = lists.get(i);
                threadArray[i] = new Thread(() -> {
                    try {
                        //最后一个线程抛出异常
                        if (num == 3) {
                            throw new RuntimeException("001出现异常");
                        }
                        //批量添加,mybatisPlus中自带的batch方法
                        this.saveBatch(list);
                        num++;
                    } finally {
                        countDownLatch.countDown();
                    }
                });
            }
            for (int i = 0; i < lists.size(); i++) {
                service.execute(threadArray[i]);
                Thread.sleep(200);
            }
            //当子线程执行完毕时,主线程再往下执行
            countDownLatch.await();
            System.out.println("添加完毕...");
        } catch (Exception e) {
            log.info("error.....", e);
            throw new RuntimeException("异常");
        } finally {
//            connection.close();
        }
    }

    static class ChildExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            log.error(String.format("thread [%s] happen exception [%s]", t, e.getMessage()));
        }
    }

    /**
     * 平均拆分list方法.
     *
     * @param source
     * @param n
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
        List<List<T>> result = new ArrayList<List<T>>();
        int remaider = source.size() % n;
        int number = source.size() / n;
        int offset = 0;//偏移量
        for (int i = 0; i < n; i++) {
            List<T> value = null;
            if (remaider > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remaider--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }

}
