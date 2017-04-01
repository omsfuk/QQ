<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/3/30
  Time: 17:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/bootstrap.min.css" />
    <title>写说说</title>
</head>

<body>
<div class="container">
    <form method="post" id="form">
        <div class="form-group">
            <label for="cont"></label>
            <textarea type="email" name="content" rows="5" class="form-control" id="cont" placeholder="请输入说说内容"></textarea>
        </div>
    </form>
    <button id="publish" type="submit" class="btn btn-default">发布</button>
    <a href="index" class="btn btn-default">返回首页</a>
    <a href="comment" class="btn btn-default">查看评论</a>
</div>
</body>
<script type="text/javascript" src="js/jquery.min.js" ></script>
<script type="text/javascript" src="js/bootstrap.min.js" ></script>
<script>
    $(document).ready(function() {
        $("#publish").click(function() {
            $.post("write", "&content=" + $("#cont").val(), function(data) {
                if(data != "failure") {
                    alert("发表成功");
                } else {
                    alert("发表失败");
                };
            });
        });
    });
</script>
</html>