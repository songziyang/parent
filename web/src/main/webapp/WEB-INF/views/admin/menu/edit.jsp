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
			<li><i class="fa fa-home"></i> <a href="#">系统管理</a></li>
			<li><a href="#">菜单管理</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>
				<c:choose>
					<c:when test="${!empty menu.id}">菜单编辑</c:when>
					<c:otherwise>菜单添加</c:otherwise>
				</c:choose>
			</h1>
		</div>
		<!--Header Buttons-->
		<div class="header-buttons">
			<a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
			</a> <a class="refresh" id="refresh-toggler" href=""> <i
				class="glyphicon glyphicon-refresh"></i>
			</a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
				class="glyphicon glyphicon-fullscreen"></i>
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
							<form action="${ctx}/admin/menu/saveMenu" id="editForm"
								name="editForm" method="post" role="form">

								<input type="hidden" name="id"
									value='<c:out value="${menu.id}"/>'> <input
									type="hidden" name="created"
									value='<c:out value="${menu.created}"/>'>

								<div class="form-title">菜单信息</div>
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>菜单名称<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												name="name" value="<c:out value="${menu.name}"/>"
												style="padding-left: 100px;" required maxlength="20"
												data-bv-stringlength-max="20"
												data-bv-stringlength-message="不能超过20个字">
											</span>
										</div>
									</div>

									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>权限标识：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 100px;" name="purflag"
												value="<c:out value="${menu.purflag}"/>" 
												maxlength="30" data-bv-stringlength-max="30"
												data-bv-stringlength-message="不能超过20个字">
											</span>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>菜单地址：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 100px;" name="url"
												value="<c:out value="${menu.url}"/>"  maxlength="70"
												data-bv-stringlength-max="70"
												data-bv-stringlength-message="不能超过70个字">
											</span>
										</div>
									</div>
								</div>
								
								<div class="row">
									<div class="col-sm-6">
									<label style="color: #999;">上级菜单<sup style="color: red;">&nbsp;*</sup>：</label>
										<div class="form-group">
											 <select name="parentMenu.id" class="form-control">
												<option value="">顶级菜单</option>
												<c:forEach items="${menuLst }" var="menus">
													<option value='<c:out value="${menus.id }" />'
														<c:if test="${menu.parentMenu.id==menus.id}">selected="selected"</c:if>><c:out
															value="${menus.name }" />
													</option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>

								<div style="text-align: right; margin-bottom: 10px;">
									<button type="submit" class="btn btn-labeled btn-success">
										<i class="btn-label glyphicon glyphicon-ok"></i>保存
									</button>
									<a href="${ctx}/admin/menu/listMenu"
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