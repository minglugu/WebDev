import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    // 不能持久化保存数据，servlet重启是，会导致 message 数据丢失
    // private List<Message> messages = new ArrayList<>();
    // 因此 改成数据库，就不需要这个变量了

    // 客户端给服务器提交留言
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 处理提交消息的请求
        // 解析
        Message message = objectMapper.readValue(req.getInputStream(), Message.class);

        // 使用服务器，将前端信息保存到内存里，数据就不会消失。这是最简单的方法
        // messages.add(message);

        // 通过content type 告知页面，返回的数据是 JSON 格式
        // 有了这样的声明，此时 jquery ajax 就会自动的帮我们把字符串转成 js 对象
        // 如果没有，jquery ajax 就只是当成字符串来处理的
        // 用save() 直接保存 message
        save(message);
        resp.setContentType("application/json; charset=utf8");
        resp.getWriter().write("{ \"OK\": true }");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取消息的列表，只要把消息列表中的内容，整个的都返回给客户端即可
        // 此处需要使用 ObjectMapper 把 Java 对象，转成 JSON 格式字符串
        // 从数据库中，获取messages
        List<Message> messages = load();
        String jsonString = objectMapper.writeValueAsString(messages);
        System.out.println("jsonString: " + jsonString);
        resp.setContentType("application/json; charset=utf8");
        resp.getWriter().write(jsonString);
    }

    // 把一条消息保存到数据库中
    private void save(Message message) {
        Connection connection = null;
        PreparedStatement statement = null;
        // 1. 和数据库建立连接
        try {
            connection = DBUtil.getConnection();
            // 2. 构造 SQL query
            String sql = "insert into message values(?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, message.from);
            statement.setString(2, message.to);
            statement.setString(3, message.message);
            // 3. 执行 sql 插入操作的语句，
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 4. 释放资源
            DBUtil.close(connection, statement, null);
        }
    }

    private List<Message> load() {
        // 从数据库里面获取到的message，会保存到这个 ArrayList 中
        List<Message> messages = new ArrayList<>();
        // 从数据库中获取到所有的 message
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select * from message";
            // Creates a PreparedStatement object for sending parameterized SQL statements to the database
            statement = connection.prepareStatement(sql);
            // 查询 操作
            resultSet = statement.executeQuery();
            // 遍历结果集 resultSet
            while(resultSet.next()) {
                // 创建 message 对象
                Message message = new Message();
                message.from = resultSet.getString("from");
                message.to = resultSet.getString("to");
                message.message = resultSet.getString("message");
                messages.add(message);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, resultSet);
        }
        return messages;
    }
}
