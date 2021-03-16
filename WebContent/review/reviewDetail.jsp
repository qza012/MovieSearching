<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
        div{
            width: 100%;
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
</style>
</head>
<body>
	<input type="hidden" id="review_idx" value="${review.idx}"/>
	
	<table>
        <tr>
        	<th>리뷰 영화</th>
            <td colspan="5">
            	<c:if test="${review.posterURL ne null}">
            		<div>
                    <img src="${review.posterURL}"/>
                	</div>
            	</c:if>
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
            <td colspan="6">
            ${fn:replace(review.content, cn, br)}
            </td>
        </tr>
        <tr>
        	<!-- 세션아이디 가져와서 작성자와 같으면 그냥 냅두고 -->
        	<!-- 작성자와 다른데 좋아요 이미 눌렀으면? -->
        	<!-- 작성자와 다른데 좋아요 안눌렀으면? -->
        	<c:if test="${sessionScope.loginId eq review.id}">
        		<th>좋아요</th>
        	</c:if>
        	<c:if test="${sessionScope.loginId ne review.id}">
        		<c:if test="${reviewLike eq 1}">
        			<th class="like" style="cursor: pointer;  color: red;" onclick="reviewLike(${review.idx})">좋아요 취소</th>
        		</c:if>
        		<c:if test="${reviewLike eq 0}">
        			<th class="like" style="cursor: pointer;" onclick="reviewLike(${review.idx})">좋아요</th>
        		</c:if>
        	</c:if>
            <td id="reviewCntLike">${review.cntLike}</td>
            <!-- 세션에서 아이디 가져와서 작성자와 다르면 보여야함 -->
            <td colspan="2">
            <c:if test="${sessionScope.loginId ne review.id}">
            	<a onclick="reportOpen(${review.idx},2001)" style="cursor: pointer;">신고</a>
            </c:if>
            </td>
            <th>작성일</th>
            <td>${review.reg_date}</td>
        </tr>
    </table>
    <div class="aTag">
	  	<c:if test="${sessionScope.loginId eq review.id}">
	   		<a href="reviewUpdateForm?Idx=${review.idx}">수정</a>
	    	<a onclick="return confirm('정말 삭제하시겠습니까?')" href="reviewDel?Idx=${review.idx}">삭제</a>
	    </c:if>
    </div>

    <div class="list">
    <input type="button" value="리스트" onclick="location.href='./reviewList' "/>
    </div>

    <div style="margin: 30px 10px;">
    <h3>댓글</h3>
    </div>
	
	<table>
		<tr>
			<td>
	        	<input type="hidden" id="comment_idx"/>
	        	<textarea id="comment_content" style="width: 100%; height: 50px; resize: none;"></textarea>
	        </td>
	        <td>
	        	<input type="button" id="save" value="등록"  style="width:20%; height: 50px;"/>
	        	<input type="hidden" id="update" value="수정" style="width:20%; height: 50px;"/>
	        </td>
		</tr> 
	</table>
   

    <table>
        <tr>
            <th>내용</th>
            <th>작성자</th>
            <th>작성일</th>
        </tr>
        <c:forEach items="${comment}" var="comment">
        <tr style="text-align: center;">
            <td> ${fn:replace(comment.content, cn, br)}</td>
            <td>${comment.id}</td>
            <td>${comment.reg_date}</td>
            
            <td style="border-top: white; border-bottom: white; ">
            	<c:if test="${sessionScope.loginId ne comment.id}">
            		<a onclick="reportOpen(${comment.idx},2002)" style="cursor: pointer;">신고</a>
            	</c:if>
            </td>
            <td style="border-top: white; border-bottom: white; text-align: right; cursor: pointer;">
            	<c:if test="${sessionScope.loginId eq comment.id}">
            		<a onclick="update(${comment.idx})">수정</a>
            	</c:if>
            </td>
            <td style="border-top: white; border-bottom: white; text-align: left; cursor: pointer;">
            	<c:if test="${sessionScope.loginId eq comment.id}">
            		<a onclick="return confirm('정말 삭제하시겠습니까?')" href="commentDel?idx=${comment.idx}&review_idx=${review.idx}">삭제</a>
            	</c:if>
            </td>
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
	
	if(content==""){
		alert("내용을 입력해주세요.");
		$("#content").focus();
	}else{
		$.ajax({ 
			type:'post' 
			,url:'./commentWrite' 
			,data:{ 
				'content':content,
				'id':id,
				'review_idx':review_idx,
			}
			,dataType: 'json' 
			,success: function(data){
				console.log(data);
				console.log('data.success');
				if(data.success == 1){
					alert(data.msg);
					location.href = "./reviewDetail?Idx="+review_idx;
				}else{
					alert('댓글 작성에 실패했습니다.');
				}
			}
			,error: function(e){
				console.log(e);
			}
		});
	}
});

function update(comment_idx){
	$.ajax({
		type:'post' 
		,url:'./commentUpdateForm' 
		,data:{
			'comment_idx':comment_idx,
		}
		,dataType: 'json'
		,success: function(data){
			console.log(data);
			console.log(data.content);
			$("#comment_content").val(data.content);
			$("#comment_idx").val(comment_idx);
			$("#save").attr('type', 'hidden');
			$("#update").attr('type', 'button');
		}
		,error: function(e){
			console.log(e);
		}
	});
}

$("#update").click(function(){
	var comment_idx = $("#comment_idx").val();
	var content = $("#comment_content").val();
	var review_idx = $("#review_idx").val();
	console.log(content + " / "+ comment_idx);
	
	if(content==""){
		alert("내용을 입력해주세요.");
		$("#content").focus();
	}else{
		$.ajax({
			type:'post' 
			,url:'./commentUpdate' 
			,data:{
				'content':content,
				'comment_idx':comment_idx,
			}
			,dataType: 'json' 
			,success: function(data){
				console.log(data);
				if(data.success == 1){
					alert(data.msg);
					location.href = "./reviewDetail?Idx="+review_idx;
				}else{
					alert('댓글 수정에 실패했습니다.');
				}
			}
			,error: function(e){
				console.log(e);
			}
		});
	}
});

function reviewLike(review_idx){
	$.ajax({
		type:'post' 
		,url:'./reviewLike' 
		,data:{
			'review_idx':review_idx,
		}
		,dataType: 'json' 
		,success: function(data){
			console.log(data);
			if(data.success == 1){
				location.href = "./reviewDetail?Idx="+review_idx;
			}
		}
		,error: function(e){
			console.log(e);
		}
	});
}

function reportOpen(idx, type_idx){
	window.open("./reviewReportForm?idx="+idx+"&type_idx="+type_idx, "report", "width=600, height=300, left=200, top=100");
	}
	
var msg="${msg}";
	if(msg!=""){
		alert(msg);
	}
</script>
</html>