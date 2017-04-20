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

	<div id="myModal" class="modal fade modal-darkorange" tabindex="-1"
		role="dialog" aria-hidden="true" style="display: none;">
		<div class="modal-dialog">
			<div class="modal-content"></div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>


	<!-- Page Breadcrumb -->
	<div class="page-breadcrumbs">
		<ul class="breadcrumb" style="line-height: 40px;">
			<li><i class="fa fa-home"></i> <a href="#">高端理财</a></li>
			<li><a href="#">配资账户</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>配资账户</h1>
		</div>
		<!--Header Buttons-->
		<div class="header-buttons">
			<a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
			</a> <a class="refresh" id="refresh-toggler"
				href="${ctx}/matchstock/matchstockAccout/listAccout"> <i
				class="glyphicon glyphicon-refresh"></i>
			</a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
				class="glyphicon glyphicon-fullscreen"></i>
			</a>
		</div>
		<!--Header Buttons End-->
	</div>
	<!-- /Page Header -->
	<!-- Page Body -->
	<form action="${ctx}/matchstock/matchstockAccout/listAccout"
		method="post" id="listForm" name="listForm">
		<div class="page-body">
			<div class="row">
				<div class="col-xs-12 col-md-12">
					<div class="row"></div>
					<div class="widget">
						<div class="widget-header  with-footer">
							<span class="widget-caption">用户列表</span>
							<div class="widget-buttons">
								<a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
								</a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
								</a>
							</div>
						</div>
						<div class="widget-body">
							<div class="flip-scroll">
								<table class="table table-hover table-striped table-bordered">
									<thead style="font-size: 16px; font-weight: bold;">
										<tr>
											<th width="80" style="text-align: center;">序号</th>
											<th>累计保证金</th>
											<th>现有保证金</th>
											<th>配资么累计利息</th>
											<th>配资么现有利息</th>
											<th>银多累计利息</th>
											<th>银多现有利息</th>
											<th width="200" style="text-align: center;">操作</th>
										</tr>
									</thead>
									<tbody style="font-size: 12px;">
										<c:forEach items="${page.content}" var="account"
											varStatus="status">
											<tr>
												<td class="table_no" width="80" align="center"></td>
												<td><c:out value="${account.historyMargin}" /></td>
												<td><c:out value="${account.margin}" /></td>
												<td><c:out value="${account.historyInterestFund}" /></td>
												<td><c:out value="${account.interestFund}" /></td>
												<td><c:out value="${account.historyInterest}" /></td>
												<td><c:out value="${account.interest}" /></td>
												<td style="text-align: center;"><shiro:hasPermission
														name="accout_recharge">
														<a href='${ctx}/matchstock/matchstockAccout/listAccoutLog'
															class="btn btn-info btn-xs edit"><i
															class="fa fa-edit"></i>流水</a>
                                                    			&nbsp;&nbsp;
                                                    			</shiro:hasPermission> <shiro:hasPermission
														name="accout_recharge">
														<a data-toggle="modal" data-target="#myModal"
															href='${ctx}/matchstock/matchstockAccout/rechargeAccount'
															class="btn btn-info btn-xs edit"><i
															class="fa fa-edit"></i>充值</a>
                                                    			&nbsp;&nbsp;
                                                    		  </shiro:hasPermission> <shiro:hasPermission
														name="accout_withdraw">
														<a data-toggle="modal" data-target="#myModal"
															href='${ctx}/matchstock/matchstockAccout/withDrawAccount'
															class="btn btn-info btn-xs edit"><i
															class="fa fa-edit"></i>提现</a>&nbsp;&nbsp;
														<a data-toggle="modal" data-target="#myModal"
															href='${ctx}/matchstock/matchstockAccout/withDrawAccountInterest'
															class="btn btn-info btn-xs edit"><i
															class="fa fa-edit"></i>提现利息</a>

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