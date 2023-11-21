package com.ww.regexpStudy;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则语法
 */
public class RegGrammar {

    /**
     * 正则转义符 //
     *
     *  转义符  \ \ ：使用正则表达式去匹配某些特殊字符时，需要用到转义符。注意在java中，转义符号用两个反斜杠 \\。
     *
     *  注意： 需要用到转义符号的字符有：. * + ( ) $ / \ ? [ ] ^ { }，注意，在[ ]中不需要转义，会自动转义，例如[?]匹配的就是一个实实在在的 ?
     */
    @Test
    public void t() {
        String content = "1123(.(/";
        String regStr = "\\(";
        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            System.out.println(matcher.group(0));
        }
    }

    /**
     * 字符匹配符 (匹配任意一个字符或数字)
     */
    @Test
    public void t1() {
        String content = "acb";
//        String regStr = "[a-z]";
//        String regStr = "(?i)[a-z]"; // 不区分大小写
//        Pattern pattern = Pattern.compile(regStr, Pattern.CASE_INSENSITIVE); // 不区分大小写
        String regStr = "^[bc]";
//        String regStr = "[^bc]";
//        String regStr = "\\D";
//        String regStr = "\\w";
//        String regStr = "\\W";
//        String regStr = "\\s";
//        String regStr = "\\S";
//        String regStr = ".";
        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            System.out.println("找到: " + matcher.group(0));
        }
    }


    /**
     * 选择匹配符 (匹配任意一个字符或数字)
     */
    @Test
    public void t2() {
        String content = "a12Q3b你 好GGcc";
        String regStr = "a|b|c";
        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            System.out.println("找到: " + matcher.group(0));
        }
    }

    /**
     * 限定符匹配
     */
    @Test
    public void t3() {
        String content = "1111aaaac";
//        String regStr = "1+";
//        String regStr = "1*";
//        String regStr = "1?";
//        String regStr = "\\d";
        String regStr = "[ac]{5}";
        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            System.out.println("找到: " + matcher.group(0));
        }
    }

    /**
     * 定位符匹配
     */
    @Test
    public void t4() {
        String content = "1han233-han";
//        String regStr = "^[0-9]+[a-z]*";
//        String regStr = "^[0-9]\\-[a-z]$";
//        String regStr = "han\\b";
        String regStr = "han\\B";
        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            System.out.println("找到: " + matcher.group(0));
        }
    }

    /**
     * 捕获分组匹配
     */
    @Test
    public void t5() {
        String content = "1han1233-han你好啊!";
        String regStr = "(?<g1>\\d\\d)(?<g2>\\d\\d)";
        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            System.out.println("找到: " + matcher.group(0));
            System.out.println("第一个分组的内容: " + matcher.group("g1"));
            System.out.println("第一个分组的内容: " + matcher.group(1));
            System.out.println("第二个分组的内容: " + matcher.group("g2"));
            System.out.println("第一个分组的内容: " + matcher.group(2));
        }
    }

    /**
     * 非捕获分组匹配
     */
    @Test
    public void t6() {
        String content = "han2-han3-han6-你好啊";
//        String regStr = "han(?:2|3)";
//        String regStr = "han(?=2|3)";
        String regStr = "han(?!2|3)";
        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            System.out.println("找到: " + matcher.group(0));
        }
    }


    @Test
    public void t0() {
        String content = "15634567890";
//        String regStr = "^[1-9]\\d{5}$";
//        String regStr = "^[1-9]\\d{4,9}$";
        String regStr = "[\\w-]$";
        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            System.out.println("找到: " + matcher.group(0));
        } else {
            System.out.println("未找到");
        }
    }

}
