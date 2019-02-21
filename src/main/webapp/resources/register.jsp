<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>register</title>
    <meta name="viewport" content="height=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/welcome.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <script type="text/javascript">
        $(function () {
            $("#btn-order").click(function () {
                var uname = $(".login-name").val().trim();
                var upwd = $(".login-word").val().trim();
                $.ajax({
                    url:"http://localhost:8080/sports/user/loginInfo",
                    contentType:"application/json;charset=UTF-8",
                    data:'{"user_name":"'+uname+'","user_pwd":"'+upwd+'"}',
                    dataType:"json",
                    type:"post",
                    success:function (data) {
                        if(data.status == 0){
                            alert("注册成功！将返回到登陆页面");
                            var username = uname;
                            var password = upwd;
                            console.log("============"+username);
                            setCookie("username", username, 2);
                            setCookie("password", password, 2);
                            $("#back").click();
                        }else if(data.status == 1){ //用户已存在
                            $("#warning_1 span").html(data.msg);
                            $("#warning_1").show();//显示提示信息
                        }
                    }
                });

            });
            $("#back").click(function () {
                location.href='login.jsp';
            });
            function setCookie(cname, cvalue, exdays) {
                var d = new Date();
                d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
                var expires = "expires=" + d.toGMTString();
                document.cookie = cname + "=" + cvalue + "; " + expires;
            }
        });
    </script>

</head>
<body>
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
        <form action="/sports/user/loginInfo" method="post">
            <div class="user-login">
                姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：<input type="text" name="user.name" class="login-name" style="width: 40%"/>
                <div class='warning' id='warning_1' style="display: none; color: #FF0000"><span>该用户名不可用</span></div>
            </div>
            <div class="user-word">
                密 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：<input type="password" id="regist_password" name="user.password" class="login-word" style="width: 40%" />
                <div class='warning' id='warning_2' style="display: none; color: #FF0000;"><span>密码长度过短</span></div>
            </div>
            <div class="user-word">
                确认密码：<input type="password" id="final_password" name="密码" class="check-word"  style="width: 40%"/>
                <div class='warning' id='warning_3' style="display: none; color:#FF0000"><span>密码输入不一致</span></div>
            </div>
            <div class="order-btn">
                <input type="button" id="btn-order" class="btn btn-success" style="width: 80%" value="注册">
                <input type="button" name="" id="back" value=' 返 回 ' style="display: none" tabindex='10'/>
            </div>
        </form>
        <div class="optionr-btn">
            <a href="login.jsp">已有账户</a>
        </div>
        <script type="text/javascript">
            function get(e){
                return document.getElementById(e);
            }
            get('final_password').onblur=function(){
                var npassword=get('regist_password').value;
                var fpassword=get('final_password').value;
                if(npassword != fpassword){
                    get('warning_3').style.display='block';
                }
            }
            get('regist_password').onblur=function(){
                var npassword=get('regist_password').value.length;
                if(npassword<6&&npassword>0){
                    get('warning_2').style.display='block';
                }
            }
            get('regist_password').onfocus=function(){
                get('warning_2').style.display='none';
            }
            get('final_password').onfocus=function(){
                get('warning_3').style.display='none';
            }
        </script>
    </div>
</body>
</html>
