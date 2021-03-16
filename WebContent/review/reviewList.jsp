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
            width: 70%;
        }
        table,th,td{
            border-top: 1px solid lightgray;
            border-radius: 1px solid lightgray;
            border-collapse: collapse;
            text-align: center;
        }
        th,td{
            padding: 10px;
        }
        div.paging{
            width: 70%;
            text-align: center;
        }
        div.button{
            width: 70%;
            text-align: right;
        }
        .reviewDetail{
            cursor: pointer;
        }
        .movieDetail{
            cursor: pointer;
        }
        .memberDetail{
            cursor: pointer;
        }
        div.search{
            width: 70%;
            height: 70px;
            text-align: center;
        }
        .searchForm{
            width: 40%;
            height: 30%;
        }
        .searchBar{
            height: 40%;
        }
        .paging>a:link,
        .paing>a:hover,
        .paging>a:visited,
        .paging>a:active{
            color: black;
            text-decoration: none;
        }
    </style>
</head>
<body>
	<form action="reviewList" method="POST">
		<div class="search">
			<select name="search" class="searchBar">
		        <option value="movieName" selected>영화 제목</option>
		        <option value="id">사용자 아이디</option>
		        <option value="subject">리뷰 제목</option>
   		 	</select>
	    <input class="searchForm" name="keyword" value="${keyword}" type="text"/>
	    <input type="submit" value="검색"/>
    	</div>
	</form>
    
    <table>
        <tr>
            <th>순번</th>
            <th>제목</th>
            <th>영화명</th>
            <th>평점</th>
            <th>작성자</th>
            <th>좋아요</th>
            <th>작성일</th>
        </tr>
	
		<c:forEach items="${review}" var="review">
			 <tr>
            <td>${review.idx}</td>
            <td class="reviewDetail" onclick="location.href='reviewDetail?Idx=${review.idx}' ">${review.subject}</td>
            <td class="movieDetail" onclick="location.href='#' ">${review.movieName}</td>
            <td>${review.score}</td>
            <td class="memberDetail" onclick="location.href='#' ">${review.id}</td>
            <td>${review.cntLike}</td>
            <td>${review.reg_date}</td>
        </tr>
		</c:forEach>
    </table>

    <div class="paging">
		<span>
			<c:if test="${currPage>1}">
				<a href="reviewList?page=${currPage-1}&search=${search}&keyword=${keyword}">이전</a>
			</c:if>
			<c:if test="${currPage == 1}">
			이전
			</c:if>
		</span>
		
		<span id="pageArea">${currPage}</span>
		
		<span>
			<c:if test="${currPage < maxPage}">
				<a href="reviewList?page=${currPage+1}&search=${search}&keyword=${keyword}">다음</a>
			</c:if>
			<c:if test="${currPage == maxPage}">
			다음
			</c:if>
		</span>
    </div>

    <div class="button">
    <input type="button" value="리뷰 작성" onclick="location.href='./review/reviewWrite.jsp' ">
    </div>
</body>
<script>
	var msg="${msg}";
	
	if(msg!=""){
		alert(msg);
	}
</script>
</html>