<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<style>
        table{
            width: 100%;
        }
        table,th,td{
            border-top: 1px solid lightgray;
            border-radius: 1px solid lightgray;
            border-collapse: collapse;
            text-align: center;
        }
        th,td{
            padding: 5px;
        }
        .movie{
            background-color: tomato;
        }
        .search{
            margin-bottom: 10px;
            text-align: center;
        }
        #movieName{
            width: 50%;
        }
        div.paging{
            width: 100%;
            text-align: center;
        }
        a:link,
        a:hover,
        a:visited,
        a:active{
            color: black;
            text-decoration: none;
        }
 </style>
</head>
<body>
	<form action="reviewMovieSearch?page=1" method="POST">
		<div class="search">
		<input type="text" name="subName" value="${subName}" placeholder="영화제목으로 검색"/>
        <input type="submit" value="검색"/>
    </div>
	</form>
	
    <table>
        <tr>
            <th>영화제목</th>
            <th>장르</th>
            <th>감독</th>
            <th>국가</th>
            <th>개봉일</th>
        </tr>
        
        <c:forEach items="${movie}" var="movie">
        	<tr style="cursor: pointer;" onclick="movieChoice(${movie.movieCode})">
	            <td>${movie.movieName}</td>
	            <td>${movie.genre}</td>
	            <td>${movie.director}</td>
	            <td>${movie.country}</td>
	            <td>${movie.openDate}</td>
			</tr>
        </c:forEach>
    </table>
    
    <div class="paging">
    		<span>
			<c:if test="${currPage>1}">
				<a href="reviewMovieSearch?subName=${subName}&page=${currPage-1}">이전</a>
			</c:if>
			<c:if test="${currPage == 1 && maxPage != 0}">
			이전
			</c:if>
		</span>
		
		<c:set var="startNum" value="${currPage-(currPage-1)%10}"/>
		
		<span id="pageArea">
			<c:forEach begin="0" end="9" var="i">
				<c:if test="${startNum+i <= maxPage}">
					<c:if test="${startNum+i != currPage}">
						<a href="reviewMovieSearch?subName=${subName}&page=${startNum+i}">${startNum+i} </a>
					</c:if>
					<c:if test="${startNum+i == currPage}">
						<b><a href="reviewMovieSearch?subName=${subName}&page=${startNum+i}">${startNum+i} </a></b>
					</c:if>
				</c:if>
			</c:forEach>
		</span>
		
		<span>
			<c:if test="${currPage < maxPage}">
				<a href="reviewMovieSearch?subName=${subName}&page=${currPage+1}">다음</a>
			</c:if>
			<c:if test="${currPage == maxPage}">
			다음
			</c:if>
		</span>
    </div>
</body>
<script>
function movieChoice(movieCode){
	$.ajax({
		type:'post' 
		,url:'./reviewMovieChoice' 
		,data:{
			'movieCode':movieCode,
		}
		,dataType: 'json' 
		,success: function(data){
			console.log(data);
			if(data.haveReview == 1){
				alert("이미 리뷰를 작성한 영화입니다.");
			}else{
				if(data.success == 1){
					opener.document.getElementById("movieCode").value=data.moiveCode;
					opener.document.getElementById("movieName").value=data.movieName;
					window.close();
				}
			}	
		}
		,error: function(e){
			console.log(e);
		}
	});
}
</script>
</html>