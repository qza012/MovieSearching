<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
<title>영화</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<style>
</style>
</head>
<body>
	<jsp:include page="include.jsp" />
	<div id="basic" class="basic">
		<div id="container">
			<div id="content">
				<div class="movie_main">
					<ul style="position: absolute; width: 825px; height: 560px; left: 0%; display: block;">
						<li>
						<c:forEach items="${top}" var="movie">
							<a href="moviedetail?movieCode=${movie.movieCode}"><img src="${movie.posterUrl}" style="width: 159px; height: 280px;" alt="${movie.movieName}" ></a>
						</c:forEach>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	
</script>
</html>