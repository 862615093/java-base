package com.ww.mybatis.interceptor.mapper;

import com.ww.mybatis.interceptor.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    //分页查询所有用户，通过原生limit
    List<User> selectAllUserByLimit(Map map);
}
