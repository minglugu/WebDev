import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Message {
    public String from;
    public String to;
    public String message;
}

@WebServlet("/message")
public class MessageServlet extends HttpServlet {
    // ObjectMapper (Jackson.databind)
    private ObjectMapper objectMapper = new ObjectMapper();
    private List<Message> messages = new ArrayList<>();

    // 客户端给服务器提交留言
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 处理提交消息的请求
        // 解析
        Message message = objectMapper.readValue(req.getInputStream(), Message.class);

        // 使用服务器，将前端信息保存到内存里，数据就不会消失。这是最简单的方法
        messages.add(message);

        // 通过content type 告知页面，返回的数据是 JSON 格式
        // 有了这样的声明，此时 jquery ajax 就会自动的帮我们把字符串转成 js 对象
        // 如果没有，jquery ajax 就只是当成字符串来处理的
        resp.setContentType("application/json; charset=utf8");
        resp.getWriter().write("{ \"OK\": true }");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取消息的列表，只要把消息列表中的内容，整个的都返回给客户端即可
        // 此处需要使用 ObjectMapper 把 Java 对象，转成 JSON 格式字符串
        String jsonString = objectMapper.writeValueAsString(messages);
        System.out.println("jsonString: " + jsonString);
        resp.setContentType("application/json; charset=utf8");
        resp.getWriter().write(jsonString);
    }
}
