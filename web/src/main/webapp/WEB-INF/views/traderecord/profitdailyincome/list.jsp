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
        <script type="text/javascript">
            $(document).ready(
                function () {
                    var filename = '<c:out value="${fileName}"/>';
                    if (filename != "") {
                       window.location.href = "http://keng.yinduoziben.com/static/download/" + filename;
                       //window.location.href = "http://localhost:8080/static/download" + filename;
                    }
                });
            function exprotExcel(condition) {
                var url = "${ctx}/traderecord/profitDailyIncome/export/" + condition
                window.location.href = url;
            }
        </script>
    </head>
    <body>
    <div class="page-breadcrumbs">
        <ul class="breadcrumb" style="line-height: 40px;">
            <li><i class="fa fa-home"></i> <a href="#">交易管理</a></li>
            <li><a href="#">每日额外收益</a></li>
        </ul>
    </div>
    <!-- /Page Breadcrumb -->
    <!-- Page Header -->
    <div class="page-header position-relative">
        <div class="header-title">
            <h1>每日额外收益</h1>
        </div>
        <!--Header Buttons-->
        <div class="header-buttons">
            <a class="sidebar-toggler" href="#">
                <i class="fa fa-arrows-h"></i>
            </a>
            <a class="refresh" id="refresh-toggler" href="${ctx}/traderecord/profitDailyIncome/list">
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
    <form action="${ctx}/traderecord/profitDailyIncome/list" method="post" id="listForm" name="listForm">
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
                                            <label class="control-label no-padding-right"></label>&nbsp;&nbsp;
                                            记录日期：从
                                            <input type="text" name="startDate"
                                                  class="form-control" tabindex="1"
                                                  style="padding-left: 50px;" required maxlength="40"
                                                  onfocus="WdatePicker({skin:'twoer'})"> 到
                                            <input type="text" name="endDate" class="form-control" tabindex="1"
                                                style="padding-left: 50px;" required maxlength="40"
                                                onfocus="WdatePicker({skin:'twoer'})">
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
                            <span class="widget-caption">每日额外收益</span>
                            <div class="widget-buttons">
                                <a href="#" data-toggle="maximize"><i class="fa fa-expand"></i></a>
                                <a href="#" data-toggle="collapse"><i class="fa fa-minus"></i></a>
                            </div>
                        </div>
                        <div class="widget-body">
                            <div class="flip-scroll">
                                <div style="text-align: right; margin-bottom: 10px;">
                                    <a href="javascript:exprotExcel('<c:out value="${condition}"/>');"
                                            class="btn btn-labeled btn-success">
                                        <i class="btn-label glyphicon glyphicon-envelope"></i>导出Excel
                                    </a>
                                </div>
                                <div style="margin-bottom: 10px; text-align: center; color: red; font-size: 15px">
                                    总收益金额:
                                    <c:out value="${empty sumIncome? 0: sumIncome}" />
                                </div>
                                <table class="table table-hover table-striped table-bordered">
                                    <thead style="font-size: 16px; font-weight: bold;">
                                        <th width="80" style="text-align: center;">序号</th>
                                        <th>收益金额</th>
                                        <th>日期</th>
                                    </thead>
                                    <tbody style="font-size: 12px;">
                                        <c:forEach items="${page.content}" var="income" varStatus="status">
                                            <tr>
                                                <td class="table_no"></td>
                                                <td>${income[0]}</td>
                                                <td>${income[1]}</td>
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