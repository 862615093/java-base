package com.ww.frame.spring.aop.cglib;

//cglib需要代理的目标类
public class Target {
    public void save() {
        System.out.println("2... save()");
    }

    public void save(int i) {
        System.out.println("save(int)");
    }

    public void save(long j) {
        System.out.println("save(long)");
    }
}