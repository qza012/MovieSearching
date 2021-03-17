<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
     table {
        border-collapse: separate;
        border-spacing: 0;
        text-align: center;
        line-height: 1.5;
        border-top: 1px solid #ccc;
        border-left: 1px solid #ccc;
        margin : auto;
        margin-top: 100px;
    }
    th {
        width: 100px;
        padding: 10px;
        font-weight: bold;
        vertical-align: top;
        border-right: 1px solid #ccc;
        border-bottom: 1px solid #ccc;
        border-top: 1px solid #fff;
        border-left: 1px solid #fff;
        background: #eee;
    }
    td {
        width: 150px;
        padding: 10px;
        vertical-align: top;
        border-right: 1px solid #ccc;
        border-bottom: 1px solid #ccc;
    }
    h4{
		position: relative;
		left: 35%;
		float: left;
		z-index: 5;
		color: white;
		font-size: 25px;
	}
</style>
<body>
	<jsp:include page="../movie/include.jsp" />
	<hr>
	<h4>알람 리스트</h4>
    <table>
        <tr>
            <th>번호</th>
            <th>알람내용</th>
            <th>알람 날짜</th>
            <th>확인</th>
        </tr>
        <c:forEach items="${alarm_list}" var="alarm3">
        <tr>
            <td>${alarm3.idx}</td>
            <td>${alarm3.content}</td>
            <td>${alarm3.reg_date}</td>
            <td><a href="alarmDel?idx=${alarm3.idx}">삭제</a></td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>