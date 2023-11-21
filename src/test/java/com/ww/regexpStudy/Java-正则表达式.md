

## 一.正则表达式语法

----

```
正则表达式中的元字符从功能上大致分为：
1.限定符
2.选择匹配符
3.分组组合和反向引用符
4.特殊字符
5.字符匹配符
6.定位符
```



#### **1.正则转义符**

**转义符  \ \ ：**使用正则表达式去撇皮某些特殊字符时，需要用到转义符。注意在java中，转义符号用两个反斜杠\。

**注意：**需要用到转义符号的字符有：. * + ( ) $ / \ ? [ ] ^ { }，注意，在[ ]中不需要转义，会自动转义，例如[?]匹配的就是一个实实在在的 ?

```java
// 演示转义字符的使用
public class Regex01 {
    public static void main(String[] args) {
        String content = "abc$(abc(123(";
        // 匹配(
        String regStr = "\\(";
        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            System.out.println("find: " + matcher.group(0));
        }
    }
}
```



#### **2.字符匹配符**

| 符号 | 含义                                                         | 实例               | 说明                                                 |
| :--- | ------------------------------------------------------------ | ------------------ | ---------------------------------------------------- |
| [ ]  | 可接受的字符列表                                             | [abcd123#%]        | abcd123#%中的任意一个字符，可自动转义                |
| [^]  | 不可接受的字符列表                                           | [^abc]             | 除abc之外的任意一个字符，包括数字和特殊符号          |
| -    | 连字符                                                       | [ a-b ] 或 [ A-Z ] | 任意单个大、小写字母                                 |
| .    | 匹配除\n以外的任何一个字符                                   | a..b               | 以a开头，b结尾，中间包括2个任意字符的长度为4的字符串 |
| \ \d | 匹配单个数字字符，相当于[0-9]                                | \ \d{3}(\ \d)?     | 包含3个或4个数字的字符串                             |
| \ \D | 匹配单个非数字字符，相当于[ ^0-9 ]                           | \ \D(\ \d)*        | 以单个非数字字符开头，后接任意个数字的字符串         |
| \ \w | 匹配单个数字、大小写字母字符，下划线， 相当于[0-9a-zA-Z]     | \ \d{3}\ \w{4}     | 以3个数字字符开头的, 长度为7的数字字母字符串         |
| \ \W | 匹配单个非数字、非大小写字母字符，非下划线，相当于[ ^0-9a-zA-Z ] | \ \W+\ \d{2}       | 以至少1个非数字字母字符开头，2个数字字符结尾的字符串 |
| \ \s | 匹配单个任意空白字符（例如空格，制表符等）                   |                    |                                                      |
| \ \S | 匹配单个任意非空白字符                                       |                    |                                                      |



**注意**

- \ \d{3}：表示连续三个数字字符，等价于\ \d\ \d\ \d

- (\ \d)?：表示可能有0个或1个数字字符

- java正则表达式默认是区分大小写的，但可以实现不区分大小写

  ```
  方法一：利用(?i)
  (?i)abc：表示abc都不区分大小写
  a(?i)bc：表示bc不区分大小写
  a((?i)b)c：表示b不区分大小写
  
  方法二：设置大小写不敏感
  Pattern pattern = Pattern.compile(regStr, Pattern.CASE_INSENSITIVE);
  
  ```

  



#### **3.选择匹配符**	

在匹配某个字符串的时候是选择性的，即：既可以匹配这个，又可以匹配那个，这时候需要使用选择匹配符 ' | ' 



#### **4.限定符**

限定符用于指定前面的字符和组合项连续出现多少次。

| 符号  | 含义                         | 示例        | 说明                                                         |
| ----- | ---------------------------- | ----------- | ------------------------------------------------------------ |
| *     | 指定字符重复0次或n次，零到多 | (abc)*      | 仅包含任意个abc的字符串                                      |
| +     | 指定字符重复1次或n次，一到多 | m+          | 以至少1个m开头的字符串                                       |
| ?     | 指定字符重复0次或1次，零到一 | m+abc?      | 以至少1个m开头，后接abc或ab的字符串(贪婪匹配，优先匹配多个)  |
| {n}   | 只能输出n个字符              | [abcd]{3}   | 由abcd中的字母组成的任意长度为3的字符串                      |
| {n,}  | 指定至少n个匹配              | [abcd]{3,}  | 由abcd中的字母组成的任意长度不小于3的字符串(贪婪匹配，优先匹配多个) |
| {n,m} | 指定至少n个但不多于m个匹配   | [abcd]{3,5} | 由abcd中的字母组成的任意长度不小于3但不大于5的字符串(贪婪匹配，优先匹配多个) |

**注意：**限定符都是用在目标字符后面的!!!




#### **5.定位符**

定位符的作用是规定要匹配的字符串出现的位置，比如在字符串的开始还是结束的位置。

| 符号 | 含义                   | 示例                 | 说明                                                         |
| ---- | ---------------------- | -------------------- | ------------------------------------------------------------ |
| ^    | 指定起始字符           | ^[0-9]+[a-z]*        | 以至少1个数字开头，后接任意个小写字母的字符串                |
| $    | 指定结束字符           | ^[0-9] \ \ - [a-z]+$ | 以1个数字开头，后接连字符-，并以至少一个小写字母结尾的字符串 |
| \ \b | 匹配目标字符串的边界   | han\ \b              | 这里说的字符串边界指的是子串间有空格，或者是目标字符串的结束位置 |
| \ \B | 匹配目标字符串的非边界 | han\ \B              | 和\ \b含义相反                                               |



#### **6.捕获分组**

分组：我们可以用圆括号组成一个比较复杂的匹配模式，一个圆括号的部分就可以看作是一个子表达式
捕获：把正则表达式中子表达式/分组匹配的内容，保存到内存中，以数字编号或显式命名的组里，方便后面引用。注意组0代表的是整个正则式，分组编号从1开始。

| 常用分组构造形式 | 说明                                                         |
| ---------------- | ------------------------------------------------------------ |
| (pattern)        | 非命名捕获。捕获匹配的子字符串。编号为零的第一个捕获是由整个正则表达式模式匹配的文本，其他捕获结果则根据左括号的顺序从1开始自动编号 |
| (?<name>pattern) | 命名捕获。将匹配的子字符串捕获到一个组名称或编号名称中。用于name的字符串不能包含任何标点符号，并且不能以数字开头。可以使用单引号代替尖括号，例如(?'name') |

```java
// 分组
public class Regex06 {
    public static void main(String[] args) {
        String content = "Hongyi s7788 nn1198han";

        String regStr = "(?<g1>\\d\\d)(?<g2>\\d\\d)";

        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            System.out.println("find: " + matcher.group(0));
            System.out.println("第一个分组的内容: " + matcher.group("g1"));
            System.out.println("第二个分组的内容: " + matcher.group("g2"));
        }
    }
}
```



#### **7.非捕获分组**

| 常用分组构造形式 | 说明                                                         |
| ---------------- | ------------------------------------------------------------ |
| (?:pattern)      | 匹配pattern但不捕获该匹配的子表达式，即它是一个非捕获匹配，不存储供以后使用的匹配。 |
| (?=pattern)      | 非捕获匹配                                                   |
| (?!pattern)      | 作者: Kisugi Takumi非捕获匹配。匹配不处于匹配pattern的字符串的起始点的搜索字符串，即与第二条规则匹配相反的内容 |

```java
public class Regex08 {
    public static void main(String[] args) {
        String content = "hello韩顺平教育 jack韩顺平老师 韩顺平同学hello";

        String regStr = "韩顺平(?:教育|老师|同学)";

        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            System.out.println("find: " + matcher.group(0));
        }
    }
}

// 非捕获分组
public class Regex08 {
    public static void main(String[] args) {
        String content = "hello韩顺平教育 jack韩顺平老师 韩顺平同学hello";

        String regStr = "韩顺平(?=教育|老师)";

        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            System.out.println("find: " + matcher.group(0));
        }
    }
}

String regStr = "韩顺平(?!教育|老师)";
```




#### 8.反向引用

反向引用：圆括号的内容被捕获后，可以在这个括号后被使用，从而写出一个比较实用的匹配模式。这种引用既可以在正则表达式内部，也可以在外部。内部反向引用使用\\分组号，外部引用使用$分组号

```java
需求：匹配两个连续的相同数字
String content = "hello jack11 tom22 Hongyi";
String regStr = "(\\d)\\1"; // 11 22

需求：匹配四个连续的相同数字
String content = "hello jack1111 tom2222 Hongyi";
String regStr = "(\\d)\\1{4}"; // 1111 2222

需求：匹配四位的回文数
String content = "hello jack1221 tom2332 Hongyi";
String regStr = "(\\d)(\\d)\\2\\1"; // 1221 2332

需求：形如12319-333999111，前面是一个五位数，紧随连字符，然后是一个九位数，连续的三位要相同
String regStr = "\\d{5}-(\\d)\\1{2}(\\d)\\2{2}(\\d)\\3{2}";


需求：结巴去重，把类似我...我要...学学学学...编程java!修改为我要学编程java!
public class Regex04 {
    public static void main(String[] args) {
        String content = "我...我要...学学学学...编程java!";
        // 1.去掉所有的.
        Pattern pattern = Pattern.compile("\\.");
        Matcher matcher = pattern.matcher(content);
        content = matcher.replaceAll("");

        // 此时 content = "我我要学学学学编程java!"
        // 2.去掉重复的字
        // 使用 (.)\\1+ 匹配
        pattern = Pattern.compile("(.)\\1+"); // 分组捕获的内容记录到$1
        matcher = pattern.matcher(content);

        content = matcher.replaceAll("$1");
        System.out.println(content);
    }
}
```





## 二.String类中使用正则表达式

**1.替换(replaceAll)**

```java
String content = "jdk1.3 jdk1.4";
content = content.replaceAll("jdk1\\.3|jdk1\\.4","jdk");
sout(content);
```

**2.匹配(matches)**

```java
//要求：验证一个手机号，必须是以138、139开头的
String content = "13564564565";
if(content.matches("1(38|39)\\d{8}")){
    sout("验证成功");
}else{
    sout("验证失败");
}
```

**3.分割(split)**

```java
String content = "hello#abc-jack12smith~北京";
//要求按照 # - ~ 数字 来分割
String[] split = content.split("#|-|~|\\d+");
//iter,增强for循环输出split数组
```








