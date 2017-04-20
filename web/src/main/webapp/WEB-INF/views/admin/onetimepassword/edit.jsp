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
</style>
<script>
	function checkUserInfo() {
		var condition = $("#condition").val();
		if (null == condition || "" == condition) {
			alert("请填写用户信息！！！");
		} else {
			var url = "${ctx}/admin/onetimepassword/findAdmin";
			$.post(url, {"condition": $("#condition").val()}, function(user) {
				var html = "";
				if (user == "" || user.length == 0) {
					html = '<p style="color: red">没有查到该用户，请重新输入用户信息</p>';
				} else {
					html += '<p>' +
							'用户名: ' + user.username + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' +
							'<button type="button" class="btn btn-primary btn-success" onclick="selectUser(\'' + user.username + '\',\'' + user.id + '\');">选择</button> ' +
							'</p>';
				}
				$("#ct1").html(html);
			});
		}
	}

	function selectUser(username,id) {
		$("#username").val(username);
		$("#adminId").val(id);
		$("#myModal").modal("hide");
	}
</script>
</head>
<body>

	<!-- Page Breadcrumb -->
	<div class="page-breadcrumbs">
		<ul class="breadcrumb" style="line-height: 40px;">
			<li><i class="fa fa-home"></i> <a href="#">系统管理</a></li>
			<li><a href="#">数字令牌信息管理</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>
				<c:choose>
					<c:when test="${!empty onetimePassword.id}">数字令牌信息编辑</c:when>
					<c:otherwise>数字令牌信息添加</c:otherwise>
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
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		 aria-labelledby="myModalLabe" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
					<h4 class="modal-title" id="myModalLabe">请查找后台用户信息</h4>
				</div>
				<div class="modal-body">
						<span class="form-group input-icon icon-right">
							<label>用户名：
								<sup style="color: red;">&nbsp;*</sup>
							</label>
							<input type="text" name="condition" id="condition"
								   class="form-control" tabindex="1"
								   style="padding-left: 30px; text-align: center" required>
							<div style="margin-top: 20px;" id="ct1"></div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
							onclick="javaScript:checkUserInfo();">搜索</button>
				</div>
			</div>
		</div>
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
							<form action="${ctx}/admin/onetimepassword/save" id="editForm"
								name="editForm" method="post" role="form">

								<input type="hidden" name="admin.id" id="adminId" />
								<input type="hidden" name="id" value="${onetimePassword.id}" />
								<div class="form-title">数字令牌信息</div>
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>令牌号码<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												name="otpNumber" value="<c:out value="${onetimePassword.otpNumber}"/>"
												style="padding-left: 100px;" required maxlength="13"
												data-bv-stringlength-max="13"
												data-bv-stringlength-message="不能超过13个字"
												data-bv-regexp="true"
												data-bv-regexp-regexp="^\d{13}$"
												data-bv-regexp-message="令牌号码利率格式错误">
											</span>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>密钥
												<sup style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 100px;" name="authkey" required
												value="<c:out value="${onetimePassword.authkey}"/>"
												maxlength="50" data-bv-stringlength-max="50"
												data-bv-stringlength-message="不能超过50个字"
												data-bv-regexp="true"
												data-bv-regexp-regexp="^([0-9]|[a-z]|[A-Z]){50}$"
												data-bv-regexp-message="密钥格式错误">
											</span>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right">
												<label>绑定用户
												</label>
												<input type="text" class="form-control" tabindex="1"
													   style="padding-left: 100px;" id="username" name="username"
													   value="${onetimePassword.admin.username}" maxlength="20"
													   data-bv-stringlength-max="20"
													   readonly="readonly"
													   data-bv-stringlength-message="不能超过20个字" data-toggle="modal"
													   data-target="#myModal" />
											</span>
										</div>
									</div>
								<div style="text-align: right; margin-bottom: 10px;">
									<button type="submit" class="btn btn-labeled btn-success">
										<i class="btn-label glyphicon glyphicon-ok"></i>保存
									</button>
									<a href="${ctx}/admin/onetimepassword/list"
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