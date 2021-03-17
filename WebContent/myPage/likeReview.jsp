<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>likeReview</title>
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
            .likeReview {
                font-family: Verdana;
                width: 70%;
                margin-top: 40px;
                margin-left: 20%;
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
            #notLike{
            	font-size: small;
            	font-weight: 600;
            }   
            .pageArea{
				width:700px;
				text-align: center;
				margin-top: 25px;
				margin-left: 35%;
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
        <div class="likeReview">
            <h2>좋아요한 리뷰</h2>
            <table>
                <tr>
                    <th>리뷰 번호</th>
                    <th>제목</th>
                    <th>영화 제목</th>
                    <th>평점</th>
                    <th>좋아요</th>
                    <th>작성일</th>
                    <th></th>
                </tr>
                <c:forEach items="${list}" var="review">
                	<tr>
                		<td>${review.idx}</td><td> ${review.subject}</td><td>${review.movieName}</td><td>${review.score}</td>
                		<td>${review.cntLike}</td><td>${review.reg_date}</td><td><button id="notLike" onclick="location.href='./iDonotLike?idx=${review.idx}'">좋아요 취소</button></td>
                	</tr>
                </c:forEach>
            </table>
        </div>
        <div class="pageArea">
			<span id="btn">
				<c:if test="${currPage == 1}"> 이전</c:if>
				<c:if test="${currPage > 1}">
					<a href="./iLikeReview?id=${sessionScope.myLoginId}&page=${currPage-1}">이전</a>
				</c:if>
			</span>
			<span id="page">${currPage}</span>
			<span id="btn">
				<c:if test="${currPage == maxPage}">다음</c:if>
				<c:if test="${currPage < maxPage}">
					<a href="./iLikeReview?id=${sessionScope.myLoginId}&page=${currPage+1}">다음</a>
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