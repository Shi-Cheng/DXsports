<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>activityOptions</title>
    <meta name="viewport" content="height=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/activeorder.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
    <style type="text/css">
        .options{
            margin-left: 0px;
        }
    </style>
    <script type="text/javascript">
        $(function() {
            var value = $("#activityDate").text();
            var activityDate = value.split("-").join("");
            $("#btn-order").click(function () {
                var activityStatus =  $("input:radio[name='checks']:checked").val();
                if(activityStatus == "1"){
                    $("#status").text("参加");
                }else {
                    $("#status").text("放弃");
                }
                $.ajax({
                    url: "http://localhost:8080/sports/user/activityOrder",
                    contentType: "application/json;charset=UTF-8",
                    data: '{"activityDate":"' + activityDate + '","activityStatus":"' + activityStatus+ '"}',
                    dataType: "json",
                    type: "post",
                    success: function (data) {
                        //前台获取用户预约的状态，进行动态显示
                        console.log("=============="+data.reserve_status+"================");
                    }
                });
            })
        })
    </script>

</head>
<body>
<form>
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
        <img src="${pageContext.request.contextPath}/resources/images/1.png"/>
        <div class="active-title">
            <label class="la1">活动名称：高尔夫球赛</label><br/>
            <label class="la2">活动日期：<div id="activityDate">2019-01-01</div></label><br/>
            <!--<span id="status" class="status"></span>-->
            <!--当前状态：<div id="status" class="test"></div>-->
            参加状态：<span id="status" >参加</span>

        </div>
        <br>
        <div class="options">
            <label >本次活动：</label>
            <input type="radio" name="checks" id="option1" value="0"> 放弃&nbsp;&nbsp;
            <input type="radio" name="checks" id="option2" value="1" checked="checked"> 参加
        </div>
        <div class="order-btn">
            <input type="button" id="btn-order" class="btn btn-success" style="width: 100%; margin-top: 20%" value="确定修改">
        </div>
    </div>
</form>
</body>
</html>