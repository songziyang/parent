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
    <style type="text/css">
        .form-inline .form-group {
            margin-bottom: 10;
        }
    </style>
    <script type="text/javascript">

        /**
         * 多选确认发货
         * @param urls
         */
        function ensureSendgoods(urls) {
            if (hasChecked(listForm) == false) {
                Notify('请先选择要执行操作的记录！', 'top-left', '5000', 'warning', 'fa-warning', true);
                return;
            }
            bootbox.dialog({
                message: "您确定要执行此操作吗？",
                title: "操作提示",
                className: "modal-darkorange",
                buttons: {
                    success: {
                        label: "确认",
                        className: "btn-default",
                        callback: function () {
                            document.listForm.action = urls;
                            document.listForm.target = "_self";
                            document.listForm.submit();
                        }
                    },
                    "取消": {
                        className: "btn-default",
                        callback: function () {
                        }
                    }
                }
            });
        }


        function auditSuccess(url) {
            bootbox.dialog({
                message: "您确定要执行此操作吗？",
                title: "操作提示",
                className: "modal-darkorange",
                buttons: {
                    success: {
                        label: "确认",
                        className: "btn-default",
                        callback: function () {
                            window.location.href = url;
                        }
                    },
                    "取消": {
                        className: "btn-default",
                        callback: function () {
                        }
                    }
                }
            });
        }


        function auditFailure(url) {
            bootbox.dialog({
                message: "您确定要取消订单吗？",
                title: "取消订单提示",
                className: "modal-darkorange",
                buttons: {
                    success: {
                        label: "确认",
                        className: "btn-default",
                        callback: function () {
                            window.location.href = url;
                        }
                    },
                    "取消": {
                        className: "btn-default",
                        callback: function () {
                        }
                    }
                }
            });
        }

        $(function () {
            var filename = '<c:out value="${fileName}"/>';
            if (filename != "") {
                window.location.href = "http://keng.yinduoziben.com/static/download/" + filename;
//                window.location.href ="http://localhost:8080/static/download/"+filename;
            }
        });

        function exprotExcel(condition) {
            var url = "${ctx}/activity/threeBillionExchange/exportExcel/" + condition
            window.location.href = url;
        }
    </script>
</head>
<body>

<!-- Page Breadcrumb -->
<div class="page-breadcrumbs">
    <ul class="breadcrumb" style="line-height: 40px;">
        <li><i class="fa fa-home"></i> <a href="#">活动管理</a></li>
        <li><a href="#">30亿活动兑换记录</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>30亿活动兑换记录</h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#">
            <i class="fa fa-arrows-h"></i>
        </a>
        <a class="refresh" id="refresh-toggler" href="${ctx}/activity/threeBillionExchange/list">
            <i class="glyphicon glyphicon-refresh"></i>
        </a>
        <a class="fullscreen" id="fullscreen-toggler" href="#">
            <i class="glyphicon glyphicon-fullscreen"></i>
        </a>
    </div>
    <!--Header Buttons End-->
</div>
<!-- /Page Header -->
<!-- Page Body -->
<form action="${ctx}/activity/threeBillionExchange/list" method="post" id="listForm" name="listForm">
    <div class="page-body">
        <div class="row">
            <div class="col-xs-12 col-md-12">
                <div class="row">
                    <div class="col-lg-12 col-sm-12 col-xs-12">
                        <div class="widget collapsed">
                            <div class="widget-header bordered-bottom bordered-sky">
                                <i class="widget-icon fa fa-arrows-v blue"></i>
                                <span class="widget-caption">查询条件</span>

                                <div class="widget-buttons">
                                    <a href="#"></a>
                                    <a href="#" data-toggle="collapse">
                                        <i class="fa fa-plus blue"></i>
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
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right">手机号</label>
                                        <input type="text" name="mobile" class="form-control" placeholder="手机号">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right">奖品类型</label>
                                        <select class="form-control" name="type" style="width: 200px;">
                                            <option value="">全部</option>
                                            <option value="1">订制U盘</option>
                                            <option value="2">纪念银币</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right">状态</label>
                                        <select class="form-control" name="status" style="width: 200px;">
                                            <option value="1">待处理</option>
                                            <option value="2">已发货</option>
                                            <option value="3">已收货</option>
                                            <option value="4">已撤单</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right"></label>
                                        &nbsp;&nbsp;
                                        兑换日期：从
                                        <input type="text" name="startDate" class="form-control" tabindex="1"
                                               style="padding-left: 50px;" required maxlength="40"
                                               onfocus="WdatePicker({skin:'twoer'})">
                                        到
                                        <input type="text" name="endDate" class="form-control" tabindex="1"
                                               style="padding-left: 50px;" required maxlength="40"
                                               onfocus="WdatePicker({skin:'twoer'})">
                                    </div>
                                    <div class="form-group">
                                        <a href="javascript:searchData();" class="btn btn-labeled btn-blue">
                                            <i class="btn-label fa fa-search"></i>查询
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
                        <span class="widget-caption">30亿活动兑换记录</span>

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
                            <div style="text-align: right;margin-bottom: 10px;">
                                <a href="javascript:exprotExcel('<c:out value="${condition}"/>');"
                                   class="btn btn-labeled btn-success"> <i
                                        class="btn-label glyphicon glyphicon-envelope"></i>导出Excel
                                </a>
                                <c:if test="${status eq 1}">
                                    <shiro:hasPermission name="act3billexchange_deal">
                                        <a class="btn btn-labeled btn-info"
                                           href="javascript:ensureSendgoods('${ctx}/activity/threeBillionExchange/auditSuccess/2');">
                                            <i class="btn-label fa fa-edit"></i>
                                            确认发货
                                        </a>
                                    </shiro:hasPermission>
                                </c:if>
                                <c:if test="${status eq 2}">
                                    <shiro:hasPermission name="act3billexchange_deal">
                                        <a class="btn btn-labeled btn-info"
                                           href="javascript:ensureSendgoods('${ctx}/activity/threeBillionExchange/auditSuccess/3');">
                                            <i class="btn-label fa fa-edit"></i>
                                            确认收货
                                        </a>
                                    </shiro:hasPermission>
                                </c:if>
                            </div>
                            <table class="table table-hover table-striped table-bordered">
                                <thead style="font-size: 16px; font-weight: bold;">
                                <tr>
                                    <th width="80" style="text-align: center;">序号</th>
                                    <th>用户名</th>
                                    <th>手机号</th>
                                    <th>奖品类型</th>
                                    <th>收货人</th>
                                    <th>收货人手机号</th>
                                    <th>收货地址</th>
                                    <th>状态</th>
                                    <th>兑换时间</th>
                                    <th>受理时间</th>
                                    <shiro:hasPermission name="act3billexchange_deal ">
                                        <c:if test="${status eq 1 || status eq 2}">
                                            <th width="20"><label> <input type="checkbox">
                                                <span class="text"></span>
                                            </label></th>
                                            <th>操作</th>
                                        </c:if>
                                    </shiro:hasPermission>
                                </tr>
                                </thead>
                                <tbody style="font-size: 12px;">
                                <c:forEach items="${page.content}" var="exchange" varStatus="status">
                                    <tr>
                                        <td class="table_no" width="80" align="center"></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${exchange.user.userLeve.gradeNum eq 1}"><img src="${ctx}/static/lib/images/vip1.png" /></c:when>
                                                <c:when test="${exchange.user.userLeve.gradeNum eq 2}"><img src="${ctx}/static/lib/images/vip2.png" /></c:when>
                                            </c:choose>
                                            <c:out value="${exchange.user.username}" />
                                        </td>
                                        <td><c:out value="${exchange.user.mobile}" /></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${exchange.type eq 1}">订制U盘</c:when>
                                                <c:when test="${exchange.type eq 2}">纪念银币</c:when>
                                            </c:choose>
                                        </td>
                                        <td><c:out value="${exchange.realName}" /></td>
                                        <td><c:out value="${exchange.mobile}" /></td>
                                        <td><c:out value="${exchange.addr}" /></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${exchange.status eq 1}">待处理</c:when>
                                                <c:when test="${exchange.status eq 2}">已发货</c:when>
                                                <c:when test="${exchange.status eq 3}">已确认收货</c:when>
                                                <c:when test="${exchange.status eq 4}">已撤单</c:when>
                                            </c:choose>
                                        </td>
                                        <td><fmt:formatDate value="${exchange.createdDate}" pattern="yyyy-MM-dd HH:mm" />
                                        <td><fmt:formatDate value="${exchange.operationDate}" pattern="yyyy-MM-dd HH:mm" />
                                        <shiro:hasPermission name="act3billexchange_deal">
                                        <c:if test="${exchange.status eq 1 || exchange.status eq 2}">
                                                <td width="20">
                                                    <label>
                                                        <input type="checkbox" name="ids" value='<c:out value="${integral.id}" />'>
                                                        <span class="text"></span>
                                                    </label>
                                                </td>
                                                <td width="200px" align="center">
                                                    <c:choose>
                                                        <c:when test="${exchange.status eq 1}">
                                                            <a onclick="auditSuccess('${ctx}/activity/threeBillionExchange/auditSuccess/<c:out value="${exchange.id}"/>/2')"
                                                               class="btn btn-success btn-xs edit">
                                                                <i class="fa fa-edit"></i>确认发货
                                                            </a>
                                                            &nbsp;&nbsp;
                                                            <a onclick="auditFailure('${ctx}/activity/threeBillionExchange/auditFailure/<c:out value="${exchange.id}"/>')"
                                                               class="btn btn-danger btn-xs edit">
                                                                <i class="fa fa-edit"></i>取消订单
                                                            </a>
                                                        </c:when>
                                                        <c:when test="${exchange.status eq 2}">
                                                            <a onclick="auditSuccess('${ctx}/activity/threeBillionExchange/auditSuccess/<c:out value="${exchange.id}"/>/3')"
                                                               class="btn btn-success btn-xs edit">
                                                                <i class="fa fa-edit"></i>确认收货
                                                            </a>
                                                        </c:when>
                                                    </c:choose>
                                                </td>
                                            </c:if>
                                        </shiro:hasPermission>
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