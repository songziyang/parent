<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/static/inc/main.inc"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>银多资本</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	
	<!-- 头部页面 -->
	<%@ include file="/WEB-INF/views/header.jsp"%>
	<div class="main-container container-fluid">
		<div class="page-container">
			<!-- 左侧菜单  -->
			<%@ include file="/WEB-INF/views/left.jsp"%>
			<div class="page-content">
				<iframe name="rightFrame" id="rightFrame" src=""
					style="width: 100%; border: 0px; frameborder: 0px;" scrolling="no" >
					浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架 </iframe>
			</div>
		</div>
	</div>
	<%@include file="/static/inc/footer.inc"%>
</body>
</html>
