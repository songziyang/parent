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
    <script type="text/javascript">
        $(document)
                .ready(
                function () {
                    var filename = '<c:out value="${fileName}"/>';
                    if (filename != "") {
                        //window.location.href = "http://keng.yinduoziben.com/static/download/" + filename;
                        window.location.href = "http://localhost:8080/static/download"+ filename;
                    }

                    if (("${payTimeStart}" != "" && "${payTimeStart}" != undefined ) || ("${payTimeEnd}" != "" && "${payTimeEnd}" != undefined )) {
                        $("#selector").click();
                    }
                });

        function exprotExcel(condition) {
            var url = "${ctx}/userwithdraw/paback/exportExcel/" + condition
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
        <li><a href="#">放款操作</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>放款操作列表</h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
        </a> <a class="refresh" id="refresh-toggler"
                href="${ctx}/userwithdraw/paback/listWithDrawPayBack"> <i
            class="glyphicon glyphicon-refresh"></i>
    </a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
            class="glyphicon glyphicon-fullscreen"></i>
    </a>
    </div>
    <!--Header Buttons End-->
</div>
<!-- /Page Header -->
<!-- Page Body -->
<form action="${ctx}/userwithdraw/paback/listWithDrawPayBack"
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
                                    <a href="#"> </a> <a href="#" data-toggle="collapse" id="selector"> <i
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
                                            type="text" name="realname" class="form-control" style="width: 100px"
                                            placeholder="申请人">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right">用户名</label> <input
                                            type="text" name="username" class="form-control"  style="width: 120px"
                                            placeholder="用户名">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right">手机号</label> <input
                                            type="text" name="mobile" class="form-control"  style="width: 120px"
                                            placeholder="手机号">
                                    </div>
                                    <div class="form-group" style="margin-left: 10px">
                                        <label class="control-label no-padding-right">放款时间</label> <input
                                            type="text" name="applicationTimeStart" class="form-control" value="${payTimeStart}"
                                            placeholder="开始时间" onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd HH:mm'})">到<input
                                            type="text" name="applicationTimeEnd" class="form-control" value="${payTimeEnd}"
                                            placeholder="结束时间" onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd HH:mm'})">
                                    </div>



                                    <div class="form-group" style="margin-left: 10px">
                                        <label class="control-label no-padding-right">状态</label>
                                        <select class="form-control" name="status">
                                            <option value="3">待放款</option>
                                            <option value="6">处理中</option>
                                            <option value="4">放款成功</option>
                                            <option value="5">放款失败</option>
                                            <option value="7">单笔处理失败</option>
                                            <option value="8">分批处理失败</option>
                                        </select>
                                    </div>

                                    <div class="form-group" style="margin-left: 10px">
                                        <label class="control-label no-padding-right">放款类型</label>
                                        <select class="form-control" name="drawAuto">
                                            <option value="">全部</option>
                                            <option value="0">手动</option>
                                            <option value="1">自动</option>
                                        </select>
                                    </div>

                                    <div class="form-group" style="margin-left: 10px">
                                        <label class="control-label no-padding-right">提现类型</label>
                                        <select class="form-control" name="withdrawtype">
                                            <option value="">全部</option>
                                            <option value="0">银多账户提现</option>
                                            <option value="1">电子账户提现</option>
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
                        <span class="widget-caption">申请列表</span>

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
                            <div class="flip-scroll">

                                <div style="text-align: right;margin-bottom: 10px;">
                                </div>
                                <div style="margin-bottom: 10px; text-align: center; color: red; font-size: 15px">
                                    提现金额：
                                    <c:out value="${empty withdrawMoney? 0: withdrawMoney}"/>
                                    打款金额：
                                    <c:out value="${empty advanceMoney? 0: advanceMoney}"/>
                                    手续费:
                                    <a href='?condition=${condition}&poundage=true'>
                                        <c:out value="${(empty withdrawMoney? 0: withdrawMoney) -( empty advanceMoney? 0: advanceMoney) }"/>
                                    </a>
                                </div>
                                <table class="table table-hover table-striped table-bordered">
                                    <thead style="font-size: 16px; font-weight: bold;">
                                    <tr>
                                        <th width="80" style="text-align: center;">序号</th>
                                        <th>真实姓名</th>
                                        <th>用户名</th>
                                        <th>手机号</th>
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
                                        <th>打款金额</th>
                                        <th>到账金额</th>
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
                                        <th>审核时间</th>
                                        <!--民生处理用-->
                                        <th>放款时间</th>
                                        <th>状态</th>
                                        <th>处理类型</th>
                                        <th>提现类型</th>
                                        <th width="200px" style="text-align: center;">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody style="font-size: 12px;">
                                    <c:forEach items="${page.content}" var="payback"
                                               varStatus="status">
                                        <tr>
                                            <td class="table_no" width="80" align="center"></td>
                                            <th>
                                                <a href='${ctx}/userinfo/user/listUserInfo/<c:out value="${payback.user.id}" />'>
                                                    <c:out value="${payback.realname}"/>
                                                </a>
                                            </th>
                                            <th>
                                                <c:choose>
                                                    <c:when test="${payback.user.userLeve.gradeNum eq 1}"><img src="${ctx}/static/lib/images/vip1.png" /></c:when>
                                                    <c:when test="${payback.user.userLeve.gradeNum eq 2}"><img src="${ctx}/static/lib/images/vip2.png" /></c:when>
                                                </c:choose>
                                                <a href='${ctx}/userinfo/user/listUserInfo/<c:out value="${payback.user.id}" />'
                                                   data-simpletooltip="init"
                                                   title='<c:out value="${payback.user.remark}" />'
                                                   <c:if test="${not empty payback.user.remark}">style='color: red;'</c:if>>
                                                    <c:out value="${payback.user.username}"/>
                                                </a>
                                            </th>
                                            <th>
                                                <a href='${ctx}/userinfo/user/listUserInfo/<c:out value="${payback.user.id}" />'
                                                   data-simpletooltip="init"
                                                   title='<c:out value="${payback.user.remark}" />'
                                                   <c:if test="${not empty payback.user.remark}">style='color: red;'</c:if>>
                                                    <c:out value="${payback.user.mobile}"/>
                                                </a>
                                            </th>
                                            <th><fmt:formatNumber value="${payback.withdrawMoney}" type="number"/></th>
                                            <th><c:out value="${payback.advanceMoney}"/></th>
                                            <th><c:out value="${empty payback.sucAmt ? 0 : payback.sucAmt }"/></th>
                                            <th><fmt:formatDate value="${payback.sqDate}"  pattern="yyyy年MM月dd日 HH时mm分"/></th>
                                            <th><fmt:formatDate value="${payback.auditDate}"  pattern="yyyy年MM月dd日 HH时mm分"/></th>
                                            <th><fmt:formatDate value="${payback.payDate}" pattern="yyyy年MM月dd日 HH时mm分"/></th>
                                            <th>
                                                <c:if test="${payback.status eq 3}">待放款</c:if>
                                                <c:if test="${payback.status eq 6}">处理中</c:if>
                                                <a href='${ctx}/userwithdraw/manualpay/listManualPay?pageCurrent=0&condition={"linkId":${payback.id},"type":1}'>
                                                    <c:if test="${payback.status eq 4}">放款成功</c:if>
                                                    <c:if test="${payback.status eq 5}">放款失败</c:if>
                                                    <c:if test="${payback.status eq 7}">单笔处理失败</c:if>
                                                    <c:if test="${payback.status eq 8}">分批处理失败</c:if>
                                                </a>
                                            </th>
                                            <th>
                                                <c:if test="${empty payback.drawAuto}">手动处理</c:if>
                                                <c:if test="${payback.drawAuto eq 0}">手动处理</c:if>
                                                <c:if test="${payback.drawAuto eq 1}">自动处理</c:if>
                                            </th>
                                            <th>
                                                <c:if test="${empty payback.withdrawtype}">银多账户提现</c:if>
                                                <c:if test="${payback.withdrawtype eq 0}">银多账户提现</c:if>
                                                <c:if test="${payback.withdrawtype eq 1}">电子账户提现</c:if>
                                            </th>
                                            <th width="200px" style="text-align: center;"><c:if
                                                    test="${payback.status eq 3 || payback.status eq 7 || payback.status eq 8 }">
                                                <shiro:hasPermission name="withdraws_detailed">
                                                    <a
                                                            href='${ctx}/userwithdraw/paback/findWithDrawByID/<c:out value="${payback.id}"/>'
                                                            class="btn btn-blue btn-sm" style="width: 90px"><i
                                                            class="fa fa-edit"></i>放款</a>
                                                </shiro:hasPermission>
                                            </c:if> <c:if
                                                    test="${payback.status eq 4 || payback.status eq 5 || payback.status eq 6 }">
                                                <shiro:hasPermission name="withdraws_list">
                                                    <a
                                                            href='${ctx}/userwithdraw/paback/findWithDrawByID/<c:out value="${payback.id}"/>'
                                                            class="btn btn-palegreen btn-sm" style="width: 90px"><i
                                                            class="fa fa-edit"></i>查看</a>
                                                </shiro:hasPermission>
                                            </c:if>
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
