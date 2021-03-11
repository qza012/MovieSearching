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
        table,td,th{
            border-top: 1px solid lightgray;
            border-radius: 1px solid lightgray;
            border-collapse: collapse;
        }
        th,td{
            padding: 10px;
        }
        textarea{
            resize: none;
            width: 90%;
            height: 40px;
        }
        .button{
            width: 100%;
            text-align: center;
            margin-top: 10px;
        }
    </style>
</head>
<body>
	<table>
        <tr>
            <th>신고자ID</th>
            <td>loloo</td>
        </tr>
        <tr>
            <th>신고유형</th>
            <td>리뷰</td>
        </tr>
        <tr>
            <th>신고사유</th>
            <td>
                <textarea></textarea>
            </td>
        </tr>
    </table>
    <div class="button">
        <input type="button" value="신고"/>
        <input type="button" value="취소" onclick="location.href='./reviewList.jsp' "/>
    </div>
</body>
</html>