<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=yes">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<style>
table {
	width: 100%;
	color: white;
}

table, td, th {
	border-top: 1px solid lightgray;
	border-radius: 1px solid lightgray;
	border-collapse: collapse;
}

.button {
	width: 100%;
	text-align: center;
}

textarea {
	width: 99%;
	height: 600px;
	resize: none;
}

.title {
	width: 100%;
	text-align: center;
}
</style>
</head>
<body>
	<jsp:include page="../movie/include.jsp" />
	<div id="basic" class="basic">
		<div id="container">
			<div id="content">
				<div class="movie_main">
					<h3 class="title" style="color: white;">리뷰 작성하기</h3>
					<table>
						<tr>
							<th>제목</th>
							<td><input type="text" id="subject" style="width: 80%;" /></td>
							<th>작성자</th>
							<td><input type="text" id="id"
								value="${sessionScope.myLoginId}" style="width: 80%;" readonly /></td>
						</tr>

						<tr>
							<th>영화제목</th>
							<td><input type="hidden"  value="${movieCode}" id="movieCode" /> 
							<input type="text" id="movieName" value="${movieName}"  style="width: 80%;" readonly/> 
							<input type="button" value="검색" onclick="movieSearchOpen()" /></td>

							<th>평점</th>
							<td><select style="width: 20%;" id="score" class="star">
									<option value="0"></option>
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
							</select></td>
						</tr>

						<tr>
							<td colspan="4"><textarea id="reviewContent"></textarea></td>
						</tr>

					</table>
					<div class="button">
						<input type="button" id="save" value="저장" /> <input type="button"
							value="취소" onclick="cancel()" />
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
$("#save").click(function(){
	
	var subject = $("#subject").val();
	var id = $("#id").val();
	var movieCode = $('#movieCode').val();
	var movieName = $("#movieName").val();
	var score = $("#score").val();
	var content = $('#reviewContent').val(); 
	
	console.log(subject+" / "+id + " / " + movieCode + " / " + movieName + " / " + score + " / " + content);

	if(subject==""){
		alert("제목을 입력해주세요.");
		$("#subject").focus();
	}else if(id==""){
		alert("로그인을 해주세요.");
	}else if(movieCode=="" || movieName==""){
		alert("적합한 영화를 선택해주세요.");
		$("#movieName").focus();
	}else if(score==0){
		alert('평점을 선택해주세요.');
		$("#score").focus();
	}else if(content==""){
		alert('내용을 입력해주세요.');
		$("#content").focus();
	}else{
		$.ajax({ 
			type:'post' 
			,url:'../reviewWrite' 
			,data:{
				'subject':subject,
				'id':id,
				'movieCode':movieCode,
				'movieName':movieName,
				'score':score,
				'content':content
			}
			,dataType: 'json' 
			,success: function(data){
				console.log(data);
				console.log('data.success');
				if(data.success == 1){
					alert(data.msg);
					location.href = "/MovieSearching/reviewList";
				}else{
					alert('리뷰 작성에 실패했습니다.');
				}
			}
			,error: function(e){
				console.log(e);
			}
		});
	}
});

function movieSearchOpen(){
	var movieName = $("#movieName").val();
	window.open("/MovieSearching/reviewMovieSearch?page=1&subName="+movieName, "report", "width=1000, height=600, left=300, top=100");
}

function cancel(){
	var check = confirm('이미 작성한 내용은 모두 지워집니다. 취소하시겠습니까?');
	if(check){
		location.href='/MovieSearching/reviewList'
	}
}
</script>
</html>