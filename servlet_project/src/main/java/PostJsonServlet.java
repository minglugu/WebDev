import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 创建新的类
class User {
    // 当前属性都设置成public. 如果设为private，但是同时提供getter和setter的效果，是等同的。
    public int userId;
    public int classId;
}

@WebServlet("/postJson")
public class PostJsonServlet extends HttpServlet {
    // 1. 创建一个 jackson 的核心对象, 通过 ObjectMapper 来解析
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //2. 读取 body 中的请求，然后使用 ObjectMapper 来解析成需要的对象
        // readValue 就是把 JSON 格式的字符串({"userId":"dfd","classId":"222"})，转成 Java 的对象()
        // 第一个参数，表示对哪个字符串进行转换，这个参数可以填写成一个 String， 也可以填写一个 InputStream 对象，还可以填一个 file 对象
        // 第二个参数，表示要把这个 JSON 格式的字符串，转成哪个 Java 对象. 第二个参数是 class valueType
        User user = objectMapper.readValue(req.getInputStream(), User.class);
        resp.getWriter().write("userId: " + user.userId + ", classId: " + user.classId);
    }
}
