import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 把当前的 HelloServlet 类，和 http 请求的 URL 路径带有 /hello 这样的请求，就给关联起来了
// 也要保证是 get 方法，方法要匹配（此处不能是post）
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    // override doGet(), do：处理，Get对应到HTTP的 GET 方法。
    // 这个方法就是在 tomcat 收到了一个 HTTP GET 请求的时候，会被 Tomcat 调用到（回调函数）
    // 请求：Request 响应：Response
    // HttpServletRequest: 代表了 http 的请求。（Tomcat解析输入的数据）
    // HttpServletResponse: 代表了 http 的响应 （根据请求）
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 这个代码一定要comment掉，不能调用父类的doGet()
        super.doGet(req, resp);
        // 这个是服务器在控制台里面打印
        System.out.println("hello world!");

        // 在页面上也能打印 hello world. 把 hello world 字符串，放到 http 响应的 body 中。浏览器就会把 body 的内容显示到页面上
        // resp: 响应的对象。getWriter(): 返回了一个 Write 对象，字符流对象
        resp.getWriter().write("hello world!" + System.currentTimeMillis());
    }
}
