<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="wifi.LoadWifi"%>
<%@page import="java.sql.ResultSetMetaData"%>
<%@page import="java.sql.ResultSet"%>
<!DOCTYPE html>
<html>

<head>
<style>
div {text-align: center;
}
</style>
<meta charset="UTF-8">
<title>와이파이 정보 가져오기</title>
</head>
<body>
<div >
	<h1>
		<% 
		LoadWifi wifi = new LoadWifi();
				out.print(wifi.LoadWifi());
		%>개의 WIFI정보를 정상적으로 저장하였습니다.
		
	</h1>
		<a href="home.jsp">홈으로 가기</a>&nbsp;
</div>

</body>
</html>