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
        <li><i class="fa fa-home"></i> <a href="#">信息管理</a></li>
        <li><a href="#">验证码管理</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>验证码管理</h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
        </a> <a class="refresh" id="refresh-toggler" href="${ctx}/infoMessage/authCode/listAuthCode"> <i
            class="glyphicon glyphicon-refresh"></i>
    </a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
            class="glyphicon glyphicon-fullscreen"></i>
    </a>
    </div>
    <!--Header Buttons End-->
</div>
<!-- /Page Header -->
<!-- Page Body -->
<form action="${ctx}/infoMessage/authCode/listAuthCode" method="post"
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
                                        <label class="control-label no-padding-right">手机号</label>
                                        <input type="text" name="name" class="form-control" placeholder="手机号">
                                    </div>

                                    <div class="form-group" style="margin-left:25px;">
                                        <a href="javascript:searchData();"
                                           class="btn btn-labeled btn-blue">
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
                        <span class="widget-caption">验证码管理</span>

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
                                    <th width="80" style="text-align: center;">序号</th>
                                    <th>认证类型</th>
                                    <th>手机号</th>
                                    <th>验证码</th>
                                    <th>类型</th>
                                    <th>添加时间</th>
                                    <th>过期时间</th>
                                </tr>
                                </thead>
                                <tbody style="font-size: 12px;">
                                <c:forEach items="${page.content}" var="authCode"
                                           varStatus="status">
                                    <tr>
                                        <td class="table_no" width="80" align="center"></td>
                                        <td>
                                            <c:choose>
                                            <c:when test="${authCode.type eq 1}">
                                            注册验证
                                            </c:when>
                                            <c:when test="${authCode.type eq 2}">
                                            提现申请
                                            </c:when>
                                            <c:when test="${authCode.type eq 3}">
                                            活期宝赎回
                                            </c:when>
                                            <c:when test="${authCode.type eq 4}">
                                            定存宝赎回
                                            </c:when>
                                            <c:when test="${authCode.type eq 5}">
                                            找回密码
                                            </c:when>
                                            <c:when test="${authCode.type eq 6}">
                                            登录验证
                                            </c:when>
                                            </c:choose>
                                        <td><c:out value="${authCode.name}"/></td>
                                        <td><c:out value="${authCode.content}"/></td>
                                        <td><c:out value="${authCode.status == 0 ? '未验证':'已验证'}"/></td>
                                        <td><fmt:formatDate value="${authCode.createDate}"
                                                            pattern="yyyy年MM月dd日 HH时mm分"/></td>
                                        <td><fmt:formatDate value="${authCode.expireDate}"
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