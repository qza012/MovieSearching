<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>FollowerList</title>
		<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
        <style>
 			h2{
				margin-left: 2%;
			}            
			a:link, a:visited{
				color:darkslategrey;
				text-decoration: none;
				font-size: 14px;
				font-weight: 600;
			}
            a:active{
                color: cornflowerblue;
           	}
			::marker {
               	font-size: 0px;
           	}
  			.followerList {
                font-family: Verdana;
                width:70%;
                margin-top: 4%;
                margin-left: 22%;

            } 
           #table{
            	padding: 1%;
            	margin: 1%;
            	width: 19%;
            	border: 1px solid black;
            	background-color: white;
            	float: left;
            }
            img{
            	width:100px; 
            	height:110px;
            	margin-left: 30%;
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
				margin-top: 38%;
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
        	<table>
	         	<c:forEach items="${fList}" var="follow">
	            	<tr>
		            	<div id="table">
			             	<div id="profile">
				             		<c:if test="${follow.profileURL == null}">
					             		<img src="photo/${follow.newFileName}" alt="${follow.oriFileName}"/>		             		
				             		</c:if>
				             		<c:if test="${follow.profileURL != null}">
				             			<img src="${follow.profileURL}" width="100px" height="100px"/>
				             		</c:if>
				             	</div>
			             	<span>
		        	        	&nbsp;<div id="followerId">${follow.id}</div>	
		    	            	<div id="followNum">팔로잉 ${follow.followingNum} 팔로워 ${follow.followerNum}</div>
	    	        	    	<div id="notLike"><button onclick="location.href='./deleteFollower?id=${follow.id}'">팔로워 삭제</button></div> 	
			             	</span>
	               		</div>
	               	</tr>	
	        	</c:forEach>
        	</table>
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
	var msg = "${msg}";
	if(msg != ""){
		alert(msg);
		console.log(msg);
	}
	</script>
</html>