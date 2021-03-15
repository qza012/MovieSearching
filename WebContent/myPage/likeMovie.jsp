<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>likeMovie</title>
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
            .likeMovie {
                font-family: Verdana;
                width: 75%;
                margin-top: 40px;
                margin-left: 10%;
            } 
            table {
                text-align: center;
                width: 100%;
            }
            th, td{
                background-color: whitesmoke;
            }
            th, td{
                padding: 10px;
                background-color: whitesmoke;
            }
            #notLikeMovie{
            	font-size: small;
            	font-weight: 600;
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
        <div class="likeMovie">
            <h3>좋아요한 영화</h3>
            <table>
                <tr>
                    <th></th>
                    <th>영화제목</th>
                    <th>장르</th>
                    <th>감독</th>
                    <th>배우</th>
                    <th>개봉일</th>
                    <th></th>
                </tr>
                <tr>
                	<td><img src="https://i.pinimg.com/originals/96/a0/0d/96a00d42b0ff8f80b7cdf2926a211e47.jpg" width="100px"></td>
                	<td> 영화는 어떻게 만들어 지는가 </td><td> 다큐 </td><td> 익명 </td><td> 김배우, 이배우 </td><td> 2021-03-09 </td>
                    <td><button id="notLikeMovie" onclick="location.href='./iDonotLikeMovie?idx=${review.idx}'">좋아요 취소</button></td>
               	</tr>
                <tr>
                    <td><img src="https://i.pinimg.com/736x/30/d5/38/30d53895b7337958e79aff2e974c7a1f.jpg" width="100px"></td>
                	<td> The Title </td><td> 액션 </td><td> 익명 </td><td> 김배우, 이배우 </td><td> 2021-03-09 </td>
                    <td><button id="notLikeMovie" onclick="location.href='./notLikeMovie?idx=${review.idx}'">좋아요 취소</button></td>
                </tr>
                <tr>
                    <td><img src="https://i.pinimg.com/originals/9e/4b/97/9e4b97433364d774a2a4a9c6290e8906.jpg" width="100px"></td>
                	<td> 영화같은 삶 </td><td> 멜로 </td><td> 익명 </td><td> 김배우, 이배우 </td><td> 2021-03-09 </td>
                    <td><button id="notLikeMovie" onclick="location.href='./notLikeMovie?idx=${review.idx}'">좋아요 취소</button></td>
                </tr>
            </table>
        </div>
        <div class="pageArea">
			<span>
				<c:if test="${currPage == 1}">이전</c:if>
				<c:if test="${currPage > 1}">
					<a href="./?page=${currPage-1}">이전</a>
				</c:if>				
			</span>
			<span id="page">${currPage}</span>
			<span>
				<c:if test="${currPage == maxPage}">다음</c:if>
				<c:if test="${currPage < maxPage}">
					<a href="./?page=${currPage+1}">다음</a>	
				</c:if>	
			</span>
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