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

.tickets-container {
	padding: 0px;
}
</style>
<script type="text/javascript">
	function isLogining(islogin, id) {
		bootbox
				.dialog({
					message : islogin == 1 ? "您确定要解锁该用户么？" : "您确定要锁定该用户么？",
					title : islogin == 1 ? "解锁提示" : "锁定提示",
					className : "modal-darkorange",
					buttons : {
						success : {
							label : "确认",
							className : "btn-default",
							callback : function() {
								if (islogin == 1) {
									window.location.href = '${ctx}/userinfo/user/userLogin/'
											+ id;
								}
								if (islogin == 2) {
									window.location.href = '${ctx}/userinfo/user/userUnLogin/'
											+ id;
								}
							}
						},
						"取消" : {
							className : "btn-default",
							callback : function() {
							}
						}
					}
				});
	}
	function setvip(id) {
		bootbox.dialog({
			message : "您确定要设置该用户等级吗么？",
			title : "设置提示",
			className : "modal-darkorange",
			buttons : {
				success : {
					label : "确认",
					className : "btn-default",
					callback : function() {
						window.location.href = '${ctx}/userinfo/user/userVip/'
								+ id + '/' + $("#vip").val();
					}
				},
				"取消" : {
					className : "btn-default",
					callback : function() {
					}
				}
			}
		});
	}
</script>
</head>
<body>
	<div id="myModal" class="modal fade modal-darkorange" tabindex="-1"
		role="dialog" aria-hidden="true" style="display: none;">
		<div class="modal-dialog">
			<div class="modal-content"></div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>

	<!-- /.modal -->
	<!-- Page Breadcrumb -->
	<div class="page-breadcrumbs">
		<ul class="breadcrumb" style="line-height: 40px;">
			<li><i class="fa fa-home"></i> <a href="#">放款管理</a></li>
			<li><a href="#">前台用户</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>前台用户详细信息</h1>
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
				<div class="row">
					<div class="col-lg-12 col-sm-12 col-xs-12">
						<div class="widget">
							<div class="widget-header bordered-bottom bordered-sky">
								<i class="widget-icon fa fa-arrows-v blue"></i> <span
									class="widget-caption">用户基本信息</span>
								<div class="widget-buttons">

									<a href="#"> </a> <a href="#" data-toggle="collapse"> <i
										class="fa fa-plus blue"></i>
									</a>
								</div>
								<!--Widget Buttons-->
							</div>
							<!--Widget Header-->
							<div class="widget-body">


								<div class="tickets-container">
									<ul class="tickets-list">
										<li class="ticket-item">
											<div class="row">
												<div class="ticket-type  col-sm-3  ">
													<span class="divider hidden-xs"></span> <span
														class="user-company">用户名：</span> <span
														class="user-company">
														<c:choose>
															<c:when test="${user.userLeve.gradeNum eq 1}"><img src="${ctx}/static/lib/images/vip1.png" /></c:when>
															<c:when test="${user.userLeve.gradeNum eq 2}"><img src="${ctx}/static/lib/images/vip2.png" /></c:when>
														</c:choose>
													<c:out
															value="${user.username}" /></span>
												</div>
												<div class="ticket-type   col-sm-3 ">
													<span class="divider hidden-xs"></span> <span
														class="user-company">手机号：</span> <span
														class="user-company"><c:out value="${user.mobile}" /></span>
												</div>
												<div class="ticket-type   col-sm-3">
													<span class="divider hidden-xs"></span> <span
														class="user-company">推荐人手机号：</span> <span
														class="user-company"><c:out
															value="${user.referralMobile}" /></span>
												</div>
												<div class="ticket-type  col-sm-3 ">
													<span class="divider hidden-xs"></span> <span
														class="user-company">注册：</span> <span class="user-company"><fmt:formatDate
															value="${user.createDate}" pattern="yyyy年MM月dd日HH时mm分" /></span>
												</div>
											</div>
										</li>

										<li class="ticket-item">
											<div class="row">
												<div class="ticket-time   col-sm-2 ">
													<span class="divider hidden-xs"></span> <span
														class="user-company">真实姓名：</span> <span
														class="user-company"><c:out
															value="${userInfo.realname}" /></span>
												</div>
												<div class="ticket-type  col-sm-3 ">
													<span class="divider hidden-xs"></span> <span
														class="user-company">银行名称：</span> <span
														class="user-company"><c:out
															value="${userInfo.bankName}" /></span>
												</div>
												<div class="ticket-type  col-sm-4 ">
													<span class="divider hidden-xs"></span> <span
														class="user-company">银行卡号：</span> <span
														class="user-company"><c:out
															value="${userInfo.bankCardNumber}" /></span>
												</div>
												<div class="ticket-type  col-sm-3 ">
													<span class="divider hidden-xs"></span> <span
														class="user-company">开户行：</span> <span
														class="user-company"><c:out
															value="${userInfo.openingBank}" /></span>
												</div>
											</div>
										</li>


										<li class="ticket-item">
											<div class="row">
												<div class="ticket-time   col-sm-4 ">
													<span class="divider hidden-xs"></span> <span
														class="user-company">开户行省份：</span> <span
														class="user-company"><c:out
															value="${userInfo.bankProvince}" /></span>
												</div>
												<div class="ticket-type  col-sm-3 ">
													<span class="divider hidden-xs"></span> <span
														class="user-company">开户行城市：</span> <span
														class="user-company"><c:out
															value="${userInfo.bankCity}" /></span>
												</div>
											</div>
										</li>

										<li class="ticket-item">
											<div class="row">
												<div class="ticket-time   col-sm-3 ">
													<span class="divider hidden-xs"></span> <span
														class="user-company">登陆错误次数：</span> <span
														class="user-company"><c:out
															value="${empty user.loginNum ?0 : user.loginNum}" /></span>
												</div>
												<div class="ticket-type  col-sm-3 ">
													<span class="divider hidden-xs"></span> <span
														class="user-company">锁定状态：</span> <span
														class="user-company"><c:out
															value="${user.isLogin eq -1 ? '锁定' :'未锁'}" /></span>
												</div>
												<div class="ticket-type  col-sm-3 ">
													<span class="divider hidden-xs"></span> <span
														class="user-company">锁定时间：</span> <span
														class="user-company"><fmt:formatDate
															value="${empty user.lockTimeDate ? null :user.lockTimeDate}"
															pattern="yyyy年MM月dd日 HH时mm分" /></span>
												</div>


											</div>
										</li>


										<li class="ticket-item">
											<div class="row">
												<div class="ticket-type  col-sm-4 ">
													<span class="divider hidden-xs"></span> <span
														class="user-company"
														style="float: left; padding-right: 10px;">用户等级:</span> <select
														id="vip" name="vip" class="form-control"
														style="width: 120px; float: left">
														<option value="0"
															<c:if test="${userInfo.vip == 0}">selected="selected"</c:if>>普通用户</option>
														<option value="1"
															<c:if test="${userInfo.vip == 1 }">selected="selected"</c:if>>银多老用户</option>
														<option value="2"
															<c:if test="${userInfo.vip == 2 }">selected="selected"</c:if>>股东用户</option>
													</select>
												</div>
												<div class="ticket-type  col-sm-4 ">
													<span class="divider hidden-xs"></span>
												</div>
											</div>
										</li>
									</ul>
								</div>


							</div>
							<!--Widget Body-->
						</div>
						<!--Widget-->
					</div>
				</div>


				<div class="row">
					<div class="col-lg-12 col-sm-12 col-xs-12">
						<div class="widget">
							<div class="widget-header bordered-bottom bordered-sky">
								<i class="widget-icon fa fa-arrows-v blue"></i> <span
									class="widget-caption">用户资金信息</span>
								<div class="widget-buttons">
									<a href="#"> </a> <a href="#" data-toggle="collapse"> <i
										class="fa fa-plus blue"></i>
									</a>
								</div>
								<!--Widget Buttons-->
							</div>
							<!--Widget Header-->
							<div class="widget-body">

								<div class="tickets-container">
									<ul class="tickets-list">
										<li class="ticket-item">
											<div class="row">
												<div class="ticket-time   col-sm-3 ">
													<div class="divider hidden-md hidden-sm hidden-xs"></div>
													<i class="glyphicon glyphicon-th-list"></i> <span
														class="user-company">用户贡献值</span>
												</div>
												<div class="ticket-type  col-sm-3 ">
													<span class="divider hidden-xs"></span> <span
														class="user-company">总贡献值：</span> <span
														class="user-company"><c:out
															value="${userMoney.totalScore}" /></span>
												</div>
												<div class="ticket-type  col-sm-3 ">
													<span class="divider hidden-xs"></span> <span
														class="user-company">可用贡献值：</span> <span
														class="user-company"><c:out
															value="${userMoney.usableScore}" /></span>
												</div>
											</div>
										</li>

										<li class="ticket-item">
											<div class="row">
												<div class="ticket-time   col-sm-3 ">
													<div class="divider hidden-md hidden-sm hidden-xs"></div>
													<i class="glyphicon glyphicon-th-list"></i> <span
														class="user-company">用户资金</span>
												</div>
												<div class="ticket-type  col-sm-3 ">
													<span class="divider hidden-xs"></span> <span
														class="user-company">总资金：</span> <span
														class="user-company"><c:out
															value="${userMoney.totalFund}" /></span>
												</div>
												<div class="ticket-type  col-sm-3 ">
													<span class="divider hidden-xs"></span> <span
														class="user-company">可用资金：</span> <span
														class="user-company"><c:out
															value="${userMoney.usableFund}" /></span>
												</div>
												<div class="ticket-type  col-sm-3 ">
													<span class="divider hidden-xs"></span> <span
														class="user-company">冻结资金：</span> <span
														class="user-company"><c:out
															value="${userMoney.blokedFund}" /></span>
												</div>
											</div>
										</li>

										<li class="ticket-item">
											<div class="row">
												<div class="ticket-time   col-sm-3 ">
													<div class="divider hidden-md hidden-sm hidden-xs"></div>
													<i class="glyphicon glyphicon-th-list"></i> <span
														class="user-company">用户投入</span>
												</div>

												<div class="ticket-type  col-sm-3 ">
													<span class="divider hidden-xs"></span> <span
														class="user-company">2.0投入资金：</span> <span
														class="user-company"><c:out
															value="${userMoney.dayloanCostInput}" /></span>
												</div>
												<div class="ticket-type  col-sm-3 ">
													<span class="divider hidden-xs"></span> <span
														class="user-company">2.0M投入资金：</span> <span
														class="user-company"><c:out
															value="${userMoney.tdCostInput}" /></span>
												</div>
												<div class="ticket-type  col-sm-3 ">
													<span class="divider hidden-xs"></span> <span
														class="user-company">2.0+投入资金：</span> <span
														class="user-company"><c:out
															value="${userMoney.whitenoteTotalFund}" /></span>
												</div>
											</div>
										</li>
									</ul>
								</div>

							</div>
							<!--Widget Body-->

						</div>
						<!--Widget-->
					</div>
				</div>
				<!-- 返回审核信息列表 -->
				<shiro:hasPermission name="withdraw_list">
					<c:if test="${tag== 0 }">
						<a href="${ctx}/userwithdraw/withdraw/listUserWithDraw"
							class="btn btn-labeled btn-blue"
							style="width: 100px; float: right;"> <i
							class="btn-label fa fa-arrow-left"></i>返回
						</a>
					</c:if>
				</shiro:hasPermission>
				<!-- 返回放款信息列表 -->
				<shiro:hasPermission name="withdraws_list">
					<c:if test="${tag==1}">
						<a href="${ctx}/userwithdraw/paback/listWithDrawPayBack"
							class="btn btn-labeled btn-blue"
							style="width: 100px; float: right;"> <i
							class="btn-label fa fa-arrow-left"></i>返回
						</a>
					</c:if>
				</shiro:hasPermission>
			</div>
		</div>
	</div>
	<%@include file="/static/inc/footer.inc"%>
</body>
</html>