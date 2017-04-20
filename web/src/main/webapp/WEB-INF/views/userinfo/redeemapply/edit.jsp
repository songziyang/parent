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
<style>
.form-group label {
	position: absolute;
	left: 10px;
	top: 7px;
	color: #999;
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


	<!-- Page Breadcrumb -->
	<div class="page-breadcrumbs">
		<ul class="breadcrumb" style="line-height: 40px;">
			<li><i class="fa fa-home"></i> <a href="#">前台用户</a></li>
			<li><a href="#">2.0赎回申请</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>2.0赎回申请</h1>
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
		<div class="row">
			<div class="col-xs-12 col-md-12">
				<div class="widget">
					<div class="widget-header bordered-bottom bordered-palegreen">
						<span class="widget-caption">表单</span>
					</div>
					<div class="widget-body">
						<div id="registration-form">
							<form action="${ctx}/userwithdraw/withdraw/failWithDraw"
								method="post" role="form">

								<input type="hidden" name="id"
									value='<c:out value="${dayloanRedeemApply.id}"/>'>

								<div class="form-title">2.0赎回申请</div>
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>申请时间：
											</label> <input type="text" class="form-control" tabindex="1"
												readonly="readonly"
												value="<fmt:formatDate value="${dayloanRedeemApply.createDate}"
														pattern="yyyy-MM-dd HH:ss" />"
												style="padding-left: 100px;">
											</span>
										</div>
									</div>

									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>用户名：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 100px;" readonly="readonly"
												value="<c:out value="${dayloanRedeemApply.user.username}"/>">
											</span>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>申请份数：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 100px;"
												value='<fmt:formatNumber value="${dayloanRedeemApply.applyFund}" type="number"/>'
												readonly="readonly"> <input type="hidden"
												id="withdrawMoney"
												value='<c:out value="${dayloanRedeemApply.applyFund}"/>'>
											</span>
										</div>
									</div>

									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>手机号：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 100px;"
												value="${dayloanRedeemApply.user.mobile}"
												readonly="readonly">
											</span>
										</div>
									</div>

								</div>


								<div class="row">

									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>已批准份数：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 100px;" readonly="readonly"
												value="${dayloanRedeemApply.fund}">
											</span>
										</div>
									</div>

									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>审核状态：
											</label> <c:if test="${dayloanRedeemApply.status eq 0}">
													<input type="text" class="form-control" tabindex="1"
														style="padding-left: 100px;" value="审核中"
														readonly="readonly">
												</c:if> <c:if test="${dayloanRedeemApply.status eq 1}">
													<input type="text" class="form-control" tabindex="1"
														style="padding-left: 100px;" value="赎回中"
														readonly="readonly">
												</c:if> <c:if test="${dayloanRedeemApply.status eq 2}">
													<input type="text" class="form-control" tabindex="1"
														style="padding-left: 100px;" value="赎回成功"
														readonly="readonly">
												</c:if> <c:if test="${dayloanRedeemApply.status eq 3}">
													<input type="text" class="form-control" tabindex="1"
														style="padding-left: 100px;" value="赎回失败"
														readonly="readonly">
												</c:if>
											</span>
										</div>
									</div>

								</div>


								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>累计充值：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" readonly="readonly"
												value="${empty totalRecharge ? 0 : totalRecharge}">
											</span>
										</div>
									</div>

									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>累计提现：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;"
												value="${empty totalWithDraw  ? 0 : totalWithDraw}"
												readonly="readonly">
											</span>
										</div>
									</div>

								</div>

								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>用户总额：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" readonly="readonly"
												value="${userMoney.totalFund}">
											</span>
										</div>
									</div>

									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>用户余额：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" value="${userMoney.usableFund}"
												readonly="readonly">
											</span>
										</div>
									</div>

								</div>

								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>2.0现投金额：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" readonly="readonly"
												value='<fmt:formatNumber value="${userMoney.dayloanCostInput}" type="number" />'>
											</span>
										</div>
									</div>

									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>2.0M现投金额：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;"
												value='<fmt:formatNumber value="${userMoney.tdCostInput}" type="number" />'
												readonly="readonly">
											</span>
										</div>
									</div>

								</div>


								<div style="text-align: right; margin-bottom: 10px;">
									<c:choose>
										<c:when
											test="${dayloanRedeemApply.status eq 0 ||dayloanRedeemApply.status eq 1 }">
											<shiro:hasPermission name="redeemapply_audit">
												<a data-toggle="modal" data-target="#myModal"
													href='${ctx}/userinfo/redeemApply/redeemapplyAudit/<c:out value="${dayloanRedeemApply.id}"/>'
													class="btn btn-labeled btn-success"> <i
													class="btn-label glyphicon glyphicon-ok"></i>审核成功
												</a>
												<button type="button" class="btn btn-labeled btn-danger"
													onclick="successProduct('${ctx}/userinfo/redeemApply/redeemapplyCancel/<c:out value="${dayloanRedeemApply.id}"/>')">
													<i class="btn-label glyphicon glyphicon-remove"></i>审核失败
												</button>
											</shiro:hasPermission>
										</c:when>

									</c:choose>
									<a href="${ctx}/userinfo/redeemApply/listRedeemApply"
										class="btn btn-labeled btn-blue" style="width: 100px"> <i
										class="btn-label fa fa-arrow-left"></i>返回
									</a>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/static/inc/footer.inc"%>
</body>
</html>