<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/3/30
  Time: 7:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/bootstrap.min.css" />
    <title>注册</title>
</head>

<body>
<div class="container">
    <div method="post">
        <div class="form-group">
            <label id="lblUsername" for="inputUsername">用户名</label>
            <input name="username" type="text" class="form-control" id="inputUsername" placeholder="请输入用户名">
        </div>
        <div class="form-group">
            <label for="inputPassword1">密码</label>
            <input name="password1" type="password" class="form-control" id="inputPassword1" placeholder="请输入密码">
        </div>
        <div class="form-group">
            <label for="inputPassword2">重复密码</label>
            <input name="password2" type="password" class="form-control" id="inputPassword2" placeholder="请重复密码">
        </div>

        <div class="form-group">
            <label for="inputGender">性别</label>
            <select name="gender" class="form-control" id="inputGender">
                <option>男</option>
                <option>女</option>
            </select>
        </div>
        <div class="form-group">
            <label for="inputAge">年龄</label>
            <input name="age" type="text" class="form-control" id="inputAge" placeholder="请输入年龄">
        </div>
        <div class="form-group">
            <label for="inputVerifyCode">验证码</label>
            <input name="verifyCode" type="text" class="form-control" id="inputVerifyCode" placeholder="请输入验证码">
            <div style="margin-top:5px">
                <img id="imgVerCode" src="http://qq.knife037.cn/verifyCode" />
            </div>
        </div>
        <button id="register" class="btn btn-default">注册</button>
    </div>
</div>
</body>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script>
    $(document).ready(function() {
        $("#imgVerCode").click(function() {
            $(this).attr("src", "http://qq.knife037.cn/verifyCode?date=" + new Date());
        });

        $("#inputUsername").blur(function() {

            $.post("register", "username="+$(this).val(), function(data) {
                if(data == "success") {
                    $("#lblUsername").html("用户名（可用）")
                } else {
                    $("#lblUsername").html("用户名（不可用）");;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
                    alert(data);
                }
            });
        });;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

        $("#register").click(function() {
            var username = $("#inputUsername").val();
            var password = $("#inputPassword1").val();
            var gender = $("#inputGender").val();
            var age = $("#inputAge").val();
            var verCode = $("#inputVerifyCode").val();

            if(password != $("#inputPassword2").val()) {
                $("#inputPassword1").val("");;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
                $("#inputPassword2").val("");;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
                alert("两次密码不一致");
                return ;
            }

            $.post("register", "username="+username+"&password="+password+"&gender="+gender+"&age="+age+"&verifyCode="+verCode, function (data) {
                if(data == "success") {
                    alert("创建成功");
                    window.location.href = "login";
                } else {
                    alert(data)
                }
            });

        });
    });
</script>

</html>
