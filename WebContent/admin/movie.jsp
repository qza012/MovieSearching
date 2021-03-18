<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
		<title>Insert title here</title>
		<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
		<script src="https://kit.fontawesome.com/abf52b8f21.js"></script>
			<style>
				/*table {
					width: 100%;
					color: white;
					font-size: 13px;
				}
				
				table, th, td {
					border-top: 1px solid lightgray;
					border-radius: 1px solid lightgray;
					border-collapse: collapse;
					text-align: center;
				}
				
				th, td {
					padding: 10px;
				}
				
				 select{
					width : 100px;
					height : 26px;
					margin-right: 8px ;
				}
				.searchInput{
					width : 320px;
					height : 20px;
				}
				input[type='submit']{
					width : 80px;
					height : 26px;
					margin-top :10px;
					margin-left: 8px;
				}
				#back, #next, #page {
		            display: inline-block;
		            width: 30px;
		            height: 25px;
		            border: 1px solid #cdcdcd;
		            color: #000000;
		            font-size: 11px;
		            border-collapse: collapse;
		            line-height:25px;
		            margin-top : 50px;
       		 	}
       		 	#back{
       		 		border-bottom-left-radius: 4px;
            		border-top-left-radius: 4px;
       		 	}
       		 	#page{
					border-radius: 0px;	
					font-weight: bold;
					margin-right:-5px;
					margin-left:-5px;
				}
       		 	#next{
       		 		border-bottom-right-radius: 4px;
            		border-top-right-radius: 4px;
       		 	}
				
				a:visited,a:link{
					color: #000000;
       			}
				h3{
					padding: 40px 180px 0px;
				}*/
				
			</style>
	</head>
	<body>
	<jsp:include page="../movie/include.jsp" />
	<div id="basic" class="basic">
		<div id="container">
			<div id="content">
				<div class="movie_main">
					<input id="storeCurStandard" type="hidden" value="${standard}"/>
					<input id="storeCurKeyWord" type="hidden" value="${keyWord}"/>
					<h3>영화 관리</h3>
					<div align="center">
						<form action="movieList" method="GET">
						    <select class="standard" name="standard" onchange=changeSearchInput(this.value)>
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
					<hr/>
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
						<%-- <td id="${movie.movieCode}_youtute">${movie.youtubeUrl }</td> --%>
						<td>
							<a id="${movie.movieCode}_youtube">${movie.youtubeUrl }</a>
						</td>
						<td id="${movie.movieCode}_poster">${movie.posterUrl }</td>
						<td>
							<button id="youtube" value="${movie.movieCode }">예고편 수정</button>
						</td>
						<td>
							<button id="poster" value="${movie.movieCode }">포스터 수정</button>
						</td>
					</tr>
					</c:forEach>
					</table>
					<div align="center">
						<span id="back">
							<c:if test="${curPage == 1 }">이전</c:if>
							<c:if test="${curPage > 1 }">
								<a href="javascript:prevFunc();">이전</a>
							</c:if>
						</span>
						<span id="page">${curPage }</span>
						<span id="next">
							<c:if test="${curPage == maxPage }">다음</c:if>
							<c:if test="${curPage < maxPage }">
								<a href="javascript:nextFunc();">다음</a>
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
		});
		
		// 팝업 창으로 전달할 값.
		var urlBoxSelector;
		var IsYoutubeUrl = true;
		var boxId;
		var movieCode;
		
		$('button').click(function() {		
			var moveUrl;
			
			if(this.id == 'youtube') {
				boxId = "#"+this.value + "_youtube";
				urlBoxSelector = $(boxId);
				IsYoutubeUrl = true;
				movieCode = this.value;
				
				moveUrl = "movieYoutubeUrl.jsp";
			} else if(this.id == 'poster'){
				boxId = "#"+this.value + "_poster";
				urlBoxSelector = $(boxId);
				IsYoutubeUrl = false;
				movieCode = this.value;
				
				moveUrl = "moviePosterUrl.jsp";
			}
			
			window.open(moveUrl, "_blank", "height=100px, width=470px");
		});
		
		function updateUrl(url, boxSelector) {
			//console.log("호출 성공");

			if(IsYoutubeUrl) {

				$.ajax({
					type:'GET'
					,url: 'updateYoutubeUrl'
					,data:{'movieCode' : movieCode,
							'youtubeUrl' : url}
					,dataType:'JSON'
					,success:function(data) {
						if(data.youtubeUrl == null) {
							$(boxId).html("");
						} else {
							$(boxId).html(data.youtubeUrl);
						}				
					},error:function(e) {
						console.log("url 버튼 비동기 에러");
					}
				})
			} else {
				$.ajax({
					type:'GET'
					,url: 'updatePosterUrl'
					,data:{'movieCode' : movieCode,
							'posterUrl' : url}
					,dataType:'JSON'
					,success:function(data) {
						if(data.posterUrl == null) {
							$(boxId).html("");			
						} else {
							$(boxId).html(data.posterUrl);
						}
					},error:function(e) {
						console.log("url 버튼 비동기 에러");
					}
				})
			}
		}

		
		// 셀렉박스 값에 따라 inputBox 교체
		// input박스 자동 교체.
		function changeSearchInput(value) {
			var searchInput = $(".searchInput");
			//console.log(value);
			
			switch(value) {
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
		};

		// next 함수
		function nextFunc() {
			var standard = $("#storeCurStandard").val();
			var keyWord = $("#storeCurKeyWord").val();

			location.href="movieList?curPage=${curPage + 1}&standard=" + standard + "&keyWord=" + keyWord;
		}
		
		// prev 함수
		function prevFunc() {
			var standard = $("#storeCurStandard").val();
			var keyWord = $("#storeCurKeyWord").val();

			location.href="movieList?curPage=${curPage - 1}&standard=" + standard + "&keyWord=" + keyWord;
		}
		
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