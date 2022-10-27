import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


// 登录逻辑
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 处理用户的请求，按照约定要的key-value pair
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // 判定用户名或者密码是否正确
        // 正常来说这个判定操作，是要放到数据库中进行存取的
        // 此处是hardcode，为了简单，直接在代码里面写死了， 假设有效的用户名和密码是 “zhangsan” 密码是“123”
        // 这样写，可以规避空指针异常的情况
        if ("zhangsan".equals(username) && "123".equals(password)) {
            //登录成功, 规定了重定向的响应
            // 获取到用户的身份信息，当下需要创建好一个session，为后面来使用。
            // 在session 中填写上必要的身份信息(可以自定义，需要什么信息，就保存什么信息)，供后面的逻辑使用。
            // 创建会话，并保存必要的身份信息
            HttpSession httpSession = req.getSession(true); // 如果会话不存在，创建新的。存在就返回
            // 往会话中存储键值对，必要的身份信息
            httpSession.setAttribute("username", username); // 后续访问的话，就很容易获取到username里面的内容是什么
            // 会话中，可以保存其它任意信息。举例：计算登录的次数 count
            // 初始情况下，登录次数为0
            httpSession.setAttribute("count", 0);
            resp.sendRedirect("index");
        } else {
            //登录失败
            resp.getWriter().write("login failed");
        }

    }

}
