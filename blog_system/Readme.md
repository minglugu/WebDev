DBUtil: video JAVA EE 初阶 #35 2022-05-23 video


# 在blog edit页面，引入 editor.md 编辑器

# 页面.html
<div id="editor"> </div>

# 初始化.js
<!-- 初始化编辑器 -->
var editor = editormd("editor", {
    <!-- 尺寸必须在这里设置，设置样式会被 editormd 自动覆盖掉 -->
    width: "100%",
    <!-- 设定编辑器高度 -->
    height: "500px",
    <!-- 编辑器中的初始化内容 -->
    markdown: "# 在这里写下一篇博客",
    <!-- 指定 editor.md 依赖的插件路径 -->
    path: "editor.md/lib/"
});

# 获得编辑框的内容.js
<!-- 获取 markdown 格式的数据 -->
editor.getMarkdown();
<!-- 获取 html 格式的数据 -->
editor.getHTML();


1. download editor.md: https://pandao.github.io/editor.md/en.html
2. save the editor.md folder into the project

3. import editor.md in blog_edit.html

4. jQuery 不是 editor.md 的一部分,而是 editor.md 的依赖库
   所以还要引入 jQuery. 
   - releases.jquery.com
   - jQuery Core 3.6.0 minified version
   - copy the path in src(https://code.jquery.com/jquery-3.6.1.min.js), paste in Google 
   - 打开 jQuery 的源码文件,ctrl+A, and ctrl+v in jquery.min.js 里面. 或者直接复制<script>
    src="https://code.jquery.com/jquery-3.6.1.min.js"
    integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ="
    crossorigin="anonymous"></script>, 但有时不稳定.建议复制到本地.

## 前端引入第三方库，其实是非常容易的。
## 从官网，下载好第三方库，保存到自己的项目目录中，在html中引入 css或js 文件。

-   每个页面都要约定前后端交互的接口



## ObjectMapper:
URL: https://www.baeldung.com/jackson-object-mapper-tutorial
Jackson ObjectMapper class: serialize Java objects into JSON and deserialize JSON string into Java objects.

- JSON to Java Object
  objectMapper.readValue() API of the ObjectMapper parses or deserializes JSON content into a Java object.

- Java Object to JSON
  objectMapper.writeValueAsString() returns the generated JSON as a string or as a byte array.
  
## Data Access Object
URL: https://www.oracle.com/java/technologies/data-access-object.html#:~:text=The%20Data%20Access%20Object%20(or,to%20a%20generic%20client%20interface

## 先修改 blog_list.html 里的代码
   此处访问的是静态页面，所以输入URL这个：http://127.0.0.1:8080/blog_system/blog_list.html

问题1.
## 博客的发布顺序和排序。 需要保证最新的博客得在最上面
    在BlogDao中，加入order by

问题2. 
## 刷新页面的时候，页面会有个替换过程，会有页面的滞后
删除旧的测试数据。 blog_list.html里面，注释掉

## 博客详情页
http://127.0.0.1:8080/blog_system/blog_detail.html?blogId=5 发送请求
这个正文内容，需要继续通过 ajax 来进行获取
在 blog_detail.html 页面加载的时候，触发 ajax 请求来访问服务器，获取到博客内容，再次填充到博客详情页里面
1. 约定前后端接口
2. 写后端BlogServlet class里面的doGet()
   后端代码实现和博客列表页的获取，基本相同。所以放在doGet() 方法中实现了。使用 blogId 参数来区分是获取博客列表，还是博客详情
3. 写前端代码。blog_detail.html
   在前端代码这里，要构造一个请求，获取博客详情，就需要知道用户点击的博客的blogId
   blogId就已经包含在当前的 blog_detail.html 的页面的 url(127.0.0.1:8080/blog_system/blog?blogId=5) 里了
   用location.search 就能够获取到 ?blogId=5
   
   总结：希望在博客详情页，拿到博客的具体内容
   就需要构造一个请求 /blog?blogId=5, 有了这个参数，才能够告诉服务器，需要哪个博客
   请求是blog_detail.html 通过 ajax 发送的。blog_detail.html 就需要构造出 blogId=5 的这个参数。关键是这个参数(5)是从哪里来的呢？
   答：当来到blog_detail.html 这个页面的时候，url 里面就已经带上了这个参数（127.0.0.1:8080/blog_system/blog?blogId=5）
   通过location.search 就能够拿到 ?blogId=5 这段内容。从而构造出 /blog?blogId=5 这样的请求。
   ajax中的url: 'blog' + location.search
   
数据流动的路径，是 web 开发中的一个难点


blog_detail.html
约定前后端交互接口
request:
POST/login
Content-Type: application/x-www-form-urlencoded     // 使用提交按钮， input type="submit"

username=zhangsan&password=123

response:
HTTP/1.1 302                // 当登录成功后，就自动跳转到，主页(博客列表页)
Location: blog_list.html
登录页面做调整

## 规定了，登录后，才能访问博客列表页
1.  约定前后端交互接口
    请求：
    GET /login
    
    响应：
    登录成功，就返回登录的用户信息。
    未登录，则返回 userId 为 0 的对象（类似一个空的对象）    {
                                                        userId: 0,
                                                        username: ""
                                                     }

    HTTP/1.1 200 OK
    Content-type: application/json
    {
        userId: 1,
        username: zhangsan,
        
    }

## 登录的检测功能。
1. 在后端 Java 代码中，需要判定 session 和 user 是否为空。
2. 在前端代码 blog_list.html 里面，如果未登录，需要重定向到登录页 blog_login.html 里面，登录成功后，才能看到博客列表

1)  针对博客列表页
    这里其实前面处理过了，是在检测用户登录状态的时候
    
    注意！当前用户名正常了，但用户的头像，github，文章的统计，都是没有变化的
    在设计的数据库中，添加相应的字段(github, 文章数量的统计)，然后用替换用户名的类似办法，把card这个属性里面的信息都换掉
    
2)  针对博客详情页
    看到的是，该博客的作者名
    因此，需要处理一下，让博客列表页和详情页，做一些区分
    也就是列表页和详情页显示不一样的内容
    
    比如说，针对当前这个 blogId 为 11 的博客来说，这个博客的作者是 lisi，此处显示的用户名，也应该是lisi
    让服务器，提供一个新接口。这个接口，可以让客户端指定 blogId，获取到指定的blogId的作者信息
    
    重新约定 前后端的接口. 在博客详情页，给服务器发送这个接口，来获取到当前的数据.

    请求：
    GET /authorInfo?blogId=11
    
    响应：
    {
        userId: 2,
        username: 'lisi'    
    }

    另外，对于博客详情页来说，在 getUserInfo() 里面的, 方法名需要修改 changeUserName(body.username) 
    
    
    
    