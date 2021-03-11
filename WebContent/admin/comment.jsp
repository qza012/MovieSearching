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
		<h3>댓글리스트</h3>
		<hr/>
		<table>
		<tr>
			<th>댓글번호</th><th>리뷰 게시글 번호 </th><th>내용</th><th>작성자아이디</th><th>작성날짜</th><th>삭제여부</th>
		</tr>
		<c:forEach items="${list }" var="comment">
		<tr>
			<td>${comment.idx }</td>
			<td>${comment.reviewIdx }</td>
			<td>${comment.content }</td>
			<td>${comment.id }</td>
			<td>${comment.regDate }</td>
			<td>${comment.delType }</td>
			<td>
				<c:if test="${comment.delType == 'y' || comment.delType == 'Y'}">
					<button value="${comment.idx }">삭제취소</button>
				</c:if>
				<c:if test="${member.delType == 'n' || comment.delType == 'N'}">
					<button value="${comment.idx }">삭제</button>
				</c:if>
			</td>
		</tr>
		</c:forEach>
		</table>
	</body>
	<script>
		var msg = "${msg}";
		if(msg != "") {
			alert(msg);
		}
		
		$('button').click(function() {
	
			$.ajax({
				type:'POST'
				,url:'toggleCommentDelType'
				,data:{'idx' : this.value}
				,dataType:'JSON'
				,success:function(data) {
					//console.log(data);
					
					if(data.DelType == "Y") {
						$(this).html("비활성화");
					} else {
						$(this).html("활성화");
					}
					location.reload(true);
				},error:function(e) {
					console.log("활성화/비활성화 버튼 비동기 에러");
				}
			})				
		})
	</script>
</html>