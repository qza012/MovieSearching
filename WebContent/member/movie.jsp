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
    #navi{
        margin-top: 100px;
    }
    #navi a{
        display: block;
    }
    #navi th:hover{
        background-color: lightgray;
    }
    .table{
        margin-top: 10px;
    }
    #follow,#id{
        float: left;
        position: relative;
        left: 30%;
        padding: 5px 10px;
    }
    button{
        margin-top: 10px;
        padding: 10px;
        font-size: large;
        font-weight: bold;
    }
</style>
	<body>
    <div id="top">
        <a href="#">로그아웃</a>
        &nbsp;&nbsp;
        <a href="#">알람</a>
    </div>
    <hr>
    <div id="id">
        <h3>님</h3>
    </div>
    <div id="follow">
        <button>팔로우</button>
    </div>
    <div id="navi">
        <table>
            <tr>
                <th><a href="review.jsp">작성한 리뷰</a></th>
                <th><a href="movie.jsp">좋아요한 영화</a></th>
                <th><a href="#">좋아요한 리뷰</a></th>
                <th><a href="#">팔로워</a></th>
                <th><a href="#">팔로잉</a></th>
            </tr>
        </table>
    </div>
    <table class="table">
        <tr>
            <th>포스터</th>
            <th>영화 제목</th>
            <th>장르</th>
            <th>감독</th>
            <th>배우</th>
            <th>개봉일</th>
        </tr>
        <c:forEach items="${movie_list}" var="movie3">
	        <tr>
	            <td>${movie3.posterurl}</td>
	            <td>${movie3.moviename}</td>
	            <td>${movie3.genre}</td>
	            <td>${movie3.director}</td>
	            <td>${movie3.actors}</td>
	            <td>${movie3.opendate}</td>
	        </tr>
        </c:forEach>
    </table>
</body>
	<script>
	</script>
</html>