package springmvc.httpMessageConverter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.mock.http.MockHttpInputMessage;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Slf4j
public class TestHttpMessageConverter {

    /**
     * 学到了什么： http请求得响应返回值类型转换成什么样格式
     *
     * 在构造参数解析器 RequestResponseBodyMethodProcessor、返回值解析器 HttpEntityMethodProcessor 和 HttpEntityMethodProcessor 时，都需要传入消息转换器列表。
     * 消息转换器的基类是 HttpMessageConverter。
     *
     * 介绍两个常见的消息转换器的实现：
     * 1、MappingJackson2HttpMessageConverter
     * 2、MappingJackson2XmlHttpMessageConverter
     *
     * a. MessageConverter 的作用, @ResponseBody 是返回值处理器解析的, 但具体转换工作是 MessageConverter 做的
     * b. 如何选择 MediaType
     *     - 首先看 @RequestMapping 上有没有指定
     *     - 其次看 request 的 Accept 头有没有指定
     *     - 最后按 MessageConverter 的顺序, 谁能谁先转换
     *
     */
    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
        test4();
    }

    @SneakyThrows
    public static void test1() {
        // http响应
        MockHttpOutputMessage message = new MockHttpOutputMessage();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        // 判断能否将对象转换成目标消息格式
        if (converter.canWrite(User.class, MediaType.APPLICATION_JSON)) {
            converter.write(new User("张三", 18), MediaType.APPLICATION_JSON, message);
            System.out.println(message.getBodyAsString());
        }
    }

    @SneakyThrows
    public static void test2() {
        MockHttpOutputMessage message = new MockHttpOutputMessage();
        MappingJackson2XmlHttpMessageConverter converter = new MappingJackson2XmlHttpMessageConverter();
        if (converter.canWrite(User.class, MediaType.APPLICATION_XML)) {
            converter.write(new User("李四", 20), MediaType.APPLICATION_XML, message);
            System.out.println(message.getBodyAsString());
        }
    }

    @SneakyThrows
    public static void test3() {
        //language=JSON
        String json = "{\n" +
                "  \"name\": \"李四\",\n" +
                "  \"age\": 20\n" +
                "}";
        //http 请求
        MockHttpInputMessage message = new MockHttpInputMessage(json.getBytes(StandardCharsets.UTF_8));
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        if (converter.canRead(User.class, MediaType.APPLICATION_JSON)) {
            Object read = converter.read(User.class, message);
            System.out.println(read);
        }
    }

    //存在多个消息转换器
    @SneakyThrows
    public static void test4() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        ServletWebRequest webRequest = new ServletWebRequest(request, response);

        request.addHeader(HttpHeaders.ACCEPT, MimeTypeUtils.APPLICATION_XML_VALUE);
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE); // 等效于： @RequestMapping(produces = MimeTypeUtils.APPLICATION_JSON_VALUE)

        RequestResponseBodyMethodProcessor processor = new RequestResponseBodyMethodProcessor(Arrays.asList(
                new MappingJackson2HttpMessageConverter(), // 哪个转换器放在前面，那个就生效
                new MappingJackson2XmlHttpMessageConverter()
        ));
        processor.handleReturnValue(
                new User("张三", 18),
                new MethodParameter(TestHttpMessageConverter.class.getMethod("user"), -1),
                new ModelAndViewContainer(),
                webRequest
        );
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println(new String(response.getContentAsByteArray(), StandardCharsets.UTF_8));
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }



    @ResponseBody
    public User user() {
        return null;
    }


}
