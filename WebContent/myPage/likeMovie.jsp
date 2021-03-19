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
				margin-top: 5%;
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
            .likeMovie {
               	font-family: Verdana;
                width: 70%;
               	margin-top: 5%;
                margin-left: 22%;
            } 
            table {
               	text-align: center;
                width: 100%;
           	}
            th, td{
                padding: 10px;
                background-color: whitesmoke;
            }
            #notLikeMovie{
            	font-size: small;
            	font-weight: 600;
            }   
            .pageArea{
				width:700px;
				text-align: center;
				margin-top: 30px;
				margin-left: 38%;
			}
			.pageArea span{
				font-size:16px;
				padding: 5px 10px;
				margin: 5px;
			}
			#btn{
				color:black;
			}
			#page{
				color:red;
			}
        </style>
    </head>
    <body>
		<jsp:include page="/movie/include.jsp" />
		<div class="likeMovie">
    		<h2>좋아요한 영화</h2>
    		<table>
     	   		<tr>
           			<th></th>
        	        <th>영화제목</th>
            	    <th>장르</th>
           	     	<th>감독</th>
           	   		<th>개봉일</th>
           			<th></th>
				</tr>
				<c:forEach items="${movie_list}" var="movie">
                	<tr>
                		<td><img src="${movie.posterUrl}" width="100px"></td>
                		<td> ${movie.movieName}</td><td>${movie.genre}</td><td>${movie.director}</td>
                		<td>${movie.openDate}</td><td><button id="notLike" onclick="location.href='./notLikeMovie?idx=${movie.idx}'">좋아요 취소</button></td>
                	</tr>
				</c:forEach>
			</table>
		</div>
        <div class="pageArea">
			<span id="btn">
				<c:if test="${currPage == 1}"> 이전</c:if>
				<c:if test="${currPage > 1}">
					<a href="./iLikeMovie?id=${sessionScope.myLoginId}&page=${currPage-1}">이전</a>
				</c:if>
			</span>
			<span id="page">${currPage}</span>
			<span id="btn">
				<c:if test="${currPage == maxPage}">다음</c:if>
				<c:if test="${currPage < maxPage}">
					<a href="./iLikeMovie?id=${sessionScope.myLoginId}&page=${currPage+1}">다음</a>
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