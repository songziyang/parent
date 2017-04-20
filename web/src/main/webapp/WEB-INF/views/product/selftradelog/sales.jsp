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
	if ($("#state").val() == 4) {
		$("#endDate").css("display", "none");
	} else if($("#state").val() == 5) {
		$("#endDate").css("display", "");
	}
}

</script>
</head>
<body>

	<!-- Page Breadcrumb -->
	<div class="page-breadcrumbs">
		<ul class="breadcrumb" style="line-height: 40px;">
			<li><i class="fa fa-home"></i> <a href="#">私人订制</a></li>
			<li><a href="#">私人订制</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>私人订制</h1>
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
							<form action="${ctx}/product/selfTradeLog/sales" id="editForm"
								name="editForm" method="post" role="form">

								<input type="hidden" name="fid"
									value='<c:out value="${tradeLog.id}"/>'>

								<div class="form-title">私人订制</div>


								<div class="row">
									<div class="col-sm-6">
										<label style="color: #999;">还款状态</label>
										<div class="form-group">
											<select class="form-control" name="state" id="state" onchange="javascript:changeViews();" >
												<option value="4"
													<c:if test="${tradeLog.state eq 4  }" >selected="selected"</c:if>>还款中</option>
												<option value="5"
													<c:if test="${tradeLog.state eq 5  }" >selected="selected"</c:if>>还款结束</option>
											</select>
										</div>
									</div>
								</div>


								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>收益<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" name="revenue" required
												maxlength="10" data-bv-stringlength-max="10"
												data-bv-stringlength-message="不能超过10个字"
												data-bv-regexp="true" data-bv-regexp-regexp="^-?\d+(\.\d+)?$"
												data-bv-regexp-message="格式错误">
											</span>
										</div>
									</div>

									<div class="col-sm-6" id="endDate" style="display: none;" >
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>结束日期<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" name="endDate" class="form-control"
												tabindex="1" style="padding-left: 120px;" maxlength="40"
												required
												value="<fmt:formatDate value="${optionDate}"  pattern="yyyy-MM-dd " />"
												onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:function(dp){$('#editForm').data('bootstrapValidator').updateStatus(this.name, 'NOT_VALIDATED').validateField(this.name);}})">
											</span>
										</div>
									</div>

								</div>


								<div class="row">
									<div class="col-sm-12">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>收益说明</label>
												<textarea style="padding-left: 80px;" class="form-control"
													rows="4" name="remark" maxlength="200"
													data-bv-stringlength-max="200"
													data-bv-stringlength-message="不能超过200个字"></textarea>
											</span>
										</div>
									</div>
								</div>


								<div style="text-align: right; margin-bottom: 10px;">
									<button type="submit" class="btn btn-labeled btn-success">
										<i class="btn-label glyphicon glyphicon-ok"></i>保存
									</button>
									<a href="${ctx}/product/selfTradeLog/list"
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