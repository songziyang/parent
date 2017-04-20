<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@include file="/static/inc/main.inc"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh" >
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
<body >

	<!-- Page Breadcrumb -->
	<div class="page-breadcrumbs">
		<ul class="breadcrumb" style="line-height: 40px;">
			<li><i class="fa fa-home"></i> <a href="#">信息管理</a></li>
			<li><a href="#">短信群发记录查询</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>短信群发记录查询</h1>
		</div>
		<!--Header Buttons-->
		<div class="header-buttons">
			<a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
			</a> <a class="refresh" id="refresh-toggler" href="${ctx}/infoMessage/mass/listMass"> <i
				class="glyphicon glyphicon-refresh"></i>
			</a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
				class="glyphicon glyphicon-fullscreen"></i>
			</a>
		</div>
		<!--Header Buttons End-->
	</div>
	<!-- /Page Header -->
	<div class="page-body">
		<!-- Page Body -->
	<form action="${ctx}/infoMessage/mass/listMass" method="post" id="listForm" name="listForm">
			<div class="row">
				<div class="col-xs-12 col-md-12">
					<div class="row">
						<div class="col-lg-12 col-sm-12 col-xs-12">
							<div class="widget collapsed">
								<!--Widget Body-->
							</div>
							<!--Widget-->
						</div>
					</div>
					<div class="widget">
						<div class="widget-header  with-footer">
							<span class="widget-caption">短信群发记录查询列表</span>
							<div class="widget-buttons">
								<a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
								</a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
								</a>
							</div>
						</div>
						<div class="widget-body">
							<div class="flip-scroll">
							<div style="text-align: right;margin-bottom: 10px;">
							
								<a href="${ctx}/infoMessage/mass/listMass"
									class="btn btn-labeled btn-blue" style="width: 100px"> <i
									class="btn-label fa fa-arrow-left"></i>返回
								</a>
								
								</div>
								<table class="table table-hover table-striped table-bordered">
									<thead style="font-size: 16px; font-weight: bold;">
										<tr>
											<th style="text-align: center;">序号</th>
											<th>手机号码</th>
										</tr>
									</thead>
									<tbody style="font-size: 12px;">
										<c:forEach items="${page.content}" var="massDeal"
											varStatus="status">
											<tr>
												<td class="table_no" align="center"></td>
												<td><c:out value="${massDeal.mobile}"/></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<tags:pagination page="${page}"></tags:pagination>
							</div>
						</div>
					</div>
				</div>
			</div>
	</form>
	</div>
	<%@include file="/static/inc/footer.inc"%>
</body>
</html>

<script src="${ctx}/static/lib/beyond/js/charts/easypiechart/jquery.easypiechart.js"></script>
<script src="${ctx}/static/lib/beyond/js/charts/easypiechart/easypiechart-init.js"></script>
<script type="text/javascript">
	InitiateEasyPieChart.init();
</script>


