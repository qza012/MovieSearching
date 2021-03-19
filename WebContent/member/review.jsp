<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>회원이 작성한 리뷰</title>
		<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
	</head>
	<style>
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
	<jsp:include page="../movie/include.jsp"/>
    <hr>
        <table class="table">
            <tr>
                <th>순번</th>
                <th>제목</th>
                <th>영화제목</th>
                <th>평점</th>
                <th>좋아요</th>
                <th>작성일</th>
            </tr>
            <c:forEach items="${review_list}" var="review3">
	            <tr>
	                <td>${review3.idx}</td>
	                <td><a href="../reviewDetail?Idx=${review3.idx}">${review3.subject}</a></td>
	                <td>${review3.movieCode}</td>
	                <td>${review3.score}</td>
	                <td></td>
	                <td>${review3.reg_date}</td>
	            </tr>
            </c:forEach>
        </table>
</body>
	<script>
	</script>
</html>