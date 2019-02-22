<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>admin login</title>
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
    <script type="text/javascript">
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
        function adminLogin(){
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
                        } else{
                            $("#loginInfo").html(data.msg);
                        }
                    }
                });
            }
        }
        function setCookie(adKey, adValue, adDays) {
            var d = new Date();
            d.setTime(d.getTime() + (adDays * 24 * 60 * 60 * 1000));
            var expires = "expires=" + d.toGMTString();
            document.cookie = adKey + "=" + adValue + "; " + expires;
        }
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
        </div>
        <div class="user-word">
            密-码：<input type="password" id="login_password" name="password" class="login-word" style="width: 40%" />
        </div>
        <div id="loginInfo" style="text-align: center; margin-bottom:5px; color: red">
        </div>
        <div class="order-btn">
            <input onclick="adminLogin()" type="button" id="btn-login" class="btn btn-success" style="width: 80%" value="登陆">
        </div>
        <div class="optionr-btn">
            <a href="index.jsp">返回首页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </div>
    </div>
</form>
</body>
</html>