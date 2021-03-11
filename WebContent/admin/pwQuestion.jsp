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
		<h3>질문리스트</h3>
		<hr/>
		<table>
		<tr>
			<th>순번</th><th>질문내용</th>
		</tr>
		<c:forEach items="${list }" var="question">
		<tr>
			<td>${question.idx }</td>
			<td>${question.content }</td>
			<td>
				<button>삭제</button>
			</td>
		</tr>
		</c:forEach>
		</table>
	</body>
	<script>
	</script>
</html>