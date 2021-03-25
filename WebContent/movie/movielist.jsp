<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=yes">
<title>영화</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<style>
.movie_main {
	overflow: hidden;
	position: relative;
	height: 600px;
	padding: 30px;
	text-align: center;
}

.search {
	text-align: center;
	padding-top: 10px;
}

input[type=text] {
	width: 500px;
	height: 22px;
}

select, input[type=submit] {
	height: 30px;
	width: 70px;
}

.pageArea {
	width: 100%;
	border-top: 1px solid black;
	text-align: center;
	padding: 10px 0px;
}

.pageArea span {
	font-size: 15px;
	border: 1px solid lightgray;
	padding: 2px 10px;
	margin: 5px;
	color: gray;
}

#page {
	font-weight: 600;
	color: red;
}
</style>
</head>
<body>
	<jsp:include page="include.jsp" />
	<div id="basic" class="basic">
		<div id="container">
			<div id="content">
				<form class="search" action="movieSearch" method="GET">
					<select class="select" name="search">
						<option value="movieName">영화제목</option>
						<option value="genre">장르</option>
						<option value="director">감독</option>
					</select>
					<input type="text" value="${keyWord}" name="keyWord" placeholder="검색어를 입력하세요">
					<input type="submit" value="검색">
				</form>
				<div class="movie_main">
					<ul style="position: absolute; width: 825px; height: 560px; left: 0%; display: block;">
						<li>
							<c:forEach items="${movie_list}" var="movie">
								<a href="moviedetail?movieCode=${movie.movieCode}">
									<img src="${movie.posterUrl}" style="width: 159px; height: 280px;" alt="${movie.movieName}">
								</a>
							</c:forEach>
						</li>
					</ul>
				</div>
				<div class="pageArea">
					<span>
						<c:if test="${currPage == 1}">이전</c:if>
						<c:if test="${currPage > 1}">
							<a href="movieList?page=${currPage-1}">이전</a>
						</c:if>
					</span>
					<span id="page">${currPage}</span>
					<span>
						<c:if test="${currPage == maxPage}">다음</c:if>
						<c:if test="${currPage < maxPage}">
							<a href="movieList?page=${currPage+1}">다음</a>
						</c:if>
					</span>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	
</script>
</html>