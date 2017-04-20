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
	function queryByID(id) {
		var address = "${ctx}/userinfo/usermoneyinfo/findoperation/" + id;
		alert(id);
	}
</script>
</head>
<body>

	
	<div class="page-breadcrumbs">
		<ul class="breadcrumb" style="line-height: 40px;">
			<li><i class="fa fa-home"></i> <a href="#">业务员管理</a></li>
			<li><a href="#">推荐用户资金记录</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>推荐用户资金记录</h1>
		</div>
		<!--Header Buttons-->
		<div class="header-buttons">
			<a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
			</a> <a class="refresh" id="refresh-toggler"
				href="">
				<i class="glyphicon glyphicon-refresh"></i>
			</a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
				class="glyphicon glyphicon-fullscreen"></i>
			</a>
		</div>
		<!--Header Buttons End-->
	</div>
	<!-- /Page Header -->
	<!-- Page Body -->
	<form action="${ctx}/userinfo/referralUser/findoperation/${userId}" method="post" id="listForm" name="listForm">
		<div class="page-body">
			<div class="row">
				<div class="col-lg-12 col-sm-12 col-xs-12">
					<div class="widget collapsed">
						<div class="widget-header bordered-bottom bordered-sky">
							<i class="widget-icon fa fa-arrows-v blue"></i> <span
								class="widget-caption">查询条件</span>
							<div class="widget-buttons">
								<a href="#"></a> <a href="#" data-toggle="collapse"> <i
									class="fa fa-plus blue"></i>
								</a>
							</div>
						</div>
						<div class="widget-body">
							<div class="form-inline" role="form">
								<div class="form-group">
									<div>
										<label class="control-label no-padding-right">&nbsp;产品类型</label>
										<select class="form-control" name="product"
											style="width: 200px;">
											<option value="0" selected>全部</option>
											<option value="1">活期宝</option>
											<option value="2">定存宝</option>
										</select>
									</div>
								</div>
								<div class="form-group">操作时间&nbsp;
									<input type="text" name="operateStartDate"
										class="form-control" tabindex="1"
										id="operateStartDate"
										style="padding-left: 10px;" maxlength="40"
										onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'operateEndDate\')}',skin:'twoer'})">&nbsp;到&nbsp;
									<input type="text" name="operateEndDate" class="form-control"
										id="operateEndDate" 
										onfocus="WdatePicker({minDate:'#F{$dp.$D(\'operateStartDate\')}',skin:'twoer'})" tabindex="1"
										style="padding-left: 10px;" maxlength="40">
								</div>
								<div class="form-group">
									<a href="javascript:searchData();"
										class="btn btn-labeled btn-blue"> <i
										class="btn-label fa fa-search"></i>查询
									</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="widget">
				<div class="widget-header  with-footer">
					<span class="widget-caption">推荐用户资金记录</span>
					<div class="widget-buttons">
						<a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
						</a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
						</a>
					</div>
				</div>
				<div class="widget-body">
					<div class="flip-scroll">
<!-- 						<div style="text-align: right; margin-bottom: 10px;"> -->
<%-- 							<a href="${ctx}/userinfo/referralUser/list?referralMobile=${user.mobile}" --%>
<!-- 								class="btn btn-labeled btn-blue"> <i -->
<!-- 								class="btn-label glyphicon glyphicon-arrow-left"></i>返回 -->
<!-- 							</a> -->
<!-- 						</div> -->

						<table class="table table-hover table-striped table-bordered">
							<thead style="font-size: 16px; font-weight: bold;">
								<tr>
									<th width="80" style="text-align: center;">序号</th>
									<th>类型</th>
									<th>操作金额</th>
									<th>账户余额</th>
									<th>操作时间</th>
								</tr>
							</thead>
							<tbody style="font-size: 12px;">
								<c:forEach items="${page.content}" var="useroperated"
									varStatus="status">
									<tr>
										<td class="table_no" width="80" align="center"></td>
										<td><c:out value="${useroperated.typeName}" /></td>
										<td><c:out value="${useroperated.fund}" /></td>
										<td><c:out value="${useroperated.usableFund}" /></td>
										<td><fmt:formatDate value="${useroperated.createTime}"
												pattern="yyyy年MM月dd日HH点mm分" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<tags:pagination page="${page}"></tags:pagination>
					</div>
				</div>
			</div>
		</div>
	</form>
	<%@include file="/static/inc/footer.inc"%>
</body>
</html>
