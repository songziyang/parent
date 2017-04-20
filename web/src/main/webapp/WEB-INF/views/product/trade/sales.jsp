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
	function revenue() {
		var stockCount = $("#stockCount").val();
		var perValue = $("#perValue").val();
		if (stockCount != '' && perValue != '') {
			$("#allValue").val(parseFloat(stockCount * perValue).toFixed(2));
		}
	}
</script>
</head>
<body>

	<!-- Page Breadcrumb -->
	<div class="page-breadcrumbs">
		<ul class="breadcrumb" style="line-height: 40px;">
			<li><i class="fa fa-home"></i> <a href="#">高端理财</a></li>
			<li><a href="#">股票卖出</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>股票卖出</h1>
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
							<form action="${ctx}/matchstock/trade/salesOption" id="editForm"
								name="editForm" method="post" role="form">
								<input type="hidden" name="fid"
									value='<c:out value="${trade.id}"/>'>
								<div class="form-title">股票卖出</div>


								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>股票代码：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" readonly="readonly"
												value="${trade.stockNumb}">
											</span>
										</div>
									</div>

									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>股票名称：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" value="${trade.stockName}"
												readonly="readonly">
											</span>
										</div>
									</div>

								</div>

								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>股票数量<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												id="stockCount" style="padding-left: 130px;"
												name="stockCount" required maxlength="11"
												data-bv-stringlength-max="11"
												data-bv-stringlength-message="不能超过11个字"
												data-bv-regexp="true" data-bv-regexp-regexp="^\d+$"
												data-bv-regexp-message="股票数量格式错误"
												value="${trade.investCount}" onkeyup="javascript:revenue();"
												onchange="javascript:revenue();">
											</span>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>股票单价<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												id="perValue" style="padding-left: 130px;" name="perValue"
												required maxlength="5" data-bv-stringlength-max="5"
												data-bv-stringlength-message="不能超过5个字" data-bv-regexp="true"
												data-bv-regexp-regexp="^\d+(\.\d+)?$"
												data-bv-regexp-message="股票单价格式错误" min="0"
												data-bv-greaterthan-inclusive="true"
												data-bv-greaterthan-message="股票单价应大于0" max="999.99"
												data-bv-lessthan-inclusive="true"
												data-bv-lessthan-message="股票单价应小于999.99"
												onkeyup="javascript:revenue();"
												onchange="javascript:revenue();">
											</span>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>股票总金额<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 100px;" name="allValue" required
												id="allValue" maxlength="14" data-bv-stringlength-max="14"
												data-bv-stringlength-message="不能超过14个字"
												data-bv-regexp="true" data-bv-regexp-regexp="^\d+(\.\d+)?$"
												data-bv-regexp-message="股票总金额格式错误" min="0"
												data-bv-greaterthan-inclusive="true"
												data-bv-greaterthan-message="股票总金额应大于0"
												max="999999999999.99" data-bv-lessthan-inclusive="true"
												data-bv-lessthan-message="股票总金额应小于999999999999.99">
											</span>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>券商手续费<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 130px;" name="otherMoney" required
												maxlength="8" data-bv-stringlength-max="8"
												data-bv-stringlength-message="不能超过8个字" data-bv-regexp="true"
												data-bv-regexp-regexp="^\d+(\.\d+)?$"
												data-bv-regexp-message="券商手续费" min="0"
												data-bv-greaterthan-inclusive="true"
												data-bv-greaterthan-message="券商手续费应大于0" max="999999.99"
												data-bv-lessthan-inclusive="true"
												data-bv-lessthan-message="券商手续费小于999999.99">
											</span>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>卖出时间<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" name="optionDate" class="form-control"
												tabindex="1" style="padding-left: 120px;" id="optionDate"
												maxlength="40" required value="${optionDate}"
												onfocus="WdatePicker({minDate:'#F{$dp.$D(\'optionDate\')}',skin:'twoer',dateFmt:'yyyy/MM/dd HH:mm:00',readOnly:true,onpicked:function(dp){$('#editForm').data('bootstrapValidator').updateStatus(this.name, 'NOT_VALIDATED').validateField(this.name);}})">
											</span>
										</div>
									</div>
								</div>

								<div style="text-align: right; margin-bottom: 10px;">
									<button type="submit" class="btn btn-labeled btn-success">
										<i class="btn-label glyphicon glyphicon-ok"></i>保存
									</button>
									<a href="${ctx}/matchstock/trade/listTrade"
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