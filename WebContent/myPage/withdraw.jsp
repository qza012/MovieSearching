<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Withdraw</title>
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
               margin-bottom: 10%;
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
           .withdraw{
                text-align: center;
                font-family: Verdana;
                border: 3px solid darkgrey;
                background-color: whitesmoke;
                border-collapse: collapse;
                padding: 1%;
                margin-top: 40px;
                margin-left: 25%;
                width: 40%;
            }
           .conPW{
           		text-align:center;
            }
            input[type="text"]{
            	width: 50%;
            	height: 20px;
            }
            #btn{
            	margin: 10% 30%;
            }
       </style>
   </head>
   <body>
       <h2>로고</h2>
       <p>
     		${loginId}님,
           <a href="./logout">[ 로그아웃</a>
           |
           <a href="alram.jsp">알람 ]</a>
       </p>
       <hr/>
       <div class="naviBar">
           <nav aria-label="naviBar">
               <ul class="navi">
                   <li>
                       <a href="./"> 영화 홈
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
                       <a href="./updateMF?id=${loginId}">마이페이지</a>
                       <ul class="myPage">
                           <li>
                               <a href="./updateMF?id=${loginId}">회원 정보 수정</a>
                           </li>
                           <li>
                               <a href="withdraw.jsp">회원 탈퇴</a>
                           </li>
                           <li>
                               <a href="reviewList.jsp">작성한 리뷰</a>
                           </li>
                           <li>
                               <a href="likeMovie.jsp">좋아요한 영화</a>
                           </li>
                           <li>
                               <a href="likeReview.jsp">좋아요한 리뷰</a>
                           </li>
                           <li>
                               <a href="follower.jsp">팔로워</a>
                           </li>
                           <li>
                               <a href="following.jsp">팔로잉</a>
                           </li>
                           <li>
                               <a href="alram.jsp">알람</a>
                           </li>
                       </ul>
                   </li>
                   <li>
                       <a href="admin.jsp">
                           관리자 페이지
                           &nbsp;&nbsp;&nbsp;&nbsp;
                           <span> > </span>
                       </a>
                   </li>
               </ul>
           </nav>
       </div>
        <div class="withdraw">
            <h3>회원 탈퇴</h3>
            <span class="conPW">
                	비밀번호 &nbsp;
                    <input type="text" placeholder="현재 비밀번호"/>
       		</span>
       		<br/>           
	    	<button id="btn" onclick="location.href='./withdraw?id=${loginId}'">회원 탈퇴</button>
        </div>
    </body>
    <script>
     
    </script>
</html>