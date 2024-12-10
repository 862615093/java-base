package springmvc.requestMappingHandlerAdapter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class Controller1 {
    @GetMapping("/test1")
    public ModelAndView test1() throws Exception {
        log.debug("test1()");
        return null;
    }

    @PostMapping("/test2")
    public ModelAndView test2(@RequestParam("name") String name) {
        log.debug("test2({})", name);
        return null;
    }

    @PutMapping("/test3")
    public ModelAndView test3(String token) {
        log.debug("test3({})", token);
        return null;
    }

    @RequestMapping("/test4")
    public User test4() {
        log.debug("test4");
        return new User("张三", 18);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class User {
        String name;
        int age;
    }
}
