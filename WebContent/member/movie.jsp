<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		 <meta charset="UTF-8">
    	<title>좋아요한 영화</title>
		<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
	</head>
		<style>
    #top{
        text-align: right;
    }
    a{
        text-decoration: none;
    }
    table {
        border-collapse: separate;
        border-spacing: 0;
        text-align: center;
        line-height: 1.5;
        border-top: 1px solid #ccc;
        border-left: 1px solid #ccc;
        margin : auto;
    }
    th {
        width: 200px;
        padding: 10px;
        font-weight: bold;
        vertical-align: top;
        border-right: 1px solid #ccc;
        border-bottom: 1px solid #ccc;
        border-top: 1px solid #fff;
        border-left: 1px solid #fff;
        background: #eee;
    }
    td {
        width: 150px;
        padding: 10px;
        vertical-align: top;
        border-right: 1px solid #ccc;
        border-bottom: 1px solid #ccc;
    }
    .table{
        margin-top: 10px;
    }
</style>
	<body>
	<jsp:include page="../movie/include.jsp" />
    <hr>
    <div id="follow">
        <button>팔로우</button>
    </div>
    <table class="table">
        <tr>
            <th>영화 제목</th>
            <th>장르</th>
            <th>감독</th>
            <th>개봉일</th>
        </tr>
        <c:forEach items="${movie_list}" var="movie3">
	        <tr>
	            <td>${movie3.movieName}</td>
	            <td>${movie3.genre}</td>
	            <td>${movie3.director}</td>
	            <td>${movie3.openDate}</td>
	        </tr>
        </c:forEach>
    </table>
</body>
	<script>
	</script>
</html>