//package com.ww.multidb.service.impl;
//
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.ww.multidb.entity.Teacher;
//import com.ww.multidb.mapper.TeacherMapper;
//import com.ww.multidb.service.ITeacherService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Slf4j
//@Service
//public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {
//
//    @Autowired
//    private TeacherMapper teacherMapper;
//
//    @Override
//    @Transactional
//    public void saveDemo(Teacher teacher) {
//        teacherMapper.insert(teacher);
//    }
//
//    @Override
//    public void insert1(Teacher teacher) {
//        teacherMapper.insert1(teacher);
//    }
//}
