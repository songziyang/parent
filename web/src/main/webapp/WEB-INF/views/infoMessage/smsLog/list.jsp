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
        <li><a href="#">信息发送查询</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>信息发送查询列表</h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
        </a> <a class="refresh" id="refresh-toggler" href="${ctx}/infoMessage/smsLog/listSmsLog"> <i
            class="glyphicon glyphicon-refresh"></i>
    </a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
            class="glyphicon glyphicon-fullscreen"></i>
    </a>
    </div>
    <!--Header Buttons End-->
</div>
<!-- /Page Header -->
<div class="page-body">
    <!--开始 databox  -->
    <div class="row">
        <div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
            <div class="databox radius-bordered databox-shadowed databox-graded">
                <div class="databox-left <c:if test="${overage>500}">bg-green</c:if><c:if test="${overage<=500&&overage>200}">bg-yellow</c:if><c:if test="${overage<=200 }">bg-red</c:if>">
                    <div class="databox-piechart">
                        <div id="my_pre1" data-toggle="easypiechart" class="easyPieChart"
                             data-barcolor="#fff" data-linecap="butt" data-percent="${surplusatio}"
                             data-animate="800" data-linewidth="3" data-size="47"
                             data-trackcolor="<c:if test="${overage>500}">#aadc95</c:if><c:if test="${overage<=500&&overage>200}">#fee29f</c:if><c:if test="${overage<=200 }">#fa8872</c:if>">
                            <%--<span class="white font-90">${surplusatio}%</span>--%>
                        </div>
                    </div>
                </div>
                <div class="databox-right">
                    <span class="databox-number <c:if test="${overage>500}">green</c:if><c:if test="${overage<=500&&overage>200}">yellow</c:if><c:if test="${overage<=200 }">red</c:if>">${overage}</span>

                    <div class="databox-text darkgray">剩余数量</div>
                    <div class="databox-stat radius-bordered <c:if test="${overage>500}">green</c:if><c:if test="${overage<=500&&overage>200}">yellow</c:if><c:if test="${overage<=200 }">red</c:if>">
                        <i class="stat-icon  icon-lg fa fa-envelope-o"></i>
                    </div>
                </div>
            </div>
        </div>
        <%--<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
            <div class="databox radius-bordered databox-shadowed databox-graded">
                <div class="databox-left bg-azure">
                    <div class="databox-piechart">
                        <div data-toggle="easypiechart" class="easyPieChart"
                             data-barcolor="#fff" data-linecap="butt" data-percent="${smsInfo.useatio}"
                             data-animate="1000" data-linewidth="3" data-size="47"
                             data-trackcolor="#7fe2fa">
                            <span class="white font-90">${smsInfo.useatio}%</span>
                        </div>
                    </div>
                </div>
                <div class="databox-right">
                    <span class="databox-number azure">${smsInfo.sendTotal}</span>

                    <div class="databox-text darkgray">已发送数量</div>
                    <div class="databox-state bg-azure">
                        <i class="stat-icon  icon-lg fa fa-envelope-o"></i>
                    </div>
                </div>
            </div>
        </div>--%>
    </div>
    <!--结束 databox  -->
    <!-- Page Body -->
    <form action="${ctx}/infoMessage/smsLog/listSmsLog" method="post"
          id="listForm" name="listForm">

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
                                        <input type="text" name="phone" class="form-control" placeholder="手机号">
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
                        <span class="widget-caption">信息发送查询列表</span>

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
                                    <th style="text-align: center;">序号</th>
                                    <th>手机号码</th>
                                    <th>发送内容</th>
                                    <th>创建时间</th>
                                </tr>
                                </thead>
                                <tbody style="font-size: 12px;">
                                <c:forEach items="${page.content}" var="smsLog"
                                           varStatus="status">
                                    <tr>
                                        <td class="table_no" align="center"></td>
                                        <td><c:out value="${smsLog.phone}"/></td>
                                        <td><c:out value="${smsLog.content}"/></td>
                                        <td title="${smsLog.created}"><fmt:formatDate value="${smsLog.createDate}"
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
    </form>
</div>
<%@include file="/static/inc/footer.inc" %>
</body>
</html>

<script src="${ctx}/static/lib/beyond/js/charts/easypiechart/jquery.easypiechart.js"></script>
<script src="${ctx}/static/lib/beyond/js/charts/easypiechart/easypiechart-init.js"></script>
<script type="text/javascript">
    InitiateEasyPieChart.init();
</script>


