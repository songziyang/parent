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
<script type="text/javascript">
	function changeViews() {
		//天标
		if ($("#type").val() == 1) {
			$("#scoreRatio").css("display", "none");
			$("#days").css("display", "none");
		//白条
		} else if($("#type").val() == 3) {
			$("#days").css("display", "");
			$("#scoreRatio").css("display", "none");
		//定存
		}else{
			$("#days").css("display", "");
			$("#scoreRatio").css("display", "");
		}
	}
	
	$(function(){		
		var type = '<c:out value="${product.type}"/>';
		//天标
		if(type == 1){
			$("#scoreRatio").css("display", "none");
			$("#days").css("display", "none");
		//白条
		}else if(type == 3){
			$("#days").css("display", "");
			$("#scoreRatio").css("display", "none");
		//定存
		}else{
			$("#scoreRatio").css("display", "");
			$("#days").css("display", "");
		}
	});
</script>
</head>
<body>

	<!-- Page Breadcrumb -->
	<div class="page-breadcrumbs">
		<ul class="breadcrumb" style="line-height: 40px;">
			<li><i class="fa fa-home"></i> <a href="#">高端理财</a></li>
			<li><a href="#">配资杠杆</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>
				<c:choose>
					<c:when test="${!empty mslever.id}">配资杠杆信息编辑</c:when>
					<c:otherwise>配资杠杆信息添加</c:otherwise>
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
							<form action="${ctx}/matchstock/lever/saveLever"  id="editForm" name="editForm" method="post" role="form">

								<input type="hidden" name="id"
									value='<c:out value="${mslever.id}"/>'> 
						
								<input
									type="hidden" name="createuser"
									value='<c:out value="${mslever.createuser}"/>'>
								<input
									type="hidden" name="created"
									value='<c:out value="${mslever.created}"/>'>
			
									<input
									type="hidden" name="updateuser"
									value='<c:out value="${mslever.updateuser}"/>'>
									
								<div class="form-title">配资杠杆信息</div>


									<div class="col-sm-12">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>配资杠杆信息<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" name="lever" required
												value="<c:out value="${mslever.lever}"/>" maxlength="2"
												data-bv-stringlength-max="2"
												data-bv-stringlength-message="不能超过2个字" data-bv-regexp="true"
												 data-bv-regexp-regexp="^\d+(\.\d+)?$"
												data-bv-regexp-message="杠杆数值格式错误">
											</span>
										</div>
									</div>
									<!--  							<div class="row">
									<div class="col-sm-12">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>配资股票名称</label>
												<textarea style="padding-left: 100px;" class="form-control"
													rows="1" name="stockName" maxlength="20"
													data-bv-stringlength-max="20"
													data-bv-stringlength-message="不能超过20个字"><c:out value="${msStock.stockName}"/></textarea>
											</span>
										</div>
									</div>
								</div>-->

								<div style="text-align: right; margin-bottom: 10px;">
									<button type="submit" class="btn btn-labeled btn-success">
										<i class="btn-label glyphicon glyphicon-ok"></i>保存
									</button>
									<a href="${ctx}/matchstock/lever/listlever"
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