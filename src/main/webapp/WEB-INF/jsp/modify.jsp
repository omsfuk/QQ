<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/3/31
  Time: 16:39
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
    <form method="post">
        <div class="form-group">
            <label for="inputPassword1">密码</label>
            <input name="password1" type="password" class="form-control" id="inputPassword1" placeholder="请输入密码">
        </div>
        <div class="form-group">
            <label for="inputPassword2">重复密码</label>
            <input name="password2" type="password" class="form-control" id="inputPassword2" placeholder="请重复密码">
        </div>

        <div class="form-group">
            <label for="inputGender">重复密码</label>
            <select name="gender" class="form-control" id="inputGender">
                <option>男</option>
                <option>女</option>
            </select>
        </div>
        <div class="form-group">
            <label for="inputAge">年龄</label>
            <input name="age" type="text" class="form-control" id="inputAge" placeholder="请输入年龄">
        </div>
        <button type="submit" class="btn btn-default">提交</button>
    </form>
</div>
</body>
</html>