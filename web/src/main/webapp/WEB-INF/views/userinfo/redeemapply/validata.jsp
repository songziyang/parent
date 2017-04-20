<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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

<style type="text/css">
.table-scrollable {
	width: 100%;
	height: 100%;
	overflow-x: hidden;
	overflow-y: auto;
	border: 0px;
}

.form-group .labelt {
	position: absolute;
	left: 10px;
	top: 7px;
	color: #999;
}

.tickets-container {
	padding: 0px;
}
</style>

<script type="text/javascript">
	function openUrl() {
		window.location.href = "${ctx}/userinfo/redeemApply/listRedeemApply";
	}
</script>

</head>
<body>

	<form action="${ctx}/userinfo/redeemApply/applyAudit" method="post"
		id="editForm" name="editForm">
		<input type="hidden" name="id"
			value='<c:out value="${dayloanRedeemApply.id}"/>'>
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="false" onclick="javascrpt:openUrl();">×</button>
			<h4 class="modal-title">2.0赎回审核</h4>
		</div>
		<div class="modal-body">
			<div style="width: 100%; height: 12%;">
				<div class="row">
					<div class="col-sm-12">
						<div class="form-group">
							<span class="form-group input-icon icon-right"> <label
								class="labelt">申请赎回剩余份数： </label> <input type="text"
								class="form-control" tabindex="1" style="padding-left: 150px;"
								name="applyFund" readonly="readonly"
								value='<c:out value="${dayloanRedeemApply.applyFund - dayloanRedeemApply.fund}" />'>
							</span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-12">
						<div class="form-group">
							<span class="form-group input-icon icon-right"> <label
								class="labelt">批准赎回份数<sup style="color: red;">&nbsp;*</sup>：
							</label> <input type="text" class="form-control" tabindex="1"
								style="padding-left: 120px;" name="fund" required maxlength="11"
								data-bv-stringlength-max="11"
								data-bv-stringlength-message="不能超过11个字" data-bv-regexp="true"
								data-bv-regexp-regexp="^\d+$"
								data-bv-regexp-message="批准赎回份数格式错误" data-bv-lessthan="true"
								data-bv-lessthan-value="applyFund"
								data-bv-lessthan-message="批准赎回份数应小于等于申请赎回份数"
								value='<c:out value="${dayloanRedeemApply.applyFund - dayloanRedeemApply.fund}" />'>
							</span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button type="submit" class="btn btn-success">确定</button>
			<button type="button" class="btn btn-blue" data-dismiss="modal"
				aria-hidden="false" onclick="javascript:openUrl();">取消</button>
		</div>
	</form>

	<%@include file="/static/inc/footer.inc"%>
</body>
</html>