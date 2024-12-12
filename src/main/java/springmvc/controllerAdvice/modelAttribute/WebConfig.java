package springmvc.controllerAdvice.modelAttribute;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import springmvc.controllerAdvice.initBinder.MyDateFormatter;

@Configuration
public class WebConfig {

    @Controller
    static class Controller1 {
        @ResponseStatus(HttpStatus.OK)
        public ModelAndView foo(@ModelAttribute User user) {
            // 使用 @ResponseStatus 注解，咋不考虑返回值的处理
            System.out.println("foo");
            return null;
        }
    }

    @ControllerAdvice
    static class MyControllerAdvice {
        @ModelAttribute("a")
        public String aa() {
            return "aa";
        }
    }


    @Getter
    @Setter
    @ToString
    static class User {
        private String name;
    }
}

