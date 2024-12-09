package springmvc.controllerAdvice;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

// 指定增强S001LoginController和S002LogoutController
@ControllerAdvice(assignableTypes = {S001LoginController.class, S002LogoutController.class})
public class S_Controller {

    @Autowired
    private HttpServletRequest request;
	
	// 方式1: @ModelAttribute注解的使用
    @ModelAttribute
    public void initData1(Model model) {
        System.out.println("initData1()...");
        request.setAttribute("num1", 66);
        model.addAttribute("userMail", Lists.newArrayList("123@mail.com", "456@mail.com"));
    }
	
	// 方式2: @ModelAttribute注解的使用
    @ModelAttribute(name = "userInfo")
    public Map<String, String> initData2() {
        System.out.println("initData2()...");
        request.setAttribute("num2", 99);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", "贾飞天");
        hashMap.put("age", "18");
        return hashMap;
    }
	
	// 捕获S001LoginController或S002LogoutController类中的Exception异常
    @ExceptionHandler(Exception.class)
    public void exceptionHandle(Exception ex) {
        System.out.println("exceptionHandle()...");
        System.out.println(ex.getMessage());
    }
}
