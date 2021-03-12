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
		<h3>리뷰 관리 </h3>
		<hr/>
		<table>
		<tr>
			<th>리뷰번호</th><th>제목</th><th>영화 제목</th><th>작성자 ID</th><th>작성날짜</th><th>삭제여부</th>
		</tr>
		<!-- key : reviewList, value : movieList-->
		<c:forEach items="${reviewList }" var="review" varStatus="status">
		<tr>
			<td>${review.idx }</td>
			<td>${review.subject }</td>
			<td>${movieList[status.index].movieName }</td>
			<td>${review.id}</td>
			<td>${review.reg_date }</td>
			<td id="${review.idx }">${review.del_type }</td>
			<td>
				<c:if test="${review.del_type == 'y' || review.del_type == 'Y'}">
					<button value="${review.idx }">삭제 취소</button>
				</c:if>
				<c:if test="${review.del_type == 'n' || review.del_type == 'N'}">
					<button value="${review.idx }">삭제</button>
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
			var button = $(this);
			var flag = $("#"+this.value);
			
			$.ajax({
				type:'POST'
				,url:'toggleRevieDelType'
				,data:{'idx' : this.value}
				,dataType:'JSON'
				,success:function(data) {
					
					if(data.del_type == "Y") {
						flag.html("Y");
						button.html("삭제 취소");
					} else {
						flag.html("N");
						button.html("삭제");
					}
					
				},error:function(e) {
					console.log("삭제/삭제 취소 버튼 비동기 에러");
				}
			})				
		})
	</script>
</html>