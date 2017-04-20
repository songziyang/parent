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
    <link href="${ctx}/static/lib/simpletooltip/src/css/simpletooltip.min.css" rel="stylesheet"/>
    <script src="${ctx}/static/lib/simpletooltip/src/js/simpletooltip.js"></script>
    <style type="text/css">
        .form-inline .form-group {
            margin-bottom: 10;
        }
    </style>
    <script type="text/javascript">
        function sortSerach(orderNumber, orderSort) {
            $("#orderNumber").val(orderNumber);
            $("#orderSort").val(orderSort);
            document.listForm.submit();
        }

        $(document).ready(
                function () {
                    var filename = '<c:out value="${fileName}"/>';
                    if (filename != "") {
                        window.location.href = "http://keng.yinduoziben.com/static/download/" + filename;
                        //window.location.href = "http://localhost:8080/static/download/" + filename;
                    }
                });

        function exprotExcel(condition) {
            var url = "${ctx}/userinfo/usermoneyinfo/exportExcelFund/" + condition
            window.location.href = url;
        }
    </script>
</head>
<body>

<!-- Page Breadcrumb -->
<div class="page-breadcrumbs">
    <ul class="breadcrumb" style="line-height: 40px;">
        <li><i class="fa fa-home"></i> <a href="#">前台用户管理</a></li>
        <li><a href="#">用户资金管理</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>用户资金管理</h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
        </a> <a class="refresh" id="refresh-toggler"
                href="${ctx}/userinfo/usermoneyinfo/usermoneylist"> <i
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

    <form action="${ctx}/userinfo/usermoneyinfo/usermoneylist"
          method="post" id="listForm" name="listForm">

        <div class="row">
            <div class="col-xs-12 col-md-12">
                <div class="row">
                    <div class="col-lg-12 col-sm-12 col-xs-12">
                        <div class="widget collapsed">
                            <div class="widget-header bordered-bottom bordered-sky">
                                <i class="widget-icon fa fa-arrows-v blue"></i> <span
                                    class="widget-caption">查询条件</span>

                                <div class="widget-buttons">
                                    <a href="#"> </a> <a href="#" data-toggle="collapse"> <i
                                        class="fa fa-plus blue"></i>
                                </a>
                                </div>
                                <!--Widget Buttons-->
                            </div>
                            <!--Widget Header-->
                            <div class="widget-body">
                                <div class="form-inline" role="form">
                                    <div class="form-group">
                                        <label class="control-label no-padding-right">用户名</label>
                                        <input type="text" name="username" class="form-control" placeholder="用户名">
                                        <label class="control-label no-padding-right">手机号</label>
                                        <input type="text" name="mobile" class="form-control" placeholder="手机号">
                                        <label class="control-label no-padding-right">用户ID</label>
                                        <input type="text" name="userId" class="form-control" placeholder="用户ID">
                                        <input type="hidden" name="orderNumber" class="form-control" id="orderNumber">
                                        <input type="hidden" name="orderSort" class="form-control" id="orderSort">
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label no-padding-right">推荐人手机号</label>
                                        <input type="text" name="referralMobile" class="form-control"
                                               placeholder="推荐人手机号">
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label no-padding-right">微信名</label>
                                        <input type="text" name="wechatNumber" class="form-control"
                                               placeholder="微信名">
                                        <label class="control-label no-padding-right">qq号</label>
                                        <input type="text" name="qqNumber" class="form-control"
                                               placeholder="qq号">
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label no-padding-right">用户类型</label>
                                        <select class="form-control" name="userType">
                                            <option value="">全部</option>
                                            <option value="0">普通用户</option>
                                            <option value="1">老用户</option>
                                            <option value="2">股东用户</option>
                                            <option value="3">业务员用户</option>
                                            <option value="4">新鲜说用户</option>
                                            <option value="5">麦罗用户</option>
                                            <option value="6">涌金门</option>
                                            <option value="7">金贸街</option>
                                        </select>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label no-padding-right">用户等级</label>
                                        <select class="form-control" name="userLeve">
                                            <option value="">全部</option>
                                            <c:forEach items="${vipGrades}" var="vipGrade">
                                                <option value="${vipGrade.id}">${vipGrade.gradeName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        收益总额&nbsp;
                                        <input type="text" name="startAllIncome" class="form-control"
                                               tabindex="1" style="padding-left: 10px; width: 80px;" maxlength="40">
                                        &nbsp;到&nbsp;
                                        <input type="text" name="endAllIncome" class="form-control"
                                               tabindex="1" style="padding-left: 10px; width: 80px;" maxlength="40">
                                    </div>
                                    <div class="form-group">
                                        冻结金额&nbsp;
                                        <input type="text" name="startFreezeMoney" class="form-control"
                                               tabindex="1" style="padding-left: 10px; width: 80px;" maxlength="40">
                                        &nbsp;到&nbsp;
                                        <input type="text" name="endFreezeMoney" class="form-control"
                                               tabindex="1" style="padding-left: 10px; width: 80px;" maxlength="40">
                                    </div>
                                    <div class="form-group">
                                        体验金总额&nbsp;
                                        <input type="text" name="startExpmoney" class="form-control"
                                               tabindex="1" style="padding-left: 10px; width: 80px;" maxlength="40">
                                        &nbsp;到&nbsp;
                                        <input type="text" name="endExpmoney" class="form-control"
                                               tabindex="1" style="padding-left: 10px; width: 80px;" maxlength="40">
                                    </div>
                                    <div class="form-group">
                                        体验金余额&nbsp;
                                        <input type="text" name="startAbledExpmoney" class="form-control"
                                               tabindex="1" style="padding-left: 10px; width: 80px;" maxlength="40">
                                        &nbsp;到&nbsp;
                                        <input type="text" name="endAbledExpmoney" class="form-control"
                                               tabindex="1" style="padding-left: 10px; width: 80px;" maxlength="40">
                                    </div>
                                    <div class="form-group">
                                        活期宝&nbsp;
                                        <input type="text" name="startCurrent" class="form-control"
                                               tabindex="1" style="padding-left: 10px; width: 80px;" maxlength="40">
                                        &nbsp;到&nbsp;
                                        <input type="text" name="endCurrent" class="form-control"
                                               tabindex="1" style="padding-left: 10px; width: 80px;" maxlength="40">
                                    </div>
                                    <div class="form-group">
                                        定存宝&nbsp;
                                        <input type="text" name="startRagular" class="form-control"
                                               tabindex="1" style="padding-left: 10px; width: 80px;" maxlength="40">
                                        &nbsp;到&nbsp;
                                        <input type="text" name="endRagular" class="form-control"
                                               tabindex="1" style="padding-left: 10px; width: 80px;" maxlength="40">
                                    </div>
                                    <div class="form-group">
                                        随心存&nbsp;
                                        <input type="text" name="startFree" class="form-control"
                                               tabindex="1" style="padding-left: 10px; width: 80px;" maxlength="40">
                                        &nbsp;到&nbsp;
                                        <input type="text" name="endFree" class="form-control"
                                               tabindex="1" style="padding-left: 10px; width: 80px;" maxlength="40">
                                    </div>
                                    <div class="form-group">
                                        新手宝&nbsp;
                                        <input type="text" name="startPrivilege" class="form-control"
                                               tabindex="1" style="padding-left: 10px; width: 80px;" maxlength="40">
                                        &nbsp;到&nbsp;
                                        <input type="text" name="endPrivilege" class="form-control"
                                               tabindex="1" style="padding-left: 10px; width: 80px;" maxlength="40">
                                    </div>
                                    <div class="form-group" style="margin-left: 10px">
                                        <label class="control-label no-padding-right">是否红名</label> <select
                                            class="form-control" name="status">
                                        <option value="">全部</option>
                                        <option value="1">红名</option>
                                        <option value="2">未红名</option>
                                    </select>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right"></label>&nbsp&nbsp
                                        注册日期:从
                                        <input type="text" name="startRegiest" class="form-control" tabindex="1"
                                               style="text-align: center; width: 120px;" readonly
                                               onfocus="WdatePicker({skin:'twoer'})">
                                        到
                                        <input type="text" name="endRegiest" class="form-control"
                                               tabindex="1" style="text-align: center; width: 120px;"
                                               readonly onfocus="WdatePicker({skin:'twoer'})">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right"></label>&nbsp&nbsp
                                        资金余额:从
                                        <input type="text" name="startAbleMoney" class="form-control" tabindex="1"
                                               style=" width: 120px;">
                                        到
                                        <input type="text" name="endAbleMoney" class="form-control"
                                               tabindex="1" style=" width: 120px;">
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label no-padding-right"></label>&nbsp&nbsp
                                        资金总额:从
                                        <input type="text" name="startTotalMoney" class="form-control" tabindex="1"
                                               style="width: 120px;">
                                        到
                                        <input type="text" name="endTotalMoney" class="form-control"
                                               tabindex="1" style=" width: 120px;">
                                    </div>


                                    <div class="form-group">
                                        <a href="javascript:searchData();"
                                           class="btn btn-labeled btn-blue"> <i
                                                class="btn-label fa fa-search"></i>查询
                                        </a>
                                    </div>
                                </div>
                            </div>
                            <!--Widget Body-->
                        </div>
                        <!--Widget-->
                    </div>
                </div>
                <div class="widget">
                    <div class="widget-header  with-footer">
                        <span class="widget-caption">前台用户资金列表</span>

                        <div class="widget-buttons">
                            <a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
                            </a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
                        </a>
                        </div>
                    </div>
                    <div class="widget-body">
                        <div class="flip-scroll">
                            <div style="text-align: right; margin-bottom: 10px;">
                                <shiro:hasPermission name="usermoney_exportexcel">
                                    <a
                                            href="javascript:exprotExcel('<c:out value="${condition}"/>');"
                                            class="btn btn-labeled btn-success"> <i
                                            class="btn-label glyphicon glyphicon-download-alt"></i>导出Excel
                                    </a>
                                </shiro:hasPermission>
                            </div>

                            <table class="table table-hover table-striped table-bordered">
                                <thead style="font-size: 16px; font-weight: bold;">
                                <tr>
                                    <th width="80" style="text-align: center;">序号</th>
                                    <th>ID</th>
                                    <th>用户名</th>
                                    <th>手机号</th>
                                    <%--<th>微信名</th>
                                    <th>qq号</th>--%>
                                    <th>
                                        <div style="float: left">账户总额</div>
                                        <div style="float: right;">
                                            <a href="javascript:sortSerach(1,'asc');"><i
                                                    class="fa fa-sort-asc"
                                                    style="height: 5px; float: left; margin-top: 2px"></i></a> <br/>
                                            <a href="javascript:sortSerach(1,'desc');"><i
                                                    class="fa fa-sort-desc"
                                                    style="height: 5px; margin-top: -10px"></i></a>
                                        </div>
                                    </th>
                                    <th>
                                        <div style="float: left">账户余额</div>
                                        <div style="float: right;">
                                            <a href="javascript:sortSerach(2,'asc');"><i
                                                    class="fa fa-sort-asc"
                                                    style="height: 5px; float: left; margin-top: 2px"></i></a> <br/>
                                            <a href="javascript:sortSerach(2,'desc');"><i
                                                    class="fa fa-sort-desc"
                                                    style="height: 5px; margin-top: -10px"></i></a>
                                        </div>
                                    </th>
                                    <!--    -->
                                    <th>
                                        <div style="float: left">收益</div>
                                        <div style="float: right;">
                                            <a href="javascript:sortSerach(11,'asc');"><i
                                                    class="fa fa-sort-asc"
                                                    style="height: 5px; float: left; margin-top: 2px"></i></a> <br/>
                                            <a href="javascript:sortSerach(11,'desc');"><i
                                                    class="fa fa-sort-desc"
                                                    style="height: 5px; margin-top: -10px"></i></a>
                                        </div>
                                    </th>

                                    <th>
                                        <div style="float: left">冻结金额</div>
                                        <div style="float: right;">
                                            <a href="javascript:sortSerach(9,'asc');"><i
                                                    class="fa fa-sort-asc"
                                                    style="height: 5px; float: left; margin-top: 2px"></i></a> <br/>
                                            <a href="javascript:sortSerach(9,'desc');"><i
                                                    class="fa fa-sort-desc"
                                                    style="height: 5px; margin-top: -10px"></i></a>
                                        </div>
                                    </th>
                                    <th>
                                        <div style="float: left">体验金总额</div>
                                        <div style="float: right;">
                                            <a href="javascript:sortSerach(7,'asc');"><i
                                                    class="fa fa-sort-asc"
                                                    style="height: 5px; float: left; margin-top: 2px"></i></a> <br/>
                                            <a href="javascript:sortSerach(7,'desc');"><i
                                                    class="fa fa-sort-desc"
                                                    style="height: 5px; margin-top: -10px"></i></a>
                                        </div>
                                    </th>

                                    <th>
                                        <div style="float: left">体验金余额</div>
                                        <div style="float: right;">
                                            <a href="javascript:sortSerach(8,'asc');"><i
                                                    class="fa fa-sort-asc"
                                                    style="height: 5px; float: left; margin-top: 2px"></i></a> <br/>
                                            <a href="javascript:sortSerach(8,'desc');"><i
                                                    class="fa fa-sort-desc"
                                                    style="height: 5px; margin-top: -10px"></i></a>
                                        </div>
                                    </th>
                                    <!--
                                    <th>
                                        <div style="float: left">体验金冻结金额</div>
                                        <div style="float: right;">
                                            <a href="javascript:sortSerach(10,'asc');"><i
                                                    class="fa fa-sort-asc"
                                                    style="height: 5px; float: left; margin-top: 2px"></i></a> <br/>
                                            <a href="javascript:sortSerach(10,'desc');"><i
                                                    class="fa fa-sort-desc"
                                                    style="height: 5px; margin-top: -10px"></i></a>
                                        </div>
                                    </th>
                                    -->
                                    <th>
                                        <div style="float: left">活期宝</div>
                                        <div style="float: right;">
                                            <a href="javascript:sortSerach(3,'asc');"><i
                                                    class="fa fa-sort-asc"
                                                    style="height: 5px; float: left; margin-top: 2px"></i></a> <br/>
                                            <a href="javascript:sortSerach(3,'desc');"><i
                                                    class="fa fa-sort-desc"
                                                    style="height: 5px; margin-top: -10px"></i></a>
                                        </div>
                                    </th>
                                    <th>
                                        <div style="float: left">定存宝</div>
                                        <div style="float: right;">
                                            <a href="javascript:sortSerach(4,'asc');"><i
                                                    class="fa fa-sort-asc"
                                                    style="height: 5px; float: left; margin-top: 2px"></i></a> <br/>
                                            <a href="javascript:sortSerach(4,'desc');"><i
                                                    class="fa fa-sort-desc"
                                                    style="height: 5px; margin-top: -10px"></i></a>
                                        </div>
                                    </th>
                                    <th>
                                        <div style="float: left">随心存</div>
                                        <div style="float: right;">
                                            <a href="javascript:sortSerach(12,'asc');"><i
                                                    class="fa fa-sort-asc"
                                                    style="height: 5px; float: left; margin-top: 2px"></i></a> <br/>
                                            <a href="javascript:sortSerach(12,'desc');"><i
                                                    class="fa fa-sort-desc"
                                                    style="height: 5px; margin-top: -10px"></i></a>
                                        </div>
                                    </th>
                                    <th>
                                        <div style="float: left">新手宝</div>
                                        <div style="float: right;">
                                            <a href="javascript:sortSerach(13,'asc');"><i
                                                    class="fa fa-sort-asc"
                                                    style="height: 5px; float: left; margin-top: 2px"></i></a> <br/>
                                            <a href="javascript:sortSerach(13,'desc');"><i
                                                    class="fa fa-sort-desc"
                                                    style="height: 5px; margin-top: -10px"></i></a>
                                        </div>
                                    </th>
                                    <th style="text-align: center;">操作</th>
                                </tr>
                                </thead>
                                <tbody style="font-size: 12px;">
                                <c:forEach items="${page.content}" var="user"
                                           varStatus="status">
                                    <tr>
                                        <td class="table_no" width="80" align="center"></td>
                                        <td>
                                            <span
                                                    <c:if test="${not empty user.wechatNumber or not empty user.qqNumber}">data-simpletooltip="init"
                                                    title='<c:out value="微信号：${user.wechatNumber}<br/> qq号：${user.qqNumber}" />'
                                            </c:if>>
                                                <c:out value="${user.id}"/>
                                            </span>
                                            </a>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${user.userLeve.gradeNum eq 1}"><img src="${ctx}/static/lib/images/vip1.png" /></c:when>
                                                <c:when test="${user.userLeve.gradeNum eq 2}"><img src="${ctx}/static/lib/images/vip2.png" /></c:when>
                                            </c:choose>
                                            <a href='${ctx}/userinfo/user/listUserInfo/<c:out value="${user.id}" />'
                                               data-simpletooltip="init" title='<c:out value="${user.remark}" />'
                                               <c:if test="${not empty user.remark}">style='color: red;'</c:if>>
                                                <c:out value="${user.username}"/>
                                            </a>
                                        </td>
                                        <td>
                                            <a href='${ctx}/userinfo/user/listUserInfo/<c:out value="${user.id}" />'
                                               data-simpletooltip="init"
                                               title='<c:out value="${user.remark}" />'
                                               <c:if test="${not empty user.remark}">style='color: red;'</c:if>>
                                                <c:out value="${user.mobile}"/>
                                            </a>
                                        </td>
                                            <%-- <td>
                                                 <a href='${ctx}/userinfo/user/listUserInfo/<c:out value="${user.id}" />'
                                                    data-simpletooltip="init"
                                                    title='<c:out value="${user.remark}" />'
                                                    <c:if test="${not empty user.remark}">style='color: red;'</c:if>>
                                                     <c:out value="${user.wechatNumber}"/>
                                                 </a>
                                             </td>
                                             <td>
                                                 <a href='${ctx}/userinfo/user/listUserInfo/<c:out value="${user.id}" />'
                                                    data-simpletooltip="init"
                                                    title='<c:out value="${user.remark}" />'
                                                    <c:if test="${not empty user.remark}">style='color: red;'</c:if>>
                                                     <c:out value="${user.qqNumber}"/>
                                                 </a>
                                             </td>--%>
                                        <td>
                                            <a href='${ctx}/userinfo/usermoneyinfo/findUserFundRecord/<c:out value="${user.id}"/>'><c:out
                                                    value="${user.userMoney.totalFund}"/></a></td>
                                        <td>
                                            <a href='${ctx}/userinfo/usermoneyinfo/findUserFundRecord/<c:out value="${user.id}"/>'><c:out
                                                    value="${user.userMoney.usableFund}"/></a></td>

                                        <td>
                                            <a href='${ctx}/traderecord/income/list?mobile=<c:out value="${user.mobile}"/>'><c:out
                                                    value="${user.userIncome.allIncome}"/></a></td>
                                            <%----%>
                                        <td>
                                            <a href='${ctx}/userinfo/usermoneyinfo/findBlokedlogByUserID/<c:out value="${user.id}"/>'>
                                                <fmt:formatNumber
                                                        value="${empty user.userMoney.freezeFund ? 0 : user.userMoney.freezeFund}"
                                                        type="number"/>

                                            </a>
                                        </td>
                                        <td>
                                            <a href="${ctx}/traderecord/expmoney/list?mobile=${user.mobile}">
                                                <fmt:formatNumber
                                                        value="${empty user.userExMoney.amount ? 0 : user.userExMoney.amount}"
                                                        type="number"/>

                                            </a>
                                        </td>
                                        <td>
                                            <a href="${ctx}/traderecord/expmoney/list?mobile=${user.mobile}">
                                                <fmt:formatNumber
                                                        value="${empty user.userExMoney.ableMoney ? 0 : user.userExMoney.ableMoney}"
                                                        type="number"/>
                                            </a>
                                        </td>
                                        <!--
                                        <td>
                                            <a href='${ctx}/userinfo/usermoneyinfo/findExBlokedlogByUserID/<c:out value="${user.id}"/>'><c:out
                                                    value="${user.userExMoney.blockedMoney}"/></a></td>
                                        -->
                                        <td>
                                            <a href="${ctx}/traderecord/current/listCurrentRecored?mobile=${user.mobile}"><c:out
                                                    value="${user.userInvestinfo.allInvestDayloan}"/></a></td>
                                        <td>
                                            <a href="${ctx}/traderecord/ragular/listRagularRecored?mobile=${user.mobile}">
                                                <fmt:formatNumber
                                                        value="${empty user.userInvestinfo.allInvestDeposit ? 0 : user.userInvestinfo.allInvestDeposit}"
                                                        type="number"/>
                                            </a>
                                        </td>
                                        <td>
                                            <a href="${ctx}/traderecord/free/list?mobile=${user.mobile}">
                                                <fmt:formatNumber
                                                        value="${empty user.userInvestinfo.allInvestFree ? 0 : user.userInvestinfo.allInvestFree}"
                                                        type="number"/>
                                            </a>
                                        </td>
                                        <td>
                                            <a href="${ctx}/traderecord/privilege/listPrivilegeRecored?mobile=${user.mobile}">
                                                <fmt:formatNumber
                                                        value="${empty user.userInvestinfo.allInvestPrivilege ? 0 : user.userInvestinfo.allInvestPrivilege}"
                                                        type="number"/>
                                            </a>
                                        </td>
                                        <td align="center">
                                            <shiro:hasPermission name="usermoney_fundrecordlist">
                                                <a href='${ctx}/traderecord/redpacket/list?mobile=${user.mobile}'
                                                   class="btn btn-info btn-xs edit"><i
                                                        class="fa fa-file-text"></i>红包</a>

                                            </shiro:hasPermission>
                                                <%--   <shiro:hasPermission name="usermoney_listredpack">
                                                   <a
                                                           href='${ctx}/userinfo/usermoneyinfo/finduserredpack/<c:out value="${user.id}"/>'
                                                           class="btn btn-info btn-xs edit"><i
                                                           class="fa fa-file-text"></i>红包</a>
                                               </shiro:hasPermission>--%>
                                        </td>
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
    </form>
</div>
<%@include file="/static/inc/footer.inc" %>
</body>
</html>