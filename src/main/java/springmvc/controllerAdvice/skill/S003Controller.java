package springmvc.controllerAdvice.skill;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/ss003")
public class S003Controller {

    @GetMapping("/init")
    public ModelAndView init() {

        int num = 1 / 0;

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("s003");
        return modelAndView;
    }
}
