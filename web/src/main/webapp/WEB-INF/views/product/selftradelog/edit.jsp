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
	<script type="text/javascript">
	
		$(function() {
	        var ue = UE.getEditor('container', {
	        	autoHeightEnabled: false,
				elementPathEnabled: false,
				initialFrameHeight: 300
	        });
		});
		function checkUserInfo() {
			var condition = $("#condition").val();
			if (null == condition || "" == condition) {
				alert("请填写用户信息！！！");
			} else {
				var url = "${ctx}/product/selfTradeLog/findSelfTradeUser";
				$.post(url, {"condition": $("#condition").val()}, function(data) {
					var html = "";
					if (data == "" || data.length == 0) {
						html = '<p style="color: red">没有查到该用户，请重新输入用户信息</p>';
					} else {
						$(data).each(function(index){
							var user = data[index];
							html += '<p>' + 
									'用户名: ' + user.username + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + 
									'手机号: ' + user.mobile + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + 
									'<button type="button" class="btn btn-primary btn-success" onclick="selectUser(\'' + user.username + '\',\'' + user.mobile + '\',\'' + user.id + '\');">选择</button> ' + 
									'</p>';
						})
					}
					$("#ct1").html(html);
				});
			}
		}
	
		function selectUser(username, mobile, id) {
			$("#username").val(username);
			$("#mobile").val(mobile);
			$("#userId").val(id);
			$("#myModal").modal("hide");
		}
	</script>
	<body>
		<!-- Page Breadcrumb -->
		<div class="page-breadcrumbs">
			<ul class="breadcrumb" style="line-height: 40px;">
				<li><i class="fa fa-home"></i> <a href="#">产品管理</a></li>
				<li><a href="#">私人订制记录</a></li>
			</ul>
		</div>
		<!-- /Page Breadcrumb -->
		<!-- Page Header -->
		<div class="page-header position-relative">
			<div class="header-title">
				<h1>
					<c:choose>
						<c:when test="${!empty netLoan.id}">私人订制记录编辑</c:when>
						<c:otherwise>私人订制记录添加</c:otherwise>
					</c:choose>
				</h1>
			</div>
			<!--Header Buttons-->
			<div class="header-buttons">
				<a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i></a>
				<a class="refresh" id="refresh-toggler" href=""> 
					<i class="glyphicon glyphicon-refresh"></i>
				</a> 
				<a class="fullscreen" id="fullscreen-toggler" href="#"> 
					<i class="glyphicon glyphicon-fullscreen"></i>
				</a>
			</div>
		</div>
		
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h4 class="modal-title" id="myModalLabel">请查找用户信息</h4>
					</div>
					<div class="modal-body">
						<span class="form-group input-icon icon-right">
							<label>用户名或手机号：
								<sup style="color: red;">&nbsp;*</sup>
							</label> 
							<input type="text" name="condition" id="condition"
									class="form-control" tabindex="1"
									style="padding-left: 30px; text-align: center" required>
							<div style="margin-top: 20px;" id="ct1"></div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary"
							onclick="javaScript:checkUserInfo();">搜索</button>
					</div>
				</div>
			</div>
		</div>
		
		<div class="page-body">
			<div class="row">
				<div class="col-xs-12 col-md-12">
					<div class="widget">
						<div class="widget-header bordered-bottom bordered-palegreen">
							<span class="widget-caption">表单</span>
						</div>
						<div class="widget-body">
							<div id="registration-form">
								<form action="${ctx}/product/selfTradeLog/save" id="editForm"
										name="editForm" method="post" role="form">
									<input type="hidden" name="id" value='<c:out value="${tradeLog.id}"/>'> 
										<input type="hidden" id="userId" name="user.id" value='<c:out value="${tradeLog.user.id}"/>' />
									
									<div class="form-title">私人订制记录</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<span class="form-group input-icon icon-right">
													<label>用户名
														<sup style="color: red;">&nbsp;*</sup>：
													</label> 
													<input type="text" class="form-control" tabindex="1"
															style="padding-left: 100px;" id="username" name="username"
															value="${tradeLog.user.username}" maxlength="20"
															data-bv-stringlength-max="20"
															readonly="readonly" required
															data-bv-stringlength-message="不能超过20个字" data-toggle="modal"
															data-target="#myModal" />
												</span>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<span class="form-group input-icon icon-right">
													<label>手机号
														<sup style="color: red;">&nbsp;*</sup>：
													</label> 
													<input type="text" class="form-control" tabindex="1"
															style="padding-left: 100px;" id="mobile" name="mobile"
															value="${tradeLog.user.mobile}" maxlength="20"
															data-bv-stringlength-max="20"
															readonly="readonly" required
															data-bv-stringlength-message="不能超过20个字" data-toggle="modal"
															data-target="#myModal" />
												</span>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<span class="form-group input-icon icon-right"> 
													<label>产品名称
														<sup style="color: red;">&nbsp;*</sup>：
													</label> 
													<input type="text" name="buyName" class="form-control" 
															style="padding-left: 130px;" required tabindex="1"
															maxlength="50" data-bv-stringlength-max="50"
															value="<c:out value='${tradeLog.buyName}'/>"
															data-bv-stringlength-message="不能超过50个字" />
												</span>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="form-group">
												<span class="form-group input-icon icon-right"> 
													<label>冻结金额
														<sup style="color: red;">&nbsp;*</sup>：
													</label> 
													<input type="text" name="freezeMoney" class="form-control" 
															style="padding-left: 130px;" required tabindex="1"
															maxlength="10" data-bv-stringlength-max="10"
															value="<c:out value="${tradeLog.freezeMoney}"/>"
															data-bv-stringlength-message="不能超过10个字" data-bv-regexp="true"
															data-bv-regexp-regexp="^\d+(\.\d+)?$"
															data-bv-regexp-message="冻结金额格式错误" />
												</span>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<span class="form-group input-icon icon-right"> <label>交易日期<sup
														style="color: red;">&nbsp;*</sup>：
												</label> <input type="text" class="form-control" tabindex="1"
													id="dTradeDate" 
													style="padding-left: 130px;" name="dTradeDate" required
													maxlength="40"
													<c:choose>
														<c:when test="${curDate eq null}">
															value = "<fmt:formatDate value="${tradeLog.tradeDate}" pattern="yyyy-MM-dd" />"
														</c:when>
														<c:otherwise>
															value = "${curDate}"
														</c:otherwise>
													</c:choose>
													value = "<fmt:formatDate value="${tradeLog.tradeDate}" pattern="yyyy-MM-dd" />"
													onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:function(dp){$('#editForm').data('bootstrapValidator').updateStatus(this.name, 'NOT_VALIDATED').validateField(this.name);}})" />
												</span>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<label style="color: #999;">资产类型 </label>
											<div class="form-group">
												<select class="form-control" id="type" name="proType" onchange="javascript:changeViews();">
													<option value="1" <c:if test="${tradeLog.proType eq 1  }" >selected="selected"</c:if>>债权投资</option>
													<option value="2" <c:if test="${tradeLog.proType eq 2  }" >selected="selected"</c:if>>股票投资</option>
													<option value="3" <c:if test="${tradeLog.proType eq 3  }" >selected="selected"</c:if>>股权投资</option>
													<option value="4" <c:if test="${tradeLog.proType eq 4  }" >selected="selected"</c:if>>基金投资</option>
													<option value="5" <c:if test="${tradeLog.proType eq 5  }" >selected="selected"</c:if>>消费类型</option>
												</select>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-12">
											<div class="form-group">
												<span class="form-group input-icon icon-right"> <label>描述：</label>
												<script id="container" name="description" type="text/plain">
       												${tradeLog.description}
    											</script>
												
<!-- 													<textarea style="padding-left: 120px;" class="form-control" -->
<!-- 														rows="4" name="description" maxlength="500" -->
<!-- 														data-bv-stringlength-max="500" -->
<%-- 														data-bv-stringlength-message="不能超过500个字"><c:out value="${tradeLog.description}"/></textarea> --%>
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
