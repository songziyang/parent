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

    <script type="text/javascript">
        $(document).ready(function () {
            var filename = '<c:out value="${fileName}"/>';
            if (filename != "") {
                window.location.href = "http://keng.yinduoziben.com/static/download/" + filename;
                //window.location.href ="http://localhost:8080/static/download"+filename;
            }
        });

        function exprotExcel(condition) {
            var url = "${ctx}/product/productsalesrecord/exportExcel/" + condition
            window.location.href = url;
        }
    </script>


    <style type="text/css">
        .form-inline .form-group {
            margin-bottom: 10px;
        }
    </style>
</head>
<body>

<div class="page-breadcrumbs">
    <ul class="breadcrumb" style="line-height: 40px;">
        <li><i class="fa fa-home"></i> <a href="#">产品管理</a></li>
        <li><a href="#">产品销售统计</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>产品销售统计</h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
        </a> <a class="refresh" id="refresh-toggler"
                href="${ctx}/product/productsalesrecord/listSalesRecord"> <i
            class="glyphicon glyphicon-refresh"></i>
    </a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
            class="glyphicon glyphicon-fullscreen"></i>
    </a>
    </div>
    <!--Header Buttons End-->
</div>
<!-- /Page Header -->
<!-- Page Body -->
<form action="${ctx}/product/productsalesrecord/listSalesRecord" method="post" id="listForm" name="listForm">
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
                                        <label class="control-label no-padding-right">销售状态</label>
                                        <select class="form-control" name="status" style="width: 200px;">
                                            <option value="">全部</option>
                                            <option value="1">在售</option>
                                            <option value="2">售罄</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right"></label>
                                        &nbsp;&nbsp;
                                        发布日期：从
                                        <input type="text" name="startDate" class="form-control" tabindex="1"
                                               style="padding-left: 10px;" required maxlength="40" style="width: 80px;"
                                               onfocus="WdatePicker({skin:'twoer'})">
                                        到
                                        <input type="text" name="endDate" class="form-control" tabindex="1"
                                               style="padding-left: 10px;" required maxlength="40" style="width: 80px;"
                                               onfocus="WdatePicker({skin:'twoer'})">
                                    </div>
                                    <div class="form-group">
                                        <a href="javascript:searchData();" class="btn btn-labeled btn-blue"> <i
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
                        <span class="widget-caption">产品销售统计</span>

                        <div class="widget-buttons">
                            <a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
                            </a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
                        </a>
                        </div>
                    </div>
                    <div class="widget-body">
                        <div class="flip-scroll">

                            <div style="text-align: right; margin-bottom: 10px;">
                                <a
                                        href="javascript:exprotExcel('<c:out value="${condition}"/>');"
                                        class="btn btn-labeled btn-success"> <i
                                        class="btn-label glyphicon glyphicon-envelope"></i>导出Excel
                                </a>
                            </div>

                            <table class="table table-hover table-striped table-bordered">
                                <thead style="font-size: 16px; font-weight: bold;">
                                <tr>
                                    <th width="80" style="text-align: center;">序号</th>
                                    <th>产品期数</th>
                                    <th>发布时间</th>
                                    <th>发布金额</th>
                                    <th>销售状态</th>
                                    <th>剩余金额</th>
                                    <th>售罄时间</th>
                                    <th>记录时间</th>
                                </tr>
                                </thead>
                                <tbody style="font-size: 12px;">
                                <c:forEach items="${page.content}" var="salesRecord" varStatus="status">
                                    <tr>
                                        <td class="table_no" width="80" align="center"></td>
                                        <td>
                                            <c:if test="${salesRecord.productInfo.type.id eq 1}">
                                                <c:out value="${salesRecord.productInfo.name}"/>
                                            </c:if>
                                            <c:if test="${salesRecord.productInfo.type.id eq 2}">
                                                <c:out value="${salesRecord.productInfo.name}"/><fmt:formatDate value="${salesRecord.releaseDate}" pattern="yyMMddHH"/>期
                                            </c:if>
                                        </td>
                                        <td><fmt:formatDate value="${salesRecord.releaseDate}"  pattern="yyyy年MM月dd日 HH时mm分"/></td>
                                        <td><c:out value="${salesRecord.releaseAmount}"/></td>
                                        <td>
                                            <c:if test="${salesRecord.status eq 1 }">在售</c:if>
                                            <c:if test="${salesRecord.status eq 2 }">售罄</c:if>
                                        </td>
                                        <td><c:out  value="${salesRecord.salesAmount}"/></td>
                                        <td><fmt:formatDate value="${salesRecord.salesDate}"  pattern="yyyy年MM月dd日 HH时mm分"/></td>
                                        <td><fmt:formatDate value="${salesRecord.createdDate}" pattern="yyyy年MM月dd日 HH时mm分"/></td>
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