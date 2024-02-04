package com.ww.multidb.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ww.multidb.entity.Student;
import com.ww.multidb.entity.Teacher;

@DS("student")
public interface IStudentService extends IService<Student> {
    void saveDemo(Student student);

    void insert1(Student student);

    void saveStudentAndTeacherDemo(Teacher teacher, Student student);

}
