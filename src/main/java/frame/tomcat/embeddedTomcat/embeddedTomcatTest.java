package frame.tomcat.embeddedTomcat;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.http11.Http11Nio2Protocol;

import java.io.File;
import java.nio.file.Files;
import java.util.Collections;

@Slf4j
public class embeddedTomcatTest {

    /**
     * 内嵌 Tomcat 的使用分为 6 步：
     * 1、创建 Tomcat
     * 2、创建项目文件夹，即 docBase 文件夹
     * 3、创建 Tomcat 项目，在 Tomcat 中称为 Context
     * 4、编程添加 Servlet
     * 5、启动 Tomcat
     * 6、创建连接器，设置监听端口
     */
    @SneakyThrows
    public static void main(String[] args) {
        // 1. 创建 Tomcat
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("tomcat");
        // 2. 创建项目文件夹，即 docBase 文件夹
        File docBase = Files.createTempDirectory("boot.").toFile();
        docBase.deleteOnExit();
        // 3. 创建 tomcat 项目，在 tomcat 中称为 Context
        Context context = tomcat.addContext("", docBase.getAbsolutePath());
        // 4. 编程添加 Servlet
        context.addServletContainerInitializer((set, servletContext) -> {
            HelloServlet helloServlet = new HelloServlet();
            // 还要设置访问 Servlet 的路径
            servletContext.addServlet("hello", helloServlet).addMapping("/hello");
        }, Collections.emptySet());
        // 5. 启动 tomcat
        tomcat.start();
        // 6. 创建连接器，设置监听端口
        Connector connector = new Connector(new Http11Nio2Protocol());
        connector.setPort(8080);
        tomcat.setConnector(connector);
    }

}
