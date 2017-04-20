<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>出错啦！</title>
</head>
<body>
	

	<div class="error_h1">出错啦！</div>

	<div class="error_text">
		错误信息:
		<p>
			<c:out value="${e.message}"></c:out>
		</p>
	</div>

	<div class="hr"></div>


</body>
</html>