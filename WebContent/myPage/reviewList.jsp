<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>ReviewList</title>
		<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
        <style>
            h2{
                text-align: center; 
                border: 10px solid cornflowerblue;
                width: 100px;
                padding: 5px;
                margin-left: 50%;
            }
            h3{
                text-align: center;
            }            
            p{
                position: relative;
                text-align: right;
                margin: 1%;
            }
            p, a:link, a:visited{/*링크를 클릭 하기 전*/
                color:darkslategrey;
                text-decoration: none;
                font-size: 14px;
                font-weight: 600;
            }
            a:active{/*링크 실행 시*/
                color: cornflowerblue;
            }
            ::marker {
                font-size: 0px;
            }
            ul.navi{
                border: 3px solid whitesmoke;
                border-collapse: collapse;
                padding: 1%;
                margin: 5%;
                width: 120%;
                background-color: lightgrey;
            }
            li{
                border-bottom: 2px double whitesmoke;
                padding: 3%;
            }
            div{
                float: left;
            }
            .reviewList {
                font-family: Verdana;
                width: 75%;
                margin-top: 40px;
                margin-left: 10%;
            }    
            table {
                text-align: center;
                width: 100%;
            }
            th{
                background-color: lightgrey;
            }
            td{
                background-color: whitesmoke;
            }
            th, td{
                padding: 10px;
                border-bottom: 1px solid darkslategrey;
            }
            #delete{
            	color: red;
            }
        </style>
    </head>
    <body>
        <h2>로고</h2>
        <p>
     		${sessionScope.myLoginId}님,
           <a href="./logout?id=${sessionScope.myLoginId}">[ 로그아웃 ]</a>
           |
           <a href="alram.jsp">알람 ]</a>
       </p>
        <hr/>
        <div class="naviBar">
           <nav aria-label="naviBar">
               <ul class="navi">
                   <li>
                       <a href="./main.jsp"> 영화 홈
                           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                           <span> > </span>
                       </a>
                   </li>    
                   <li>
                       <a href="#"> 영화 리스트
                           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                           <span> > </span>
                       </a>
                   </li>    
                   <li>
                       <a href="#"> 리뷰 게시판
                           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                           <span> > </span>
                       </a>
                   </li>    
                   <li>
                       <a href="#"> 회원 리스트
                           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                           <span> > </span>
                       </a>
                   </li>    
                   <li>
                       <a href="https://serieson.naver.com/movie/home.nhn" style="color: darkslategrey;">
                       		영화 다운로드
                       	</a>
                   </li>    
                   <li>    
                       <a href="./updateMF?id=${sessionScope.myLoginId}" onclick="showMyPage()">마이페이지</a>
                      	 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         <span> > </span>
                       <ul id="myPage">
                           <li>
                               <a href="./updateMF?id=${sessionScope.myLoginId}">회원 정보 수정</a>
                           </li>
                           <li>
                               <a href="./withdraw.jsp">회원 탈퇴</a>
                           </li>
                           <li>
                               <a href="./myReviewList?id=${sessionScope.myLoginId}">작성한 리뷰</a>
                           </li>
                           <li>
                               <a href="./iLikeMovie?id=${sessionScope.myLoginId}">좋아요한 영화</a>
                           </li>
                           <li>
                               <a href="./iLikeReview?id=${sessionScope.myLoginId}">좋아요한 리뷰</a>
                           </li>
                           <li>
                               <a href="./followerList?id=${sessionScope.myLoginId}">팔로워</a>
                           </li>
                           <li>
                               <a href="./followingList?id=${sessionScope.myLoginId}">팔로잉</a>
                           </li>
                           <li>
                               <a href="alram.jsp">알람</a>
                           </li>
                       </ul>
                   </li>
                   <li>
                       <a href="admin.jsp">관리자 페이지
                           &nbsp;&nbsp;&nbsp;&nbsp;
                           <span> > </span>
                       </a>
                   </li>
               </ul>
           </nav>
       </div>    
        <div class="reviewList">
            <h3>작성한 리뷰</h3>
            <table>
                <tr>
                    <th>리뷰 번호</th>
                    <th>제목</th>
                    <th>영화 제목</th>
                    <th>평점</th>
                    <th>좋아요</th>
                    <th>작성일</th>
                    <th>삭제</th>
                </tr>
                <c:forEach items="${list}" var="review">
                	<tr>
                		<td>${review.idx}</td><td> ${review.subject}</td><td>${review.movieName}</td><td>${review.score}</td>
                		<td>${review.cntLike}</td><td>${review.reg_date}</td><td> <a id="delete" href="./deleteMyReview?idx=${review.idx}">삭제</a> </td>
                	</tr>
                </c:forEach>
            </table>
        </div>
    </body>
	<script>
		var showIf = document.getElementById('myPage').style.display;
		
		function showMyPage(){
			if(showIf = 'none'){
				document.getElementById('myPage').style.display='block';				
			} 
		}
	</script>
</html>