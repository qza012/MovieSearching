<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Main</title>
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
           #myPage{
            	display: none;
            }
           .mainData{
               font-family: Verdana;
               width: 50%;
               margin-top: 40px;
               margin-left: 30%;
           }    
           table,input[type="text"]{
               text-align: center;
               width: 70%;
               height: 20px;
               
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
       </style>
   </head>
   <body>
        <jsp:include page="/movie/include.jsp" />
       <div class="mainData">
           <h1>로그인</h1>
           <form action="./loginForMyPage" method="post">
	           <table>
	               <tr>
	                   <th>아이디</th>
	                   <td>
	                   		<input type="text" id="userId" name="userId"/>
	                   </td>
	               </tr>
	           		<tr>
	                   <th>비밀번호</th>
	                   <td>
	                   		<input type="text" id="userPw" name="userPw"/>
	                   </td>
	               </tr>
	               <tr>
	               		<td colspan="2">
	               			<input type="submit" value="로그인"/>
	               		</td>
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