<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/3/30
  Time: 17:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8" />
    <title></title>
    <link rel="stylesheet" href="css/bootstrap.min.css" />
    <link rel="stylesheet" href="css/mystyle.css" />
</head>

<body>
<input id="username" type="hidden" value="${user.username}" />
<div class="container">

    <div class="container">
        <div style="width:70%;float:left" class="panel panel-default">
            <div class="panel-heading">
                <img src="users/${user.username}/portrait.png" width="100px" height="100px" style="box-shadow: 0px 0px 10px #999999;" />
                <div style="display:inline-block;border:2px solid skyblue;margin-left:10px;padding:10px">
                    ${user.username}
                </div>
                <div style="display:inline-block;margin-left:20px;">
                    <a href="write" class="btn btn-default">我要发说说</a>
                    <a href="comment" class="btn btn-default">我要吐槽</a>
                    <a href="modify" class="btn btn-default">修改信息</a>
                    <a href="modpor" class="btn btn-default">修改头像</a>
                    <div id="info" style="display: inline-block;margin-left:10px;color:lawngreen"></div>
                </div>
            </div>
            <div id="boxes" class="panel-body">

            </div>
        </div>

        <div style="width:30%;float:left;" class="panel panel-default">
            <div class="panel-heading">
                好友列表
            </div>
            <div class="panel-body">
                <div class="container" style="margin-bottom: 10px;">
                    <div class="form-inline">
                        <div class="form-group" style="width:200px">
                            <div class="input-group">
                                <div class="input-group-addon">ID</div>
                                <input type="text" class="form-control" id="friendName" placeholder="请输入好友用户名">
                            </div>
                        </div>
                        <button id="addFriend" class="btn btn-primary">添加好友</button>
                    </div>
                </div>
                <c:forEach var="friend" items="${friends}">
                    <div>
                        <div style="display:inline-block;">
                            <img src="users/${friend}/portrait.png" width="50px" height="50px" ;/>
                            <div style="display: inline-block;padding-left:10px;padding-right:10px;">${friend}</div>
                        </div>
                        <button name="btnChat" data-friend="${friend}" class="btn btn-primary">聊一聊</button>
                        <button name="btnDelFriend" data-friend="${friend}" class="btn btn-danger">绝交</button>
                    </div>
                </c:forEach>
                <c:forEach var="friend" items="${ingFriends}">
                    <div>
                        <div style="display:inline-block;">
                            <img src="users/${friend}/portrait.png" width="50px" height="50px" ;/>
                            <div style="display: inline-block;padding-left:10px;padding-right:10px;">${friend}</div>
                        </div>
                        <button name="btnPass" data-friend="${friend}" class="btn btn-primary">验证通过</button>
                        <button name="btnDelFriend" data-friend="${friend}" class="btn btn-danger">滚蛋</button>
                    </div>
                </c:forEach>

            </div>
        </div>
    </div>
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script>
        function showBox(friend) {
            $("#boxes").append('<div id="' +
                friend +
                '" class="chatbox">' +
                '<div class="panel panel-default">' +
                '<div class="panel-heading">' +
                friend +
                '<button name="close" class="btn btn-danger" style="float:right;">X</button>' +
                '</div>' +
                '<div class="panel-body">' +
                '<div class="form-group">' +
                '<textarea rows="5" id="content" class="disabled"></textarea>' +
                '<input type="text" class="form-control" id="msg" placeholder="Content">' +
                '</div>' +
                '<div class="form-group">' +
                '<button name="btnSend" data-friend="' +
                friend +
                '" class="btn btn-default">发送</button>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>');
        }

        var webSocket = new WebSocket('ws://localhost:8080/websocket');

        webSocket.onerror = function(event) {
            $("#info").html("连接异常");
        };
        //与WebSocket建立连接
        webSocket.onopen = function(event) {
            $("#info").html("连接成功");
            var from = $("#username").val()
            var msg = {
                from: from,
                to: "SERVER",
                cont: "none"
            }

            webSocket.send(JSON.stringify(msg));
        };
        //处理服务器返回的信息
        webSocket.onmessage = function(event) {

            var msg = jQuery.parseJSON(event.data);
            var from = msg.from;
            var cont = msg.cont;

            if($("#" + from).length == 0) {
                showBox(from);
            }

            $("#" + from + " textarea").html($("#" + from + " textarea").html() + msg.from + " :\r\n    " + cont + "\r\n");

        };

        $(document).on("click", "div[class='chatbox'] button[name='close']", function() {
            $(this).parent().parent().parent().remove();
        });

        $(document).on("click", "div[class='chatbox'] button[name='btnSend']", function() {
            var btn = $(this);
            var s = $("#" + btn.attr("data-friend") + " input");
            var to = btn.attr("data-friend");
            var from = $("#username").val()
            var msg = {
                from: from,
                to: to,
                cont: s.val()
            }

            $("#" + to + " textarea").html($("#" + to + " textarea").html() + "我 :\r\n    " + s.val() + "\r\n");

            webSocket.send(JSON.stringify(msg));
            s.val("");
        });

        $(document).on("click", "button[name='btnChat']", function() {
            if($("#" + $(this).attr("data-friend")).length == 0) {
                showBox($(this).attr("data-friend"));
            }
        });

        $(document).on("click", 'button[name="btnDelFriend"]', function() {
            $.post("deleteFriend", "friendName=" + $(this).attr("data-friend"), function(data) {
                if(data == "failure") {
                    alert("由于未知原因，删除失败")
                } else {
                    alert("好友已删除");
                    window.location.reload()
                }
            });
        });

        $(document).on("click", "button[name='btnPass']", function() {
            $.post("upgradeFriend", "friendName=" + $(this).attr("data-friend"), function(data) {
                if(data == "failure") {
                    alert("由于未知原因，验证失败")
                } else {
                    alert("好友已添加");
                    window.location.reload()
                }
            });
        });

        $(document).ready(function() {

            $("#addFriend").click(function() {
                var friendName = $("#friendName").val();
                if(friendName != null) {
                    $.post("addFriend", "friendName=" + friendName, function(data) {
                        if(data == "success") {
                            alert("等待对方确认");
                            window.location.reload()
                        } else {
                            alert(data)
                        }
                    });
                } else {
                    alert("好友ID不能为空")
                };
            })

        });
    </script>
</div>
</body>