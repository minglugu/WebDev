import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 定义关联的路径，同一个webapp里面，servlet package里面，多个Servlet关联的路径名，不可以相同
@WebServlet("/method")
public class MethodServlet extends HelloServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // super.doPost(req, resp);

        // 当前 body 里面的数据格式
        resp.setContentType("text/html; charset=utf8");
        resp.getWriter().write("response from POST method: POST的响应");
    }
}
