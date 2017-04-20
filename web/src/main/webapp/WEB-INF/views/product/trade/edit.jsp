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
			<li><i class="fa fa-home"></i> <a href="#">高端理财</a></li>
			<li><a href="#">配资信息</a></li>
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
							<form action="${ctx}/matchstock/trade/saveTrade" method="post"
								role="form" id="editForm" name="editForm">
								<input type="hidden" name="id"
									value='<c:out value="${trade.id}"/>'>
								<div class="form-title">配资信息</div>
								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>用户名：
											</label> <input type="text" class="form-control" tabindex="1"
												readonly="readonly"
												value="<c:out value="${trade.user.username}"/>"
												style="padding-left: 120px;">
											</span>
										</div>
									</div>

									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>手机号：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" readonly="readonly"
												value="<c:out value="${trade.user.mobile}"/>">
											</span>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>保证金：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;"
												value='<c:out value="${trade.margin}"/>' readonly="readonly">
											</span>
										</div>
									</div>

									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>杠杆：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" value="${trade.lever}"
												readonly="readonly">
											</span>
										</div>
									</div>

								</div>


								<div class="row">

									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>利息率：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" readonly="readonly"
												value="${trade.interestrate}">
											</span>
										</div>
									</div>

									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>配资费用：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" value="${trade.interest}"
												readonly="readonly">
											</span>
										</div>
									</div>

								</div>

								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>配资金额：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" readonly="readonly"
												value="${trade.matchMoney}">
											</span>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>状态：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" readonly="readonly"
												value='<c:if test="${trade.state eq 1 }">交易中</c:if> <c:if
															test="${trade.state eq 2 }">交易成功</c:if> <c:if
															test="${trade.state eq 3 }">交易完成</c:if> <c:if
															test="${trade.state eq 4 }">交易失败</c:if>'>
											</span>
										</div>
									</div>

								</div>




								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>平仓金额：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" readonly="readonly"
												value='${trade.closeMoney}'>
											</span>
										</div>
									</div>

									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>预警金额：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" value='${trade.warningMoney}'
												readonly="readonly">
											</span>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>配资类型：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" readonly="readonly"
												value='<c:if test="${trade.mtype eq 1 }">随机配资</c:if> <c:if
															test="${trade.mtype eq 2 }">按天配资</c:if> <c:if
															test="${trade.mtype eq 3 }">按月配资</c:if>'>
											</span>
										</div>
									</div>

									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>配资周期：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" value='${trade.cycle}'
												readonly="readonly">
											</span>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>股票代码<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" name="stockNumb" required
												maxlength="6" value="${trade.stockNumb}">
											</span>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>股票名称<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" name="stockName" required
												maxlength="6" value="${trade.stockName}">
											</span>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>买入时间<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" name="buyStartDate"
												value="${trade.buyStartDate}" required id = "buyStartDate"
												onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy/MM/dd HH:mm:00',readOnly:true,onpicked:function(dp){$('#editForm').data('bootstrapValidator').updateStatus(this.name, 'NOT_VALIDATED').validateField(this.name);}})">
											</span>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>卖出时间<sup
													style="color: red;">&nbsp;*</sup>：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" value="${trade.buyEndDate}"
												name="buyEndDate" required
												onfocus="WdatePicker({minDate:'#F{$dp.$D(\'buyStartDate\')}',skin:'twoer',dateFmt:'yyyy/MM/dd HH:mm:00',readOnly:true,onpicked:function(dp){$('#editForm').data('bootstrapValidator').updateStatus(this.name, 'NOT_VALIDATED').validateField(this.name);}})">
											</span>
										</div>
									</div>


								</div>

								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>实际买入时间：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" readonly="readonly"
												value='${buy.optionDate}'>
											</span>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>实际卖出时间：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" readonly="readonly"
												value='${sales.optionDate}'>
											</span>
										</div>
									</div>
								</div>


								<div class="row">

									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>实际投资股份数：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" value="${trade.investCount}"
												readonly="readonly">
											</span>
										</div>
									</div>

									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>卖出股票总金额：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" readonly="readonly"
												value='${sales.allValue}'>
											</span>
										</div>
									</div>



								</div>



								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>买入券商手续费：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" readonly="readonly"
												value='${buy.otherMoney}'>
											</span>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>卖出券商手续费：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" readonly="readonly"
												value='${sales.otherMoney}'>
											</span>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>实际投资金额：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" value="${trade.investMoney}"
												readonly="readonly">
											</span>
										</div>
									</div>

									<div class="col-sm-6">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>收益：
											</label> <input type="text" class="form-control" tabindex="1"
												style="padding-left: 120px;" readonly="readonly"
												value='${trade.profit}'>
											</span>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-sm-12">
										<div class="form-group">
											<span class="form-group input-icon icon-right"> <label>备注信息：</label>
												<textarea style="padding-left: 120px;" class="form-control"
													rows="4"><c:out value="${trade.remark}" /></textarea>
											</span>
										</div>
									</div>
								</div>
								<div style="text-align: right; margin-bottom: 10px;">
									<c:if test="${trade.state eq 1 }">
										<c:if test="${trade.mtype eq 2 || trade.mtype eq 3}">
											<button type="submit" class="btn btn-labeled btn-success">
												<i class="btn-label glyphicon glyphicon-ok"></i>保存
											</button>
										</c:if>
									</c:if>
									<a href="${ctx}/matchstock/trade/listTrade"
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