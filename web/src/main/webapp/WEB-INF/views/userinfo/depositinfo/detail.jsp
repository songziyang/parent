<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@include file="/static/inc/main.inc"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>银多资本</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
.form-inline .form-group {
	margin-bottom: 10;
}
</style>
</head>
<body>

	<!-- Page Breadcrumb -->
	<div class="page-breadcrumbs">
		<ul class="breadcrumb" style="line-height: 40px;">
			<li><i class="fa fa-home"></i> <a href="#">前台用户管理</a></li>
			<li><a href="#">用户资金</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>银多2.0M</h1>
		</div>
	</div>
	<!-- /Page Header -->
	<!-- Page Body -->
	<div class="page-body">
		<div class="row">

			<div class="col-lg-3 col-sm-3 col-xs-3">
				<div
					class="databox radius-bordered databox-shadowed databox-graded databox-vertical">
					<div class="databox-top bg-green">
						<div class="databox-icon">
							<i class="fa fa-clock-o"></i>
						</div>
					</div>
					<div class="databox-bottom text-align-center">
						<span class="databox-text">一个月</span> <span class="databox-text">${mon}</span>
					</div>
				</div>
			</div>

			<div class="col-lg-3 col-sm-3 col-xs-3">
				<div
					class="databox radius-bordered databox-shadowed databox-graded databox-vertical">
					<div class="databox-top bg-green">
						<div class="databox-icon">
							<i class="fa fa-clock-o"></i>
						</div>
					</div>
					<div class="databox-bottom text-align-center">
						<span class="databox-text">三个月</span> <span class="databox-text">${smon}</span>
					</div>
				</div>
			</div>
			<div class="col-lg-3 col-sm-3 col-xs-3">
				<div
					class="databox radius-bordered databox-shadowed databox-graded databox-vertical">
					<div class="databox-top bg-green">
						<div class="databox-icon">
							<i class="fa fa-clock-o"></i>
						</div>
					</div>
					<div class="databox-bottom text-align-center">
						<span class="databox-text">六个月</span> <span class="databox-text">${lmon}</span>
					</div>
				</div>
			</div>
			<div class="col-lg-3 col-sm-3 col-xs-3">
				<div
					class="databox radius-bordered databox-shadowed databox-graded databox-vertical">
					<div class="databox-top bg-green">
						<div class="databox-icon">
							<i class="fa fa-clock-o"></i>
						</div>
					</div>
					<div class="databox-bottom text-align-center">
						<span class="databox-text">十二个月</span> <span class="databox-text">${ymon}</span>
					</div>
				</div>
			</div>

		</div>
	</div>
		<%@include file="/static/inc/footer.inc"%>
</body>
</html>