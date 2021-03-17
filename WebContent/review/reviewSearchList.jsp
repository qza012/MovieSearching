<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<script src="https://kit.fontawesome.com/abf52b8f21.js"></script>
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
            padding: 10px;
        }
        div.paging{
            width: 100%;
            text-align: center;
        }
        div.button{
            width: 100%;
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
            width: 100%;
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
	<form action="reviewSearchList" method="POST">
		<div class="search">
			<select name="search" class="searchBar">
		        <option ${(search == "movieName")? "selected" : ""} value="movieName" selected>영화 제목</option>
		        <option ${(search == "id")? "selected" : ""} value="id">사용자 아이디</option>
		        <option ${(search == "subject")? "selected" : ""} value="subject">리뷰 제목</option>
   		 	</select>
	    <input class="searchForm" id="keyword" name="keyword" value="${keyword}" type="text"/>
	    <input type="button" id="searchButton" value="검색" onclick="keywordCheck()"/>
    	</div>
	</form>
	
    <h2>리뷰 검색 결과</h2>
    <div style="text-align: right; padding-bottom: 10px;">
    	<a href="./reviewList">전체 리뷰 리스트</a>
    </div>
    
    <table>
    <c:if test="${maxPage != 0}">
    	<tr>
            <th style="width: 8%;">순번</th>
            <th style="width: 40%">제목</th>
            <th style="width: 20%">영화명</th>
            <th style="width: 8%;">평점</th>
            <th style="width: 8%;" >작성자</th>
            <th style="width: 8%;">좋아요</th>
            <th style="width: 8%;">작성일</th>
        </tr>
    </c:if>
    <c:if test="${maxPage == 0}">
    	<p style="text-align: center; font-size: 30px;"><b>검색결과가 없습니다.</b></p>
    </c:if>
    
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
				<a href="reviewSearchList?page=${currPage-1}&search=${search}&keyword=${keyword}"><i class="fas fa-angle-left"></i></a>
			</c:if>
			<c:if test="${currPage == 1 && maxPage != 0}">
				<a style="color: gray;"><i class="fas fa-angle-left"></i></a>
			</c:if>
		</span>
		
		<c:set var="startNum" value="${currPage-(currPage-1)%10}"/>
		
		<span id="pageArea">
			<c:forEach begin="0" end="9" var="i">
				<c:if test="${startNum+i <= maxPage}">
					<c:if test="${startNum+i != currPage}">
						<a href="reviewSearchList?page=${startNum+i}&search=${search}&keyword=${keyword}">${startNum+i} </a>
					</c:if>
					<c:if test="${startNum+i == currPage}">
						<b><a href="reviewSearchList?page=${startNum+i}&search=${search}&keyword=${keyword}">${startNum+i} </a></b>
					</c:if>
				</c:if>
			</c:forEach>
		</span>
		
		<span>
			<c:if test="${currPage < maxPage}">
				<a href="reviewSearchList?page=${currPage+1}&search=${search}&keyword=${keyword}"><i class="fas fa-angle-right"></i></a>
			</c:if>
			<c:if test="${currPage == maxPage}">
				<a style="color: gray;"><i class="fas fa-angle-right"></i></a>
			</c:if>
		</span>
    </div>

    <div class="button">
    <input type="button" value="리뷰 작성" onclick="location.href='./review/reviewWrite.jsp' ">
    </div>
</body>
<script>
	function keywordCheck(){
		var keyword = $("#keyword").val();
		if(keyword == ""){
			alert("검색어를 입력해주세요.");
		}else{
			$("#searchButton").attr('type','submit');
		}
	}

	var msg="${msg}";
	
	if(msg!=""){
		alert(msg);
	}
</script>
</html>