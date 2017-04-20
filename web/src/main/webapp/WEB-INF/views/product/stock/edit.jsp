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
			<li><i class="fa fa-home"></i> <a href="#">产品管理</a></li>
			<li><a href="#">产品信息</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>
				<c:choose>
					<c:when test="${!empty product.id}">产品信息编辑</c:when>
					<c:otherwise>产品信息添加</c:otherwise>
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
							<form action="${ctx}/product/product/saveProduct" id="editForm"
								name="editForm" method="post" role="form">

								<input type="hidden" name="id"
									value='<c:out value="${product.id}"/>'> 
								<input
									type="hidden" name="created"
									value='<c:out value="${product.created}"/>'> 
								<input
									type="hidden" name="createUser"
									value='<c:out value="${product.createUser}"/>'>
								<input
									type="hidden" name="unit"
									value='<c:out value="${product.unit}"/>'>
								<input
									type="hidden" name=status
									value='<c:out value="${product.status}"/>'>
									
								<div class="form-title">产品信息</div>

								<div class="row">
									<div class="col-sm-6">
										<label style="color: #999;">产品类型 </label>
										<div class="form-group">
											<select class="form-control" id="type" name="type"
												onchange="javascript:changeViews();">
											<option value="2"
												<c:if test="${product.type eq 2  }" >selected="selected"</c:if>>银多2.0M</option>
											<option value="3"
												<c:if test="${product.type eq 3  }" >selected="selected"</c:if>>银多2.0+</option>
											<option value="1"
												<c:if test="${product.type eq 1  }" >selected="selected"</c:if>>银多2.0</option>
											</select>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>产品名称<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												name="name" value="<c:out value="${product.name}"/>"
												style="padding-left: 100px;" required maxlength="50"
												data-bv-stringlength-max="50"
												data-bv-stringlength-message="不能超过50个字">
											</span>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>年化利率(%)<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" name="apr" required
												value="<c:out value="${product.apr}"/>" maxlength="5"
												data-bv-stringlength-max="5"
												data-bv-stringlength-message="不能超过5个字" data-bv-regexp="true"
												data-bv-regexp-regexp="^\d+(\.\d+)?$"
												data-bv-regexp-message="年化利率格式错误" min="0"
												data-bv-greaterthan-inclusive="true"
												data-bv-greaterthan-message="年化利率应大于0" max="100"
												data-bv-lessthan-inclusive="true"
												data-bv-lessthan-message="年化利率应小于100">
											</span>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-sm-6" id="days" <c:if test="${product.type eq 1  }" >style="display:none;"</c:if> >
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>产品天数<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 100px;" name="days" required
												maxlength="3" data-bv-stringlength-max="3"
												value="<c:out value="${product.days}"/>"
												data-bv-stringlength-message="不能超过3个字"
												data-bv-regexp="true" data-bv-regexp-regexp="^\d+$"
												data-bv-regexp-message="产品天数格式错误">
											</span>
										</div>
									</div>
									<div class="col-sm-6" id="scoreRatio"
										<c:if test="${product.type eq 1  }" >style="display:none;"</c:if>>
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>贡献值比例(%)<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 130px;" name="scoreRatio" required
												maxlength="2" data-bv-stringlength-max="2"
												value="<c:out value="${product.scoreRatio}"/>"
												data-bv-stringlength-message="不能超过2个字" data-bv-regexp="true"
												data-bv-regexp-regexp="^\d+$"
												data-bv-regexp-message="贡献值比例格式错误">
											</span>
										</div>
									</div>
								</div>

								<div style="text-align: right; margin-bottom: 10px;">
									<button type="submit" class="btn btn-labeled btn-success">
										<i class="btn-label glyphicon glyphicon-ok"></i>保存
									</button>
									<a href="${ctx}/product/product/listProduct"
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