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
			<li><a href="#">红包维护</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>
				<c:choose>
					<c:when test="${!empty redpaper.id}">红包编辑</c:when>
					<c:otherwise>红包添加</c:otherwise>
				</c:choose>
			</h1>
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
	<div class="page-body">
		<div class="row">
			<div class="col-xs-12 col-md-12">
				<div class="widget">
					<div class="widget-header bordered-bottom bordered-palegreen">
						<span class="widget-caption">表单</span>
					</div>
					<div class="widget-body">
						<div id="registration-form">
							<form
								action="${ctx}/redpapermanager/redpaper/redPaperSaveorUpdate"
								method="post" role="form" id="editForm" name="editForm">

								<input type="hidden" name="id"
									value='<c:out value="${redpaper.id}"/>'> <input
									type="hidden" name="createDate"
									value='<c:out value="${redpaper.createDate}"/>'> <input
									type="hidden" name="createUserName"
									value='<c:out value="${redpaper.createUserName}"/>'> <input
									type="hidden" name="status"
									value='<c:out value="${redpaper.status}"/>'>


								<div class="form-title">红包信息</div>
								<div class="row">
									<div class="col-sm-6">
										<label style="color: #999;">&nbsp;&nbsp;&nbsp;<sup
											style="color: red;"></sup>
										</label>
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>红包名称<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												name="redPaperName"
												value="<c:out value="${redpaper.redPaperName}"/>"
												style="padding-left: 100px;" required maxlength="12"
												data-bv-stringlength-max="12"
												data-bv-stringlength-message="不能超过12个字">
											</span>
										</div>
									</div>

									<div class="col-sm-6">
										<label style="color: #999;">红包类型<sup
											style="color: red;">&nbsp;*</sup>：
										</label>
										<div class="form-group">

											<span class="form-group input-icon icon-right"> <label><sup
													style="color: red;">&nbsp;</sup>： </label> <select
												name="redPaperType" class="form-control" required>
													<option value="">--请选择--</option>
													<option value="1"
														<c:if test="${redpaper.redPaperType == 1 }">selected="selected"</c:if>>现金红包</option>
													<option value="4"
														<c:if test="${redpaper.redPaperType == 4 }">selected="selected"</c:if>>利率红包</option>
													<option value="5"
														<c:if test="${redpaper.redPaperType == 5 }">selected="selected"</c:if>>日加息券</option>
											</select>
											</span>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>领取有效天数<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												value="<c:out value="${redpaper.receiveDays}"/>"
												style="padding-left: 120px;" name="receiveDays" required
												maxlength="3" data-bv-stringlength-max="3"
												data-bv-stringlength-message="不能超过3个字" data-bv-regexp="true"
												data-bv-regexp-regexp="^\d+$"
												data-bv-regexp-message="领取有效天数格式错误">
											</span>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>使用有效天数<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" name="useDays" required
												value="<c:out value="${redpaper.useDays}"/>" maxlength="3"
												data-bv-stringlength-max="3"
												data-bv-stringlength-message="不能超过3个字" data-bv-regexp="true"
												data-bv-regexp-regexp="^\d+$"
												data-bv-regexp-message="使用有效天数格式错误">
											</span>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-sm-6">
										<label style="color: #999;">&nbsp;<sup
											style="color: red;"></sup>
										</label>
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>赠送值<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 100px;" name="giveValue" required
												value="<c:out value="${redpaper.giveValue}"/>" maxlength="6"
												data-bv-stringlength-max="6"
												data-bv-stringlength-message="不能超过6个字" data-bv-regexp="true"
												data-bv-regexp-regexp="^\d+(\.\d+)?$"
												data-bv-regexp-message="赠送值格式错误">
											</span>
										</div>
									</div>
									<div class="col-sm-6">
										<label style="color: #999;">触发事件<sup
											style="color: red;">&nbsp;*</sup>：
										</label>
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label><sup
													style="color: red;">&nbsp;</sup>： </label> <select
												name="detonateEvent" class="form-control" required>
													<option value="0"
														<c:if test="${redpaper.detonateEvent == 0 }">selected="selected"</c:if>>手动</option>
													<option value="1"
														<c:if test="${redpaper.detonateEvent == 1 }">selected="selected"</c:if>>注册</option>
													<option value="2"
														<c:if test="${redpaper.detonateEvent == 2 }">selected="selected"</c:if>>充值</option>
													<option value="3"
														<c:if test="${redpaper.detonateEvent == 3 }">selected="selected"</c:if>>投资</option>
													<option value="4"
														<c:if test="${redpaper.detonateEvent == 4 }">selected="selected"</c:if>>普通推荐</option>
													<option value="5"
														<c:if test="${redpaper.detonateEvent == 5 }">selected="selected"</c:if>>VIP推荐</option>
													<option value="6"
														<c:if test="${redpaper.detonateEvent == 6 }">selected="selected"</c:if>>升级VIP</option>
													<option value="7"
														<c:if test="${redpaper.detonateEvent == 7 }">selected="selected"</c:if>>被推荐人</option>
											</select>
											</span>
										</div>
									</div>
								</div>



								<div class="row">
									<div class="col-sm-6">
										<label style="color: #999;">&nbsp;<sup
											style="color: red;"></sup>
										</label>
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>活动开始日期<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" name="acstartTime" class="form-control"
												tabindex="1" style="padding-left: 120px;" required
												maxlength="40" id="acstartTime"
												value="<fmt:formatDate value="${redpaper.fbDate}"
														pattern="yyyy-MM-dd" />"
												onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'acendTime\')}',skin:'twoer',dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:function(dp){$('#editForm').data('bootstrapValidator').updateStatus(this.name, 'NOT_VALIDATED').validateField(this.name);}})">
											</span>
										</div>
									</div>
									<div class="col-sm-6">
										<label style="color: #999;">&nbsp;<sup
											style="color: red;"></sup>
										</label>

										<div class="form-group">

											<span class="form-group input-icon icon-right"> <label>活动结束日期<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" name="acendTime" class="form-control"
												tabindex="1" style="padding-left: 120px;" id="acendTime"
												maxlength="40" required
												value="<fmt:formatDate value="${redpaper.endDate}"
														pattern="yyyy-MM-dd" />"
												onfocus="WdatePicker({minDate:'#F{$dp.$D(\'acstartTime\')}',skin:'twoer',dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:function(dp){$('#editForm').data('bootstrapValidator').updateStatus(this.name, 'NOT_VALIDATED').validateField(this.name);}})">
											</span>
										</div>
									</div>
								</div>




								<div class="row">
									<div class="col-sm-6">
										<label style="color: #999;">&nbsp;<sup
											style="color: red;"></sup>
										</label>
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>投资有效天数<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" name="investDays" required
												maxlength="3" data-bv-stringlength-max="3"
												data-bv-stringlength-message="不能超过3个字" data-bv-regexp="true"
												data-bv-regexp-regexp="^\d+$"
												data-bv-regexp-message="投资有效天数格式错误"
												value="${redpaper.investDays}">
											</span>
										</div>
									</div>
								</div>

								<div style="text-align: right; margin-bottom: 10px;">
									<button type="submit" class="btn btn-labeled btn-success">
										<i class="btn-label glyphicon glyphicon-ok"></i>保存
									</button>
									<a href="${ctx}/redpapermanager/redpaper/listRedpaper"
										class="btn btn-labeled btn-blue"> <i
										class="btn-label glyphicon glyphicon-arrow-left"></i>返回
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
