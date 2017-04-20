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
</style>
</head>
<body>

	<form action="${ctx}/userinfo/user/validataCard" method="post"
		id="editForm" name="editForm">
			 <input type="hidden" name="userId" value='<c:out value="${user.id}"/>' >
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="false">×</button>
			<h4 class="modal-title">身份验证</h4>
		</div>
		<div class="modal-body">
			<div style="width: 100%; height: 12%;">
				<div class="row">
					<div class="col-sm-12">
						<div class="form-group">
							<span class="form-group input-icon icon-right"> <label>真实姓名<sup
									style="color: red;">&nbsp;*</sup>：
							</label> <input type="text" class="form-control" tabindex="1"
								style="padding-left: 100px;" name="realname" required
								maxlength="32" data-bv-stringlength-max="32"
								data-bv-stringlength-message="不能超过32个字">
							</span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-12">
						<div class="form-group">
							<span class="form-group input-icon icon-right"> <label>身份证号<sup
									style="color: red;">&nbsp;*</sup>：
							</label> <input type="text" class="form-control" tabindex="1"
								style="padding-left: 100px;" name="idCard" required
								maxlength="18" data-bv-stringlength-max="18"
								data-bv-stringlength-message="不能超过18个字"
								data-bv-regexp="true"
                                data-bv-regexp-regexp="^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$"
                                data-bv-regexp-message="身份证号格式错误"
								>
							</span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button type="submit" class="btn btn-success"  >验证</button>
			<button type="button" class="btn btn-blue" data-dismiss="modal" aria-hidden="false">取消</button>
		</div>
	</form>

	<%@include file="/static/inc/footer.inc"%>
</body>
</html>