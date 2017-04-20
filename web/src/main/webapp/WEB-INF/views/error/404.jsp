<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/static/inc/main.inc" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8"/>
    <title>404</title>
    <meta name="description" content="Error 404 - Page Not Found"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

</head>
<!--Head Ends-->
<!--Body-->
<body class="body-404">
<div class="error-header"></div>
<div class="container ">
    <section class="error-container text-center">
        <h1>404</h1>

        <div class="error-divider">
            <h2></h2>

            <p class="description">对不起，您访问的页面不存在或权限不够！</p>
        </div>
        <a href="${ctx}/main" class="return-btn" target="_top"><i class="fa fa-home"></i>Home</a>
    </section>
</div>
<%@include file="/static/inc/footer.inc" %>
</body>
<!--Body Ends-->
</html>
