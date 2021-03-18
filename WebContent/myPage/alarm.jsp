<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>AlarmList</title>
		<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
		<style>
			h3{
				margin-left: 2%;
			}            
			a:link, a:visited{/*링크를 클릭 하기 전*/
				color:darkslategrey;
				text-decoration: none;
				font-size: 14px;
				font-weight: 600;
			}
            a:active{/*링크 실행 시*/
                color: cornflowerblue;
            }
            ::marker {
                font-size: 0px;
            }
            div{
            	float: left;
            }
            .alarmList {
                font-family: Verdana;
                width: 70%;
                margin-top: 40px;
                margin-left: 22%;
            }    
            table {
                text-align: center;
                width: 100%;
            }
            th{
                background-color: lightgrey;
            }
            td{
                background-color: whitesmoke;
            }
            th, td{
                padding: 10px;
                border-bottom: 1px solid darkslategrey;
            }
            #delete{
            	color: red;
            }
            .pageArea{
				width:70%;
				text-align: center;
				margin-top: 1%;
				margin-left: 22%;
			}
			.pageArea span{
				font-size:16px;
				padding: 5px 10px;
				margin: 5px;
			}
			#btn{
				color:black;
			}
			#page{
				color:red;
			}
        </style>
    </head>
    <body>
        <jsp:include page="/movie/include.jsp" />
        <div class="alarmList">
            <h2>알람</h2>
            <table>
         		<tr>
         			<th>알람 번호</th>
         			<th>알람 내용</th>
         			<th>도착 날짜</th>
         			<th>삭제</th>
         		</tr>
				<c:forEach items="${aList}" var="alarm">
					<tr>
	          			<td id="alarmIdx">${alarm.idx}</td>	
                		<td id="alarmContent">${alarm.content}</td>
                		<td id="alarmDate">${alarm.reg_date}</td>
                		<td id="deleteAlarm">
                			<button onclick="location.href='./alarmDel?idx=${alarm.idx}'">삭제</button>
                		</td>
                	</tr>	
                </c:forEach>
        	</table>
        </div>
        <div class="pageArea">
			<span id="btn">
				<c:if test="${currPage == 1}"> 이전</c:if>
				<c:if test="${currPage > 1}">
					<a href="./myReviewList?id=${sessionScope.myLoginId}&page=${currPage-1}">이전</a>
				</c:if>
			</span>
			<span id="page">${currPage}</span>
			<span id="btn">
				<c:if test="${currPage == maxPage}">다음</c:if>
				<c:if test="${currPage < maxPage}">
					<a href="./myReviewList?id=${sessionScope.myLoginId}&page=${currPage+1}">다음</a>
				</c:if>	
			</span>
		</div>
    </body>
	<script>
		var showIf = document.getElementById('myPage').style.display;
		
		function showMyPage(){
			if(showIf = 'none'){
				document.getElementById('myPage').style.display='block';				
			} 
		}
	</script>
</html>