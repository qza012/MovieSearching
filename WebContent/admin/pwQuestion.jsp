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
				padding: 5px 10px;
			}
		</style>
	</head>
	<body>
		<h3>질문리스트</h3>
		<hr/>
		<table>
		<tr>
			<th>순번</th><th>질문내용</th>
		</tr>
		<c:forEach items="${list }" var="question">
		<tr>
			<td>${question.idx }</td>
			<td>
				<a id="${question.idx}">${question.content }</a>
			</td>
			<td>
				<button value="${question.idx}">변경</button>
			</td>
		</tr>
		</c:forEach>
		</table>
	</body>
	<script>
	
		// 팝업으로 전달할 값
		var idx;
		var questionBox;
		
		$('button').click(function() {
			idx = this.value;
			questionBox = $('#'+idx);

			window.open("pwQuestionPopUp.jsp", "_blank", "height=300px, width=300px");
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
					console.log(data.content);
					
					questionBox.html(data.content);
					
					/* 실시간으로 바뀌게 수정해야함. */
					
					/* if(data.disable == "Y") {
						flag.html("Y");
						button.html("비활성화");
					} else {
						flag.html("N");
						button.html("활성화");
					} */
	
				},error:function(e) {
					console.log("변경 버튼 비동기 에러");
				}
			})	
		}
	</script>
</html>