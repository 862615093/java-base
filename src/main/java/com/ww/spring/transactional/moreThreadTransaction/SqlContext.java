package com.ww.frame.spring.transactional.moreThreadTransaction;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 获取sqlSession
 */
@Component
public class SqlContext {

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    public SqlSession getSqlSession(){
        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        return sqlSessionFactory.openSession();
    }
}