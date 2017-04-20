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
			//window.location.href = "http://localhost:8080/ydzb-web/static/download"+ filename;
			}
		});

	function exprotExcel(condition) {
		var url = "${ctx}/userinfo/platformcount/exportExcel/" + condition
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

	<div class="page-breadcrumbs">
		<ul class="breadcrumb" style="line-height: 40px;">
			<li><i class="fa fa-home"></i> <a href="#">前台用户管理</a></li>
			<li><a href="#">平台支出统计</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>平台支出统计列表</h1>
		</div>
		<!--Header Buttons-->
		<div class="header-buttons">
			<a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
			</a> <a class="refresh" id="refresh-toggler"
				href="${ctx}/userinfo/platformcount/listPlatformcount"> <i
				class="glyphicon glyphicon-refresh"></i>
			</a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
				class="glyphicon glyphicon-fullscreen"></i>
			</a>
		</div>
		<!--Header Buttons End-->
	</div>
	<!-- /Page Header -->
	<!-- Page Body -->
	<form action="${ctx}/userinfo/platformcount/listPlatformcount"
		method="post" id="listForm" name="listForm">
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
											<label class="control-label no-padding-right">支出类型</label> <select
												class="form-control" name="type" style="width: 200px;">
												<option value="">全部</option>
												<option value="2">现金红包</option>
												<option value="5">2.0M收益</option>
												<option value="6">活动收益</option>
												<option value="8">2.0收益</option>
												<option value="13">2.0M推荐返现</option>
												<option value="15">银多发工资</option>
												<option value="16">银多发红包</option>
											</select>
										</div>
										<div class="form-group">
											<label class="control-label no-padding-right"></label>日期：从 <input
												type="text" name="startTime" class="form-control"
												tabindex="1" style="padding-left: 50px;" maxlength="40"
												onfocus="WdatePicker({skin:'twoer'})"> 到 <input
												type="text" name="endTime" class="form-control" tabindex="1"
												style="padding-left: 50px;" maxlength="40"
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
							<span class="widget-caption">前台用户列表</span>
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
								<div
									style="margin-bottom: 10px; text-align: center; color: red; font-size: 15px">
									支出总计：
									<c:out value="${totalFund}" />
								</div>
								<table class="table table-hover table-striped table-bordered">
									<thead style="font-size: 16px; font-weight: bold;">
										<tr>
											<th width="80" style="text-align: center;">序号</th>
											<th>支出类型</th>
											<th>支出金额</th>
											<th>支出时间</th>
											<th>用户手机号</th>
										</tr>
									</thead>
									<tbody style="font-size: 12px;">
										<c:forEach items="${page.content}" var="platform"
											varStatus="status">
											<tr>
												<td class="table_no" width="80" align="center"></td>
												<td><c:out value="${platform.typeName}" /></td>
												<td><c:out value="${platform.fund}" /></td>
												<td><fmt:formatDate value="${platform.createDate}"
														pattern="yyyy年MM月dd日 HH时mm分" /></td>
												<td><c:out value="${platform.user.mobile}" /></td>
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