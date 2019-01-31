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
        $(function() {
            $("#btn-order").click(function () {
                var options =  $("input:radio[name='checks']:checked").val();
                // $(".test").text(options);
                $("#status").text(options);
                //$("#status").html('<h1>'+ options + '</h1>')
            })
        })
    </script>


</head>
<body>
<form>
    <div class="bar-top">
        <ul>
            <li><a href="#">首页</a></li>
            <li><a href="activity.jsp">活动发布</a></li>
            <li><a href="adminupdate.jsp">活动更新</a></li>
            <li><a href="adminquery.jsp">活动查询</a></li>
        </ul>
    </div>
    <div class="active-img">
        <img src="${pageContext.request.contextPath}/resources/images/1.png" />
        <div class="active-title">
            <label class="la1">活动名称：羽毛球第二期</label><br/>
            <label class="la2">活动日期：2019-01-01</label><br/>
            活动状态：<span id="status" ></span>
        </div>
        <br>
        <div class="options">
            <label >本次活动：</label>
            <input type="radio" name="checks" id="option1" value="已取消"> 取消本次活动&nbsp;&nbsp;
            <input type="radio" name="checks" id="option2" value="持续中" checked="checked"> 继续本次活动
        </div>
        <div class="order-btn">
            <input type="button" id="btn-order" class="btn btn-success" style="width: 100%; margin-top: 20%" value="确定修改">
        </div>
    </div>
</form>
</body>
</html>
