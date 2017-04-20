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
    <script src="${ctx}/static/lib/simpletooltip/src/js/simpletooltip.js"></script>
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
            var url = "${ctx}/traderecord/redpacket/exportExcel/" + condition
            window.location.href = url;
        }
    </script>
</head>
<body>

<div class="page-breadcrumbs">
    <ul class="breadcrumb" style="line-height: 40px;">
        <li><i class="fa fa-home"></i> <a href="#">交易管理</a></li>
        <li><a href="#">红包记录</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>红包记录</h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
        </a> <a class="refresh" id="refresh-toggler" href="${ctx}/traderecord/redpacket/list"> <i
            class="glyphicon glyphicon-refresh"></i>
    </a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
            class="glyphicon glyphicon-fullscreen"></i>
    </a>
    </div>
    <!--Header Buttons End-->
</div>
<!-- /Page Header -->
<!-- Page Body -->
<form action="${ctx}/traderecord/redpacket/list"
      method="post" id="listForm" name="listForm">
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
                                        <label class="control-label no-padding-right">&nbsp;&nbsp;用户名</label> <input
                                            type="text" name="username" class="form-control" style="width: 120px;"
                                            placeholder="用户名">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right">&nbsp;&nbsp;手机号</label>
                                        <input type="text" name="mobile" class="form-control" style="width: 120px;"
                                               placeholder="手机号">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right">&nbsp;&nbsp;产品类型</label>
                                        <select class="form-control" name="productType">
                                            <option value="">全部</option>
                                            <option value="1">活期宝</option>
                                            <option value="2">定存宝</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right">&nbsp;&nbsp;红包类型</label>
                                        <select class="form-control" name="redpacketType">
                                            <option value="">全部</option>
                                            <option value="1">现金红包</option>
                                            <option value="2">加息券</option>
                                            <option value="3">代金券</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right">&nbsp;&nbsp;状态</label>
                                        <select class="form-control" name="status">
                                            <option value="">全部</option>
                                            <option value="1">未使用</option>
                                            <option value="2">已冻结</option>
                                            <option value="3">已使用</option>
                                            <option value="4">已过期</option>
                                            <option value="5">现金领取</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right"></label> 发放日期:从
                                        <input type="text" name="startTime" class="form-control"
                                               tabindex="1" style="padding-left: 10px;width: 100px;" maxlength="40"
                                               onfocus="WdatePicker({skin:'twoer'})"> 到 <input
                                            type="text" name="endTime" class="form-control" tabindex="1"
                                            style="padding-left: 10px;width: 100px;" maxlength="40"
                                            onfocus="WdatePicker({skin:'twoer'})">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right"></label> 使用日期:从
                                        <input type="text" name="startUseTime" class="form-control"
                                               tabindex="1" style="padding-left: 10px;width: 100px;" maxlength="40"
                                               onfocus="WdatePicker({skin:'twoer'})"> 到 <input
                                            type="text" name="endUseTime" class="form-control" tabindex="1"
                                            style="padding-left: 10px;width: 100px;" maxlength="40"
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
                        <span class="widget-caption">红包记录</span>

                        <div class="widget-buttons">
                            <a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
                            </a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
                        </a>
                        </div>
                    </div>
                    <div class="widget-body">
                        <div class="flip-scroll">
                            <div style="text-align: right; margin-bottom: 10px;">
                                <a href="javascript:exprotExcel('<c:out value="${condition}"/>');"
                                   class="btn btn-labeled btn-success"> <i
                                        class="btn-label glyphicon glyphicon-envelope"></i>导出Excel
                                </a>
                            </div>
                            <table class="table table-hover table-striped table-bordered">
                                <thead style="font-size: 16px; font-weight: bold;">
                                <th width="80" style="text-align: center;">序号</th>
                                <th>用户名</th>
                                <th>手机号</th>
                                <th>红包值</th>
                                <th>产品类型</th>
                                <th>红包类型</th>
                                <th>状态</th>
                                <th>发放日期</th>
                                <th>使用日期</th>
                                <th>到期日期</th>
                                </tr>
                                </thead>
                                <tbody style="font-size: 12px;">
                                <c:forEach items="${page.content}" var="redpacketLog" varStatus="status">
                                    <tr>
                                        <td class="table_no" width="80" align="center"></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${redpacketLog.user.userLeve.gradeNum eq 1}"><img
                                                        src="${ctx}/static/lib/images/vip1.png"/></c:when>
                                                <c:when test="${redpacketLog.user.userLeve.gradeNum eq 2}"><img
                                                        src="${ctx}/static/lib/images/vip2.png"/></c:when>
                                            </c:choose>
                                            <a href='${ctx}/userinfo/user/listUserInfo/<c:out value="${redpacketLog.user.id}" />'
                                               data-simpletooltip="init"
                                               title='<c:out value="${redpacketLog.user.remark}" />'
                                               <c:if test="${not empty redpacketLog.user.remark}">style='color: red;'</c:if>>
                                                <c:out value="${redpacketLog.user.username}"/>
                                            </a>
                                        </td>
                                        <td>
                                            <a href='${ctx}/userinfo/user/listUserInfo/<c:out value="${redpacketLog.user.id}" />'
                                               data-simpletooltip="init"
                                               title='<c:out value="${redpacketLog.user.remark}" />'
                                               <c:if test="${not empty redpacketLog.user.remark}">style='color: red;'</c:if>>
                                                <c:out value="${redpacketLog.user.mobile}"/>
                                            </a>
                                        </td>
                                        <td>
                                            <c:out value="${redpacketLog.giveValue}"/>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${redpacketLog.productType eq 1}">
                                                    活期宝
                                                </c:when>
                                                <c:when test="${redpacketLog.productType eq 2}">
                                                    定存宝
                                                </c:when>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${redpacketLog.redpacketType eq 1}">
                                                    现金红包
                                                </c:when>
                                                <c:when test="${redpacketLog.redpacketType eq 2}">
                                                    加息券
                                                </c:when>
                                                <c:when test="${redpacketLog.redpacketType eq 3}">
                                                    代金券
                                                </c:when>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${redpacketLog.status eq 1}">
                                                    未使用
                                                </c:when>
                                                <c:when test="${redpacketLog.status eq 2}">
                                                    已冻结
                                                </c:when>
                                                <c:when test="${redpacketLog.status eq 3}">
                                                    已使用
                                                </c:when>
                                                <c:when test="${redpacketLog.status eq 4}">
                                                    已过期
                                                </c:when>
                                                <c:when test="${redpacketLog.status eq 5}">
                                                    现金领取
                                                </c:when>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${redpacketLog.getDate}"
                                                            pattern="yyyy年MM月dd日"/>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${redpacketLog.userUseDate}"
                                                            pattern="yyyy年MM月dd日"/>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${redpacketLog.status eq 1}">
                                                    <fmt:formatDate value="${redpacketLog.useFinishDate}"
                                                                    pattern="yyyy年MM月dd日"/>
                                                </c:when>
                                                <c:when test="${redpacketLog.status eq 3 }">
                                                    <fmt:formatDate value="${redpacketLog.investFinishDate}"
                                                                    pattern="yyyy年MM月dd日"/>
                                                </c:when>
                                                <c:when test="${redpacketLog.status eq 4 }">
                                                    <c:choose>
                                                        <%--已过期，没有使用时间，则到期日期是使用截至日期--%>
                                                        <c:when test="${empty redpacketLog.userUseTime}">
                                                            <fmt:formatDate value="${redpacketLog.useFinishDate}"
                                                                            pattern="yyyy年MM月dd日"/>
                                                        </c:when>
                                                        <%--已过期，有使用时间，则到期日期是投资截至日期--%>
                                                        <c:otherwise>
                                                            <fmt:formatDate value="${redpacketLog.investFinishDate}"
                                                                            pattern="yyyy年MM月dd日"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                            </c:choose>
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