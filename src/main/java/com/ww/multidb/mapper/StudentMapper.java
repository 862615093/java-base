package com.ww.multidb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ww.multidb.entity.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    void insert1(Student student);
}
