package frame.springboot.runner.run;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@SpringBootApplication
public class Step3 {

    /**
     * 第三步：准备 Environment
     * Environment 即环境对象，是对配置信息的抽象，配置信息的来源有多种，比如：系统环境变量、properties 配置文件、YAML 配置文件 等等。
     *
     */
    public static void main(String[] args) throws IOException {
        SpringApplication app = new SpringApplication(Step3.class);
        ApplicationContext context = app.run(args);
        Environment environment = context.getEnvironment();

        // 获取 properties 配置文件、YAML 配置文件
        System.out.println(">>>>>>>>>>>>>>>>>>>>>" + environment.getProperty("server.port"));

        // 获取 系统环境变量
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + environment.getProperty("JAVA_HOME"));

        String[] defaultProfiles = environment.getDefaultProfiles();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>" + Arrays.toString(defaultProfiles));
        String[] activeProfiles = environment.getActiveProfiles();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>" + Arrays.toString(activeProfiles));
    }


}
