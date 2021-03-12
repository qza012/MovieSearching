<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.mvc.movie.dto.*"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=yes">
<title>영화</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<style>
td {
	width: 320px;
}

th {
	width: 450px;
	height: 500px;
}

table, th, td {
	border: 1px solid black;
	border-collapse: collapse;
	padding: 5px 10px;
	text-align: left;
	color: white;
}
</style>
</head>
<body>
	<jsp:include page="include.jsp" />
	<div id="basic" class="basic">
		<div id="container">
			<div id="content">
				<div class="movie_main">
					<a class="like">좋아요</a>
					<ul
						style="position: absolute; width: 100%; height: 100%; left: 0%; z-index: 1; display: block;">
						<li>
							<table>
								<tr>
									<th rowspan="5">${movie.youtubeUrl}</th>
									<td>제목 : ${movie.movieName}</td>
								</tr>
								<tr>
									<td>장르 : ${movie.genre}</td>
								</tr>
								<tr>
									<td>나라 : ${movie.country}</td>
								</tr>
								<tr>
									<td>개봉일 : ${movie.openDate}</td>
								</tr>
								<tr>
									<td>감독 : ${movie.director}</td>
								</tr>
							</table>
						</li>
					</ul>
				</div>
				<h3 style="padding-left: 40px">이 영화의 최신 리뷰</h3>
				<div class="review">
					<ul>
						<li>
							<table>
								<c:forEach items="${review}" var="review">
									<tr>
										<td>${review.idx}</td>
										<td class="reviewDetail" onclick="location.href='./reviewDetail?Idx=${review.idx}'">${review.subject}</td>
										<td class="movieDetail" onclick="location.href='#' ">${review.movieName}</td>
										<td>${review.score}</td>
										<td class="memberDetail" onclick="location.href='#' ">${review.id}</td>
										<td>${review.cntLike}</td>
										<td>${review.reg_date}</td>
									</tr>
								</c:forEach>
							</table>
							<div class="button">
								<input type="button" value="리뷰 작성" onclick="location.href='./reviewWrite.jsp'">
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	
</script>
</html>