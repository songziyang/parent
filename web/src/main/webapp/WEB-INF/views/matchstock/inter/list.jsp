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

function deleteProduct(url){
	bootbox.dialog({
        message: "您确定要删除此记录吗？",
        title: "删除提示",
        className: "modal-darkorange",
        buttons: {
            success: {
                label: "确认",
                className: "btn-default",
                callback: function () {
                		window.location.href = url;
                }
            },
            "取消": {
                className: "btn-default",
                callback: function () { }
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
			<li><i class="fa fa-home"></i> <a href="#">配资管理</a></li>
			<li><a href="#">配资利息率</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>配资利息率信息列表</h1>
		</div>
		<!--Header Buttons-->
		<div class="header-buttons">
			<a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
			</a> <a class="refresh" id="refresh-toggler" href="${ctx}/matchstock/inter/listinter"> <i
				class="glyphicon glyphicon-refresh"></i>
			</a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
				class="glyphicon glyphicon-fullscreen"></i>
			</a>
		</div>
		<!--Header Buttons End-->
	</div>
	<!-- /Page Header -->
	<!-- Page Body -->
	<form action="${ctx}/matchstock/inter/listinter" method="post"
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
											<label class="control-label no-padding-right">配资利息率查询</label> <input
												type="text" name="name" class="form-control"
												placeholder="配资利息率">
										</div>
										<!--  	<div class="form-group">
											<label class="control-label no-padding-right">配资利息率user</label> <input
												type="text" name="stockname" class="form-control"
												placeholder="配资利息率">
										</div>-->
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
							<span class="widget-caption">配资利息率信息列表</span>
							<div class="widget-buttons">
								<a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
								</a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
								</a>
							</div>
						</div>
						<div class="widget-body">
							<div class="flip-scroll">
							
								<div style="text-align: right;margin-bottom: 10px;">
								<shiro:hasPermission name="inter_create">
									<a class="btn btn-labeled btn-success" href="${ctx}/matchstock/inter/createInter" ><i class="btn-label glyphicon glyphicon-plus"></i>添加</a>							
								</shiro:hasPermission>
								</div>
								
								<table class="table table-hover table-striped table-bordered">
									<thead style="font-size: 16px; font-weight: bold;">
										<tr>
											<th width="80" style="text-align: center;">序号</th>
											<th>利息率</th>
											
											<th>创建人</th>
											<th>创建时间</th>
										
											
											<th width="200px" style="text-align: center;">操作</th>
										</tr>
									</thead>
									<tbody style="font-size: 12px;">
										<c:forEach items="${page.content}" var="vinter"
											varStatus="status">
											<tr>
												<td class="table_no" width="80" align="center"></td>
												<td><c:out value="${vinter.interestrate}" /></td>
																					
												<td><c:out value="${vinter.createuser}" /></td>
												<td><fmt:formatDate value="${vinter.createDate}"  pattern="yyyy年MM月dd日 HH时mm分" /></td>
											
												<td width="200px" align="center"><shiro:hasPermission
														name="inter_edit">
														<a
															href='${ctx}/matchstock/inter/editInter/<c:out value="${vinter.id}" />'
															class="btn btn-info btn-xs edit"><i
															class="fa fa-edit"></i>编辑</a>
                                                    			&nbsp;&nbsp;
                                                    </shiro:hasPermission> <shiro:hasPermission
														name="inter_del">
														<a class="btn btn-danger btn-xs delete"
															onclick="deleteProduct('${ctx}/matchstock/inter/deleteInter/<c:out value="${vinter.id}" />')"><i
															class="fa fa-trash-o"></i>删除</a>
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