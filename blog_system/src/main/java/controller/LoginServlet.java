package controller;

import model.UserDao;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");
        // 1. 获取到的请求中的参数
        //    请求中的数据是这样的格式(username=zhangsan&password=123)，因此就需要使用 getParameter()。
        //    通过 form 表单，所以需要用 getParameter() 来获取表单的内容
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (username == null || "".equals(username) || password == null || "".equals(password)) {
            // 请求的内容缺失，肯定是登录失败！！
            resp.setContentType("text/html;charset=utf8");
            resp.getWriter().write("当前的用户名或密码为空！");
        }

        // 2. 和数据库中的内容进行比较
        UserDao userDao = new UserDao();
        User user = userDao.selectByName(username);
        if (user == null || user.getPassword().equals(password)) {
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
