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
			message : "您确定交易失败么？",
			title : "交易失败提示",
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

	$(document)
			.ready(
					function() {
						var filename = '<c:out value="${fileName}"/>';
						if (filename != "") {
							window.location.href = "http://keng.yinduoziben.com/static/download/"+ filename; 
							//window.location.href = "http://localhost:8080/ydzb-web/static/download/"+ filename;
						}
					});

	function exprotExcel() {
		var url = "${ctx}/matchstock/trade/listExportExcel";
		window.location.href = url;
	}
</script>
</head>
<body>

	<div class="page-breadcrumbs">
		<ul class="breadcrumb" style="line-height: 40px;">
			<li><i class="fa fa-home"></i> <a href="#">高端理财</a></li>
			<li><a href="#">配资信息</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>配资信息</h1>
		</div>
		<!--Header Buttons-->
		<div class="header-buttons">
			<a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
			</a> <a class="refresh" id="refresh-toggler"
				href="${ctx}/matchstock/trade/listTrade"> <i
				class="glyphicon glyphicon-refresh"></i>
			</a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
				class="glyphicon glyphicon-fullscreen"></i>
			</a>
		</div>
		<!--Header Buttons End-->
	</div>
	<!-- /Page Header -->
	<!-- Page Body -->
	<form action="${ctx}/matchstock/trade/listTrade" method="post"
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
										<!--  
										<div class="form-group">
											<label class="control-label no-padding-right">用户名</label> <input
												type="text" name="username" class="form-control"
												placeholder="用户名">
										</div>
										-->
										<div class="form-group">
											<label class="control-label no-padding-right">手机号</label> <input
												type="text" name="mobile" class="form-control"
												placeholder="手机号">
										</div>


										<div class="form-group">
											<label class="control-label no-padding-right">订单号</label> <input
												type="text" name="ordernum" class="form-control"
												placeholder="订单号">
										</div>


										<div class="form-group">
											<label class="control-label no-padding-right">股票代码</label> <input
												type="text" name="stockNumb" class="form-control"
												placeholder="股票代码">
										</div>

										<div class="form-group">
											<label class="control-label no-padding-right">交易状态</label> <select
												class="form-control" name="status" style="width: 100px;">
												<option value="1">交易中</option>
												<option value="2">交易成功</option>
												<option value="3">交易完成</option>
												<option value="4">交易失败</option>
											</select>
										</div>

										<div class="form-group">
											<label class="control-label no-padding-right">配资类型</label> <select
												class="form-control" name="mtype" style="width: 100px;">
												<option value="">全部</option>
												<option value="1">随机配资</option>
												<option value="2">按天配资</option>
												<option value="3">按月配资</option>
											</select>
										</div>

										<div class="form-group">
											<label class="control-label no-padding-right"></label>交易完成日期：从
											<input type="text" name="startDate" class="form-control"
												tabindex="1" style="padding-left: 10px;" required
												maxlength="40"
												onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy/MM/dd'})"
												id="startDate"> 到 <input type="text" name="endDate"
												class="form-control" tabindex="1"
												style="padding-left: 10px;" required maxlength="40"
												onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy/MM/dd'})">
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
							<span class="widget-caption">配资信息列表</span>
							<div class="widget-buttons">
								<a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
								</a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
								</a>
							</div>
						</div>
						<div class="widget-body">
							<div class="flip-scroll">
								<div class="flip-scroll">
									<div style="text-align: right; margin-bottom: 10px;">

										<div style="text-align: center; color: red; font-size: 15px">
											当前收益:${ empty totalRevenue ? 0 :totalRevenue}</div>

										<shiro:hasPermission name="trade_deal">
											<a href="${ctx}/matchstock/trade/uploadFile"
												class="btn btn-labeled btn-success"> <i
												class="btn-label glyphicon glyphicon-envelope"></i>导入
											</a>
										</shiro:hasPermission>

										<shiro:hasPermission name="trade_list">
											<a href="javascript:exprotExcel();"
												class="btn btn-labeled btn-success"> <i
												class="btn-label glyphicon glyphicon-envelope"></i>导出
											</a>
										</shiro:hasPermission>

									</div>

									<table class="table table-hover table-striped table-bordered">
										<thead style="font-size: 16px; font-weight: bold;">
											<tr>
												<th width="80" style="text-align: center;">序号</th>
												<th>用户名</th>
												<th>手机号</th>
												<th>订单号</th>
												<th>保证金</th>
												<th>杠杆</th>
												<th>配资金额</th>
												<th>利息率</th>
												<th>股票代码</th>
												<th>买入时间</th>
												<th>卖出时间</th>
												<th>交易状态</th>
												<th>交易类型</th>
												<th>交易周期</th>
												<th style="text-align: center;">操作</th>
											</tr>
										</thead>
										<tbody style="font-size: 12px;">
											<c:forEach items="${page.content}" var="trade"
												varStatus="status">
												<tr>
													<td class="table_no" width="80" align="center"></td>
													<td>
														<c:choose>
															<c:when test="${trade.user.userLeve.gradeNum eq 1}"><img src="${ctx}/static/lib/images/vip1.png" /></c:when>
															<c:when test="${trade.user.userLeve.gradeNum eq 2}"><img src="${ctx}/static/lib/images/vip2.png" /></c:when>
														</c:choose>
														<a href='${ctx}/userinfo/user/listUserInfo/<c:out value="${trade.user.id}" />'
														   data-simpletooltip="init" title='<c:out value="${trade.user.remark}" />'
														   <c:if test="${not empty trade.user.remark}">style='color: red;'</c:if>>
															<c:out value="${trade.user.username}" />
													</a></td>
													<td>
														<a href='${ctx}/userinfo/user/listUserInfo/<c:out value="${trade.user.id}" />'
														   data-simpletooltip="init" title='<c:out value="${trade.user.remark}" />'
														   <c:if test="${not empty trade.user.remark}">style='color: red;'</c:if>>
															<c:out value="${trade.user.mobile}" />
														</a>
													</td>
													<td><c:out value="${trade.ordernum}" /></td>
													<td><c:out value="${trade.margin}" /></td>
													<td><c:out value="${trade.lever}" /></td>
													<td><c:out value="${trade.matchMoney}" /></td>
													<td><c:out value="${trade.interestrate}" />‰</td>
													<td><c:out value="${trade.stockNumb}" /></td>
													<td><c:out value="${trade.buyStartDate}" /></td>
													<td><c:out value="${trade.buyEndDate}" /></td>
													<td><c:if test="${trade.state eq 1 }">交易中</c:if> <c:if
															test="${trade.state eq 2 }">交易成功</c:if> <c:if
															test="${trade.state eq 3 }">交易完成</c:if> <c:if
															test="${trade.state eq 4 }">交易失败</c:if></td>
													<td><c:if test="${trade.mtype eq 1 }">随机配资</c:if> <c:if
															test="${trade.mtype eq 2 }">按天配资</c:if> <c:if
															test="${trade.mtype eq 3 }">按月配资</c:if></td>
													<td><c:out value="${trade.cycle}" /> <c:if
															test="${trade.mtype eq 1 }">天</c:if> <c:if
															test="${trade.mtype eq 2 }">天</c:if> <c:if
															test="${trade.mtype eq 3 }">月</c:if></td>
													<td style="text-align: center;"><shiro:hasPermission
															name="trade_deal">
															<c:if test="${trade.state eq 1 }">
																<a
																	onclick="successProduct('${ctx}/matchstock/trade/buyTradeFail/<c:out value="${trade.id}" />')"
																	class="btn btn-info btn-xs btn-danger"><i
																	class="fa fa-warning"></i>失败</a>
																<c:if test="${trade.mtype eq 2 || trade.mtype eq 3}">
																	<a
																		href='${ctx}/matchstock/trade/buyTrade/<c:out value="${trade.id}" />'
																		class="btn btn-info btn-xs edit"><i
																		class="fa fa-edit"></i>买入</a>
																</c:if>
															</c:if>

															<c:if test="${trade.state eq 2 }">
																<c:if test="${trade.mtype eq 2 || trade.mtype eq 3}">
																	<a
																		href='${ctx}/matchstock/trade/salesTrade/<c:out value="${trade.id}" />'
																		class="btn btn-info btn-xs edit"><i
																		class="fa fa-edit"></i>卖出</a>
																</c:if>
															</c:if>
														</shiro:hasPermission> <c:if test="${trade.state eq 1 }">
															<c:if test="${trade.mtype eq 2 || trade.mtype eq 3}">
																<a
																	href='${ctx}/matchstock/trade/findTrade/<c:out value="${trade.id}" />'
																	class="btn btn-info btn-xs edit"><i
																	class="fa fa-edit"></i>编辑</a>
															</c:if>
															<c:if test="${trade.mtype eq 1 }">
																<a
																	href='${ctx}/matchstock/trade/findTrade/<c:out value="${trade.id}" />'
																	class="btn btn-info btn-xs edit"><i
																	class="fa fa-edit"></i>详细</a>
															</c:if>
														</c:if> <c:if test="${trade.state ne 1 }">
															<a
																href='${ctx}/matchstock/trade/findTrade/<c:out value="${trade.id}" />'
																class="btn btn-info btn-xs edit"><i
																class="fa fa-edit"></i>详细</a>
														</c:if></td>
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
