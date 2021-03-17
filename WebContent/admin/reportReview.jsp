<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
		<h3>리뷰 리포트</h3>
		<hr/>
		<div>
			<form action="reportReviewList" method="GET">
			    <select class="standard" name="standard">
			    	<option value="all">전체</option>
			        <option value="idx">신고 번호</option>
			        <option value="subject">리뷰제목</option>
			        <option value="content">사유</option>
			        <option value="report_id">신고한 회원ID</option>
			        <option value="id">신고당한 회원ID</option>
			        <option value="reg_date">신고날짜</option>
			        <option value="complete">처리 유무</option>
			    </select>
				<input class="searchInput" type="text" name="keyWord" value="${keyWord }" readonly/>
			    <input type="submit" value="검색"/>
			</form>
		</div>
		<table>
		<tr>
			<th>신고번호</th><th>리뷰번호</th><th>사유</th><th>신고한 회원 ID</th><th>신고당한 회원 ID</th><th>신고날짜</th><th>처리 유무</th>
		</tr>
		<c:forEach items="${reportList }" var="report" varStatus="status">
		<tr>
			<td>${report.idx }</td>
			<td><a href="#?idx=${report.report_idx }">${report.report_idx }</a></td>
			<td>${report.content }</td>
			<td>${report.report_id }</td>
			<td>${reviewList[status.index].id }</td>
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
		<div>
			<span>
				<c:if test="${curPage == 1 }">이전</c:if>
				<c:if test="${curPage > 1 }">
					<a href="javascript:prevFunc();">이전</a>
				</c:if>
			</span>
			<span id="page">${curPage }</span>
			<span>
				<c:if test="${curPage == maxPage }">다음</c:if>
				<c:if test="${curPage < maxPage }">
					<a href="javascript:nextFunc();">다음</a>
				</c:if>
			</span>
		</div>
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
				type:'GET'
				,url:'toggleReportReviewComplete'
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
		
		// 셀렉박스 값에 따라 inputBox 교체
		$('.standard').change(function(){
			var searchInput = $(".searchInput");
			console.log(this.value);
			
			switch(this.value) {
			case "all" :
				searchInput.replaceWith(
						"<input class='searchInput' type='text' name='keyWord' readonly/>"
				);
				break;
				
			case "idx" :
			case "subject" :
			case "content" :		
			case "id" :
			case "reg_date" :
				searchInput.replaceWith(
						"<input class='searchInput' type='text' name='keyWord'/>"
						);	
				break;
			case "del_type" :
			case "complete" :
				searchInput.replaceWith(
						"<select class='searchInput' name='keyWord'>"
				    	+"<option value='Y'>Y</option>"
				    	+"<option value='N'>N</option>"
				    	+"</select>"
				);
				break;
			}
		})
		
		// next 함수
		function nextFunc() {
			var standard = $(".standard").val();
			var keyWord = $(".searchInput").val();

			location.href="reportReviewList?curPage=${curPage + 1}&standard=" + standard + "&keyWord=" + keyWord;
		}
		
		// prev 함수
		function prevFunc() {
			var standard = $(".standard").val();
			var keyWord = $(".searchInput").val();

			location.href="reportReviewList?curPage=${curPage - 1}&standard=" + standard + "&keyWord=" + keyWord;
		}
		
		var selectBox = $(".standard");
		if("${standard}" == "") {
			selectBox.val("all").prop("selected", true);
		} else{
			selectBox.val("${standard}").prop("selected", true);
		}
		if(selectBox.val() != 'all') {
			$('.searchInput').removeAttr("readonly");
		} 
		
	</script>
</html>