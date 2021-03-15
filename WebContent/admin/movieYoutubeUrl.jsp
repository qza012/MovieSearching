<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>youtube URL 수정</title>
</head>
	<body>
		<a>youtube url 입력</a>
		<input class="url" type="text" name="url"/>
		<button onclick="finish()">완료</button>
	</body>
	<script>
		var urlBox = opener.urlBox;
		
		function finish() {
			
			$(opener.location).attr("href", "javascript:updateYoutubeUrl($('.standard').val(), urlBox);");
			
			self.close();
		}
	</script>
</html>