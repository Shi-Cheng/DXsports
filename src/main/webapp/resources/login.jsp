<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>login</title>
    <meta name="viewport" content="height=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/welcome.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <script>
        function checkCookie(){
            var user = getCookie("username");
            var password = getCookie("password");
            $("#login_name").val(user);
            $("#login_password").val(password);
        }
        function getCookie(cname){
            var name = cname + "=";
            var ca = document.cookie.split(';');
            for(var i=0; i<ca.length; i++) {
                var c = ca[i].trim();
                if (c.indexOf(name)==0) { return c.substring(name.length,c.length); }
            }
            return "";
        }
    </script>
    <script type="text/javascript">
        $(function () {
            $("#btn-order").click(function () {
                $("#count_msg").html(" ");
                $("#password_msg").html(" ");
                var uname = $(".login-name").val().trim();
                var upwd = $(".login-word").val().trim();
                var ok = true;
                if (uname == "") {
                    $("#count_msg").html("用户名不能为空");
                    ok = false;
                }
                if (upwd == "") {
                    $("#password_msg").html("密码不能为空");
                    ok = false;
                }
                if (ok) {
                    $.ajax({
                        url: "http://localhost:8080/sports/user/checkLogin",
                        contentType: "application/json;charset=UTF-8",
                        data: '{"user_name":"' + uname + '","user_pwd":"' + upwd + '"}',
                        dataType: "json",
                        type: "post",
                        success: function (data) {
                            if (data.status == 0) { //用户存在
                                location.href='index.jsp';
                            } else if (data.status == 1) { //用户名不存在
                                $("#count_msg").html(data.msg);
                            } else if (data.status == 2) { //密码不正确
                                $("#password_msg").html(data.msg);
                            }
                        }
                    });
                }
                $("#back").click(function () {
                    location.href = 'index.jsp';
                });
            });
        })
    </script>
</head>
<body onload="checkCookie()">
    <div class="bar-top">
        <ul>
            <li><a href="index.jsp">首页</a></li>
            <li><a href="activeorder.jsp">预约</a></li>
            <li><a href="activityOptions.jsp">我的预约</a></li>
            <li><a href="login.jsp">登陆</a></li>
            <li><a href="adminLogin.jsp">管理员</a></li>
        </ul>
    </div>
    <div class="active-img">
        <img src="${pageContext.request.contextPath}/resources/images/2.png"/>
        <div class="active-title">
            <label class="la1">欢迎使用羽毛球预约系统</label><br>
        </div>
        <br>
        <form action="/sports/user/checkLogin" method="post">
            <div class="user-login">
                姓 - 名：<input type="text" id="login_name" name="user.name" class="login-name" style="width: 40%" /><br/>
                <span id="count_msg" style="color:red"></span>
            </div>
            <div class="user-word">
                密 - 码：<input type="password" id="login_password" name="user.password" class="login-word" style="width: 40%" /><br/>
                <span id="password_msg" style="color: red" ></span>
            </div>
            <div class="order-btn">
                <input type="button" id="btn-order" class="btn btn-success" style="width: 80%" value="登陆">
                <input type="button" name="" id="back" value=' 返 回 ' style="display: none" tabindex='10'/>
            </div>
        </form>
        <div class="optionr-btn">
            <a href="register.jsp">注册</a>
        </div>
    </div>
</body>
</html>
