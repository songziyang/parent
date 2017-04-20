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
            margin-bottom: 10px;
        }
    </style>
</head>
<body>

<!-- Page Breadcrumb -->
<div class="page-breadcrumbs">
    <ul class="breadcrumb" style="line-height: 40px;">
        <li><i class="fa fa-home"></i> <a href="#">放款管理</a></li>
        <li><a href="#">民生打款信息</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>民生打款信息</h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
        </a> <a class="refresh" id="refresh-toggler"
                href="${ctx}/userwithdraw/manualpay/listManualPay"> <i
            class="glyphicon glyphicon-refresh"></i>
    </a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
            class="glyphicon glyphicon-fullscreen"></i>
    </a>
    </div>
    <!--Header Buttons End-->
</div>
<!-- /Page Header -->
<!-- Page Body -->
<form action="${ctx}/userwithdraw/manualpay/listManualPay"
      method="post" id="listForm" name="listForm">
    <div class="page-body">
        <div class="row">
            <div class="col-xs-12 col-md-12">
                <div class="widget">
                    <div class="widget-header  with-footer">
                        <span class="widget-caption">民生打款信息</span>

                        <div class="widget-buttons">
                            <a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
                            </a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
                        </a>
                        </div>
                    </div>
                    <div class="widget-body">
                        <div class="flip-scroll">
                            <table class="table table-hover table-striped table-bordered">
                                <thead style="font-size: 16px; font-weight: bold;">
                                <tr>
                                    <th width="80" style="text-align: center;">序号</th>
                                    <th>请求流水号</th>
                                    <th>交易金额</th>
                                    <th>交易响应码</th>
                                    <th>交易状态</th>
                                    <th>信息描述</th>
                                </tr>
                                </thead>
                                <tbody style="font-size: 12px;">
                                <c:forEach items="${page.content}" var="pay" varStatus="status">
                                    <tr>
                                        <th class="table_no" width="80" align="center"></th>
                                        <th><c:out value="${pay.bussflowno}"/></th>
                                        <th><c:out value="${pay.tranamt}"/></th>
                                        <th><c:out value="${pay.respcode}"/></th>
                                        <th>
                                            <c:if test="${empty pay.tranState }">
                                                --
                                            </c:if>
                                            <c:if test="${pay.tranState eq '00'}">
                                                代付受理成功
                                            </c:if>
                                            <c:if test="${pay.tranState eq '01'}">
                                                代付成功
                                            </c:if>
                                            <c:if test="${pay.tranState eq '02'}">
                                                系统处理中
                                            </c:if>
                                            <c:if test="${pay.tranState eq '03'}">
                                                代付失败
                                            </c:if>
                                        </th>
                                        <th><c:out value="${pay.respmsg}"/></th>
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
