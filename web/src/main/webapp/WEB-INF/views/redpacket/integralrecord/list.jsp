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
            margin-bottom: 10px;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            var filename = '<c:out value="${fileName}"/>';
            if (filename != "") {
                window.location.href = "http://keng.yinduoziben.com/static/download/" + filename;
                //window.location.href ="http://localhost:8080/static/download/"+filename;
            }
        });

        function exprotExcel(condition) {
            var url = "${ctx}/redpacket/integralrecord/exportExcel/" + condition
            window.location.href = url;
        }
    </script>
</head>
<body>

<!-- Page Breadcrumb -->
<div class="page-breadcrumbs">
    <ul class="breadcrumb" style="line-height: 40px;">
        <li><i class="fa fa-home"></i> <a href="#">积分商城</a></li>
        <li><a href="#">积分记录</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>积分记录列表</h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#">
            <i class="fa fa-arrows-h"></i>
        </a>
        <a class="refresh" id="refresh-toggler" href="${ctx}/redpacket/integralrecord/listIntegralRecord">
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
<form action="${ctx}/redpacket/integralrecord/listIntegralRecord" method="post" id="listForm" name="listForm">
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
                                        <label class="control-label no-padding-right">用户名</label> <input
                                            type="text" name="username" class="form-control"
                                            placeholder="用户名">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right">手机号</label> <input
                                            type="text" name="mobile" class="form-control"
                                            placeholder="手机号">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right"></label>&nbsp;&nbsp;
                                        日期：从 <input type="text" name="startDate"
                                                    class="form-control" tabindex="1"
                                                    style="padding-left: 50px;" required maxlength="40"
                                                    onfocus="WdatePicker({skin:'twoer'})"> 到 <input
                                            type="text" name="endDate" class="form-control" tabindex="1"
                                            style="padding-left: 50px;" required maxlength="40"
                                            onfocus="WdatePicker({skin:'twoer'})">
                                    </div>
                                    <div class="form-group">
                                        &nbsp;&nbsp; <a href="javascript:searchData();"
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
                        <span class="widget-caption">积分记录列表</span>

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
                                <a href="javascript:exprotExcel('<c:out value="${condition}"/>');"
                                   class="btn btn-labeled btn-success"> <i
                                        class="btn-label glyphicon glyphicon-envelope"></i>导出Excel
                                </a>
                            </div>
                            <table class="table table-hover table-striped table-bordered">
                                <thead style="font-size: 16px; font-weight: bold;">
                                <tr>
                                    <th style="text-align: center;">序号</th>
                                    <th>用户名</th>
                                    <th>手机号</th>
                                    <th>来源去向</th>
                                    <th>积分</th>
                                    <th>积分余额</th>
                                    <th>操作时间</th>
                                </tr>
                                </thead>
                                <tbody style="font-size: 12px;">
                                <c:forEach items="${page.content}" var="integral" varStatus="status">
                                    <tr>
                                        <td class="table_no" align="center"></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${integral.user.userLeve.gradeNum eq 1}"><img src="${ctx}/static/lib/images/vip1.png" /></c:when>
                                                <c:when test="${integral.user.userLeve.gradeNum eq 2}"><img src="${ctx}/static/lib/images/vip2.png" /></c:when>
                                            </c:choose>
                                            <a href="${ctx}/userinfo/user/listUserInfo/<c:out value='${integral.user.id}'/>"
                                               data-simpletooltip="init"
                                               title='<c:out value="${integral.user.remark}" />'
                                               <c:if test="${not empty integral.user.remark}">style='color: red;'</c:if>>
                                                <c:out value="${integral.user.username}"/>
                                            </a>
                                        </td>
                                        <td>
                                            <a href="${ctx}/userinfo/user/listUserInfo/<c:out value='${integral.user.id}'/>"
                                               data-simpletooltip="init"
                                               title='<c:out value="${integral.user.remark}" />'
                                               <c:if test="${not empty integral.user.remark}">style='color: red;'</c:if>>
                                                <c:out value="${integral.user.mobile}"/>
                                            </a>
                                        </td>
                                        <td><c:out value="${integral.fundflow}"/></td>
                                        <td>
                                            <c:if test="${integral.type eq 2 }">
                                                <i class="fa fa-minus red"></i>
                                            </c:if>
                                            <c:if test="${integral.type eq 1 }">
                                                <i class="fa  fa-plus blue"></i>
                                            </c:if>
                                            <c:out value="${integral.integral}"/>
                                        </td>
                                        <td><c:out value="${integral.balance}"/></td>
                                        <td><fmt:formatDate value="${integral.createdDate}"
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