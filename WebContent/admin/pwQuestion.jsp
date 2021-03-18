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
				padding: 10px 10px;
			}
			#basic {
				color: white;
			}
		</style>
	</head>
	<body>
	<jsp:include page="../movie/include.jsp" />
	<div id="basic" class="basic">
		<div id="container">
			<div id="content">
				<div class="movie_main">
					<h3>질문리스트</h3>
					<div>
						<button value="move" onclick="location.href='memberDisableList'">회원 비활성화 관리</button>
						<button value="move">비밀번호 찾기 질문 관리</button>
					</div>
					<hr/>
					<table>
					<tr>
						<th>순번</th><th>질문내용</th>
					</tr>
					<c:forEach items="${list }" var="question">
					<tr>
						<td>${question.idx }</td>
						<td>
							<span id="${question.idx}">${question.content }</span>
						</td>
						<td>
							<button value="${question.idx}">변경</button>
						</td>
					</tr>
					</c:forEach>
					</table>
				</div>
			</div>
		</div>
	</div>
	</body>
	<script>
	
		// 팝업으로 전달할 값
		var idx;
		var questionBox;
		
		$('button').click(function() {
			if(this.value == "move") {
				return;
			}
			
			idx = this.value;
			questionBox = $('#'+idx);

			var popupX = (window.screen.width / 2) - (150 / 2);
			var popupY= (window.screen.height / 2) - (470 / 2);
			
			window.open("pwQuestionPopUp.jsp", "_blank", "height=150px, width=470px, left="+popupX+", top="+popupY);
		});
	
		// 내용 변경 비동기 버튼
		function updateContent(content) {
			
			$.ajax({
				type:'GET'
				,url:'updatePwQuestion'
				,data:{'idx' : idx,
						'content' : content}
				,dataType:'JSON'
				,success:function(data) {
					//console.log(data.content);
				
					questionBox.html(data.content);
				},error:function(e) {
					console.log("변경 버튼 비동기 에러");
				}
			})	
		}
	</script>
</html>