<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@include file="/static/inc/main.inc"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1,minimum-scale=1, maximum-scale=1,user-scalable=no">
<meta name="format-detection" content="telephone=no" />
<title>配资</title>
<link href="${ctx}/static/mobile/css/bootstrap.min.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/static/mobile/css/detail.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="${ctx}/static/mobile/js/jquery-2.1.3.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/mobile/js/bootstrap.min.js"></script>
<script>
	function successProduct(url) {
		window.location.href = url;
	}
</script>
</head>

<body>

	<div class="container-fluid" id="page">

		<div class="row-fluid">
			<div class="col-xs-6 text-left">
				<a href="${ctx}/main" id="back"><span class="middle-span"></span><span>首页</span></a>
			</div>
			<div class="col-xs-6 text-right">
				<a href="#" id="index"><span class="middle-span"></span><span></span></a>
			</div>
			<div class="col-xs-1"></div>
			<div class="col-xs-10">
				<div style="width: 100%; height: 500px; background: #f3f3f3">
					<div
						style="width: 100%; height: 100%; border-radius: 20px; background: #FFFFFF; text-align: center">
						<div style="height: 20%; width: 100%;">
							<div class="name">
								<span class="middle-span"></span> <span>2.0赎回申请</span>
							</div>
						</div>
						<div style="height: 5%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>申请时间</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span><fmt:formatDate
										value="${dayloanRedeemApply.createDate}"
										pattern="yyyy-MM-dd HH:ss" /></span>
							</div>
						</div>
						<div style="height: 5%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>用户名</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span>
								<c:choose>
									<c:when test="${dayloanRedeemApply.user.userLeve.gradeNum eq 1}"><img src="${ctx}/static/lib/images/vip1.png" /></c:when>
									<c:when test="${dayloanRedeemApply.user.userLeve.gradeNum eq 2}"><img src="${ctx}/static/lib/images/vip2.png" /></c:when>
								</c:choose>
								<c:out value="${dayloanRedeemApply.user.username}" />
							</span>
							</div>
						</div>
						<div style="height: 5%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>申请份数</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span><c:out
										value="${dayloanRedeemApply.applyFund}" /></span>
							</div>
						</div>
						<div style="height: 5%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>手机号</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span><c:out
										value="${dayloanRedeemApply.user.mobile}" /></span>
							</div>
						</div>
						<div style="height: 5%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>已批准份数</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span><c:out
										value="${dayloanRedeemApply.fund}" /></span>
							</div>
						</div>
						<div style="height: 5%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>审核状态</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span> <c:if
										test="${dayloanRedeemApply.status eq 0}">
								审核中
								</c:if> <c:if test="${dayloanRedeemApply.status eq 1}">
								赎回中
								</c:if> <c:if test="${dayloanRedeemApply.status eq 2}">
								赎回成功
								</c:if> <c:if test="${dayloanRedeemApply.status eq 3}">
								赎回失败
								</c:if>
								</span>
							</div>
						</div>
						<div style="height: 5%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>累计充值</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span> <c:out
										value="${empty totalRecharge ? 0 : totalRecharge}" />
								</span>
							</div>
						</div>
						<div style="height: 5%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>累计提现</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span><c:out
										value="${empty totalWithDraw  ? 0 : totalWithDraw}" /></span>
							</div>
						</div>
						<div style="height: 5%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>用户总额</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span><c:out
										value="${userMoney.totalFund}" /></span>
							</div>
						</div>
						<div style="height: 5%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>用户余额</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span><c:out
										value="${userMoney.usableFund}" /></span>
							</div>
						</div>
						<div style="height: 5%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>2.0现投金额</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span><fmt:formatNumber
										value="${userMoney.dayloanCostInput}" type="number" /></span>
							</div>
						</div>
						<div style="height: 5%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>2.0M现投金额</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span><fmt:formatNumber
										value="${userMoney.tdCostInput}" type="number" /></span>
							</div>
						</div>
						<div style="width: 100%; height: 20%">
							<c:choose>
								<c:when
									test="${dayloanRedeemApply.status eq 0 ||dayloanRedeemApply.status eq 1 }">
									<shiro:hasPermission name="redeemapply_audit">
										<div style="width: 50%; height: 100%; float: left">
											<span class="middle-span"></span>
											<button id="success" data-toggle="modal"
												data-target="#exampleModal"
												onclick="successProduct('${ctx}/userinfo/redeemApply/redeemapplyAudit/<c:out value="${dayloanRedeemApply.id}"/>')">审核成功</button>
										</div>
										<div style="width: 50%; height: 100%; float: left">
											<span class="middle-span"></span>
											<button id="fail" data-toggle="modal"
												data-target="#exampleModal2">审核失败</button>
										</div>
									</shiro:hasPermission>
								</c:when>

							</c:choose>
						</div>


					</div>
					<div class="modal fade" id="exampleModal" tabindex="-1"
						role="dialog" aria-labelledby="exampleModalLabel"
						aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h4 class="modal-title" id="exampleModalLabel">2.0赎回审核</h4>
								</div>
								<div class="modal-body"></div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">取消</button>
									<button type="button" class="btn btn-primary">确定</button>
								</div>
							</div>
						</div>
					</div>
					<div class="modal fade" id="exampleModal2" tabindex="-1"
						role="dialog" aria-labelledby="exampleModalLabel"
						aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h4 class="modal-title" id="exampleModalLabel2">撤销提示</h4>
								</div>
								<div class="modal-body">
									<form>

										<div class="form-group">
											<label for="message-text" class="control-label">你确定要撤销嘛?</label>
										</div>
									</form>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">取消</button>
									<button type="button" class="btn btn-primary"
										onclick="successProduct('${ctx}/userinfo/redeemApply/redeemapplyCancel/<c:out value="${dayloanRedeemApply.id}"/>')">确定</button>
								</div>
							</div>
						</div>
					</div>


				</div>
				<div class="col-xs-1"></div>

			</div>

		</div>
	</div>
</body>
</html>