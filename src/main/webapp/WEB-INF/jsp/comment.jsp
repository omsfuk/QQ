<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/3/30
  Time: 15:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/bootstrap.min.css" />
    <link rel="stylesheet" href="css/mystyle.css" />
    <title></title>
</head>
<body>
<div class="main">

    <a href="index" class="btn btn-primary">返回首页</a>
    <a href="write" class="btn btn-primary">发表说说</a>

    <c:import url="__r.jsp"/>

</div>
</body>
<script type="text/javascript" src="js/jquery.min.js" ></script>
<script type="text/javascript" src="js/bootstrap.min.js" ></script>
<script type="text/javascript" src="js/jquery.cookie.js" ></script>
<script>

    $(document).on("click", 'button[name*="btn"]', function() {
        var button = $(this);
        if(button.attr("name").indexOf("Comment") != -1) {

            var content = prompt("请输入评论:", "");
            if (content != null) {
                $.post("write", "id=" + button.attr("data-id") + "&content=" + content, function (data) {
                    if (data != "failure") {
                        button.parent().append('<div class="saysay"><b>' + $.cookie("username") + '</b> say ' + content +
                            '<button data-id="' + data + '" name="btnGreat" class="btn btn-danger">0</button>' +
                            '<button data-id="' + data + '" name="btnComment" class="btn btn-default">评论</button>' +
                            '<button data-id="' + data + '" name="btnDelete" class="btn btn-default">删除</button>' +
                            '</div>');
                        //$("div[class='saysay'][data-id='" + data + "']").click($.comment($(this)));
                    } else {
                        $("#lblUsername").html("用户名（不存在）");
                    }
                    ;
                });
            }
        } else if(button.attr("name").indexOf("Delete") != -1) {
            var button = $(this);
            $.post("delete", "id=" + button.attr("data-id"), function(data) {
                if(data == "success") {
                    alert("删除成功");
                    $("div[class='saysay'][data-id=" + button.attr("data-id") + "]").remove();
                } else {
                    alert(data);
                }
            });
        } else if(button.attr("name").indexOf("Great") != -1) {
            var button = $(this);
            if(button.attr("class") == "btn btn-danger disabled") {
                $.post("great", "id=" + button.attr("data-id"), function(data) {
                    if(data == "success") {
                        button.attr("class", "btn btn-danger");
                        button.html(parseInt(button.html()) - 1);
                    } else {
                        alert(data);
                    }
                });
            } else {
                $.post("great", "id=" + button.attr("data-id"), function(data) {
                    if(data == "success") {
                        button.attr("class", "btn btn-danger disabled");
                        button.html(parseInt(button.html()) + 1);
                    } else {
                        alert(data);
                    }
                });
            }
        }
    });
</script>
</html>