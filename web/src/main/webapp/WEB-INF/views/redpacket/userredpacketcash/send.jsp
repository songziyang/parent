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
				var address = "${ctx}/redpacket/userRedpacketCash/sendRedpacket?id=" + id;
				bootbox.dialog({
					message : "您确定要发送此红包么？",
					title : "红包发放提示",
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
				Notify("对不起无符号条件的用户，您不能进行红包发放！", 'top-left', '5000', 'warning',
						'fa-warning', true);
			}
		} else {
			Notify("请选择要发放的红包！", 'top-left', '5000', 'warning', 'fa-warning',
					true);
		}
	}
</script>


</head>
<body>

	<!-- Page Breadcrumb -->
	<div class="page-breadcrumbs">
		<ul class="breadcrumb" style="line-height: 40px;">
			<li><i class="fa fa-home"></i> <a href="#">红包管理</a></li>
			<li><a href="#">红包维护</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>红包列表</h1>
		</div>
		<!--Header Buttons-->
		<div class="header-buttons">
			<a class="sidebar-toggler" href="#">
				<i class="fa fa-arrows-h"></i>
			</a>
			<a class="refresh" id="refresh-toggler" href="${ctx}/redpacket/userRedpacketCash/send">
				<i class="glyphicon glyphicon-refresh"></i>
			</a>
			<a class="fullscreen" id="fullscreen-toggler" href="#">
				<i class="glyphicon glyphicon-fullscreen"></i>
			</a>
		</div>
		<!--Header Buttons End-->
	</div>
	<!-- /Page Header -->
	<!-- Page Body -->
	<form action="${ctx}/redpacket/userRedpacketCash/querySendUserCount"
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
											账户总额&nbsp;<input type="text" name="startAccount"
												value="${condition.startAccount}" class="form-control"
												tabindex="1" style="padding-left: 10px;width: 100px;" maxlength="40">&nbsp;到&nbsp;<input
												type="text" name="endAccount"
												value="${condition.endAccount}" class="form-control"
												tabindex="1" style="padding-left: 10px;width: 100px;" maxlength="40">
										</div>
										<div class="form-group">
											&nbsp;&nbsp;投资金额&nbsp;<input type="text" name="startInvest"
												value="${condition.startInvest}" class="form-control"
												tabindex="1" style="padding-left: 10px;width: 100px;" maxlength="40">
											&nbsp;到&nbsp; <input type="text" name="endInvest"
												value="${condition.endInvest}" class="form-control"
												tabindex="1" style="padding-left: 10px;width: 100px;" maxlength="40">
										</div>

										<div class="form-group">
											<label class="control-label no-padding-right">&nbsp;&nbsp;用户类型</label>
											<select class="form-control" name="userType"
												value="${condition.userType}" style="width: 100px;">
												<option value="-1">请选择</option>
												<option value="0" <c:if test="${condition.userType == 0 }">selected</c:if>>
													普通用户
												</option>
												<option value="1" <c:if test="${condition.userType == 1 }">selected</c:if>>
													银多老用户
												</option>
												<option value="2" <c:if test="${condition.userType == 2 }">selected</c:if>>
													股东用户
												</option>
												<option value="3" <c:if test="${condition.userType == 3 }">selected</c:if>>
													业务员用户
												</option>
												<option value="4" <c:if test="${condition.userType == 4 }">selected</c:if>>
													新鲜说用户
												</option>
												<option value="5" <c:if test="${condition.userType == 5 }">selected</c:if>>
													麦罗用户
												</option>
											</select>
										</div>

										<div class="form-group">
											<label class="control-label no-padding-right">&nbsp;&nbsp;用户等级</label>
											<select class="form-control" name="userLevel"
												value="${condition.userLevel}" style="width: 100px;">
												<option value="-1">请选择</option>
												<c:forEach items="${vipGrades}" var="vipGrade">
													<option value="${vipGrade.id}" <c:if test="${vipGrade.id == condition.userLevel }">selected</c:if>>
														${vipGrade.gradeName}
													</option>
												</c:forEach>
											</select>
										</div>

										<div class="form-group">
											注册日期&nbsp;<input type="text" name="registerStartDate"
															 value="${condition.registerStartDate}" class="form-control"
															 tabindex="1" style="padding-left: 10px;width: 100px;" maxlength="40"
															 onfocus="WdatePicker({skin:'twoer'})">&nbsp;到&nbsp;
											<input type="text" name="registerEndDate"
												   value="${condition.registerEndDate}" class="form-control"
												   onfocus="WdatePicker({skin:'twoer'})" tabindex="1"
												   style="padding-left: 10px;width: 100px;" maxlength="40">
										</div>


									</div>

									<div class="form-inline" role="form">


										<div class="form-group">
											&nbsp;&nbsp;推荐人数&nbsp; <input type="text"
												name="refStartNum"
												value="${condition.refStartNum}" class="form-control"
												tabindex="1" style="padding-left: 10px;width: 100px;" maxlength="40">&nbsp;到&nbsp; <input type="text" name="refEndNum"
												value="${condition.refEndNum}" class="form-control"
												tabindex="1" style="padding-left: 10px;width: 100px;" maxlength="40">
										</div>

										<div class="form-group">
											&nbsp;&nbsp;手机号 <input type="text" name="mobile"
												class="form-control" tabindex="1"
												style="padding-left: 10px; width: 160px" maxlength="40"
												value=${condition.mobile}>
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
							<span class="widget-caption">红包列表</span>
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
										<c:if test="${currentPerson >= 0 }">
											当前选中人数为${currentPerson }
										</c:if>

									</div>
									<a class="btn btn-labeled btn-success"
										href="javascript:checkradio();"><i
										class="btn-label glyphicon glyphicon-envelope"></i>红包发放</a>
								</div>

								<table class="table table-hover table-striped table-bordered">
									<thead style="font-size: 16px; font-weight: bold;">
										<tr>
											<th width="80" align="center">单选</th>
											<th width="80">序号</th>
											<th>红包名称</th>
											<th>金额</th>
											<th>触发种类</th>
											<th>使用有效天数</th>
											<th>活动开始时间</th>
											<th>活动结束时间</th>
										</tr>
									</thead>
									<tbody style="font-size: 12px;">
										<c:forEach items="${redpackets}" var="redpacket" varStatus="status">
											<tr>
												<th align="center" align="center">
													<label>
														<input name="form-field-radio" type="radio" class="colored-blue"
														value="${redpacket.id }" />
														<span class="text"></span>
													</label>
												</th>
												<td><c:out value="${status.count}" /></td>
												<td><c:out value="${redpacket.name}" /></td>
												<td><c:out value="${redpacket.fund}" /></td>
												<td>
													<c:choose>
														<c:when test="${redpacket.triggerType eq -1}">抽奖</c:when>
														<c:when test="${redpacket.triggerType eq 0}">手动</c:when>
														<c:when test="${redpacket.triggerType eq 1}">注册</c:when>
														<c:when test="${redpacket.triggerType eq 2}">充值</c:when>
														<c:when test="${redpacket.triggerType eq 3}">投资</c:when>
														<c:when test="${redpacket.triggerType eq 4}">推荐</c:when>
														<c:when test="${redpacket.triggerType eq 8}">分享</c:when>
														<c:when test="${redpacket.triggerType eq 9}">7日活动</c:when>
														<c:when test="${redpacket.triggerType eq 11}">定存推荐返现</c:when>
														<c:when test="${redpacket.triggerType eq 13}">周年活动现金红包</c:when>
													</c:choose>
												</td>
												<td><c:out value="${redpacket.useDays}" /></td>
												<td>
													<fmt:formatDate value="${redpacket.beginDate}"  pattern="yyyy年MM月dd日" />
												</td>
												<td>
													<fmt:formatDate value="${redpacket.finishDate}"  pattern="yyyy年MM月dd日" />
												</td>
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