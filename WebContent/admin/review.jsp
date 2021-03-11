<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset=UTF-8>
		<title>Insert title here</title>
		<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
		<style>
			table, th, td{
				border: 1px solid black;
				border-collapse: collapse;
				padding: 5px 10px;
			}
		</style>
	</head>
	<body>
		<h3>리뷰 관리</h3>
		<hr/>
		<table>
		<tr>
			<th>리뷰번호</th><th>제목</th><th>영화 제목</th><th>작성자 ID</th><th>작성날짜</th><th>삭제여부</th>
		</tr>
		<!-- key : reviewList, value : movieList-->
		<c:forEach items="${map }" var="list">
		<tr>
			<td>${list.key.idx }</td>
			<td>${list.key.subject }</td>
			<td>${list.value.subject }</td>
			<td>${list.key.id}</td>
			<td>${list.key.date }</td>
			<td>${list.key.delType }</td>
			<td>
				<c:if test="${list.value.delType == 'y' || list.value.delType == 'Y'}">
					<button value="${review.idx }">처리중</button>
				</c:if>
				<c:if test="${list.value.delType == 'n' || list.value.delType == 'N'}">
					<button value="${review.idx }">처리완료</button>
				</c:if>
			</td>
		</tr>
		</c:forEach>
		</table>
	</body>
	<script>
	</script>
</html>