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
        .query-notice {
            margin-left: 10%;
        }
        .active-title{
            margin-top: 20%;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            $("#btn-order").click(function () {
                var activityDate = $("#activity-date").val().replace(/-/g, "");
                var activityTime = $("#activity-time").find("option:selected").text();
                var activityPlace = $("#activity-place").find("option:selected").text();
                $.ajax({
                    url: "http://localhost:8080/sports/admin/activityCreate",
                    contentType: "application/json;charset=UTF-8",
                    data: '{"activity_date":"' + activityDate + '","activity_place":"' + activityPlace + '","activity_period":"' + activityTime + '"}',
                    dataType: "json",
                    type: "post",
                    success: function (data) {
                        setCookie("activityID", data.activity_id, 2);
                        setCookie("activityPlace", data.activity_place, 2);
                        setCookie("activityDate", data.activity_date, 2);
                        setCookie("activityStatus", data.activity_status, 2);
                        $("#activityAdd").html('<p style="margin-top:10%;color: #32CD32;"> 添加成功,请在最新活动中查看</p>');
                    }
                })

            });
            function setCookie(activityIdKey, activityIdValue, exdays) {
                var date = new Date();
                date.setTime(date.getTime() + (exdays * 24 * 60 * 60 * 1000));
                var expires = "expires=" + date.toGMTString();
                document.cookie = activityIdKey + "=" + activityIdValue + "; " + expires;
            }
        })
    </script>

</head>
<body>
<form action="/sports/admin/activityCreate">
    <div class="bar-top">
        <ul>
            <li><a href="#">首页</a></li>
            <li><a href="activity.jsp">活动发布</a></li>
            <li><a href="adminupdate.jsp">最新活动</a></li>
            <li><a href="adminquery.jsp">活动查询</a></li>
        </ul>
    </div>
    <div class="active-img">
        <span class="query-notice"> </span><br/><br/>
        <div class="user-login">
            活动日期：<input type="date" id="activity-date" style="width: 40%"/>
        </div>
        <div class="user-login">
            活动时间： <select id="activity-time"  style="width: 40%" >
                            <option value="time1">18:00-20:00</option>
                            <option value="time2">17:00-20:00</option>
                        </select>
        </div>
        <div class="user-login">
            活动地点：  <select id="activity-place"  style="width: 40%">
                            <option value="place1">地点1</option>
                            <option value="place2">地点2</option>
                        </select>
        </div>

        <div class="order-btn">
            <input type="button" id="btn-order" class="btn btn-success" style="width: 80%" value="添加">
        </div>
        <div id="activityAdd" style="text-align: center;color: #3D9140; margin-top: 10px;">
        </div>

    </div>
</form>
</body>
</html>
