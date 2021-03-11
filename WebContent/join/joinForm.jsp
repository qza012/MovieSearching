<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>회원가입</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<style>
table, td, th {
	padding: 5px 40px;
}
</style>
</head>


<body>
	<h2>회원가입</h2>
		<table>
			<tr>
				<td>
					<div>
						<label>아이디</label><br> <input type="text" name="id" id="id" />
						<input type="button" value="중복 확인" id="idChk" /> <span
							id="id_span"></span> <span id="fail"></span>
					</div>

					<div>
						<label>비밀번호</label><br>
						<input type="password" name="pw" id="pw" onchange="check_pw()" />

					</div>
					<div>
						<label>비밀번호 확인</label><br> <input type="password" name="pw2"
							id="pw2" onkeyup="check_pw()" /> <span id="check"></span>
					</div>



					<div>
						<lable>비밀번호 찾기 질문</lable>
						<br> <select id="pw_q">
							<!-- <option value="">선택</option> -->
						<c:forEach items="${Qlist}" var="Qlist" >
							
							<option value="${Qlist.idx}">${Qlist.content}</option>
						</c:forEach>
							
						</select>
					</div>
					<div>
						<label>비밀번호 찾기 답변</label><br> <input type="text"
							name="pw_answer" id="pw_answer" />
					</div>
					<div>
						<label>이름</label><br> <input type="text" name="name"
							id="name" />
					</div>
					<div>
						<label>나이</label><br> <input type="number" min="15" max="100"
							name="age" id="age" />
					</div>
					<div>
						<label>성별</label> <input type="radio" name="gender" value="male"
							id="male" /> 남 <input type="radio" name="gender" value="female"
							id="female" /> 여
					</div>
					<div>
						<label>이메일<input type="text" name="email" id="email_id" />@
						</label> 
						<span id="email_span"></span>
						 <select
							name="email_sel" id="email_sel" onchange="change_email()">
							<option value="선택" >선택</option>
							<option value="직접 입력" >직접 입력</option>
							<option value="naver.com">naver.com</option>
							<option value="gmail.com">gmail.com</option>
							<option value="daum.net">daum.net</option>
							<option value="nate.com">nate.com</option>
						</select>
					</div>
					<div>
						<label>선호하는 영화 장르</label><br> <select id="genre">
							<option value="액션">액션</option>
							<option value="코미디">코미디</option>
							<option value="스릴러">스릴러</option>
							<option value="로맨스">로맨스</option>
							<option value="애니메이션">애니메이션</option>
							<option value="다큐">다큐</option>
							<option value="드라마">드라마</option>
							<option value="범죄">범죄</option>
							<option value="SF">SF</option>
						</select>
					</div>
					<div>
						<button type="button" onclick="checkAll()">회원가입</button>
					</div>

				</td>
			</tr>
		</table>
	
</body>
<script>
var idChk = false;//중복 체크 여부

$("#idChk").click(function(){			
	$.ajax({
		type:'get'
		,url:'idChk'
		,data:{"id":$("#id").val()}
		,dataType:'JSON'
		,success:function(obj){
			console.log(obj);
			if(obj.use){
				alert('사용할 수 있는 아이디 입니다.');
				$("#id").css({backgroundColor:'yellowgreen'});
				idChk= true;
			}else{
				alert('이미 사용중인 아이디 입니다.');
				$("#id").val('');
			}
		}
		,error:function(e){
			console.log(e);
		}
	});				
});	
	
	function checkAll() {//빈칸 확인 
		var id = document.getElementById("id");
		var pw = document.getElementById("pw");
		var pw2 = document.getElementById("pw2");
		var name = document.getElementById("name");
		var age = document.getElementById("age");
		var female = document.getElementById("female");
		var male = document.getElementById("male");
		var email_id = document.getElementById("email_id");
		var A = document.getElementById("pw_answer");
		var email_sel = document.getElementById("email_sel");
		var email = email_sel.value;

		if (id.value == "") {
			alert("아이디를 입력하세요.")
			id.focus()
			
		}else if (pw.value == "") {
			alert("비밀번호를 입력하세요.");
			pw.focus();
		}else if (pw2.value !== pw.value) {
			alert("비밀번호가 다릅니다.");
			pw2.focus();
		}else if(A.value == "") {
			alert("비밀번호 찾기 답변을 입력하세요. ");
			A.focus();
		}else if(name.value == "") {
			alert("이름을 입력하세요.");
			name.focus();
		}else if(age.value == "") {
			alert("나이를 입력하세요.");
			age.focus();
		}else if(!female.checked && !male.checked) {//둘 다 선택 X
			alert("성별을 선택해주세요.");
			male.focus();
		}else if(email_id.value == "") {//@이전 입력 X
			alert("이메일을 입력하세요.");
			email_id.focus();
		} else if (email_sel.value == "선택") {//@이후 입력 X 
			alert("이메일 주소를 확인해주세요.");
			email_sel.focus();
		}else if(email_sel.value=="직접 입력"){
			if ((email_add.value == "naver.com") || (email_add.value == "gmail.com")
					|| (email_add.value == "nate.com") || (email_add.value == "daum.net")) {
			} else {
				alert("올바른 도메인을 입력해주세요.");
				email_add.focus();
			};
		}else{
			$.ajax({
				type:'post'
				,url:'join'
				,data:{
					"id":$("#id").val(),
					"pw":$("#pw").val(),
					"name":$("#name").val(),
					"age":$("#age").val(),
					"genre":$("#genre").val(),
					"gender":$("input[name='gender']:checked").val(),
					"question_idx":$("#pw_q").val(),
					"pw_answer":$("#pw_answer").val(),
					"email_id":$("#email_id").val(),
					"email_sel":$("#email_sel").val(),
					
					}
				,dataType:'JSON'
				,success:function(obj){
					console.log(obj.use);
					location.href="./index.jsp";
				}
				,error:function(e){
					console.log(e);
				}
			});				
		}
		
	}

	//email  옵션 선택 후 주소완성 
	function change_email() {
		
		if($('#email_sel').val()=='직접 입력'){
		$('#email_span').html('<input type="text" name="email_add" id="email_add" />');
			
		}else{
			$('#email_span').html('');
		}
		console.log($('#email_sel').val());
	}

	function check_pw() {//비밀번호 실시간 확인
		document.addEventListener('keyup', pw2);

		if (document.getElementById('pw').value == document
				.getElementById('pw2').value) {
			document.getElementById('check').innerHTML = '';
		} else {
			document.getElementById('check').innerHTML = '<font color=red>비밀번호가 일치하지 않습니다.</font>';
		}
	} 
	
	
	
</script>

</html>