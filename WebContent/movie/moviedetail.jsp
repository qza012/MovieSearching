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
<script src="https://kit.fontawesome.com/abf52b8f21.js"></script>
<style>
td {
	height: 25px;
}

th {
	width: 100%;
	height: 440px;
}

table, th, td {
	width: 100%;
	padding-top: 2px;
	border-collapse: collapse;
	text-align: center;
	color: white;
}

.movie_main {
	overflow: hidden;
	position: relative;
	height: 600px;
	text-align: center;
}

.movie_main>ul {
	padding: 0px;
}

.review>ul {
	padding: 0px;
}

.review>ul li table tr td {
	width: auto;
	border: 5px solid white;
	border-collapse: collapse;
	height: 40px;
}

.review_Write {
	padding: 30px 40px;
	font-size: 15px;
	font-weight: bold;
	text-align: right;
}
</style>
</head>
<body>
	<jsp:include page="include.jsp" />
	<div id="container">
		<div id="content">
			<div class="movie_main">
				<ul style="position: absolute; width: 100%; height: 100%; left: 0%; z-index: 1; display: block;">
					<li>
						<table>
							<tr>
								<th>${movie.youtubeUrl}</th>
							</tr>
							<tr>
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
			<table>
				<tr>
					<c:if test="${sessionScope.myLoginId == null}">
						<td>
							<i style="font-size: 23px;" class="far fa-heart"></i>
						</td>
					</c:if>
					<c:if test="${sessionScope.myLoginId != null}">
						<c:if test="${movieLike == 1}">
							<td class="movieLike" style="cursor: pointer;" onclick="movieLike(${movie.movieCode})">
								<i style="color: red; font-size: 23px;" class="fas fa-heart"></i>
							</td>
						</c:if>
						<c:if test="${movieLike == 0}">
							<td class="movieLike" style="cursor: pointer;" onclick="movieLike(${movie.movieCode})">
								<i style="font-size: 23px;" class="far fa-heart"></i>
							</td>
						</c:if>
					</c:if>
				</tr>
				<tr><td id="movieLike_Count">${movieLike_Count}</td></tr>
			</table>
			<h3 style="padding-left: 40px; color: white;">이 영화의 최신 리뷰</h3>
			<div class="review">
				<ul>
					<li>
						<table>
							<tr>
								<td>번호</td>
								<td>제목</td>
								<td>평점</td>
								<td>작성자</td>
								<td>좋아요</td>
								<td>작성일</td>
							</tr>
							<c:forEach items="${review}" var="review">
								<tr>
									<td>${review.idx}</td>
									<td class="reviewDetail" onclick="location.href='/MovieSearching/reviewDetail?Idx=${review.idx}'">${review.subject}</td>
									<td>${review.score}</td>
									<td class="memberDetail" onclick="location.href='#'">${review.id}</td>
									<td>${review.cntLike}</td>
									<td>${review.reg_date}</td>
								</tr>
							</c:forEach>
						</table>
						<div class="review_Write">
							<input type="button" value="리뷰 작성" onclick="location.href='../movieReviewWriteForm?movieCode=${movie.movieCode}&movieName=${movie.movieName}' ">
						</div>
					</li>
				</ul>
			</div>
		</div>
	</div>
</body>
<script>
	function movieLike(movieCode){
		var id = "${sessionScope.myLoginId}";
		$.ajax({
			type:'post' 
			,url:'movieLike' 
			,data:{
				'id':id,
				'movieCode':movieCode
			}
			,dataType: 'json' 
			,success: function(data){
				console.log(data);
				if(data.success == 1){
					if(data.movieLikeState == 0){
						$('.movieLike').html('<i style="color: red; font-size: 23px;" class="fas fa-heart"></i>');
						$('#movieLike_Count').html(data.movieLike_Count);
					}else{
						$('.movieLike').html('<i style="font-size: 23px;" class="far fa-heart"></i>');
						$('#movieLike_Count').html(data.movieLike_Count);
					}
				}
			}
			,error: function(e){
				console.log(e);
			}
		});
	}
	var msg = "${msg}";
	if (msg != "") {
		alert(msg);
	}
</script>
</html>