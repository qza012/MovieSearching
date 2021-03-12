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
		<h3>댓글 리포트</h3>
		<hr/>
		<table>
		<tr>
			<th>신고번호</th><th>댓글번호</th><th>리뷰번호</th><th>사유</th><th>신고한 회원 ID</th><th>신고당한 회원 ID</th><th>신고날짜</th><th>처리 유무</th>
		</tr>
		<c:forEach items="${reportList }" var="report" varStatus="status">
		<tr>
			<td>${report.idx }</td>
			<td>${report.report_idx }</td>
			<td>${commentList[status.index].review_idx }</td>
			<td>${report.content }</td>
			<td>${report.report_id}</td>
			<td>${commentList[status.index].id }</td>
			<td>${report.reg_date }</td>
			<td id="${report.idx }">${report.complete }</td>
			<td>
				<c:if test="${report.complete == 'y' || report.complete == 'Y'}">
					<button value="${report.idx }">처리중</button>
				</c:if>
				<c:if test="${report.complete == 'n' || report.complete == 'N'}">
					<button value="${report.idx }">처리완료</button>
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
				,url:'toggleReportCommentComplete'
				,data:{'idx' : this.value}
				,dataType:'JSON'
				,success:function(data) {
					
					if(data.complete == "Y") {
						flag.html("Y");
						button.html("처리 중");
					} else {
						flag.html("N");
						button.html("처리 완료");
					}
					
				},error:function(e) {
					console.log("처리 중/처리 완료 버튼 비동기 에러");
				}
			})				
		})
	</script>
</html>