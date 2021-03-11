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
		<h3>댓글 리포트</h3>
		<hr/>
		<table>
		<tr>
			<th>신고번호</th><th>댓글번호</th><th>리뷰번호</th><th>사유</th><th>신고한 회원 ID</th><th>신고당한 회원 ID</th><th>신고날짜</th><th>처리 유무</th>
		</tr>
		<c:forEach items="${list }" var="comment">
		<tr>
			<td>${comment.idx }</td>
			<td>${comment.reportIdx }</td>
			<td>${comment.content }</td>
			<td>${comment.reportId}</td>
			<td>${comment.email }</td>
			<td>${comment.regDate }</td>
			<td>${comment.complete }</td>
			<td>
				<c:if test="${comment.complete == 'y' || comment.complete == 'Y'}">
					<button value="${comment.idx }">처리중</button>
				</c:if>
				<c:if test="${comment.complete == 'n' || comment.complete == 'N'}">
					<button value="${comment.idx }">처리완료</button>
				</c:if>
			</td>
		</tr>
		</c:forEach>
		</table>
	</body>
	<script>
	</script>
</html>