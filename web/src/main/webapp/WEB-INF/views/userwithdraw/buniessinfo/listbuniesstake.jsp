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
			<h1>前台用户资金列表</h1>
		</div>
		<!--Header Buttons-->
		<div class="header-buttons">
			<a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
			</a> <a class="refresh" id="refresh-toggler" href=""> <i
				class="glyphicon glyphicon-refresh"></i>
			</a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
				class="glyphicon glyphicon-fullscreen"></i>
			</a>
		</div>
		<!--Header Buttons End-->
	</div>
	<!-- /Page Header -->
	<!-- Page Body -->
	<div class="page-body">
		<form action="${ctx}/userinfo/usermoneyinfo/usermoneylist"
			method="post" id="listForm" name="listForm">

			<div class="row">
				<div class="col-xs-12 col-md-12">
					<div class="widget">
						<div class="widget-header  with-footer">
							<span class="widget-caption">前台用户资金列表</span>
							<div class="widget-buttons">
								<a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
								</a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
								</a>
							</div>
						</div>
						<div class="widget-body">
							<div class="flip-scroll">
								<div style="text-align: right; margin-bottom: 10px;"></div>

								<table class="table table-hover table-striped table-bordered">
									<thead style="font-size: 16px; font-weight: bold;">
										<tr>
											<th width="80" style="text-align: center;">序号</th>
											<th>用户名</th>
											<th>账户总额</th>
											<th>账户余额</th>
											<th>银多2.0已投金额</th>
											<th>银多M已投金额</th>
											<th>银多2.0+已投金额</th>
											<th>预投金额</th>
											<th style="text-align: center;">操作</th>
										</tr>
									</thead>
									<tbody style="font-size: 12px;">
										<c:forEach items="${page.content}" var="usermoney"
											varStatus="status">
											<tr>
												<td class="table_no" width="80" align="center"></td>
												<td>
													<c:choose>
														<c:when test="${usermoney.user.userLeve.gradeNum eq 1}"><img src="${ctx}/static/lib/images/vip1.png" /></c:when>
														<c:when test="${usermoney.user.userLeve.gradeNum eq 2}"><img src="${ctx}/static/lib/images/vip2.png" /></c:when>
													</c:choose>
													<c:out value="${usermoney.user.username}" />
												</td>
												<td><c:out value="${usermoney.totalFund}" /></td>
												<td><c:out value="${usermoney.usableFund}" /></td>

												<td><c:out value="${usermoney.dayloanCostInput}" /></td>
												<td><c:out value="${usermoney.tdCostInput}" /></td>
												<td><c:out value="${usermoney.whitenoteTotalFund}" /></td>
												<td><c:out value="${usermoney.blokedFund}" /></td>
												<td align="center"><shiro:hasPermission
														name="usermoney_listoperate">
														<a
															href='${ctx}/userwithdraw/useroperate/findoperation/<c:out value="${usermoney.user.id}"/>'
															class="btn btn-info btn-xs edit"><i
															class="fa fa-file-text"></i>资金</a>

													</shiro:hasPermission> <shiro:hasPermission name="usermoney_listtrade">
														<a
															href='${ctx}/userwithdraw/useroperate/buniesstake/<c:out value="${usermoney.user.id}"/>'
															class="btn btn-info btn-xs edit"><i
															class="fa fa-file-text"></i>交易</a>
													</shiro:hasPermission> <shiro:hasPermission name="usermoney_listredpack">
														<a
															href='${ctx}/userwithdraw/useroperate/finduserredpack/<c:out value="${usermoney.user.id}"/>'
															class="btn btn-info btn-xs edit"><i
															class="fa fa-file-text"></i>红包</a>
													</shiro:hasPermission> <!-- 
													<shiro:hasPermission name="usermoney_listdevote">
														<a
															href='${ctx}/userwithdraw/useroperate/findcontributebyid/<c:out value="${usermoney.user.id}"/>'
															class="btn btn-info btn-xs edit"><i
															class="fa fa-file-text"></i>贡献值</a>
													</shiro:hasPermission>
													 --></td>
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