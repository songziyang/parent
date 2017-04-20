<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/static/inc/main.inc"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<!--
Beyond Admin - Responsive Admin Dashboard Template build with Twitter Bootstrap 3
Version: 1.0.0
Purchase: http://wrapbootstrap.com
-->

<html xmlns="http://www.w3.org/1999/xhtml">
<!--Head-->
<head>
<meta charset="utf-8" />
<title>Error 500</title>

<meta name="description" content="Error 500" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="assets/img/favicon.png"
	type="image/x-icon">
<!--Head Ends-->
<!--Body-->
<body class="body-500">
	<div class="error-header"></div>
	<div class="container ">
		<section class="error-container text-center">
			<h1>500</h1>
			<div class="error-divider">
				<h2></h2>
				<p class="description">对不起，系统错误，请联系管理员！</p>
			</div>
			<a href="${ctx}/main" class="return-btn" target="_top" ><i class="fa fa-home"></i>Home</a>
		</section>
	</div>
	<%@include file="/static/inc/footer.inc"%>
</body>
<!--Body Ends-->
</html>
