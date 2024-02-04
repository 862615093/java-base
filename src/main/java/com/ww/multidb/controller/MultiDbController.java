package com.ww.multidb.controller;

import com.ww.multidb.entity.Student;
import com.ww.multidb.entity.Teacher;
import com.ww.multidb.service.IStudentService;
import com.ww.multidb.service.ITeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = {"本地多数据源事务"})
@RequestMapping("/multidb/transactional")
public class MultiDbController {

    @Autowired
    private IStudentService studentService;

    @Autowired
    private ITeacherService teacherService;

    @GetMapping("t1")
    @ApiOperation("student save...")
    public void db1TransactionalTest() {
        Student student = new Student();
        student.setName("ww");
        studentService.saveDemo(student);
    }

    @GetMapping("t2")
    @ApiOperation("teacher save...")
    public void db2TransactionalTest() {
        Teacher teacher = new Teacher();
        teacher.setName("qq");
        teacherService.saveDemo(teacher);
    }

    @GetMapping("t3")
    public void db3TransactionalTest() {
        Student student = new Student();
        student.setName("ww123");
        studentService.saveDemo(student);
    }

    @GetMapping("t4")
    @ApiOperation("teacher and student save...")
    public void db4TransactionalTest() {
        Teacher teacher = new Teacher();
        teacher.setName("qq1");
        Student student = new Student();
        student.setName("ww1");
        studentService.saveStudentAndTeacherDemo(teacher, student);
    }

    @GetMapping("t5")
    @ApiOperation("本地多数据源事务测试")
    @Transactional
    public void db5TransactionalTest() {
        Teacher teacher = new Teacher();
        teacher.setName("qq2");
        teacherService.saveDemo(teacher);

        int i = 1 / 0;

        Student student = new Student();
        student.setName("ww2");
        studentService.saveDemo(student);
    }


}
