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
        margin-top: 10%;
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
     #member{
        margin-top: 100px;
        position: relative;
        margin-left: 30%;
    }
    #member a{
        display: block;
    }
    #member th:hover{
        background-color: lightgray;
    }
</style>
<body>
    <div id="top">
        <a href="logout">로그아웃</a>
        &nbsp;&nbsp;
        <a href="#">알람</a>
    </div>
    <hr>
    <h3>인기 회원</h3>
    <div id="member">
        <table>
	        <c:forEach items="${top_list}" var="review3">
	            <tr>
	                <th><a href="#">${review3.subject}</a></th>
	            </tr>
	            <tr>
	                <td>${review3.score}</td>
	            </tr>
	         </c:forEach>
        </table>
    </div>
    <div id="search">
        <form action="search" method="GET">
            <select id="select" name="search">
                <option value="id">ID</option>
                <option value="이름">이름</option>
                <option value="나이">나이</option>
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
	var msg ="${msg}";
	if(msg != ""){
		alert(msg);
	}
</script>
</html>