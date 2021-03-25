<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset=UTF-8>
		<title>Insert title here</title>
		<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
		<style>
			table {
					width: 100%;
					color: white;
			}
			table, th, td{
				border: 1px solid black;
				border-collapse: collapse;
				padding: 5px 15px;
			}
			#basic {
				color: white;
			}
			#page{
				border-radius: 4px;	
				padding : 10px;
				margin : 10px;
			}
			#pageNum{
				padding : 10px;
				margin : 10px;
			}
			#curPageNum{
				padding : 10px;
				margin : 10px;
				font-weight: bold;
				font-size: 30px;
			}
		</style>
	</head>
	<body>
	<jsp:include page="../movie/include.jsp" />
	<div id="basic" class="basic">
		<div id="container">
			<div id="content">
				<div class="main">
					<input id="storeCurStandard" type="hidden" value="${standard}"/>
					<input id="storeCurKeyWord" type="hidden" value="${keyWord}"/>
					<h3>리뷰 관리 </h3>
					<hr/>
					<div align="center">
						<form action="reviewList" method="GET">
						    <select class="standard" name="standard" onchange=changeSearchInput(this.value)>
						    	<option value="all">전체</option>
						        <option value="idx">리뷰번호</option>
						        <option value="subject">제목</option>
						        <option value="id">아이디</option>
						        <option value="reg_date">작성날짜</option>
						        <option value="del_type">삭제여부</option>
						    </select>
							<input class="searchInput" type="text" name="keyWord" value="${keyWord }" readonly/>
						    <input type="submit" value="검색"/>
						</form>
					</div>
					<table>
					<tr>
						<th>리뷰번호</th><th>제목</th><th>영화 제목</th><th>작성자 ID</th><th>작성날짜</th><th>삭제여부</th>
					</tr>
					<c:forEach items="${reviewList }" var="review" varStatus="status">
					<tr>
						<td>${review.idx }</td>
						<td><a href="/MovieSearching/reviewDetail?Idx=${review.idx }">${review.subject }</a></td>
						<td><a href="/MovieSearching/movie/moviedetail?movieCode=${movieList[status.index].movieCode }">${movieList[status.index].movieName }</a></td>
						<td>${review.id}</td>
						<td>${review.reg_date }</td>
						<td id="${review.idx }">${review.del_type }</td>
						<td>
							<c:if test="${review.del_type == 'y' || review.del_type == 'Y'}">
								<button value="${review.idx }" onclick="toggleDel(this)">삭제 취소</button>
							</c:if>
							<c:if test="${review.del_type == 'n' || review.del_type == 'N'}">
								<button value="${review.idx }" onclick="toggleDel(this)">삭제</button>
							</c:if>
						</td>
					</tr>
					</c:forEach>
					</table>
					<div align="center">
						<span>
							<c:if test="${curPage == 1 }">이전</c:if>
							<c:if test="${curPage > 1 }">
								<a id='pageNum' href="javascript:pageMove('${curPage-1}');">이전</a>
							</c:if>
							<c:if test="${curPage - 4 > 1}">
								<a id='pageNum' href="javascript:pageMove(1);">1</a>
								...
							</c:if>
						</span>
						
						<span id="page">
	
						</span>
						
						<span>
							<c:if test="${curPage + 4 < maxPage}">
								...
								<a id='pageNum' href="javascript:pageMove('${maxPage}');">${maxPage}</a>
							</c:if>
							<c:if test="${curPage == maxPage }">다음</c:if>
							<c:if test="${curPage < maxPage }">
								<a id='pageNum' href="javascript:pageMove('${curPage+1}');">다음</a>
							</c:if>
						</span>
					</div>
				</div>
			</div>
		</div>
	</div>
	</body>
	<script>
		var msg = "${msg}";
		if(msg != "") {
			alert(msg);
		}
		
		// 최초 불러올 때 실행하는 함수들.
		$(document).ready(function() {
			staySelectBoxValue();
			changeSearchInput($(".standard").val());
			setPageNum();
		});
		
		// 삭제 버튼 비동기 통신
		function toggleDel(buttonObj) {
			var button = $(buttonObj);
			var flag = $("#"+buttonObj.value);
			
			$.ajax({
				type:'GET'
				,url:'toggleRevieDelType'
				,data:{'idx' : buttonObj.value}
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
		}
		
		// input박스 자동 교체.
		function changeSearchInput(value) {
			var searchInput = $(".searchInput");
			console.log(value);
			
			switch(value) {
			case "all" :
				searchInput.replaceWith(
						"<input class='searchInput' type='hidden' name='keyWord' readonly/>"
				);
				break;
				
			case "idx" :
			case "subject" :
			case "id" :		
			case "reg_date" :
				searchInput.replaceWith(
						"<input class='searchInput' type='text' name='keyWord'/>"
						);	
				break;
				
			case "del_type" :
				searchInput.replaceWith(
						"<select class='searchInput' name='keyWord'>"
				    	+"<option value='Y'>Y</option>"
				    	+"<option value='N'>N</option>"
				    	+"</select>"
				);
				break;
			}
		}
		
		// 페이지 이동 함수
		function pageMove(pageNum) {
			var standard = $("#storeCurStandard").val();
			var keyWord = $("#storeCurKeyWord").val();
			//console.log(pageNum);
			
			location.href="reviewList?curPage=" + pageNum + "&standard=" + standard + "&keyWord=" + keyWord;
		}
		
		// page 번호 매기기
		function setPageNum() {
			var page$ = $("#page");
			var resultHtml = "";
			
			for(var i=-4; i<5; ++i) {

				var curNum = ${curPage} + i;
				if(1 <= curNum && curNum <= ${maxPage}) {	
					if(i == 0) {
						resultHtml += "<b id='curPageNum'>" + curNum + "</b>";
					} else {
						resultHtml += "<a id='pageNum' href='javascript:pageMove(" + curNum + ")'>" + curNum + "</a>";					
					}
					
				}
			}

			page$.html(resultHtml);
		}
		
		// 셀렉트 박스 속성 유지시키기
		function staySelectBoxValue() {
			var selectBox = $(".standard");
			if("${standard}" == "") {
				selectBox.val("all").prop("selected", true);
			} else{
				selectBox.val("${standard}").prop("selected", true);
			}
			if(selectBox.val() != 'all') {
				$('.searchInput').removeAttr("readonly");
			}
		}
		
	</script>
</html>