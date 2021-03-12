<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
</head>
<body>
	<h3>movier</h3>
	<table>
		<tr>
			<td>
				<form action="" method="post">
					<div>
						<label>아이디</label><br> <input type="text" id="id" name="id" />

					</div>

					<div>
						<label>비밀번호</label><br> <input type="password" id="pw" name="pw" />
					</div>

				</form>
		<tr>
			<td colspan="3" align="center"><a href="idFind.jsp">아이디</a>/<a
				href="pwFind.jsp">비밀번호 찾기</a></td>
		</tr>
		<tr>
			<td colspan="3"><input type="button" value="로그인"  id="login"/> </br> 
			<input type="button" value="회원가입" onclick="location.href='./Qlist'" /></td>
		</tr>
	</table>
</body>
<script>
	 
	var id = document.getElementById("id");
	var pw = document.getElementById("pw");
	/* var id = $('#id').val();
	var pw = $('#pw').val(); */
	$("#login").click(function() {
		console.log(id + "/" + pw);
		if (id.value == "") {
			alert("아이디를 입력하세요.");
			id.focus();

		} else if (pw.value == "") {
			alert("패스워드를 입력하세요.");
			pw.focus();
		} else {
			$.ajax({
				type : 'POST',
				url : 'login',
				data : {
					"id" : id.value,
					"pw" : pw.value
				},
				success : function(data){
					console.log(data);
					console.log(data.use);
					if(data=='{"use":true}'){
						
					location.href='idFind.jsp';
					}else{
						alert("아이디 또는 비밀번호를 확인하세요.")
					}
				},
				error : function(e){
					console.log(e);
				}
			});
			
		}
	});
</script>
</html>