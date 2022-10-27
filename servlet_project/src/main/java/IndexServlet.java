import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// 主页逻辑
@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 返回一个主页。（简单的 html 片段）
        // 此处需要得到 username 是什么，从Httpsession 中得到, 因为是前面登录过的，所以session 已经存在了，所以设置成 false
        // 此处的 session 是由 LoginServlet创建并设置 (httpSession.setAttribute()) 了session的username。因此是同一个对象
        HttpSession session = req.getSession(false);
        String username = (String) session.getAttribute("username");
        // 还从会话中，取出登录的次数 count. Integer这样的包装类
        Integer count = (Integer) session.getAttribute("count");
        // 修改次数
        count++;
        // 写入session: 把自增的值，写回到会话中,是键值对
        session.setAttribute("count", count);

        resp.setContentType("text/html;charset=utf8");
        resp.getWriter().write("<h3>欢迎你！ " + username + "。这是第" + count + "次访问主页 </h3>");
    }
}
