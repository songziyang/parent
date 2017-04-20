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
<link href="${ctx}/static/lib/simpletooltip/src/css/simpletooltip.min.css" rel="stylesheet"/>
<script src="${ctx}/static/lib/simpletooltip/src/js/simpletooltip.js" ></script>
<style type="text/css">
.form-inline .form-group {
	margin-bottom: 10;
}
</style>
<script type="text/javascript">

	function checkSuccess(url) {
		bootbox.dialog({
			message : "您确定审核成功么？",
			title : "审核成功提示",
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

	function checkFail(url) {
		bootbox.dialog({
			message : "您确定审核失败么？",
			title : "审核失败提示",
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
	<!-- Page Breadcrumb -->
	<div class="page-breadcrumbs">
		<ul class="breadcrumb" style="line-height: 40px;">
			<li><i class="fa fa-home"></i> <a href="#">产品管理</a></li>
			<li><a href="#">私人订制审核</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>私人订制审核</h1>
		</div>
		<!--Header Buttons-->
		<div class="header-buttons">
			<a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i></a>
			<a class="refresh" id="refresh-toggler"
				href="${ctx}/product/selfRevenue/list"><i
				class="glyphicon glyphicon-refresh"></i></a> <a class="fullscreen"
				id="fullscreen-toggler" href="#"> <i
				class="glyphicon glyphicon-fullscreen"></i></a>
		</div>
		<!--Header Buttons End-->
	</div>
	<!-- /Page Header -->
	<!-- Page Body -->
	<form action="${ctx}/product/selfRevenue/list" method="post"
		id="listForm" name="listForm">
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
										<a href="#"></a> <a href="#" data-toggle="collapse"> <i
											class="fa fa-plus blue"></i>
										</a>
									</div>
								</div>
								<div class="widget-body">
									<div class="form-inline" role="form">
										<div class="form-group">
											<div>
												<label class="control-label no-padding-right">&nbsp;状态</label>
												<select class="form-control" name="status"
													style="width: 200px;">
													<option value="0">审核中</option>
													<option value="1">审核成功</option>
													<option value="2">审核失败</option>
												</select>
											</div>
										</div>
										<div class="form-group">
											<a href="javascript:searchData();" class="btn btn-labeled btn-blue">
												<i class="btn-label fa fa-search"></i>查询
											</a>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="widget">
						<div class="widget-header  with-footer">
							<span class="widget-caption">私人订制审核列表</span>
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
											<th>用户名</th>
											<th>手机号</th>
											<th>产品名称</th>
											<th>投资金额</th>
											<th>资产类型</th>
											<th>还款金额</th>
											<th>是否还本金</th>
											<th>描述</th>
											<th>审核状态</th>
											<th width="200px" style="text-align: center;">操作</th>
										</tr>
									</thead>
									<tbody style="font-size: 12px;">
										<c:forEach items="${page.content}" var="revenue"
											varStatus="status">
											<tr>
												<td class="table_no" width="80" align="center"></td>
												<c:set var="tradeLog" value="${revenue.selfTradeLog}" />
												<c:set var="selfUser" value="${revenue.selfTradeLog.user}" />
												<td>
													<c:choose>
														<c:when test="${selfUser.user.userLeve.gradeNum eq 1}"><img src="${ctx}/static/lib/images/vip1.png" /></c:when>
														<c:when test="${selfUser.user.userLeve.gradeNum eq 2}"><img src="${ctx}/static/lib/images/vip2.png" /></c:when>
													</c:choose>
													<a href="${ctx}/userinfo/user/listUserInfo/<c:out value='${selfUser.id}'/>"
													   data-simpletooltip="init" title='<c:out value="${selfUser.remark}" />'
													   <c:if test="${not empty selfUser.remark}">style='color: red;'</c:if>>
														<c:out value="${selfUser.username}" />
													</a>
												</td>
												<td>
													<a href="${ctx}/userinfo/user/listUserInfo/<c:out value='${selfUser.id}'/>"
													   data-simpletooltip="init" title='<c:out value="${selfUser.remark}" />'
													   <c:if test="${not empty selfUser.remark}">style='color: red;'</c:if>>
														<c:out value="${selfUser.mobile}" />
													</a>
												</td>
												<td><c:out value="${tradeLog.buyName}" /></td>
												<td><c:out value="${tradeLog.tradeMoney}" /></td>
												<td>
													<c:choose>
														<c:when test="${tradeLog.proType eq 1}">债权投资</c:when>
														<c:when test="${tradeLog.proType eq 2}">股票投资</c:when>
														<c:when test="${tradeLog.proType eq 3}">股权投资</c:when>
														<c:when test="${tradeLog.proType eq 4}">基金投资</c:when>
													</c:choose>
												</td>
												<td><c:out value="${revenue.revenue}" /></td>
												<td>
													<c:choose>
														<c:when test="${revenue.finalDeal eq 0}">
															是
														</c:when>
														<c:when test="${revenue.finalDeal eq 1}">
															否
														</c:when>
													</c:choose>
												</td>
												<td><c:out value="${revenue.remark}" /></td>
												<td>
													<c:choose>
														<c:when test="${revenue.status eq 0}">审核中</c:when>
														<c:when test="${revenue.status eq 1}">审核成功</c:when>
														<c:when test="${revenue.status eq 2}">审核失败</c:when>
													</c:choose>
												</td>
												<!--
													<td width="20">
														<label> 
														<input type="checkbox" name="ids" value='<c:out value="${tradeLog.id}" />'> 
														<span class="text"></span>
														</label>
													</td>
													 -->
												<td width="200px" align="center">
													<c:if test="${ revenue.status eq 0 }">
														<shiro:hasPermission name="selfrevenue_check">
															<a href="javascript:;" class="btn btn-success btn-xs edit"
															   onclick="checkSuccess('${ctx}/product/selfRevenue/check/<c:out value="${revenue.id}/success" />');">
																<i class="fa fa-edit"></i>审核成功
															</a>&nbsp;&nbsp;
															<a href='javascript:;' class="btn btn-danger btn-xs delete"
															   onclick="checkSuccess('${ctx}/product/selfRevenue/check/<c:out value="${revenue.id}/fail" />');">
																<i class="fa fa-trash-o"></i>审核失败
															</a>
														</shiro:hasPermission>
													</c:if>
												</td>
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
