<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>adminQuery</title>
    <meta name="viewport" content="height=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/welcome.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/adminstyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>

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
        .query-notice{
            margin-left: 10%;
        }
        .query-content{
            width: 50%;
            height: 40%;
            border: 1px #2aabd2;
            margin-top: 10%;
        }
    </style>
    <script type="text/javascript">
        $(function() {
            $("#date-value").blur(function () {
                $("#count_msg").hide();
            });
            $("#btn-order").click(function () {
                var activityDate =  $("#date-value").val().replace(/-/g,"");
                var ok = true;
                if (activityDate == "") {
                    $("#count_msg").html("查询日期不能为空");
                    ok = false;
                }
                if(ok){
                    $.ajax({
                        url: "http://localhost:8080/sports/admin/activityQuery",
                        contentType: "application/json;charset=UTF-8",
                        data: '{"activityDate":"' + activityDate + '"}',
                        dataType: "json",
                        type: "post",
                        success: function (data) {

                            $("#query-content").html('<p style="margin-top:10%"> 查询结果如下</p>'+'<p style="margin-top: 5%; color: #32CD32;"> 活动地点：'+data.activity_place+'</p>'+'<p style="color: #32CD32;"> 活动日期：'+data.activity_date+'</p>');
                        }
                    });
                }
            })
        })
    </script>
</head>
<body>
<form action="/sports/admin/activityQuery">
    <div class="bar-top">
        <ul>
            <li><a href="#">首页</a></li>
            <li><a href="activity.jsp">活动发布</a></li>
            <li><a href="adminupdate.jsp">最新活动</a></li>
            <li><a href="adminquery.jsp">活动查询</a></li>
        </ul>
    </div>
    <div class="active-img">

        <img src="${pageContext.request.contextPath}/resources/images/2.png"/>
        <div class="active-title">
            <label class="la1">欢迎使用羽毛球预约系统</label><br>
        </div>
        <br>
        <span class="query-notice" style="margin-left: 35%">请输入查询时间 </span><br/><br/>
        <div class="user-login">
            日期：<input type="date" id="date-value" style="width: 40%" />
            <span id="count_msg" style="color:red"></span>
        </div>
        <div class="order-btn">
            <input type="button" id="btn-order" class="btn btn-success" style="width: 80%" value="查询">
        </div>
        <div id="query-content" style="text-align: center; margin-top: 10px;">
        </div>

    </div>
</form>
</body>
</html>
