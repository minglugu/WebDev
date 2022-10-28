package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.Blog;
import controller.BlogDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// 通过这个类，来处理 /blog 路径对应的请求
@WebServlet("/blog")
public class BlogServlet extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();

    // 这个方法用来获取到数据库中的博客列表
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 可以用一个doGet()来实现，区别是一个带参数，一个不带参数
        // 获取博客列表请求是不带参数的，而获取博客内容（显示的博客全部正文）是带参数的。

        // GET /blog?blogId=1 这是带参数的，会跳转去blog_detail.html
        // GET /blog 是不带参数的，会跳转去blog_list.html

        // 因此先进行一个判断
        // 先尝试获取到 req 中的blogId 参数。如果参数存在，说明请求的是博客详情页
        // 如果参数不存在，说明请求的是博客列表页
        String param = req.getParameter("blogId");
        BlogDao blogDao = new BlogDao();
        if (param == null) {
            // 不存在参数，获取列表
            // 从数据库中查询到博客列表，转成 JSON 格式，然后直接返回即可。
            // 查询数据库，要创建BlogDao
            List<Blog> blogs = blogDao.selectAll();
            // 把 blogs 对象转成 JSON 格式
            String respJson = objectMapper.writeValueAsString(blogs);
            // 下面两行代码的顺序不能颠倒
            resp.setContentType("application/json; charset=utf8");
            resp.getWriter().write(respJson);
        } else {
            // 存在参数，获取博客详情
            int blogId = Integer.parseInt(param);
            // 得到 blog 对象
            Blog blog = blogDao.selectOne(blogId);
            String respJson = objectMapper.writeValueAsString(blog);
            resp.setContentType("application/json; charset=utf8");
            resp.getWriter().write(respJson);
        }
    }
}
