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
        table,td,th{
            border-top: 1px solid lightgray;
            border-radius: 1px solid lightgray;
            border-collapse: collapse;
        }
        th,td{
            padding: 10px;
        }
        div{
            width: 70%;
        }
        .aTag{
            text-align: right;
        }
        a:link,
        a:hover,
        a:visited,
        a:active{
            color: black;
            text-decoration: none;
        }
        .list{
            text-align: center;
        }
        .comment{
            margin-bottom: 10px;

        }

    </style>
</head>
<body>
	<table>
        <tr>
            <td colspan="6">
                <div>
                    ${review.posterURL}
                </div>
                <div>
                    ${review.movieName}
                </div>
            </td>
        </tr>
        <tr>
            <th>제목</th>
            <td>${review.subject}</td>
            <th>평점</th>
            <td>${review.score}</td>
            <th>작성자</th>
            <td>${review.id}</td>
        </tr>
        <tr>
            <td colspan="6">${review.content}</td>
        </tr>
        <tr>
            <th>좋아요</th>
            <td>${review.cntLike}</td>
            <td colspan="2"><a href="./review/report.jsp" target="_blank">신고</a></td>
            <th>작성일</th>
            <td>${review.reg_date}</td>
        </tr>
    </table>

    <div class="aTag">
    <a href="#">수정</a>
    <a href="#">삭제</a>
    </div>

    <div class="list">
    <input type="button" value="리스트" onclick="location.href='./reviewList' "/>
    </div>


    <div style="margin-bottom: 10px;">
    <sapn>댓글</sapn>
    </div>

    <div class="comment">
        <div>
        <input style="width: 90%; height: 50px;"/>
        <input type="button" value="등록"  style="width:7%; height: 50px;"/>
        </div>
    </div>

    <table>
        <tr>
            <th>내용</th>
            <th>작성자</th>
            <th>작성일</th>
        </tr>
        <c:forEach items="${comment}" var="comment">
        <tr>
            <td>${comment.content}</td>
            <td>${comment.id}</td>
            <td>${comment.reg_date}</td>
            <td style="border-top: white; border-bottom: white; "><a href="./review/report.jsp" target="_blank">신고</a></td>
            <td style="border-top: white; border-bottom: white; text-align: right;"><a href="#">수정</a></td>
            <td style="border-top: white; border-bottom: white; text-align: left;"><a href="#">삭제</a></td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>