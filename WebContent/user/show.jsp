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
Username:${user.username}<br/>
Password:${user.password}<br/>
Nickname:${user.nickname}<br/>
Email:${user.email}<br/>
</body>
</html>
