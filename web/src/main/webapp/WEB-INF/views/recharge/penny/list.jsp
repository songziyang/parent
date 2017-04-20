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
//                        window.location.href = "http://keng.yinduoziben.com/static/download/" + filename;
                        window.location.href = "http://localhost:8080/static/download"+ filename;
                    }
                });

        function exportExcel(condition) {
            var url = "${ctx}/recharge/penny/exportExcel/" + condition
            window.location.href = url;
        }
    </script>
</head>
<body>

<div class="page-breadcrumbs">
    <ul class="breadcrumb" style="line-height: 40px;">
        <li><i class="fa fa-home"></i> <a href="#">充值管理</a></li>
        <li><a href="#">小额充值列表</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>小额充值列表</h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
        </a> <a class="refresh" id="refresh-toggler"
                href="${ctx}/recharge/penny/list"> <i
            class="glyphicon glyphicon-refresh"></i>
    </a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
            class="glyphicon glyphicon-fullscreen"></i>
    </a>
    </div>
    <!--Header Buttons End-->
</div>
<!-- /Page Header -->
<!-- Page Body -->
<form action="${ctx}/recharge/penny/list"
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
                                        <label class="control-label no-padding-right">充值金额</label> <input
                                            type="text" name="startMoney" class="form-control"
                                            placeholder="开始金额">到
                                        <input type="text" name="endMoney" class="form-control"
                                                placeholder="结束金额">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right">充值日期</label> <input
                                            type="text" name="startDays" class="form-control" readonly
                                            onfocus="WdatePicker({skin:'twoer'})"
                                            placeholder="开始日期">到
                                        <input type="text" name="endDays" class="form-control" required
                                               onfocus="WdatePicker({skin:'twoer'})"
                                               placeholder="结束日期">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right">充值类型</label>
                                        <select class="form-control" name="rechargetype">
                                            <option value="">全部</option>
                                            <option value="0">银多账户充值</option>
                                            <option value="1">电子账户充值</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right">充值渠道</label>
                                        <select class="form-control" name="onlines">
                                            <option value="">全部</option>
                                            <option value="0">线下充值</option>
                                            <option value="1">线上充值</option>
                                        </select>
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
                        <span class="widget-caption">充值信息列表</span>

                        <div class="widget-buttons">
                            <a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
                            </a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
                        </a>
                        </div>
                    </div>
                    <div class="widget-body">
                        <div class="flip-scroll">
                            <div style="text-align: right; margin-bottom: 10px;">
                                <a href="javascript:exportExcel('<c:out value="${condition}"/>');"
                                   class="btn btn-labeled btn-success"> <i
                                        class="btn-label glyphicon glyphicon-envelope"></i>导出Excel
                                </a>
                            </div>
                            <div style="margin-bottom: 10px; text-align: center; color: red; font-size: 15px">
                                总充值人数:<c:out value="${empty userCount? 0: userCount}" />
                            </div>
                            <table class="table table-hover table-striped table-bordered">
                                <thead style="font-size: 16px; font-weight: bold;">
                                <tr>
                                    <th width="80" style="text-align: center;">序号</th>
                                    <th>用户名</th>
                                    <th>手机号</th>
                                    <th>真实姓名</th>
                                    <th>请求流水</th>
                                    <th>充值金额</th>
                                    <th>充值时间</th>
                                    <th>充值类型</th>
                                    <th>充值渠道</th>
                                </tr>
                                </thead>
                                <tbody style="font-size: 12px;">
                                <c:forEach items="${page.content}" var="userRecharge"
                                           varStatus="status">
                                    <tr>
                                        <td class="table_no" width="80" align="center"></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${userRecharge.user.userLeve.gradeNum eq 1}"><img src="${ctx}/static/lib/images/vip1.png" /></c:when>
                                                <c:when test="${userRecharge.user.userLeve.gradeNum eq 2}"><img src="${ctx}/static/lib/images/vip2.png" /></c:when>
                                            </c:choose>
                                            <a href='${ctx}/userinfo/user/listUserInfo/<c:out value="${userRecharge.user.id}" />'
                                               data-simpletooltip="init" target="_blank"
                                               title='<c:out value="${userRecharge.user.remark}" />'
                                               <c:if test="${not empty userRecharge.user.remark}">style='color: red;'</c:if>>
                                                <c:out value="${userRecharge.user.username}"/>
                                            </a>
                                        </td>
                                        <td>
                                            <a href='${ctx}/userinfo/user/listUserInfo/<c:out value="${userRecharge.user.id}" />'
                                               data-simpletooltip="init" target="_blank"
                                               title='<c:out value="${userRecharge.user.remark}" />'
                                               <c:if test="${not empty userRecharge.user.remark}">style='color: red;'</c:if>>
                                                <c:out value="${userRecharge.user.mobile}"/>
                                            </a>
                                        </td>
                                        <td>
                                            <a href='${ctx}/userinfo/user/listUserInfo/<c:out value="${userRecharge.user.id}" />'
                                               data-simpletooltip="init" target="_blank"
                                               title='<c:out value="${userRecharge.user.remark}" />'
                                               <c:if test="${not empty userRecharge.user.remark}">style='color: red;'</c:if>>
                                                <c:out value="${userRecharge.user.realName}"/>
                                            </a>
                                        </td>
                                        <td>
                                            <c:out value="${userRecharge.serialNumber}"/>
                                        </td>
                                        <td>
                                            <c:out value="${userRecharge.money}"/>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${userRecharge.rechargeSucceedDate}"
                                                    pattern="yyyy年MM月dd日 HH时mm分"/></td>
                                        <td>
                                            <c:if test="${userRecharge.rechargetype eq 1 }">
                                                电子账户充值
                                            </c:if>
                                            <c:if test="${userRecharge.rechargetype ne 1 }">
                                                银多账户充值
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:if test="${userRecharge.onlines eq 1 }">
                                                线上充值
                                            </c:if>
                                            <c:if test="${userRecharge.onlines ne 1 }">
                                                线下充值
                                            </c:if>
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