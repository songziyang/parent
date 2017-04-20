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
    <script type="text/javascript">
        $(function() {
            var filename = '<c:out value="${fileName}"/>';
            if (filename != "") {
                window.location.href = "http://keng.yinduoziben.com/static/download/"+ filename;
                //window.location.href ="http://localhost:8080/static/download/"+filename;
            }
        });

        function exprotExcel(condition) {
            var url = "${ctx}/activity/rewardrecord/exportExcel/" + condition
            window.location.href = url;
        }
    </script>
</head>
<body>

<!-- Page Breadcrumb -->
<div class="page-breadcrumbs">
    <ul class="breadcrumb" style="line-height: 40px;">
        <li><i class="fa fa-home"></i> <a href="#">活动管理</a></li>
        <li><a href="#">周年中奖记录</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>周年中奖记录</h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#">
            <i class="fa fa-arrows-h"></i>
        </a>
        <a class="refresh" id="refresh-toggler" href="${ctx}/activity/rewardrecord/list">
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
<form action="${ctx}/activity/rewardrecord/list" method="post" id="listForm" name="listForm">
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
                                        <label class="control-label no-padding-right">真实姓名</label>
                                        <input type="text" name="realName" class="form-control" placeholder="真实姓名">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right">手机号</label>
                                        <input type="text" name="mobile" class="form-control" placeholder="手机号">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right">奖品类型</label>
                                        <select class="form-control" name="rewardType" style="width: 200px;">
                                            <option value="0">全部</option>
                                            <option value="1">一等奖</option>
                                            <option value="2">二等奖</option>
                                            <option value="3">三等奖</option>
                                            <option value="4">四等奖</option>
                                            <option value="5">五等奖</option>
                                            <option value="6">现金红包</option>
                                            <option value="7">鼓励奖</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right">兑换记录</label>
                                        <select class="form-control" name="exchageType" style="width: 200px;">
                                            <option value="-1">全部</option>
                                            <option value="0">未兑换</option>
                                            <option value="1">已兑换</option>
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
                        <span class="widget-caption">周年中奖记录</span>

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
                                    <th width="80" style="text-align: center;">序号</th>
                                    <th>用户姓名</th>
                                    <th>手机号</th>
                                    <th>奖品名称</th>
                                    <th>奖品类型</th>
                                    <th>奖品金额</th>
                                    <th>中奖时间</th>
                                    <th>兑换记录</th>
                                    <th>兑换时间</th>
                                    <th>收货人姓名</th>
                                    <th>收货人手机号</th>
                                    <th>收货地址</th>
                                    <th>备注信息</th>
                                </tr>
                                </thead>
                                <tbody style="font-size: 12px;">
                                <c:forEach items="${page.content}" var="record" varStatus="status">
                                    <tr>
                                        <td class="table_no" width="80" align="center"></td>
                                        <td><c:out value="${record.user.realName}" /></td>
                                        <td><c:out value="${record.user.mobile}" /></td>
                                        <td><c:out value="${record.rewardName}" /></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${record.rewardType eq 1}">一等奖</c:when>
                                                <c:when test="${record.rewardType eq 2}">二等奖</c:when>
                                                <c:when test="${record.rewardType eq 3}">三等奖</c:when>
                                                <c:when test="${record.rewardType eq 4}">四等奖</c:when>
                                                <c:when test="${record.rewardType eq 5}">五等奖</c:when>
                                                <c:when test="${record.rewardType eq 6}">现金红包</c:when>
                                                <c:when test="${record.rewardType eq 7}">鼓励奖</c:when>
                                            </c:choose>
                                        </td>
                                        <td><c:out value="${record.rewardVal}" /></td>
                                        <td>
                                            <fmt:formatDate value="${record.rewardDate}"
                                                    pattern="yyyy年MM月dd日 HH时mm分"/>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${record.exchageType eq 0}">未兑换</c:when>
                                                <c:when test="${record.exchageType eq 1}">已兑换</c:when>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${record.exchageDate}"
                                                    pattern="yyyy年MM月dd日 HH时mm分"/>
                                        </td>
                                        <td><c:out value="${record.realName}" /></td>
                                        <td><c:out value="${record.mobile}" /></td>
                                        <td><c:out value="${record.address}" /></td>
                                        <td><c:out value="${record.remark}" /></td>
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