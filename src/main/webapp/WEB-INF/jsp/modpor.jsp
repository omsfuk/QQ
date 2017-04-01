<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/3/31
  Time: 18:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/bootstrap.min.css" />
    <title>修改头像</title>
</head>

<body>

<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">系统提示</h3>
        </div>
        <div class="panel-body">
            <form method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="exampleInputFile">请选择文件</label>
                    <input type="file" id="exampleInputFile" name="file">
                    <input class="btn btn-default" type="submit" value="上传">
                </div>
            </form>
        </div>
    </div>
</div>

</body>

</html>