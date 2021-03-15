<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PW찾기</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<style>
table {
	width: 300px;
	margin: auto;
	margin-top : 100px;
}

div {
	text-align: left;
}

a:link {
	color: black;
	text-decoration: none;
	font-size: 14px;
	font-weight: 600;
}

a:visited {
	color: rgb(58, 55, 55);
	text-decoration: none;
	font-size: 14px;
	font-weight: 600;
}

a:hover {
	text-decoration: underline;
}

lable {
	text-align: left;
}

#Findpw {
	border: none;
	border-radius: 4px;
	dispalay: inline;
	background: #212529;
	color: #fff;
	width: 300px;
	margin-top: 20px;
	border: solid 2px #212529;
}
</style>
</head>
<body>
	<table>
		<tr>
			<td align="left"><a href="idFind.jsp" id="idFind">아이디 찾기</a> <a href=""
				id="Findpw">비밀번호 찾기</a></td>
		</tr>
		<tr>
			<td>
				<div>
					<label>아이디</label><br> <input type="text" id="id" name="id" />
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div>
					<lable>비밀번호 찾기 질문</lable>
					<br> <select id="pw_q">
						<!-- <option value="">선택</option> -->
						<c:forEach items="${questionList}" var="questionList">

							<option value="${questionList.idx}">${questionList.content}</option>
						</c:forEach>

					</select>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div>
					<label>비밀번호 찾기 답변</label><br> <input type="text"
						name="pw_answer" id="pw_answer" />
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="3"><input type="submit" value="비밀번호 찾기" 
				onclick="pwFind()" /> <!-- <input type="submit" value="비밀번호 찾기" onclick="location.href='../pwFind'" /> -->
			</td>
		</tr>
		<tr>
			<td>비밀번호
				<div>
					<input type="text" id="pwFind">
				</div>
			</td>
		</tr>


	</table>


</body>
<script>
	function pwFind() {
		var id = document.getElementById("id");
		var A = document.getElementById("pw_answer");
		if (id.value == "") {
			alert("아이디를 입력하세요.")
			id.focus()

		} else if (A.value == "") {
			alert("비밀번호 찾기 답변을 입력하세요.");
			A.focus();
		} else {
			$.ajax({
				type : 'post',
				url : 'pwFind',
				data : {
					"id" : $("#id").val(),
					"question_idx" : $("#pw_q").val(),
					"pw_answer" : $("#pw_answer").val()
				},
				dataType : 'JSON',
				success : function(obj) {
					console.log(obj.userPW);
					//일치하면 찾는 비밀번호 보여주기 
					if (obj.userPW != null) {
						$('#pwFind').val(obj.userPW);
					} else {
						$('#pwFind').val("입력한 정보와 일치하는 회원이 없습니다.");
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