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
		<div>
			<form action="reviewList" method="GET">
			    <select class="standard" name="standard">
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
		})
		
		// next 함수
		function nextFunc() {
			var standard = $(".standard").val();
			var keyWord = $(".searchInput").val();

			location.href="reviewList?curPage=${curPage + 1}&standard=" + standard + "&keyWord=" + keyWord;
		}
		
		// prev 함수
		function prevFunc() {
			var standard = $(".standard").val();
			var keyWord = $(".searchInput").val();

			location.href="reviewList?curPage=${curPage - 1}&standard=" + standard + "&keyWord=" + keyWord;
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