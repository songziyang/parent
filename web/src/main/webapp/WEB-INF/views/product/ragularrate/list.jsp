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

        function disableRate(url) {
            bootbox.dialog({
                message: "您确定要删除该活期宝份数吗？",
                title: "删除提示",
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
    </script>
</head>
<body>

<!-- Page Breadcrumb -->
<div class="page-breadcrumbs">
    <ul class="breadcrumb" style="line-height: 40px;">
        <li><i class="fa fa-home"></i> <a href="#">产品管理</a></li>
        <li><a href="#">定存宝份数设置</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>定存宝份数列表</h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#">
            <i class="fa fa-arrows-h"></i>
        </a>
        <a class="refresh" id="refresh-toggler" href="">
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
<form action="${ctx}/product/ragularRate/list" method="post" id="listForm" name="listForm">
    <div class="page-body">
        <div class="row">
            <div class="col-xs-12 col-md-12">
                <div class="widget">
                    <div class="widget-header  with-footer">
                        <span class="widget-caption">定存宝份数列表</span>

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
                                <shiro:hasPermission name="ragularrate_create">
                                    <a class="btn btn-labeled btn-success" href="${ctx}/product/ragularRate/create">
                                        <i class="btn-label glyphicon glyphicon-plus"></i>添加
                                    </a>
                                </shiro:hasPermission>
                            </div>
                            <table class="table table-hover table-striped table-bordered">
                                <thead style="font-size: 16px; font-weight: bold;">
                                <tr>
                                    <th width="80" style="text-align: center;">序号</th>
                                    <th>发布利率(%)</th>
                                    <th>活动利率(%)</th>
                                    <th>份数</th>
                                    <th>天数</th>
                                    <th>状态</th>
                                    <th>是否作为发布份数</th>
                                    <th>创建时间</th>
                                    <th width="200px" style="text-align: center;">操作</th>
                                </tr>
                                </thead>
                                <tbody style="font-size: 12px;">
                                <c:forEach items="${page.content}" var="rate" varStatus="status">
                                    <tr>
                                        <td class="table_no" width="80" align="center"></td>
                                        <td>
                                            <c:out value="${rate.ragularRate}"/>
                                        </td>
                                        <td>
                                            <c:out value="${empty rate.activityRate ? 0.00 : rate.activityRate }"/>
                                        </td>
                                        <td><c:out value="${empty rate.copies? 0:rate.copies}"/></td>
                                        <td>
                                            <c:out value="${rate.days}"/>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${rate.status eq 0}">使用中</c:when>
                                                <c:when test="${rate.status eq 1}">已删除</c:when>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${rate.releaseType eq 0}">否</c:when>
                                                <c:otherwise>是</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td><fmt:formatDate value="${rate.createdDate}"
                                                            pattern="yyyy年MM月dd日 HH时mm分"/></td>
                                        <td width="200px" align="center">
                                            <shiro:hasPermission name="ragularrate_edit">
                                                <a href='${ctx}/product/ragularRate/edit/<c:out value="${rate.id}" />'
                                                   class="btn btn-info btn-xs edit">
                                                    <i class="fa fa-edit"></i>编辑
                                                </a>
                                                &nbsp;&nbsp;
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