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
	<script>
		function changeViews() {
			var isSend = $("#isSend").val();
			change(isSend);
		}

		$(function(){
			var isSend = '<c:out value="${expMoney.isSend}"/>';
			change(isSend);
		});

		function change(isSend) {
			if (isSend == "" || isSend == 1) {
				$("#content").hide();
			} else {
				$("#content").show();
			}
		}
	</script>
</head>
<body>

<!-- Page Breadcrumb -->
<div class="page-breadcrumbs">
	<ul class="breadcrumb" style="line-height: 40px;">
		<li><i class="fa fa-home"></i> <a href="#">红包管理</a></li>
		<li><a href="#">体验金管理</a></li>
	</ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
	<div class="header-title">
		<h1>
			<c:choose>
				<c:when test="${!empty expMoney.id}">体验金编辑</c:when>
				<c:otherwise>体验金添加</c:otherwise>
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
						<form action="${ctx}/redpacket/expmoney/save" id="editForm"
							  name="editForm" method="post" role="form">
							<input type="hidden" name="id" value='<c:out value="${expMoney.id}"/>'>
							<div class="form-title">体验金</div>
							<div class="row">
								<div class="col-sm-6">
									<div class="form-group">
											<span class="form-group input-icon icon-right">
												<label>体验金份数
													<sup style="color: red;">&nbsp;*</sup>：
												</label>
												<input type="text" class="form-control" tabindex="1"
														name="copies"
														value="<c:out value="${expMoney.copies}"/>"
														style="padding-left: 100px;" required maxlength="9"
														data-bv-stringlength-max="9"
														data-bv-stringlength-message="不能超过9个字">
											</span>
									</div>
								</div>
								<div class="col-sm-6">
									<div class="form-group">
											<span class="form-group input-icon icon-right">
												<label>天数
													<sup style="color: red;">&nbsp;*</sup>：
											</label>
											<input type="text" class="form-control" tabindex="1"
													value="<c:out value="${expMoney.days}"/>"
													style="padding-left: 120px;" name="days" required
													maxlength="3" data-bv-stringlength-max="3"
													data-bv-stringlength-message="不能超过3个字" data-bv-regexp="true"
													data-bv-regexp-regexp="^\d+$"
													data-bv-regexp-message="天数格式错误">
											</span>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-6">
									<label style="color: #999;">是否发送短信
										<sup style="color: red;">&nbsp;*</sup>：
									</label>
									<div class="form-group">
										<span class="form-group input-icon icon-right">
											<label>
												<sup style="color: red;">&nbsp;</sup>
											</label>
											<select id="isSend" name="isSend" class="form-control" required onchange="javascript:changeViews();">
												<option value="">--请选择--</option>
												<option value="0" <c:if test="${expMoney.isSend eq 0 }">selected="selected"</c:if>>
													是
												</option>
												<option value="1" <c:if test="${expMoney.isSend eq 1 }">selected="selected"</c:if>>
													否
												</option>
											</select>
										</span>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12" id="content" style="display:none;">
									<div class="form-group">
										<span class="form-group input-icon icon-right">
											<label>短信内容：<sup style="color: red;">&nbsp;*</sup>：</label>
											<textarea style="padding-left: 120px;" class="form-control"
													rows="4" name="content" maxlength="500" name="content"
													data-bv-stringlength-max="500" required
													data-bv-stringlength-message="不能超过500个字"><c:out value="${expMoney.content}"/></textarea>
										</span>
									</div>
								</div>
							</div>
							<div style="text-align: right; margin-bottom: 10px;">
								<button type="submit" class="btn btn-labeled btn-success">
									<i class="btn-label glyphicon glyphicon-ok"></i>保存
								</button>
								<a href="${ctx}/redpacket/expmoney/list" class="btn btn-labeled btn-blue">
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