<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Withdraw</title>
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
           	.withdraw{
				text-align: center;
                font-family: Verdana;
                border: 3px solid darkgrey;
                background-color: whitesmoke;
                border-collapse: collapse;
                padding: 1%;
                margin-top: 10%;
                margin-left: 35%;
                width: 40%;
            }
           .conPW{
           		text-align:center;
            }
            input[type="text"]{
            	width: 50%;
            	height: 20px;
            	margin-top: 10%;
            }
            input[type="submit"]{
            	margin: 10% 30%;
            }
       </style>
   </head>
   <body>
       <jsp:include page="/movie/include.jsp" />
       <form action="./withdraw">
        	<div class="withdraw">
            	<h3>회원 탈퇴</h3>
            	<span class="conPW">
                	비밀번호 &nbsp;
                    <input type="text" name="userPw" placeholder="현재 비밀번호"/>
       			</span>       
	    		<input type="submit" value="회원 탈퇴"/>
        	</div>
       	</form>
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