<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/3/30
  Time: 19:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page isELIgnored="false"%>

<c:choose>

    <c:when test="${node.child == null}">

    </c:when>
    <c:otherwise>
        <c:forEach var="child" items="${node.child}">

            <div data-id="${child.say.uuid}" class="saysay">
                <b>${child.say.username}</b> say ${child.say.content}
                <c:choose>
                    <c:when test="${child.say.hasGreat}">
                        <button data-id="${child.say.uuid}" name="btnGreat" class="btn btn-danger disabled">${child.say.greatNum}</button>
                    </c:when>
                    <c:otherwise>
                        <button data-id="${child.say.uuid}" name="btnGreat" class="btn btn-danger">${child.say.greatNum}</button>
                    </c:otherwise>
                </c:choose>
                <button data-id="${child.say.uuid}" name="btnComment" class="btn btn-default">评论</button>
                <button data-id="${child.say.uuid}" name="btnDelete" class="btn btn-default">删除</button>
                <c:set var="node" value="${child}" scope="request"/>
                <c:import url="__r.jsp"/>
            </div>
        </c:forEach>

    </c:otherwise>
</c:choose>
