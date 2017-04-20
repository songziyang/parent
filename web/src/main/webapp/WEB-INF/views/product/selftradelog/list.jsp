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
        function deleteSelfTradeLog(url) {
            bootbox.dialog({
                message: "您确定要删除该产品吗？",
                title: "删除提示",
                className: "modal-darkorange",
                buttons: {
                    success: {
                        label: "确认",
                        className: "btn-default",
                        callback: function () {
                            window.location.href = url;
                        }
                    },
                    "取消": {
                        className: "btn-default",
                        callback: function () {
                        }
                    }
                }
            });
        }

        function buyFail(url) {
            bootbox.dialog({
                message: "您确定交易失败么？",
                title: "交易失败提示",
                className: "modal-darkorange",
                buttons: {
                    success: {
                        label: "确认",
                        className: "btn-default",
                        callback: function () {
                            window.location.href = url;
                        }
                    },
                    "取消": {
                        className: "btn-default",
                        callback: function () {
                        }
                    }
                }
            });
        }
    </script>
</head>
<body>
<!-- Page Breadcrumb -->
<div class="page-breadcrumbs">
    <ul class="breadcrumb" style="line-height: 40px;">
        <li><i class="fa fa-home"></i> <a href="#">产品管理</a></li>
        <li><a href="#">私人订制记录</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>私人订制记录列表</h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i></a>
        <a class="refresh" id="refresh-toggler"
           href="${ctx}/product/selfTradeLog/list"><i
                class="glyphicon glyphicon-refresh"></i></a> <a class="fullscreen"
                                                                id="fullscreen-toggler" href="#"> <i
            class="glyphicon glyphicon-fullscreen"></i></a>
    </div>
    <!--Header Buttons End-->
</div>
<!-- /Page Header -->
<!-- Page Body -->
<form action="${ctx}/product/selfTradeLog/list" method="post"
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
                                    <a href="#"></a> <a href="#" data-toggle="collapse"> <i
                                        class="fa fa-plus blue"></i>
                                </a>
                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="form-inline" role="form">
                                    <div class="form-group">
                                        <label class="control-label no-padding-right">用户名</label> <input
                                            type="text" name="username" class="form-control"
                                            placeholder="用户名"/>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right">手机号</label> <input
                                            type="text" name="mobile" class="form-control"
                                            placeholder="手机号"/>
                                    </div>
                                    <div class="form-group">
                                        <div>
                                            <label class="control-label no-padding-right">&nbsp;状态</label>
                                            <select class="form-control" name="state"
                                                    style="width: 200px;">
                                                <option value="1" selected>待确认</option>
                                                <option value="2">交易中</option>
                                                <option value="3">交易失败</option>
                                                <option value="4">交易成功</option>
                                                <option value="5">交易结束</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <a href="javascript:searchData();" class="btn btn-labeled btn-blue">
                                            <i class="btn-label fa fa-search"></i>查询
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="widget">
                    <div class="widget-header  with-footer">
                        <span class="widget-caption">私人订制记录列表</span>

                        <div class="widget-buttons">
                            <a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i></a>
                            <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i></a>
                        </div>
                    </div>
                    <div class="widget-body">
                        <div class="flip-scroll">
                            <div style="text-align: right; margin-bottom: 10px;">
                                <shiro:hasPermission name="selftradelog_create">
                                    <a class="btn btn-labeled btn-success"
                                       href="${ctx}/product/selfTradeLog/create"><i
                                            class="btn-label glyphicon glyphicon-plus"></i>添加</a>
                                </shiro:hasPermission>
                                <!--
										<shiro:hasPermission name="selftradelog_del">
											<a class="btn btn-labeled btn-danger"  href="javascript:removeData('${ctx}/product/selfTradeLog/deleteOnes');">
												<i class="btn-label glyphicon glyphicon-trash"></i>删除
											</a>
										</shiro:hasPermission>
										-->
                            </div>
                            <table class="table table-hover table-striped table-bordered">
                                <thead style="font-size: 16px; font-weight: bold;">
                                <tr>
                                    <th width="80" style="text-align: center;">序号</th>
                                    <th>用户名称</th>
                                    <th>手机号</th>
                                    <th>产品名称</th>
                                    <th>交易日期</th>
                                    <th>投资日期</th>
                                    <th>冻结金额</th>
                                    <th>投资金额</th>
                                    <th>资产类型</th>
                                    <th>状态</th>
                                    <!--
                                        <th width="20">
                                            <label>
                                            <input type="checkbox">
                                            <span class="text"></span>
                                            </label>
                                        </th>
                                         -->
                                    <th width="200px" style="text-align: center;">操作</th>
                                </tr>
                                </thead>
                                <tbody style="font-size: 12px;">
                                <c:forEach items="${page.content}" var="tradeLog"
                                           varStatus="status">
                                    <tr>
                                        <td class="table_no" width="80" align="center"></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${tradeLog.user.userLeve.gradeNum eq 1}"><img src="${ctx}/static/lib/images/vip1.png" /></c:when>
                                                <c:when test="${tradeLog.user.userLeve.gradeNum eq 2}"><img src="${ctx}/static/lib/images/vip2.png" /></c:when>
                                            </c:choose>
                                            <a href="${ctx}/userinfo/user/listUserInfo/<c:out value='${tradeLog.user.id}'/>"
                                               data-simpletooltip="init" title='<c:out value="${tradeLog.user.remark}" />'
                                               <c:if test="${not empty tradeLog.user.remark}">style='color: red;'</c:if>>
                                                <c:out value="${tradeLog.user.username}"/>
                                            </a>
                                        </td>
                                        <td>
                                            <a href="${ctx}/userinfo/user/listUserInfo/<c:out value='${tradeLog.user.id}'/>"
                                               data-simpletooltip="init" title='<c:out value="${tradeLog.user.remark}" />'
                                               <c:if test="${not empty tradeLog.user.remark}">style='color: red;'</c:if>>
                                                <c:out value="${tradeLog.user.mobile}"/>
                                            </a>
                                        </td>
                                        <td><c:out value="${tradeLog.buyName}"/></td>
                                        <td><fmt:formatDate value="${tradeLog.tradeDate}"
                                                            pattern="yyyy年MM月dd日 "/></td>
                                        <td><fmt:formatDate value="${tradeLog.investDate}"
                                                            pattern="yyyy年MM月dd日"/></td>
                                        <td><c:out value="${tradeLog.freezeMoney}"/></td>
                                        <td><c:out value="${tradeLog.tradeMoney}"/></td>
                                        <td><c:choose>
                                            <c:when test="${tradeLog.proType eq 1}">债权投资</c:when>
                                            <c:when test="${tradeLog.proType eq 2}">股票投资</c:when>
                                            <c:when test="${tradeLog.proType eq 3}">股权投资</c:when>
                                            <c:when test="${tradeLog.proType eq 4}">基金投资</c:when>
                                            <c:when test="${tradeLog.proType eq 5}">消费类型</c:when>
                                        </c:choose></td>
                                        <c:set var="state" value="${tradeLog.state}"></c:set>
                                        <td><c:choose>
                                            <c:when test="${state eq 1}">待确认</c:when>
                                            <c:when test="${state eq 2}">交易中</c:when>
                                            <c:when test="${state eq 3}">交易失败</c:when>
                                            <c:when test="${state eq 4}">交易成功</c:when>
                                            <c:when test="${state eq 5}">交易结束</c:when>
                                        </c:choose></td>
                                        <!--
													<td width="20">
														<label> 
														<input type="checkbox" name="ids" value='<c:out value="${tradeLog.id}" />'> 
														<span class="text"></span>
														</label>
													</td>
													 -->
                                        <td width="200px" align="center">
                                            <c:if test="${ tradeLog.state eq 1 }">
                                                <shiro:hasPermission name="selftradelog_edit">
                                                    <a href='${ctx}/product/selfTradeLog/edit/<c:out value="${tradeLog.id}" />'
                                                       class="btn btn-info btn-xs edit">
                                                        <i class="fa fa-edit"></i>编辑
                                                    </a>&nbsp;&nbsp;
                                                </shiro:hasPermission>
                                                <shiro:hasPermission name="selftradelog_del">
                                                    <a class="btn btn-danger btn-xs delete"
                                                       onclick="deleteSelfTradeLog('${ctx}/product/selfTradeLog/delete/
                                                           <c:out value="${tradeLog.id}"/>')">
                                                        <i class="fa fa-trash-o"></i>删除
                                                    </a>
                                                </shiro:hasPermission>
                                            </c:if>
                                            <shiro:hasPermission name="selftradelog_buy">
                                                <c:if test="${ tradeLog.state eq 2 }">
                                                    <a href='${ctx}/product/selfTradeLog/buy/<c:out value="${tradeLog.id}" />'
                                                       class="btn btn-info btn-xs edit">
                                                        <i class="fa fa-edit"></i>购买成功
                                                    </a>&nbsp;&nbsp;
                                                    <a onclick="buyFail('${ctx}/product/selfTradeLog/buyFail/<c:out
                                                            value="${tradeLog.id}"/>')"
                                                       class="btn btn-danger btn-xs delete">
                                                        <i class="fa fa-trash-o"></i>购买失败
                                                    </a>
                                                </c:if>
                                            </shiro:hasPermission>
                                            <shiro:hasPermission name="selftradelog_back">
                                                <c:if test="${ tradeLog.state eq 4 }">
                                                    <c:if test="${tradeLog.principalStatus eq 1 || empty tradeLog.principalStatus}">
                                                        <a href='${ctx}/product/selfTradeLog/sales/<c:out value="${tradeLog.id}" />'
                                                           class="btn btn-info btn-xs edit">
                                                            <i class="fa fa-edit"></i>还款
                                                        </a>
                                                    </c:if>
                                                    <a href='${ctx}/product/selfTradeLog/listSelfRevenue?condition={fid:<c:out value="${tradeLog.id}" />}'
                                                       class="btn btn-info btn-xs edit">
                                                        <i class="fa fa-edit"></i>还款记录
                                                    </a>
                                                </c:if>
                                            </shiro:hasPermission>
                                            <c:if test="${ tradeLog.state eq 5}">
                                                <a href='${ctx}/product/selfTradeLog/listSelfRevenue?condition={fid:<c:out value="${tradeLog.id}" />}'
                                                   class="btn btn-info btn-xs edit">
                                                    <i class="fa fa-edit"></i>还款记录
                                                </a>
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
