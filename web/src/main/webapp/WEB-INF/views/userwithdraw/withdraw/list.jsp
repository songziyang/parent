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
        $(document).ready(function () {
            var filename = '<c:out value="${fileName}"/>';
            if (filename != "") {
                window.location.href = "http://keng.yinduoziben.com/static/download/" + filename;
                //window.location.href = "http://localhost:8080/static/download"+ filename;
            }
        });


        function exprotExcel(condition) {
            var url = "${ctx}/userwithdraw/withdraw/exportExcel/" + condition
            window.location.href = url;
        }

        function sortSerach(orderNumber, orderSort) {
            $("#orderNumber").val(orderNumber);
            $("#orderSort").val(orderSort);
            document.listForm.submit();
        }

    </script>

    <style type="text/css">
        .form-inline .form-group {
            margin-bottom: 10px;
        }
    </style>
</head>
<body>

<!-- Page Breadcrumb -->
<div class="page-breadcrumbs">
    <ul class="breadcrumb" style="line-height: 40px;">
        <li><i class="fa fa-home"></i> <a href="#">放款管理</a></li>
        <li><a href="#">放款审核</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>放款审核列表</h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
        </a> <a class="refresh" id="refresh-toggler"
                href="${ctx}/userwithdraw/withdraw/listUserWithDraw"> <i
            class="glyphicon glyphicon-refresh"></i>
    </a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
            class="glyphicon glyphicon-fullscreen"></i>
    </a>
    </div>
    <!--Header Buttons End-->
</div>
<!-- /Page Header -->
<!-- Page Body -->
<form action="${ctx}/userwithdraw/withdraw/listUserWithDraw"
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
                                        <label class="control-label no-padding-right">真实姓名</label> <input
                                            type="text" name="realname" class="form-control"
                                            placeholder="申请人">
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
                                    <div class="form-group" style="margin-left: 10px">
                                        <label class="control-label no-padding-right">申请日期</label> <input
                                            type="text" name="applicationTimeStart" class="form-control"
                                            placeholder="开始时间" onfocus="WdatePicker({skin:'twoer'})">到
                                        <input type="text" name="applicationTimeEnd"
                                               class="form-control" placeholder="结束时间"
                                               onfocus="WdatePicker({skin:'twoer'})">
                                    </div>

                                    <div class="form-group" style="margin-left: 10px">
                                        <label class="control-label no-padding-right">状态</label> <select
                                            class="form-control" name="status">
                                        <option value="0">审核中</option>
                                        <option value="2">审核失败</option>
                                    </select>
                                    </div>


                                    <div class="form-group">
                                        <input type="hidden" name="orderSort" class="form-control" id="orderSort">
                                        <input type="hidden" name="orderNumber" class="form-control" id="orderNumber">
                                        <input type="hidden" name="pageCurrent" class="form-control" value="0">
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
                        <span class="widget-caption">放款审核列表</span>

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
                                        class="btn-label glyphicon glyphicon-download-alt"></i>导出Excel
                                </a>
                            </div>

                            <table class="table table-hover table-striped table-bordered">
                                <thead style="font-size: 16px; font-weight: bold;">
                                <tr>
                                    <th>序号</th>
                                    <th>真实姓名</th>
                                    <th>用户名</th>
                                    <th>手机号</th>
                                    <th>状态</th>
                                    <th>
                                        <div style="float: left">提现金额</div>
                                        <div style="float: right;">
                                            <a href="javascript:sortSerach(1,'asc');"><i
                                                    class="fa fa-sort-asc"
                                                    style="height: 5px; float: left; margin-top: 2px"></i></a> <br/>
                                            <a href="javascript:sortSerach(1,'desc');"><i
                                                    class="fa fa-sort-desc"
                                                    style="height: 5px; margin-top: -10px"></i></a>
                                        </div>
                                    </th>
                                    <th>
                                        <div style="float: left">申请时间</div>
                                        <div style="float: right;">
                                            <a href="javascript:sortSerach(2,'asc');"><i
                                                    class="fa fa-sort-asc"
                                                    style="height: 5px; float: left; margin-top: 2px"></i></a> <br/>
                                            <a href="javascript:sortSerach(2,'desc');"><i
                                                    class="fa fa-sort-desc"
                                                    style="height: 5px; margin-top: -10px"></i></a>
                                        </div>
                                    </th>
                                    <th>
                                        前七天提现次数
                                    </th>
                                    <th>
                                        前七天充值次数
                                    </th>
                                    <th width="350px" style="text-align: center;">操作</th>
                                </tr>
                                </thead>
                                <tbody style="font-size: 12px;">
                                <c:forEach items="${page.content}" var="payback"
                                           varStatus="status">
                                    <tr>
                                        <td class="table_no" width="80" align="center"></td>
                                        <th>
                                            <a href='${ctx}/userinfo/user/listUserInfo/<c:out value="${payback.user.id}" />'
                                                data-simpletooltip="init" target="_blank"
                                                title='<c:out value="${payback.user.remark}" />'
                                                <c:if test="${not empty payback.user.remark}">style='color: red;'</c:if>>
                                                <c:out value="${payback.realname}"/>
                                            </a>
                                        </th>
                                        <th>
                                            <c:choose>
                                                <c:when test="${payback.user.userLeve.gradeNum eq 1}"><img src="${ctx}/static/lib/images/vip1.png" /></c:when>
                                                <c:when test="${payback.user.userLeve.gradeNum eq 2}"><img src="${ctx}/static/lib/images/vip2.png" /></c:when>
                                            </c:choose>
                                            <a href='${ctx}/userinfo/usermoneyinfo/findUserFundRecord/<c:out value="${payback.user.id}"/>'
                                                data-simpletooltip="init" target="_blank"
                                                title='<c:out value="${payback.user.remark}" />'
                                                <c:if test="${not empty payback.user.remark}">style='color: red;'</c:if>>
                                                <c:out value="${payback.user.username}"/>
                                            </a>
                                        </th>
                                        <th>
                                            <a href='${ctx}/userinfo/usermoneyinfo/findUserFundRecord/<c:out value="${payback.user.id}"/>'
                                                data-simpletooltip="init" target="_blank"
                                                title='<c:out value="${payback.user.remark}" />'
                                                <c:if test="${not empty payback.user.remark}">style='color: red;'</c:if>>
                                                <c:out value="${payback.user.mobile}"/>
                                            </a>
                                        </th>
                                        <th><c:if test="${payback.status == 0}">审核中</c:if>
                                            <c:if test="${payback.status == 2}">审核失败</c:if>
                                            <c:if test="${payback.status == 3}">打款中</c:if>
                                            <c:if test="${payback.status == 4}">打款成功</c:if>
                                            <c:if test="${payback.status == 5}">打款失败</c:if>
                                        </th>
                                        <th><fmt:formatNumber value="${payback.withdrawMoney}" type="number"/></th>
                                        <th><fmt:formatDate value="${payback.sqDate}"
                                                            pattern="yyyy年MM月dd日 HH时mm分"/></th>
                                        <th><c:out
                                                value="${empty payback.user.userWithDrawNum.countNum ? 0 : payback.user.userWithDrawNum.countNum }"/></th>
                                        <th><c:out
                                                value="${empty payback.user.userWithDrawNum.rechargeNum ? 0 : payback.user.userWithDrawNum.rechargeNum }"/></th>

                                        <th width="200px" style="text-align: center;">
                                            <c:if test="${payback.status eq 0}">
                                                <shiro:hasPermission name="withdraw_detailed">
                                                    <a href='${ctx}/userwithdraw/withdraw/findWithDrawByID/<c:out value="${payback.id}"/>'
                                                       class="btn btn-blue btn-sm" style="width: 95px">
                                                        <i class="fa fa-edit"></i>审核</a>
                                                </shiro:hasPermission>
                                            </c:if>
                                            <c:if test="${payback.status eq 2}">
                                                <shiro:hasPermission name="withdraw_detailed">
                                                    <a href='${ctx}/userwithdraw/withdraw/findWithDrawByID/<c:out value="${payback.id}"/>'
                                                       class="btn btn-info btn-sm" style="width: 80px"><i
                                                            class="fa fa-file-text"></i>查看</a>
                                                </shiro:hasPermission>
                                            </c:if>
                                            <shiro:hasPermission name="usermoney_fundrecordlist">
                                                <a href='${ctx}/userinfo/usermoneyinfo/findUserFundRecord/<c:out value="${payback.user.id}"/>'
                                                   class="btn btn-palegreen btn-sm" style="width: 80px"><i
                                                        class="fa fa-file-text"></i>资金记录</a>
                                            </shiro:hasPermission>
                                        </th>
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
