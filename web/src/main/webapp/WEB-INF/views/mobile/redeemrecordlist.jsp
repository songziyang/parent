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
<title>银多资本</title>
<link href="${ctx}/static/mobile/css/bootstrap.min" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="${ctx}/static/mobile/js/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="${ctx}/static/mobile/js/bootstrap.min.js"></script>
<style>
html, body {
	margin: 0;
	height: 100%
}

* {
	margin: 0;
	padding: 0
}

ul {
	list-style-type: none
}

.container-fluid .row-fluid .col-xs-12 {
	padding-left: 0px;
	padding-right: 0px
}

.panel-default>.panel-heading {
	background: #B0E2FF
}

.panel-body {
	padding: 3px
}

#conditionItem .panel-body .col-xs-12 {
	margin: 3px;
	text-align: center
}

.search {
	width: 50%
}

.middle-span {
	display: inline-block;
	height: 100%;
	vertical-align: middle;
}

.block {
	width: 100%;
	border-radius: 20px;
	background: #FFFFFF;
	text-align: center
}

.going {
	height: 200px;
}

.title {
	width: 100%;
	height: 20%
}

.half {
	width: 50%;
	height: 100%;
	float: left
}

.half .name {
	width: 100px;
	height: 30px;
	position: relative;
	left: 50%;
	top: 50%;
	margin-top: -15px;
	margin-left: -50px;
	border-radius: 20px;
	color: #ffffff
}

.going .half .name {
	background: #00ab9b;
}

.going .time {
	width: 100%;
	height: 10%
}

.going .status {
	width: 100%;
	height: 10%
}

.details {
	width: 100%;
	height: 20%
}

.text {
	font-size: 1.2em
}

.going  .text {
	color: #00ab9b;
}

.left {
	width: 30%;
	height: 100%;
	float: left;
	text-align: left
}

.right {
	width: 65%;
	height: 100%;
	float: left;
	text-align: end
}

.col-xs-6 {
	background: #00ab9b;
}

#index {
	height: 30px;
	width: 100%;
	margin-top: 5px;
	margin-bottom: 5px;
	display: inline-block;
	color: #FFFFFF;
	font-size: 1.5em
}

#back {
	height: 30px;
	width: 100%;
	margin-top: 5px;
	margin-bottom: 5px;
	display: inline-block;
	color: #FFFFFF;
	font-size: 1.5em
}

#listData .panel-body li {
	margin-top: 10px;
	color: #999999
}
</style>

<script>
	$(function() {

	})
</script>
</head>
<body>
	<form action="${ctx}/userinfo/redeemRecord/listRedeemRecord"
		method="post" id="listForm" name="listForm">
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="col-xs-6 text-left">
					<a href="${ctx}/main" id="back"><span class="middle-span"></span><span
						class="glyphicon glyphicon-menu-left" aria-hidden="true">首页</span></a>
				</div>
				<div class="col-xs-6 text-right">
					<a href="#" id="index"><span class="middle-span"></span><span></span></a>
				</div>
				<div class="col-xs-12">
					<div class="panel-group" id="list" role="tablist"
						aria-multiselectable="true">
						<div class="panel panel-default">
							<div class="panel-heading" role="tab" id="headingList">
								<h4 class="panel-title">
									<a data-toggle="collapse" data-parent="#list" href="#listData"
										aria-expanded="true" aria-controls="listData"> 银多2.0赎回批准列表
									</a>
								</h4>
							</div>
							<div id="listData" class="panel-collapse collapse in"
								role="tabpanel" aria-labelledby="headingList">
								<div class="panel-body">
									<div class="row-fluid">
										<div class="col-xs-12">
											<ul>
												<c:forEach items="${page.content}" var="redeemrecord"
													varStatus="status">
													<li>
														<div class="block going">
															<div class="title">
																<div class="half">
																	<div class="name">
																		<span class="middle-span"></span> <span><c:out
																				value="${status.count}" /> </span>
																	</div>

																</div>
																<div class="half">
																	<span class="middle-span"></span> <span></span>

																</div>
															</div>
															<div class="time">
																<div class="left">
																	<span class="middle-span"></span> <span>用户名</span>
																</div>
																<div class="right">
																	<span class="middle-span"></span> <span>
																	<c:choose>
																		<c:when test="${redeemrecord.user.userLeve.gradeNum eq 1}"><img src="${ctx}/static/lib/images/vip1.png" /></c:when>
																		<c:when test="${redeemrecord.user.userLeve.gradeNum eq 2}"><img src="${ctx}/static/lib/images/vip2.png" /></c:when>
																	</c:choose>
																	<c:out value="${redeemrecord.user.username}" /></span>
																</div>
															</div>
															<div class="time">
																<div class="left">
																	<span class="middle-span"></span> <span>手机号</span>
																</div>
																<div class="right">
																	<span class="middle-span"></span> <span><c:out
																			value="${redeemrecord.user.mobile}" /></span>
																</div>
															</div>
															<div class="status">
																<div class="left">
																	<span class="middle-span"></span> <span>申请份数</span>
																</div>
																<div class="right">
																	<span class="middle-span"></span> <span><c:out
																			value="${redeemrecord.applyFund}" /></span>
																</div>
															</div>
															<div class="status">
																<div class="left">
																	<span class="middle-span"></span> <span>批准份数</span>
																</div>
																<div class="right">
																	<span class="middle-span"></span> <span><c:out
																			value="${redeemrecord.fund}" /></span>
																</div>
															</div>
															<div class="status">
																<div class="left">
																	<span class="middle-span"></span> <span>审核时间</span>
																</div>
																<div class="right">
																	<span class="middle-span"></span> <span><fmt:formatDate
																			value="${redeemrecord.createDate}"
																			pattern="yyyy年MM月dd日 HH时mm分" /></span>
																</div>
															</div>
															<div class="status">
																<div class="left">
																	<span class="middle-span"></span> <span>审核人</span>
																</div>
																<div class="right">
																	<span class="middle-span"></span> <span><c:out
																			value="${redeemrecord.auditUser}" /></span>
																</div>
															</div>
														</div>
													</li>
												</c:forEach>
											</ul>
										</div>
									</div>
								</div>
							</div>
							<tags:mobile page="${page}"></tags:mobile>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>