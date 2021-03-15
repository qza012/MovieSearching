<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>pw question 수정</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
</head>
	<body>
		<a>변경할 내용 입력</a>
		<input class="content" type="text" name="url"/>
		<button onclick="finish()">완료</button>
	</body>
	<script>
		function finish() {
			opener.parent.updateContent($(".content").val());
			self.close();
		}
	</script>
</html>