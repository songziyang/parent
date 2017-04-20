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

<style type="text/css">
input {
	background: #ffffff !important;
	border: #ffffff 0px !important;
}
</style>

<script type="text/javascript">
	function success(id) {
			var advanceMoney = $("#advanceMoney").val();
			var url = "${ctx}/userwithdraw/withdraw/submitWithDraw";
			document.forms[0].action = url;
			document.forms[0].submit();
	}

	function fail() {
		var value = $("#description").val();
		if (value == "" || value == null) {
			$('#exampleModal2').modal('show')
		} else {
			document.forms[0].submit();
		}
	}

	function deductionFree() {
		var free = $("#deduction").val();
		var fund = $("#withdrawMoney").val();
		$("#advanceMoney").val('');
		if (free == 1) {
			var v = parseFloat(fund - 2).toFixed(2) > 0 ? parseFloat(fund - 2).toFixed(2) : 0.00;
			$("#advanceMoney").val(v);
		} else if (free == 2) {
			var v = parseFloat(fund - 2 - (fund * 3.5) / 1000).toFixed(2) > 0 ? parseFloat(fund - 2 - (fund * 3.5) / 1000).toFixed(2) : 0.00;
			$("#advanceMoney").val(v);
		}else if(free == 3){
			$("#advanceMoney").val(parseFloat(fund).toFixed(2));
		}
	}
</script>
</head>

<body>

	<div class="container-fluid" id="page">

		<div class="row-fluid">
			<div class="col-xs-6 text-left">
				<a href="${ctx}/main" id="back"><span class="middle-span"></span><span
					class="glyphicon glyphicon-menu-left" aria-hidden="true">首页</span></a>
			</div>
			<div class="col-xs-6 text-right">
				<a href="#" id="index"><span class="middle-span"></span><span></span></a>
			</div>
			<div class="col-xs-1"></div>
			<div class="col-xs-10">

				<div style="width: 100%; height: 700px; background: #f3f3f3">
					<div
						style="width: 100%; height: 100%; border-radius: 20px; background: #FFFFFF; text-align: center">
						<form action="${ctx}/userwithdraw/withdraw/failWithDraw"
							method="post" role="form">

							<input type="hidden" name="id"
								value='<c:out value="${userWithDraw.id}"/>'>


							<div style="height: 12%; width: 100%;">
								<div class="name">
									<span class="middle-span"></span> <span>放款审核</span>
								</div>
							</div>
							<div style="height: 3%; width: 100%;">
								<div class="left">
									<span class="middle-span"></span> <img
										src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
										height="15px" style="margin-top: -1px"> <span>申请时间</span>
								</div>
								<div class="right">
									<span class="middle-span"></span> <span> <input
										type="text" readonly="readonly"
										value="<fmt:formatDate value="${userWithDraw.sqDate}"
														pattern="yyyy-MM-dd HH:mm" />">
									</span>
								</div>
							</div>
							<div style="height: 3%; width: 100%;">
								<div class="left">
									<span class="middle-span"></span> <img
										src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
										height="15px" style="margin-top: -1px"> <span>真实姓名</span>
								</div>
								<div class="right">
									<span class="middle-span"></span> <span><input
										type="text" readonly="readonly"
										value="<c:out value="${userWithDraw.realname}"/>"></span>
								</div>
							</div>
							<div style="height: 3%; width: 100%;">
								<div class="left">
									<span class="middle-span"></span> <img
										src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
										height="15px" style="margin-top: -1px"> <span>提现金额</span>
								</div>
								<div class="right">
									<span class="middle-span"></span> <span><input
										type="text"
										value='<fmt:formatNumber value="${userWithDraw.withdrawMoney}" type="number"/>'
										readonly="readonly"> <input type="hidden"
										id="withdrawMoney"
										value='<c:out value="${userWithDraw.withdrawMoney}"/>'></span>
								</div>
							</div>
							<div style="height: 3%; width: 100%;">
								<div class="left">
									<span class="middle-span"></span> <img
										src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
										height="15px" style="margin-top: -1px"> <span>银行</span>
								</div>
								<div class="right">
									<span class="middle-span"></span> <span><input
										type="text" value="${userWithDraw.bankName}"
										readonly="readonly"></span>
								</div>
							</div>
							<div style="height: 3%; width: 100%;">
								<div class="left">
									<span class="middle-span"></span> <img
										src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
										height="15px" style="margin-top: -1px"> <span>银行卡号</span>
								</div>
								<div class="right">
									<span class="middle-span"></span> <span><input
										type="text" readonly="readonly"
										value="${userWithDraw.bankCardNumber}"></span>
								</div>
							</div>
							<div style="height: 3%; width: 100%;">
								<div class="left">
									<span class="middle-span"></span> <img
										src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
										height="15px" style="margin-top: -1px"> <span>开户银行</span>
								</div>
								<div class="right">
									<span class="middle-span"></span> <span><input
										type="text" value="${userWithDraw.openingBank}"
										readonly="readonly"></span>
								</div>
							</div>
							<div style="height: 3%; width: 100%;">
								<div class="left">
									<span class="middle-span"></span> <img
										src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
										height="15px" style="margin-top: -1px"> <span>开户行省份</span>
								</div>
								<div class="right">
									<span class="middle-span"></span> <span><input
										type="text" readonly="readonly"
										value="${userInfo.bankProvince}"></span>
								</div>
							</div>
							<div style="height: 3%; width: 100%;">
								<div class="left">
									<span class="middle-span"></span> <img
										src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
										height="15px" style="margin-top: -1px"> <span>开户行城市</span>
								</div>
								<div class="right">
									<span class="middle-span"></span> <span><input
										type="text" value="${userInfo.bankCity}" readonly="readonly"></span>
								</div>
							</div>
							<div style="height: 3%; width: 100%;">
								<div class="left">
									<span class="middle-span"></span> <img
										src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
										height="15px" style="margin-top: -1px"> <span>扣费类型</span>
								</div>
								<div class="right">
									<span class="middle-span"></span> <select id="deduction"
										name="deduction" onchange="deductionFree()">
										<option value="1">正常提现</option>
										<option value="2">恶意提现</option>
										<option value="3" <c:if test="${userWithDraw.withdrawMoney eq userWithDraw.advanceMoney}">selected="selected"</c:if> >免费提现</option>
									</select>
								</div>
							</div>
							<div style="height: 3%; width: 100%;">
								<div class="left">
									<span class="middle-span"></span> <img
										src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
										height="15px" style="margin-top: -1px"> <span>放款金额</span>
								</div>
								<div class="right">
									<span class="middle-span"></span> <span><input
										type="text" id="advanceMoney" name="advanceMoney"
										value="${userWithDraw.advanceMoney}" readonly="readonly"></span>
								</div>
							</div>
							<div style="height: 3%; width: 100%;">
								<div class="left">
									<span class="middle-span"></span> <img
										src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
										height="15px" style="margin-top: -1px"> <span>累计充值</span>
								</div>
								<div class="right">
									<span class="middle-span"></span> <span><input
										type="text" readonly="readonly"
										value="${empty totalRecharge ? 0 : totalRecharge}"></span>
								</div>
							</div>
							<div style="height: 3%; width: 100%;">
								<div class="left">
									<span class="middle-span"></span> <img
										src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
										height="15px" style="margin-top: -1px"> <span>累计提现</span>
								</div>
								<div class="right">
									<span class="middle-span"></span> <span><input
										type="text"
										value="${empty totalWithDraw  ? 0 : totalWithDraw}"
										readonly="readonly"></span>
								</div>
							</div>
							<div style="height: 3%; width: 100%;">
								<div class="left">
									<span class="middle-span"></span> <img
										src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
										height="15px" style="margin-top: -1px"> <span>用户总额</span>
								</div>
								<div class="right">
									<span class="middle-span"></span> <span> <input
										type="text" readonly="readonly" value="${userMoney.totalFund}"></span>
								</div>
							</div>
							<div style="height: 3%; width: 100%;">
								<div class="left">
									<span class="middle-span"></span> <img
										src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
										height="15px" style="margin-top: -1px"> <span>用户余额</span>
								</div>
								<div class="right">
									<span class="middle-span"></span> <span><input
										type="text" value="${userMoney.usableFund}"
										readonly="readonly"></span>
								</div>
							</div>
							<div style="height: 3%; width: 100%;">
								<div class="left">
									<span class="middle-span"></span> <img
										src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
										height="15px" style="margin-top: -1px"> <span>2.0现投金额</span>
								</div>
								<div class="right">
									<span class="middle-span"></span> <span><input
										type="text" readonly="readonly"
										value='<fmt:formatNumber value="${userMoney.dayloanCostInput}" type="number" />'></span>
								</div>
							</div>
							<div style="height: 3%; width: 100%;">
								<div class="left">
									<span class="middle-span"></span> <img
										src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
										height="15px" style="margin-top: -1px"> <span>2.0M现投金额</span>
								</div>
								<div class="right">
									<span class="middle-span"></span> <span> <input
										type="text"
										value='<fmt:formatNumber value="${userMoney.tdCostInput}" type="number" />'
										readonly="readonly"></span>
								</div>
							</div>

							<div style="height: 3%; width: 100%;">
								<div class="left">
									<span class="middle-span"></span> <img
										src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
										height="15px" style="margin-top: -1px"> <span>股权众筹</span>
								</div>
								<div class="right">
									<span class="middle-span"></span> <span> <input
										type="text"
										value='<fmt:formatNumber value="${empty totalJumu ? 0 : totalJumu}" type="number" />'
										readonly="readonly"></span>
								</div>
							</div>


							<div style="height: 3%; width: 100%;">
								<div class="left">
									<span class="middle-span"></span> <img
										src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
										height="15px" style="margin-top: -1px"> <span>稳进宝</span>
								</div>
								<div class="right">
									<span class="middle-span"></span> <span> <input
										type="text"
										value='<fmt:formatNumber value="${ empty wenjinBao ? 0 : wenjinBao}" type="number" />'
										readonly="readonly"></span>
								</div>
							</div>

							<div style="height: 3%; width: 100%;">
								<div class="left">
									<span class="middle-span"></span> <img
										src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
										height="15px" style="margin-top: -1px"> <span>配资投入</span>
								</div>
								<div class="right">
									<span class="middle-span"></span> <span> <input
										type="text"
										value='<fmt:formatNumber value="${ empty totalTrade ? 0 : totalTrade }" type="number" />'
										readonly="readonly"></span>
								</div>
							</div>


							<div style="height: 3%; width: 100%;">
								<div class="left">
									<span class="middle-span"></span> <img
										src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
										height="15px" style="margin-top: -1px"> <span>配资收益</span>
								</div>
								<div class="right">
									<span class="middle-span"></span> <span> <input
										type="text"
										value='${ empty totalTradeRevenue ? 0 : totalTradeRevenue }'
										readonly="readonly"></span>
								</div>
							</div>


							<div style="height: 3%; width: 100%;">
								<div class="left">
									<span class="middle-span"></span> <img
										src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
										height="15px" style="margin-top: -1px"> <span>问题描述</span>
								</div>
								<div class="right">
									<span class="middle-span"></span> <span></span>
								</div>
							</div>
							<div style="height: 5%; width: 100%;">
								<textarea name="probleDescription" maxlength="200"
									id="description" data-bv-stringlength-max="200"
									data-bv-stringlength-message="不能超过200个字"><c:out
										value="${userWithDraw.probleDescription }" /></textarea>
							</div>
						</form>
						<div style="width: 100%; height: 20%">
							<c:choose>
								<c:when test="${userWithDraw.status == 0}">
									<shiro:hasPermission name="withdraw_success">
										<div style="width: 50%; height: 100%; float: left">
											<span class="middle-span"></span>
											<button id="success" data-toggle="modal"
												data-target="#exampleModal">审核成功</button>
										</div>
										<div style="width: 50%; height: 100%; float: left">
											<span class="middle-span"></span>
											<button id="fail" data-toggle="modal" onclick="fail();">审核失败</button>
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
									<h4 class="modal-title" id="exampleModalLabel">审核提示</h4>
								</div>
								<div class="modal-body">
									<form>
										<div class="form-group">
											<label class="control-label">您确认批准该申请?</label>
										</div>
									</form>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">取消</button>
									<button type="button" onclick="success(${userWithDraw.id})"
										class="btn btn-primary">确定</button>
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
											<label class="control-label">提交失败，请填写问题描述！</label>
										</div>
									</form>
								</div>
								<div class="modal-footer">

									<button type="button" class="btn btn-primary"
										data-dismiss="modal">确定</button>
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