<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
		<title>회원 목록</title>
		<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
	</head>
<style>
    #top{
        text-align: right;
    }
    a{
        text-decoration: none;
    }
    #search{
        text-align: center;
        margin-top: 7%;
    }
    input[type=text]{
        width: 500px;
        height: 22px;
    }
    select,input[type=submit]{
        height: 30px;
        width: 70px;
    }
    table {
        border-collapse: separate;
        border-spacing: 0;
        text-align: center;
        line-height: 1.5;
        border-top: 1px solid #ccc;
        border-left: 1px solid #ccc;
        margin : auto;
    }
    th {
        width: 100px;
        padding: 10px;
        font-weight: bold;
        vertical-align: top;
        border-right: 1px solid #ccc;
        border-bottom: 1px solid #ccc;
        border-top: 1px solid #fff;
        border-left: 1px solid #fff;
        background: #eee;
    }
    td {
        width: 150px;
        padding: 10px;
        vertical-align: top;
        border-right: 1px solid #ccc;
        border-bottom: 1px solid #ccc;
    }
    .table{
        margin-top: 10px;
    }
 	#topMenu { 
        height: 30px;
        position: relative;
        float: left;
        left: 10%;
        margin-top: 50px;
    } 
    #topMenu ul {
        list-style-type: none;
        margin: 0px;
        padding: 0px;
    } 
    #topMenu ul li {
        color: white;
        background-color: #2d2d2d;
        float: left;
        line-height: 30px;
        vertical-align: middle;
        text-align: center;
        position: relative;
    } 
    .menuLink, .submenuLink {
        text-decoration: none;
        display: block;
        width: 200px;
        font-size: 12px;
        font-weight: bold;
    }
    .menuLink {
        color: white;
    }
    .topMenuLi:hover .menuLink {
        color: lightblue;
        background-color: #4d4d4d;
    }
    .longLink {
        width: 200px;
    }
    .submenuLink {
        color: #2d2d2d;
        background-color: white;
        border: solid 1px black;
        margin-right: -1px;
    }
    .submenu {
        position: absolute;
        height: 0px;
        overflow: hidden;
        transition: height .2s;
        width: 574px;
    }
    .topMenuLi:hover .submenu {
        height: 32px;
    } 
    .submenuLink:hover {
        background-color: #dddddd;
    }
    .pageArea{
		width:800px;
		text-align: center;
		margin: 10px;
		margin-top: 50px;
		position: relative;
		float: left;
		left: 30%
	}
	.pageArea span{
		font-size: 18px;
		border : 1px solid lightgray;
		padding: 2px 10px;		
		margin: 5px;		
		color : gray;
	}
	#page{
		font-weight: 600;
		color: red;
	}
	h4{
		position: relative;
		left: 20%;
		float: left;
		z-index: 5;
		color: white;
		font-size: 25px;
		margin-top: 5px;
	}
</style>
<body>
	<jsp:include page="../movie/include.jsp" />
    <hr>
    <h4>인기 리뷰</h4>
    <div id="member">
        <c:forEach items="${top_list}" var="review3">
	        <nav id="topMenu">
		        <ul>
		            <li class="topMenuLi"> <a class="menuLink" href="../reviewDetail?Idx=${review3.idx}">${review3.subject}</a>
		                <ul class="submenu">
		                    <li><href class="submenuLink longLink">평점:${review3.score}</href></li>
		                </ul>
		            </li>
	        	</ul>
	    	</nav>
        </c:forEach>
    </div>
    <div id="search">
        <form action="search" method="GET">
            <select id="select" name="search">
                <option value="id">ID</option>
                <option value="name">이름</option>
                <option value="gender">성별</option>
            </select>
            <input type="text" value="${param.keyWord}" name="keyWord" placeholder="검색어를 입력하세요">
            <input type="submit" value="검색">
        </form>
    </div>
    <div class="table">
        <table>
            <tr>
                <th>아이디</th>
                <th>이름</th>
                <th>성별</th>
                <th>선호장르</th>
                <th>팔로우</th>
            </tr>
            <c:forEach items="${member_list}" var="member3">
	            <tr>
	                <td><a href="memReviewList?id=${member3.id}">${member3.id}</a></td>
	                <td>${member3.name}</td>
	                <td>${member3.gender}</td>
	                <td>${member3.genre}</td>
	           		<td>
	           			<c:if test="${member3.id eq sessionScope.myLoginId}">
	           			</c:if>
	           			<c:if test="${member3.id ne sessionScope.myLoginId}">
	           				<c:if test="${member3.follow_check eq 0}">
	     						<button id="${member3.id}" onclick="follow('${member3.id}')">팔로우</button>
	     					</c:if>
	           				<c:if test="${member3.follow_check eq 1}">
	           					<button id="${member3.id}" onclick="follow('${member3.id}')" >팔로우 취소</button>
	           				</c:if>
	           			</c:if>
	               		
	                </td>
                </tr>
            </c:forEach>
        </table>
    </div>
   <div class="pageArea">
			<span>
				<c:if test="${currPage == 1}">이전</c:if>
				<c:if test="${currPage > 1}">
					<a href="member?page=${currPage-1}">이전</a>
				</c:if>				
			</span>
			<span id="page">${currPage}</span>
			<span>
				<c:if test="${currPage == maxPage}">다음</c:if>
				<c:if test="${currPage < maxPage}">
					<a href="member?page=${currPage+1}">다음</a>
				</c:if>				
			</span>
		</div>
</body>
<script>
function follow(id){
	var id = id;
	console.log(id);
	$.ajax({
		type:'post' 
		,url:'/MovieSearching/member/memberFollow' 
		,data:{
			'target_id':id
		}
		,dataType: 'json' 
		,success: function(data){
			console.log(data);
			if(data.success == 1){
				if(data.follow_check == 0){ //팔로우 안한 경우
					$("#"+id).html('팔로우 취소');
				console.log($(".id"));
				}else{ //팔로우 한경우
					$("#"+id).html('팔로우');
				}
				location.href="/MovieSearching/member/member?page=${currPage}";
			}
		}
		,error: function(e){
			console.log(e);
		}
	});
}
</script>
</html>