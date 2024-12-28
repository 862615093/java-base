package frame.springboot.runner.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Step7 {

    /**
     * 第七步：打印 Banner
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Step6.class);

        app.setBanner((environment, sourceClass, out) -> out.println("Custom Banner <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"));

        app.run(args);


    }

}
