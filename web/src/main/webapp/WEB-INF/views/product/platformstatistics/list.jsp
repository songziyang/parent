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
    <script>
        $(function () {
            var filename = '<c:out value="${fileName}"/>';
            if (filename != "") {
                window.location.href = "http://keng.yinduoziben.com/static/download/" + filename;
                //window.location.href ="http://localhost:8080/static/download/"+filename;
            }
        });

        function exprotExcel(condition) {
            var url = "${ctx}/platform/platformStatistics/exportExcel/" + condition
            window.location.href = url;
        }
    </script>
</head>
<body>
<!-- Page Breadcrumb -->
<div class="page-breadcrumbs">
    <ul class="breadcrumb" style="line-height: 40px;">
        <li><i class="fa fa-home"></i> <a href="#">统计管理</a></li>
        <li><a href="#">平台统计</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>平台统计列表</h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
        </a> <a class="refresh" id="refresh-toggler" href="${ctx}/platform/platformStatistics/list"> <i
            class="glyphicon glyphicon-refresh"></i>
    </a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
            class="glyphicon glyphicon-fullscreen"></i>
    </a>
    </div>
    <!--Header Buttons End-->
</div>
<!-- /Page Header -->
<!-- Page Body -->
<form action="${ctx}/platform/platformStatistics/list" method="post"
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
                                        <label class="control-label no-padding-right"></label>&nbsp&nbsp
                                        日期:从
                                        <input type="text" name="totalStartTime"
                                               class="form-control" tabindex="1"
                                               style="padding-left: 50px;" required maxlength="40"
                                               onfocus="WdatePicker({skin:'twoer'})"/> 到
                                        <input type="text" name="totalEndTime" class="form-control"
                                               tabindex="1" style="padding-left: 50px;" required
                                               maxlength="40" onfocus="WdatePicker({skin:'twoer'})"/>
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
                        <span class="widget-caption">平台统计列表</span>

                        <div class="widget-buttons">
                            <a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
                            </a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
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
                                    <th width="80" style="text-align: center;">序号</th>
                                    <th>日期</th>
                                    <th>累计交易金额</th>
                                    <th>累计收益</th>
                                    <th>活期累计金额</th>
                                    <th>定期累计金额</th>
                                    <th>活期年化利率(%)</th>
                                    <th>债权数量</th>
                                    <th>债权金额</th>
                                    <th>平台数</th>
                                    <th>投资人数</th>
                                    <th>充值人数</th>
                                    <th>活期当日人数</th>
                                    <th>定存当日人数</th>
                                    <th>活期总人数</th>
                                    <th>定存总人数</th>
                                    <th>总人数</th>
                                </tr>
                                </thead>
                                <tbody style="font-size: 12px;">
                                <c:forEach items="${page.content}" var="statistics"
                                           varStatus="status">
                                    <tr>
                                        <td class="table_no" width="80" align="center"></td>
                                        <td><fmt:formatDate value="${statistics.totalDate}" pattern="yyyy年MM月dd日"/></td>
                                        <td><c:out value="${statistics.totalFund}"/></td>
                                        <td><c:out value="${statistics.totalRevenue}"/></td>
                                        <td><c:out value="${statistics.totalDayloan}"/></td>
                                        <td><c:out value="${statistics.totalDeposit}"/></td>
                                        <td><c:out value="${statistics.dayloanApr}"/></td>
                                        <td><c:out value="${statistics.investNum}"/></td>
                                        <td><c:out value="${statistics.investFund}"/></td>
                                        <td><c:out value="${statistics.platformNum}"/></td>
                                        <td><c:out value="${statistics.investPersons}"/></td>
                                        <td><c:out value="${statistics.rechargeNum}"/></td>
                                        <td><c:out value="${empty statistics.currentPersons? 0: statistics.currentPersons }"/></td>
                                        <td><c:out value="${empty statistics.ragularPersons? 0: statistics.ragularPersons}"/></td>
                                        <td><c:out value="${empty statistics.currents? 0: statistics.currents}"/></td>
                                        <td><c:out value="${empty statistics.ragulars? 0: statistics.ragulars}"/></td>
                                        <td><c:out value="${empty statistics.totalPersons? 0: statistics.totalPersons}"/></td>
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