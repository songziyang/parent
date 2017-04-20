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
        function success(id, condition, pageCurrent) {
            //新版本
            var url = "${ctx}/userwithdraw/paback/minSubmitWithDraw/" + id + "/" + condition + "/" + pageCurrent;
            bootbox.dialog({
                // dialog的内容
                message: "您确认批准该申请？",
                // dialog的标题
                title: "放款提示",
                // 是否显示此dialog，默认true
                show: true,
                // 是否显示body的遮罩，默认true
                backdrop: true,
                // 是否显示关闭按钮，默认true
                closeButton: true,
                // 是否动画弹出dialog，IE10以下版本不支持
                animate: true,
                // dialog的类名
                className: "my-modal",
                // dialog底端按钮配置
                buttons: {
                    // 另一个按钮配置
                    "确定": {
                        className: "btn-danger",
                        callback: function () {
                            window.location.href = url;
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

        function fail() {
            var value = $("#description").val();
            if (value == "" || value == null) {
                bootbox.dialog({
                    // dialog的内容
                    message: "提交失败，请填写问题描述！",
                    // dialog的标题
                    title: "放款提示",
                    // 是否显示此dialog，默认true
                    show: true,
                    // 是否显示body的遮罩，默认true
                    backdrop: true,
                    // 是否显示关闭按钮，默认true
                    closeButton: true,
                    // 是否动画弹出dialog，IE10以下版本不支持
                    animate: true,
                    // dialog的类名
                    className: "my-modal",
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


        function failMinShen() {
            var value = $("#description").val();
            if (value == "" || value == null) {
                bootbox.dialog({
                    // dialog的内容
                    message: "提交失败，请填写问题描述！",
                    // dialog的标题
                    title: "放款提示",
                    // 是否显示此dialog，默认true
                    show: true,
                    // 是否显示body的遮罩，默认true
                    backdrop: true,
                    // 是否显示关闭按钮，默认true
                    closeButton: true,
                    // 是否动画弹出dialog，IE10以下版本不支持
                    animate: true,
                    // dialog的类名
                    className: "my-modal",
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

        function loanMoney(id, condition, pageCurrent) {
            var url = "${ctx}/userwithdraw/paback/loanMoney/" + id + "/" + condition + "/" + pageCurrent;
            bootbox.dialog({
                // dialog的内容
                message: "您确认代付打款么？",
                // dialog的标题
                title: "放款提示",
                // 是否显示此dialog，默认true
                show: true,
                // 是否显示body的遮罩，默认true
                backdrop: true,
                // 是否显示关闭按钮，默认true
                closeButton: true,
                // 是否动画弹出dialog，IE10以下版本不支持
                animate: true,
                // dialog的类名
                className: "my-modal",
                // dialog底端按钮配置
                buttons: {
                    // 另一个按钮配置
                    "确定": {
                        className: "btn-danger",
                        callback: function () {
                            window.location.href = url;
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


        function sdloanMoney(id, condition, pageCurrent) {
            var url = "${ctx}/userwithdraw/paback/sdloanMoney/" + id + "/" + condition + "/" + pageCurrent;
            bootbox.dialog({
                // dialog的内容
                message: "您确认手动打款么？",
                // dialog的标题
                title: "放款提示",
                // 是否显示此dialog，默认true
                show: true,
                // 是否显示body的遮罩，默认true
                backdrop: true,
                // 是否显示关闭按钮，默认true
                closeButton: true,
                // 是否动画弹出dialog，IE10以下版本不支持
                animate: true,
                // dialog的类名
                className: "my-modal",
                // dialog底端按钮配置
                buttons: {
                    // 另一个按钮配置
                    "确定": {
                        className: "btn-danger",
                        callback: function () {
                            window.location.href = url;
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

    </script>


</head>
<body>

<!-- Page Breadcrumb -->
<div class="page-breadcrumbs">
    <ul class="breadcrumb" style="line-height: 40px;">
        <li><i class="fa fa-home"></i> <a href="#">放款管理</a></li>
        <li><a href="#">放款管理</a></li>
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
            <div class="widget collapsed ">
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
            <div class="widget ">
                <div class="widget-header bordered-bottom bordered-sky">
                    <span class="widget-caption">表单</span>
                </div>
                <div class="widget-body">
                    <div id="registration-form">


                        <form action="${ctx}/userwithdraw/paback/minFailPayBack" id="editForm" name="editForm"
                              method="post" role="form">

                            <input type="hidden" name="id" value='<c:out value="${userWithDraw.id}"/>'>
                            <input type="hidden" name="condition" value='<c:out value="${condition}"/>'>
                            <input type="hidden" name="pageCurrent" value='<c:out value="${pageCurrent}"/>'>

                            <div class="form-title">放款信息</div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>申请时间<sup
                                                    style="color: red;">&nbsp;*</sup>：
                                            </label> <input type="text" class="form-control" tabindex="1"
                                                            readonly="readonly" value="<fmt:formatDate value="${userWithDraw.sqDate}"
														    pattern="yyyy-MM-dd HH:ss" />" style="padding-left: 100px;">
											</span>
                                    </div>
                                </div>

                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>真实姓名<sup
                                                    style="color: red;">&nbsp;*</sup>：
                                            </label> <input type="text" class="form-control" tabindex="1"
                                                            style="padding-left: 100px;"
                                                            readonly="readonly"
                                                            value="<c:out value="${userWithDraw.realname}"/>">
											</span>
                                    </div>
                                </div>
                            </div>
                            <div class="row">

                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>提现金额<sup
                                                    style="color: red;">&nbsp;*</sup>：
                                            </label> <input type="text" class="form-control" tabindex="1"
                                                            style="padding-left: 100px;color: red;"
                                                            value='<fmt:formatNumber value="${userWithDraw.withdrawMoney}" type="number"/>'
                                                            readonly="readonly">
											</span>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>打款金额<sup
                                                    style="color: red;">&nbsp;*</sup>：
                                            </label> <input type="text" class="form-control" tabindex="1"
                                                            style="padding-left: 100px;color: red;"
                                                            readonly="readonly" value="${userWithDraw.advanceMoney}">
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
                                                           style="padding-left: 100px;" readonly="readonly"
                                                           value="${user.userInfo.allRecharge}">
											</span>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>累计提现<sup
                                                    style="color: red;">&nbsp;*</sup>：
                                            </label> <input type="text" class="form-control" tabindex="1"
                                                            style="padding-left: 100px;" required readonly="readonly"
                                                            value="${user.userInfo.alliWthdraw}">
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
                                                           style="padding-left: 100px;" readonly="readonly"
                                                           value="<c:out value="${empty user.userMoney.totalFund ? 0 : user.userMoney.totalFund }"/>">
											</span>
                                    </div>
                                </div>


                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>可用资金<sup
                                                    style="color: red;">&nbsp;*</sup>：
                                            </label><input type="text" class="form-control" tabindex="1"
                                                           style="padding-left: 100px;" readonly="readonly"
                                                           value="${user.userMoney.usableFund}">
											</span>
                                    </div>
                                </div>

                            </div>


                            <div class="row">

                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>总收益<sup
                                                    style="color: red;">&nbsp;*</sup>：
                                            </label> <input type="text" class="form-control" tabindex="1"
                                                            style="padding-left: 100px;" readonly="readonly"
                                                            value="${user.userIncome.allIncome}">
											</span>
                                    </div>
                                </div>

                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>银行<sup
                                                    style="color: red;">&nbsp;*</sup>：
                                            </label> <input type="text" class="form-control" tabindex="1"
                                                            style="padding-left: 100px;"
                                                            data-bv-identical="true" value="${userWithDraw.bankName}"
                                                            readonly="readonly">
											</span>
                                    </div>
                                </div>

                            </div>


                            <div class="row">

                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>银行卡号<sup
                                                    style="color: red;">&nbsp;*</sup>：
                                            </label> <input type="text" class="form-control" tabindex="1"
                                                            style="padding-left: 100px;" readonly="readonly"
                                                            value="${userWithDraw.bankCardNumber}">
											</span>
                                    </div>
                                </div>

                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>开户银行<sup
                                                    style="color: red;">&nbsp;*</sup>：
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
											<span class="form-group input-icon icon-right"> <label>开户行省份<sup
                                                    style="color: red;">&nbsp;*</sup>：
                                            </label> <input type="text" class="form-control" tabindex="1"
                                                            style="padding-left: 100px;"
                                                            readonly="readonly" value="${userWithDraw.openProvince}">
											</span>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>开户行城市<sup
                                                    style="color: red;">&nbsp;*</sup>：
                                            </label> <input type="text" class="form-control" tabindex="1"
                                                            style="padding-left: 100px;"
                                                            value="${userWithDraw.openCity}" readonly="readonly">
											</span>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-6">

                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>审核人<sup
                                                    style="color: red;">&nbsp;*</sup>：
                                            </label> <input type="text" class="form-control" tabindex="1"
                                                            style="padding-left: 100px;" maxlength="40"
                                                            readonly="readonly"
                                                            value="${userWithDraw.auditUser}">
											</span>
                                    </div>
                                </div>
                                <div class="col-sm-6">


                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>审核时间<sup
                                                    style="color: red;">&nbsp;*</sup>：
                                            </label> <input type="text" class="form-control" tabindex="1"
                                                            style="padding-left: 100px"
                                                            readonly="readonly"
                                                            value='<fmt:formatDate value="${userWithDraw.auditDate}"  pattern="yyyy年MM月dd日 HH时mm分"/>'>
											</span>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>放款人<sup
                                                    style="color: red;">&nbsp;*</sup>：
                                            </label> <input type="text" class="form-control" tabindex="1"
                                                            style="padding-left: 100px"
                                                            readonly="readonly"
                                                            value="<c:if test="${userWithDraw.transferUser!=''}"><c:out value="${userWithDraw.transferUser}"/></c:if>">
											</span>
                                    </div>
                                </div>

                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>放款时间<sup
                                                    style="color: red;">&nbsp;*</sup>：
                                            </label> <input type="text" class="form-control" tabindex="1"
                                                            style="padding-left: 100px;" readonly="readonly"
                                                            value='<fmt:formatDate value="${userWithDraw.payDate}" pattern="yyyy年MM月dd日 HH时mm分"/>'>
											</span>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>身份证号码<sup
                                                    style="color: red;">&nbsp;*</sup>：
                                            </label> <input type="text" class="form-control" tabindex="1"
                                                            style="padding-left: 100px" readonly="readonly"
                                                            value="<c:out value="${userWithDraw.idCard}"/>">
											</span>
                                    </div>
                                </div>

                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>预留手机号<sup
                                                    style="color: red;">&nbsp;*</sup>：
                                            </label> <input type="text" class="form-control" tabindex="1"
                                                            style="padding-left: 100px;" readonly="readonly"
                                                            value="<c:out value="${userWithDraw.transMobile}"/>">
											</span>
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
                                    <c:when test="${userWithDraw.status eq 3}">


                                        <shiro:hasPermission name="withdraws_detailed">
                                            <a href="javaScript:sdloanMoney('<c:out value="${userWithDraw.id}"/>','<c:out value="${condition}"/>','<c:out value="${pageCurrent}"/>')"
                                               class="btn btn-labeled btn-darkorange"> <i
                                                    class="btn-label glyphicon glyphicon-ok"></i>线下打款
                                            </a>
                                        </shiro:hasPermission>

                                        <shiro:hasPermission name="withdraws_detailed">
                                            <a href="javaScript:loanMoney('<c:out value="${userWithDraw.id}"/>','<c:out value="${condition}"/>','<c:out value="${pageCurrent}"/>')"
                                               class="btn btn-labeled btn-success"> <i
                                                    class="btn-label glyphicon glyphicon-ok"></i>线上代付
                                            </a>
                                        </shiro:hasPermission>

                                    </c:when>

                                    <c:when test="${userWithDraw.status eq 7 || userWithDraw.status eq 8}">


                                        <shiro:hasPermission name="withdraws_detailed">
                                            <a href="javaScript:success('<c:out value="${userWithDraw.id}"/>','<c:out value="${condition}"/>','<c:out value="${pageCurrent}"/>')"
                                               class="btn btn-labeled btn-success"> <i
                                                    class="btn-label glyphicon glyphicon-ok"></i>批准放款
                                            </a>
                                        </shiro:hasPermission>

                                        <shiro:hasPermission name="withdraws_detailed">
                                            <a href="javaScript:failMinShen();"
                                               class="btn btn-labeled btn-danger"> <i
                                                    class="btn-label glyphicon glyphicon-remove"></i>放款失败
                                            </a>
                                        </shiro:hasPermission>

                                    </c:when>
                                </c:choose>

                                <a href="${ctx}/userwithdraw/paback/listWithDrawPayBack"
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

