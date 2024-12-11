package springmvc.beanBinder_typeConvert;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestDataBinderFactory;

import java.util.Collections;
import java.util.Date;

@Slf4j
public class TestBeanBinder_TypeConverter {

    /**
     * 对象绑定和接口转换
     *
     * 1、底层第一套转换接口与实现
     * Printer 把其它类型转为 String
     * Parser 把 String 转为其它类型
     * Formatter 综合 Printer 与 Parser 的功能
     * Converter 把类型 S 转为类型 T
     * Printer、Parser、Converter 经过适配转换成 GenericConverter 放入 Converters 集合
     * FormattingConversionService 利用其它接口实现转换
     *
     * 2、JDK 提供
     * PropertyEditor 将 String 与其它类型相互转换
     * PropertyEditorRegistry 可以注册多个 PropertyEditor 对象
     * 可以通过 FormatterPropertyEditorAdapter 与第一套接口进行适配
     *
     * 3、高层转换接口与实现
     * SimpleTypeConverter 仅做类型转换
     * BeanWrapperImpl 利用 Property，即 Getter/Setter，为 Bean 的属性赋值，，必要时进行类型转换
     * DirectFieldAccessor 利用 Field，即字段，为 Bean 的字段赋值，必要时进行类型转换
     * ServletRequestDataBinder 为 Bean 的属性执行绑定，必要时进行类型转换，根据布尔类型成员变量 directFieldAccess 选择利用 Property 还是 Field，还具备校验与获取校验结果功能
     */
    public static void main(String[] args) {
//        testBeanWrapperImpl();
//        testDirectFieldAccessor();
//        testDataBinder();
//        testServletRequestDataBinder2();
//        testServletRequestDataBinder3();
        testServletRequestDataBinder4();
    }

    @SneakyThrows
    private static void testServletRequestDataBinder4() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("birthday", "1999|01|02");
        request.setParameter("address.name", "成都");

        User1 user = new User1();

        // 使用 ConversionService 有一个默认实现 DefaultFormattingConversionService，它还是 FormattingConversionService 的子类
        // 需要搭配注解 @DateTimeFormat 使用
        // SpringBoot 中还提供了 ApplicationConversionService，它也是 FormattingConversionService 的子类，
        // 上述代码将 DefaultFormattingConversionService 换成 ApplicationConversionService 也能达到相同效果。
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
        ConfigurableWebBindingInitializer initializer = new ConfigurableWebBindingInitializer();
        initializer.setConversionService(conversionService);
        ServletRequestDataBinderFactory factory = new ServletRequestDataBinderFactory(null, initializer);

        WebDataBinder dataBinder = factory.createBinder(new ServletWebRequest(request), user, "user");

        dataBinder.bind(new ServletRequestParameterPropertyValues(request));
        System.out.println(user);
    }

    @Getter
    @Setter
    @ToString
    public static class User1 {
//        @DateTimeFormat(pattern = "yyyy|MM|dd")
        private Date birthday;
        private Address address;
    }


    @SneakyThrows
    private static void testServletRequestDataBinder3() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("birthday", "1999|01|02");
        request.setParameter("address.name", "成都");

        User user = new User();

        // 使用 ConversionService 转换
        FormattingConversionService service = new FormattingConversionService();
        service.addFormatter(new MyDateFormatter("用 ConversionService 方式拓展转换功能"));

        ConfigurableWebBindingInitializer initializer = new ConfigurableWebBindingInitializer();
        initializer.setConversionService(service);
        ServletRequestDataBinderFactory factory = new ServletRequestDataBinderFactory(null, initializer);


        WebDataBinder dataBinder = factory.createBinder(new ServletWebRequest(request), user, "user");

        dataBinder.bind(new ServletRequestParameterPropertyValues(request));
        System.out.println(user);
    }

    @SneakyThrows
    private static void testServletRequestDataBinder2() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("birthday", "1999|01|02");
        request.setParameter("address.name", "成都");

        User user = new User();
        InvocableHandlerMethod handlerMethod = new InvocableHandlerMethod(new MyController(), MyController.class.getMethod("myMethod", WebDataBinder.class));
        ServletRequestDataBinderFactory factory = new ServletRequestDataBinderFactory(Collections.singletonList(handlerMethod), null);
        WebDataBinder dataBinder = factory.createBinder(new ServletWebRequest(request), user, "user");

        dataBinder.bind(new ServletRequestParameterPropertyValues(request));
        System.out.println(user);
    }


    static class MyController {
        @InitBinder
        public void myMethod(WebDataBinder dataBinder) {
            // 拓展 dataBinder 的转换器 (使用了 JDK 提供的 PropertyEditorRegistry)
            dataBinder.addCustomFormatter(new MyDateFormatter("用 @InitBinder 进行拓展"));
        }
    }


    @Getter
    @Setter
    @ToString
    public static class User {
        private Date birthday;
        private Address address;
    }

    @Getter
    @Setter
    @ToString
    public static class Address {
        private String name;
    }


    /**
     * Web 环境下的数据绑定
     * 使用 DataBinder 的子类 ServletRequestDataBinder
     */
    private static void testServletRequestDataBinder() {
        // web 环境下的数据绑定
        MyBean bean = new MyBean();
        DataBinder dataBinder = new ServletRequestDataBinder(bean);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("a", "120");
        request.setParameter("b", "hello");
        request.setParameter("c", "1999/03/04");
        dataBinder.bind(new ServletRequestParameterPropertyValues(request));
        System.out.println(bean);
    }


    private static void testDataBinder() {
        // 执行数据绑定
        MyBean bean = new MyBean();
        DataBinder binder = new DataBinder(bean);
        MutablePropertyValues pvs = new MutablePropertyValues();
        pvs.add("a", "110");
        pvs.add("b", "hello");
        pvs.add("c", "1999/03/04");
        binder.bind(pvs);
        System.out.println(bean);
    }

    private static void testDirectFieldAccessor() {
        // 利用反射为 bean 的字段赋值
        MyBean bean = new MyBean();
        DirectFieldAccessor accessor = new DirectFieldAccessor(bean);
        accessor.setPropertyValue("a", "10");
        accessor.setPropertyValue("b", "hello");
        accessor.setPropertyValue("c", "1999/03/04");
        System.out.println(bean);
    }


    private static void testBeanWrapperImpl() {
        // 利用反射为 bean 的属性赋值
        MyBean bean = new MyBean();
        BeanWrapperImpl wrapper = new BeanWrapperImpl(bean);
        wrapper.setPropertyValue("a", "10");
        wrapper.setPropertyValue("b", "hello");
        wrapper.setPropertyValue("c", "1999/03/04");
        System.out.println(bean);
    }

    @Getter
    @Setter
    @ToString
    static class MyBean {
        private int a;
        private String b;
        private Date c;
    }

    private static void testSimpleTypeConverter() {
        SimpleTypeConverter converter = new SimpleTypeConverter();
        Integer number = converter.convertIfNecessary("13", int.class);
        System.out.println(number);
        Date date = converter.convertIfNecessary("1999/03/04", Date.class);
        System.out.println(date);
    }


}
