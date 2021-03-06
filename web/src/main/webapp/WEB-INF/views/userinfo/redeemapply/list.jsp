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

<script type="text/javascript">
	function successProduct(url) {
		bootbox.dialog({
			message : "您确定撤销么？",
			title : "撤销提示",
			className : "modal-darkorange",
			buttons : {
				success : {
					label : "确认",
					className : "btn-default",
					callback : function() {
						window.location.href = url;
					}
				},
				"取消" : {
					className : "btn-default",
					callback : function() {
					}
				}
			}
		});
	}
</script>

</head>
<body>

	<div id="myModal" class="modal fade modal-darkorange" tabindex="-1"
		role="dialog" aria-hidden="true" style="display: none;">
		<div class="modal-dialog">
			<div class="modal-content"></div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>

	<div class="page-breadcrumbs">
		<ul class="breadcrumb" style="line-height: 40px;">
			<li><i class="fa fa-home"></i> <a href="#">银多2.0</a></li>
			<li><a href="#">银多2.0赎回申请列表</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>银多2.0赎回申请列表</h1>
		</div>
		<!--Header Buttons-->
		<div class="header-buttons">
			<a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
			</a> <a class="refresh" id="refresh-toggler"
				href="${ctx}/userinfo/redeemApply/listRedeemApply"> <i
				class="glyphicon glyphicon-refresh"></i>
			</a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
				class="glyphicon glyphicon-fullscreen"></i>
			</a>
		</div>
		<!--Header Buttons End-->
	</div>
	<!-- /Page Header -->
	<!-- Page Body -->
	<form action="${ctx}/userinfo/redeemApply/listRedeemApply"
		method="post" id="listForm" name="listForm">
		<div class="page-body">
			<div class="row">
				<div class="col-xs-12 col-md-12">
					<div class="row">
						<div class="col-lg-12 col-sm-12 col-xs-12">
							<div class="widget collapsed">
								<div class="widget-header bordered-bottom bordered-sky">
									<i class="widget-icon fa fa-arrows-v blue"></i> <span
										class="widget-caption">查询条件</span>
									<div class="widget-buttons">
										<a href="#"> </a> <a href="#" data-toggle="collapse"> <i
											class="fa fa-plus blue"></i>
										</a>
									</div>
									<!--Widget Buttons-->
								</div>
								<!--Widget Header-->
								<div class="widget-body">
									<div class="form-inline" role="form">
										<div class="form-group">
											<label class="control-label no-padding-right">用户名</label> <input
												type="text" name="username" class="form-control"
												placeholder="用户名">
										</div>
										<div class="form-group">
											<label class="control-label no-padding-right">手机号</label> <input
												type="text" name="mobile" class="form-control"
												placeholder="手机号">
										</div>

										<div class="form-group" style="margin-left: 10px">
											<label class="control-label no-padding-right">审核状态</label> <select
												class="form-control" name="status">
												<option value="0">审核中</option>
												<option value="1">赎回中</option>
												<option value="2">赎回成功</option>
												<option value="3">赎回失败</option>
											</select>
										</div>

										<div class="form-group">
											<a href="javascript:searchData();"
												class="btn btn-labeled btn-blue"> <i
												class="btn-label fa fa-search"></i>查询
											</a>
										</div>
									</div>
								</div>
								<!--Widget Body-->
							</div>
							<!--Widget-->
						</div>
					</div>
					<div class="widget">
						<div class="widget-header  with-footer">
							<span class="widget-caption">银多2.0赎回申请列表</span>
							<div class="widget-buttons">
								<a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
								</a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
								</a>
							</div>
						</div>
						<div class="widget-body">
							<div class="flip-scroll">
								<table class="table table-hover table-striped table-bordered">
									<thead style="font-size: 16px; font-weight: bold;">
										<tr>
											<th width="80" style="text-align: center;">序号</th>
											<th>用户名</th>
											<th>手机号</th>
											<th>申请份数</th>
											<th>批准份数</th>
											<th>申请时间</th>
											<th>审核状态</th>
											<th style="text-align: center;">操作</th>
										</tr>
									</thead>
									<tbody style="font-size: 12px;">
										<c:forEach items="${page.content}" var="redeemapply"
											varStatus="status">
											<tr>
												<td class="table_no" width="80" align="center"></td>
												<td>
													<c:choose>
														<c:when test="${redeemapply.user.userLeve.gradeNum eq 1}"><img src="${ctx}/static/lib/images/vip1.png" /></c:when>
														<c:when test="${redeemapply.user.userLeve.gradeNum eq 2}"><img src="${ctx}/static/lib/images/vip2.png" /></c:when>
													</c:choose>
													<a
													href='${ctx}/userinfo/user/listUserInfo/<c:out value="${redeemapply.user.id}" />'>
														<c:out value="${redeemapply.user.username}" />
												</a></td>
												<td><c:out value="${redeemapply.user.mobile}" /></td>
												<td><c:out value="${redeemapply.applyFund}" /></td>
												<td><c:out value="${redeemapply.fund}" /></td>
												<td><fmt:formatDate value="${redeemapply.createDate}"
														pattern="yyyy年MM月dd日 HH时mm分" /></td>
												<td><c:if test="${redeemapply.status eq 0}">审核中</c:if>
													<c:if test="${redeemapply.status eq 1}">赎回中</c:if> <c:if
														test="${redeemapply.status eq 2}">赎回成功</c:if> <c:if
														test="${redeemapply.status eq 3}">赎回失败</c:if></td>
												<td align="center">
												<shiro:hasPermission name="redeemapply_audit">
														<!-- 
														<c:if
															test="${redeemapply.status eq 0 || redeemapply.status eq 1 }">
															<a data-toggle="modal" data-target="#myModal"
																href='${ctx}/userinfo/redeemApply/redeemapplyAudit/<c:out value="${redeemapply.id}"/>'
																class="btn btn-blue btn-sm" style="width: 95px"><i
																class="fa fa-edit"></i>审核</a>

															<a
																onclick="successProduct('${ctx}/userinfo/redeemApply/redeemapplyCancel/<c:out value="${redeemapply.id}"/>')"
																class="btn btn-blue btn-sm" style="width: 95px"><i
																class="fa  fa-warning"></i>撤销</a>
														</c:if>
													-->
													<c:choose>
														<c:when test="${redeemapply.status eq 0 ||redeemapply.status eq 1 }">
															<a
																href='${ctx}/userinfo/redeemApply/auditInfo/<c:out value="${redeemapply.id}"/>'
																class="btn btn-blue btn-sm" style="width: 95px"><i
																class="fa fa-edit"></i>审核</a>
														</c:when>
														<c:otherwise>
															<a
																	href='${ctx}/userinfo/redeemApply/auditInfo/<c:out value="${redeemapply.id}"/>'
																	class="btn btn-blue btn-sm" style="width: 95px"><i
																	class="fa fa-edit"></i>详细</a>
														</c:otherwise>
													</c:choose>
													</shiro:hasPermission> <shiro:hasPermission name="redeemapply_listinfo">
														<a
															href='${ctx}/userinfo/redeemRecord/listRedeemRecord?condition={pid:<c:out value="${redeemapply.id}"/>}'
															class="btn btn-blue btn-sm" style="width: 80px"><i
															class="fa fa-file-text"></i>批准记录</a>
													</shiro:hasPermission> <shiro:hasPermission name="redeemapply_listinfo">
														<a
															href='${ctx}/userinfo/redeemApply/buniess/<c:out value="${redeemapply.user.id}"/>'
															class="btn btn-blue btn-sm" style="width: 80px"><i
															class="fa fa-file-text"></i>交易记录</a>
													</shiro:hasPermission></td>

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