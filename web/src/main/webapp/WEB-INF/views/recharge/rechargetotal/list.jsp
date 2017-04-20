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
	$(document).ready(
		function() {
			var filename = '<c:out value="${fileName}"/>';
			if (filename != "") {
//				window.location.href = "http://keng.yinduoziben.com/static/download/"+ filename;
				window.location.href = "http://localhost:8080/static/download"+ filename;
			}

			//如果有查询条件,则将查询框展开
			if (("${startTime}" != "" && "${startTime}" != undefined) || ("${endTime}" != "" && "${endTime}" != undefined)) {
				$("#selector").click();
			}
	});

	function exprotExcel(condition) {
		var url = "${ctx}/recharge/rechargetotal/exportExcel/" + condition
		window.location.href = url;
	}
</script>

</head>
<body>

	<div class="page-breadcrumbs">
		<ul class="breadcrumb" style="line-height: 40px;">
			<li><i class="fa fa-home"></i> <a href="#">充值管理</a></li>
			<li><a href="#">每日充值列表</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>每日充值列表</h1>
		</div>
		<!--Header Buttons-->
		<div class="header-buttons">
			<a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
			</a> <a class="refresh" id="refresh-toggler"
				href="${ctx}/recharge/rechargetotal/listRechargetotal"> <i
				class="glyphicon glyphicon-refresh"></i>
			</a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
				class="glyphicon glyphicon-fullscreen"></i>
			</a>
		</div>
		<!--Header Buttons End-->
	</div>
	<!-- /Page Header -->
	<!-- Page Body -->
	<form action="${ctx}/recharge/rechargetotal/listRechargetotal"
		method="post" id="listForm" name="listForm">
		<div class="page-body">
			<div class="row">
				<div class="col-lg-3 col-sm-3 col-xs-6">
					<div
						class="databox radius-bordered databox-shadowed databox-vertical">
						<div class="databox-top bg-red no-padding">
							<div class="databox-row row-2"></div>
							<div class="databox-row row-10">
								<div class="databox-sparkline">充值</div>
							</div>
						</div>
						<div class="databox-bottom no-padding bg-white">
							<div class="databox-row">
								<div
									class="databox-cell cell-6 text-align-center bordered-right bordered-platinum">
									<a href="${ctx}/recharge/rechargetotal/detail">
										<span class="databox-number lightcarbon">
											<fmt:formatNumber value="${empty totalPerson ? 0 : totalPerson }" type="number" />
										</span> <span class="databox-text sonic-silver no-margin">累计充值人数</span>
									</a>
								</div>
								<div class="databox-cell cell-6 text-align-center">
									<a href="${ctx}/recharge/rechargetotal/detail">
										<span class="databox-number lightcarbon">
											<fmt:formatNumber value="${empty totalFund ? 0 : totalFund }" type="number" pattern="0.00" maxFractionDigits="2" />
										</span> <span class="databox-text sonic-silver no-margin">累计充值金额</span>
									</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-xs-12 col-md-12">
					<div class="row">
						<div class="col-lg-12 col-sm-12 col-xs-12">
							<div class="widget collapsed">
								<div class="widget-header bordered-bottom bordered-sky">
									<i class="widget-icon fa fa-arrows-v blue"></i> <span
										class="widget-caption">查询条件</span>
									<div class="widget-buttons">
										<a href="#"> </a> <a href="#" id="selector" data-toggle="collapse"> <i
											class="fa fa-plus blue"></i>
										</a>
									</div>
									<!--Widget Buttons-->
								</div>
								<!--Widget Header-->
								<div class="widget-body">
									<div class="form-inline" role="form">
										<div class="form-group">
											<label class="control-label no-padding-right"></label> 统计日期:从
											<input type="text" name="startTime" class="form-control"
												tabindex="1" style="padding-left: 50px;" maxlength="40" value="${startTime}"
												onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd HH:mm'})"> 到 <input
												type="text" name="endTime" class="form-control" tabindex="1"
												style="padding-left: 50px;" maxlength="40" value="${endTime}"
												onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd HH:mm'})">
										</div>

										<div class="form-group">
											<label class="control-label no-padding-right">充值类型</label>
											<select class="form-control" name="rechargetype">
												<option value="">全部</option>
												<option value="0">银多账户充值</option>
												<option value="1">电子账户充值</option>
											</select>
										</div>
										<div class="form-group">
											<label class="control-label no-padding-right">充值渠道</label>
											<select class="form-control" name="onlines">
												<option value="">全部</option>
												<option value="0">线下充值</option>
												<option value="1">线上充值</option>
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
							<span class="widget-caption">每日充值列表</span>
							<div class="widget-buttons">
								<a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
								</a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
								</a>
							</div>
						</div>
						<div class="widget-body">
							<div class="flip-scroll">
								<div style="text-align: right; margin-bottom: 10px;">
									<div style="text-align: center; color: red; font-size: 15px"
										id="result">
										<c:if test="${total ge 0 }">
											当前日期充值总额:<c:out value="${total }" />
										</c:if>
									</div>
									<a
										href="javascript:exprotExcel('<c:out value="${condition}"/>');"
										class="btn btn-labeled btn-success"> <i
										class="btn-label glyphicon glyphicon-envelope"></i>导出Excel
									</a>
								</div>
								<table class="table table-hover table-striped table-bordered">
									<thead style="font-size: 16px; font-weight: bold;">
										<tr>
											<th width="80" style="text-align: center;">序号</th>
											<th>用户名</th>
											<th>手机号</th>
											<th>请求流水</th>
											<th>充值金额</th>
											<th>充值时间</th>
											<th>充值类型</th>
											<th>充值渠道</th>
											<th>交易订单号</th>
											<th>充值成功时间</th>
										</tr>
									</thead>
									<tbody style="font-size: 12px;">
										<c:forEach items="${page.content}" var="userrecharge"
											varStatus="status">
											<tr>
												<td class="table_no" width="80" align="center"></td>
												<td>
													<c:choose>
														<c:when test="${userrecharge.user.userLeve.gradeNum eq 1}"><img src="${ctx}/static/lib/images/vip1.png" /></c:when>
														<c:when test="${userrecharge.user.userLeve.gradeNum eq 2}"><img src="${ctx}/static/lib/images/vip2.png" /></c:when>
													</c:choose>
													<a href="${ctx}/userinfo/user/listUserInfo/<c:out value='${userrecharge.user.id}'/>"
													   data-simpletooltip="init" title='<c:out value="${userrecharge.user.remark}" />'
													   <c:if test="${not empty userrecharge.user.remark}">style='color: red;'</c:if>>
														<c:out value="${userrecharge.user.username}" />
													</a>
												</td>
												<td>
													<a href="${ctx}/userinfo/user/listUserInfo/<c:out value='${userrecharge.user.id}'/>"
													   data-simpletooltip="init" title='<c:out value="${userrecharge.user.remark}" />'
													   <c:if test="${not empty userrecharge.user.remark}">style='color: red;'</c:if>>
														<c:out value="${userrecharge.user.mobile}" />
													</a>
												</td>
												<td><c:out value="${userrecharge.orderNumber}" /></td>
												<td><c:out value="${userrecharge.money}" /></td>
												<td>
													<fmt:formatDate value="${userrecharge.rechargeDate}" pattern="yyyy年MM月dd日 HH时mm分" />
												</td>
												<td>
													<c:if test="${userrecharge.rechargetype eq 1 }">
														电子账户充值
													</c:if>
													<c:if test="${userrecharge.rechargetype ne 1 }">
														银多账户充值
													</c:if>
												</td>
												<td>
													<c:if test="${userrecharge.onlines eq 1 }">
														线上充值
													</c:if>
													<c:if test="${userrecharge.onlines ne 1 }">
														线下充值
													</c:if>
												</td>
												<td>
													<c:out value="${userrecharge.serialNumber}" />
												</td>
												<td>
													<fmt:formatDate value="${userrecharge.rechargeSuccessDate}" pattern="yyyy年MM月dd日 HH时mm分" />
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