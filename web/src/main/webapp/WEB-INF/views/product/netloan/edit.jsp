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
				<li><i class="fa fa-home"></i> <a href="#">网贷管理</a></li>
				<li><a href="#">产品信息</a></li>
			</ul>
		</div>
		<!-- /Page Breadcrumb -->
		<!-- Page Header -->
		<div class="page-header position-relative">
			<div class="header-title">
				<h1>
					<c:choose>
						<c:when test="${!empty netLoan.id}">产品信息编辑</c:when>
						<c:otherwise>产品信息添加</c:otherwise>
					</c:choose>
				</h1>
			</div>
			<!--Header Buttons-->
			<div class="header-buttons">
				<a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i></a>
				<a class="refresh" id="refresh-toggler" href=""> 
					<i class="glyphicon glyphicon-refresh"></i>
				</a> 
				<a class="fullscreen" id="fullscreen-toggler" href="#"> 
					<i class="glyphicon glyphicon-fullscreen"></i>
				</a>
			</div>
		</div>
		<div class="page-body">
			<div class="row">
				<div class="col-xs-12 col-md-12">
					<div class="widget">
						<div class="widget-header bordered-bottom bordered-palegreen">
							<span class="widget-caption">表单</span>
						</div>
						<div class="widget-body">
							<div id="registration-form">
								<form action="${ctx}/product/netLoan/saveNetLoan" id="editForm"
									name="editForm" method="post" role="form">
									<input type="hidden" name="id" value='<c:out value="${netLoan.id}"/>'> 
									<div class="form-title">产品信息</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<span class="form-group input-icon icon-right"> 
													<label>产品名称
														<sup style="color: red;">&nbsp;*</sup>：
													</label> 
													<input type="text" class="form-control" tabindex="1"
															name="proName" value="<c:out value="${netLoan.proName}"/>"
															style="padding-left: 100px;" required maxlength="50"
															data-bv-stringlength-max="50"
															data-bv-stringlength-message="不能超过50个字">
												</span>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<span class="form-group input-icon icon-right"> 
													<label>利率范围(%)
														<sup style="color: red;">&nbsp;*</sup>：
													</label> 
													<input type="text" class="form-control" tabindex="1"
															placeholder="多个利率之间用逗号隔开，例如13.8,14.88,12"
															style="padding-left: 120px;" name="proRate" required
															value="<c:out value="${netLoan.proRate}"/>" maxlength="20"
															data-bv-stringlength-max="20"
															data-bv-stringlength-message="不能超过20个字" data-bv-regexp="true"
															data-bv-regexp-regexp="^((\d+|\d+(\.\d+)?)\,)*\d+(\.\d+)?$"
															data-bv-regexp-message="利率范围格式错误" />
												</span>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<span class="form-group input-icon icon-right"> 
													<label>来源平台
														<sup style="color: red;">&nbsp;*</sup>：
													</label> 
													<input type="text" name="proPlatform" class="form-control" 
															style="padding-left: 100px;" required tabindex="1"
															maxlength="20" data-bv-stringlength-max="20"
															value="<c:out value='${netLoan.proPlatform}'/>"
															data-bv-stringlength-message="不能超过20个字" />
												</span>
											</div>
										</div>
										<div class="col-sm-6" id="scoreRatio">
											<div class="form-group">
												<span class="form-group input-icon icon-right"> 
													<label>投资周期(月)
														<sup style="color: red;">&nbsp;*</sup>：
													</label> 
													<input type="text" name="proCycle" class="form-control" 
															placeholder="多个周期之间用逗号隔开，例如1,3,6,12"
															style="padding-left: 130px;" required tabindex="1"
															maxlength="20" data-bv-stringlength-max="20"
															value="<c:out value="${netLoan.proCycle}"/>"
															data-bv-stringlength-message="不能超过20个字" data-bv-regexp="true"
															data-bv-regexp-regexp="^(([1-9]|[1-9]\d),)*([1-9]|[1-9]\d)$"
															data-bv-regexp-message="投资周期格式错误" />
												</span>
											</div>
										</div>
									</div>
	
									<div style="text-align: right; margin-bottom: 10px;">
										<button type="submit" class="btn btn-labeled btn-success">
											<i class="btn-label glyphicon glyphicon-ok"></i>保存
										</button>
										<a href="${ctx}/product/netLoan/listNetLoan"
											class="btn btn-labeled btn-blue"> <i
											class="btn-label glyphicon glyphicon-arrow-left"></i>返回
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