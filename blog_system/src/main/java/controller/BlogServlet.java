package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Blog;
import model.BlogDao;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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

    // 服务器代码, 发布新写好的服务器代码
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        // 1. sesion 和 user 必须同时不为空，才能确认 用户已经登录
        //    判断 session 是否为空
        if (session == null) {
            // 当前用户未登录，不能提交博客
            resp.setContentType("text/html;charset=utf8");
            resp.getWriter().write("当前用户未登录，不能提交博客");
            return;
        }
        User user = (User) session.getAttribute("user");
        // 判断是否 user 为空
        if (user == null) {
            resp.setContentType("text/html;charset=utf8");
            resp.getWriter().write("当前用户未登录，不能提交博客");
            return;
        }

        // 一定要指定好，请求按照那种 编码 来解析
        req.setCharacterEncoding("utf8");
        // 2. 先从请求中，获取到参数(博客的标题和正文)
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        if (title == null || "".equals(title) || content == null || "".equals(content)) {
            // 保证中文不乱码
            resp.setContentType("text/html;charset=utf8");
            // 直接告诉客户端，请求参数不对
            resp.getWriter().write("提交博客失败！缺少必要的参数！");
            return;
        }
        // 3. 数据正确，那么就构造 blog 对象，把当前的信息填进去，并插入到数据库中
        // 需要指定的信息有 title, content, userId(作者信息)
        // blogId 和 postTime 是数据库自动设置的，无需写额外的代码。
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setContent(content);
        // blog userId 如何指定？作者id就是当前提交这个博客的用户的身份信息，可以在 session 中获取到
        // 用户对象的userId 是从 session 里面获取。所以在doPost()里，先判断session和user不为空，拿到用户信息
        blog.setUserId(user.getUserId());

        // 4. 插入数据库
        BlogDao blogDao = new BlogDao();
        blogDao.insert(blog);

        // 5. 重定向到blog_list.html
        resp.sendRedirect("blog_list.html");
    }
}
