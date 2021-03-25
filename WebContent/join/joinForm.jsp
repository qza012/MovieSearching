<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
<title>회원가입</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<style>

div{
	margin : 5px;
	color :lightgray;
}
h2{
	text-align : center;
	
}
table{
		margin : auto;
		margin-top : 100px;
	}
label{
	text-align : center;
}
:focus{
    	outline-color: black;
    }
#img,#img2{
	display : none;
	padding : 0px 5px;
	height : 18px;
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
									<h2>회원가입</h2>
								</td>
							</tr>
							<tr>
								<td>
									<div>
										<label>아이디</label><br> <input type="text" name="id" id="id" />
										<img src="https://img.icons8.com/android/24/000000/checkmark.png" id="img" />
										<input type="button" value="중복 확인" id="idChk" /><!--  <span
											id="id_span"></span> <span id="fail"></span> -->
									</div>
				
									<div>
										<label>비밀번호</label><br>
										<input type="password" name="pw" id="pw" onchange="check_pw()" width="400px"/>
				
									</div>
									<div>
										<label>비밀번호 확인</label><br> <input type="password" name="pw2"
											id="pw2" onkeyup="check_pw()" /><span id="check"></span>
									</div>
				
				
				
									<div>
										<lable>비밀번호 찾기 질문</lable>
										<br> <select id="pw_q">
											<!-- <option value="">선택</option> -->
										<c:forEach items="${questionList}" var="questionList" >
											
											<option value="${questionList.idx}">${questionList.content}</option>
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
										<label>나이</label><br> <input type="number" min="1" max="130"
											name="age" id="age" />
									</div>
									<div>
										<label>성별</label> <input type="radio" name="gender" value="male"
											id="male" /> 남 <input type="radio" name="gender" value="female"
											id="female" /> 여
									</div>
									<div>
										<label>이메일</label><br>
										<input type="text" name="email" id="email_id" size="10px"/>@
										
										<span id="email_span"></span>
										 <select
											name="email_sel" id="email_sel" onchange="change_email()">
											<option value="선택" >선택</option>
											<option value="직접 입력">직접 입력</option>
											<option value="naver.com">naver.com</option>
											<option value="gmail.com">gmail.com</option>
											<option value="daum.net">daum.net</option>
											<option value="nate.com">nate.com</option>
										</select>
										<img src="https://img.icons8.com/android/24/000000/checkmark.png" id="img2" />
										<input type="button" value="중복 확인" id="emailChk" />
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
										<input type="submit" value="회원가입" onclick="checkAll()" id="join"/>
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
$("button").remove("#btn2");
var idChk = false;//ID 중복 체크 여부
var emailChk = false;//이메일 중복체크
var re = /^[a-zA-Z0-9]{4,15}$/; //ID 유효
var re2 = /^[a-zA-Z0-9!@#$%^*+=-]{4,15}$/; //PW 유효
var re3 = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/; //이름 유효
var re4 = /^[0-9]{1,3}$/; //나이 유효
var re5 = /^[a-z0-9]{4,50}$/; //email 유효	 


$(function(){
	$('#id').change(function(){
		$('#img').hide();
		$('#idChk').show();
		idChk = false;
	});

	 $("#idChk").click(function(){
		
			$.ajax({
				type:'get'
				,url:'idChk'
				,data:{"id":$("#id").val()}
				,dataType:'JSON'
				,success:function(obj){
					console.log(obj);
					if(!re.test(id.value)) {
					 alert("아이디는 4~15자 이내의 영문 대소문자와 숫자로만 입력");
					 id.focus(); 
					 idChk=false;
					}else{
						if(obj.use==false){
							alert('이미 사용중인 아이디 입니다.');
							$("#id").val('').focus();
							
						}else if(obj.use){
							alert('사용할 수 있는 아이디 입니다.');
							$('#img').show();
							$('#idChk').hide();
							idChk= true;
						}
					}
					}
				
				,error:function(e){
					console.log(e);
				}
			});				
	});	 
});	
	
$(function(){
	$('#email_id').change(function(){
		$('#img2').hide();
		$('#emailChk').show();
		emailChk = false;
	});
	$('#email_sel').change(function(){
		$('#img2').hide();
		$('#emailChk').show();
		emailChk = false;
	});

	 $("#emailChk").click(function(){
		 if(email_id.value == "") {//@이전 입력 X
				alert("이메일을 입력하세요.");
				email_id.focus();
			}else if(!re5.test(email_id.value)) {
				 alert("이메일을 확인해주세요.");
				 id.focus(); 
		    } else if (email_sel.value == "선택") {//@이후 입력 X 
				alert("이메일 주소를 확인해주세요.");
				email_sel.focus();
			}
		    else if(email_sel.value=="직접 입력"){
			$.ajax({
				type:'get'
				,url:'emailChk'
				,data:{
					"email_id":$("#email_id").val(),
					"email_sel":$("#email_add").val()
					}
				,dataType:'JSON'
				,success:function(obj){
					console.log(obj);
					if (!re5.test(email_id.value)) {
						alert("이메일 주소를 확인해주세요.");
						email_sel.focus();
					}else{
						if(obj.use==false){
							alert('이미 사용중인 email 입니다.');
							$("#email_id").val('').focus();
							
						}else if(obj.use){
							alert('사용할 수 있는 email 입니다.');
							$('#img2').show();
							$('#emailChk').hide();
							emailChk= true;
						}
					}
				}
					
				
				,error:function(e){
					console.log(e);
				}
			});	
		}else{
			$.ajax({
				type:'get'
				,url:'emailChk'
				,data:{
					"email_id":$("#email_id").val(),
					"email_sel":$("#email_sel").val()
					}
				,dataType:'JSON'
				,success:function(obj){
					console.log(obj);
					
						if(obj.use==false){
							alert('이미 사용중인 email 입니다.');
							$("#email_id").val('').focus();
							
						}else if(obj.use){
							alert('사용할 수 있는 email 입니다.');
							$('#img2').show();
							$('#emailChk').hide();
							emailChk= true;
						}
					}
					
				
				,error:function(e){
					console.log(e);
				}
			});	
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
		}else if(!re.test(id.value)) {
			 alert("아이디는 4~15자 이내의 영문 대소문자와 숫자로만 입력");
			 id.focus(); 
	    }else if (idChk==false) {
			alert("아이디 중복체크를 해주세요.");
			id.focus();
		}else if (pw.value == "") {
			alert("비밀번호를 입력하세요.");
			pw.focus();
		}else if(!re2.test(pw.value)) {
			 alert("비밀번호는 4~15자 이내의 영문 대소문자와 숫자,특수문자 조합이여야 합니다");
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
		}else if(!re3.test(name.value)) {
			 alert("이름은 한글만 가능합니다.");
			 name.focus(); 
	    }else if(age.value == "") {
			alert("나이를 입력하세요.");
			age.focus();
		}else if(!re4.test(age.value)) {
			 alert("나이를 확인해주세요.");
			 age.focus(); 
	    }else if(!female.checked && !male.checked) {//둘 다 선택 X
			alert("성별을 선택해주세요.");
			male.focus();
		}else if(email_id.value == "") {//@이전 입력 X
			alert("이메일을 입력하세요.");
			email_id.focus();
		}else if(!re5.test(email_id.value)) {
			 alert("이메일을 확인해주세요.");
			 id.focus(); 
	    }else if (emailChk==false) {
			alert("이메일 중복체크를 해주세요.");
			email_id.focus();
		} else if (email_sel.value == "선택") {//@이후 입력 X 
			alert("이메일 주소를 확인해주세요.");
			email_sel.focus();
		}else if(email_sel.value=="직접 입력"){
			if ((email_add.value == "naver.com") || (email_add.value == "gmail.com")
					|| (email_add.value == "nate.com") || (email_add.value == "daum.net")) {
				
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
						"email_sel":$("#email_add").val()
						}
					,dataType:'JSON'
					,success:function(obj){
						console.log(obj.use);
						location.href="join/index.jsp";
					}
					,error:function(e){
						console.log(e);
					}
				});	
			} else {
				alert("올바른 도메인을 입력해주세요.");
				email_add.focus();
			}
		
			
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
					"email_sel":$("#email_sel").val()
					}
				,dataType:'JSON'
				,success:function(obj){
					console.log(obj.use);
					location.href="join/index.jsp";
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
		$('#email_span').html('<input type="text" name="email_add" id="email_add"/>');
			
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