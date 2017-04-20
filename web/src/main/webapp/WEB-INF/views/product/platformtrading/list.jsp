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
<script src="${ctx}/static/lib/echart/dist/echarts.js"></script>
<style type="text/css">
.form-inline .form-group {
	margin-bottom: 10;
}
</style>
<script type="text/javascript">
	$(function() {
		var filename = '<c:out value="${fileName}"/>';
		if (filename != "") {
			window.location.href = "http://keng.yinduoziben.com/static/download/"+ filename;
			//window.location.href ="http://localhost:8080/static/download/"+filename;
		}
	});
		
	function exprotExcel(condition) {
		var url = "${ctx}/product/platformTrading/exportExcel/" + condition
		window.location.href = url;
	}
</script>
</head>
<body>

	<!-- Page Breadcrumb -->
	<div class="page-breadcrumbs">
		<ul class="breadcrumb" style="line-height: 40px;">
			<li><i class="fa fa-home"></i> <a href="#">产品管理</a></li>
			<li><a href="#">交易统计</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>交易统计</h1>
		</div>
		<!--Header Buttons-->
		<div class="header-buttons">
			<a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
			</a> <a class="refresh" id="refresh-toggler"
				href="${ctx}/product/platformTrading/list"> <i
				class="glyphicon glyphicon-refresh"></i>
			</a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
				class="glyphicon glyphicon-fullscreen"></i>
			</a>
		</div>
		<!--Header Buttons End-->
	</div>
	<!-- /Page Header -->
	<!-- Page Body -->
	<form action="${ctx}/product/platformTrading/list" method="post"
		id="listForm" name="listForm">
		<div class="page-body">
			<div>
				<div id="main" style="height: 440px;"></div>
				<script type="text/javascript">
					require.config({
						paths : {
							echarts : ctx + '/static/lib/echart/dist'
						}
					});

					require([ 'echarts', 'echarts/chart/line',
							'echarts/chart/bar' ], function(ec) {
						// 基于准备好的dom，初始化echarts图表
						var myChart = ec.init(document.getElementById('main'));

						var option = {
							tooltip : {
								trigger : 'axis'
							},
							legend : {
								data : [ '充值', '提现', '活期宝', '定存宝', '机构宝', '私人订制', '随心存' ]
							},
							calculable : true,
							xAxis : [ {
								type : 'category',
								boundaryGap : false,
								data :  ${dates}
							} ],
							yAxis : [ {
								type : 'value',
								axisLabel : {
									formatter : '{value}'
								}
							} ],
							series : [ {
								name : '充值',
								type : 'line',
								data : ${recharges}
								
							}, {
								name : '提现',
								type : 'line',
								data : ${withdraws}
							} ,
							 {
								name : '活期宝',
								type : 'line',
								data : ${currents}
							} ,
							 {
								name : '定存宝',
								type : 'line',
								data : ${regulars}
							} ,
							{
								name : '机构宝',
								type : 'line',
								data : ${institutions}
							} ,
							{
								name : '私人订制',
								type : 'line',
								data : ${privateOrderings}
							},
							{
								name : '随心存',
								type : 'line',
								data : ${frees}
							}
							]
						};

						myChart.setOption(option);
					});
				</script>
			</div>

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
											<label class="control-label no-padding-right">操作类型</label> <select
												class="form-control" name="type" style="width: 200px;">
												<option value="">全部</option>
												<option value="1">充值</option>
												<option value="2">提现</option>
												<option value="3">活期宝</option>
												<option value="4">定存宝</option>
												<option value="5">机构宝</option>
												<option value="6">私人订制</option>
												<option value="7">随心存</option>
											</select>
										</div>

										<div class="form-group">
											<label class="control-label no-padding-right"></label>&nbsp&nbsp
											操作日期:从 <input type="text" name="startDate"
												class="form-control" tabindex="1"
												style="padding-left: 50px;" required maxlength="40"
												onfocus="WdatePicker({skin:'twoer'})"> 到 <input
												type="text" name="endDate" class="form-control" tabindex="1"
												style="padding-left: 50px;" required maxlength="40"
												onfocus="WdatePicker({skin:'twoer'})">
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
							<span class="widget-caption">交易统计</span>
							<div class="widget-buttons">
								<a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
								</a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
								</a>
							</div>
						</div>
						<div class="widget-body">
							<div class="flip-scroll">

								<div style="text-align: right; margin-bottom: 10px;">
									<div style="text-align: center; color: red; font-size: 15px"
										id="result">
										总额：
										<fmt:formatNumber value="${empty total  ? 0 : total}"
											type="number" pattern="0.00" maxFractionDigits="2" />
									</div>
								</div>
								<div style="text-align: right;margin-bottom: 10px;">
	 								<a href="javascript:exprotExcel('<c:out value="${condition}"/>');"
											class="btn btn-labeled btn-success"> <i
											class="btn-label glyphicon glyphicon-envelope"></i>导出Excel
									</a>
								</div>
								<table class="table table-hover table-striped table-bordered">
									<thead style="font-size: 16px; font-weight: bold;">
										<tr>
											<th width="80" style="text-align: center;">序号</th>
											<th>金额</th>
											<th>类型</th>
											<th>操作日期</th>
										</tr>
									</thead>
									<tbody style="font-size: 12px;">
										<c:forEach items="${page.content}" var="record"
											varStatus="status">
											<tr>
												<td class="table_no" width="80" align="center"></td>
												<td><a href="${ctx}/platform/tradingDeal/list/${record.id}"><fmt:formatNumber value="${record.fund}"
														type="number" pattern="0.00" maxFractionDigits="2" /></a></td>
												<td>
													<c:if test="${record.type eq 1}">
														充值
													</c:if>
													<c:if test="${record.type eq 2}">
														提现
													</c:if>
													<c:if test="${record.type eq 3}">
														活期宝
													</c:if>
													<c:if test="${record.type eq 4}">
														定存宝
													</c:if>
													<c:if test="${record.type eq 5}">
														机构宝
													</c:if>
													<c:if test="${record.type eq 6}">
														私人订制
													</c:if>
													<c:if test="${record.type eq 7}">
														随心存
													</c:if>
												</td>
												<td><fmt:formatDate value="${record.operationDate}"  pattern="yyyy年MM月dd日 " /></td>
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