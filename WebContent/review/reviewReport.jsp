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
			<th>신고자 아이디</th>
            <td>
               ${sessionScope.loginId}
            </td>
		</tr>
		<tr>
			<th>신고 유형</th>
            <td>
            	<input type="hidden" id="report_idx" value="${idx}"/>
                <c:if test="${type_idx eq 2001}">
                	<input type="hidden" id="type_idx" value="${type_idx}"/> 리뷰
                </c:if>
                <c:if test="${type_idx eq 2002}">
                	<input type="hidden" id="type_idx" value="${type_idx}"/>댓글
                </c:if>
            </td>
		</tr>
        <tr>
            <th>신고사유</th>
            <td>
                <textarea id="content"></textarea>
            </td>
        </tr>
    </table>
	
    <div class="button">
        <input type="submit" id="report" value="신고"/>
        <input type="button" value="취소" onclick="cancel()"/>
    </div>
</body>
<script>
var msg = "${msg}";
if(msg != ""){
	alert(msg);
	window.close();
}

$("#report").click(function(){
	var type_idx =  $("#type_idx").val() //2001 | 2002
	var report_idx = $("#report_idx").val(); //글번호
	var content = $("#content").val(); //내용
	
	console.log(type_idx + report_idx + content);

	$.ajax({ 
		type:'post' 
		,url:'./reviewReport'
		,data:{ 
			'type_idx':type_idx,
			'report_idx':report_idx,
			'content':content
		}
		,dataType: 'json' 
		,success: function(data){
			console.log(data);
			if(data.success == 1){
				alert(data.msg);
				window.close();
			}else{
				alert(data.msg);
			}
		}
		,error: function(e){
			console.log(e);
		}
	});
});

function cancel(){
	var check = confirm('작성한 내용은 저장되지 않습니다. 취소하시겠습니까?');
	if(check){
		window.close()
	}
}
</script>
</html>