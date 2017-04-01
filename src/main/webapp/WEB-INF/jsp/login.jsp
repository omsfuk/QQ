<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/3/30
  Time: 15:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>登录</title>
    <link rel="stylesheet" href="css/bootstrap.min.css" />
</head>
<body>
<div class="container">
    <form>
        <div class="form-group">
            <label id="lblUsername" for="inputUsername">用户名</label>
            <input name="username" type="text" class="form-control" id="inputUsername" placeholder="请输入用户名">
        </div>
        <div class="form-group">
            <label id="lblPassword" for="inputPassword">密码</label>
            <input name="password" type="password" class="form-control" id="inputPassword" placeholder="请输入密码">
        </div>
    </form>
    <button id="login" class="btn btn-default">登录</button>
    <a href="register" class="btn btn-default">注册</a>
</body>
<script type="text/javascript" src="js/jquery.min.js" ></script>
<script>
    $(document).ready(function() {
        $("#inputUsername").blur(function() {
            $.post("login", "username=" + $("#inputUsername").val(), function(data) {
                if(data == "success") {
                    $("#lblUsername").html("用户名（存在）");
                } else {
                    $("#lblUsername").html("用户名（不存在）");
                }
            });
        });

        $("#login").click(function() {
            $.post("login", "username=" + $("#inputUsername").val() + "&password=" + $("#inputPassword").val(), function(data) {
                if(data == "success") {
                    window.location.href = "index";
                } else {
                    $("#lblPassword").html("密码错误");
                }
            });
        });
    });
</script>
</html>
