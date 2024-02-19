package com.ww.mybatis.reflector;

import org.apache.ibatis.reflection.*;
import org.apache.ibatis.reflection.invoker.Invoker;
import org.apache.ibatis.reflection.invoker.MethodInvoker;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ReflectorTest {

    public static void main(String[] args) throws Exception {
//        m1();
//        m2();
//        m3();
        m4();
    }

    private static void m4() {
        RichType rich = new RichType();
        MetaObject meta = SystemMetaObject.forObject(rich);
        meta.setValue("richField", "foo");
        System.out.println(meta.getValue("richField"));
        System.out.println("=========================================");
        meta.setValue("richType.richField", "foo");
        System.out.println(meta.getValue("richType.richField"));
        System.out.println("=========================================");
        meta.setValue("richMap[key]", "foo");
        System.out.println(meta.getValue("richMap[key]"));
    }

    private static void m3(){
        ReflectorFactory reflectorFactory = new DefaultReflectorFactory();
        MetaClass meta = MetaClass.forClass(RichType.class, reflectorFactory);
        System.out.println("0====================================");
        System.out.println(meta.hasGetter("richField"));
        System.out.println(meta.hasGetter("richProperty"));
        System.out.println(meta.hasGetter("richList"));
        System.out.println(meta.hasGetter("richMap"));
        System.out.println(meta.hasGetter("richList[0]"));
        System.out.println("1====================================");
        System.out.println(meta.hasGetter("richType"));
        System.out.println(meta.hasGetter("richType.richField"));
        System.out.println(meta.hasGetter("richType.richProperty"));
        System.out.println(meta.hasGetter("richType.richList"));
        System.out.println(meta.hasGetter("richType.richMap"));
        System.out.println(meta.hasGetter("richType.richList[0]"));
        System.out.println("2====================================");
        // findProperty 只能处理 . 的表达式
        System.out.println(meta.findProperty("richType.richProperty"));
        System.out.println(meta.findProperty("richType.richProperty1"));
        System.out.println(meta.findProperty("richList[0]"));
        System.out.println("3====================================");
        System.out.println(Arrays.toString(meta.getGetterNames()));
    }

    private static void m2() throws Exception{
        ReflectorFactory factory = new DefaultReflectorFactory();
        Reflector reflector = factory.findForClass(Student1.class);
        // 获取构造器 生成对应的对象
        Object o = reflector.getDefaultConstructor().newInstance();
        // 写入
        MethodInvoker invoker1 = (MethodInvoker) reflector.getSetInvoker("id");
        invoker1.invoke(o,new Object[]{999});
        // 读取
        Invoker invoker2 = reflector.getGetInvoker("id");
        invoker2.invoke(o,null);
    }

    private static void m1() {
        ReflectorFactory factory = new DefaultReflectorFactory();
        Reflector reflector = factory.findForClass(Student.class);
        System.out.println("可读属性:" + Arrays.toString(reflector.getGetablePropertyNames()));
        System.out.println("可写属性:" + Arrays.toString(reflector.getSetablePropertyNames()));
        System.out.println("是否具有默认的构造器:" + reflector.hasDefaultConstructor());
        System.out.println("Reflector对应的Class:" + reflector.getType());
    }
}
