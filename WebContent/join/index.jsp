<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
<title>로그인</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<style>
table {
	margin: auto;
	width: 300px;
	margin-top: 100px;
}

h3 {
	text-align: center;
	color: lightgray;
}

div {
	text-align: left;
	color: lightgray;
}

input {
	width: 300px;
	margin: 5px;
}

a:link, a:visited {
	color: lightgray;
	text-decoration: none;
	font-size: 14px;
	font-weight: 600;
}

a:hover {
	text-decoration: underline;
}

:focus {
	outline-color: lightgray;
}
</style>
</head>
<body>
	<jsp:include page="../movie/include.jsp" />
	<div id="basic" class="basic">
		<div id="container">
			<div id="content">
				<div class="movie_main">
					<table>
						<tr>
							<td>
								<h3>movier</h3>
							</td>
						</tr>
						<tr>
							<td>
								<form action="" method="post">
									<div>
										<label>아이디</label><br/> <input type="text" id="id" name="id" />
									</div>
									<div>
										<label>비밀번호</label><br/> <input type="password" id="pw" name="pw" />
									</div>
								</form>
							</td>
						</tr>
						<tr>
							<td colspan="3" align="right" style="color: lightgray">
								<a href="idFind.jsp">아이디</a>/<a href="../pwQuestionList">비밀번호 찾기</a>
							</td>
						</tr>
						<tr>
							<td colspan="3">
								<input type="button" value="로그인" id="login" style="text-align: center;" />
								<br/>
								<input type="button" value="회원가입" onclick="location.href='../questionList'" />
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	 $("div").remove("#login");
	 
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
					"myLoginId" : id.value,
					"pw" : pw.value
				},
				success : function(data){
					console.log(data);
					console.log(data.use);
					if(data=='{"use":true}'){
						location.href="../movie/home";//임시
					}else{
						alert("아이디 또는 비밀번호를 확인하세요.");
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