<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>회원 목록</title>
		<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
	</head>
<style>
    #top{
        text-align: right;
    }
    a{
        text-decoration: none;
    }
    #search{
        text-align: center;
        margin-top: 5%;
    }
    input[type=text]{
        width: 500px;
        height: 22px;
    }
    select,input[type=submit]{
        height: 30px;
        width: 70px;
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
        width: 100px;
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
    h3{
        position: relative;
        left: 30%;
    }
    nav{
    	text-align: center;
    }
 	#topMenu { 
        height: 30px;
        position: relative;
        float: left;
        left: 22%;
    } 
    #topMenu ul {
        list-style-type: none;
        margin: 0px;
        padding: 0px;
    } 
    #topMenu ul li {
        color: white;
        background-color: #2d2d2d;
        float: left;
        line-height: 30px;
        vertical-align: middle;
        text-align: center;
        position: relative;
    } 
    .menuLink, .submenuLink {
        text-decoration: none;
        display: block;
        width: 150px;
        font-size: 12px;
        font-weight: bold;
    }
    .menuLink {
        color: white;
    }
    .topMenuLi:hover .menuLink {
        color: lightblue;
        background-color: #4d4d4d;
    }
    .longLink {
        width: 150px;
    }
    .submenuLink {
        color: #2d2d2d;
        background-color: white;
        border: solid 1px black;
        margin-right: -1px;
    }
    .submenu {
        position: absolute;
        height: 0px;
        overflow: hidden;
        transition: height .2s;
        width: 574px;
    }
    .topMenuLi:hover .submenu {
        height: 32px;
    } 
    .submenuLink:hover {
        background-color: #dddddd;
    }
</style>
<body>
    <div id="top">
        <a href="logout">로그아웃</a>
        &nbsp;&nbsp;
        <a href="member/alarm.jsp">알람</a>
    </div>
    <hr>
    <h3>인기 리뷰</h3>
    <div id="member">
        <c:forEach items="${top_list}" var="review3">
	        <nav id="topMenu">
		        <ul>
		            <li class="topMenuLi"> <a class="menuLink" href="#">${review3.subject}</a>
		                <ul class="submenu">
		                    <li><href class="submenuLink longLink">평점:${review3.score}</href></li>
		                </ul>
		            </li>
	        	</ul>
	    	</nav>
        </c:forEach>
    </div>
    <div id="search">
        <form action="search" method="GET">
        <button><a href="member">회원목록 돌아가기</a></button>
            <select id="select" name="search">
                <option value="id">ID</option>
                <option value="name">이름</option>
                <option value="age">나이</option>
            </select>
            <input type="text" value="${param.keyWord}" name="keyWord" placeholder="검색어를 입력하세요">
            <input type="submit" value="검색">
        </form>
    </div>
    <div class="table">
        <table>
            <tr>
                <th>아이디</th>
                <th>이름</th>
                <th>나이</th>
                <th>선호장르</th>
                <th>팔로우</th>
            </tr>
            <c:forEach items="${member_list}" var="member3">
	            <tr>
	                <td><a href="review?id=${member3.id}">${member3.id}</a></td>
	                <td>${member3.name}</td>
	                <td>${member3.age}</td>
	                <td>${member3.genre}</td>
	                <td><input type="button" value="팔로우"></td>
	            </tr>
            </c:forEach>
        </table>
    </div>
</body>
<script>
</script>
</html>