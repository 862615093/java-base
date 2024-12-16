package utils.guava.str;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class StrUtil {

    public static void main(String[] args) {
//        joiner1();
//        joiner2();
//        splitter();
//        charMatcher();
        charMatcher1();


    }


    // Joiner是用来连接字符串的
    // 1.它能以指定的字符对多个字符串进行连接
    // 2.可以跳过空元素，还可以连接map中的key和value
    private static void joiner1() {
        List<String> list = Lists.newArrayList("a", "b", "c");
        //jdk
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                buffer.append(",");
            }
            buffer.append(list.get(i));
        }
        System.out.println(buffer.toString());

        //guava
        String str = Joiner.on(",").join(list);
        System.out.println(str);
    }

    public static void joiner2() {

        //指定第一个、第二个字符串，再跟list中的元素进行连接
        String str = Joiner.on("@").join("first", "second", Lists.newArrayList("a", "b", "c"));
        System.out.println(str);

        //跳过list中的空值，这里的空值是指null，并不包括空字符串
        String str3 = Joiner.on(",").skipNulls().join(Lists.newArrayList("a", "b", " ", null, "c", null));
        System.out.println(str3);

        //useForNull(str)用指定的字符串来替换空值
        String str4 = Joiner.on(",").useForNull("#").join(Lists.newArrayList("a", "b", " ", null, "c", null));
        System.out.println(str4);


        Map<String, String> map = Maps.newHashMap();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        //withKeyValueSeparator 用:来连接map的key和value，再将连接好的key和value用逗号分隔
        String str5 = Joiner.on(",").withKeyValueSeparator(":").join(map);
        System.out.println(str5);
    }


    //Splitter用于字符串的分隔，与Joiner正好是相反的操作,Joiner是将字符串拼接起来，Splitter是将字符串拆分
    public static void splitter() {
        List<String> list = Splitter.on(",").splitToList("a,b,c,d");
        System.out.println(list);

        Splitter.on(",").split("a,b,c,d").forEach(s -> {
            System.out.println(s);
        });

        //过滤掉空字符串，但不包括空格
        List<String> list1 = Splitter.on(",").omitEmptyStrings().splitToList("a,b,, ,c,d");
        System.out.println("list1:" + list1);  //输出：[a, b,  ,c, d]

        //去掉字符串中的空格，再进行过滤空元素
        List<String> list2 = Splitter.on(",").omitEmptyStrings().trimResults().splitToList("a,b,, ,c,d");
        System.out.println("list2:" + list2); //输出 [a, b, c, d]

        //limit表示最多把字符串分隔成多少份
        List<String> list3 = Splitter.on("#").omitEmptyStrings().trimResults().limit(2).splitToList("a#b#c#d");
        System.out.println("list3:" + list3);

        //将字串还原成map，是Joiner的逆向操作，注意：字符串的格式必须满足“a:1#b:2”这种格式，格式不对会导致还原map失败
        Map<String, String> map = Splitter.on("#").omitEmptyStrings().trimResults().withKeyValueSeparator(":").split("a:1#b:2");
        System.out.println("map:" + map);

    }


    public static void charMatcher() {

        String s = "er 3j6o  3k  ,)$ wt@ wr4576je  ow3453535345irjew jwfel ";

        //返回只保留a-z的字母的字符中
        String str = CharMatcher.inRange('a','z').retainFrom(s);
        System.out.println(str);

        //去掉字符串中数字
        String str1 = CharMatcher.digit().removeFrom(s);
        System.out.println(str1);
    }

    public static void charMatcher1(){
        String str = "aj\tld1\b23aAbCs  kF45JAb  c56sl";

        //移除str中的a
        CharMatcher.is('a').removeFrom(str);

        //移除str中的a
        CharMatcher.isNot('a').retainFrom(str);

        //保留str中的a,b,c字符
        CharMatcher.anyOf("abc").retainFrom(str);

        //保留str中的a,b,c字符
        CharMatcher.noneOf("abc").removeFrom(str);

        //匹配str中的a-j的字母，全部替换成数字6
        CharMatcher.inRange('a','j').replaceFrom(str,"6");

        //去str中的空格
        CharMatcher.breakingWhitespace().removeFrom(str);

        //去掉str中的数字
        CharMatcher.digit().removeFrom(str);

        //去掉控制字符(\t,\n,\b...)
        CharMatcher.javaIsoControl().removeFrom(str);

        //获取str中的小写字母
        CharMatcher.javaLowerCase().retainFrom(str);

        //获取str中的大写字母
        CharMatcher.javaUpperCase().retainFrom(str);

        //组合条件：获取str中的大写字母和数字
        System.out.println(CharMatcher.javaUpperCase().or(CharMatcher.digit()).retainFrom(str));
    }



}
