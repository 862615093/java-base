package com.ww.frame.spring.transactional.moreThreadTransaction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ww.frame.spring.transactional.moreThreadTransaction.Employee;

import java.sql.SQLException;
import java.util.List;

public interface ISpringTransactionalService extends IService<Employee> {

    public void saveThread(List<Employee> employeeList);

    public void saveThread2(List<Employee> employeeList) throws SQLException;

}
