<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
	</head>
		<style>
		table,td,th{
			border: 1px solid black;
			border-collapse: collapse;
			padding: 5px 10px;
		}
	</style>
	<body>
		<h3>회원가입</h3>
		<form action="join" method="POST">
			<table>
				<tr>
					<th>ID</th>
					<td><input type="text" name="userId"/></td>
				</tr>
				<tr>
					<th>PW</th>
					<td><input type="text" name="userPw"/></td>
				</tr>
				<tr>
					<th>NAME</th>
					<td><input type="text" name="name"/></td>
				</tr>
				<tr>
					<th>AGE</th>
					<td><input type="text" name="age"/></td>
				</tr>
				<tr>
					<th>GENDER</th>
					<td>
						<input type="radio" name="gender" value="남"/>남
						<input type="radio" name="gender" value="여"/>여
					</td>
				</tr>
				<tr>
					<th>EMAIL</th>
					<td><input type="text" name="email"/></td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="회원가입"/>
					</td>
				</tr>
			</table>
		</form>
	</body>
	<script>
	var msg = "${msg}";
	if(msg!=""){
		alert(msg);
	}
	</script>
</html>