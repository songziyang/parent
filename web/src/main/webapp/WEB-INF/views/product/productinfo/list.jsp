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

        function deleteProductInfo(url) {
            bootbox.dialog({
                message: "您确定要删除该产品信息吗？",
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

        function emptyCurrent(url) {
            bootbox.dialog({
                message: "您确定要清空该活期宝剩余份数吗？",
                title: "清空提示",
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
        <li><a href="#">产品信息</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>产品信息列表</h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#">
            <i class="fa fa-arrows-h"></i>
        </a>
        <a class="refresh" id="refresh-toggler" href="${ctx}/product/productInfo/list">
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
<form action="${ctx}/product/productInfo/list" method="post" id="listForm" name="listForm">
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
                                        <label class="control-label no-padding-right">产品名称</label>
                                        <input type="text" name="name" class="form-control" placeholder="产品名称">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right">使用状态</label>
                                        <select class="form-control" name="status" style="width: 120px;">
                                            <option value="-1">全部</option>
                                            <option value="0">使用中</option>
                                            <option value="1">停用</option>
                                        </select>
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
                        <span class="widget-caption">产品信息列表</span>

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
                                <shiro:hasPermission name="productinfo_create">
                                    <a class="btn btn-labeled btn-success" href="${ctx}/product/productInfo/create">
                                        <i class="btn-label glyphicon glyphicon-plus"></i>添加
                                    </a>
                                </shiro:hasPermission>
                            </div>
                            <table class="table table-hover table-striped table-bordered">
                                <thead style="font-size: 16px; font-weight: bold;">
                                <tr>
                                    <th width="80" style="text-align: center;">序号</th>
                                    <th>产品名称</th>
                                    <th>产品类型</th>
                                    <th>产品类别</th>
                                    <th>天数</th>
                                    <th>期数</th>
                                    <th>年化利率(%)</th>
                                    <th>活动利率(%)</th>
                                    <th>发布金额</th>
                                    <th>剩余金额</th>
                                    <th>发布方式</th>
                                    <th>创建时间</th>
                                    <th width="200px" style="text-align: center;">操作</th>
                                </tr>
                                </thead>
                                <tbody style="font-size: 12px;">
                                <c:forEach items="${page.content}" var="productInfo" varStatus="status">
                                    <tr>
                                        <td class="table_no" width="80" align="center"></td>
                                        <td><c:out value="${productInfo.name}"/></td>
                                        <td><c:out value="${productInfo.type.type}"/></td>
                                        <td>
                                            <c:choose>
                                            <c:when test="${productInfo.productClas eq 1}">银多类别</c:when>
                                            <c:when test="${productInfo.productClas eq 2}">机构类别</c:when>
                                            </c:choose>
                                        <td><c:out value="${productInfo.cylcleDays}"/></td>
                                        <td><c:out value="${productInfo.stage}"/></td>
                                        <td><c:out value="${productInfo.interestRate}"/></td>
                                        <td><c:out value="${empty productInfo.activityRate  ? 0.00 : productInfo.activityRate }"/></td>
                                        <td><c:out value="${productInfo.funds}"/></td>
                                        <td><c:out value="${productInfo.surplus}"/></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${productInfo.createWay eq 0}">手动</c:when>
                                                <c:when test="${productInfo.createWay eq 1}">自动</c:when>
                                            </c:choose>
                                        </td>
                                        <td><fmt:formatDate value="${productInfo.createdDate}"
                                                            pattern="yyyy年MM月dd日 HH时mm分"/></td>
                                        <td width="200px" align="center">
                                            <shiro:hasPermission name="productinfo_edit">
                                                <%--定存宝或新年福利包--%>
                                                <c:if test="${productInfo.type.id eq 2 || productInfo.type.id eq 7}">
                                                    <a href='${ctx}/product/productInfo/edit/<c:out value="${productInfo.id}" />'
                                                       class="btn btn-info btn-xs edit">
                                                        <i class="fa fa-edit"></i>复制
                                                    </a>
                                                </c:if>
                                                <%--活期宝--%>
                                                <%--<c:if test="${productInfo.type.id eq 1}">--%>
                                                    <%--<a href='javascript:;' class="btn btn-success btn-xs edit"--%>
                                                        <%--onclick="emptyCurrent('${ctx}/product/productInfo/emptyCurrent/<c:out value="${productInfo.id}" />');">--%>
                                                        <%--<i class="fa fa-exclamation-circle"></i>清空--%>
                                                    <%--</a>--%>
                                                <%--</c:if>--%>

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