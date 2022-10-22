1.  configuration files:
    URL reference: https://cloud.google.com/appengine/docs/legacy/standard/java/configuration-files

2.  web.xml
    reference url: https://cloud.google.com/appengine/docs/legacy/standard/java/config/webxml#:~:text=and%20URL%20paths-,web.,method%20for%20HTTP%20GET%20requests.

    web. xml defines mappings between URL paths and the servlets that handle requests with those paths. The web server uses this configuration to identify the servlet to handle a given request and call the class method that corresponds to the request method. For example: the doGet() method for HTTP GET requests.

3.  内网IP
    192.168*
    10.*
    172.16.* - 172.31.*
    192.168.*

    983 879 008

4.  用Tomcat部署静态页面
    1. 安装Tomcat
    2. 在tomcat文件夹里面，找到apache-tomcat-8.5.83\bin 里面的startup.bat(for windows),拖到 cmd 里面。
    3. 找到apache-tomcat-8.5.83\webapps文件夹，在该目录下，新建一个项目文件夹，来存放项目相关的文件并保存。
    4. 在浏览器里输入项目相关的url链接，就可以在本地机器上面浏览了。例如：http://127.0.0.1:8080/blog102/blog_edit.html

5.  When do browsers send GET request?
    - 直接在地址栏里，输入URL
    - 通过 a 标签跳转
    - 通过img/link/script标签，也会触发 get 请求
    - 通过 form 表单，method 指定为 GET
    - 通过Ajax，type 指定为 GET

    啥时候浏览器发的是 POST 请求？
    - 通过 form 表单，method 指定为 POST
    - 通过Ajax，type 指定为 POST