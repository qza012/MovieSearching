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
                margin-top: 40px;
                margin-left: 20%;
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
            .pageArea{
				width:700px;
				text-align: center;
				margin-top: 25px;
				margin-left: 30%;
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
            <h2>좋아요한 영화 -> 아직 내용 안불러온거에요</h2>
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