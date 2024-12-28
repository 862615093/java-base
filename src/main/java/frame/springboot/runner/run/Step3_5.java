package frame.springboot.runner.run;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.io.IOException;

@Slf4j
@SpringBootApplication
public class Step3_5 {

    /**
     * 第三步：准备 Environment
     * Environment 即环境对象，是对配置信息的抽象，配置信息的来源有多种，比如：系统环境变量。
     *
     * 第五步：使用 EnvironmentPostProcessor 进行环境对象后置处理 ， 读取 properties 配置文件、YAML 配置文件
     * 读取 properties、YAML 配置文件的源就是在第五步中添加的。
     *
     */
    public static void main(String[] args) throws IOException {
        SpringApplication app = new SpringApplication(Step3_5.class);
        ApplicationContext context = app.run(args);
        Environment environment = context.getEnvironment();

        // 获取 properties 配置文件、YAML 配置文件
        System.out.println(">>>>>>>>>>>>>>>>>>>>>" + environment.getProperty("server.port"));

        // 获取 系统环境变量
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>" + environment.getProperty("JAVA_HOME"));

    }


}
