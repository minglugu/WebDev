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

视频 #37
上述功能，就实现了把用户信息，显示到页面上(左侧卡片中)
显示博客作者信息，而不是登录的用户的名字，到blog_detail.html
1.  对于博客列表页，要显示登录用户的信息。登录用户的信息，再检测用户是否登录的接口中，就已经拿到了
    只需要把拿到的用户信息，显示到界面上即可。（只是微调了前端代码，不涉及后端代码的修改）
    blog_detail.html 里面，增加了getAuthorInfo() 这个function

2.  对于博客详情页，要显示文章的作者信息，就需要提供一个新的API，让客户端传一个 blogId，在服务器这边，查询当前的用户信息
    查到之后，返回给页面。AuthorServlet class in JAVA： 后端服务器代码
    
总结： 页面和服务器的多次交互。
      博客列表页，涉及到两次交互：
      1. 从服务器拿到博客列表数据
      2. 从服务器拿到当前的登录用户信息

      博客详情页，涉及到三次交互
      1. 从服务器拿到博客的详细内容
      2. 从服务器拿到了当前登录用户的信息
      3. 从服务器拿到了当前blog的作者信息

实现“logout”的功能，退出登录状态
在导航栏里面，加一个“logout”按钮，当用户点击“logout”时，就会在服务器上取消登录状态，并且能够跳转到登录页面。
注销按钮里面，这里是空的”#“。
点击之后，就能够给服务器发送一个 HTTP 请求，从而触发注销动作。（这个动作，具体就是把会话中的信息给删除了）

前后端交互接口
请求：
GET /logout

302 是重定向
响应：
HTTP/1.1 302
Location: login.html

URL: https://docs.oracle.com/javaee/7/api/javax/servlet/http/HttpSession.html

- removeAttribute
void removeAttribute(String name)
Removes the object bound with the specified name from this session. If the session does not have an object bound with the specified name, this method does nothing.
After this method executes, and if the object implements HttpSessionBindingListener, the container calls HttpSessionBindingListener.valueUnbound. The container then notifies any HttpSessionAttributeListeners in the web application.

Parameters:
name - the name of the object to remove from this session
Throws:
IllegalStateException - if this method is called on an invalidated session

如何定义登录？
用户有一个session，同时session有一个user属性
两者同时具备，才叫登录状态
注销，只要破坏掉上面任意一个条件就行，在这个LogoutServlet 代码中，用到removeAttribute(“user”)，将user属性从session中移除。
Servlet里面没有提供删除session的API
在LoginServlet.java中的doGet()方法，检测登录状态的代码里面，同时需要验证session 对象 和user 属性 是否都存在，
才能返回登录的用户对象。

客户端代码的修改：
就把 博客列表页，博客详情页，博客编辑页，这里的导航栏中的注销按钮的 href 属性
都做出修改，改成 “logout”（相对路径） 这个路径。注意不是“/logout”（绝对路径）

1) 博客列表页
2) 博客详情页
3) 登录功能
4) 检测用户登录状态
5) 显示用户信息
6) 注销

上面代码的核心逻辑都是一样的
1) 约定前后端交互接口
2) 实现服务器代码
   a) 写 controller，写各种servlet来实现API
   b) 写 model 层，通过JDBC 来操作数据库(DBUtil，Dao等等)
3) 实现客户端代码
   ajax/ form /a 标签跳转，来触发 HTTP 请求的
   
## 发布博客功能(blog_edit.html)
在博客编辑页，写好博客标题和正文后，点击“发布文章”按钮，就会把博客数据提交给服务器，由服务器保存到数据库里。
后续的博客列表页(blog_list.html)和博客详情页(blog_detail.html)也能够显示这个博客信息

约定前后端交互接口
请求request：带有博客内容，需要有 body 部分，所以需要用到 POST
POST /blog                                          // 在 BlogServlet 里面，已经有 “/blog” 的请求路径，以及doGet的方法来获取blog list，
                                                    // 那么可以增加doPost的方法，用来递交博客
Content-Type: application/x-www-form-urlencoding

title=thisistitle&content=thisisblog...             // 此处的内容都是需要 urlencoded 的，浏览器会自动进行编码

响应response：
HTTP/1.1 302
Location: blog_list.html                            // 每次提交之后，就会回到博客列表页，新增的博客会显示在最上面
                                                    // (按照时间排序order by)

写完后端代码(goPost() 在BlogServlet.java), 就需要整一个 form 表单，把这里的内容套上（输入框，提交按钮）
1. 在blog_edit.html增加form表单（line34）
2. input 标题的输入框，增加了 name 属性，
3. 发布按钮，改成了 input 标签, 并修改一下发布按钮的样式CSS
4. 增加了textarea,并增加了隐藏的style(display:none)，为了后续的 form 提交
5. 在 editor 里面，设置了一个标志位，使当前的输入框的内容 能够自动保存到textarea中。saveHTMLToTextarea: true
6. 在blog_edit.css里面，button的css选择器，改成 id 为submit的css 选择器

## 删除博客系统(delete a blog)
只有写博客的作者才有权限删除博客，不能删除别人的博客
只有管理员才有权限，删除用户的博客，但此处不考虑管理员

1. 在用户界面上的处理
在博客详情页这里，就去进行判定，看当前这个博客的作者，是否就是登录的用户
如果是，就在导航栏里，显示一个删除按钮
如果不是，就在导航里里，不显示删除按钮

2. 服务器的处理
用户点击删除按钮，触发一个 HTTP 请求，HTTP请求，就会让服务器删除指定的博客
服务器收到请求后，就会把这个博客从数据库里，给删除掉
   
In blog_detail.html,
getAuthorInfo()
getUserInfo("blog_detail.html")
这两个函数，调用之后，就给服务器发送了两个 HTTP 请求
但是，两个请求对应的响应，谁先回来，是不确定的。
虽然函数有先后，但是两个 ajax 请求是并发的关系。
第一个ajax 发出去以后，不等响应回来，第二个 ajax 已经发出去了。是个异步执行的过程
改动如下：
在getUserInfo()里面，回调getAuthorInfo(),从而让并发改成前后串行。先得到用户信息，再拿到作者信息。
其次，在getAuthorInfo() function里面，进一步判定，文章作者和登录用户的名字是否一样。
如果一样的话，那么准备好在这个navDiv里，构造a 标签，并添加到里面。
重点是 ajax 是并发执行的。

JS ES6 Promise 解决了 ajax 不停套娃(一个ajax里面，调用另外一个ajax)的情况。
Promise 会解决这个问题，用语法糖
ES7 引用了 async 和 await，能够更加优雅得解决上述问题
Syntactic sugar

# 服务器代码处理删除的请求
请求：
GET /blogDelete?blogId=7

预期的响应
直接跳转到 博客列表页

这里用到的技术：MySQL, JDBC, servlet, HTTP, Ajax, JavaScript, HTML, CSS

Linux, Linux command lines + deploy the blog system in cloud server
    