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
<style>
.left {
	width: 40%;
	height: 100%;
	float: left;
	text-align: left;
	position: relative;
	left: 5%
}

.right {
	width: 55%;
	height: 100%;
	float: left;
	text-align: end;
}
</style>
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
				<div style="width: 100%; height: 600px; background: #f3f3f3">
					<div
						style="width: 100%; height: 100%; border-radius: 20px; background: #FFFFFF; text-align: center">
						<div style="height: 12%; width: 100%;">
							<div class="name">
								<span class="middle-span"></span> <span>用户信息</span>
							</div>
						</div>
						<div style="height: 4%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>用户名</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span>
								<c:choose>
									<c:when test="${user.userLeve.gradeNum eq 1}"><img src="${ctx}/static/lib/images/vip1.png" /></c:when>
									<c:when test="${user.userLeve.gradeNum eq 2}"><img src="${ctx}/static/lib/images/vip2.png" /></c:when>
								</c:choose>
								<c:out value="${user.username}" /></span>
							</div>
						</div>
						<div style="height: 4%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>手机号</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span><c:out
										value="${user.mobile}" /></span>
							</div>
						</div>
						<div style="height: 4%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>推荐人手机号</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span><c:out
										value="${user.referralMobile}" /></span>
							</div>
						</div>
						<div style="height: 4%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>注册</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span><fmt:formatDate
										value="${user.createDate}" pattern="yyyy年MM月dd日HH时mm分" /></span>
							</div>
						</div>
						<div style="height: 4%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>真实姓名</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span><c:out
										value="${userInfo.realname}" /></span>
							</div>
						</div>
						<div style="height: 4%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>银行名称</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span><c:out
										value="${userInfo.bankName}" /></span>
							</div>
						</div>
						<div style="height: 4%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>银行卡号</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span><c:out
										value="${userInfo.bankCardNumber}" /></span>
							</div>
						</div>
						<div style="height: 4%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>开户行</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span><c:out
										value="${userInfo.openingBank}" /></span>
							</div>
						</div>
						<div style="height: 4%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>开户行省份</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span><c:out
										value="${userInfo.bankProvince}" /></span>
							</div>
						</div>
						<div style="height: 4%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>开户行城市</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span><c:out
										value="${userInfo.bankCity}" /></span>
							</div>
						</div>
						<div style="height: 4%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>登陆错误次数</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span><c:out
										value="${empty user.loginNum ?0 : user.loginNum}" /></span>
							</div>
						</div>
						<div style="height: 4%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>锁定状态</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span><c:out
										value="${user.isLogin eq -1 ? '锁定' :'未锁'}" /></span>
							</div>
						</div>
						<div style="height: 4%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>锁定时间</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span><fmt:formatDate
										value="${empty user.lockTimeDate ? null :user.lockTimeDate}"
										pattern="yyyy年MM月dd日 HH时mm分" /></span>
							</div>
						</div>
						<div style="height: 4%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>用户等级</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <select disabled="true">
									<option value="0"
										<c:if test="${userInfo.vip == 0}">selected="selected"</c:if>>普通用户</option>
									<option value="1"
										<c:if test="${userInfo.vip == 1 }">selected="selected"</c:if>>银多老用户</option>
									<option value="2"
										<c:if test="${userInfo.vip == 2 }">selected="selected"</c:if>>股东用户</option>
								</select>
							</div>
						</div>
						<div style="height: 4%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>总贡献值</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span><c:out
										value="${userMoney.totalScore}" /></span>
							</div>
						</div>
						<div style="height: 4%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>可用贡献值</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span><c:out
										value="${userMoney.usableScore}" /></span>
							</div>
						</div>
						<div style="height: 4%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>总资金</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span><c:out
										value="${userMoney.totalFund}" /></span>
							</div>
						</div>
						<div style="height: 4%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>可用资金</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span><c:out
										value="${userMoney.usableFund}" /></span>
							</div>
						</div>
						<div style="height: 4%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>冻结资金</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span><c:out
										value="${userMoney.blokedFund}" /></span>
							</div>
						</div>
						<div style="height: 4%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>2.0投入资金</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span><c:out
										value="${userMoney.dayloanCostInput}" /></span>
							</div>
						</div>
						<div style="height: 4%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>2.0M投入资金</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span><c:out
										value="${userMoney.tdCostInput}" /></span>
							</div>
						</div>
						<div style="height: 4%; width: 100%;">
							<div class="left">
								<span class="middle-span"></span> <img
									src="${ctx}/static/mobile/images/circle_icon.png" width="15px"
									height="15px" style="margin-top: -1px"> <span>2.0+投入资金</span>
							</div>
							<div class="right">
								<span class="middle-span"></span> <span><c:out
										value="${userMoney.whitenoteTotalFund}" /></span>
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