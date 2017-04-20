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
    <script src="${ctx}/static/lib/simpletooltip/src/js/simpletooltip.js" ></script>
    <style type="text/css">
        .form-inline .form-group {
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<!-- Page Breadcrumb -->
<div class="page-breadcrumbs">
    <ul class="breadcrumb" style="line-height: 40px;">
        <li><i class="fa fa-home"></i> <a href="#">产品管理</a></li>
        <li><a href="#">转转赚产品</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>转转赚列表</h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i></a>
        <a class="refresh" id="refresh-toggler"
           href="${ctx}/product/structure/list"><i
                class="glyphicon glyphicon-refresh"></i></a> <a class="fullscreen"
            id="fullscreen-toggler" href="#"> <i
            class="glyphicon glyphicon-fullscreen"></i></a>
    </div>
    <!--Header Buttons End-->
</div>
<!-- /Page Header -->
<!-- Page Body -->
<form action="${ctx}/product/structure/list" method="post"
      id="listForm" name="listForm">
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
                                    <a href="#"></a> <a href="#" data-toggle="collapse"> <i
                                        class="fa fa-plus blue"></i>
                                </a>
                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="form-inline" role="form">
                                    <div class="form-group">
                                        <label class="control-label no-padding-right">产品名称</label>
                                        <input type="text" name="name" class="form-control"
                                            placeholder="产品名称"/>
                                    </div>
                                    <div class="form-group">
                                        <a href="javascript:searchData();" class="btn btn-labeled btn-blue">
                                            <i class="btn-label fa fa-search"></i>查询
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="widget">
                    <div class="widget-header  with-footer">
                        <span class="widget-caption">转转赚列表</span>

                        <div class="widget-buttons">
                            <a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i></a>
                            <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i></a>
                        </div>
                    </div>
                    <div class="widget-body">
                        <div class="flip-scroll">
                            <div style="text-align: right; margin-bottom: 10px;">
                                <shiro:hasPermission name="structure_create">
                                    <a class="btn btn-labeled btn-success"  href="${ctx}/product/structure/create">
                                        <i class="btn-label glyphicon glyphicon-plus"></i>
                                        添加
                                    </a>
                                </shiro:hasPermission>
                            </div>
                            <table class="table table-hover table-striped table-bordered">
                                <thead style="font-size: 16px; font-weight: bold;">
                                <tr>
                                    <th width="80" style="text-align: center;">序号</th>
                                    <th>产品名称</th>
                                    <th>产品期数</th>
                                    <th>开放份数</th>
                                    <th>剩余份数</th>
                                    <th>最小份额</th>
                                    <th>普通用户最大限额</th>
                                    <th>VIP最大限额</th>
                                    <th>利率(%)</th>
                                    <th>助力加息限制(%)</th>
                                    <th>申购开始日期</th>
                                    <th width="200px" style="text-align: center;">操作</th>
                                </tr>
                                </thead>
                                <tbody style="font-size: 12px;">
                                <c:forEach items="${page.content}" var="structure"  varStatus="status">
                                    <tr>
                                        <td class="table_no" width="80" align="center"></td>
                                        <td>${structure.name}</td>
                                        <td>${structure.issue}</td>
                                        <td>${structure.copies}</td>
                                        <td>${structure.remainingCopies}</td>
                                        <td>${structure.minCopies}</td>
                                        <td>${structure.maxCopies}</td>
                                        <td>${structure.vipMaxCopies}</td>
                                        <td>${structure.apr}</td>
                                        <td>${structure.helpMaxApr}</td>
                                        <td><fmt:formatDate value="${structure.sDate}" pattern="yyyy-MM-dd"/></td>
                                        <td>
                                            <shiro:hasPermission name="structure_edit">
                                                <a href='${ctx}/product/structure/edit/<c:out value="${structure.id}" />'
                                                        class="btn btn-info btn-xs edit"><i
                                                        class="fa fa-edit"></i>编辑</a>
                                                &nbsp;&nbsp;
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="structure_list">
                                                <a class="btn btn-info btn-xs edit" href='${ctx}/product/structuredeal/list?structureId=<c:out value="${structure.id}" />'>
                                                    <i class="fa fa-edit"></i> 交易</a>
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
