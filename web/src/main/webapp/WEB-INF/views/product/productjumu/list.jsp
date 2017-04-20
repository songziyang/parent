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
<script type="text/javascript">
	function successProduct(url) {
		bootbox.dialog({
			message : "您确定该项目购买成功了么？",
			title : "成功提示",
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

	function failProduct(url) {
		bootbox.dialog({
			message : "您确定该项目购买失败了么？",
			title : "失败提示",
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
			<li><i class="fa fa-home"></i> <a href="#">高端理财</a></li>
			<li><a href="#">聚幕交易</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>聚幕交易列表</h1>
		</div>
		<!--Header Buttons-->
		<div class="header-buttons">
			<a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
			</a> <a class="refresh" id="refresh-toggler"
				href="${ctx}/product/productJumu/listProductJumu"> <i
				class="glyphicon glyphicon-refresh"></i>
			</a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
				class="glyphicon glyphicon-fullscreen"></i>
			</a>
		</div>
		<!--Header Buttons End-->
	</div>
	<!-- /Page Header -->
	<!-- Page Body -->
	<form action="${ctx}/product/productJumu/listProductJumu" method="post"
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
										<div class="form-group">
											<label class="control-label no-padding-right">状态</label> <select
												class="form-control" name="status" style="width: 100px;">
												<option value="0">购买中</option>
												<option value="1">购买失败</option>
												<option value="2">购买成功</option>
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
							<span class="widget-caption">聚幕交易列表</span>
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
											<th>项目编号</th>
											<th>项目名称</th>
											<th>投资金额</th>
											<th>投资状态</th>
											<th>投资时间</th>
											<th width="200px" style="text-align: center;">操作</th>
										</tr>
									</thead>
									<tbody style="font-size: 12px;">
										<c:forEach items="${page.content}" var="productlogjumu"
											varStatus="status">
											<tr>
												<td class="table_no" width="80" align="center"></td>
												<td>
													<c:choose>
														<c:when test="${productlogjumu.user.userLeve.gradeNum eq 1}"><img src="${ctx}/static/lib/images/vip1.png" /></c:when>
														<c:when test="${productlogjumu.user.userLeve.gradeNum eq 2}"><img src="${ctx}/static/lib/images/vip2.png" /></c:when>
													</c:choose>
													<a href='${ctx}/userinfo/user/listUserInfo/<c:out value="${productlogjumu.user.id}" />'
													   data-simpletooltip="init" title='<c:out value="${productlogjumu.user.remark}" />'
													   <c:if test="${not empty productlogjumu.user.remark}">style='color: red;'</c:if>>
														<c:out value="${productlogjumu.user.username}" />
													</a>
												</td>
												<td>
													<a href='${ctx}/userinfo/user/listUserInfo/<c:out value="${productlogjumu.user.id}" />'
													   data-simpletooltip="init" title='<c:out value="${productlogjumu.user.remark}" />'
													   <c:if test="${not empty productlogjumu.user.remark}">style='color: red;'</c:if>>
														<c:out value="${productlogjumu.user.mobile}" />
													</a>
													</td>
												<td><c:out
														value="${productlogjumu.projectJumu.projectNumber}" /></td>
												<td><c:out
														value="${productlogjumu.projectJumu.projectName}" /></td>
												<td><c:out value="${productlogjumu.tradeMoney}" /></td>
												<td><c:choose>
														<c:when test="${productlogjumu.status eq 0}">  
													         	购买中
													   	</c:when>
														<c:when test="${productlogjumu.status eq 1}">  
													         	购买失败 
													    	</c:when>
														<c:when test="${productlogjumu.status eq 2}">  
													        	购买成功
													    	</c:when>
													</c:choose></td>
												<td><fmt:formatDate value="${productlogjumu.trade}"
														pattern="yyyy年MM月dd日 HH时mm分" /></td>
												<td width="200px" align="center">
												<c:if test="${productlogjumu.status eq 0 }">
													<shiro:hasPermission
														name="productjumu_audit">
														<a class="btn btn-xs btn-success"
															onclick="successProduct('${ctx}/product/productJumu/successProduct/<c:out value="${productlogjumu.id}" />')"><i
															class="fa fa-legal"></i>成功</a>
                                                    			&nbsp;&nbsp;
                                                    </shiro:hasPermission> <shiro:hasPermission
														name="productjumu_audit">
														<a class="btn btn-danger btn-xs delete"
															onclick="failProduct('${ctx}/product/productJumu/failProduct/<c:out value="${productlogjumu.id}" />')"><i
															class="fa  fa-warning"></i>失败</a>
													</shiro:hasPermission>
												</c:if>
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