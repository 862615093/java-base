package frame.springboot.runner.run;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

@Slf4j
public class Step4 {

    /**
     * 第四步：添加 ConfigurationPropertySources ---> 处理不规则的key
     *
     */
    public static void main(String[] args) throws IOException {
        ConfigurableEnvironment env = new StandardEnvironment();
        try {
            // 动态加载 properties 文件
            env.getPropertySources().addLast(new ResourcePropertySource("step4", new ClassPathResource("step4.properties")));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        env.getPropertySources().forEach(System.out::println);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println(env.getProperty("user.first-name"));  // George
        System.out.println(env.getProperty("user.middle-name")); // null
        System.out.println(env.getProperty("user.last-name"));   // null
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");


        ConfigurationPropertySources.attach(env);
        System.out.println("<<<<<<<<<<< 添加 ConfigurationPropertySources 后 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println(env.getProperty("user.first-name"));  // George
        System.out.println(env.getProperty("user.middle-name")); // Walker
        System.out.println(env.getProperty("user.last-name"));   // Bush
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }

}
