<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
<title>ID찾기</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<style>
table {
	margin: auto;
	margin-top : 100px;
}


a:link {
	color: black;
	text-decoration: none;
	font-size: 12px;
	font-weight: 600;
}

a:visited {
	color: rgb(58, 55, 55);
	text-decoration: none;
	font-size: 12px;
	font-weight: 600;
}

a:hover {
	text-decoration: underline;
}

input {
	width: 300px;
	margin: 5px;
}

#Findid {
	border: none;
	border-radius: 4px;
	dispalay: inline;
	background: #212529;
	color: #fff;
	border: solid 2px #212529;
}

:focus{
    	outline-color: black;
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
								
								<td align="left" ><a href="idFind.jsp" id="Findid">아이디 찾기</a> <a
									href="../pwQuestionList" id="Findpw">비밀번호 찾기</a></td>
							</tr>
							<tr>
								<td align="center">
									<div>
										<label>아이디 찾기</label><br>
									</div>
								</td>
							</tr>
							<tr>
								<td align="left">이름
									<div>
										<input type="text" name="name" id="name" />
									</div>
								</td>
							</tr>
							<tr>
								<td align="left">이메일
									<div>
										<input type="email" name="email" id="email" />
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div>
										<input type="submit" value="아이디 찾기" onclick="idFind()" />
									</div>
								</td>
							</tr>
					
							<tr>
								<td align="left">아이디
									<div>
										<!-- <p id="idFind"></p> -->
										<input type="text" id="idFind">
									</div>
								</td>
							</tr>
							<tr>
								<td align="right">
									<div>
										<a href="index.jsp">로그인 하러 가기</a>
									</div>
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
	function idFind() {
		var name = document.getElementById("name");
		var email = document.getElementById("email");

		if (name.value == "") {
			alert("이름을 입력하세요.")
			name.focus()

		} else if (email.value == "") {
			alert("이메일을 입력하세요.");
			email.focus();
		}else if (email.value.indexOf('@')<0 || email.value.indexOf('.')<0) {
			alert("이메일 형식이 올바르지 않습니다.");
			email.focus();
		} else {
			$.ajax({
				type : 'post',
				url : 'idFind',
				data : {
					"name" : $("#name").val(),
					"email" : $("#email").val()
				},
				dataType : 'JSON',
				success : function(obj) {
					console.log(obj.userID);
					//일치하면 찾는 아이디 보여주기 
					if (obj.userID != null) {
						$('#idFind').val(obj.userID);
					} else {
						alert("등록된 회원 정보가 없습니다.");
						$('#idFind').val("");
					}
					
					
				},
				error : function(e) {
					console.log(e);
				}
			});
		}
	}
</script>
</html>