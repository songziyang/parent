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
</head>
<body>

<!-- Page Breadcrumb -->
<div class="page-breadcrumbs">
	<ul class="breadcrumb" style="line-height: 40px;">
		<li><i class="fa fa-home"></i> <a href="#">产品管理</a></li>
		<li><a href="#">定存宝份数管理</a></li>
	</ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
	<div class="header-title">
		<h1>
			<c:choose>
				<c:when test="${!empty ragularRate.id}">定存宝份数编辑</c:when>
				<c:otherwise>定存宝份数添加</c:otherwise>
			</c:choose>
		</h1>
	</div>
	<!--Header Buttons-->
	<div class="header-buttons">
		<a class="sidebar-toggler" href="#">
			<i class="fa fa-arrows-h"></i>
		</a>
		<a class="refresh" id="refresh-toggler" href="">
			<i class="glyphicon glyphicon-refresh"></i>
		</a>
		<a class="fullscreen" id="fullscreen-toggler" href="#">
			<i class="glyphicon glyphicon-fullscreen"></i>
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
						<form action="${ctx}/product/ragularRate/save" id="editForm"
							  name="editForm" method="post" role="form">
							<input type="hidden" name="id" value='<c:out value="${ragularRate.id}"/>'>
							<div class="form-title">定存宝份数</div>
							<div class="row">
								<div class="col-sm-6">
									<label style="color: #999;">天数</label>
									<div class="form-group">
										<select class="form-control" name="days">
											<option value="30" <c:if test="${ragularRate.days eq 30}" >selected="selected"</c:if>>30</option>
											<option value="90" <c:if test="${ragularRate.days eq 90}" >selected="selected"</c:if>>90</option>
											<option value="180" <c:if test="${ragularRate.days eq 180}" >selected="selected"</c:if>>180</option>
											<option value="365" <c:if test="${ragularRate.days eq 365}" >selected="selected"</c:if>>365</option>
										</select>
									</div>
								</div>
								<div class="col-sm-6">
									<label style="color: #999;">是否作为发布份数 </label>
									<div class="form-group">
										<select class="form-control" id="releaseType" name="releaseType">
											<option value="1" <c:if test="${ragularRate.releaseType eq 1}" >selected="selected"</c:if>>是</option>
										</select>
									</div>
								</div>
							</div>

							<div class="row">
								<div class="col-sm-6" id="copies">
									<div class="form-group">
										<span class="form-group input-icon icon-right">
											<label>发布份数
												<sup style="color: red;">&nbsp;*</sup>：
											</label>
											<input type="text" class="form-control" tabindex="1"
												   name="copies" value="<c:out value="${ragularRate.copies}"/>"
												   style="padding-left: 100px;" maxlength="9"
												   data-bv-stringlength-max="9" required
												   data-bv-stringlength-message="不能超过9个字" />
										</span>
									</div>
								</div>
								<div class="col-sm-6" id="currentRate">
									<div class="form-group">
									<span class="form-group input-icon icon-right">
										<label>发布利率(%)
											<sup style="color: red;">&nbsp;*</sup>：
										</label>
										<input type="text" class="form-control" tabindex="1" id="ipt_currentRate"
											   style="padding-left: 120px;" name="ragularRate" required
											   value="<c:out value="${ragularRate.ragularRate}"/>" maxlength="5"
											   data-bv-stringlength-max="5"
											   data-bv-stringlength-message="不能超过5个字" data-bv-regexp="true"
											   data-bv-regexp-regexp="^\d+(\.\d+)?$"
											   data-bv-regexp-message="发布利率格式错误" min="0"
											   data-bv-greaterthan-inclusive="true"
											   data-bv-greaterthan-message="发布利率应大于0" max="100"
											   data-bv-lessthan-inclusive="true"
											   data-bv-lessthan-message="发布利率应小于100">
									</span>
									</div>
								</div>
							</div>

							<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
									<span class="form-group input-icon icon-right">
										<label>活动利率(%)
											<sup style="color: red;">&nbsp;*</sup>：
										</label>
										<input type="text" class="form-control" tabindex="1"
											   style="padding-left: 120px;" name="activityRate" required
											   value="<c:out value="${ragularRate.activityRate}"/>" maxlength="5"
											   data-bv-stringlength-max="5"
											   data-bv-stringlength-message="不能超过5个字" data-bv-regexp="true"
											   data-bv-regexp-regexp="^\d+(\.\d+)?$"
											   data-bv-regexp-message="活动利率格式错误" min="0"
											   data-bv-greaterthan-inclusive="true"
											   data-bv-greaterthan-message="发布利率应大于0" max="100"
											   data-bv-lessthan-inclusive="true"
											   data-bv-lessthan-message="活动利率应小于100">
									</span>
									</div>
								</div>
							</div>

							<div style="text-align: right; margin-bottom: 10px;">
								<button type="submit" class="btn btn-labeled btn-success">
									<i class="btn-label glyphicon glyphicon-ok"></i>保存
								</button>
								<a href="${ctx}/product/ragularRate/list" class="btn btn-labeled btn-blue">
									<i class="btn-label glyphicon glyphicon-arrow-left"></i>返回
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