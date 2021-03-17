<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
	margin-left: 0px;
}

body {
	background-color: gray;
	display: block;
}

li {
	list-style-type: none;
}

#basic {
	width: 1095px;
	position: relative;
	min-height: 100%;
	margin: 0 auto;
	padding-top: 7px;
}

#login {
	text-align: right;
}

#navi {
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

#container {
	background-color: #212121;
	margin-top: 10px;
	margin-left: 200px;
}

.movie_main {
	overflow: hidden;
	position: relative;
	height: 600px;
	padding: 30px;
}
</style>
</head>

<body>
	<div id="basic">
		<div id="login"></div>
		<div id="navi" style="bottom: 0px;">
			<div id="naviscroll" class="naviscroll" style="width: 180px;">
				<div class="naviscroll-box" style="width: 180px;">
					<div class="naviscroll-content" style="top: 0px; width: 180px;">
						<div class="naviscroll-in">
							<ul class="navi">
								<li><a href="home" id="moviehome" title="영화홈"><h3>영화 홈</h3></a></li>
								<li><a href="movieList" id="movieList" title="영화 리스트"><h3>영화리스트</h3></a></li>
								<li><a href="../reviewList" id="reviewboard" title="리뷰 게시판"><h3>리뷰게시판</h3></a></li>
								<li><a href="../member/member" id="userlist" title="회원 리스트"><h3>회원리스트</h3></a></li>
								<li><a href="https://serieson.naver.com/movie/home.nhn" id="moviedownload" title="영화 다운로드"><h3>영화다운로드</h3></a></li>
								<li><a href="#" id="mypage" title="마이페이지"><h3>마이페이지</h3></a>
									<ul class="navi_sub1" style="display: none;">
										<li><a href="../myPage/updateMF?id=${sessionScope.myLoginId}" title="회원정보수정"> 회원정보수정 </a></li>
										<li><a href="../myPage/withdraw.jsp" title="회원탈퇴"> 회원탈퇴 </a></li>
										<li><a href="../myPage/myReviewList?id=${sessionScope.myLoginId}" title="작성한 리뷰"> 작성한 리뷰 </a></li>
										<li><a href="../myPage/iLikeMovie?id=${sessionScope.myLoginId}" title="좋아요한 영화"> 좋아요한 영화 </a></li>
										<li><a href="../myPage/iLikeReview?id=${sessionScope.myLoginId}" title="좋아요한 리뷰"> 좋아요한 리뷰 </a></li>
										<li><a href="../myPage/followerList?id=${sessionScope.myLoginId}" title="팔로워"> 팔로워 </a></li>
										<li><a href="../myPage/followingList?id=${sessionScope.myLoginId}" title="팔로잉"> 팔로잉 </a></li>
										<li><a href="alram.jsp" title="알람"> 알람 </a></li>
									</ul>
								</li>
								<li><a href="#" id="adminpage" title="관리자페이지"><h3>관리자페이지</h3></a>
									<ul class="navi_sub2" style="display: none;">
										<li><a href="../admin/movieList" title="영화관리"> 영화관리 </a></li>
										<li><a href="../admin/memberDisableList" title="회원관리"> 회원관리 </a></li>
										<li><a href="../admin/reviewList" title="리뷰관리"> 리뷰관리 </a></li>
										<li><a href="../admin/commentList" title="댓글관리"> 댓글관리 </a></li>
										<li><a href="../admin/reportReviewList" title="신고관리"> 신고관리 </a></li>
									</ul>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<body>

</body>
<script>
	var loginId = "${sessionScope.myLoginId}";

	if (loginId == "") {
		var content = "<button id='btn1'>로그인</button> <button id='btn2'>회원가입</button>"
		document.getElementById("login").innerHTML = content;
	} else {
		content = "환영합니다. " + loginId
				+ "님 <button id='btn3'>로그아웃</button> <button>알람</button>";
		document.getElementById("login").innerHTML = content;
	}

	//if (loginId == "admin") {
	if(loginId != "dsafsd"){
		$("#adminpage").css('display', 'block');
	} else {
		$("#adminpage").css('display', 'none');
	}

	$("#btn1").click(function() {
		location.href = "../join/index.jsp";
	});

	$("#btn2").click(function() {
		location.href = "join.jsp";
	});

	$("#btn3").click(function() {
		location.href = "../join/logout";
	});

	$("#movielist").click(function() {
		if (loginId == "") {
			alert("로그인후 이용해주세요.");
		}
	});
	
	$("#reviewboard").click(function() {
		if (loginId == "") {
			alert("로그인후 이용해주세요.");
		}
	});
	
	$("#userlist").click(function() {
		if (loginId == "") {
			alert("로그인후 이용해주세요.");
		}
	});
	
	$("#mypage").click(function() {
		if (loginId == "") {
			alert("로그인후 이용해주세요.");
		} else {
			$(".navi_sub1").slideToggle('slow');
			$(".navi_sub1").css('display', 'block');
		}
	});

	$("#adminpage").click(function() {
		$(".navi_sub2").slideToggle('slow');
		$(".navi_sub2").css('display', 'block');
	});
	
	
	
</script>
</html>