<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset=UTF-8>
		<title>Insert title here</title>
		<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
		<style>
			table {
				width: 100%;
				color: white;
			}
			table, th, td{
				border: 1px solid black;
				border-collapse: collapse;
				padding: 5px 10px;
				text-align: center;
			}
			#basic {
				color: white;
			}
			/*
			select{
					width : 100px;
					height : 26px;
					margin-right: 8px ;
			}
			.searchInput{
				width : 320px;
				height : 20px;
			}
			input[type='submit']{
				width : 80px;
				height : 26px;
				margin-top :10px;
				margin-left: 8px;
			}
			th{
					padding :0px 8px 0px 8px;
					border-right: 1px solid #cdcdcd;
				}
			tr>td{
				padding : 5px 10px 5px 10px;
			}
			h3{
					padding: 40px 180px 0px;
				}
			#back, #next, #page {
		            display: inline-block;
		            width: 30px;
		            height: 25px;
		            border: 1px solid #cdcdcd;
		            color: #000000;
		            font-size: 11px;
		            border-collapse: collapse;
		            line-height:25px;
		            margin-top : 50px;
       		 	}
			*/
       		 	}
       		 	#page{
					border-radius: 4px;	
					padding : 10px;
					margin : 10px;
				}
				#pageNum{
					padding : 10px;
					margin : 10px;
				}
				#curPageNum{
					padding : 10px;
					margin : 10px;
					font-weight: bold;
					font-size: 30px;
				}

       		 	a:link{
					color: red;
					font-weight: bold;
       			}
       			
       			a:hover {
       				color : blue;
					text-decoration: underline;
				}
		</style>
	</head>
	<body>
	<jsp:include page="../movie/include.jsp" />
	<div id="basic" class="basic">
		<div id="container">
			<div id="content">
				<div class="main">
					<input id="storeCurStandard" type="hidden" value="${standard}"/>
					<input id="storeCurKeyWord" type="hidden" value="${keyWord}"/>
					<h3>회원 관리</h3>
					<div>
						<button value="move">회원 비활성화 관리</button>
						<button value="move" onclick="location.href='pwQuestionList'">비밀번호 찾기 질문 관리</button>
					</div>
					<div align="center">
						<form action="memberDisableList" method="GET">
						    <select class="standard" name="standard" onchange=changeSearchInput(this.value)>
						    	<option value="all">전체</option>
						        <option value="id">아이디</option>
						        <option value="name">이름</option>
						        <option value="age">나이</option>
						        <option value="gender">성별</option>
						        <option value="email">이메일</option>
						        <option value="withdraw">탈퇴 여부</option>
						       	<option value="disable">비활성화 여부</option>
						    </select>
							<input class="searchInput" type="text" name="keyWord" value="${keyWord }" readonly/>
						    <input type="submit" value="검색"/>
						</form>
					</div>
					<hr/>
					<table>
						<tr>
							<th>아이디</th><th>이름</th><th>나이</th><th>성별</th><th>이메일</th><th>탈퇴여부</th><th>활성화 여부</th>
						</tr>
						<c:forEach items="${list }" var="member">
						<tr>
			
							<td><a href="../member/memReviewList?id=${member.id }">${member.id }</a></td>
							<td>${member.name }</td>
							<td>${member.age }</td>
							<td>${member.gender}</td>
							<td>${member.email }</td>
							<td>${member.withdraw }</td>
							<td id="${member.id }">${member.disable }</td>
							<td>
								<%-- <c:if test="${member.disable == 'y' || member.disable == 'Y'}">
									<button value="${member.id }">비활성화</button>
								</c:if>
								<c:if test="${member.disable == 'n' || member.disable == 'N'}">
									<button value="${member.id }">활성화</button>
								</c:if> --%>
								<c:if test="${member.disable == 'y' || member.disable == 'Y'}">
									<button value="${member.id }" onclick='toggleDisable(this)'>비활성화</button>
								</c:if>
								<c:if test="${member.disable == 'n' || member.disable == 'N'}">
									<button value="${member.id }" onclick='toggleDisable(this)'>활성화</button>
								</c:if>
							</td>
						</tr>
						</c:forEach>
					</table>
					<div id='paging' align="center">
						<span>
							<c:if test="${curPage == 1 }">이전</c:if>
							<c:if test="${curPage > 1 }">
								<a id='pageNum' href="javascript:pageMove('${curPage-1}');">이전</a>
							</c:if>
							<c:if test="${curPage - 4 > 1}">
								<a id='pageNum' href="javascript:pageMove(1);">1</a>
								...
							</c:if>
						</span>
						
						<span id="page">
	
						</span>
						
						<span>
							<c:if test="${curPage + 4 < maxPage}">
								...
								<a id='pageNum' href="javascript:pageMove('${maxPage}');">${maxPage}</a>
							</c:if>
							<c:if test="${curPage == maxPage }">다음</c:if>
							<c:if test="${curPage < maxPage }">
								<a id='pageNum' href="javascript:pageMove('${curPage+1}');">다음</a>
							</c:if>
						</span>
					</div>	
				</div>
			</div>
		</div>
	</div>
	</body>
	<script>
		var msg = "${msg}";
		if(msg != "") {
			alert(msg);
		};
		
		// 최초 불러올 때 실행하는 함수들.
		$(document).ready(function() {
			staySelectBoxValue();
			changeSearchInput($(".standard").val());
			setPageNum();
		});
		
		// 활성화 버튼 비동기 통신.
		function toggleDisable(buttonObj) {
			
			var button = $(buttonObj);
			var flag = $("#"+buttonObj.value);
		
			console.log(button);
			console.log(flag);
			
			$.ajax({
				type:'GET'
				,url:'toggleMemberDisable'
				,data:{'id' : buttonObj.value}
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
		};
		
		
		// input박스 자동 교체.
		function changeSearchInput(value) {
			var searchInput = $(".searchInput");
			//console.log(value)
			
			switch(value) {
			case "all" :
				searchInput.replaceWith(
						"<input class='searchInput' type='text' name='keyWord' readonly/>"
				);
				break;
				
			case "id" :
			case "name" :
			case "age" :		
			case "email" :
				searchInput.replaceWith(
						"<input class='searchInput' type='text' name='keyWord' />"
						);	
				break;
				
			case "gender" :
				searchInput.replaceWith(
						"<select class='searchInput' name='keyWord'>"
				    	+"<option value='male'>남</option>"
				    	+"<option value='female'>여</option>"
				    	+"</select>"
				);
				break;
				
			case "withdraw" :
			case "disable" :
				searchInput.replaceWith(
						"<select class='searchInput' name='keyWord'>"
				    	+"<option value='Y'>Y</option>"
				    	+"<option value='N'>N</option>"
				    	+"</select>"
				);
				break;
			}
		};
		
		// 페이지 이동 함수
		function pageMove(pageNum) {
			var standard = $("#storeCurStandard").val();
			var keyWord = $("#storeCurKeyWord").val();
			//console.log(pageNum);
			
			location.href="memberDisableList?curPage=" + pageNum + "&standard=" + standard + "&keyWord=" + keyWord;
		}

		// page 번호 매기기
		function setPageNum() {
			var page$ = $("#page");
			var resultHtml = "";
			
			for(var i=-4; i<5; ++i) {

				var curNum = ${curPage} + i;
				if(1 <= curNum && curNum <= ${maxPage}) {	
					if(i == 0) {
						resultHtml += "<b id='curPageNum'>" + curNum + "</b>";
					} else {
						resultHtml += "<a id='pageNum' href='javascript:pageMove(" + curNum + ")'>" + curNum + "</a>";					
					}
					
				}
			}

			page$.html(resultHtml);
		}
		
		// 셀렉트 박스 속성 유지시키기
		function staySelectBoxValue() {
			var selectBox = $(".standard");
			if("${standard}" == "") {
				selectBox.val("all").prop("selected", true);
			} else{
				selectBox.val("${standard}").prop("selected", true);
			}
			if(selectBox.val() != 'all') {
				$('.searchInput').removeAttr("readonly");
			}
		}
		
	</script>
</html>