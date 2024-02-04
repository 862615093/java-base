package com.ww.multidb.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ww.multidb.entity.Teacher;

@DS("teacher")
public interface ITeacherService extends IService<Teacher> {

    void saveDemo(Teacher teacher);

    void insert1(Teacher teacher);

}