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
    
6.  POST 请求 body 格式
    1. x-www-form-urlencoded (常见): getParameter
       如何在前端构造一个这样的格式请求？
       1) form 表单
          - 输入127.0.0.1:8080/hello102/test.html
          - 页面会跳转
           
          POST http://127.0.0.1:8080/hello102/postGetParameter HTTP/1.1
          Host: 127.0.0.1:8080
          Connection: keep-alive
          Content-Length: 22
          Cache-Control: max-age=0
          sec-ch-ua: "Chromium";v="106", "Google Chrome";v="106", "Not;A=Brand";v="99"
          sec-ch-ua-mobile: ?0
          sec-ch-ua-platform: "Windows"
          Upgrade-Insecure-Requests: 1
          Origin: http://127.0.0.1:8080
          Content-Type: application/x-www-form-urlencoded
          User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36
          Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
          Sec-Fetch-Site: same-origin
          Sec-Fetch-Mode: navigate
          Sec-Fetch-User: ?1
          Sec-Fetch-Dest: document
          Referer: http://127.0.0.1:8080/hello102/test.html
          Accept-Encoding: gzip, deflate, br
          Accept-Language: en-US,en;q=0.9

          userId=dfd&classId=111
          
       2) postman
        
    2. form-data
    3. JSON (常见)
       因为JSON里面的字段是能够嵌套的，所以手动解析的话，不容易。但是可以借助第三方库，来直接处理 JSON 格式数据。
       课堂上，主要用的库是Jackson（Spring官方推荐的）
       JSON 的请求方式。
       {
        userId:dfd,
        classId: 111
        }
       通过Maven, 把 Jackson 引入
       button.onclick = function() {
            $.ajax({
                type: 'post',
                url: 'postGetParameter2',
                // 对于 POST 这样的请求，ajax 就允许使用 data 属性来构造请求的 body 部分
                data: JSON.stringify({   
                    // 此处要构造的内容，是个JS对象，需要把JS对象，通过JSON.stringfy,转成一个字符串。该字符串的格式就是JSON的格式        
                    userId: userIdInput.value,
                    classId: classIdInput.value
                }),
                success: function(body) {
                console.log(body);
                }
            });
        }
       使用 ajax 的方式来提交数据，这个操作默认不会产生页面跳转。form会跳转。