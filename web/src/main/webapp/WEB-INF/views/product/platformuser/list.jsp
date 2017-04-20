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
    <script src="${ctx}>/static/lib/beyond/js/toastr/toastr.js"></script>
    <style type="text/css">
        .form-inline .form-group {
            margin-bottom: 10;
        }
    </style>
</head>
<body>
<div id="rechargeModal" class="modal fade modal-darkorange"
     tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="${ctx}/platform/platformuser/recharge" method="post" id="editForm" name="editForm">
                <input type="hidden" name="platformUserId" value='<c:out value="${platformUser.id}"/>'>

                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="false">×</button>
                    <h4 class="modal-title">余额充值</h4>
                </div>
                <div class="modal-body">
                    <div style="width: 100%; height: 5%;">
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="form-group">
									<span class="form-group input-icon icon-right">
										<label>充值金额<sup style="color: red;">&nbsp;*</sup>：</label>
										<input type="text" class="form-control" tabindex="1"
                                               style="padding-left: 100px;" name="fund" required maxlength="20"
                                               data-bv-stringlength-max="16"
                                               data-bv-stringlength-message="不能超过16个字" data-bv-regexp="true"
                                               data-bv-regexp-regexp="^\d+(\.\d+)?$"
                                               data-bv-regexp-message="充值金额格式错误">
									</span>
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <div class="form-group">
									<span class="form-group input-icon icon-right"> <label>充值说明：</label>
										<textarea style="padding-left: 80px;" class="form-control"
                                                  rows="4" name="desc" maxlength="40"
                                                  data-bv-stringlength-max="40"
                                                  data-bv-stringlength-message="不能超过40个字"></textarea>
									</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-success">确认充值</button>
                    <button type="button" class="btn btn-blue" data-dismiss="modal"
                            aria-hidden="false">取消
                    </button>
                </div>
            </form>

        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<div id="buyDebtModal" class="modal fade modal-darkorange"
     tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="${ctx}/platform/platformuser/buyDebt" method="post" id="editForm2" name="editForm">
                <input type="hidden" name="platformUserId" value='<c:out value="${platformUser.id}"/>'>

                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="false">×</button>
                    <h4 class="modal-title">购买债权</h4>
                </div>
                <div class="modal-body">
                    <div style="width: 100%; height: 5%;">
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="form-group">
									<span class="form-group input-icon icon-right">
										<label>购买债权<sup style="color: red;">&nbsp;*</sup>：</label>
										<input type="text" class="form-control" tabindex="1"
                                               style="padding-left: 100px;" name="fund" required maxlength="20"
                                               data-bv-stringlength-max="16"
                                               data-bv-stringlength-message="不能超过16个字" data-bv-regexp="true"
                                               data-bv-regexp-regexp="^\d+(\.\d+)?$"
                                               data-bv-regexp-message="购买金额格式错误">
									</span>
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <div class="form-group">
									<span class="form-group input-icon icon-right"> <label>购买说明：</label>
										<textarea style="padding-left: 80px;" class="form-control"
                                                  rows="4" name="desc" maxlength="40"
                                                  data-bv-stringlength-max="40"
                                                  data-bv-stringlength-message="不能超过40个字"></textarea>
									</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-success">确认购买</button>
                    <button type="button" class="btn btn-blue" data-dismiss="modal"
                            aria-hidden="false">取消
                    </button>
                </div>
            </form>

        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<div id="sellDebtModal" class="modal fade modal-darkorange"
     tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="${ctx}/platform/platformuser/sellDebt" method="post" id="editForm3" name="editForm">
                <input type="hidden" name="platformUserId" value='<c:out value="${platformUser.id}"/>'>

                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="false">×</button>
                    <h4 class="modal-title">售出债权</h4>
                </div>
                <div class="modal-body">
                    <div style="width: 100%; height: 5%;">
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="form-group">
									<span class="form-group input-icon icon-right">
										<label>售出债权<sup style="color: red;">&nbsp;*</sup>：</label>
										<input type="text" class="form-control" tabindex="1"
                                               style="padding-left: 100px;" name="fund" required maxlength="20"
                                               data-bv-stringlength-max="16"
                                               data-bv-stringlength-message="不能超过16个字" data-bv-regexp="true"
                                               data-bv-regexp-regexp="^\d+(\.\d+)?$"
                                               data-bv-regexp-message="售出金额格式错误">
									</span>
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <div class="form-group">
									<span class="form-group input-icon icon-right"> <label>售出说明：</label>
										<textarea style="padding-left: 80px;" class="form-control"
                                                  rows="4" name="desc" maxlength="40"
                                                  data-bv-stringlength-max="40"
                                                  data-bv-stringlength-message="不能超过40个字"></textarea>
									</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-success">确认售出</button>
                    <button type="button" class="btn btn-blue" data-dismiss="modal"
                            aria-hidden="false">取消
                    </button>
                </div>
            </form>

        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- Page Breadcrumb -->
<div class="page-breadcrumbs">
    <ul class="breadcrumb" style="line-height: 40px;">
        <li><i class="fa fa-home"></i> <a href="#">产品管理</a></li>
        <li><a href="#">平台用户</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>平台用户</h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
        </a> <a class="refresh" id="refresh-toggler"
                href="${ctx}/platform/platformuser/list"> <i
            class="glyphicon glyphicon-refresh"></i>
    </a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
            class="glyphicon glyphicon-fullscreen"></i>
    </a>
    </div>
    <!--Header Buttons End-->
</div>
<!-- /Page Header -->
<!-- Page Body -->
<form action="${ctx}/platform/platformuser/list" method="post" id="listForm" name="listForm">
<div class="page-body">
    <div class="row">
        <div class="col-xs-12 col-md-12">

                <div class="row">
                    <div class="col-lg-12 col-sm-12 col-xs-12">
                        <div class="widget">
                            <div class="widget-header bordered-bottom bordered-sky">
                                <i class="widget-icon fa fa-arrows-v blue"></i> <span
                                    class="widget-caption">平台用户信息</span>

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
                                                <input type="hidden" id="hid_userId" value="${user.id}"/>

                                                <div class="ticket-type  col-sm-6  ">
                                                    <span class="divider hidden-xs"></span> <span
                                                        class="user-company">用户名：</span> <span
                                                        class="user-company">

                                               				 <c:out value="${platformUser.realName}"/></span>
                                                </div>
                                                <div class="ticket-type   col-sm-6 ">
                                                    <span class="divider hidden-xs"></span> <span
                                                        class="user-company">身份证号：</span> <span
                                                        class="user-company">
                                                     		<c:out value="${platformUser.idCard}"/>

														</span>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="ticket-item">
                                            <div class="row">
                                                <div class="ticket-time  col-sm-3 ">
                                                    <span class="divider hidden-xs"></span> <span
                                                        class="user-company">账户余额：</span> <span
                                                        class="user-company"><c:out
                                                        value="${platformUser.usableFund }"/></span>
                                                </div>
                                                <div class="ticket-time  col-sm-3 ">
                                                    <span class="divider hidden-xs"></span>
                                                    <shiro:hasPermission name="platformuser_recharge">
                                                        <button class="btn btn-blue" type="button"
                                                                data-target="#rechargeModal" data-toggle="modal">余额充值
                                                        </button>
                                                    </shiro:hasPermission>
                                                </div>
                                                <div class="ticket-time col-sm-3 ">
                                                    <span class="divider hidden-xs"></span> <span
                                                        class="user-company">债权金额：</span> <span
                                                        class="user-company"><c:out
                                                        value="${platformUser.allInvest }"/></span>
                                                </div>
                                                <div class="ticket-time col-sm-3 ">
                                                    <span class="divider hidden-xs"></span>
                                                    <shiro:hasPermission name="platformuser_buydebt">
                                                        <button class="btn btn-blue" type="button"
                                                                data-target="#buyDebtModal" data-toggle="modal">购买债权
                                                        </button>
                                                    </shiro:hasPermission>
                                                    <span class="divider hidden-xs"></span>
                                                    <shiro:hasPermission name="platformuser_selldebt">
                                                        <button class="btn btn-blue" type="button"
                                                                data-target="#sellDebtModal" data-toggle="modal">售出债权
                                                        </button>
                                                    </shiro:hasPermission>
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

                <div class="widget">
                    <div class="widget-header  with-footer">
                        <span class="widget-caption">平台资金记录列表</span>

                        <div class="widget-buttons">
                            <a href="#" data-toggle="maximize">
                                <i class="fa fa-expand"></i>
                            </a>
                            <a href="#" data-toggle="collapse">
                                <i class="fa fa-minus"></i>
                            </a>
                        </div>
                    </div>
                    <div class="widget-body">
                        <div class="flip-scroll">
                            <table class="table table-hover table-striped table-bordered">
                                <thead style="font-size: 16px; font-weight: bold;">
                                <tr>
                                    <th width="80" style="text-align: center;">序号</th>
                                    <th>资金类型</th>
                                    <th>操作金额</th>
                                    <th>操作类型</th>
                                    <th>说明</th>
                                    <th>操作时间</th>
                                </tr>
                                </thead>
                                <tbody style="font-size: 12px;">
                                <c:forEach items="${page.content}" var="record" varStatus="status">
                                    <tr>
                                        <td class="table_no" width="80" align="center"></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${record.type eq 1}">入账</c:when>
                                                <c:when test="${record.type eq 2}">出账</c:when>
                                            </c:choose>
                                        </td>
                                        <td><c:out value="${record.fund}"/></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${record.linkType eq 1}">充值</c:when>
                                                <c:when test="${record.linkType eq 2}">购买债权</c:when>
                                                <c:when test="${record.linkType eq 3}">售出债权</c:when>
                                                <c:when test="${record.linkType eq 10}">活期购买</c:when>
                                                <c:when test="${record.linkType eq 11}">活期赎回</c:when>
                                                <c:when test="${record.linkType eq 20}">定存购买</c:when>
                                                <c:when test="${record.linkType eq 21}">定存到期</c:when>
                                                <c:when test="${record.linkType eq 30}">自由存购买</c:when>
                                                <c:when test="${record.linkType eq 31}">自由存到期</c:when>
                                                <c:when test="${record.linkType eq 40}">美利金融购买</c:when>
                                                <c:when test="${record.linkType eq 41}">美利金融到期</c:when>
                                                <c:when test="${record.linkType eq 50}">转转赚购买</c:when>
                                                <c:when test="${record.linkType eq 51}">转转赚到期</c:when>
                                            </c:choose>
                                        </td>
                                        <td><c:out value="${record.opinfo}"/></td>
                                        <td><fmt:formatDate value="${record.opdate}"
                                                            pattern="yyyy年MM月dd日 HH时mm分"/></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <tags:pagination page="${page}"></tags:pagination>
                        </div>
                    </div>
                </div>


        </div>
    </div>
</div>
</form>
<%@include file="/static/inc/footer.inc" %>
</body>
</html>