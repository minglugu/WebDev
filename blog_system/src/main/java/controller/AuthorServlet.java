package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Blog;
import model.BlogDao;
import model.User;
import model.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/authorInfo")
public class AuthorServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=utf8");
        // 通过这个方法，来获取到指定的博客的作者的信息
        String param = req.getParameter("blogId");
        if (param == null || "".equals(param)) {
            // 参数缺失, 给出错误提示
            resp.getWriter().write("{\" ok \": false, \"reason\": \"参数缺失\"}");
            return;
        }

        // 根据当前的blogId 在数据库中进行查找，找到对应的 blog 对象，在根据 blog 对象，找到 author 的信息
        BlogDao blogDao = new BlogDao();
        int blogId = Integer.parseInt(param);
        Blog blog = blogDao.selectOne(blogId);
        // 判断 blog 对象，是否为空
        if(blog == null) {
            // 参数缺失, 给出错误提示
            resp.getWriter().write("{\" ok \": false, \"reason\": \"要查询的博客不存在\"}");
            return;
        }

        // 再根据blog对象，查询到用户对象
        UserDao userDao = new UserDao();
        // blog对象里面有个 userId 属性，可以通过 getUserId() 这个方法，来获取到博客的 userId，
        User author = userDao.selectById(blog.getUserId());
        if(author == null) {
            // 参数缺失, 给出错误提示
            resp.getWriter().write("{\" ok \": false, \"reason\": \"要查询的用户不存在\"}");
            return;
        }

        // 此时作者已经查到，把 author 返回到浏览器上, 把author对象，构造成 JSON 格式的字符串
        // Important: 把显示的密码给去掉
        author.setPassword("");
        resp.getWriter().write(objectMapper.writeValueAsString(author));
    }
}
