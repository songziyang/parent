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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1,minimum-scale=1, maximum-scale=1,user-scalable=no">
<style>
.form-group label {
	position: absolute;
	left: 10px;
	top: 7px;
	color: #999;
}
</style>
<script type="text/javascript">
	function getProductSet() {
		/* 		$("#productId").find("option").each(function(index,element){
		 if($(element).prop('selected')){
		 if($(element).attr('ptype') == 1){
		 $("#dlAutoTotalId").css("display", "");
		 }else{
		 $("#dlAutoTotalId").css("display", "none");
		 }
		 return true;
		 }
		 }); */

		//获取产品类型为银多2.0+（type:3）
		var v = $("#productId").val();
		var productType = $("#productId option[value='" + v + "']").attr(
				'ptype');
		if (productType == 3) {
			$("#circulation").css("display", "none");
			$("#activityApr").css("display", "none");
		} else {
			$("#circulation").css("display", "");
			$("#activityApr").css("display", "");
		}

		$.post("${ctx}/product/productset/getProductById", {
			id : $("#productId").val()
		}, function(data) {
			if (data != '') {
				var dataObj = eval("(" + data + ")");
				$("input[name=apr]").val(dataObj.apr);
			} else {
				$("input[name=apr]").val('');
			}
		});
	}

	$(function() {

		/* 	$("#productId").find("option").each(function(index,element){
				if($(element).prop('selected')){
					if($(element).attr('ptype') == 1){
						$("#dlAutoTotalId").css("display", "");
					}else{
						$("#dlAutoTotalId").css("display", "none");
					}
					return true;
				}
			}); */

		$.post("${ctx}/product/productset/getProductById", {
			id : $("#productId").val()
		}, function(data) {
			if (data != '') {
				var dataObj = eval("(" + data + ")");
				$("input[name=apr]").val(dataObj.apr);
			} else {
				$("input[name=apr]").val('');
			}
		});

	});
</script>
</head>
<body>

	<!-- Page Breadcrumb -->
	<div class="page-breadcrumbs">
		<ul class="breadcrumb" style="line-height: 40px;">
			<li><i class="fa fa-home"></i> <a href="#">产品管理</a></li>
			<li><a href="#">产品每日设置信息</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>
				<c:choose>
					<c:when test="${!empty productset.id}">产品每日设置编辑</c:when>
					<c:otherwise>产品每日设置添加</c:otherwise>
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
		<!-- 开始 昨日数据统计部分 -->
		<div class="row">
			<!-- 
			<div class="col-lg-4 col-sm-6 col-xs-12">
				<div
					class="databox radius-bordered databox-shadowed databox-graded databox-vertical">
					<div class="databox-top bg-blue">
						<div class="databox-icon">
							<i class="fa fa-clock-o"></i>
						</div>
					</div>
					<div class="databox-bottom text-align-center">
						<span class="databox-text">昨日定存总数</span> 
						<span class="databox-text">${yesterdayDpTotal}</span>
					</div>
				</div>
			</div>
			 -->
			<div class="col-lg-4 col-sm-6 col-xs-6">
				<div
					class="databox radius-bordered databox-shadowed databox-graded databox-vertical">
					<div class="databox-top bg-orange">
						<div class="databox-icon">
							<i class="fa fa-clock-o"></i>
						</div>
					</div>
					<div class="databox-bottom text-align-center">
						<span class="databox-text">定存复投预购总数</span> <span
							class="databox-text">${dpQueue}</span>
					</div>
				</div>
			</div>
			<div class="col-lg-4 col-sm-6 col-xs-6">
				<div
					class="databox radius-bordered databox-shadowed databox-graded databox-vertical">
					<div class="databox-top bg-green">
						<div class="databox-icon">
							<i class="fa fa-clock-o"></i>
						</div>
					</div>
					<div class="databox-bottom text-align-center">
						<span class="databox-text">天标预购总数</span> <span
							class="databox-text"><fmt:formatNumber value="${dlQueue}" type="number" /></span>
					</div>
				</div>
			</div>
		</div>
		<!-- 结束 昨日数据统计部分 -->
		<div class="row">
			<div class="col-xs-12 col-md-12">
				<div class="widget">
					<div class="widget-header bordered-bottom bordered-palegreen">
						<span class="widget-caption">表单</span>
					</div>
					<div class="widget-body">
						<div id="registration-form">
							<form action="${ctx}/product/productset/saveProductSet"
								id="editForm" name="editForm" method="post" role="form">
								<input type="hidden" name="productset.yesterdayDpTotal"
									value="<c:out value="${productset.yesterdayDpTotal}"/>" />
								<div class="form-title">产品每日设置</div>

								<div class="row">
									<div class="col-sm-6">
										<label style="color: #999;">产品名称<sup
											style="color: red;">&nbsp;*</sup>：
										</label>
										<div class="form-group">
											<select class="form-control" id="productId" name="product.id"
												onchange="getProductSet()">
												<c:forEach items="${productLst }" var="product">
													<option ptype='<c:out value="${product.type }" />'
														value='<c:out value="${product.id }" />'
														<c:if test="${productset.product.id==product.id}">selected="selected"</c:if>><c:out
															value="${product.name }" />
													</option>
												</c:forEach>
											</select>
										</div>
									</div>


								</div>

								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>发布利率(%)<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" name="apr" required
												value="<c:out value="${productset.apr}"/>" maxlength="5"
												data-bv-stringlength-max="5"
												data-bv-stringlength-message="不能超过5个字" data-bv-regexp="true"
												data-bv-regexp-regexp="^\d+(\.\d+)?$"
												data-bv-regexp-message="发布利率格式错误" min="0"
												data-bv-greaterthan-inclusive="false"
												data-bv-greaterthan-message="发布利率应大于0" max="100"
												data-bv-lessthan-inclusive="true"
												data-bv-lessthan-message="发布利率应小于100">
											</span>
										</div>
									</div>
									<div class="col-sm-6" id="activityApr">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>活动利率(%)<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" name="activityApr"
												value="<c:out value="${empty productset.activityApr ? 0.0 : productset.activityApr}"/>"
												maxlength="5" data-bv-stringlength-max="5" required
												data-bv-stringlength-message="不能超过5个字" data-bv-regexp="true"
												data-bv-regexp-regexp="^\d+(\.\d+)?$"
												data-bv-regexp-message="活动利率格式错误" min="0"
												data-bv-greaterthan-inclusive="true"
												data-bv-greaterthan-message="活动利率应大于0" max="100"
												data-bv-lessthan-inclusive="true"
												data-bv-lessthan-message="活动利率应小于100">
											</span>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-sm-6" id="circulation">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>发布份数<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												name="circulation"
												value="<c:out value="${productset.circulation}"/>"
												style="padding-left: 110px;" required maxlength="10"
												data-bv-stringlength-max="10"
												data-bv-stringlength-message="不能超过10个字"
												data-bv-regexp="true" data-bv-regexp-regexp="^\d+$"
												data-bv-regexp-message="发布份数格式错误">
											</span>
										</div>
									</div>

									<!--  
										<div class="col-sm-6" id="dlAutoTotalId" >
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>贡献值份数<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												name="dlAutoTotal"
												value="<c:out value="${productset.dlAutoTotal}"/>"
												style="padding-left: 120px;" 
												required
												maxlength="10"
												data-bv-stringlength-max="10"
												data-bv-stringlength-message="不能超过10个字"
												data-bv-regexp="true" data-bv-regexp-regexp="^\d+$"
												data-bv-regexp-message="贡献值份数格式错误">
											</span>
										</div>
									</div>
									-->
								</div>



								<div style="text-align: right; margin-bottom: 10px;">
									<button type="submit" class="btn btn-labeled btn-success">
										<i class="btn-label glyphicon glyphicon-ok"></i>发布
									</button>
									<a href="${ctx}/product/productset/listProductSet"
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