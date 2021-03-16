<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>회원 목록</title>
		<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
	</head>
<style>
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
					<c:forEach items="${search_list}" var="movie">
						<a href="moviedetail?movieCode=${movie.movieCode}">
							<img src="${movie.posterUrl}" style="width: 159px; height: 280px;" alt="${movie.movieName}">
						</a>
					</c:forEach>
				</div>
				<div class="pageArea">
					<span>
						<c:if test="${currPage == 1}">이전</c:if>
						<c:if test="${currPage > 1}"><a href="movieSearch?search=${search}&keyWord=${keyWord}&page=${currPage-1}">이전</a></c:if>
					</span>
					<span id="page">${currPage}</span>
					<span>
						<c:if test="${currPage == maxPage}">다음</c:if>
						<c:if test="${currPage < maxPage}"><a href="movieSearch?search=${search}&keyWord=${keyWord}&page=${currPage+1}">다음</a></c:if>
					</span>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
</script>
</html>