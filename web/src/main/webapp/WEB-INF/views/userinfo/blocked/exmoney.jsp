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
    <script src="${ctx}/static/lib/simpletooltip/src/js/simpletooltip.js" ></script>
    <style type="text/css">
        .form-inline .form-group {
            margin-bottom: 10px;
        }
    </style>
</head>
<body>

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
        <h1>体验金冻结查询</h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
        </a> <a class="refresh" id="refresh-toggler"
                href="${ctx}/userinfo/usermoneyinfo/findBlokedlogByUserID/${userId}"> <i
            class="glyphicon glyphicon-refresh"></i>
    </a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
            class="glyphicon glyphicon-fullscreen"></i>
    </a>
    </div>
    <!--Header Buttons End-->
</div>
<!-- /Page Header -->
<!-- Page Body -->
<form action="${ctx}/userinfo/user/listUser" method="post"
      id="listForm" name="listForm">
    <div class="page-body">
        <div class="row">
            <div class="col-xs-12 col-md-12">

                <div class="widget">
                    <div class="widget-header  with-footer">
                        <span class="widget-caption">体验金冻结查询</span>

                        <div class="widget-buttons">
                            <a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
                            </a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
                        </a>
                        </div>
                    </div>
                    <div class="widget-body">
                        <div class="flip-scroll">
                            <table class="table table-hover table-striped table-bordered">
                                <thead style="font-size: 16px; font-weight: bold;">
                                <tr>
                                    <th>用户名</th>
                                    <th>手机号</th>
                                    <th>操作金额</th>
                                    <th>操作类型</th>
                                    <th>操作时间</th>
                                </tr>
                                </thead>
                                <tbody style="font-size: 12px;">
                                <c:forEach items="${blokedlogLst}" var="blokedlog"  varStatus="status"  >
                                    <tr>
                                        <td>
                                            <c:choose>
                                                <c:when test="${user.userLeve.gradeNum eq 1}"><img src="${ctx}/static/lib/images/vip1.png" /></c:when>
                                                <c:when test="${user.userLeve.gradeNum eq 2}"><img src="${ctx}/static/lib/images/vip2.png" /></c:when>
                                            </c:choose>
                                            <a href="${ctx}/userinfo/user/listUserInfo/<c:out value='${user.id}'/>"
                                               data-simpletooltip="init" title='<c:out value="${user.remark}" />'
                                               <c:if test="${not empty user.remark}">style='color: red;'</c:if>>
                                                <c:out value="${user.username}"/>
                                            </a>
                                        </td>
                                        <td>
                                            <a href="${ctx}/userinfo/user/listUserInfo/<c:out value='${user.id}'/>"
                                               data-simpletooltip="init" title='<c:out value="${user.remark}" />'
                                               <c:if test="${not empty user.remark}">style='color: red;'</c:if>>
                                                <c:out value="${user.mobile}"/>
                                            </a>
                                        </td>
                                        <td> <c:out value="${blokedlog.fund}"/></td>
                                        <td>
                                            <c:if test="${blokedlog.linkType eq 3 }">
                                                活期宝预投
                                            </c:if>
                                        </td>
                                        <td><fmt:formatDate value="${blokedlog.createDate}" pattern="yyyy年MM月dd日 HH时mm分"/></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
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