<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@include file="/static/inc/main.inc" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>银多资本</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <style>
        .form-group label {
            position: absolute;
            left: 10px;
            top: 7px;
            color: #999;
        }
    </style>
    <script type="text/javascript">
        function success(id) {
            var free = $("#deduction").val();
            if (free == "" || free == null) {
                bootbox.dialog({
                    // dialog的内容
                    message: "提交失败，请选择扣费类型！",
                    // dialog的标题
                    title: "审核提示",
                    // dialog底端按钮配置
                    buttons: {
                        // 另一个按钮配置
                        "确定": {
                            className: "btn-danger",
                            callback: function () {
                            }
                        }
                    }
                });
            } else {

                var advanceMoney = $("#advanceMoney").val();
                var url = "${ctx}/userwithdraw/withdraw/submitWithDraw";
                bootbox.dialog({
                    // dialog的内容
                    message: "您确认批准该申请？",
                    // dialog的标题
                    title: "审核提示",
                    // dialog底端按钮配置
                    buttons: {
                        // 另一个按钮配置
                        "确定": {
                            className: "btn-danger",
                            callback: function () {
                                document.forms[0].action = url;
                                document.forms[0].submit();
                            }
                        },
                        "取消": {
                            className: "btn btn-success",
                            callback: function () {

                            }
                        }
                    }
                });
            }

        }

        function fail() {
            var value = $("#description").val();
            if (value == "" || value == null) {
                bootbox.dialog({
                    // dialog的内容
                    message: "提交失败，请填写问题描述！",
                    // dialog的标题
                    title: "审核提示",
                    // dialog底端按钮配置
                    buttons: {
                        // 另一个按钮配置
                        "确定": {
                            className: "btn-danger",
                            callback: function () {
                            }
                        }
                    }
                });
            } else {
                document.forms[0].submit();
            }
        }

        function deductionFree() {
            var free = $("#deduction").val();
            var fund = $("#withdrawMoney").val();
            $("#advanceMoney").val('');
            if (free == 1) {
                var v = parseFloat(fund - 2).toFixed(2) > 0 ? parseFloat(fund - 2).toFixed(2) : 0.00;
                $("#advanceMoney").val(v);
            } else if (free == 2) {
                var v = parseFloat(fund - 2 - (fund * 3.5) / 1000).toFixed(2) > 0 ? parseFloat(fund - 2 - (fund * 3.5) / 1000).toFixed(2) : 0.00;
                $("#advanceMoney").val(v);
            } else if (free == 3) {
                $("#advanceMoney").val(parseFloat(fund).toFixed(2));
            }
        }
    </script>


</head>
<body>

<!-- Page Breadcrumb -->
<div class="page-breadcrumbs">
    <ul class="breadcrumb" style="line-height: 40px;">
        <li><i class="fa fa-home"></i> <a href="#">放款管理</a></li>
        <li><a href="#">放款审核</a></li>
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
        <div class="col-lg-12 col-sm-12 col-xs-12">
            <div class="widget collapsed">
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
                                            class="user-company">充值提现</span>
                                    </div>
                                    <div class="ticket-type  col-sm-3 ">
                                        <span class="divider hidden-xs"></span> <span
                                            class="user-company">累计充值：</span> <span
                                            class="user-company"><c:out
                                            value="${empty user.userInfo.allRecharge ? 0 : user.userInfo.allRecharge}"/></span>
                                    </div>
                                    <div class="ticket-type  col-sm-3 ">
                                        <span class="divider hidden-xs"></span> <span
                                            class="user-company">累计提现：</span> <span
                                            class="user-company"><c:out
                                            value="${empty user.userInfo.alliWthdraw ? 0 : user.userInfo.alliWthdraw }"/></span>
                                    </div>
                                    <div class="ticket-type  col-sm-3 ">
                                        <span class="divider hidden-xs"></span> <span
                                            class="user-company">累计提现扣费：</span> <span
                                            class="user-company"><c:out
                                            value="${empty user.userInfo.mangeFee ? 0 : user.userInfo.mangeFee }"/></span>
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
                                            class="user-company">
                                                <c:out value="${empty user.userMoney.totalFund ? 0 : user.userMoney.totalFund }"/></span>
                                    </div>
                                    <div class="ticket-type  col-sm-3 ">
                                        <span class="divider hidden-xs"></span> <span
                                            class="user-company">可用资金：</span> <span
                                            class="user-company">
                                                <c:out value="${empty user.userMoney.usableFund ? 0 : user.userMoney.usableFund}"/></span>
                                    </div>
                                    <div class="ticket-type  col-sm-3 ">
                                        <span class="divider hidden-xs"></span> <span
                                            class="user-company">冻结资金：</span> <span
                                            class="user-company">
                                                <c:out value="${empty user.userMoney.freezeFund ? 0 : user.userMoney.freezeFund}"/></span>
                                    </div>
                                </div>
                            </li>


                            <li class="ticket-item">
                                <div class="row">
                                    <div class="ticket-time   col-sm-3 ">
                                        <div class="divider hidden-md hidden-sm hidden-xs"></div>
                                        <i class="glyphicon glyphicon-th-list"></i> <span
                                            class="user-company">用户体验金</span>
                                    </div>
                                    <div class="ticket-type  col-sm-3 ">
                                        <span class="divider hidden-xs"></span> <span
                                            class="user-company">总体验金：</span> <span
                                            class="user-company">
                                                <c:out value="${empty user.userExMoney.amount ? 0 : user.userExMoney.amount}"/></span>
                                    </div>
                                    <div class="ticket-type  col-sm-3 ">
                                        <span class="divider hidden-xs"></span> <span
                                            class="user-company">可用体验金：</span> <span
                                            class="user-company">
                                                <c:out value="${empty user.userExMoney.ableMoney ? 0 : user.userExMoney.ableMoney}"/></span>
                                    </div>
                                    <div class="ticket-type  col-sm-3 ">
                                        <span class="divider hidden-xs"></span> <span
                                            class="user-company">冻结体验金：</span> <span
                                            class="user-company">
                                                <c:out value="${empty user.userExMoney.blockedMoney ? 0 : user.userExMoney.blockedMoney}"/></span>
                                    </div>
                                </div>
                            </li>

                            <li class="ticket-item">
                                <div class="row">
                                    <div class="ticket-time   col-sm-3 ">
                                        <div class="divider hidden-md hidden-sm hidden-xs"></div>
                                        <i class="glyphicon glyphicon-th-list"></i> <span
                                            class="user-company">用户投资</span>
                                    </div>
                                    <div class="ticket-type  col-sm-3 ">
                                        <span class="divider hidden-xs"></span> <span
                                            class="user-company">总投资金额：</span> <span
                                            class="user-company">
                                                <c:out value="${empty user.userInvestinfo.allInvest ? 0 : user.userInvestinfo.allInvest }"/></span>
                                    </div>
                                    <div class="ticket-type  col-sm-3 ">
                                        <span class="divider hidden-xs"></span> <span
                                            class="user-company">活期宝：</span> <span
                                            class="user-company">
                                                <c:out value="${empty user.userInvestinfo.allInvestDayloan ? 0 : user.userInvestinfo.allInvestDayloan }"/></span>
                                    </div>
                                    <div class="ticket-type  col-sm-3 ">
                                        <span class="divider hidden-xs"></span> <span
                                            class="user-company">定存宝：</span> <span
                                            class="user-company">
                                                <c:out value="${empty user.userInvestinfo.allInvestDeposit ? 0 : user.userInvestinfo.allInvestDeposit }"/></span>
                                    </div>
                                </div>
                            </li>

                            <li class="ticket-item">
                                <div class="row">
                                    <div class="ticket-time   col-sm-3 ">
                                        <div class="divider hidden-md hidden-sm hidden-xs"></div>
                                        <i class="glyphicon glyphicon-th-list"></i> <span
                                            class="user-company">用户投资</span>
                                    </div>
                                    <div class="ticket-type  col-sm-3 ">
                                        <span class="divider hidden-xs"></span> <span
                                            class="user-company">债权转让：</span> <span
                                            class="user-company">
                                                <c:out value="${empty user.userInvestinfo.allInvestTransfer ? 0 : user.userInvestinfo.allInvestTransfer }"/></span>
                                    </div>
                                    <div class="ticket-type  col-sm-3 ">
                                        <span class="divider hidden-xs"></span> <span
                                            class="user-company">稳进宝：</span> <span
                                            class="user-company">
                                                <c:out value="${empty user.userInvestinfo.allInvestWjb ? 0 : user.userInvestinfo.allInvestWjb }"/></span>
                                    </div>
                                    <div class="ticket-type  col-sm-3 ">
                                        <span class="divider hidden-xs"></span> <span
                                            class="user-company">私人投资：</span> <span
                                            class="user-company">
                                                <c:out value="${empty user.userInvestinfo.allInvestSelf ? 0 : user.userInvestinfo.allInvestSelf }"/></span>
                                    </div>
                                </div>
                            </li>

                            <li class="ticket-item">
                                <div class="row">
                                    <div class="ticket-time   col-sm-3 ">
                                        <div class="divider hidden-md hidden-sm hidden-xs"></div>
                                        <i class="glyphicon glyphicon-th-list"></i> <span
                                            class="user-company">用户投资</span>
                                    </div>
                                    <div class="ticket-type  col-sm-3 ">
                                        <span class="divider hidden-xs"></span> <span
                                            class="user-company">随心存：</span> <span
                                            class="user-company">
                                                <c:out value="${empty user.userInvestinfo.allInvestFree ? 0 : user.userInvestinfo.allInvestFree }"/></span>
                                    </div>
                                </div>
                            </li>


                            <li class="ticket-item">
                                <div class="row">
                                    <div class="ticket-time   col-sm-3 ">
                                        <div class="divider hidden-md hidden-sm hidden-xs"></div>
                                        <i class="glyphicon glyphicon-th-list"></i> <span
                                            class="user-company">用户收益</span>
                                    </div>
                                    <div class="ticket-type  col-sm-3 ">
                                        <span class="divider hidden-xs"></span> <span
                                            class="user-company">总收益：</span> <span
                                            class="user-company">
                                                <c:out value="${empty user.userIncome.allIncome  ? 0 : user.userIncome.allIncome  }"/></span>
                                    </div>
                                    <div class="ticket-type  col-sm-3 ">
                                        <span class="divider hidden-xs"></span> <span
                                            class="user-company">活期收益：</span> <span
                                            class="user-company">
                                                <c:out value="${empty user.userIncome.allIncomeDayloan  ? 0 : user.userIncome.allIncomeDayloan  }"/></span>
                                    </div>
                                    <div class="ticket-type  col-sm-3 ">
                                        <span class="divider hidden-xs"></span> <span
                                            class="user-company">定存收益：</span> <span
                                            class="user-company">
                                                <c:out value="${empty user.userIncome.allIncomeDeposit ? 0 : user.userIncome.allIncomeDeposit }"/></span>
                                    </div>
                                </div>
                            </li>

                            <li class="ticket-item">
                                <div class="row">
                                    <div class="ticket-time   col-sm-3 ">
                                        <div class="divider hidden-md hidden-sm hidden-xs"></div>
                                        <i class="glyphicon glyphicon-th-list"></i> <span
                                            class="user-company">用户收益</span>
                                    </div>
                                    <div class="ticket-type  col-sm-3 ">
                                        <span class="divider hidden-xs"></span> <span
                                            class="user-company">稳进宝收益：</span> <span
                                            class="user-company">
                                                <c:out value="${empty user.userIncome.allIncomeWjb  ? 0 : user.userIncome.allIncomeWjb  }"/></span>
                                    </div>
                                    <div class="ticket-type  col-sm-3 ">
                                        <span class="divider hidden-xs"></span> <span
                                            class="user-company">活动收益：</span> <span
                                            class="user-company">
                                                <c:out value="${empty user.userIncome.allIncomeActivity ? 0 : user.userIncome.allIncomeActivity  }"/></span>
                                    </div>
                                    <div class="ticket-type  col-sm-3 ">
                                        <span class="divider hidden-xs"></span> <span
                                            class="user-company">私人定制收益：</span> <span
                                            class="user-company">
                                                <c:out value="${empty user.userIncome.allIncomeSelf ? 0 : user.userIncome.allIncomeSelf }"/></span>
                                    </div>
                                </div>
                            </li>

                            <li class="ticket-item">
                                <div class="row">
                                    <div class="ticket-time   col-sm-3 ">
                                        <div class="divider hidden-md hidden-sm hidden-xs"></div>
                                        <i class="glyphicon glyphicon-th-list"></i> <span
                                            class="user-company">用户额外收益</span>
                                    </div>
                                    <div class="ticket-type  col-sm-3 ">
                                        <span class="divider hidden-xs"></span> <span
                                            class="user-company">加息收益：</span> <span
                                            class="user-company">
                                                <c:out value="${empty user.userIncome.allIncomeInterest ? 0 : user.userIncome.allIncomeInterest }"/></span>
                                    </div>
                                    <div class="ticket-type  col-sm-3 ">
                                        <span class="divider hidden-xs"></span> <span
                                            class="user-company">体验金收益：</span> <span
                                            class="user-company">
                                                <c:out value="${empty user.userIncome.allIncomeInvest ? 0 : user.userIncome.allIncomeInvest }"/></span>
                                    </div>
                                    <div class="ticket-type  col-sm-3 ">
                                        <span class="divider hidden-xs"></span> <span
                                            class="user-company">VIP收益：</span> <span
                                            class="user-company">
                                                <c:out value="${empty user.userIncome.allIncomeVip ? 0 : user.userIncome.allIncomeVip }"/></span>
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
        <div class="col-xs-12 col-md-12">
            <div class="widget">
                <div class="widget-header bordered-bottom bordered-sky">
                    <span class="widget-caption">表单</span>
                </div>
                <div class="widget-body">
                    <div id="registration-form">
                        <form action="${ctx}/userwithdraw/withdraw/failWithDraw"
                              method="post" role="form">

                            <input type="hidden" name="id" value='<c:out value="${userWithDraw.id}"/>'>
                            <input type="hidden" name="condition" value='<c:out value="${condition}"/>'>
                            <input type="hidden" name="pageCurrent" value='<c:out value="${pageCurrent}"/>'>

                            <div class="form-title">放款审核</div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>申请时间：
                                            </label>
                                                <input type="text" class="form-control" tabindex="1" readonly="readonly"
                                                       value="<fmt:formatDate value="${userWithDraw.sqDate}"
														pattern="yyyy-MM-dd HH:mm" />"
                                                       style="padding-left: 100px;">
											</span>
                                    </div>
                                </div>

                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>真实姓名：
                                            </label> <input type="text" class="form-control" tabindex="1"
                                                            style="padding-left: 100px;" readonly="readonly"
                                                            value="<c:out value="${userWithDraw.realname}"/>">
											</span>
                                    </div>
                                </div>
                            </div>

                            <div class="row">

                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>累计充值<sup
                                                    style="color: red;">&nbsp;*</sup>：
                                            </label><input type="text" class="form-control" tabindex="1"
                                                           style="padding-left: 100px;" required readonly="readonly"
                                                           value="${user.userInfo.allRecharge}" maxlength="40"
                                                           data-bv-stringlength-max="40"
                                                           data-bv-stringlength-message="不能超过40个字">
											</span>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>累计提现<sup
                                                    style="color: red;">&nbsp;*</sup>：
                                            </label> <input type="text" class="form-control" tabindex="1"
                                                            style="padding-left: 100px;" required readonly="readonly"
                                                            value="${user.userInfo.alliWthdraw}" maxlength="40"
                                                            data-bv-stringlength-max="40"
                                                            data-bv-stringlength-message="不能超过40个字">
											</span>
                                    </div>
                                </div>

                            </div>

                            <div class="row">

                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>总资金<sup
                                                    style="color: red;">&nbsp;*</sup>：
                                            </label><input type="text" class="form-control" tabindex="1"
                                                           style="padding-left: 100px;" required readonly="readonly"
                                                           value="<c:out value="${empty user.userMoney.totalFund ? 0 : user.userMoney.totalFund }"/>" maxlength="40"
                                                           data-bv-stringlength-max="40"
                                                           data-bv-stringlength-message="不能超过40个字">
											</span>
                                    </div>
                                </div>

                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label style="color: red;" >提现参考金额<sup
                                                    style="color: red;">&nbsp;*</sup>：
                                            </label><input type="text" class="form-control" tabindex="1"
                                                           style="padding-left: 120px;" required readonly="readonly"
                                                           value="<fmt:formatNumber type="number" pattern="0.00" maxFractionDigits="2" value="${(user.userInfo.allRecharge + user.userIncome.allIncome) - (user.userInfo.alliWthdraw) - (user.userInvestinfo.allInvest - user.userInvestinfo.allInvestInvest + user.userMoney.usableFund )}"/>" maxlength="40"
                                                           data-bv-stringlength-max="40"
                                                           data-bv-stringlength-message="不能超过40个字">
											</span>
                                    </div>
                                </div>

                            </div>

                            <div class="row">

                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>可用资金<sup
                                                    style="color: red;">&nbsp;*</sup>：
                                            </label><input type="text" class="form-control" tabindex="1"
                                                           style="padding-left: 100px;" required readonly="readonly"
                                                           value="${user.userMoney.usableFund}" maxlength="40"
                                                           data-bv-stringlength-max="40"
                                                           data-bv-stringlength-message="不能超过40个字">
											</span>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>总收益<sup
                                                    style="color: red;">&nbsp;*</sup>：
                                            </label> <input type="text" class="form-control" tabindex="1"
                                                            style="padding-left: 100px;" required readonly="readonly"
                                                            value="${user.userIncome.allIncome}" maxlength="40"
                                                            data-bv-stringlength-max="40"
                                                            data-bv-stringlength-message="不能超过40个字">
											</span>
                                    </div>
                                </div>

                            </div>
                            <%--<div class="row">--%>

                                <%--<div class="col-sm-6">--%>
                                    <%--<div class="form-group">--%>
											<%--<span class="form-group input-icon icon-right"> <label>活期宝<sup--%>
                                                    <%--style="color: red;">&nbsp;*</sup>：--%>
                                            <%--</label><input type="text" class="form-control" tabindex="1"--%>
                                                           <%--style="padding-left: 100px;" required readonly="readonly"--%>
                                                           <%--value="${user.userInvestinfo.allInvestDayloan}" maxlength="40"--%>
                                                           <%--data-bv-stringlength-max="40"--%>
                                                           <%--data-bv-stringlength-message="不能超过40个字">--%>
											<%--</span>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                                <%--<div class="col-sm-6">--%>
                                    <%--<div class="form-group">--%>
											<%--<span class="form-group input-icon icon-right"> <label>定存宝<sup--%>
                                                    <%--style="color: red;">&nbsp;*</sup>：--%>
                                            <%--</label> <input type="text" class="form-control" tabindex="1"--%>
                                                            <%--style="padding-left: 100px;" required readonly="readonly"--%>
                                                            <%--value="${user.userInvestinfo.allInvestDeposit}" maxlength="40"--%>
                                                            <%--data-bv-stringlength-max="40"--%>
                                                            <%--data-bv-stringlength-message="不能超过40个字">--%>
											<%--</span>--%>
                                    <%--</div>--%>
                                <%--</div>--%>

                            <%--</div>--%>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label  style="color: red;">提现金额：
                                            </label> <input type="text" class="form-control" tabindex="1"
                                                            style="padding-left: 100px;color: red;"
                                                            value='<fmt:formatNumber value="${userWithDraw.withdrawMoney}" type="number"/>'
                                                            readonly="readonly" > <input type="hidden"
                                                                                        id="withdrawMoney"
                                                                                        value='<c:out value="${userWithDraw.withdrawMoney}"/>'  >
											</span>
                                    </div>
                                </div>

                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>银行名称：
                                            </label> <input type="text" class="form-control" tabindex="1"
                                                            style="padding-left: 100px;"
                                                            value="${userWithDraw.bankName}" readonly="readonly">
											</span>
                                    </div>
                                </div>

                            </div>


                            <div class="row">

                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>银行卡号：
                                            </label> <input type="text" class="form-control" tabindex="1"
                                                            style="padding-left: 100px;" readonly="readonly"
                                                            value="${userWithDraw.bankCardNumber}">
											</span>
                                    </div>
                                </div>

                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>开户银行：
                                            </label> <input type="text" class="form-control" tabindex="1"
                                                            style="padding-left: 100px;"
                                                            value="${userWithDraw.openingBank}" readonly="readonly">
											</span>
                                    </div>
                                </div>

                            </div>

                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>开户行省份：
                                            </label> <input type="text" class="form-control" tabindex="1"
                                                            style="padding-left: 100px;" readonly="readonly"
                                                            value="${userWithDraw.openProvince}">
											</span>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>开户行城市：
                                            </label> <input type="text" class="form-control" tabindex="1"
                                                            style="padding-left: 100px;"
                                                            value="${userWithDraw.openCity}"
                                                            readonly="readonly">
											</span>
                                    </div>
                                </div>
                            </div>


                            <div class="row">
                                <div class="col-sm-6">
                                    <label style="color: #999;">扣费类型<sup
                                            style="color: red;">&nbsp;*</sup>：
                                    </label>

                                    <div class="form-group">
                                        <select class="form-control" id="deduction" name="deduction"
                                                onchange="deductionFree()" disabled="disabled">
                                            <option value="1">正常提现</option>
                                            <option value="2">恶意提现</option>
                                            <option value="3"
                                                    <c:if test="${userWithDraw.withdrawMoney eq userWithDraw.advanceMoney}">selected="selected"</c:if> >
                                                免费提现
                                            </option>
                                        </select>
                                    </div>
                                </div>

                                <div class="col-sm-6">
                                    <label style="color: #999;"><sup style="color: red;">&nbsp;</sup>&nbsp;</label>

                                    <div class="form-group">
											<span class="form-group input-icon icon-right"><label style="color: red;">放款金额<sup
                                                    style="color: red;">&nbsp;*</sup>：
                                            </label> <input type="text" class="form-control" tabindex="1"
                                                            style="padding-left: 100px;color: red;" id="advanceMoney"
                                                            name="advanceMoney" value="${userWithDraw.advanceMoney}"
                                                            readonly="readonly"  > </span>
                                    </div>
                                </div>

                            </div>


                            <div class="row">

                                <div class="col-sm-12">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>问题描述<sup
                                                    style="color: red;">&nbsp;*</sup></label> <textarea
                                                    style="padding-left: 80px;" class="form-control" rows="4"
                                                    name="probleDescription" maxlength="200" id="description"
                                                    data-bv-stringlength-max="200"
                                                    data-bv-stringlength-message="不能超过200个字"><c:out
                                                    value="${userWithDraw.probleDescription }"/></textarea>
											</span>
                                    </div>
                                </div>
                            </div>


                            <div style="text-align: right; margin-bottom: 10px;">
                                <c:choose>
                                    <c:when test="${userWithDraw.status == 0}">
                                        <shiro:hasPermission name="withdraw_success">
                                            <a href="javaScript:success(${userWithDraw.id});"
                                               class="btn btn-labeled btn-success"> <i
                                                    class="btn-label glyphicon glyphicon-ok"></i>审核成功
                                            </a>
                                        </shiro:hasPermission>
                                        <shiro:hasPermission name="withdraw_fail">
                                            <button type="button" class="btn btn-labeled btn-danger"
                                                    onclick="javaScript:fail();">
                                                <i class="btn-label glyphicon glyphicon-remove"></i>审核失败
                                            </button>
                                        </shiro:hasPermission>
                                    </c:when>

                                </c:choose>
                                <a href="${ctx}/userwithdraw/withdraw/listUserWithDraw"
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
<%@include file="/static/inc/footer.inc" %>
</body>
</html>