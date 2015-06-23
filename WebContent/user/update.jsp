<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 此时没有写action，直接提交会提交给/add -->
<sf:form method="POST" modelAttribute="user">
Username:<sf:input path="username"/><sf:errors path="username"/><br/>
Password:<sf:input path="password"/><sf:errors path="password"/><br/>
Nickname:<sf:input path="nickname"/><br/>
Email:<sf:input path="email"/><sf:errors path="email"/><br/>
<input type="submit" value="修改用户"/>
</sf:form>
</body>
</html>
