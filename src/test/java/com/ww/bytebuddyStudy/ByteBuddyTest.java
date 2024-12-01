package com.ww.bytebuddyStudy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.NamingStrategy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.scaffold.TypeValidation;
import net.bytebuddy.implementation.FixedValue;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.returns;

public class ByteBuddyTest {

    private String path1 = "D:\\Java\\work-space\\my-project\\java-base\\target\\test-classes";
    private String path = "C:\\Users\\联想\\.m2\\repository\\org\\junit\\platform\\junit-platform-commons\\1.8.2\\junit-platform-commons-1.8.2.jar!\\META-INF\\versions\\9\\";

//    @BeforeEach
//    public void init() {
//        path = ByteBuddyTest.class.getClassLoader().getResource("").getPath();
//        // path = C:\Users\联想\.m2\repository\org\junit\platform\junit-platform-commons\1.8.2\junit-platform-commons-1.8.2.jar!\META-INF\versions\9\
//        System.out.println("path = " + path);
//    }

    /**
     * 动态增强的三种方式:
     * 1. subclass
     * 2. rebase:变基,效果是保留原方法并重命名为xx$$original$74tHk8yR(),xx为拦截后的逻辑.
     *          命名策略:不指定的话和目标类全类名保持一致
     * 3. redefine: 原方法不再保留,命名策略:
     *      不指定的话和目标类全类名保持一致
     */
    @Test
    public void test3() throws Exception {
        DynamicType.Unloaded<UserManager> unloaded = new ByteBuddy()
//                .subclass(UserManager.class)
                .rebase(UserManager.class)
//                .redefine(UserManager.class)
//                .name("a.b.SubObj")
                .method(named("selectUserName").and(
                        returns(TypeDescription.CLASS)
                                .or(returns(TypeDescription.OBJECT))
                                .or(returns(TypeDescription.STRING))
                ))
                .intercept(FixedValue.nullValue())
                .method(named("print").and(
                        returns(TypeDescription.VOID)
                ))
                .intercept(FixedValue.value(TypeDescription.VOID))
                .method(named("selectAge"))
                .intercept(FixedValue.value(0))
                .make();
        unloaded.saveIn(new File(path));
    }


    /**
     * 对实例方法进行插桩
     */
    @Test
    public void test2() throws Exception {
        DynamicType.Unloaded<UserManager> unloaded = new ByteBuddy()
                .subclass(UserManager.class)
                .name("a.b.SubObj")
                // named通过名字指定要拦截的方法
                .method(named("toString"))
                // 指定拦截到方法后该如何处理
                .intercept(FixedValue.value("hello bytebuddy"))
                .make();
        // Loaded表示生成的字节码已经被加载到jvm
        DynamicType.Loaded<UserManager> loaded = unloaded.load(getClass().getClassLoader());
        // 获取到Class对象
        Class<? extends UserManager> loadedClass = loaded.getLoaded();
        UserManager userManager = loadedClass.newInstance();
        String toStringResult = userManager.toString();
        // loadedClass.getClassLoader() = net.bytebuddy.dynamic.loading.ByteArrayClassLoader@4d5d943d
        System.out.println("loadedClass.getClassLoader() = " + loadedClass.getClassLoader());
        // toStringResult = hello bytebuddy
        System.out.println("toStringResult = " + toStringResult);
        // loaded同样有saveIn、getBytes、inject方法,和Unloaded继承自DynamicType
        loaded.saveIn(new File(path1));
    }

    /**
     * 生成一个类
     */
    @Test
    public void test1() throws Exception {
        // 默认的策略是 new NamingStrategy.SuffixingRandom("ByteBuddy")
        NamingStrategy.SuffixingRandom suffixingRandom = new NamingStrategy.SuffixingRandom("roadjava");
        // Unloaded表示生成的字节码还未加载到jvm
        DynamicType.Unloaded<Object> unloaded = new ByteBuddy()
                // 默认是true,如果生成的字节码不合法会报错,如: Illegal type name: a.b.1SubObj for class a.b.1SubObj
                .with(TypeValidation.of(true))
                // 指定生成的类的命名策略
                .with(suffixingRandom)
                // 指定父类
                .subclass(Object.class)
                /*
                subclass生成的类的命名规则:
                在不指定命名策略的情况下:
                    1.对于父类是jdk自带的类的情况: net.bytebuddy.renamed.java.lang.Object$ByteBuddy$rA5n9r7V
                    2.对于父类非jdk自带的类的情况: com.roadjava.bytebuddy.UserManager$ByteBuddy$bMMxsrAa
                在指定命名策略的情况下:
                    1.对于父类是jdk自带的类的情况: net.bytebuddy.renamed.java.lang.Object$roadjava$XHRgCl9v
                    2.对于父类非jdk自带的类的情况: com.roadjava.bytebuddy.UserManager$roadjava$pJGjM1mw
                 */
                // 指定具体的类名
                .name("a.b.SubObj")
                .make();

        // 获取生成的类的字节码并自己写入到指定文件
        byte[] bytes = unloaded.getBytes();
        FileUtils.writeByteArrayToFile(new File("D:\\tmp\\a\\b\\SubObj.class"), bytes);
        // 把生成的类的字节码保存到哪个目录
//        unloaded.saveIn(new File(path));
        // 把生成的字节码直接注入到某个jar里面
//        unloaded.inject(new File("E:\\ideaProjects3\\code-tool-prepare\\target\\code-tool-prepare-1.0-SNAPSHOT.jar"));
    }


}