package com.ww.regexpStudy;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则匹配原理
 */
public class RegTheory {

    @Test
    public void t() {
        //需要匹配的内容
        String content = "19987你好啊,凉快圣诞节流程卡时1001间段兰蔻菁纯";
        //1.创建正则表达式
        String regStr = "\\d\\d\\d\\d";
        //2.创建模式对象(正则表达式对象)
        Pattern pattern = Pattern.compile(regStr);
        //3.创建匹配器
        Matcher matcher = pattern.matcher(content);
        //4.开始匹配
        while (matcher.find()) {
            System.out.println("找到：" + matcher.group(0));
        }
    }

    @Test
    public void t1() {
        //需要匹配的内容
        String content = "19987你好啊,凉快圣诞节流程卡时1001间段兰蔻菁纯";
        //1.创建正则表达式 (分组)
        String regStr = "(\\d\\d)(\\d\\d)";
        //2.创建模式对象(正则表达式对象)
        Pattern pattern = Pattern.compile(regStr);
        //3.创建匹配器
        Matcher matcher = pattern.matcher(content);
        //4.开始匹配
        while (matcher.find()) {
            System.out.println("找到：" + matcher.group(0)); //表示匹配到的子字符串
            System.out.println("找到第一组匹配的值：" + matcher.group(1)); //匹配到子字符串的第一组字符
            System.out.println("找到第二组匹配的值：" + matcher.group(2)); //匹配到子字符串的第二组字符
        }
    }


}
