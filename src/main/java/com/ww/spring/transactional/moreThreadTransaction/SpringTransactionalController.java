package com.ww.frame.spring.transactional.moreThreadTransaction;

import com.ww.frame.spring.transactional.moreThreadTransaction.service.ISpringTransactionalService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@Api(tags = {"多线程事务失效问题的分析与解决方案"})
@RequestMapping("/transactional")
public class SpringTransactionalController {

    @Autowired
    private ISpringTransactionalService transactionalService;

    @Autowired
    private SqlContext sqlContext;

    /**
     * 测试多线程事务
     */
    @GetMapping("t1")
    public void MoreThreadTest() throws SQLException {
        int size = 10;
        List<Employee> employeeList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Employee employee = new Employee();
            employee.setName(i + "XX");
            employeeList.add(employee);
        }
//        transactionalService.saveThread(employeeList);
        transactionalService.saveThread2(employeeList);
    }
}
