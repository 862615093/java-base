package com.ww.spring.transactional.moreThreadTransaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ww.spring.transactional.moreThreadTransaction.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SpringTransactionalMapper extends BaseMapper<Employee> {

    int saveBatch1(List<Employee> list);
}
