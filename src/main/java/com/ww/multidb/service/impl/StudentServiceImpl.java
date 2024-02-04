package com.ww.multidb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ww.multidb.entity.Student;
import com.ww.multidb.entity.Teacher;
import com.ww.multidb.mapper.StudentMapper;
import com.ww.multidb.mapper.TeacherMapper;
import com.ww.multidb.service.IStudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    @Transactional
    public void saveDemo(Student student) {
        studentMapper.insert(student);
        if (student.getName().equalsIgnoreCase("ww123")) {
            int i = 1 / 0;
        }
    }

    @Override
    @Transactional
    public void saveStudentAndTeacherDemo(Teacher teacher, Student student) {
        studentMapper.insert(student);
        teacherMapper.insert1(teacher);
    }

    @Override
    public void insert1(Student student) {
        studentMapper.insert1(student);
    }
}
