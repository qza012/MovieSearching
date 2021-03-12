<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset=UTF-8>
		<title>Insert title here</title>
		<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
		<style>
			table, th, td{
				border: 1px solid black;
				border-collapse: collapse;
				padding: 5px 10px;
			}
		</style>
	</head>
	<body>
		<h3>회원리스트</h3>
		<hr/>
		<table>
		<tr>
			<th>아이디</th><th>이름</th><th>나이</th><th>성별</th><th>이메일</th><th>탈퇴여부</th><th>활성화 여부</th>
		</tr>
		<c:forEach items="${list }" var="member">
		<tr>
			<td>${member.id }</td>
			<td>${member.name }</td>
			<td>${member.age }</td>
			<td>${member.gender}</td>
			<td>${member.email }</td>
			<td>${member.withdraw }</td>
			<td id="${member.id }">${member.disable }</td>
			<td>
				<c:if test="${member.disable == 'y' || member.disable == 'Y'}">
					<button value="${member.id }">비활성화</button>
				</c:if>
				<c:if test="${member.disable == 'n' || member.disable == 'N'}">
					<button value="${member.id }">활성화</button>
				</c:if>
			</td>
		</tr>
		</c:forEach>
		</table>
	</body>
	<script>
		var msg = "${msg}";
		if(msg != "") {
			alert(msg);
		}
		
		$('button').click(function() {
			var button = $(this);
			var flag = $("#"+this.value);
		
			//console.log(button);
			//console.log(flag);
			
			$.ajax({
				type:'POST'
				,url:'toggleMemberDisable'
				,data:{'id' : this.value}
				,dataType:'JSON'
				,success:function(data) {

					if(data.disable == "Y") {
						flag.html("Y");
						button.html("비활성화");
					} else {
						flag.html("N");
						button.html("활성화");
					}

				},error:function(e) {
					console.log("활성화/비활성화 버튼 비동기 에러");
				}
			})	
		})
		
	</script>
</html>