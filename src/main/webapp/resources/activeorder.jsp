<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>activeorder</title>
    <meta name="viewport" content="height=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/activeorder.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">

    <script type="text/javascript">
        /**
         * 自动加载到活动信息
         */
        function activityNotice() {
            var cookieName = getCookie("username");
            if(cookieName != null){
                var date = new Date();
                var month = date.getMonth() + 1;
                var strDate = date.getDate();
                if (month >= 1 && month <= 9) {
                    month = "0" + month;
                }
                if (strDate >= 0 && strDate <= 9) {
                    strDate = "0" + strDate;
                }
                var currentdate = date.getFullYear() + month + strDate;
                $.ajax({
                    url: "http://localhost:8080/sports/admin/activityDisplay",
                    contentType: "application/json;charset=UTF-8",
                    data: '{"currentdate":"' + currentdate + '"}',
                    dataType: "json",
                    type: "post",
                    success: function (data) {
                        $("#activity_place").text(data.activity_place);
                        $("#activity_date").text(data.activity_date);
                        $("#activity_number").text(data.activity_id);
                        $("#activityStatus").text(data.activity_status);
                        if(data.activity_status != 0){
                            $("#status").text("活动进行中");
                        }else {
                            $("#status").text("活动以取消");
                        }
                    }
                });
            }else {
                window.location = "login.jsp";
            }
        }
        /**
         * 用户预约活动
         */
        function order() {
            var activityId = $("#activity_number").text();
            var activityPlace = $("#activity_place").text();
            var activityDate = $("#activity_date").text();
            var activityStatus = $("#activityStatus").text();
            setCookie("useractivityPlace", activityPlace, 2);
            setCookie("useractivityDate", activityDate, 2);
            setCookie("useractivityStatus",activityStatus, 2);
            setCookie("useractivityId",activityId , 2);
            $.ajax({
                url: "http://localhost:8080/sports/user/activityOrder",
                contentType: "application/json;charset=UTF-8",
                data: '{"activity_id":"' + activityId + '"}',
                dataType: "json",
                type: "post",
                success: function (data) {
                    //前台获取用户预约的状态，进行动态显示
                    $("#orderInfo").html('<p style="margin-top:10%;color: #32CD32;"> 预约成功,在我的预约中查看</p>');
                }
            });
        }
        function setCookie(activityIdKey, activityIdValue, exdays) {
            var date = new Date();
            date.setTime(date.getTime() + (exdays * 24 * 60 * 60 * 1000));
            var expires = "expires=" + date.toGMTString();
            document.cookie = activityIdKey + "=" + activityIdValue + "; " + expires;
        }
        function getCookie(activityIdKey){
            var activityKey = activityIdKey + "=";
            var activityValue = document.cookie.split(';');
            for(var i=0; i<activityValue.length; i++) {
                var activity = activityValue[i].trim();
                if (activity.indexOf(activityKey)==0) { return activity.substring(activityKey.length,activity.length); }
            }
            return "";
        }
    </script>
</head>
<body onload="activityNotice()">
    <form action="/sports/user/activityOrder" method="post">
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
            <label class="la1">活动地点：</label><span id="activity_place" ></span><br/>
            <label class="la2">活动日期：</label><span id="activity_date" ></span><br/>
            <span id="activity_number" style="display: none"></span>
            <span id="activityId" style="display: none"></span>
            <span id="activityStatus" style="display: none"></span>
            活动状态：<span id="status" ></span>
        </div>
        <div class="active-content">
            羽毛球运动能够完美地诠释本活动对象的不凡品质。参赛者在挥杆比赛激烈角逐中，深化友谊，
            促进交流，览尽XX 山迷人风光。凡是感兴趣的活动参与者特别是意向大客户都可以报名参加比赛
        </div>
        <div class="order-btn">
            <input onclick="order()" type="button" id="btn-order" class="btn btn-success" style="width: 100%" value="一键预约">
        </div>
        <div id="orderInfo" style="text-align: center;color: #3D9140; margin-top: 10px;">
        </div>
    </div>
</form>
</body>
</html>
