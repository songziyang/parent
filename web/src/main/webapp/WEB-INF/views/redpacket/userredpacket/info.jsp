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
    <link href="${ctx}/static/lib/simpletooltip/src/css/simpletooltip.min.css" rel="stylesheet"/>
    <script src="${ctx}/static/lib/simpletooltip/src/js/simpletooltip.js" ></script>
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
        <li><i class="fa fa-home"></i> <a href="#">红包管理</a></li>
        <li><a href="#">加息券发放</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>发放用户列表</h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#">
            <i class="fa fa-arrows-h"></i>
        </a>
        <a class="refresh" id="refresh-toggler" href="${ctx}/redpacket/userRedpacket/${redpacketId}">
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
<form action="${ctx}/redpacket/userRedpacket/list" method="post" id="listForm" name="listForm">
    <div class="page-body">
        <div class="row">
            <div class="col-xs-12 col-md-12">
                <div class="widget">
                    <div class="widget-header  with-footer">
                        <span class="widget-caption">发放用户列表</span>
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
                            <div style="text-align: right; margin-bottom: 10px;">
                                <a href="${ctx}/redpacket/userRedpacket/list" class="btn btn-labeled btn-blue">
                                    <i class="btn-label glyphicon glyphicon-arrow-left"></i>返回
                                </a>
                            </div>
                            <table class="table table-hover table-striped table-bordered">
                                <thead style="font-size: 16px; font-weight: bold;">
                                    <tr>
                                        <th width="80" style="text-align: center;">序号</th>
                                        <th>红包名称</th>
                                        <th>用户名称</th>
                                        <th>活动开始日期</th>
                                        <th>活动结束日期</th>
                                        <th>投资有效天数</th>
                                        <th>状态</th>
                                    </tr>
                                </thead>
                                <tbody style="font-size: 12px;">
                                    <c:forEach items="${page.content}" var="userRp" varStatus="status">
                                        <tr>
                                            <td class="table_no" width="80" align="center"></td>
                                            <td><c:out value="${userRp.redpacketName}" /></td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${userRp.user.userLeve.gradeNum eq 1}"><img src="${ctx}/static/lib/images/vip1.png" /></c:when>
                                                    <c:when test="${userRp.user.userLeve.gradeNum eq 2}"><img src="${ctx}/static/lib/images/vip2.png" /></c:when>
                                                </c:choose>
                                                <a href="${ctx}/userinfo/user/listUserInfo/<c:out value='${userRp.user.id}'/>"
                                                   data-simpletooltip="init" title='<c:out value="${userRp.user.remark}" />'
                                                   <c:if test="${not empty userRp.user.remark}">style='color: red;'</c:if>>
                                                    <c:out value="${userRp.user.username}" />
                                                </a>
                                            </td>
                                            <td>
                                                <fmt:formatDate value="${userRp.beginDate}"  pattern="yyyy年MM月dd日" />
                                            </td>
                                            <td>
                                                <fmt:formatDate value="${userRp.finishDate}"  pattern="yyyy年MM月dd日" />
                                            </td>
                                            <td><c:out value="${userRp.investDays}" /></td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${userRp.status eq 1}">未使用</c:when>
                                                    <c:when test="${userRp.status eq 2}">已冻结</c:when>
                                                    <c:when test="${userRp.status eq 3}">已使用</c:when>
                                                    <c:when test="${userRp.status eq 4}">已过期</c:when>
                                                    <c:when test="${userRp.status eq 5}">现金领取</c:when>
                                                </c:choose>
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