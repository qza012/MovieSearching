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
a:link, a:visited {
	color: white;
	text-decoration: none;
}

a:hover {
	text-decoration: underline;
}

.navi>li ul.navi_sub1 li a, .navi>li ul.navi_sub2 li a {
	display: block;
	overflow: hidden;
	width: 180px;
	height: 17px;
	margin-top: 5px;
}

body {
	background-color: gray;
	display: block;
	text-align: center;
	display: block;
}

li {
	list-style-type: none;
}

h3 {
	height: 15px;
}

.movie_main {
	overflow: hidden;
	position: relative;
	height: 600px;
	padding-top: 68px;
	border: 1px solid black;
}

#basic {
	position: relative;
	min-height: 100%;
	margin: 0 auto;
	padding-top: 7px;
}

.basic {
	width: 1095px;
}

#container {
	background-color: #212121;
	margin-top: 10px;
	margin-left: 200px;
}

#login {
	text-align: right;
}

#header {
	font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
	font-size: 15px;
	position: fixed;
	top: 0;
	bottom: 0;
	z-index: 10000;
	width: 180px;
	background: #212121;
	min-height: 100%;
	text-align: left;
}
</style>
</head>

<body>
	<div id="basic" class="basic">
		<div id="login"></div>
		<div id="header" style="bottom: 0px;">
			<div id="naviscroll" class="naviscroll" style="width: 180px;">
				<div class="naviscroll-box" style="width: 180px;">
					<div class="naviscroll-content" style="top: 0px; width: 180px;">
						<div class="naviscroll-in">
							<ul class="navi">
								<li><a href="home" id="moviehome" title="영화홈"><h3>영화 홈</h3></a></li>
								<li><a href="list" id="movielist" title="영화 리스트"><h3>영화리스트</h3></a></li>
								<li><a href="#" id="reviewboard" title="리뷰 게시판"><h3>리뷰게시판</h3></a></li>
								<li><a href="#" id="userlist" title="회원 리스트"><h3>회원리스트</h3></a></li>
								<li><a href="#" id="moviedownload" title="영화 다운로드"><h3>영화다운로드</h3></a></li>
								<li><a href="#" id="mypage" title="마이페이지"><h3>마이페이지</h3></a>
									<ul class="navi_sub1" style="display: none;">
										<li><a href="#" title="회원정보수정"> 회원정보수정 </a></li>
										<li><a href="#" title="회원탈퇴"> 회원탈퇴 </a></li>
										<li><a href="#" title="작성한 리뷰"> 작성한 리뷰 </a></li>
										<li><a href="#" title="좋아요한 영화"> 좋아요한 영화 </a></li>
										<li><a href="#" title="좋아요한 리뷰"> 좋아요한 리뷰 </a></li>
										<li><a href="#" title="팔로워"> 팔로워 </a></li>
										<li><a href="#" title="팔로잉"> 팔로잉 </a></li>
										<li><a href="#" title="알람"> 알람 </a></li>
									</ul>
								</li>
								<li><a href="#" id="adminpage" title="관리자 페이지"><h3>관리자페이지</h3></a>
									<ul class="navi_sub2" style="display: none;">
										<li><a href="#" title="영화관리"> 영화관리 </a></li>
										<li><a href="#" title="회원관리"> 회원관리 </a></li>
										<li><a href="#" title="리뷰관리"> 리뷰관리 </a></li>
										<li><a href="#" title="댓글관리"> 댓글관리 </a></li>
										<li><a href="#" title="신고관리"> 신고관리 </a></li>
									</ul>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="container">
			<div id="content">
				<select>
					<option>영화제목</option>
					<option>감독명</option>
					<option>배우명</option>
				</select>
				<div class="movie_main">
					<c:forEach items="${list}" var="movie3">
						<img src="${movie3.posterurl}" class="movie_01_poster">
					<div class="movie_01">
						<a href="detail?moviename=${movie3.moviename}">${movie3.moviename}</a>
					</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	var loginId = "${sessionScope.loginId}";

	if (loginId == "") {
		var content = "<button id='btn1'>로그인</button> <button id='btn2'>회원가입</button>"
		document.getElementById("login").innerHTML = content;
	} else {
		content = "환영합니다. " + loginId
				+ "님 <button id='btn3'>로그아웃</button> <button>알람</button>";
		document.getElementById("login").innerHTML = content;
	}

	$("#btn1").click(function() {
		location.href = "login.jsp";
	});

	$("#btn2").click(function() {
		location.href = "join.jsp";
	});

	$("#btn3").click(function() {
		location.href = "logout";
	});

	$("#mypage").click(function() {
		$(".navi_sub1").slideToggle('slow');
		$(".navi_sub1").css('display', 'block');
	});

	$("#adminpage").click(function() {
		$(".navi_sub2").slideToggle('slow');
		$(".navi_sub2").css('display', 'block');
	});
</script>

</html>