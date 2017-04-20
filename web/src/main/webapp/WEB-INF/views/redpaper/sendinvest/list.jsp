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
	function checkradio() {
		var item = $(":radio:checked");
		var len = item.length;
		if (len > 0) {
			//判断是否存在用户
			var result = "${currentPerson}";
			if (result > 0) {
				var id = $('input:radio:checked').val();
				var address = "${ctx}/redpapermanager/sendinvest/subredpack?id=" + id;
				bootbox.dialog({
					message : "您确定要发送体验金么？",
					title : "体验金发放提示",
					className : "modal-darkorange",
					buttons : {
						success : {
							label : "确认",
							className : "btn-default",
							callback : function() {
								window.location.href = address;
							}
						},
						"取消" : {
							className : "btn-default",
							callback : function() {
							}
						}
					}
				});
			} else {
				Notify("无符号条件的用户，不能进行体验金发放！", 'top-left', '5000', 'warning','fa-warning', true);
			}
		} else {
			Notify("请选择要发放的体验金！", 'top-left', '5000', 'warning', 'fa-warning',true);
		}
	}
</script>


</head>
<body>

	<!-- Page Breadcrumb -->
	<div class="page-breadcrumbs">
		<ul class="breadcrumb" style="line-height: 40px;">
			<li><i class="fa fa-home"></i> <a href="#">红包管理</a></li>
			<li><a href="#">体验金发放</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>体验金列表</h1>
		</div>
		<!--Header Buttons-->
		<div class="header-buttons">
			<a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
			</a> <a class="refresh" id="refresh-toggler"
				href="${ctx}/redpapermanager/sendinvest/queryusers"> <i
				class="glyphicon glyphicon-refresh"></i>
			</a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
				class="glyphicon glyphicon-fullscreen"></i>
			</a>
		</div>
		<!--Header Buttons End-->
	</div>
	<!-- /Page Header -->
	<!-- Page Body -->
	<form action="${ctx}/redpapermanager/sendinvest/queryusers"
		method="post" id="listForm" name="listForm">
		<div class="page-body">
			<div class="row">
				<div class="col-xs-12 col-md-12">
					<div class="row">
						<div class="col-lg-12 col-sm-12 col-xs-12">
							<div class="widget">
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
											账户总额&nbsp;<input type="text" name="accountStart"
												class="form-control" tabindex="1"
												style="padding-left: 10px;" maxlength="40">&nbsp;到&nbsp;<input
												type="text" name="accountEnd" class="form-control"
												tabindex="1" style="padding-left: 10px;" maxlength="40">
										</div>
										<div class="form-group">
											&nbsp;&nbsp;投资金额(含体验金)&nbsp;<input type="text"
												name="totalInvestStart" class="form-control" tabindex="1"
												style="padding-left: 10px;" maxlength="40">
											&nbsp;到&nbsp; <input type="text" name="totalInvestEnd"
												class="form-control" tabindex="1"
												style="padding-left: 10px;" maxlength="40">
										</div>
										<!-- 
										<div class="form-group">
											&nbsp;&nbsp;投资金额(不含体验金)&nbsp;<input type="text"
												name="investStart" class="form-control" tabindex="1"
												style="padding-left: 10px;" maxlength="40">
											&nbsp;到&nbsp; <input type="text" name="investEnd"
												class="form-control" tabindex="1"
												style="padding-left: 10px;" maxlength="40">
										</div>
										 -->
									</div>

									<div class="form-inline" role="form">
										<div class="form-group">
											注册日期&nbsp;<input type="text" name="regiestStartDate"
												class="form-control" tabindex="1"
												style="padding-left: 10px;" maxlength="40"
												onfocus="WdatePicker({skin:'twoer'})">&nbsp;到&nbsp;
											<input type="text" name="regiestEndDate" class="form-control"
												onfocus="WdatePicker({skin:'twoer'})" tabindex="1"
												style="padding-left: 10px;" maxlength="40">
										</div>

										<div class="form-group">
											&nbsp;&nbsp;推荐人数&nbsp; <input type="text"
												name="recommCountStart" class="form-control" tabindex="1"
												style="padding-left: 10px;" maxlength="40">
											&nbsp;到&nbsp; <input type="text" name="recommCountEnd"
												class="form-control" tabindex="1"
												style="padding-left: 10px;" maxlength="40">
										</div>

										<div class="form-group">
											&nbsp;&nbsp;手机号<input type="text" name="mobile"
												class="form-control" tabindex="1"
												style="padding-left: 10px; width: 200px" maxlength="40">
										</div>


										<div class="form-group">
											&nbsp;&nbsp; <a href="javascript:searchData();"
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
							<span class="widget-caption">体验金列表</span>
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
											当前选中人数为<c:out value="${empty currentPerson  ? 0 : currentPerson}" />
									</div>
									<a class="btn btn-labeled btn-success"
										href="javascript:checkradio();"><i
										class="btn-label glyphicon glyphicon-envelope"></i>体验金发放</a>
								</div>

								<table class="table table-hover table-striped table-bordered">
									<thead style="font-size: 16px; font-weight: bold;">
										<tr>
											<th width="80px" style="text-align: center;">单选</th>
											<th>序号</th>
											<th>发放金额</th>
											<th>有效天数</th>
											<th>发送短信</th>
										</tr>
									</thead>
									<tbody style="font-size: 12px;">
										<c:forEach items="${page}" var="invest"
											varStatus="status">
											<tr>
												<td style="text-align: center;"><label> <input
														name="form-field-radio" type="radio" class="colored-blue"
														value="${invest.id }"> <span class="text"></span>
												</label></td>
												<td><c:out value="${status.count}" /></td>
												<td><c:out value="${invest.copies}" /></td>
												<td><c:out value="${invest.days}" /></td>
												<td><c:if test="${invest.isSend eq 0  }">是</c:if> <c:if
														test="${ invest.isSend eq 1  }">否</c:if></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>

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