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
        function userActivityInfo() {
            var activityPlace = getCookie("useractivityPlace");
            var activityDate = getCookie("useractivityDate");
            var activityStatus = getCookie("useractivityStatus");

            $("#activity_place").text(activityPlace);
            $("#activity_date").text(activityDate);
            if(activityStatus != 0){
                $("#status").text("活动进行中");
            }else {
                $("#status").text("活动以取消");
                $("#option1").checked = true;
            }
        }
        function updateActivity(){
            var activityId = getCookie("useractivityId");
            if($("input:radio[name='checks']:checked")){
                var activityStatus = '0';
                setCookie("useractivityStatus",activityStatus, 2);
                $("#status").text("活动以取消");
            }
            $.ajax({
                url: "http://localhost:8080/sports/user/activityCancel",
                contentType: "application/json;charset=UTF-8",
                //修改状态
                data: '{"activity_id":"' + activityId +'","reserve_status":"' + activityStatus +'"}',
                dataType: "json",
                type: "post",
                success: function (data) {
                    //前台获取用户预约的状态，进行动态显示
                    if(data.reserve_status == 1) {
                        $("#status").text("活动以取消");
                    }
                }
            });
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
        function setCookie(activityIdKey, activityIdValue, exdays) {
            var date = new Date();
            date.setTime(date.getTime() + (exdays * 24 * 60 * 60 * 1000));
            var expires = "expires=" + date.toGMTString();
            document.cookie = activityIdKey + "=" + activityIdValue + "; " + expires;
        }
    </script>
</head>
<body onload="userActivityInfo()">
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
            <label class="la1">活动地点：</label><span id="activity_place" ></span><br/>
            <label class="la2">活动日期：</label><span id="activity_date" ></span><br/>
            活动状态：<span id="status" ></span>
        </div>
        <br>
        <div class="options">
            <label >本次活动：</label>
            <input type="radio" name="checks" id="option1" value="0"> 放弃本次活动&nbsp;&nbsp;
        </div>
        <div class="order-btn">
            <input onclick="updateActivity()" type="button" id="btn-order" class="btn btn-success" style="width: 100%; margin-top: 20%" value="确定修改">
        </div>
    </div>
</form>
</body>
</html>