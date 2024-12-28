package frame.tomcat.embeddedTomcat;

import org.apache.catalina.util.ServerInfo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自行实现的 Servlet 需要继承 HttpServlet，并重写 doGet() 方法
 */
public class HelloServlet extends HttpServlet {
    private static final long serialVersionUID = 8117441197359625079L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().print("<h3>hello</h3>");
    }

}
