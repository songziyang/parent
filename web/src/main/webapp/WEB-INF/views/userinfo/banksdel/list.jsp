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
        <script type="text/javascript">
            $(document)
                    .ready(
                    function () {
                        var filename = '<c:out value="${fileName}"/>';
                        if (filename != "") {
                            window.location.href = "http://keng.yinduoziben.com/static/download/" + filename;
                            //window.location.href = "http://localhost:8080/static/download/"+ filename;
                        }
                    });

            function exprotExcel(condition) {
                var url = "${ctx}/userinfo/user/listUserExportExcel/" + condition
                window.location.href = url;
            }
        </script>
    </head>
    <body>
        <div class="page-breadcrumbs">
            <ul class="breadcrumb" style="line-height: 40px;">
                <li><i class="fa fa-home"></i> <a href="#">前台用户管理</a></li>
                <li><a href="#">用户银行解绑</a></li>
            </ul>
        </div>
        <!-- /Page Breadcrumb -->
        <!-- Page Header -->
        <div class="page-header position-relative">
            <div class="header-title">
                <h1>用户银行解绑</h1>
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
        <form action="${ctx}/userinfo/banksDel/list" method="post" id="listForm" name="listForm">
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
                                                <label class="control-label no-padding-right">用户名</label>
                                                <input type="text" name="username" class="form-control" placeholder="用户名" />
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label no-padding-right">手机号</label>
                                                <input type="text" name="mobile" class="form-control" placeholder="手机号" />
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label no-padding-right">真实姓名</label>
                                                <input type="text" name="realName" class="form-control" placeholder="真实姓名" />
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label no-padding-right"></label> 申请日期:从
                                                <input type="text" name="startDate" class="form-control"
                                                       tabindex="1" style="padding-left: 50px;" maxlength="40"
                                                       onfocus="WdatePicker({skin:'twoer'})"> 到 <input
                                                    type="text" name="endDate" class="form-control" tabindex="1"
                                                    style="padding-left: 50px;" maxlength="40"
                                                    onfocus="WdatePicker({skin:'twoer'})">
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label no-padding-right">状态</label>
                                                <select class="form-control" name="status">
                                                    <option value="0">待审核</option>
                                                    <option value="1">审核成功</option>
                                                    <option value="2">审核失败</option>
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
                                <span class="widget-caption">用户银行解绑</span>
                                <div class="widget-buttons">
                                    <a href="#" data-toggle="maximize"><i class="fa fa-expand"></i></a>
                                    <a href="#" data-toggle="collapse"><i class="fa fa-minus"></i></a>
                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="flip-scroll">
                                    <table class="table table-hover table-striped table-bordered">
                                        <thead style="font-size: 16px; font-weight: bold;">
                                            <tr>
                                                <th width="80" style="text-align: center;">序号</th>
                                                <th>用户名</th>
                                                <th>手机号</th>
                                                <th>真实姓名</th>
                                                <th>申请时间</th>
                                                <th>状态</th>
                                                <c:if test="${banksDelStatus eq 2}" >
                                                    <th>拒绝原因</th>
                                                </c:if>
                                                <th width="200px" style="text-align: center;">操作</th>
                                            </tr>
                                        </thead>
                                        <tbody style="font-size: 12px;">
                                            <c:forEach items="${page.content}" var="banksDel" varStatus="status">
                                                <tr>
                                                    <td class="table_no" width="80" align="center"></td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${banksDel.user.userLeve.gradeNum eq 1}"><img src="${ctx}/static/lib/images/vip1.png" /></c:when>
                                                            <c:when test="${banksDel.user.userLeve.gradeNum eq 2}"><img src="${ctx}/static/lib/images/vip2.png" /></c:when>
                                                        </c:choose>
                                                        <a href="${ctx}/userinfo/user/listUserInfo/<c:out value='${banksDel.user.id}'/>"
                                                                data-simpletooltip="init" title='<c:out value="${banksDel.user.remark}" />'
                                                                <c:if test="${not empty banksDel.user.remark}">style='color: red;'</c:if>>
                                                            <c:out value="${banksDel.user.username}"/>
                                                        </a>
                                                    </td>
                                                    <td>
                                                        <a href="${ctx}/userinfo/user/listUserInfo/<c:out value='${banksDel.user.id}'/>"
                                                                data-simpletooltip="init" title='<c:out value="${banksDel.user.remark}" />'
                                                                <c:if test="${not empty banksDel.user.remark}">style='color: red;'</c:if>>
                                                            <c:out value="${banksDel.user.mobile}"/>
                                                        </a>
                                                    </td>
                                                    <td>
                                                        <a href="${ctx}/userinfo/user/listUserInfo/<c:out value='${banksDel.user.id}'/>"
                                                                data-simpletooltip="init" title='<c:out value="${banksDel.user.remark}" />'
                                                                <c:if test="${not empty banksDel.user.remark}">style='color: red;'</c:if>>
                                                            <c:out value="${banksDel.user.realName}"/>
                                                        </a>
                                                    </td>
                                                    <td>
                                                        <fmt:formatDate value="${banksDel.createdDate}" pattern="yyyy-MM-dd HH:mm" />
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${banksDel.status eq 0}">待审核</c:when>
                                                            <c:when test="${banksDel.status eq 1}">审核成功</c:when>
                                                            <c:when test="${banksDel.status eq 2}">审核失败</c:when>
                                                        </c:choose>
                                                    </td>
                                                    <c:if test="${banksDelStatus eq 2}" >
                                                        <td><c:out value="${banksDel.reason}" /></td>
                                                    </c:if>
                                                    <td style="text-align: center">

                                                        <a href="${ctx}/userinfo/banksDel/detail/<c:out value='${banksDel.id}' />" class="btn btn-success">
                                                            <c:choose>
                                                                <c:when test="${banksDel.status eq 0}">审核</c:when>
                                                                <c:otherwise>详情</c:otherwise>
                                                            </c:choose>
                                                        </a>
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