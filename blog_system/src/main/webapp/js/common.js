// 这个文件里面放一些 页面的公共代码

// 此处加上逻辑，通过 GET /login 这个接口来获取当前的登录状态
function getUserInfo() {
    $.ajax({
        type: "get",
        url: "login",
        success: function(body) {
            // 判定此处的 body，是否为一个有效的 user 对象(userId 是否 非 0)
            if (body.userId && body.userId > 0) {
                // 登录成功，不做处理！
                console.log("当前用户登录成功！用户名：" + body.username);
            } else {
                // 前端页面跳转的方式。此处未登录的话，页面跳转到登录页
                alert("当前尚未登录，请登录后再访问博客列表！");
                location.assign("blog_login.html");
            }
        },
        error: function() {
            alert("当前尚未登录，请登录后再访问博客列表！");
            location.assign("blog_login.html");
        }
    });
}

// 该函数的调用
getUserInfo(); 