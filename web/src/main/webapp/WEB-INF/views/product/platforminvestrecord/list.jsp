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
</head>
<body>

<!-- Page Breadcrumb -->
<div class="page-breadcrumbs">
    <ul class="breadcrumb" style="line-height: 40px;">
        <li><i class="fa fa-home"></i> <a href="#">统计管理</a></li>
        <li><a href="#">平台债权记录</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>平台债权记录列表</h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#">
            <i class="fa fa-arrows-h"></i>
        </a>
        <a class="refresh" id="refresh-toggler" href="${ctx}/platform/investrecord/list">
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
<form action="${ctx}/platform/investrecord/list" method="post" id="listForm" name="listForm">
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
                                        <label class="control-label no-padding-right">操作类型</label>
                                        <select class="form-control" name="type" style="width: 200px;">
                                            <option value="-1">全部</option>
                                            <option value="1">入账</option>
                                            <option value="2">出账</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right">链接类型</label>
                                        <select class="form-control" name="linkType" style="width: 200px;">
                                            <option value="-1">全部</option>
                                            <option value="1">充值</option>
                                            <option value="2">购买债权</option>
                                            <option value="3">售出债权</option>
                                            <option value="10">活期购买</option>
                                            <option value="11">活期赎回</option>
                                            <option value="20">定存购买</option>
                                            <option value="21">定存售出</option>
                                            <option value="30">自由存购买</option>
                                            <option value="31">自由存到期</option>
                                            <option value="40">美利金融购买</option>
                                            <option value="41">美利金融到期</option>
                                            <option value="50">转转赚购买</option>
                                            <option value="51">转转赚到期</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right"></label>&nbsp;&nbsp;
                                        操作时间：从
                                        <input type="text" name="startTime"
                                               class="form-control" tabindex="1"
                                               style="padding-left: 20px;" required maxlength="40"
                                               onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd HH:mm'})"> 到
                                        <input type="text" name="endTime" class="form-control" tabindex="1"
                                               style="padding-left: 20px;" required maxlength="40"
                                               onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd HH:mm'})">
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
                        <span class="widget-caption">平台债权记录列表</span>

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
                                        <th>操作类型</th>
                                        <th>操作金额</th>
                                        <th>用户名称</th>
                                        <th>用户手机</th>
                                        <th>链接类型</th>
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
                                        <td><c:out value="${record.invest}"/></td>
                                        <td><c:out value="${record.user.username}"/></td>
                                        <td><c:out value="${record.user.mobile}"/></td>
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