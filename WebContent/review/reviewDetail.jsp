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
	<input type="hidden" id="review_idx" value="${review.idx}"/>
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
    <a href="reviewUpdateForm?Idx=${review.idx}">수정</a>
    <a href="reviewDel?Idx=${review.idx}">삭제</a>
    </div>

    <div class="list">
    <input type="button" value="리스트" onclick="location.href='./reviewList' "/>
    </div>


    <div style="margin-bottom: 10px;">
    <span>댓글</span>
    </div>

    <div class="comment">
        <div>
        <input type="hidden" id="comment_idx"/>
        <input type="text" id="comment_content" style="width: 90%; height: 50px;"/>
        <input type="button" id="save" value="등록"  style="width:7%; height: 50px;"/>
        <input type="hidden" id="update" value="수정" style="width:7%; height: 50px;"/>
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
            <td style="border-top: white; border-bottom: white; text-align: right; cursor: pointer;"><a onclick="update('${comment.idx}')">수정</a></td>
            <td style="border-top: white; border-bottom: white; text-align: left; cursor: pointer;"><a href="commentDel?idx=${comment.idx}&review_idx=${review.idx}">삭제</a></td>
        </tr>
        </c:forEach>
    </table>
</body>
<script>
$("#save").click(function(){
	var content = $("#comment_content").val();
	var id = "comment"; //세션에서 현재 접속한 사람 아이디 가져와야함
	var review_idx = $("#review_idx").val();
	
	console.log(content + " / " + id + " / " + review_idx);

	$.ajax({ //jquery로 ajax사용
		type:'post' //[GET|POST] 전송방식
		,url:'./commentWrite' //action 어디에 요청할 건지
		,data:{ //parameter , 보낼 데이터 object 형태로 보냄
			'content':content,
			'id':id,
			'review_idx':review_idx,
		}
		,dataType: 'json' //주고 받을 테이터 타입
		,success: function(data){//성공한 내용은 data로 들어옴
			console.log(data);
			console.log('data.success');
			if(data.success == 1){
				alert(data.msg);
				location.href = "./reviewDetail?Idx="+review_idx;
			}else{
				alert('댓글 작성에 실패했습니다.');
			}
		}
		,error: function(e){//실패할 경우 해당 내용이 e로 들어옴
			console.log(e);
		}
	});
});

function update(comment_idx){
	$.ajax({ //jquery로 ajax사용
		type:'post' //[GET|POST] 전송방식
		,url:'./commentUpdateForm' //action 어디에 요청할 건지
		,data:{ //parameter , 보낼 데이터 object 형태로 보냄
			'comment_idx':comment_idx,
		}
		,dataType: 'json' //주고 받을 테이터 타입
		,success: function(data){//성공한 내용은 data로 들어옴
			console.log(data);
			console.log(data.content);
			$("#comment_content").val(data.content);
			$("#comment_idx").val(comment_idx);
			$("#save").attr('type', 'hidden');
			$("#update").attr('type', 'button');
		}
		,error: function(e){//실패할 경우 해당 내용이 e로 들어옴
			console.log(e);
		}
	});
}

$("#update").click(function(){
	var comment_idx = $("#comment_idx").val();
	var content = $("#comment_content").val();
	var review_idx = $("#review_idx").val();
	console.log(content + " / "+ comment_idx);

	$.ajax({ //jquery로 ajax사용
		type:'post' //[GET|POST] 전송방식
		,url:'./commentUpdate' //action 어디에 요청할 건지
		,data:{ //parameter , 보낼 데이터 object 형태로 보냄
			'content':content,
			'comment_idx':comment_idx,
		}
		,dataType: 'json' //주고 받을 테이터 타입
		,success: function(data){//성공한 내용은 data로 들어옴
			console.log(data);
			if(data.success == 1){
				alert(data.msg);
				location.href = "./reviewDetail?Idx="+review_idx;
			}else{
				alert('댓글 수정에 실패했습니다.');
			}
		}
		,error: function(e){//실패할 경우 해당 내용이 e로 들어옴
			console.log(e);
		}
	});
});

var msg="${msg}";

if(msg!=""){
	alert(msg);
}
</script>
</html>