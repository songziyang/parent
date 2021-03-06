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
    <style type="text/css">
        .form-inline .form-group {
            margin-bottom: 10;
        }
    </style>
    <script type="text/javascript">
        function auditSuccess(url){
            bootbox.dialog({
                message: "您确定要审核成功吗？",
                title: "审核提示",
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
                        callback: function () { }
                    }
                }
            });
        }
        function auditFailure(url){
            bootbox.dialog({
                message: "您确定要审核失败吗？",
                title: "审核提示",
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
                        callback: function () { }
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
        <li><a href="#">民生代付审核</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>审核列表</h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#">
            <i class="fa fa-arrows-h"></i>
        </a>
        <a class="refresh" id="refresh-toggler" href="${ctx}/userwithdraw/payManualAudit/list">
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
<form action="${ctx}/userwithdraw/payManualAudit/list" method="post" id="listForm" name="listForm">
    <div class="page-body">
        <div class="row">
            <div class="col-xs-12 col-md-12">
                <div class="widget">
                    <div class="widget-header  with-footer">
                        <span class="widget-caption">审核列表</span>
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
                                        <th>收款人</th>
                                        <th>收款人账号</th>
                                        <th>收款银行名称</th>
                                        <th>身份证号码</th>
                                        <th>银行预留手机号</th>
                                        <th>交易金额</th>
                                        <th>状态</th>
                                        <th>创建时间</th>
                                        <th width="200px" style="text-align: center;">操作</th>
                                    </tr>
                                </thead>
                                <tbody style="font-size: 12px;">
                                    <c:forEach items="${page.content}" var="record" varStatus="status">
                                        <tr>
                                            <td class="table_no" width="80" align="center"></td>
                                            <td><c:out value="${record.accountName}" /></td>
                                            <td><c:out value="${record.accountNo}" /></td>
                                            <td><c:out value="${record.bankName}" /></td>
                                            <td><c:out value="${record.transCardId}"/></td>
                                            <td><c:out value="${record.transMobile}"/></td>
                                            <td><c:out value="${record.tranAmt}" /></td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${record.status eq 1}">审核中</c:when>
                                                    <c:when test="${record.status eq 2}">处理中</c:when>
                                                    <c:when test="${record.status eq 3}">审核失败</c:when>
                                                    <c:when test="${record.status eq 4}">代付成功</c:when>
                                                    <c:when test="${record.status eq 5}">代付失败</c:when>
                                                </c:choose>
                                            </td>
                                            <td><fmt:formatDate value="${record.createdDate}"  pattern="yyyy年MM月dd日 HH时mm分" /></td>
                                            <td width="200px" align="center">
                                                <shiro:hasPermission name="paymanualaudit_operate">
                                                    <a onclick="auditSuccess('${ctx}/userwithdraw/payManualAudit/auditSuccess/<c:out value="${record.id}" />')" class="btn btn-success btn-xs edit">
                                                        <i class="fa fa-edit"></i>审核成功
                                                    </a>
                                                    &nbsp;&nbsp;
                                                    <a onclick="auditFailure('${ctx}/userwithdraw/payManualAudit/auditFailure/<c:out value="${record.id}" />')" class="btn btn-danger btn-xs edit">
                                                        <i class="fa fa-edit"></i>审核失败
                                                    </a>
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
<%@include file="/static/inc/footer.inc"%>
</body>
</html>