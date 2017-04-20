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
			<li><i class="fa fa-home"></i> <a href="#">费用管理</a></li>
			<li><a href="#">未到期撤资扣款</a></li>
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
							<form action="${ctx}/feededuction/fee/savefeededuction"
								id="editForm" name="editForm" method="post" role="form">

								<input type="hidden" name="id"
									value='<c:out value="${deduction.id}"/>'>
									
								<input type="hidden" name="isDelete"
									value='<c:out value="${deduction.isDelete}"/>'>

								<div class="form-title">撤资信息</div>
								<div class="row">
									<div class="col-sm-6">
										<label style="color: white;">： </label>
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>名称<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												name="name" style="padding-left: 80px"
												value="${deduction.name}" required maxlength="20">
											</span>
										</div>
									</div>

									<div class="col-sm-6">
										<label style="color: #999;">产品<sup style="color: red;">&nbsp;*</sup>：
										</label>
										<div class="form-group">
											<select name="product.id" class="form-control">
												<c:forEach items="${products}" var="product"
													varStatus="status">
													<option value="${product.id }" style="margin-left: 200px;"
														<c:choose>
														<c:when test="${product.id == deduction.product.id }">
															 selected = "selected"
														</c:when>
														</c:choose>><c:out
															value="${product.name}"></c:out></option>
												</c:forEach>
											</select>
										</div>
									</div>
								</div>
								<div class="row">

									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>开始天数<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 100px;" name="beginDays" required
												value="<c:out value="${deduction.beginDays}"/>"
												maxlength="3" 
												data-bv-stringlength-max="3"
												data-bv-stringlength-message="不能超过3个字"
												data-bv-regexp="true"
												data-bv-regexp-regexp="^\d+$"
												data-bv-regexp-message="开始天数格式错误"
													data-bv-greaterthan="true"
												data-bv-greaterthan-message="开始天数大于0" 
												data-bv-greaterthan-value="0"
												data-bv-lessthan="true"
												data-bv-lessthan-value="999"
												data-bv-lessthan-message="开始天数999"
												>
											</span>

										</div>
									</div>


									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>扣除比例(%)<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 100px;" name="scale" required
												value="<c:out value="${deduction.scale}"/>" maxlength="5"
												data-bv-stringlength-max="5"
												data-bv-stringlength-message="不能超过5个字" data-bv-regexp="true"
												data-bv-regexp-regexp="^\d+(\.\d+)?$"
												data-bv-regexp-message="扣除比例格式错误" 
												min="0"
												data-bv-greaterthan="true"
												data-bv-greaterthan-message="扣除比例应大于0" 
												max="100"
												data-bv-lessthan="true"
												data-bv-lessthan-message="扣除比例小于100">
											</span>

										</div>
									</div>
								</div>


								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>结束天数<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 100px;" name="endDays" required
												value="${deduction.endDays}"
												maxlength="3"
												data-bv-stringlength-max="3" id="endDays"
												data-bv-stringlength-message="不能超过3个字" 
												data-bv-regexp="true"
												data-bv-regexp-regexp="^\d+$"
												data-bv-regexp-message="结束天数格式错误"
												data-bv-greaterthan="true"
												data-bv-greaterthan-message="结束天数应大于开始天数" 
												data-bv-greaterthan-value="beginDays"
												data-bv-lessthan="true"
												data-bv-lessthan-value="999"
												data-bv-lessthan-message="结束天数小于999"
												>
											</span>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>扣费描述：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 100px;" name="description"
												maxlength="40" value="${deduction.description}"
												data-bv-stringlength-max="40"
												data-bv-stringlength-message="不能超过40个字"
												>
											</span>
										</div>
									</div>
								</div>

								<div style="text-align: right; margin-bottom: 10px;">
									<button type="submit" class="btn btn-labeled btn-success">
										<i class="btn-label glyphicon glyphicon-ok"></i>保存
									</button>
									<a href="${ctx}/feededuction/fee/listFee"
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
