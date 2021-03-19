<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>UpdateMember</title>
		<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
       	<style>
 	h2{
 		margin-top: 5%;
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
            #login{
            	float: right;
            }
            #navi{
            	width: 500px;
            }
            .updateMember {
                font-family: Verdana;
                width: 48%;
                margin-top: 5%;
                margin-left: 30%;
            }    
            table {
                text-align: center;
                border:1px solid darkgrey;
                border-collapse: collapse;
                width: 100%;
            }
            th{
                background-color: lightgrey;
            }
            td{
                background-color: whitesmoke;
            }
            th, td{
                padding: 1%;
                border-bottom: 1px solid darkslategrey;
            }
            input[type="text"],select{
                text-align: center;
                width: 70%;
                height: 20px;
            }
            #urlArea{
            	width: 40%;
            	margin-left:1%;
            }
        </style>
    </head>
    <body>
        <jsp:include page="/movie/include.jsp"/>
        <div class="updateMember">
            <h2>회원정보 수정</h2>
            <form action="./update" method="post" enctype="multipart/form-data" >
	            <table>
	                <tr>
	                    <th>아이디</th>
	                </tr>
	                <tr>
	                    <td>
	                        <input type="text" name="userId" id="userId" value="${mDto.id}" readonly="readonly"/>
	                    </td>
	                </tr>
	                <tr>
	                    <th>비밀번호</th>
	                </tr>
	                <tr>
	                    <td>
	                        <input type="text" name="userPw" id="userPw"/>
	                    </td>
	                </tr>
	                <tr>
	                    <th>비밀번호 확인</th>
	                </tr>
	                <tr>
	                    <td>
	                        <input type="text" id="conPw"/>
	                    </td>
	                    <span></span>
	                </tr>
	                <tr>
	                    <th>비밀번호 찾기 질문</th>
	                </tr>
	                <tr>
	                	<td>
	                		<select name="Question">
	                			<c:forEach items="${qList}" var="list" >
	                            	<option value="${list.idx}">${list.content}</option>
	                        	</c:forEach>
	                        </select>
	                   	</td>
					</tr>
	                <tr>
	                    <th>비밀번호 찾기 답변</th>
	                </tr>
	                <tr>
	                    <td>
	                        <input type="text" name="answer" id="answer"/>
	                    </td>
	                </tr>
	                <tr>
	                    <th>이름</th>
	                </tr>
	                <tr>
	                    <td>
	                        <input type="text" name="userName" value="${mDto.name}"/>
	                    </td>
	                </tr>
	                <tr>
	                    <th>나이</th>
	                </tr>
	                <tr>
	                    <td>
	                        <input type="text" name="age" value="${mDto.age}"/>
	                    </td>
	                </tr>
	                <tr>
	                    <th>성별</th>
	                </tr>
	                <tr>
	                    <td>
	                    	<c:if test="${mDto.gender == '남'}">
	                        	<input type="radio" name="gender" value="남" checked="checked"/>남
	                        	<input type="radio" name="gender" value="여" />여
	                    	</c:if>
	                    	<c:if test="${mDto.gender == '여'}">
		                    	<input type="radio" name="gender" value="남"/>남
	                        	<input type="radio" name="gender" value="여" checked="checked"/>여
	                    	</c:if>
	                    </td>
	                </tr>
	                <tr>
	                    <th>이메일</th>
	                </tr>
	                <tr>
	                    <td>
	                        <input type="text" name="email" value="${mDto.email}"/>
	                    </td>
	                </tr>
	                <tr>
	                    <th>선호하는 영화 장르</th>
	                </tr>
	                <tr>
	                    <td>
	                        <select name="genre">
	                            <option value="${mDto.genre}">${mDto.genre}</option>
	                         </select>
	                    </td>
	                </tr>
	                <tr>
	                    <th>프로필 사진 등록</th>
	                </tr>
	                <tr>
	                	<td>
	                		<input type="text" id="urlArea" placeholder="프로필 사진 등록(최대 10MB)" value="${photoPath}"/>
                			<input type="file"  name="photo"/>
		    			</td>
	                <tr>
	                    <th colspan="2">
	                        <input type="submit" value="저장">
	                    </th>
	                </tr>
	            </table>
	    	</form>        
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