<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="service.HistoryService"%>
<%@page import="service.WifiService"%>
<%@page import="wifi.Wifi"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>

<%
String my_Lat = request.getParameter("Lat");
String my_Lnt = request.getParameter("Lnt");
%>
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
	<h1>와이파이 정보 구하기</h1>

	<div>
		<a href="home.jsp">홈</a>&nbsp;&#10072; <a href="history.jsp">위치
			히스토리 목록</a>&nbsp;&#10072; <a href="load-wifi.jsp">Open API 와이파이 정보
			가져오기</a>

	</div>

	<form action="home.jsp" method="get">

		LAT:&nbsp;<input type="text" id="Lat" name="Lat" value=0.0>
		,&nbsp; LNT:&nbsp;<input type="text" id="Lnt" name="Lnt" value=0.0>
		<button onclick="findLocation()" type="button">내 위치 가져오기</button>
		<button type="submit">근처 WIFI 정보 찾기</button>

	</form>
	<script>
		function findLocation() {
			if (navigator.geolocation) {
				navigator.geolocation.getCurrentPosition(showYourLocation);
			} else {
				loc.innerHTML = "이 문장은 사용자의 웹 브라우저가 Geolocation API를 지원하지 않을 때 나타납니다!";
			}
		}

		function showYourLocation(position) {

			document.getElementById("Lat").value = position.coords.latitude;
			document.getElementById("Lnt").value = position.coords.longitude;

		}
	</script>





	<table>
		<tr>
			<th>거리(Km)</th>
			<th>관리번호</th>
			<th>자치구</th>
			<th>와이파이명</th>
			<th>도로명주소</th>
			<th>상세주소</th>
			<th>설치위치(층)</th>
			<th>설치유형</th>
			<th>설치기관</th>
			<th>서비스구분</th>
			<th>망종류</th>
			<th>설치년도</th>
			<th>실내외구분</th>
			<th>WIFI접속환경</th>
			<th>X좌표</th>
			<th>Y좌표</th>
			<th>작업일자</th>
		</tr>
		<tr>
			<%
			if (my_Lat == null && my_Lnt == null || my_Lat.equals("0.0") && my_Lnt.equals("0.0")) {
			%>

			<td colspan="17">위치 정보를 입력한 후에 조회해 주세요.</td>
		</tr>
		<%
		} else {
		HistoryService history = new HistoryService();
		history.HistoryIn(my_Lat, my_Lnt);

		WifiService wifiService = new WifiService();
		List<Wifi> wifiList = wifiService.aroundWifi(my_Lat, my_Lnt);
		for (Wifi wifi : wifiList) {
		%>
		<tr>
			<td><%=Math.round(wifi.getDist()*100)/100.0%></td>
			<td><%=wifi.getMgrNo()%></td>
			<td><%=wifi.getWrdofc()%></td>
			<td><%=wifi.getMainNm()%></td>
			<td><%=wifi.getAdres1()%></td>
			<td><%=wifi.getAdres2()%></td>
			<% if(wifi.getInstlFloor() == null){%>
			<td></td>
			<% }else{%>

			<td><%=wifi.getInstlFloor()%></td>
			<%}%>

			<td><%=wifi.getInstlTy()%></td>
			<td><%=wifi.getInstlMby()%></td>
			<td><%=wifi.getSvcSe()%></td>
			<td><%=wifi.getCmcwr()%></td>
			<td><%=wifi.getCnstcYear()%></td>
			<td><%=wifi.getInoutDoor()%></td>
			<% if(wifi.getRemars3() == null){%>
			<td></td>
			<% }else{%>
			<td><%=wifi.getRemars3()%></td>
			<%}%>
			<td><%=wifi.getLat()%></td>
			<td><%=wifi.getLnt()%></td>
			<td><%=wifi.getWorkDttm()%></td>

		</tr>
		<%
		}
		%>
		<%
		}
		%>





	</table>


</body>
</html>