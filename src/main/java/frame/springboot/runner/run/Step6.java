package frame.springboot.runner.run;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.ToString;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;

import java.lang.reflect.Field;

@SpringBootApplication
public class Step6 {

    /**
     * 第六步：绑定 spring.main 前缀的配置信息到 SpringApplication 对象
     * 使用 @ConfigurationProperties 注解可以指定一个前缀，SpringBoot 将根据指定的前缀和属性名称在配置文件中寻找对应的信息并完成注入，其底层是利用 Binder 实现的。
     *
     * 学到了什么：
     *    1、项目中常用的 @ConfigurationProperties 注解 底层就是使用Binder类实现的
     *    2、还能松耦合绑定参数，底层是 ConfigurationPropertySources类实现的
     */
    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Step6.class);
        ConfigurableEnvironment env = app.run(args).getEnvironment();

        //配置文件绑定
        env.getPropertySources().addLast(new ResourcePropertySource("step4", new ClassPathResource("step4.properties")));
        User user = Binder.get(env).bind("user", User.class).get();
        System.out.println("user====> " + user);

        //实例绑定
        User existUser = new User();
        Binder.get(env).bind("user", Bindable.ofInstance(existUser));
        System.out.println("existUser====>" + existUser);

        //模拟 绑定 spring.main 开头的配置信息到 SpringApplication 对象中
        env.getPropertySources().addLast(new ResourcePropertySource("step6", new ClassPathResource("step6.properties")));
        Class<? extends SpringApplication> clazz = app.getClass();
        Field bannerMode = clazz.getDeclaredField("bannerMode");
        bannerMode.setAccessible(true);
        Field lazyInitialization = clazz.getDeclaredField("lazyInitialization");
        lazyInitialization.setAccessible(true);
        System.out.println(bannerMode.get(app)); // CONSOLE
        System.out.println(lazyInitialization.get(app)); // false
        Binder.get(env).bind("spring.main", Bindable.ofInstance(app));
        System.out.println(bannerMode.get(app)); // OFF
        System.out.println(lazyInitialization.get(app)); // true
    }

    @Getter
    @Setter
    @ToString
    static class User {
        private String firstName;
        private String middleName;
        private String lastName;
    }

}
