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
<script type="text/javascript">
	$(document).ready(function() {
		var filename = '<c:out value="${fileName}"/>';
		if (filename != "") {
			 window.location.href ="http://keng.yinduoziben.com/static/download/"+filename;
			 //window.location.href = "http://localhost:8080/static/download"+ filename;
			}
		});

	function exprotExcel(condition) {
		var url = "${ctx}/product/productSales/export/" + condition
		window.location.href = url;
	}
</script>

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
			<li><i class="fa fa-home"></i> <a href="#">产品管理</a></li>
			<li><a href="#">产品售出</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>产品售出列表</h1>
		</div>
		<!--Header Buttons-->
		<div class="header-buttons">
			<a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
			</a> <a class="refresh" id="refresh-toggler"
				href="${ctx}/product/productSales/list"> <i
				class="glyphicon glyphicon-refresh"></i>
			</a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
				class="glyphicon glyphicon-fullscreen"></i>
			</a>
		</div>
		<!--Header Buttons End-->
	</div>
	<!-- /Page Header -->
	<!-- Page Body -->
	<form action="${ctx}/product/productSales/list"
		method="post" id="listForm" name="listForm">
		<div class="page-body">

			<%--<div class="row">--%>
				<%--<div class="col-lg-2 col-sm-2 col-xs-2">--%>
					<%--<div--%>
						<%--class="databox radius-bordered databox-shadowed databox-graded databox-vertical">--%>
						<%--<div class="databox-top bg-green">--%>
							<%--<div class="databox-icon">--%>
								<%--<i class="fa fa-clock-o"></i>--%>
							<%--</div>--%>
						<%--</div>--%>
						<%--<div class="databox-bottom text-align-center">--%>
							<%--<span class="databox-text">活期宝</span> <span--%>
								<%--class="databox-text">--%>
								<%--<c:out value="${empty current? '0/0': current}" />--%>
								<%--</span>--%>
						<%--</div>--%>
					<%--</div>--%>
				<%--</div>--%>
				<%--<div class="col-lg-2 col-sm-2 col-xs-2">--%>
					<%--<div--%>
						<%--class="databox radius-bordered databox-shadowed databox-graded databox-vertical">--%>
						<%--<div class="databox-top bg-green">--%>
							<%--<div class="databox-icon">--%>
								<%--<i class="fa fa-clock-o"></i>--%>
							<%--</div>--%>
						<%--</div>--%>
						<%--<div class="databox-bottom text-align-center">--%>
							<%--<span class="databox-text">三个月</span> <span class="databox-text"><c:out value="${empty threemonth? '0/0': threemonth}" /></span>--%>
						<%--</div>--%>
					<%--</div>--%>
				<%--</div>--%>
				<%--<div class="col-lg-2 col-sm-2 col-xs-2">--%>
					<%--<div--%>
						<%--class="databox radius-bordered databox-shadowed databox-graded databox-vertical">--%>
						<%--<div class="databox-top bg-green">--%>
							<%--<div class="databox-icon">--%>
								<%--<i class="fa fa-clock-o"></i>--%>
							<%--</div>--%>
						<%--</div>--%>
						<%--<div class="databox-bottom text-align-center">--%>
							<%--<span class="databox-text">六个月</span> <span class="databox-text"><c:out value="${empty sixmonth? 0/0: sixmonth}" /></span>--%>
						<%--</div>--%>
					<%--</div>--%>
				<%--</div>--%>
				<%--<div class="col-lg-2 col-sm-2 col-xs-2">--%>
					<%--<div--%>
						<%--class="databox radius-bordered databox-shadowed databox-graded databox-vertical">--%>
						<%--<div class="databox-top bg-green">--%>
							<%--<div class="databox-icon">--%>
								<%--<i class="fa fa-clock-o"></i>--%>
							<%--</div>--%>
						<%--</div>--%>
						<%--<div class="databox-bottom text-align-center">--%>
							<%--<span class="databox-text">十二个月</span> <span class="databox-text"><c:out value="${empty twelvemonth? '0/0': twelvemonth}" /></span>--%>
						<%--</div>--%>
					<%--</div>--%>
				<%--</div>--%>

			<%--</div>--%>

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
											<label class="control-label no-padding-right">产品类型</label> <select
												class="form-control" id="productId" name="typeId"
												style="width: 200px;">
												<option value="">全部</option>
												<c:forEach items="${productLst }" var="product">
													<option value='<c:out value="${product.id }" />'><c:out
															value="${product.type }" />
													</option>
												</c:forEach>
											</select>
										</div>
										<div class="form-group">
											<label class="control-label no-padding-right">产品名称</label> <input
												type="text" name="name" class="form-control"
												placeholder="产品">
										</div>
										<div class="form-group">
											<label class="control-label no-padding-right"></label>&nbsp&nbsp
											售出日期:从 <input type="text" name="startDate"
												class="form-control" tabindex="1"
												style="padding-left: 50px;" required maxlength="40"
												onfocus="WdatePicker({skin:'twoer'})"> 到 <input
												type="text" name="endDate" class="form-control"
												tabindex="1" style="padding-left: 50px;" required
												maxlength="40" onfocus="WdatePicker({skin:'twoer'})">
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
							<span class="widget-caption">产品售出列表</span>
							<div class="widget-buttons">
								<a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
								</a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
								</a>
							</div>
						</div>
						<div class="widget-body">
							<div class="flip-scroll">
								<div style="text-align: right; margin-bottom: 10px;">
									<a
										href="javascript:exprotExcel('<c:out value="${condition}"/>');"
										class="btn btn-labeled btn-success"> <i
										class="btn-label glyphicon glyphicon-envelope"></i>导出Excel
									</a>
								</div>
								<table class="table table-hover table-striped table-bordered">
									<thead style="font-size: 16px; font-weight: bold;">
										<tr>
											<th width="80" style="text-align: center;">序号</th>
											<th>产品名称</th>
											<th>发布金额</th>
											<th>剩余金额</th>
											<th>售出份数</th>
											<th>发布利率(%)</th>
											<th>售出日期</th>
										</tr>
									</thead>
									<tbody style="font-size: 12px;">
										<c:forEach items="${page.content}" var="productsales"
											varStatus="status">
											<tr>
												<td class="table_no" width="80" align="center"></td>
												<td><c:out value="${productsales.productInfo.name}" /></td>
												<td><c:out value="${empty productsales.productInfo.funds? '-': productsales.productInfo.funds}" /></td>
												<td><c:out value="${empty productsales.productInfo.surplus? '-': productsales.productInfo.surplus}" /></td>
												<td>
													<c:choose>
														<c:when test="${productsales.productInfo.cylcleDays eq 1}">
															<a href="${ctx}/product/productSales/currentDetail?buyTime=${productsales.buyDate}&productId=${productsales.productInfo.id}">
																<c:out value="${productsales.buyCopies}" />
															</a>
														</c:when>
														<c:otherwise>
															<a href="${ctx}/product/productSales/regularDetail?buyTime=${productsales.buyDate}&productId=${productsales.productInfo.id}">
																<c:out value="${productsales.buyCopies}" />
															</a>
														</c:otherwise>
													</c:choose>

												</td>
												<td><c:out value="${empty productsales.productInfo.interestRate? '-': productsales.productInfo.interestRate}" /></td>
												<td><fmt:formatDate value="${productsales.buyTime}"  pattern="yyyy年MM月dd日" /></td>
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