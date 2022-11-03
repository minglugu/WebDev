// 这个文件里面放一些 页面的公共代码

// 此处加上逻辑，通过 GET /login 这个接口来获取当前的登录状态
// 这个function里面，增加的一个逻辑：是在页面加载的时候，获取到用户的身份信息。
// 判定是否登录。
function getUserInfo(pageName) {
    $.ajax({
        type: "get",
        url: "login",
        success: function(body) {
            // 判定此处的 body，是否为一个有效的 user 对象(userId 是否 非 0)
            if (body.userId && body.userId > 0) {
                // 登录成功，不做处理！
                console.log("当前用户登录成功！用户名：" + body.userName);
                
                // 根据当前用户登录的情况，把当前用户名，设置到界面上
                // 然而此处，就不该修改界面的内容了
                // 博客详情页，就不用修改，而博客列表页，需要保留login username
                // 博客详情页，通过其他的API来进行设定页面中的用户信息
                if (pageName == 'blog_list.html') {
                    // important: 需要写成userName，而不是userName
                    changeUserName(body.userName);
                }                 
            } else {
                // 前端页面跳转的方式。此处未登录或登录失败的话，页面跳转到登录页
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

getUserInfo();

// 根据博客的作者，来修改博客列表页的头像
function changeUserName(username) {
    // 拿到博客头像里面的 card>h3 标签
    let h3 = document.querySelector('.card>h3');
    // 根据从服务器获取的当前用户名，来修改博客列表页的名字。
    // 这里的username是形参，传参到 h3 里的
    h3.innerHTML = username;
}

