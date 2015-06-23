<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/main.css" type="text/css"/>
</head>
<body>

<table border="1" align="center" width="30%">
<tr><td colspan="5" align="center">登录用户：${sessionScope.loginUser.nickname}</td><td colspan="2"><a href="<%=request.getContextPath() %>/logout">退出系统</a></td></tr>
<tr>
<td>ID<td>用户名</td><td>密码</td><td>昵称</td><td>email</td><td colspan="2">操作</td>
</tr>
<c:if test="${pm.recordCount le 0 }">
	<tr>
	<td colspan="7">目前还没有用户数据</td>
	</tr>
</c:if>
<c:if test="${pm.recordCount gt 0}">
<c:forEach items="${pm.datas}" var="user">
<tr>
<td>${user.id}</td>
<td>${user.username}</td>
<td>${user.password}</td>
<td>${user.nickname}</td>
<td>${user.email}</td>
<td><a href="${user.id}/update">修改</a></td>
<td><a href="${user.id}/delete">删除</a></td>
</tr>
</c:forEach>
<tr>
	<td colspan="7">
			<jsp:include page="/pager.jsp">
				<jsp:param value="list" name="url"/>
				<jsp:param value="${pm.recordCount}" name="items"/>
			</jsp:include>
	</td>
</tr>
</c:if>
<tr><td colspan="7"><h4 align="center"><a href="add">添加用户</a></h4></td></tr>
</table>

</body>
</html>
