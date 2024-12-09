package springmvc.controllerAdvice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ss001")
public class S001LoginController {

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/init")
    public ModelAndView init(Model model) {

        // 方式1: 获取出@ControllerAdvice增强类中提前放入Model中的数据
        Map<String, Object> mapInfo = model.asMap();

        Object userMailObj = mapInfo.get("userMail");
        List<String> userMailList = (List<String>) userMailObj;
        System.out.println(userMailList);  // [123@mail.com, 456@mail.com]

        Map<String, String> userInfoMap = (Map<String, String>) mapInfo.get("userInfo");
        System.out.println(userInfoMap);  // {name=贾飞天, age=18}

        // 获取出@ControllerAdvice增强类中提前放入request的attribute中的数据
        Integer num1 = (Integer) request.getAttribute("num1");
        System.out.println(num1);  // 66

        Integer num2 = (Integer) request.getAttribute("num2");
        System.out.println(num2);  // 99

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("s001");
        return modelAndView;
    }

    // 方式2: 获取出@ControllerAdvice增强类中提前放入Model中的数据
    @GetMapping("/showInfo")
    @ResponseBody
    public void showInfo(@ModelAttribute("userMail") List<String> userMail,
                         @ModelAttribute("userInfo") Map<String, String> userInfo) {
        System.out.println(userMail);  // [123@mail.com, 456@mail.com]
        System.out.println(userInfo);  // {name=贾飞天, age=18}
    }
}
