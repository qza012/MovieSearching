<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
.search {
	text-align: center;
	margin-top: 10%;
}

input[type=text] {
	width: 500px;
	height: 22px;
}

select, input[type=submit] {
	height: 30px;
	width: 70px;
}
</style>
</head>
<body>
	<jsp:include page="include.jsp" />
	<div id="basic" class="basic">
		<div id="container">
			<div id="content">
				<div class="search">
					<form action="search" method="GET">
						<select class="select" name="search">
							<option value="영화제목">영화제목</option>
							<option value="감독명">감독명</option>
							<option value="배우명">배우명</option>
						</select> <select class="select" name="search">
							<option value="액션">액션</option>
							<option value="스릴러">스릴러</option>
							<option value="사극">사극</option>
							<option value="멜로/로맨스">멜로/로맨스</option>
							<option value="다큐멘터리">다큐멘터리</option>
							<option value="기타">기타</option>
						</select>
						<input type="text" value="${param.keyWord}" name="keyWord" placeholder="검색어를 입력하세요">
						<input type="submit" value="검색">
					</form>
				</div>
				<div class="movie_main">
					<c:forEach items="${list}" var="movie">
						<img src="${movie.posterUrl}" alt="movie_poster">>
						<a href="moviedetail?movieCode=${movie.movieCode}">${movie.movieName}</a>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	
</script>
</html>