<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="wifi.History"%>
<%@page import="service.HistoryService"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>위치 히스토리 목록</title>
<script type="text/javascript">
		function del(ID) {

			location.href="delete.jsp?id=" + ID;
			
		}
	</script>
<style>
table {
	border-collapse: collapse;
	width: 100%;
	margin: 5px;
}


th, td {
	text-align: center;
	padding: 8px;
	border: solid 1px #eeeeee;
	background-colr: #white;
}

 tr:nth-child(even){background-color: #f2f2f2;}
 tr:hover {background-color: #ddd;}
th {
	background-color: #04AA6D;
	color: #f2f2f2;
}
</style>
</head>

<body>
	<%
		HistoryService historyService = new HistoryService();
		List<History> historyList = historyService.list();
		%>
	
		
	
	<h1>위치 히스토리 목록</h1>

	<a href="home.jsp">홈</a>&nbsp;&#10072;
	<a href="history.jsp">위치 히스토리 목록</a>&nbsp;&#10072;
	<a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a>

	<table>
		<tr>
			<th>ID</th>
			<th>X좌표</th>
			<th>Y좌표</th>
			<th>조회일자</th>
			<th>비고</th>
		</tr>
		<tbody>
			
				<%
	    			for(History history : historyList){
	    		%>
				<tr id = <%= history.getId() %>>
				<td> <%= history.getId() %></td>
				<td><%=history.getLat() %></td>
				<td><%=history.getLnt() %></td>
				<td><%=history.getInquireTime() %></td>
				<td><button type="button"  value = "<%= history.getId()%>" onclick =  "del('<%= history.getId()%>');">삭제</button></td>
				</tr>
				
				<%}%>
			
		</tbody>
	</table>
</body>
</html>