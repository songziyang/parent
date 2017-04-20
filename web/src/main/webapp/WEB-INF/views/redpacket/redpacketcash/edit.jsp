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
	<style>
		.form-group label {
			position: absolute;
			left: 10px;
			top: 7px;
			color: #999;
		}
	</style>
</head>
<body>

<!-- Page Breadcrumb -->
<div class="page-breadcrumbs">
	<ul class="breadcrumb" style="line-height: 40px;">
		<li><i class="fa fa-home"></i> <a href="#">红包管理</a></li>
		<li><a href="#">现金红包管理</a></li>
	</ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
	<div class="header-title">
		<h1>
			<c:choose>
				<c:when test="${!empty redpacketCash.id}">现金红包编辑</c:when>
				<c:otherwise>现金红包添加</c:otherwise>
			</c:choose>
		</h1>
	</div>
	<!--Header Buttons-->
	<div class="header-buttons">
		<a class="sidebar-toggler" href="#">
			<i class="fa fa-arrows-h"></i>
		</a>
		<a class="refresh" id="refresh-toggler" href="">
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
<div class="page-body">
	<div class="row">
		<div class="col-xs-12 col-md-12">
			<div class="widget">
				<div class="widget-header bordered-bottom bordered-palegreen">
					<span class="widget-caption">表单</span>
				</div>
				<div class="widget-body">
					<div id="registration-form">
						<form action="${ctx}/redpacket/redpacketCash/save" id="editForm"
							  name="editForm" method="post" role="form">
							<input type="hidden" name="id" value='<c:out value="${redpacketCash.id}"/>'>
							<div class="form-title">现金红包</div>
							<div class="row">
								<div class="col-sm-6">
									<label style="color: #999;">触发种类</label>
									<div class="form-group">
										<select class="form-control" name="triggerType" <c:if test="${redpacketCash.id ne null}">disabled</c:if>>
											<option value="0" <c:if test="${redpacketCash.triggerType eq 0}" >selected="selected"</c:if>>
												手动
											</option>
											<option value="1" <c:if test="${redpacketCash.triggerType eq 1}" >selected="selected"</c:if>>
												注册
											</option>
											<option value="2" <c:if test="${redpacketCash.triggerType eq 2}" >selected="selected"</c:if>>
												充值
											</option>
											<option value="3" <c:if test="${redpacketCash.triggerType eq 3}" >selected="selected"</c:if>>
												投资
											</option>
											<option value="4" <c:if test="${redpacketCash.triggerType eq 4}" >selected="selected"</c:if>>
												推荐
											</option>
											<option value="8" <c:if test="${redpacketCash.triggerType eq 8}" >selected="selected"</c:if>>
												分享
											</option>
											<option value="9" <c:if test="${redpacketCash.triggerType eq 9}" >selected="selected"</c:if>>
												7日活动
											</option>
											<option value="11" <c:if test="${redpacketCash.triggerType eq 11}" >selected="selected"</c:if>>
												定存推荐返现
											</option>
											<option value="30" <c:if test="${redpacketCash.triggerType eq 30}" >selected="selected"</c:if>>
												4.0推荐活动
											</option>
										</select>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<span class="form-group input-icon icon-right">
											<label>名称
												<sup style="color: red;">&nbsp;*</sup>：
											</label>
											<input type="text" class="form-control" tabindex="1"
												   style="padding-left: 100px;" name="name" required
												   value="<c:out value='${redpacketCash.name}'/>" maxlength="50"
												   data-bv-stringlength-max="50"
												   data-bv-stringlength-message="不能超过50个字">
										</span>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="form-group">
										<span class="form-group input-icon icon-right">
											<label>金额
												<sup style="color: red;">&nbsp;*</sup>：
											</label>
											<input type="text" class="form-control" tabindex="1"
												   style="padding-left: 100px;" name="fund" required
												   value="<c:out value='${redpacketCash.fund}'/>" maxlength="12"
												   data-bv-stringlength-max="12"
												   data-bv-stringlength-message="不能超过12个字"
												   data-bv-regexp="true" data-bv-regexp-regexp="^\d+(\.\d+)?$"
												   data-bv-regexp-message="金额格式错误">
										</span>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
										<span class="form-group input-icon icon-right">
											<label>使用有效天数
												<sup style="color: red;">&nbsp;*</sup>：
											</label>
											<input type="text" class="form-control" tabindex="1"
												   style="padding-left: 120px;" name="useDays" required
												   value="<c:out value='${redpacketCash.useDays}'/>" maxlength="3"
												   data-bv-stringlength-max="3"
												   data-bv-stringlength-message="不能超过3个字"
												   data-bv-regexp="true" data-bv-regexp-regexp="^\d+$"
												   data-bv-regexp-message="使用有效天数格式错误">
										</span>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="form-group">
										<span class="form-group input-icon icon-right">
											<label>活动开始时间
												<sup style="color: red;">&nbsp;*</sup>：
											</label>
											<input type="text" class="form-control" tabindex="1"
													id="beginTime" required maxlength="40"
													style="padding-left: 130px;" name="aBeginTime"
													value = "<fmt:formatDate value="${redpacketCash.beginDate}" pattern="yyyy-MM-dd" />"
													onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'finishTime\')}',skin:'twoer',dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:function(dp){$('#editForm').data('bootstrapValidator').updateStatus(this.name, 'NOT_VALIDATED').validateField(this.name);}})" />
										</span>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
											<span class="form-group input-icon icon-right">
												<label>活动截止时间
													<sup style="color: red;">&nbsp;*</sup>：
												</label>
												<input type="text" class="form-control" tabindex="1"
													   id="finishTime" required maxlength="40"
													   style="padding-left: 130px;" name="aFinishTime"
													   value = "<fmt:formatDate value="${redpacketCash.finishDate}" pattern="yyyy-MM-dd" />"
													   onfocus="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\')}',skin:'twoer',dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:function(dp){$('#editForm').data('bootstrapValidator').updateStatus(this.name, 'NOT_VALIDATED').validateField(this.name);}})" />
											</span>
									</div>
								</div>
							</div>
							<div style="text-align: right; margin-bottom: 10px;">
								<button type="submit" class="btn btn-labeled btn-success">
									<i class="btn-label glyphicon glyphicon-ok"></i>保存
								</button>
								<a href="${ctx}/redpacket/redpacketCash/list" class="btn btn-labeled btn-blue">
									<i class="btn-label glyphicon glyphicon-arrow-left"></i>返回
								</a>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@include file="/static/inc/footer.inc"%>
</body>
</html>