<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>FollowList</title>
		<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
        <style>
 			h2{
				margin-left: 2%;
			}            
			a:link, a:visited{/*링크를 클릭 하기 전*/
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
            .followerList {
                font-family: Verdana;
                width:65%;
                margin-top: 4%;
                margin-left: 25%;
            } 
            div{
            	float: none;
            }
            #table{
            	padding: 1%;
            	margin: 1%;
            	width: 40%;
            	border: 1px solid black;
            	background-color: white;
            	float: left;
            }
            #followerId{
            	color: red;
            	font-size: 18px;
				font-weight: 600;
            	padding: 1%;
            	margin-top: 3%;
            	margin-left: 10px;
            }
            #followNum{
            	text-decoration: underline;
            	font-size: 18px;
				font-weight: 600;
            	padding: 1%;
            	margin-top: 3%;
            	margin-left: 10px;
            }
            #notLike{
            	margin-top: 5%;
            	margin-left: 15px;
            }
            .pageArea{
				width:700px;
				text-align: center;
				margin-top: 40%;
				margin-left: 30%;
			}
			.pageArea span{
				font-size:16px;
				padding: 5px 10px;
				margin: 5px;
			}
			#profile{
				width: 80%;
				height:80%;
			}
			#btn{
				color:balck;
			}
			#page{
				color:red;
			}
        </style>
    </head>
    <body>
        <jsp:include page="/movie/include.jsp" />
        <div class="followerList">
        	<h2>팔로워</h2>
         	<c:forEach items="${fList}" var="follow">
            	<div id="table">
	             	<div id="profile"><img src="photo/${follow.newFileName}" alt="${follow.oriFileName}" width="80px" height="80px"/></div>
                	&nbsp;<div id="followerId">${follow.id}</div>	
                	<div id="followNum">팔로잉 ${follow.followingNum} 팔로워 ${follow.followerNum}</div>
                	<div id="notLike"><button onclick="location.href='./deleteFollower?id=${follow.id}'">팔로워 삭제</button></div>
                </div>
        	</c:forEach>
        </div>
        <div class="pageArea">
			<span id="btn">
				<c:if test="${currPage == 1}"> 이전</c:if>
				<c:if test="${currPage > 1}">
					<a href="./followerList?id=${sessionScope.myLoginId}&page=${currPage-1}">이전</a>
				</c:if>
			</span>
			<span id="page">${currPage}</span>
			<span id="btn">
				<c:if test="${currPage == maxPage}">다음</c:if>
				<c:if test="${currPage < maxPage}">
					<a href="./followerList?id=${sessionScope.myLoginId}&page=${currPage+1}">다음</a>
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