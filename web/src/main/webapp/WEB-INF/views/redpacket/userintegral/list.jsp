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
            margin-bottom: 10px;
        }
    </style>
    <script type="text/javascript">

        /**
         * 多选确认发货
         * @param urls
         */
        function ensureSendgoods(urls) {
            if (hasChecked(listForm) == false) {
               Notify('请先选择要确认发货的记录！', 'top-left', '5000', 'warning', 'fa-warning', true);
               return;
            }
            bootbox.dialog({
                message: "您确定要确认发货选中的记录吗？",
                title: "确认发货提示",
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
                //window.location.href ="http://localhost:8080/static/download/"+filename;
            }
        });

        function exprotExcel(condition) {
            var url = "${ctx}/redpacket/userintegral/exportExcel/" + condition
            window.location.href = url;
        }
    </script>
</head>
<body>

<!-- Page Breadcrumb -->
<div class="page-breadcrumbs">
    <ul class="breadcrumb" style="line-height: 40px;">
        <li><i class="fa fa-home"></i> <a href="#">积分商城</a></li>
        <li><a href="#">积分兑换</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>积分兑换列表</h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#">
            <i class="fa fa-arrows-h"></i>
        </a>
        <a class="refresh" id="refresh-toggler" href="${ctx}/redpacket/userintegral/listUserIntegral">
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
<form action="${ctx}/redpacket/userintegral/listUserIntegral" method="post" id="listForm" name="listForm">
    <div class="page-body">
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
                                        <label class="control-label no-padding-right">用户名</label> <input
                                            type="text" name="username" class="form-control"
                                            placeholder="用户名">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right">手机号</label> <input
                                            type="text" name="mobile" class="form-control"
                                            placeholder="手机号">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right">类型</label> <select
                                            class="form-control" name="type" style="width: 200px;">
                                        <option value="">全部</option>
                                        <option value="1">商品</option>
                                        <option value="2">活期加息券</option>
                                        <option value="3">定存加息券</option>
                                    </select>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right">订单状态</label>
                                        <select class="form-control" name="status" style="width: 200px;">
                                            <option value="1">待处理</option>
                                            <option value="2">已发货</option>
                                            <option value="3">已收货</option>
                                            <option value="4">已取消</option>
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
                                        &nbsp;&nbsp; <a href="javascript:searchData();"
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
                        <span class="widget-caption">积分兑换列表</span>

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
                                    <shiro:hasPermission name="userintegral_list">
                                        <a class="btn btn-labeled btn-info"
                                                href="javascript:ensureSendgoods('${ctx}/redpacket/userintegral/auditSuccess/2');">
                                            <i class="btn-label fa fa-edit"></i>
                                            确认发货
                                        </a>
                                    </shiro:hasPermission>
                                </c:if>
                                <c:if test="${status eq 2}">
                                    <shiro:hasPermission name="userintegral_list">
                                        <a class="btn btn-labeled btn-info"
                                           href="javascript:ensureSendgoods('${ctx}/redpacket/userintegral/auditSuccess/3');">
                                            <i class="btn-label fa fa-edit"></i>
                                            确认收货
                                        </a>
                                    </shiro:hasPermission>
                                </c:if>
                            </div>
                            <table class="table table-hover table-striped table-bordered">
                                <thead style="font-size: 16px; font-weight: bold;">
                                <tr>
                                    <th style="text-align: center;">序号</th>
                                    <th>用户名</th>
                                    <th>手机号</th>
                                    <th>类型</th>
                                    <th>订单状态</th>
                                    <th>产品</th>
                                    <th>兑换数量</th>
                                    <th>使用积分</th>
                                    <th>收货人</th>
                                    <th>收货手机号</th>
                                    <th>收货地址</th>
                                    <th>备注</th>
                                    <th>兑换时间</th>
                                    <th>操作用户</th>
                                    <th width="20"><label> <input type="checkbox">
                                        <span class="text"></span>
                                    </label></th>
                                    <th width="200px" style="text-align: center;">操作</th>
                                </tr>
                                </thead>
                                <tbody style="font-size: 12px;">
                                <c:forEach items="${page.content}" var="integral" varStatus="status">
                                    <tr>
                                        <td class="table_no" align="center"></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${integral.user.userLeve.gradeNum eq 1}"><img src="${ctx}/static/lib/images/vip1.png" /></c:when>
                                                <c:when test="${integral.user.userLeve.gradeNum eq 2}"><img src="${ctx}/static/lib/images/vip2.png" /></c:when>
                                            </c:choose>
                                            <c:out value="${integral.user.username}"/>
                                        </td>
                                        <td><c:out value="${integral.user.mobile}"/></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${integral.type eq 1}">商品</c:when>
                                                <c:when test="${integral.type eq 2}">活期加息券</c:when>
                                                <c:when test="${integral.type eq 3}">定存加息券</c:when>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${integral.status eq 1}">待处理</c:when>
                                                <c:when test="${integral.status eq 2}">已发货</c:when>
                                                <c:when test="${integral.status eq 3}">已收货</c:when>
                                                <c:when test="${integral.status eq 4}">已取消</c:when>
                                            </c:choose>
                                        </td>
                                        <td><c:out value="${integral.productName}"/></td>
                                        <td><c:out value="${empty integral.number? 1: integral.number}" /></td>
                                        <td><c:out value="${integral.integral}"/></td>
                                        <td><c:out value="${integral.realname}"/></td>
                                        <td><c:out value="${integral.mobile}"/></td>
                                        <td><c:out value="${integral.address}"/></td>
                                        <td><c:out value="${integral.remark}"/></td>
                                        <td><fmt:formatDate value="${integral.createdDate}" pattern="yyyy年MM月dd日"/></td>
                                        <td>
                                            <c:out value="${empty integral.operationUser? '': integral.operationUser.username}" />
                                        </td>
                                        <td width="20">
                                            <label>
                                                <input type="checkbox" name="ids" value='<c:out value="${integral.id}" />'>
                                                <span class="text"></span>
                                            </label>
                                        </td>
                                        <td width="200px" align="center">
                                            <shiro:hasPermission name="userintegral_list">
                                            <c:choose>
                                                <c:when test="${integral.status eq 1}">
                                                    <a onclick="auditSuccess('${ctx}/redpacket/userintegral/auditSuccess/<c:out value="${integral.id}"/>/2')"
                                                       class="btn btn-success btn-xs edit">
                                                        <i class="fa fa-edit"></i>确认发货
                                                    </a>
                                                    <a onclick="auditFailure('${ctx}/redpacket/userintegral/auditFailure/<c:out value="${integral.id}"/>')"
                                                       class="btn btn-danger btn-xs edit">
                                                        <i class="fa fa-edit"></i>取消订单
                                                    </a>
                                                </c:when>
                                                <c:when test="${integral.status eq 2}">
                                                    <a onclick="auditSuccess('${ctx}/redpacket/userintegral/auditSuccess/<c:out value="${integral.id}"/>/3')"
                                                       class="btn btn-success btn-xs edit">
                                                        <i class="fa fa-edit"></i>确认收货
                                                    </a>
                                                    <a onclick="auditSuccess('${ctx}/redpacket/userintegral/underhandle/<c:out value="${integral.id}"/>')"
                                                       class="btn btn-danger btn-xs edit">
                                                        <i class="fa fa-edit"></i>待处理
                                                    </a>
                                                </c:when>
                                            </c:choose>
                                            </shiro:hasPermission>
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
    </div>
</form>
<%@include file="/static/inc/footer.inc" %>
</body>
</html>