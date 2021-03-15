<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
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
	<!-- 영화코드  | 영화이름 | 장르 |    개봉일    | 감독명 | 순위 | 예고편 | 포스터 -->
		<h3>영화 관리</h3>
		<hr/>
		<div>
			<form action="movieList" method="GET">
			    <select class="standard" name="standard">
			    	<option value="all">전체</option>
			    	<option value="movieCode">영화코드</option>
			        <option value="movieName">영화이름</option>
			        <option value="genre">장르</option>
			        <option value="director">감독명</option>
			    </select>
				<input class="searchInput" type="text" name="keyWord" value="${keyWord }" readonly/>
			    <input type="submit" value="검색"/>
			</form>
		</div>
		<table>
		<tr>
			<th>영화코드</th><th>영화이름</th><th>장르</th><th>개봉일</th><th>감독명</th><th>순위</th><th>예고편</th><th>포스터</th>
		</tr>
		<c:forEach items="${list }" var="movie">
		<tr>
			<td>${movie.movieCode }</td>
			<td>${movie.movieName }</td>
			<td>${movie.genre }</td>
			<td>${movie.openDate}</td>
			<td>${movie.director }</td>
			<td>${movie.rank }</td>
			<td id="${movie.movieCode} + '_youtute'">${movie.youtubeUrl }</td>
			<td id="${movie.movieCode} + '_poster'">${movie.posterUrl }</td>
			<td>
				<button id="youtube" value="${movie.movieCode }">예고편 수정</button>
			</td>
			<td>
				<button id="poster" value="${movie.movieCode }">포스터 수정</button>
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
		
		// 팝업 창으로 전달할 값.
		var urlBox = "";
		var standardVar = "";
		
		$('button').click(function() {
			if(this.id == 'youtube') {
				urlBox = $("#"+this.value + "_youtube");
				standardVar = $('.standard').val();
			} else if(this.id == 'poster'){
				urlBox = $("#"+this.value + "_poster");
				standardVar = $('.standard').val();
			}

			window.open("movieYoutubeUrl.jsp", "_blank", "height=300px, width=300px");
		});
		
		function updateYoutubeUrl(url, boxSelector) {
			console.log("호출 성공");
			/* var button = $(this);
			var urlBox = null;

			var strUrl = "";

			if(this.id == 'youtute') {
				strUrl = 'updateYoutubeUrl';
				urlBox = $("#"+this.value + "_youtube");
			} else if(this.id == 'poster'){
				strUrl = 'updatePosterUrl';
				urlBox = $("#"+this.value + "_poster");
			}
			
			$.ajax({
				type:'GET'
				,url: strUrl
				,data:{'idx' : this.value}
				,dataType:'JSON'
				,success:function(data) {
					if(this.id == 'youbute') {
						flag
					} else {
						
					}
					
					
					if(data.complete == "Y") {
						flag.html("Y");
						button.html("처리 중");
					} else {
						flag.html("N");
						button.html("처리 완료");
					}
					
				},error:function(e) {
					console.log("url 버튼 비동기 에러");
				}
			})  */
		}

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
				
			case "movieCode" :
			case "movieName" :
			case "genre" :		
			case "director" :	
				searchInput.replaceWith(
						"<input class='searchInput' type='text' name='keyWord'/>"
						);	
				break;
			}
		})

		// next 함수
		function nextFunc() {
			var standard = $(".standard").val();
			var keyWord = $(".searchInput").val();

			location.href="movieList?curPage=${curPage + 1}&standard=" + standard + "&keyWord=" + keyWord;
		}
		
		// prev 함수
		function prevFunc() {
			var standard = $(".standard").val();
			var keyWord = $(".searchInput").val();

			location.href="movieList?curPage=${curPage - 1}&standard=" + standard + "&keyWord=" + keyWord;
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