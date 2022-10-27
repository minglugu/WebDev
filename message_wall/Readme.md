1. 对象 和 JSON 字符串之间的转换

Java
objectMapper.readValue 把 JSON 字符串转换成 对象，写在 doPost() 方法里
objectMapper.writeValueAsString 把对象 转换成 JSON 字符串，写在 doGet() 方法里

JS
- JSON.parse 把 JSON 字符串 转换成 对象
  写在function getMessages() {}

- JSON.stringfy 把 对象 转换成 JSON 字符串
  写在 submit.onclick = function(){} 里面

2. 连接数据库，让message 永久化存储(persistent store)
   或者使用 file 来写和读取数据
   URL: https://docs.oracle.com/cd/E13222_01/wls/docs90/config_wls/store.html
   Comparing File Stores and JDBC Stores
   
   Persistence data: The data which is available after fully closing the application. 
   This type of data must be save into shared preference or database or internal or external memory. 
   Non- persistence data: The data which is not available after fully closing the application.
   
3. message_wall的总结
   开发一个表白墙(简单的网站)步骤如下：
   1. 约定前后端交互的接口（request，response）
      三种常用的方式。见课件里的画图板里的内容
   2. 开发服务器代码 (in MessageServlet.java)
      a. 先编写 Servlet 能够处理前端发来的请求                          //  Controller (控制器，用来处理请求之后的关键逻辑)
         doGet(HttpServletRequest req, HttpServletResponse resp)  //   Model (操作数据存取的逻辑)
         doPost(HttpServletRequest req, HttpServletResponse resp)
      b. 编写数据库代码                                             
         save() and load()
   3. 开发客户端代码                                                 //  View (给用户展示的界面)
      1) 基于 ajax 能够构造请求以及解析响应
         $.ajax() in messageWall.html
      2) 能够响应用户的操作(点击按钮之后，触发给服务器发送请求的行为)
   
MVC: 
Model (操作数据存取的逻辑)
View (给用户展示的界面)
Controller (控制器，用来处理请求之后的关键逻辑)

4. Cookie(客户端的机制) 和 Session(会话，服务器端的机制)
   可以基于 Servlet 这个 API 来完成会话管理的操作


   A) HttpServletRequest 的相关方法(HttpSession getSession() & Cookie[] getCookies())
   - getSession() 具体过程：
   1. 创建会话
      获取到请求中 cookie 里面的 sessionId 字段 （相当于会话的身份标识）
      判定这个 sessionId 是否在当前服务器上存在
      不存在的情况下，则进入创建会话逻辑
      
      a. 创建会话，会创建一个 HttpSession 对象，并且生成一个 sessionId(16进制的很长数字，而且是唯一的)
      b. 把sessionId 作为 key，把HttpSession 对象，作为 value，将这个键值对，保存到 服务器内存的一个 “哈希表” 类似的结构中。
         实际不一定是“哈希表”，但一定是类似的，能够存储键值对的结构。并且数据是在内存中的。（服务器重启，数据/会话就没有了）
      c. 服务器就会返回HTTP响应，把sessionId 通过 Set-Cookie 字段返回给浏览器。浏览器就可以保存这个 sessionId 到 Cookie 中了
   
   2. 获取会话
      a. 先获取到请求中的 cookie 里面的 sessionId 字段（也就是挥发的身份标识）
      b. 判定这个 sessionId 是否在当前服务器上存在 （也就是在这个 “哈希表” 中是否存在）
      
   -  HttpSession 对象
      这个对象 也是“键值对”结构，里面包含了若干个键值对
      允许程序员往 HttpSession 对象中，存储任意的键值对数据（key 必须是 String（sessionId），value 是一个Object（也包含键值对））
      里面的key和value都是程序员自定义的
      包含了两级键值对。
      HttpSession里面的每个key-value 称为属性 attribute
      getAttribute(): 取 key-value pair
      setAttribute(): 存 key-value pair

   -  Cookie[] getCookies() 帮助程序员，获取到请求中的Cookie 数据
      返回值是 Cookie 类型的数组
      每个元素是一个 Cookie 对象
      每个 Cookie 对象又包含了两个属性，name 和 value（也是键值对）

      HTTP请求中的 cookie 字段就是按照 key-value pair 来组织的
      这里的键值对，格式是使用 ";" 来分割多个键值对，使用 "=" 来分割键和值的
      这些键值对都会在请求中，通过 cookie 字段传给服务器。
      服务器收到请求之后，就会进行解析，解析成上述看到的 Cookie[] 这样的形式
      每个cookie 对象，就会有这几个方法 String getName() 和 String getValue() void setValue(String newValue)

      Cookie 是可以保存任意自定义的 key-value pair
      一般的 key-value pair，可以通过 getCookies 来获取
      特殊的 key-value pair(sessionId的键值对)，不需要使用 getCookies,直接用getSession，就自动从Cookie中获取 sessionId

   B) HttpServletResponse 类中的方法
      void addCookie(Cookie cookie)
      响应中就可以根据 addCookie 这个方法，来添加一个 Cookie 信息到响应报文中
      这里添加进来的键值对，就会作为 HTTP 响应中的 Set—Cookie 字段来标识

e.g. project, login page 网页登录
   编写案例代码
   1. 约定先后端交互接口(有很多种约定方式)
      两组交互：登录 + 获取主页 
      - 登录的交互
         request:
         POST/login HTTP/1.1
         Content-Type: application/x-www-form-urlencoded
         
         username=zhangsan&password=123

         response:
         HTTP/1.1 302
         Location: index // 跳转的主页
      
      - 获取主页的交互
         request
         GET/index HTTP/1.1
         Content-Type: text/html
         [body 中是一个简单的 html 片段，直接有浏览器进行展示]

   具体 coding 的操作：
   1. 先写个 login 的简单页面，在servlet_project 的 webapp 文档里
   2. LoginServlet: servlet_project/src/main/java/LoginServlet
      doPost()
         创建session
         HttpSession httpSession = req.getSession(true); // 如果会话不存在，创建新的。存在就返回
         httpSession.setAttribute("username", username); // 后续访问的话，就很容易获取到username里面的内容是什么
      
      doGet()  返回主页的逻辑

   
5. 上传文件 Part getPart(String name)
   上传文件的时候，在前端需要用到 form 表单
   form 表单中需要使用特殊的类型 form-data
   此时提交文件的时候，浏览器就会把文件内容以 form-data 的格式构造到 HTTP 请求中
   服务器就可以通过 getPart 来获取

   一个 HTTP 请求，可以一次性地提交多个文件的
   每个文件都称为一个 Part，每个 Part 都有一个 name(身份标识)，服务器代码中，就可以根据 name 找到对应的 Part
   基于这个 Part 就可以进一步地获取到文件信息，并进行下以阶段的操作

   POST 请求里面 有
   Content-Type: multipart/form-data; boundary=----WebKitFormBoundary2MLEKEVhJfikWABE
   boundary 表示body部分，从哪里开始时文件内容，到哪里文件内容结束，随机生成的两串字符串，作为边界。中间内容是二进制，
   所以在文本文档中，显示的是乱码。

6.  实现一个网站，有两种典型的风格：
    1. 服务器的渲染
    2. 客户端的渲染(前后端分离)
    Render 渲染

Web Development: w3schools.com
URL: https://www.w3schools.com/whatis/default.asp

Thymeleaf 是 Java 中，比较流行的模板引擎
前端代码写完了，可以创建一个 假的服务器(mock server),来吐出一些构造好的假数据，来验证前端代码写的是否正确
后端代码写完了，可以构造一些HTTP请求，来验证服务器的数据是否正确(postman)

