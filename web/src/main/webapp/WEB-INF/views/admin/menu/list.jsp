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
			<h1>菜单列表</h1>
		</div>
		<!--Header Buttons-->
		<div class="header-buttons">
			<a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
			</a> <a class="refresh" id="refresh-toggler" href="${ctx}/admin/menu/listMenu"> <i
				class="glyphicon glyphicon-refresh"></i>
			</a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
				class="glyphicon glyphicon-fullscreen"></i>
			</a>
		</div>
		<!--Header Buttons End-->
	</div>
	<!-- /Page Header -->
	<!-- Page Body -->
	<form action="${ctx}/admin/menu/listMenu" method="post" id="listForm"
		name="listForm">
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
											<label class="control-label no-padding-right">菜单名称</label> <input
												type="text" name="name" class="form-control"
												placeholder="菜单名称">
										</div>
										<div class="form-group">
											<div>
												<label class="control-label no-padding-right">&nbsp;上级菜单</label>
												<select class="form-control" name="pid"
													style="width: 200px;">
													<option value="">全部</option>
													<c:forEach items="${menuLst }" var="menus">
														<option value='<c:out value="${menus.id }" />'><c:out
																value="${menus.name }" />
														</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<a href="javascript:searchData();"
											class="btn btn-labeled btn-blue"> <i
											class="btn-label fa fa-search"></i>查询
										</a>
									</div>
								</div>
								<!--Widget Body-->
							</div>
							<!--Widget-->
						</div>
					</div>
					<div class="widget">
						<div class="widget-header  with-footer">
							<span class="widget-caption">菜单列表</span>
							<div class="widget-buttons">
								<a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
								</a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
								</a>
							</div>
						</div>
						<div class="widget-body">
							<div class="flip-scroll">
								<div style="text-align: right; margin-bottom: 10px;">
									<shiro:hasPermission name="menu_create">
										<a class="btn btn-labeled btn-success"
											href="${ctx}/admin/menu/createMenu"><i
											class="btn-label glyphicon glyphicon-plus"></i>添加</a>
									</shiro:hasPermission>
									<shiro:hasPermission name="menu_del">
										<a class="btn btn-labeled btn-danger"
											href="javascript:removeData('${ctx}/admin/menu/deleteMenus');"><i
											class="btn-label glyphicon glyphicon-trash"></i>删除</a>
									</shiro:hasPermission>
								</div>

								<table class="table table-hover table-striped table-bordered">
									<thead style="font-size: 16px; font-weight: bold;">
										<tr>
											<th width="80" style="text-align: center;">序号</th>
											<th>菜单名称</th>
											<th>上级菜单</th>
											<th>访问地址</th>
											<th>权限标识</th>
											<th>创建时间</th>
											<th width="20"><label> <input type="checkbox">
													<span class="text"></span>
											</label></th>
											<th width="200" style="text-align: center;">操作</th>
										</tr>
									</thead>
									<tbody style="font-size: 12px;">
										<c:forEach items="${page.content}" var="menu"
											varStatus="status">
											<tr>
												<td class="table_no" width="80" align="center"></td>
												<td><c:out value="${menu.name}" /></td>
												<td><c:choose>
														<c:when test="${menu.parentMenu.name != null}">
															<c:out value="${menu.parentMenu.name}" />
														</c:when>
														<c:otherwise>顶级菜单</c:otherwise>
													</c:choose></td>
												<td><c:out value="${menu.url}" /></td>
												<td><c:out value="${menu.purflag}" /></td>
												<td><fmt:formatDate value="${menu.createDate}"
														pattern="yyyy年MM月dd日 HH时mm分" /></td>
												<td width="20"><label> <input type="checkbox"
														name="ids" value='<c:out value="${menu.id}" />'> <span
														class="text"></span>
												</label></td>
												<td width="200" align="center"><shiro:hasPermission
														name="menu_edit">
														<a
															href='${ctx}/admin/menu/editMenu/<c:out value="${menu.id}" />'
															class="btn btn-info btn-xs edit"><i
															class="fa fa-edit"></i> 编辑</a>
                                                    			&nbsp;&nbsp;
                                                    		  </shiro:hasPermission> <shiro:hasPermission
														name="menu_del">
														<a class="btn btn-danger btn-xs delete"
															onclick="removeData('${ctx}/admin/menu/deleteMenu/','<c:out value="${menu.id}" />')"><i
															class="fa fa-trash-o"></i> 删除</a>
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