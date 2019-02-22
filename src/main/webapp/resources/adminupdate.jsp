<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>AdminUpdate</title>
    <meta name="viewport" content="height=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/activeorder.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/adminstyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>

    <style type="text/css">
        .options{
            margin-left: 0px;
        }
    </style>
    <script type="text/javascript">
        function activityUpdate(){
            var activityPlace = getCookie("activityPlace");
            var activityDate = getCookie("activityDate");
            var activityStatus = getCookie("activityStatus");
            $("#activity_place").text(activityPlace);
            $("#activity_date").text(activityDate);
            if(activityStatus != 0){
                $("#status").text("活动进行中");
            }else {
                $("#status").text("活动以取消");
                $("#option1").checked = true;
            }
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
        function updateActivity(){
            var activityId = getCookie("activityID");
            if(document.getElementById("option1").checked){
                var activityStatus = '0';
                $.ajax({
                    url: "http://localhost:8080/sports/admin/activityUpdate",
                    contentType: "application/json;charset=UTF-8",
                    data: '{"activityId":"' + activityId + '","activity_status":"' + activityStatus +'"}',
                    dataType: "json",
                    type: "post",
                    success: function (data) {
                        if(data.activity_status == 0){
                            $("#status").text("活动以取消");
                            setCookie("activityStatus", data.activity_status,2);
                            $("#showInfo").html('<p style="color: #3D9140;"> 修改成功</p>')
                        }
                    }
                });
            }
        }
        function setCookie(activityIdKey, activityIdValue, exdays) {
            var date = new Date();
            date.setTime(date.getTime() + (exdays * 24 * 60 * 60 * 1000));
            var expires = "expires=" + date.toGMTString();
            document.cookie = activityIdKey + "=" + activityIdValue + "; " + expires;
        }
    </script>
</head>
<body onload="activityUpdate()">
<form>
    <div class="bar-top">
        <ul>
            <li><a href="#">首页</a></li>
            <li><a href="activity.jsp">活动发布</a></li>
            <li><a href="adminupdate.jsp">最新活动</a></li>
            <li><a href="adminquery.jsp">活动查询</a></li>
        </ul>
    </div>
    <div class="active-img">
        <img src="${pageContext.request.contextPath}/resources/images/1.png" />
        <div class="active-title">
            <label class="la1">活动地点：</label><span id="activity_place" ></span><br/>
            <label class="la2">活动日期：</label><span id="activity_date" ></span><br/>
            活动状态：<span id="status" ></span>
        </div>
        <br>
        <div class="options">
            <label >本次活动：</label>
            <input type="radio" name="checks" id="option1" value="已取消"> 取消本次活动&nbsp;&nbsp;
        </div>

        <div class="order-btn">
            <input type="button" id="btn-order" onclick="updateActivity()" class="btn btn-success" style="width: 100%; margin-top: 20%" value="确定修改">
        </div>
        <div id="showInfo" style="text-align: center; margin-top:5px;">
        </div>
    </div>
</form>
</body>
</html>
