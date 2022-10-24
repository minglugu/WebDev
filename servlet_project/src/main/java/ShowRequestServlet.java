import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

// show the request
@WebServlet("/showRequest")
public class ShowRequestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 调用一下刚才涉及到的几个关键 API, 并且把得到的结果组织到一个 html 中，并作为响应的 body

        // 把 API 执行的结果，放这个 stringBuilder 里面
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<h3>首行部分</h3>");
        stringBuilder.append(req.getProtocol());    // HTTP/1.1
        stringBuilder.append("<br>");
        stringBuilder.append(req.getMethod());      // GET
        stringBuilder.append("<br>");
        stringBuilder.append(req.getRequestURI());  //   /hello102/showRequest
        stringBuilder.append("<br>");
        stringBuilder.append(req.getContextPath()); //   /hello102
        stringBuilder.append("<br>");
        stringBuilder.append(req.getQueryString()); //   null
        stringBuilder.append("<br>");
        stringBuilder.append("<h3>header 部分</h3>");
        stringBuilder.append("<br>");
        Enumeration<String> headerNames = req.getHeaderNames();
        // 类似迭代器的遍历
        while(headerNames.hasMoreElements()) {
            // key
            String headerName = headerNames.nextElement();
            // value
            String headerValue = req.getHeader(headerName);
            // key-value pair
            stringBuilder.append(headerName + ": " + headerValue + "<br>");
        }

        // The line in the HTTP header typically looks like this:
        // Content-Type: text/html; charset=utf-8
        // URL: https://www.w3.org/International/articles/http-charset/index

        resp.setContentType("text/html; charset=utf8");
        resp.getWriter().write(stringBuilder.toString());
    }
}
