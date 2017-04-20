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

	<!-- Page Breadcrumb -->
	<div class="page-breadcrumbs">
		<ul class="breadcrumb" style="line-height: 40px;">
			<li><i class="fa fa-home"></i> <a href="#">2.0+管理</a></li>
			<li><a href="#">购买产品管理</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>购买产品信息列表</h1>
		</div>
		<!--Header Buttons-->
		<div class="header-buttons">
			<a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
			</a> <a class="refresh" id="refresh-toggler" href="${ctx}/blanknotbuy/productlist/listProductBuy"> <i
				class="glyphicon glyphicon-refresh"></i>
			</a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
				class="glyphicon glyphicon-fullscreen"></i>
			</a>
		</div>
		<!--Header Buttons End-->
	</div>
	<!-- /Page Header -->
	<!-- Page Body -->
	<form action="${ctx}/blanknotbuy/productlist/listProductBuy" method="post"
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
											<label class="control-label no-padding-right"></label>用户昵称<input
												type="text" name="nickName" class="form-control"
												>
										</div>

										<div class="form-group" style="margin-left: 25px;">
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
							<span class="widget-caption">购买产品信息列表</span>
							<div class="widget-buttons">
								<a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
								</a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
								</a>
							</div>
						</div>
						<div class="widget-body">
							<div class="flip-scroll">
								<div style="text-align: right; margin-bottom: 10px;">
									<shiro:hasPermission name="productbuy_purchase">
										<a class="btn btn-labeled btn-success"
											href="${ctx}/blanknotbuy/productlist/createProductInfo"><i
											class="btn-label glyphicon glyphicon-plus"></i>购买</a>
									</shiro:hasPermission>
								</div>

								<table class="table table-hover table-striped table-bordered">
									<thead style="font-size: 16px; font-weight: bold;">
										<tr>
											<th width="80" style="text-align: center;">序号</th>
											<th>用户昵称</th>
											<th>产品名称</th>
											<th>产品价格</th>
											<th>创建人</th>
											<th width="20"><label> <input type="checkbox">
													<span class="text"></span>
											</label></th>
											<th style="text-align: center;">操作</th>
										</tr>
									</thead>
									<tbody style="font-size: 12px;">
										<c:forEach items="${page.content}" var="content"
											varStatus="status">
											<tr>
												<td class="table_no" width="80" align="center"></td>
												
												<td>
													<c:choose>
														<c:when test="${content.user.userLeve.gradeNum eq 1}"><img src="${ctx}/static/lib/images/vip1.png" /></c:when>
														<c:when test="${content.user.userLeve.gradeNum eq 2}"><img src="${ctx}/static/lib/images/vip2.png" /></c:when>
													</c:choose>
													<a href="${ctx}/userinfo/user/listUserInfo/<c:out value='${content.user.id}'/>" data-simpletooltip="init"
													   title='<c:out value="${content.user.remark}" />'
													   <c:if test="${not empty content.user.remark}">style='color: red;'</c:if>>
														<c:out value="${content.user.username}" />
													</a>
												</td>
												<td><c:out value="${content.goodsname}" /></td>
												<td><c:out value="${content.buyMoney}" /></td>
												<td><c:out value="${content.createdUser}" /></td>
												<td width="20"><label> <input type="checkbox"
														name="ids" value='<c:out value="${content.id}" />'>
														<span class="text"></span>
												</label></td>
												<td align="center"><shiro:hasPermission
														name="productbuy_edit">
														<a
															href='${ctx}/blanknotbuy/productlist/editproducts/<c:out value="${content.id}" />'
															class="btn btn-info btn-xs edit"> <i
															class="fa fa-edit"></i>查看
														</a>&nbsp;&nbsp;
                                                    </shiro:hasPermission></td>
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