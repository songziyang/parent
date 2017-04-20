<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
			<li><i class="fa fa-home"></i> <a href="#">机构管理</a></li>
			<li><a href="#">银多-金贸街特供管理</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>机构管理</h1>
		</div>
		<!--Header Buttons-->
		<div class="header-buttons">
			<a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
			</a> <a class="refresh" id="refresh-toggler" href="${ctx}/product/institution/listJmjtg"> <i
				class="glyphicon glyphicon-refresh"></i>
			</a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
				class="glyphicon glyphicon-fullscreen"></i>
			</a>
		</div>
		<!--Header Buttons End-->
	</div>
	<!-- /Page Header -->
	<!-- Page Body -->
	<form action="${ctx}/product/institution/listJmjtg" method="post"
		id="listForm" name="listForm">
		<div class="page-body">
			<div class="row">
				<div class="col-lg-2 col-sm-2 col-xs-2">
					<div
						class="databox radius-bordered databox-shadowed databox-graded databox-vertical">
						<div class="databox-top bg-green">
							<div class="databox-icon">
								<i class="fa fa-clock-o"></i>
							</div>
						</div>
						<div class="databox-bottom text-align-center">
							<span class="databox-text">当日投资金额</span>
							<span class="databox-text">
								<c:choose>
									<c:when test="${todayBuyFund eq null}">0</c:when>
									<c:otherwise>${todayBuyFund}</c:otherwise>
								</c:choose>
							</span>
						</div>
					</div>
				</div>
				<div class="col-lg-2 col-sm-2 col-xs-2">
					<div
						class="databox radius-bordered databox-shadowed databox-graded databox-vertical">
						<div class="databox-top bg-green">
							<div class="databox-icon">
								<i class="fa fa-clock-o"></i>
							</div>
						</div>
						<div class="databox-bottom text-align-center">
							<span class="databox-text">累计投资金额</span> <span
								class="databox-text">
									<c:choose>
										<c:when test="${totalBuyFund eq null}">
											<c:out value="0" />
										</c:when>
										<c:otherwise>
											<c:out value="${totalBuyFund}" />
										</c:otherwise>
									</c:choose>
							</span>
						</div>
					</div>
				</div>
				<div class="col-lg-2 col-sm-2 col-xs-2">
					<div
						class="databox radius-bordered databox-shadowed databox-graded databox-vertical">
						<div class="databox-top bg-green">
							<div class="databox-icon">
								<i class="fa fa-clock-o"></i>
							</div>
						</div>
						<div class="databox-bottom text-align-center">
							<span class="databox-text">累计交易笔数</span> <span
								class="databox-text">
								<c:out value="${empty totalTradeNum? 0: totalTradeNum}"></c:out>
							</span>
						</div>
					</div>
				</div>
				<div class="col-lg-3 col-sm-3 col-xs-6">
					<div
						class="databox radius-bordered databox-shadowed databox-vertical">
						<div class="databox-top bg-red no-padding">
							<div class="databox-row row-2"></div>
							<div class="databox-row row-10">
								<div class="databox-sparkline">出账金额</div>
							</div>
						</div>
						<div class="databox-bottom no-padding bg-white">
							<div class="databox-row">
								<div
									class="databox-cell cell-6 text-align-center bordered-right bordered-platinum">
									<span class="databox-number lightcarbon">
									<c:out value="${empty paidRevenue? 0: paidRevenue}" /></span>
									<span class="databox-text sonic-silver no-margin">已支出</span>
								</div>
								<div class="databox-cell cell-6 text-align-center">
									<span class="databox-number lightcarbon"> <c:out value="${empty prepaidRevenue? 0: prepaidRevenue}" />
									</span> <span class="databox-text sonic-silver no-margin">预支出</span>
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
											<label class="control-label no-padding-right">产品名称</label> <input
												type="text" name="name" class="form-control"
												placeholder="产品名称">
										</div>
										<div class="form-group">
											<label class="control-label no-padding-right"></label>购买日期：从
											<input type="text" name="startTime"
												readonly
												class="form-control" tabindex="1"
												style="padding-left: 50px;" required maxlength="40"
												onfocus="WdatePicker({skin:'twoer'})"> 到 <input
												type="text" name="endTime" class="form-control"
												readonly
												tabindex="1" style="padding-left: 50px;" required
												maxlength="40" onfocus="WdatePicker({skin:'twoer'})">
										</div>
										<div class="form-group">
											<label class="control-label no-padding-right"></label>到期日期：从
											<input type="text" name="closingStartTime"
												readonly
												class="form-control" tabindex="1"
												style="padding-left: 50px;" required maxlength="40"
												onfocus="WdatePicker({skin:'twoer'})"> 到 <input
												type="text" name="closingEndTime" class="form-control"
												readonly
												tabindex="1" style="padding-left: 50px;" required
												maxlength="40" onfocus="WdatePicker({skin:'twoer'})">
										</div>
										<div class="form-group">
											<div>
												<label class="control-label no-padding-right">&nbsp;状态</label>
												<select id="roleId"  class="form-control" name="status" style="width: 200px;">
													<option value="-1">全部</option>
													<option value="0">未到期</option>
													<option value="1">已到期</option>
												</select>
											</div>
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
							<span class="widget-caption">银多-金贸街特供购买记录</span>
							<div class="widget-buttons">
								<a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
								</a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
								</a>
							</div>
						</div>
						<div class="widget-body">
							<div class="flip-scroll">
							
								<div style="text-align: right;margin-bottom: 10px;">
							</div>
								<div
									style="margin-bottom: 10px; text-align: center; color: red; font-size: 15px">
									购买金额：
									<c:out value="${empty timeBuyFund? 0: timeBuyFund}" />
									到期金额：
									<c:out value="${empty timeClosingFund? 0: timeClosingFund}" />
									到期收益：
									<c:out value="${empty timeClosingRevenue? 0: timeClosingRevenue}" />
								</div>
								<table class="table table-hover table-striped table-bordered">
									<thead style="font-size: 16px; font-weight: bold;">
										<tr>
											<th width="80" style="text-align: center;">序号</th>
											<th>产品名称</th>
											<th>产品天数</th>
											<th>年化利率(%)</th>
											<th>购买日期</th>
											<th>购买金额</th>
											<th>到期时间</th>
											<th>状态</th>
											<th>已还金额</th>
											<th>预期收益</th>
											<th>总收益</th>
										</tr>
									</thead>
									<tbody style="font-size: 12px;">
										<c:forEach items="${page.content}" var="ragularAccount"
											varStatus="status">
											<tr>
												<td class="table_no" width="80" align="center"></td>
												<td><c:out value="${ragularAccount.productInfo.name}" /></td>
												<td><c:out value="${ragularAccount.productInfo.cylcleDays}" /></td>
												<td><c:out value="${ragularAccount.apr}" /></td>
												<td><fmt:formatDate value="${ragularAccount.buyDime}"  pattern="yyyy年MM月dd日 " /></td>
												<td><c:out value="${ragularAccount.buyFund}" /></td>
												<td><fmt:formatDate value="${ragularAccount.expireDime}"  pattern="yyyy年MM月dd日 " /></td>
												<td>
													<c:choose>
														<c:when test="${ragularAccount.status eq 0}">未到期</c:when>
														<c:when test="${ragularAccount.status eq 1}">已到期</c:when>
													</c:choose>
												</td>
												<td>
													<c:out value="${ragularAccount.refundable}" />
												</td>
												<td>
													<c:out value="${ragularAccount.predictIncome}" />
												</td>
												<td>
													<c:out value="${ragularAccount.interestFund}" />
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