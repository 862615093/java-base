package com.ww.mybatis.reflector;

public class Student {
    
    public Integer getId() {
        return 6;
    }

    public void setId(Integer id) {
        System.out.println(id);
    }

    public String getUserName() {
        return "张三";
    }
}