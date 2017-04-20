<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@include file="/static/inc/main.inc" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path;
%>
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
    </head>
    <body>

    <div class="page-breadcrumbs">
        <ul class="breadcrumb" style="line-height: 40px;">
            <li>
                <i class="fa fa-home"></i>
                <a href="#">用户管理</a>
            </li>
            <li><a href="#">其他平台用户</a></li>
        </ul>
    </div>
    <!-- /Page Breadcrumb -->
    <!-- Page Header -->
    <div class="page-header position-relative">
        <div class="header-title">
            <h1>其他平台用户</h1>
        </div>
        <!--Header Buttons-->
        <div class="header-buttons">
            <a class="sidebar-toggler" href="#">
                <i class="fa fa-arrows-h"></i>
            </a>
            <a class="refresh" id="refresh-toggler" href="${ctx}/userinfo/otherplatform/list">
                <i  class="glyphicon glyphicon-refresh"></i>
            </a>
            <a class="fullscreen" id="fullscreen-toggler" href="#">
                <i class="glyphicon glyphicon-fullscreen"></i>
            </a>
        </div>
        <!--Header Buttons End-->
    </div>
    <!-- /Page Header -->
    <!-- Page Body -->
    <form action="${ctx}/userinfo/otherplatform/list" method="post" id="listForm" name="listForm">
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
                                            <label class="control-label no-padding-right">真实姓名</label> <input
                                                type="text" name="realname" class="form-control"
                                                placeholder="真实姓名" />
                                            <label class="control-label no-padding-right">手机号</label> <input
                                                type="text" name="mobile" class="form-control"
                                                placeholder="手机号" />
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label no-padding-right">是否注册</label>
                                            <select class="form-control" name="regFlag">
                                                <option value="">全部</option>
                                                <option value="1">已注册</option>
                                                <option value="0">未注册</option>
                                            </select>
                                        </div>

                                        <div class="form-group">
                                            <label class="control-label no-padding-right"></label>注册日期：从
                                            <input type="text" name="startDate" class="form-control"
                                                   tabindex="1" style="padding-left: 50px;" maxlength="40"
                                                   onfocus="WdatePicker({skin:'twoer'})"> 到 <input
                                                type="text" name="endDate" class="form-control" tabindex="1"
                                                style="padding-left: 50px;" maxlength="40"
                                                onfocus="WdatePicker({skin:'twoer'})">
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label no-padding-right">平台来源</label>
                                            <select class="form-control" name="platformId">
                                                <option value="">全部</option>
                                                <c:forEach items="${otherPlatforms}" var="platform">
                                                    <option value="${platform.id}">${platform.name}</option>
                                                </c:forEach>
                                            </select>
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
                            <span class="widget-caption">用户信息管理</span>

                            <div class="widget-buttons">
                                <a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
                                </a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
                            </a>
                            </div>
                        </div>
                        <div class="widget-body">
                            <div class="flip-scroll">
                                <div style="text-align: right; margin-bottom: 10px;">
                                    <shiro:hasPermission name="oplatformuser_list">
                                        <a href="${ctx}/userinfo/otherplatform/toUpload"
                                           class="btn btn-labeled btn-success"> <i
                                                class="btn-label glyphicon glyphicon-envelope"></i>导入
                                        </a>
                                    </shiro:hasPermission>
                                </div>

                                <table class="table table-hover table-striped table-bordered">
                                    <thead style="font-size: 16px; font-weight: bold;">
                                    <tr>
                                        <th width="80" style="text-align: center;">序号</th>
                                        <th>真实姓名</th>
                                        <th>手机号</th>
                                        <th>是否注册</th>
                                        <th>注册时间</th>
                                        <th>平台来源</th>
                                        <th>导入日期</th>
                                    </tr>
                                    </thead>
                                    <tbody style="font-size: 12px;">
                                    <c:forEach items="${page.content}" var="user"
                                               varStatus="status">
                                        <tr>
                                            <td class="table_no" width="80" align="center"></td>
                                            <td>${user.realname}</td>
                                            <td>${user.mobile}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${user.regFlag eq 0}">未注册</c:when>
                                                    <c:when test="${user.regFlag eq 1}">已注册</c:when>
                                                </c:choose>
                                            </td>
                                            <td><fmt:formatDate value="${user.regDate}"
                                                    pattern="yyyy年MM月dd日 HH时mm秒"/></td>
                                            <td>
                                                ${user.otherPlatform.name}
                                            </td>
                                            <td><fmt:formatDate value="${user.createdDate}"
                                                    pattern="yyyy年MM月dd日"/></td>
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