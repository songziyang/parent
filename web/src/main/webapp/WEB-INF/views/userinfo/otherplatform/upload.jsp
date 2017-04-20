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
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/lib/uploadify/uploadify.css">
<script src="${ctx}/static/lib/uploadify/jquery.uploadify.js"
	type="text/javascript"></script>

<script type="text/javascript">
	$(function() {
		$('#file_upload').uploadify({
			'swf' : '${ctx}/static/lib/uploadify/uploadify.swf',
			'buttonText' : '选择文件',
			'fileTypeExts' : '*.xls',
			'fileObjName' : 'file',
			'method': "GET",
			'uploader' : '${ctx}/userinfo/otherplatform/upload',
            successTimeout: 120,
            'onUploadStart'  : function(){
                var obj = {'platformId': $("#platformId").val()};
                $("#file_upload").uploadify("settings", "formData" ,obj );
            },
			'onUploadSuccess' : function(file, data) {
				if (data == "true") {
					alert(file.name + '上传成功！ ');
				} else {
					alert(data);
				}
			}
		});
	});
</script>

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
			<li><i class="fa fa-home"></i> <a href="#">用户管理</a></li>
			<li><a href="#">其他平台用户</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1></h1>
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
							<form action="" method="post" role="form"
								enctype="multipart/form-data">
								<div class="form-title">其他平台用户导入</div>
								<div class="text-center">
									目前只支持.xls文件
								</div>
								<div class="row">
									<div class="col-sm-2"></div>
									<div class="col-sm-2"></div>
									<div class="col-sm-2 text-center">平台选择&nbsp;
										<select name="platformId" id="platformId">
											<c:forEach items="${otherPlatforms}" var="platform">
												<option value="${platform.id}">${platform.name}</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-2 text-center">
										<input id="file_upload" name="file_upload" type="file">
									</div>
									<div class="col-sm-2"></div>
									<div class="col-sm-2"></div>
								</div>
								<br/>
								<div style="text-align: right; margin-bottom: 10px;">
									<a href="${ctx}/userinfo/otherplatform/list"
										class="btn btn-labeled btn-blue" style="width: 100px"> <i
										class="btn-label fa fa-arrow-left"></i>返回
									</a>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
