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
        function queryByID(id) {
            var address = "${ctx}/userinfo/usermoneyinfo/findoperation/" + id;
            alert(id);
        }
    </script>


</head>
<body>

<div class="page-breadcrumbs">
    <ul class="breadcrumb" style="line-height: 40px;">
        <li><i class="fa fa-home"></i> <a href="#">前台用户管理</a></li>
        <li><a href="#">用户资金管理</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>用户资金记录</h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
        </a> <a class="refresh" id="refresh-toggler"
                href="">
        <i class="glyphicon glyphicon-refresh"></i>
    </a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
            class="glyphicon glyphicon-fullscreen"></i>
    </a>
    </div>
    <!--Header Buttons End-->
</div>
<!-- /Page Header -->
<!-- Page Body -->
<form action="" method="post" id="listForm" name="listForm">
    <div class="page-body">
        <div class="widget">
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
                                    <label class="control-label no-padding-right">&nbsp;&nbsp;资金类别</label>
                                    <select class="form-control" name="fundType">
                                        <option value="">全部</option>
                                        <option value="-1">充值/提现</option>
                                        <option value="0">充值</option>
                                        <option value="1">提现</option>
                                        <option value="2">到期本金</option>
                                        <option value="3">收益</option>
                                        <option value="4">活动收入</option>
                                        <option value="5">扣费</option>
                                        <option value="6">投资</option>
                                        <option value="8">转账</option>
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
            <div class="widget-body">
                <div class="flip-scroll">
                    <!--
                    <div style="text-align: right; margin-bottom: 10px;">
                        <a href="${ctx}/userinfo/usermoneyinfo/usermoneylist"
                           class="btn btn-labeled btn-blue"> <i
                                class="btn-label glyphicon glyphicon-arrow-left"></i>返回
                        </a>
                    </div>
                    -->
                    <table class="table table-hover table-striped table-bordered">
                        <thead style="font-size: 16px;">
                        <tr>
                            <th>
                                <div style="float: left">用户名：</div>
                                <c:choose>
                                    <c:when test="${user.userLeve.gradeNum eq 1}"><img src="${ctx}/static/lib/images/vip1.png" /></c:when>
                                    <c:when test="${user.userLeve.gradeNum eq 2}"><img src="${ctx}/static/lib/images/vip2.png" /></c:when>
                                </c:choose>
                                <a href="${ctx}/userinfo/user/listUserInfo/<c:out value='${user.id}'/>">
                                    <c:out value="${user.username}" />
                                </a>
                            </th>
                            <th>
                                <div style="float: left">手机号：</div>
                                <a href="${ctx}/userinfo/user/listUserInfo/<c:out value='${user.id}'/>">

                                    <c:out value="${user.mobile}" />
                                </a>
                            </th>
                            <th>
                                <div style="float: left">真实姓名：</div>
                                <a href="${ctx}/userinfo/user/listUserInfo/<c:out value='${user.id}'/>">
                                    <c:out value="${user.realName}" />
                                </a>
                            </th>
                            <th>
                                <div style="float: left">注册时间：</div>
                                <a href="${ctx}/userinfo/user/listUserInfo/<c:out value='${user.id}'/>">
                                    <fmt:formatDate value="${user.createDate}"
                                            pattern="yyyy年MM月dd日 HH时mm分"/>
                                </a>
                            </th>
                        </tr>
                        </thead>
                    </table>
                    <br/>
                    <table class="table table-hover table-striped table-bordered">
                        <thead style="font-size: 16px; font-weight: bold;">
                        <tr>
                            <th width="80" style="text-align: center;">序号</th>
                            <th>操作类型</th>
                            <th>资金类别</th>
                            <th>操作金额</th>
                            <th>账户余额</th>
                            <th>操作时间</th>
                        </tr>
                        </thead>
                        <tbody style="font-size: 12px;">
                        <c:forEach items="${page.content}" var="userFundRecord"
                                   varStatus="status">
                            <tr>
                                <td class="table_no" width="80" align="center"></td>
                                <td><c:out value="${userFundRecord.fundflow}"/></td>
                                <td>
                                    <c:if test="${userFundRecord.fundType eq 0}">
                                        <c:out value="充值"/>
                                    </c:if>
                                    <c:if test="${userFundRecord.fundType eq 1}">
                                        <c:out value="提现"/>
                                    </c:if>
                                    <c:if test="${userFundRecord.fundType eq 2}">
                                        <c:out value="到期本金"/>
                                    </c:if>
                                    <c:if test="${userFundRecord.fundType eq 3}">
                                        <c:out value="收益"/>
                                    </c:if>
                                    <c:if test="${userFundRecord.fundType eq 4}">
                                        <c:out value="活动收入"/>
                                    </c:if>
                                    <c:if test="${userFundRecord.fundType eq 5}">
                                        <c:out value="扣费"/>
                                    </c:if>
                                    <c:if test="${userFundRecord.fundType eq 6}">
                                        <c:out value="投资"/>
                                    </c:if>
                                    <c:if test="${userFundRecord.fundType eq 7}">
                                        <c:out value="私人订制"/>
                                    </c:if>
                                    <c:if test="${userFundRecord.fundType eq 8}">
                                        <c:out value="转账"/>
                                    </c:if>
                                </td>
                                <td>
                                    <c:if test="${userFundRecord.type eq 0 }">
                                        <i class="fa fa-minus red"></i>
                                    </c:if>
                                    <c:if test="${userFundRecord.type eq 1 }">
                                        <i class="fa  fa-plus blue"></i>
                                    </c:if>
                                    <c:if test="${userFundRecord.type eq 2 }">
                                        <i class="fa fa-minus red"></i>
                                    </c:if>
                                    <c:if test="${userFundRecord.type eq 3 }">
                                        <i class="fa  fa-plus blue"></i>
                                    </c:if>
                                    <c:out value="${userFundRecord.fund}"/>
                                </td>
                                <td><c:out value="${userFundRecord.balance}"/></td>
                                <td><fmt:formatDate value="${userFundRecord.recordDate}"
                                                    pattern="yyyy年MM月dd日 HH点mm分"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <tags:pagination page="${page}"></tags:pagination>
                </div>
            </div>
        </div>
    </div>
</form>
<%@include file="/static/inc/footer.inc" %>
</body>
</html>
