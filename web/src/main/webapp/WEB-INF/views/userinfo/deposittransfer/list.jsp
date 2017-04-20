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

	<div class="page-breadcrumbs">
		<ul class="breadcrumb" style="line-height: 40px;">
			<li><i class="fa fa-home"></i> <a href="#">银多2.0M</a></li>
			<li><a href="#">银多2.0M转让列表</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>银多2.0M转让列表</h1>
		</div>
		<!--Header Buttons-->
		<div class="header-buttons">
			<a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
			</a> <a class="refresh" id="refresh-toggler"
				href="${ctx}/userinfo/deposittransfer/listDepositTransfer"> <i
				class="glyphicon glyphicon-refresh"></i>
			</a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
				class="glyphicon glyphicon-fullscreen"></i>
			</a>
		</div>
		<!--Header Buttons End-->
	</div>
	<!-- /Page Header -->
	<!-- Page Body -->
	<form action="${ctx}/userinfo/deposittransfer/listDepositTransfer"
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

										<div class="form-group" style="margin-left: 10px">
											<label class="control-label no-padding-right">状态</label> <select
												class="form-control" name="status">
												<option value="3">转让成功</option>
												<option value="2">转让中</option>
											</select>
										</div>
										<!-- 
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
 										-->
										<div class="form-group">
											<label class="control-label no-padding-right"></label>&nbsp&nbsp
											转让日期:从 <input type="text" name="startDate"
												class="form-control" tabindex="1"
												style="padding-left: 50px;" required maxlength="40"
												onfocus="WdatePicker({skin:'twoer'})"> 到 <input
												type="text" name="endDate" class="form-control" tabindex="1"
												style="padding-left: 50px;" required maxlength="40"
												onfocus="WdatePicker({skin:'twoer'})">
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
							<span class="widget-caption">银多2.0M转让列表</span>
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
										总份数：
										<fmt:formatNumber
											value="${empty totalCopies  ? 0 : totalCopies}" type="number"
											pattern="#" />
									</div>
								</div>

								<table class="table table-hover table-striped table-bordered">
									<thead style="font-size: 16px; font-weight: bold;">
										<tr>
											<th width="80" style="text-align: center;">序号</th>
											<th>用户名</th>
											<th>手机号</th>
											<th>产品名称</th>
											<th>产品天数</th>
											<th>转让份数</th>
											<th>转让次数</th>
											<th>年化收益</th>
											<th>购买日期</th>
											<th>转让时间</th>
											<th>到期日期</th>
										</tr>
									</thead>
									<tbody style="font-size: 12px;">
										<c:forEach items="${page.content}" var="deposittransfer"
											varStatus="status">
											<tr>
												<td class="table_no" width="80" align="center"></td>
												<td>
													<c:choose>
														<c:when test="${deposittransfer.user.userLeve.gradeNum eq 1}"><img src="${ctx}/static/lib/images/vip1.png" /></c:when>
														<c:when test="${deposittransfer.user.userLeve.gradeNum eq 2}"><img src="${ctx}/static/lib/images/vip2.png" /></c:when>
													</c:choose>
													<a
													href='${ctx}/userinfo/user/listUserInfo/<c:out value="${deposittransfer.user.id}" />'>
														<c:out value="${deposittransfer.user.username}" />
												</a></td>
												<td><c:out value="${deposittransfer.user.mobile}" /></td>
												<td><c:out value="${deposittransfer.product.name}" /></td>
												<td><c:out value="${deposittransfer.product.days}" /></td>
												<td><c:out value="${deposittransfer.buyCopies}" /></td>
												<td><c:out value="${deposittransfer.number + 1}" /></td>
												<td><c:out value="${deposittransfer.transferApr}" /></td>
												<td><fmt:formatDate value="${deposittransfer.buyDime}"
														pattern="yyyy年MM月dd日" /></td>
												<td><fmt:formatDate
														value="${deposittransfer.applyTime}"
														pattern="yyyy年MM月dd日 HH时mm分" /></td>
												<td><fmt:formatDate
														value="${deposittransfer.expireDime}"
														pattern="yyyy年MM月dd日" /></td>

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