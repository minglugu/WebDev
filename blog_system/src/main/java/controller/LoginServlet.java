package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.UserDao;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();

    // 这个方法用来让前端检测当前的登录状态。
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf8");
        HttpSession session = req.getSession(false);
        if(session == null) {
            // 检测下，会话是否存在，不存在，说明未登录！
            User user = new User();
            // The method writeValueAsString of ObjectMapper class generates a JSON from a Java object
            // and return the generated JSON as a string.
            resp.getWriter().write(objectMapper.writeValueAsString(user)); // 空的 user 对象,以 JSON 格式写出来
            return;
        }
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // 虽然有会话，但是会话里面，没有 user 对象，也视为未登录。
            user = new User();
            resp.getWriter().write(objectMapper.writeValueAsString(user)); // 空的 user 对象,以 JSON 格式写出来
            return;
        }

        // 已经登录的状态！
        // 注意，此处不要把密码返回给前端
        user.setPassword("");
        resp.getWriter().write(objectMapper.writeValueAsString(user)); // user 对象,以 JSON 格式写出来
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");
        // 1. 获取到的请求中的参数
        //    请求中的数据是这样的格式(username=zhangsan&password=123)，因此就需要使用 getParameter()。
        //    通过 form 表单，所以需要用 getParameter() 来获取表单的内容
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        // 验证用户名是否为乱码
        System.out.println("username=" + username + ", password=" + password);
        if (username == null || "".equals(username) || password == null || "".equals(password)) {
            // 请求的内容缺失，登录失败！！
            resp.setContentType("text/html;charset=utf8");
            resp.getWriter().write("当前的用户名或密码为空！");
            return;
        }

        // 2. 和数据库中的内容进行比较
        UserDao userDao = new UserDao();
        User user = userDao.selectByName(username);
        if (user == null || !user.getPassword().equals(password)) {
            // 用户没有查到，或者密码不匹配，也是登录失败!
            resp.setContentType("text/html;charset=utf8");
            resp.getWriter().write("用户名或密码错误！");
            return;
        }

        // 3 .如果比较通过，就创建会话，才能分辨用户的信息
        HttpSession session = req.getSession(true);
        // 把刚才的用户信息，存在会话中. 把上面查到的 user 对象，放到键值对里面
        session.setAttribute("user", user);

        // 4. 返回一个重定向的报文，跳转到博客列表页
        resp.sendRedirect("blog_list.html");
    }
}
