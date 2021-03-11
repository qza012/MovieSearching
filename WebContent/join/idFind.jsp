<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>ID찾기</title>
		<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
		<style>
			{margin:0 auto}
			
		</style>
	</head>
	<body>
		<div>
			<label>아이디 찾기</label><br>
			<tr>
				<td>이름</td>
				<input type="text" name="name" >
			</tr><br>
			<tr>
				<td>이메일</td>
				<input type="email" name="email" >
			</tr><br>
			<input type="submit" value="아이디 찾기"/>
				
		</div>
		<label>아이디</label>
		<input type="text" name="id" id="findId"><br>
		<a href="index.jsp">로그인 하러 가기</a>
		
	</body>
	<script>
	
	</script>
</html>