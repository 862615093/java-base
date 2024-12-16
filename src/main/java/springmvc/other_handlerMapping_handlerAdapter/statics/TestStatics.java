package springmvc.other_handlerMapping_handlerAdapter.statics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import springmvc.other_handlerMapping_handlerAdapter.statics.WebConfig;

@Slf4j
public class TestStatics {

    public static void main(String[] args) {
        AnnotationConfigServletWebServerApplicationContext context = new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
    }


}
