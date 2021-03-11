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
 </style>
</head>
<body>
	<div class="search">
        <input type="text" id="movieName"/>
        <input type="button" value="검색"/>
    </div>
    
    <table>
        <tr>
            <th>영화제목</th>
            <th>장르</th>
            <th>개봉일</th>
            <th>감독</th>
            <th>배우</th>
        </tr>
        
        <tr onclick=" location.href='./reviewList.jsp'">
            <td>어벤져스</td>
            <td>SF</td>
            <td>2018-12-15</td>
            <td>누굴까</td>
            <td>햄식이</td>

    </table>
</body>
</html>