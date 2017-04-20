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
    <script type="text/javascript">
        $(document)
                .ready(
                function () {
                    var filename = '<c:out value="${fileName}"/>';
                    if (filename != "") {
                        window.location.href = "http://keng.yinduoziben.com/static/download/" + filename;
//                        window.location.href = "http://localhost:8080/static/download" + filename;
                    }
                });

        function exprotExcel(condition) {
            var url = "${ctx}/buyrecord/free/exportExcel/" + condition
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
        <li><i class="fa fa-home"></i> <a href="#">交易管理</a></li>
        <li><a href="#">随心存已购记录</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>随心存已购记录</h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
        </a> <a class="refresh" id="refresh-toggler" href="${ctx}/buyrecord/free/list">
        <i class="glyphicon glyphicon-refresh"></i>
    </a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
            class="glyphicon glyphicon-fullscreen"></i>
    </a>
    </div>
    <!--Header Buttons End-->
</div>
<!-- /Page Header -->
<!-- Page Body -->
<form action="${ctx}/buyrecord/free/list" method="post" id="listForm" name="listForm">
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
                                    <!-- -->

                                    <div class="form-group">
                                        <label class="control-label no-padding-right">&nbsp;&nbsp;交易状态</label>
                                        <select class="form-control" name="status">
                                            <option value="">全部</option>
                                            <option value="0">未到期</option>
                                            <option value="1">已到期</option>
                                        </select>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label no-padding-right">&nbsp;&nbsp;产品天数</label>
                                        <input type="text" name="startDays" class="form-control"
                                                data-bv-regexp="true" data-bv-regexp-regexp="^\d[1-3]$"
                                                data-bv-regexp-message="开始天数格式错误" placeholder="开始天数">到
                                        <input type="text" name="endDays" class="form-control"
                                                data-bv-regexp="true" data-bv-regexp-regexp="^\d[1-3]$"
                                                data-bv-regexp-message="结束天数格式错误"
                                                placeholder="结束天数">
                                    </div>

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
                                        <label class="control-label no-padding-right"></label>到期日期：从
                                        <input type="text" name="startDate" class="form-control"
                                               tabindex="1" style="padding-left: 50px;" maxlength="40"
                                               onfocus="WdatePicker({skin:'twoer'})"> 到 <input
                                            type="text" name="endDate" class="form-control" tabindex="1"
                                            style="padding-left: 50px;" maxlength="40"
                                            onfocus="WdatePicker({skin:'twoer'})">
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
                        <span class="widget-caption">随心存已购记录</span>

                        <div class="widget-buttons">
                            <a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
                            </a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
                        </a>
                        </div>
                    </div>
                    <div class="widget-body">
                        <div class="flip-scroll">
                            <div style="margin-bottom: 10px; text-align: center; color: red; font-size: 15px">
                                到期金额：<c:out value="${empty totalFund ? 0 : totalFund}"/>
                            </div>

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
                                    <th>用户名</th>
                                    <th>手机号</th>
                                    <th>产品名称</th>
                                    <th>产品天数</th>
                                    <th>购买份数</th>
                                    <th>购买利率</th>
                                    <th>加息利率</th>
                                    <th>VIP利率</th>
                                    <th>总收益</th>
                                    <th>未还收益</th>
                                    <th>交易状态</th>
                                    <th>购买时间</th>
                                    <th>到期日期</th>

                                </tr>
                                </thead>
                                <tbody style="font-size: 12px;">
                                <c:forEach items="${page.content}" var="free" varStatus="status">
                                    <tr>
                                        <td class="table_no" width="80" align="center"></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${free.user.userLeve.gradeNum eq 1}"><img src="${ctx}/static/lib/images/vip1.png" /></c:when>
                                                <c:when test="${free.user.userLeve.gradeNum eq 2}"><img src="${ctx}/static/lib/images/vip2.png" /></c:when>
                                            </c:choose>
                                            <a href='${ctx}/userinfo/user/listUserInfo/<c:out value="${free.user.id}" />'
                                               data-simpletooltip="init" title='<c:out value="${free.user.remark}" />'
                                               <c:if test="${not empty free.user.remark}">style='color: red;'</c:if>>
                                                <c:out value="${free.user.username}"/>
                                            </a>
                                        </td>
                                        <td>
                                            <a href='${ctx}/userinfo/user/listUserInfo/<c:out value="${free.user.id}" />'
                                               data-simpletooltip="init" title='<c:out value="${free.user.remark}" />'
                                               <c:if test="${not empty free.user.remark}">style='color: red;'</c:if>>
                                                <c:out value="${free.user.mobile}"/>
                                            </a>
                                        </td>
                                        <td><c:out value="${free.productInfo.name}"/></td>
                                        <td><c:out value="${free.days}"/></td>
                                        <td><c:out value="${free.buyFund}"/></td>
                                        <td><c:out value="${free.apr}"/></td>
                                        <td><c:out value="${free.grandApr}"/></td>
                                        <td><c:out value="${free.vipApr}"/></td>
                                        <td><c:out value="${free.interestFund}"/></td>
                                        <td><c:out value="${free.predictIncome}"/></td>
                                        <td>
                                            <c:if test="${free.status eq 0}">未到期</c:if>
                                            <c:if test="${free.status eq 1}">已到期</c:if>
                                        </td>
                                        <td><fmt:formatDate value="${free.creatDate}"
                                                            pattern="yyyy年MM月dd日 HH时mm分"/></td>
                                        <td><fmt:formatDate value="${free.closeDate}" pattern="yyyy年MM月dd日"/></td>

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