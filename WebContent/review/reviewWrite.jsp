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
    <h3 class="title">리뷰 작성하기</h3>
    <table>
        <tr>
            <th>제목</th>
            <td><input type="text" id="subject" style="width: 97%;"/></td>
            
            <th>작성자</th>
            <td>세션아이디에서 가져오기</td>
        </tr>
        
        <tr>
        <th>영화제목</th>
        <td>
        	<input type="hidden" id="movieCode" value="19398001"/>
            <input type="text" id="movieName" value="오즈의 마법사" style="width: 80%;"/>
            <input type="button" value="검색" onclick="location.href='./movieSearch.jsp' "/>
        </td>
        
        <th>평점</th>
        <td>
            <select id="score" class="star">
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
            </select>
        </td>
        </tr>
        
        <tr>
            <td colspan="4">
                <textarea id="content" rows="0" cols="0"></textarea>
            </td>
        </tr>
        
    </table>
    <div class="button">
        <input type="button" id="save" value="저장"/>
        <input type="button" value="취소" onclick="location.href='../reviewList' "/>
    </div>
</body>
<script>
$("#save").click(function(){
	
	var subject = $("#subject").val();
	var id = "juju";
	var movieCode = $('#movieCode').val();
	var movieName = $("#movieName").val();
	var score = $("#score").val();
	var content = $('#content').val(); 
	
	console.log(subject+" / "+id + " / " + movieCode + " / " + movieName + " / " + score + " / " + content);

	$.ajax({ //jquery로 ajax사용
		type:'post' //[GET|POST] 전송방식
		,url:'../reviewWrite' //action 어디에 요청할 건지
		,data:{ //parameter , 보낼 데이터 object 형태로 보냄
			'subject':subject,
			'id':id,
			'movieCode':movieCode,
			'movieName':movieName,
			'score':score,
			'content':content
		}
		,dataType: 'json' //주고 받을 테이터 타입
		,success: function(data){//성공한 내용은 data로 들어옴
			console.log(data);
			console.log('data.success');
			if(data.success == 1){
				alert(data.msg);
				location.href = "../reviewList";
			}else{
				alert('리뷰 작성에 실패했습니다.');
			}
		}
		,error: function(e){//실패할 경우 해당 내용이 e로 들어옴
			console.log(e);
		}
	});
	
});
</script>
</html>