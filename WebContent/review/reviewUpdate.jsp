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
        .button{
            width: 70%;
            text-align: center;
        }
        textarea{
            width: 99%;
            height: 500px;
            resize: none;
        }
        .title{
            width: 70%;
            text-align: center;
        }
    </style>
</head>
<body>
    <h3 class="title">리뷰 수정하기</h3>
    <input type="hidden" id="idx" value="${review.idx}"/>
    <table>
        <tr>
            <th>제목</th>
            <td><input type="text" id="subject" value="${review.subject}" style="width: 97%;"/></td>
            
            <th>작성자</th>
            <td><input type="hidden" id="id" value="${review.id}" />${review.id}</td>
        </tr>
        
        <tr>
        <th>영화제목</th>
        <td>
        	<input type="hidden" id="movieCode" value="${review.movieCode}"/>
            <input type="text" id="movieName" value="${review.movieName}" readonly style="width: 97%;" readonly/>
        </td>
        
        <th>평점</th>
        <td>
            <select id="score" class="star">
            
             <c:forEach begin="1" end="5" var="i">
             	<c:if test="${review.score eq i}">
             		<option value="${review.score}" selected>${review.score}</option>
             	</c:if>
             	<c:if test="${review.score ne i}">
             		<option value="${i}">${i}</option>
             	</c:if>
             </c:forEach>
             
            </select>
        </td>
        </tr>
        
        <tr>
            <td colspan="4">
                <textarea id="content">${review.content}</textarea>
            </td>
        </tr>
        
    </table>
    <div class="button">
        <input type="button" id="save" value="저장"/>
        <input type="button" value="취소" onclick="location.href='./reviewList' "/>
    </div>
   </body>
   <script>
$("#save").click(function(){
	
	var idx = $('#idx').val();
	var subject = $("#subject").val();
	var id =  $("#id").val();
	var movieCode = $('#movieCode').val();
	var movieName = $("#movieName").val();
	var score = $("#score").val();
	var content = $('#content').val(); 
	
	console.log(idx + " / " + subject+" / "+id + " / " + movieCode + " / " + movieName + " / " + score + " / " + content);

	$.ajax({ 
		type:'post'
		,url:'./reviewUpdate' 
		,data:{
			'idx':idx,
			'subject':subject,
			'id':id,
			'movieCode':movieCode,
			'movieName':movieName,
			'score':score,
			'content':content
		}
		,dataType: 'json'
		,success: function(data){
			console.log(data);
		
			alert(data.msg);
			location.href = "./reviewDetail?Idx="+idx;
		}
		,error: function(e){
			console.log(e);
		}
	});
	
});
</script>
</html>