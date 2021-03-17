<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>youtube URL 수정</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<style>
	body{
		padding : 10px 50px;
	}
	input{
		width : 300px;
	}
</style>
</head>
	<body>
		<a>youtube url 입력</a>
		<input class="urlBox" type="text" name="url" align="right"/>
		<button onclick="finish()">완료</button>
	</body>
	<script>
		function finish() {
			opener.parent.updateUrl($(".urlBox").val(), opener.urlBoxSelector);
			self.close();
		}
	</script>
</html>