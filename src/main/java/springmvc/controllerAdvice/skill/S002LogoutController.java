package springmvc.controllerAdvice.skill;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/ss002")
public class S002LogoutController {

    @GetMapping("/init")
    public ModelAndView init(Model model) {
		
		// 获取出@ControllerAdvice增强类中提前放入Model中的数据
        Map<String, Object> mapInfo = model.asMap();
        System.out.println(mapInfo);
		
		// 代码出现异常
        int num = 1 / 0;

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("s002");
        return modelAndView;
    }
}
