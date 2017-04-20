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
</head>
<body>

	<div class="page-breadcrumbs">
		<ul class="breadcrumb" style="line-height: 40px;">
			<li><i class="fa fa-home"></i> <a href="#">前台用户管理</a></li>
			<li><a href="#">用户资金</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>前台用户交易列表</h1>
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
	<form action="" method="post" id="listForm" name="listForm">
		<div class="page-body">

			<div class="widget">
				<div class="widget-header  with-footer">
					<span class="widget-caption">前台用户交易列表</span>
					<div class="widget-buttons">
						<a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
						</a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
						</a>
					</div>
				</div>
				<div class="widget-body">
					<div class="flip-scroll">
						<div style="text-align: right; margin-bottom: 10px;">
							<a href="${ctx}/userinfo/usermoneyinfo/usermoneylist"
								class="btn btn-labeled btn-blue"> <i
								class="btn-label glyphicon glyphicon-arrow-left"></i>返回
							</a>
						</div>

						<table class="table table-hover table-striped table-bordered">
							<thead style="font-size: 16px; font-weight: bold;">
								<tr>
									<th width="80" style="text-align: center;">序号</th>
									<th>产品</th>
									<th>类型</th>
									<th>操作金额</th>
									<th>操作时间</th>
								</tr>
							</thead>
							<tbody style="font-size: 12px;">
								<c:forEach items="${page.content}" var="buniesstake"
									varStatus="status">
									<tr>
										<td class="table_no" width="80" align="center"></td>
										<td><c:out value="${buniesstake.productName}" /></td>
										<td><c:choose>
												<c:when test="${buniesstake.depositType == 1}">
															2.0购买
												</c:when>
												<c:when test="${buniesstake.depositType == 2}">
															2.0预投
												</c:when>
												<c:when test="${buniesstake.depositType == 3}">
															2.0赎回
												</c:when>
												<c:when test="${buniesstake.depositType == 4}">
															2.0M购买
												</c:when>
												<c:when test="${buniesstake.depositType == 10}">
															2.0M到期
												</c:when>
												<c:when test="${buniesstake.depositType == 12}">
															2.0M转让
												</c:when>
												<c:when test="${buniesstake.depositType == 6}">
															2.0+购买
												</c:when>

												<c:when test="${buniesstake.depositType == 7}">
															2.0+到期
												</c:when>
											</c:choose></td>
										<td><fmt:formatNumber value="${buniesstake.buyFund}"
												type="number" /></td>

										<td><fmt:formatDate value="${buniesstake.createTime}"
												pattern="yyyy年MM月dd日 HH时mm分" /></td>
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
