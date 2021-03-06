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

<script type="text/javascript">
	function deleteProduct(url) {
		bootbox.dialog({
			message : "您确定要撤销产品吗？",
			title : "撤销提示",
			className : "modal-darkorange",
			buttons : {
				success : {
					label : "确认",
					className : "btn-default",
					callback : function() {
						window.location.href = url;
					}
				},
				"取消" : {
					className : "btn-default",
					callback : function() {
					}
				}
			}
		});
	}
</script>
</head>
<body>

	<!-- Page Breadcrumb -->
	<div class="page-breadcrumbs">
		<ul class="breadcrumb" style="line-height: 40px;">
			<li><i class="fa fa-home"></i> <a href="#">产品管理</a></li>
			<li><a href="#">产品每日设置</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>产品每日设置列表</h1>
		</div>
		<!--Header Buttons-->
		<div class="header-buttons">
			<a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
			</a> <a class="refresh" id="refresh-toggler" href="${ctx}/product/productset/listProductSet"> <i
				class="glyphicon glyphicon-refresh"></i>
			</a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
				class="glyphicon glyphicon-fullscreen"></i>
			</a>
		</div>
		<!--Header Buttons End-->
	</div>
	<!-- /Page Header -->
	<!-- Page Body -->
	<form action="${ctx}/product/productset/listProductSet" method="post"
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
											<label class="control-label no-padding-right">产品名称</label> <select
												class="form-control" id="productId" name="productId" style="width: 200px;">
												<option value="">全部</option>
												<c:forEach items="${productLst }" var="product">
													<option value='<c:out value="${product.id }" />'><c:out
															value="${product.name }" />
													</option>
												</c:forEach>
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
							<span class="widget-caption">产品每日设置列表</span>
							<div class="widget-buttons">
								<a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
								</a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
								</a>
							</div>
						</div>
						<div class="widget-body">
							<div class="flip-scroll">

								<div style="text-align: right; margin-bottom: 10px;">
									<shiro:hasPermission name="productset_create">
										<a class="btn btn-labeled btn-success"
											href="${ctx}/product/productset/createProductSet"><i
											class="btn-label glyphicon glyphicon-plus"></i>添加</a>
									</shiro:hasPermission>
								</div>

								<table class="table table-hover table-striped table-bordered">
									<thead style="font-size: 16px; font-weight: bold;">
										<tr>
											<th width="80" style="text-align: center;">序号</th>
											<th>产品名称</th>
											<th>产品类型</th>
											<th>发布份数</th>
											<th>剩余份数</th>
											<th>发布利率(%)</th>
											<th>活动利率(%)</th>
											<th>发布贡献值比例(%)</th>
											<th>发布时间</th>
											<th width="200px" style="text-align: center;">操作</th>
										</tr>
									</thead>
									<tbody style="font-size: 12px;">
										<c:forEach items="${page.content}" var="productset"
											varStatus="status">
											<tr>
												<td class="table_no" width="80" align="center"></td>
												<td><c:out value="${productset.product.name}" /></td>
												<td>
												<c:choose>
													<c:when test="${productset.product.type==1}">  
												         银多2.0
												   	</c:when>
													<c:when test="${productset.product.type==2}">  
												         银多2.0M  
												    </c:when>
													<c:when test="${productset.product.type==3}">  
												        银多2.0+
												    </c:when>
												</c:choose>
												</td>
												<td><c:out value="${productset.circulation}" /></td>
												<td><c:out value="${productset.remainingCopies}" /></td>
												<td><c:out value="${productset.apr}" /></td>
												<td><c:out value="${productset.activityApr}" /></td>
												<td><c:out value="${productset.scoreRatio}" /></td>
												<td><fmt:formatDate value="${productset.createDate}"
														pattern="yyyy年MM月dd日  HH时mm分" /></td>
												<td width="200px" align="center"><shiro:hasPermission
														name="productset_copy">
														<a
															href='${ctx}/product/productset/copyProductSet/<c:out value="${productset.id}" />'
															class="btn btn-info btn-xs edit"><i
															class="fa fa-edit"></i>复制</a>
                                                    			&nbsp;&nbsp;
                                                    </shiro:hasPermission> <shiro:hasPermission
														name="productset_del">
														<a class="btn btn-danger btn-xs delete"
															onclick="deleteProduct('${ctx}/product/productset/deleteProductSet/<c:out value="${productset.id}" />')"><i
															class="fa fa-trash-o"></i>撤销</a>
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