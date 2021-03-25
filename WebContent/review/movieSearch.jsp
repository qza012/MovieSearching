<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<script src="https://kit.fontawesome.com/abf52b8f21.js"></script>
<style>
table {
	width: 100%;
}

table, th, td {
	border-top: 1px solid lightgray;
	border-radius: 1px solid lightgray;
	border-collapse: collapse;
	text-align: center;
}

th, td {
	padding: 5px;
}

.movie {
	background-color: tomato;
}

.search {
	margin-bottom: 10px;
	text-align: center;
}

#movieName {
	width: 50%;
}

div.paging {
	width: 100%;
	text-align: center;
}

a:link, a:hover, a:visited, a:active {
	color: black;
	text-decoration: none;
}
</style>
</head>
<body>
	<form action="reviewMovieSearch?page=1" method="POST">
		<div class="search">
			<input type="text" name="subName" value="${subName}"
				placeholder="영화제목으로 검색" /> <input type="submit" value="검색" />
		</div>
	</form>

	<table>
		<c:if test="${maxPage != 0}">
			<tr>
				<th style="width: 37%;">영화제목</th>
				<th style="width: 10%;">장르</th>
				<th style="width: 20%;">감독</th>
				<th style="width: 7%;">국가</th>
				<th style="width: 8%;">개봉일</th>
			</tr>
		</c:if>
		<c:if test="${maxPage == 0}">
			<p style="text-align: center;">검색결과가 없습니다.</p>
		</c:if>

		<c:forEach items="${movie}" var="movie">
			<tr style="cursor: pointer;"
				onclick="movieChoice(${movie.movieCode})">
				<td>${movie.movieName}</td>
				<td>${movie.genre}</td>
				<td>${movie.director}</td>
				<td>${movie.country}</td>
				<td>${movie.openDate}</td>
			</tr>
		</c:forEach>
	</table>

	<div class="paging">
		<span> <c:if test="${currPage>1}">
				<a href="reviewMovieSearch?subName=${subName}&page=${currPage-1}"><i
					class="fas fa-angle-left"></i></a>
			</c:if> <c:if test="${currPage == 1 && maxPage != 0}">
				<i  style="color: gray;" class="fas fa-angle-left"></i>
			</c:if>
		</span>

		<c:set var="startNum" value="${currPage-(currPage-1)%10}" />

		<span id="pageArea"> <c:forEach begin="0" end="9" var="i">
				<c:if test="${startNum+i <= maxPage}">
					<c:if test="${startNum+i != currPage}">
						<a href="reviewMovieSearch?subName=${subName}&page=${startNum+i}">${startNum+i}
						</a>
					</c:if>
					<c:if test="${startNum+i == currPage}">
						<b><a
							href="reviewMovieSearch?subName=${subName}&page=${startNum+i}">${startNum+i}
						</a></b>
					</c:if>
				</c:if>
			</c:forEach>
		</span> <span> <c:if test="${currPage < maxPage}">
				<a href="reviewMovieSearch?subName=${subName}&page=${currPage+1}"><i
					class="fas fa-angle-right"></i></a>
			</c:if> <c:if test="${currPage == maxPage}">
				<i  style="color: gray;" class="fas fa-angle-right"></i>
			</c:if>
		</span>
	</div>
</body>
<script>
function movieChoice(movieCode){
	$.ajax({
		type:'post' 
		,url:'./reviewMovieChoice' 
		,data:{
			'movieCode':movieCode,
		}
		,dataType: 'json' 
		,success: function(data){
			console.log(data);
			if(data.haveReview == 1){
				alert("이미 리뷰를 작성한 영화입니다.");
			}else{
				console.log(data.movieCode);
				if(data.success == 1){
					opener.document.getElementById("movieCode").value=data.movieCode;
					opener.document.getElementById("movieName").value=data.movieName;
					window.close();
				}
			}	
		}
		,error: function(e){
			console.log(e);
		}
	});
}
</script>
</html>