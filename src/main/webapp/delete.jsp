<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="service.HistoryService"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
	HistoryService historyService = new HistoryService();
		
		int affected =historyService.delete(Integer.parseInt(request.getParameter("id")));
		String message = "삭제되지 않았습니다.";
		if (affected > 0) {
			message = "삭제되었습니다.";
		}
	%>
	
	<script>
		alert("<%=message %>");
		location.href="history.jsp";
	</script>

</body>
</html>