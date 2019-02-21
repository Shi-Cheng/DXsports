<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>welcome</title>
    <meta name="viewport" content="height=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/welcome.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <style type="text/css">
        body {
            background: #eee;
            background-image: url(${pageContext.request.contextPath}/resources/images/admin_bg.png);
            background-position: initial;
            background-repeat: initial;
            background-position: initial;
            background-repeat: initial;
            margin: 0px;
            padding: 0px;
        }
    </style>
    <script>
        function checkAdminCookie(){
            var name = getCookie("adminName");
            var word = getCookie("adminPWD");
            $("#login_name").val(name);
            $("#login_password").val(word);
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
        $(function() {
            $("#btn-login").click(function () {
                var username = $(".login-name").val().trim();
                var password = $(".login-word").val().trim();
                if(username == "" || password == ""){
                    alert("用户名或密码不能为空");
                }else{
                    $.ajax({
                        url: "http://localhost:8080/sports/admin/checkLogin",
                        contentType: "application/json;charset=UTF-8",
                        data: '{"username":"' + username + '","password":"' + password + '"}',
                        dataType: "json",
                        type: "post",
                        success: function (data) {
                            if (data.status == 0) { //用户存在
                                setCookie("adminName", username, 2);
                                setCookie("adminPWD", password, 2);
                                location.href='adminquery.jsp';
                            } else if (data.status == 1) { //用户名不存在  此时便于演示设置为0， 实际为data.status == 1
                                $("#count_msg").html(data.msg);
                            } else if (data.status == 2) { //密码不正确
                                $("#password_msg").html(data.msg);
                            }
                        }
                    });
                }
            })
            function setCookie(adKey, adValue, adDays) {
                var d = new Date();
                d.setTime(d.getTime() + (adDays * 24 * 60 * 60 * 1000));
                var expires = "expires=" + d.toGMTString();
                document.cookie = adKey + "=" + adValue + "; " + expires;
            }
        })
    </script>
</head>
<body onload="checkAdminCookie()">
<form action="/sports/admin/checkLogin">
    <div class="active-img">
        <img src="${pageContext.request.contextPath}/resources/images/2.png"/>
        <div class="active-title">
            <label class="la1">羽毛球预约后台管理系统</label><br>
        </div>
        <br>
        <div class="user-login">
            管理员：<input type="text" id="login_name" name="username" class="login-name"  style="width: 40%" />
            <span id="count_msg" style="color:red"></span>
        </div>
        <div class="user-word">
            密-码：<input type="password" id="login_password" name="password" class="login-word" style="width: 40%" />
            <span id="password_msg" style="color: red" ></span>
        </div>
        <div class="order-btn">
            <input type="button" id="btn-login" class="btn btn-success" style="width: 80%" value="登陆">
        </div>
        <div class="optionr-btn">
            <a href="index.jsp">返回首页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="#">忘记密码</a>
        </div>
    </div>
    <script type="text/javascript">
        function get(e){
            return document.getElementById(e);
        }
        get('login_name').onblur=function(){
            get('count_msg').style.display='none';
        }
        get('login_password').onblur=function(){
            get('password_msg').style.display='none';
        }
    </script>
</form>
</body>
</html>