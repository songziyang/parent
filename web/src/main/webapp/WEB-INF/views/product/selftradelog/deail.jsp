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
			<li><i class="fa fa-home"></i> <a href="#">私人订制还款</a></li>
			<li><a href="#">私人订制还款</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>私人订制还款</h1>
		</div>
		<!--Header Buttons-->
		<div class="header-buttons">
			<a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i></a>
			<a class="refresh" id="refresh-toggler"
				href="${ctx}/product/selfTradeLog/list"><i
				class="glyphicon glyphicon-refresh"></i></a> <a class="fullscreen"
				id="fullscreen-toggler" href="#"> <i
				class="glyphicon glyphicon-fullscreen"></i></a>
		</div>
		<!--Header Buttons End-->
	</div>
	<!-- /Page Header -->
	<!-- Page Body -->
	<form action="${ctx}/product/selfTradeLog/list" method="post"
		id="listForm" name="listForm">
		<div class="page-body">
			<div class="row">
				<div class="col-xs-12 col-md-12">
					<div class="widget">
						<div class="widget-header  with-footer">
							<span class="widget-caption">私人订制还款</span>
							<div class="widget-buttons">
								<a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i></a>
								<a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i></a>
							</div>
						</div>
						<div class="widget-body">
							<div class="flip-scroll">
								<table class="table table-hover table-striped table-bordered">
									<thead style="font-size: 16px; font-weight: bold;">
										<tr>
											<th width="80" style="text-align: center;">序号</th>
											<th>收益金额</th>
											<th>操作时间</th>
											<th>审核状态</th>
											<th>收益描述</th>
										</tr>
									</thead>
									<tbody style="font-size: 12px;">
										<c:forEach items="${page.content}" var="selfRevenue"
											varStatus="status">
											<tr>
												<td class="table_no" width="80" align="center"></td>
												<td><c:out value="${selfRevenue.revenue}" /></td>
												<td><fmt:formatDate value="${selfRevenue.createDate}"
														pattern="yyyy年MM月dd日 HH:mm:ss" /></td>
												<td>
													<c:choose>
														<c:when test="${selfRevenue.status eq 0}">审核中</c:when>
														<c:when test="${selfRevenue.status eq 1}">审核成功</c:when>
														<c:when test="${selfRevenue.status eq 2}">审核失败</c:when>

													</c:choose>
												</td>
												<td><c:out value="${selfRevenue.remark}" /></td>
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
		</div>
	</form>
	<%@include file="/static/inc/footer.inc"%>
</body>
</html>
