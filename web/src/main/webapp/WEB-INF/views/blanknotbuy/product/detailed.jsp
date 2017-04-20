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
	function checkUserName() {
		var condition = $("#condition").val();

		if (null == condition || "" == condition) {
			
		} else {
			var url = "${ctx}/blanknotbuy/productlist/finduserbycondition/"
					+ condition
			$.post(url, function(data, status) {
				$("#ct1").html('');
				var obj = eval(data);
				$(obj).each(
						function(index) {
							var val = obj[index];
							$("#ct1").html(
									'<a href=javaScript:setUserNameAndUID('
											+ "'" + val.realname + "','"
											+ val.user.id + "'"
											+ ');  style="color:green;"' + '>'
											+ val.realname + '</a>');
						});
			});
		}
	}

	function setUserNameAndUID(name, id) {
		$("#realname").attr("value", "");
		$("#realname").attr("value", name);//填充内容
		$("#userid").attr("value", "");
		$("#userid").attr("value", id);//填充内容
		$("#myModal").modal("hide");
	}

	function checkMoney() {
		var userid = $("#userid").val();
		var buyMoney = $("#buyMoney").val();
		var url = "${ctx}/blanknotbuy/productlist/checkUserBlankNoteMoney/"
				+ buyMoney + "/" + userid
		$.post(url, function(data, status) {
			if (data == "error") {
				Notify("对不起用户余额不足，不能进行购买", 'top-left', '5000', 'warning',
						'fa-warning', true);
			} else {
				document.forms[0].submit();
			}
		});

	}
</script>



</head>
<body>

	<!-- Page Breadcrumb -->
	<div class="page-breadcrumbs">
		<ul class="breadcrumb" style="line-height: 40px;">
			<li><i class="fa fa-home"></i> <a href="#">2.0+管理</a></li>
			<li><a href="#">产品购买</a></li>
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


	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="myModalLabel">请查找用户名称</h4>
				</div>
				<div class="modal-body">
					<span class="form-group input-icon icon-right"> <label>关键信息：<sup
							style="color: red;">&nbsp;*</sup>
					</label> <input type="text" name="condition" id="condition"
						class="form-control" tabindex="1"
						style="padding-left: 100px; text-align: center" required>
						<div style="margin-top: 20px;" id="ct1"></div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						onclick="javaScript:checkUserName();">搜索</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
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
							<form action="${ctx}/blanknotbuy/productlist/buyproduct"
								id="editForm" name="editForm" method="post" role="form">

								<input type="hidden" name="id"
									value='<c:out value="${blanknote.id}"/>'> <input
									type="hidden" name="user.id" id="userid"
									value="<c:out value="${blanknote.user.id}"/>">

								<div class="form-title">购买信息</div>
								<div class="row">

									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>用户名<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 100px;" name="name" id="realname"
												required value="${blanknote.user.username}" maxlength="20"
												data-bv-stringlength-max="20"
												readonly="readonly"
												data-bv-stringlength-message="不能超过20个字">
											</span>
										</div>
									</div>

									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>购买产品名称<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 100px;" name="goodsname" required
												value="${blanknote.goodsname }" maxlength="20"
												data-bv-stringlength-max="15"
												readonly="readonly"
												data-bv-stringlength-message="不能超过15个字">
											</span>
										</div>
									</div>

								</div>
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>购买金额<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 100px;" name="buyMoney" id="buyMoney"
												required value="<c:out value="${blanknote.buyMoney}"/>"
												maxlength="6" data-bv-stringlength-max="10"
												data-bv-stringlength-message="不能超过10个字"
												readonly="readonly"
												data-bv-regexp="true" data-bv-regexp-regexp="^\d+(\.\d+)?$"
												data-bv-regexp-message="赠送值格式错误">
											</span>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>购买日期<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" name="buyDate" class="form-control"
												tabindex="1" style="padding-left: 120px;" required
												maxlength="40" id="buyDate"
												readonly="readonly"
												value="<fmt:formatDate value="${blanknote.createTime}"
														pattern="yyyy-MM-dd" />"
												onfocus="WdatePicker({skin:'twoer'})">
											</span> </span>
										</div>
									</div>
								</div>

								<div class="form-group">
									<span class="form-group input-icon icon-right"> <label>描述<sup
											style="color: red;">&nbsp;*</sup>：
									</label> <input type="text" class="form-control" tabindex="1"
										style="padding-left: 100px;" name="description"
										id="description" maxlength="40" data-bv-stringlength-max="200"
										value="${blanknote.description}"
										readonly="readonly"
										data-bv-stringlength-message="不能超过200个字">
									</span>
								</div>



								<div style="text-align: right; margin-bottom: 10px;">
									
									<a href="${ctx}/blanknotbuy/productlist/listProductBuy"
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
	<%@include file="/static/inc/footer.inc"%>
</body>
</html>
<!-- ${ctx}/userwithdraw/withdraw/submitWithDraw/${userWithDraw.id}/1 
 <a
										href="javascript:fail();"
										class="btn btn-danger"> <i
										class="btn-label glyphicon glyphicon-remove"></i>审核失败
									</a>

-->
