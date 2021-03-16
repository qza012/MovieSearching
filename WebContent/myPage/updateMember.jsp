<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>UpdateMember</title>
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
            .updateMember {
                font-family: Verdana;
                width: 50%;
                margin-top: 40px;
                margin-left: 25%;
            }    
            table {
                text-align: center;
                border:1px solid darkgrey;
                border-collapse: collapse;
                width: 100%;
            }
            th{
                background-color: lightgrey;
            }
            td{
                background-color: whitesmoke;
            }
            th, td{
                padding: 1%;
                border-bottom: 1px solid darkslategrey;
            }
            input[type="text"],select{
                text-align: center;
                width: 70%;
                height: 20px;
            }
            #urlArea{
            	width: 40%;
            	margin-left:1%;
            }
        </style>
    </head>
    <body>
        <h2>로고</h2>
        <p>
     		${sessionScope.myLoginId}님,
           <a href="./logout?id=${sessionScope.myLoginId}">[ 로그아웃</a>
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
        <div class="updateMember">
            <h3>회원정보 수정</h3>
            <form action="./update" method="post" enctype="multipart/form-data" >
	            <table>
	                <tr>
	                    <th>아이디</th>
	                </tr>
	                <tr>
	                    <td>
	                        <input type="text" name="userId" id="userId" value="${mDto.id}" readonly="readonly"/>
	                    </td>
	                </tr>
	                <tr>
	                    <th>비밀번호</th>
	                </tr>
	                <tr>
	                    <td>
	                        <input type="text" name="userPw" id="userPw"/>
	                    </td>
	                </tr>
	                <tr>
	                    <th>비밀번호 확인</th>
	                </tr>
	                <tr>
	                    <td>
	                        <input type="text" id="conPw"/>
	                    </td>
	                    <span></span>
	                </tr>
	                <tr>
	                    <th>비밀번호 찾기 질문</th>
	                </tr>
	                <tr>
	                	<td>
	                		<select name="Question">
	                			<c:forEach items="${qList}" var="list" >
	                            	<option value="${list.idx}">${list.content}</option>
	                        	</c:forEach>
	                        </select>
	                   	</td>
					</tr>
	                <tr>
	                    <th>비밀번호 찾기 답변</th>
	                </tr>
	                <tr>
	                    <td>
	                        <input type="text" name="answer" id="answer"/>
	                    </td>
	                </tr>
	                <tr>
	                    <th>이름</th>
	                </tr>
	                <tr>
	                    <td>
	                        <input type="text" name="userName" value="${mDto.name}"/>
	                    </td>
	                </tr>
	                <tr>
	                    <th>나이</th>
	                </tr>
	                <tr>
	                    <td>
	                        <input type="text" name="age" value="${mDto.age}"/>
	                    </td>
	                </tr>
	                <tr>
	                    <th>성별</th>
	                </tr>
	                <tr>
	                    <td>
	                    	<c:if test="${mDto.gender == '남'}">
	                        	<input type="radio" name="gender" value="남" checked="checked"/>남
	                        	<input type="radio" name="gender" value="여" />여
	                    	</c:if>
	                    	<c:if test="${mDto.gender == '여'}">
		                    	<input type="radio" name="gender" value="남"/>남
	                        	<input type="radio" name="gender" value="여" checked="checked"/>여
	                    	</c:if>
	                    </td>
	                </tr>
	                <tr>
	                    <th>이메일</th>
	                </tr>
	                <tr>
	                    <td>
	                        <input type="text" name="email" value="${mDto.email}"/>
	                    </td>
	                </tr>
	                <tr>
	                    <th>선호하는 영화 장르</th>
	                </tr>
	                <tr>
	                    <td>
	                        <select name="genre">
	                            <option value="${mDto.genre}">${mDto.genre}</option>
	                         </select>
	                    </td>
	                </tr>
	                <tr>
	                    <th>프로필 사진 등록</th>
	                </tr>
	                <tr>
	                	<td>
	                		<input type="text" id="urlArea" placeholder="프로필 사진 등록(최대 10MB)" value="${photoPath}"/>
                			<input type="file"  name="photo"/>
		    			</td>
	                <tr>
	                    <th colspan="2">
	                        <input type="submit" value="저장">
	                    </th>
	                </tr>
	            </table>
	    	</form>        
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