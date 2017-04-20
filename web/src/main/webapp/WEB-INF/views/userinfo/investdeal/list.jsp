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
</head>
<body>

	<div class="page-breadcrumbs">
		<ul class="breadcrumb" style="line-height: 40px;">
			<li><i class="fa fa-home"></i> <a href="#">前台用户管理</a></li>
			<li><a href="#">体验金发放记录</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>体验金发放记录</h1>
		</div>
		<!--Header Buttons-->
		<div class="header-buttons">
			<a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
			</a> <a class="refresh" id="refresh-toggler"
				href="${ctx}/userinfo/investdeal/listInvestDeal"> <i
				class="glyphicon glyphicon-refresh"></i>
			</a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
				class="glyphicon glyphicon-fullscreen"></i>
			</a>
		</div>
		<!--Header Buttons End-->
	</div>
	<!-- /Page Header -->
	<!-- Page Body -->
	<form action="${ctx}/userinfo/investdeal/listInvestDeal" method="post"
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
											<label class="control-label no-padding-right">状态</label> <select
												class="form-control" name="status">
												<option value="">全部</option>
												<option value="0">未到期</option>
												<option value="1">已到期</option>
											</select>
										</div>
										<div class="form-group" style="margin-left: 10px">
											<label class="control-label no-padding-right">类型</label> <select
												class="form-control" name="type">
												<option value="">全部</option>
												<option value="1">注册</option>
												<option value="2">推荐</option>
												<option value="3">签到</option>
												<option value="4">老用户</option>
												<option value="5">茶馆活动</option>
												<option value="6">推荐收益</option>
												<option value="7">后台手动</option>
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
							<span class="widget-caption">体验金发放记录</span>
							<div class="widget-buttons">
								<a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
								</a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
								</a>
							</div>
						</div>
						<div class="widget-body">
							<div class="flip-scroll">
								<div style="text-align: right; margin-bottom: 10px;"></div>
								<table class="table table-hover table-striped table-bordered">
									<thead style="font-size: 16px; font-weight: bold;">
										<tr>
											<th width="80" style="text-align: center;">序号</th>
											<th>用户名</th>
											<th>手机号</th>
											<th>体验金金额</th>
											<th>发放时间</th>
											<th>状态</th>
											<th>类型</th>
										</tr>
									</thead>
									<tbody style="font-size: 12px;">
										<c:forEach items="${page.content}" var="investdeal"
											varStatus="status">
											<tr>
												<td class="table_no" width="80" align="center"></td>
												<td>
													<c:choose>
														<c:when test="${investdeal.user.userLeve.gradeNum eq 1}"><img src="${ctx}/static/lib/images/vip1.png" /></c:when>
														<c:when test="${investdeal.user.userLeve.gradeNum eq 2}"><img src="${ctx}/static/lib/images/vip2.png" /></c:when>
													</c:choose>
													<a href="${ctx}/userinfo/user/listUserInfo/<c:out value='${investdeal.user.id}'/>"
													   data-simpletooltip="init" title='<c:out value="${investdeal.user.remark}" />'
													   <c:if test="${not empty investdeal.user.remark}">style='color: red;'</c:if>>
														<c:out value="${investdeal.user.username}" />
													</a>
												</td>
												<td>
													<a href="${ctx}/userinfo/user/listUserInfo/<c:out value='${investdeal.user.id}'/>"
													   data-simpletooltip="init" title='<c:out value="${investdeal.user.remark}" />'
													   <c:if test="${not empty investdeal.user.remark}">style='color: red;'</c:if>>
														<c:out value="${investdeal.user.mobile}" />
													</a>
												</td>
												<td><c:out value="${investdeal.fund}" /></td>
												<td><fmt:formatDate value="${investdeal.createDate}"
														pattern="yyyy年MM月dd日 HH时mm分" /></td>
												<td><c:if test="${investdeal.status eq 0 }">
														未到期
													</c:if> <c:if test="${investdeal.status eq 1 }">
														已到期
													</c:if></td>
												<td><c:if test="${investdeal.type eq 1 }">
														注册
													</c:if> <c:if test="${investdeal.type eq 2 }">
														推荐
													</c:if> <c:if test="${investdeal.type eq 3 }">
														签到
													</c:if> <c:if test="${investdeal.type eq 4 }">
														老用户
													</c:if> <c:if test="${investdeal.type eq 5 }">
														茶馆活动
													</c:if> <c:if test="${investdeal.type eq 6 }">
														推荐收益
													</c:if> <c:if test="${investdeal.type eq 7 }">
														后台手动
													</c:if></td>
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