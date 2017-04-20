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
                        //window.location.href = "http://localhost:8080/static/download"+ filename;
                    }
                });

        function exprotExcel(condition) {
            var url = "${ctx}/recharge/rechargetotal/exportExcel/" + condition
            window.location.href = url;
        }
    </script>

</head>
<body>

<div class="page-breadcrumbs">
    <ul class="breadcrumb" style="line-height: 40px;">
        <li><i class="fa fa-home"></i> <a href="#">前台用户管理</a></li>
        <li><a href="#">平台资金管理</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>平台资金管理</h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
        </a> <a class="refresh" id="refresh-toggler"
                href="${ctx}/platform/money/listPlatformMoney"> <i
            class="glyphicon glyphicon-refresh"></i>
    </a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
            class="glyphicon glyphicon-fullscreen"></i>
    </a>
    </div>
    <!--Header Buttons End-->
</div>
<!-- /Page Header -->
<!-- Page Body -->
<form action="${ctx}/platform/money/listPlatformMoney"
      method="post" id="listForm" name="listForm">
    <div class="page-body">
        <div class="row">

            <div class="col-lg-6 col-sm-6 col-xs-6">
                <div
                        class="databox radius-bordered databox-shadowed databox-vertical">
                    <div class="databox-top bg-red no-padding">
                        <div class="databox-row row-2"></div>
                        <div class="databox-row row-10">
                            <div class="databox-sparkline">总计</div>
                        </div>
                    </div>
                    <div class="databox-bottom no-padding bg-white">
                        <div class="databox-row">
                            <div
                                    class="databox-cell cell-6 text-align-center bordered-right bordered-platinum">
                                    <span class="databox-number lightcarbon">
                                        <shiro:hasPermission name="minusamount_info">
                                            <fmt:formatNumber
                                                    value="${(empty dcbje ? 0 : dcbje) + (empty hqbrmb ? 0 : hqbrmb) + (empty freeInvestFund ? 0 : freeInvestFund) + (empty zhuanInvestFund ? 0 : zhuanInvestFund) + (empty mljrInvestFund ? 0 : mljrInvestFund) }"
                                                    pattern="#"
                                                    minFractionDigits='0'/>
                                        </shiro:hasPermission>
                                        <shiro:lacksPermission name="minusamount_info">
                                            <fmt:formatNumber
                                                    value="${(empty dcbje ? 0 : dcbje) + (empty hqbrmb ? 0 : (hqbrmb -hqbkcje)) + (empty freeInvestFund ? 0 : freeInvestFund) + (empty zhuanInvestFund ? 0 : zhuanInvestFund) + (empty mljrInvestFund ? 0 : mljrInvestFund)  }"
                                                    pattern="#"
                                                    minFractionDigits='0'/>
                                        </shiro:lacksPermission>
                                    </span> <span class="databox-text sonic-silver no-margin">总投资金额</span>
                            </div>
                            <div class="databox-cell cell-6 text-align-center">
								<span class="databox-number lightcarbon">
                                    <fmt:formatNumber value="${empty ztjrs ? 0 : ztjrs}" type="number"/>
                                </span><span
                                    class="databox-text sonic-silver no-margin">总投资人数</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <%--<div class="col-lg-6 col-sm-6 col-xs-6">--%>
                <%--<div--%>
                        <%--class="databox radius-bordered databox-shadowed databox-vertical">--%>
                    <%--<div class="databox-top bg-red no-padding">--%>
                        <%--<div class="databox-row row-2"></div>--%>
                        <%--<div class="databox-row row-10">--%>
                            <%--<div class="databox-sparkline">客户总计</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="databox-bottom no-padding bg-white">--%>
                        <%--<div class="databox-row">--%>
                            <%--<div--%>
                                    <%--class="databox-cell cell-6 text-align-center bordered-right bordered-platinum">--%>
                                    <%--<span class="databox-number lightcarbon">--%>
                                        <%--<shiro:hasPermission name="minusamount_info">--%>
                                            <%--<fmt:formatNumber--%>
                                                    <%--value="${(empty dcbje ? 0 : dcbje) + (empty hqbrmb ? 0 : hqbrmb) + (empty freeInvestFund ? 0 : freeInvestFund) + (empty zhuanInvestFund ? 0 : zhuanInvestFund) + (empty mljrInvestFund ? 0 : mljrInvestFund)}"--%>
                                                    <%--pattern="#"--%>
                                                    <%--minFractionDigits='0'/>--%>
                                        <%--</shiro:hasPermission>--%>
                                        <%--<shiro:lacksPermission name="minusamount_info">--%>
                                            <%--<fmt:formatNumber--%>
                                                    <%--value="${(empty dcbje ? 0 : dcbje) + (empty hqbrmb ? 0 : (hqbrmb -hqbkcje)) + (empty freeInvestFund ? 0 : freeInvestFund) + (empty zhuanInvestFund ? 0 : zhuanInvestFund) + (empty mljrInvestFund ? 0 : mljrInvestFund)}"--%>
                                                    <%--pattern="#"--%>
                                                    <%--minFractionDigits='0'/>--%>
                                        <%--</shiro:lacksPermission>--%>
                                    <%--</span> <span class="databox-text sonic-silver no-margin">总投资金额</span>--%>
                            <%--</div>--%>

                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>

            <%--<div class="col-lg-6 col-sm-6 col-xs-6">--%>
            <%--<div--%>
            <%--class="databox radius-bordered databox-shadowed databox-vertical">--%>
            <%--<div class="databox-top bg-blue no-padding">--%>
            <%--<div class="databox-row row-2"></div>--%>
            <%--<div class="databox-row row-10">--%>
            <%--<div class="databox-sparkline">活期宝详细</div>--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--<div class="databox-bottom no-padding bg-white">--%>
            <%--<div class="databox-row">--%>
            <%--<div--%>
            <%--class="databox-cell cell-6 text-align-center bordered-right bordered-platinum">--%>
            <%--<span class="databox-number lightcarbon">--%>
            <%--<fmt:formatNumber value="${empty hqbtyj ? 0 : hqbtyj}" type="number"/>--%>
            <%--</span> <span--%>
            <%--class="databox-text sonic-silver no-margin">体验金</span>--%>
            <%--</div>--%>
            <%--<div class="databox-cell cell-6 text-align-center">--%>
            <%--<span class="databox-number lightcarbon">--%>

            <%--<shiro:hasPermission name="minusamount_info">--%>
            <%--<fmt:formatNumber value="${empty hqbrmb ? 0 :hqbrmb}" pattern="#"--%>
            <%--minFractionDigits='0'/>--%>
            <%--</shiro:hasPermission>--%>
            <%--<shiro:lacksPermission name="minusamount_info">--%>
            <%--<fmt:formatNumber value="${empty hqbrmb ? 0 :( hqbrmb-hqbkcje)}" pattern="#"--%>
            <%--minFractionDigits='0'/>--%>
            <%--</shiro:lacksPermission>--%>

            <%--</span> <span class="databox-text sonic-silver no-margin">人民币</span>--%>
            <%--</div>--%>

            <%--</div>--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--</div>--%>

        </div>

        <div class="row">

            <div class="col-lg-6 col-sm-6 col-xs-6">
                <div
                        class="databox radius-bordered databox-shadowed databox-vertical">
                    <div class="databox-top bg-yellow no-padding">
                        <div class="databox-row row-2"></div>
                        <div class="databox-row row-10">
                            <div class="databox-sparkline">活期宝</div>
                        </div>
                    </div>
                    <div class="databox-bottom no-padding bg-white">
                        <div class="databox-row">
                            <div
                                    class="databox-cell cell-4 text-align-center bordered-right bordered-platinum">
                                <a href="${ctx}/product/currentaccount/listCurrentAccount"> <span
                                        class="databox-number lightcarbon">
                                    <c:out value="${empty hqbrs  ? 0 : hqbrs }"/></span>
                                    <span class="databox-text sonic-silver no-margin">投资人数</span>
                                </a>
                            </div>
                            <%--<div class="databox-cell cell-6 text-align-center">--%>
                            <%--<a href="${ctx}/product/currentaccount/listCurrentAccount">--%>
                            <%--<span class="databox-number lightcarbon">--%>

                            <%--<shiro:hasPermission name="minusamount_info">--%>
                            <%--<fmt:formatNumber value="${empty hqbje ? 0 : hqbje}" pattern="#"--%>
                            <%--minFractionDigits='0'/>--%>
                            <%--</shiro:hasPermission>--%>
                            <%--<shiro:lacksPermission name="minusamount_info">--%>
                            <%--<fmt:formatNumber value="${empty hqbje ? 0 : (hqbje -hqbkcje)}" pattern="#"--%>
                            <%--minFractionDigits='0'/>--%>
                            <%--</shiro:lacksPermission>--%>
                            <%--</span> <span class="databox-text sonic-silver no-margin">投资金额</span>--%>
                            <%--</a>--%>
                            <%--</div>--%>

                            <div
                                    class="databox-cell cell-4 text-align-center bordered-right bordered-platinum">
								<span class="databox-number lightcarbon">
                                   <fmt:formatNumber value="${empty hqbtyj ? 0 : hqbtyj}" type="number"/>
                                </span> <span
                                    class="databox-text sonic-silver no-margin">体验金</span>
                            </div>
                            <div class="databox-cell cell-4 text-align-center">
								<span class="databox-number lightcarbon">

                                      <shiro:hasPermission name="minusamount_info">
                                          <fmt:formatNumber value="${empty hqbrmb ? 0 :hqbrmb}" pattern="#"
                                                            minFractionDigits='0'/>
                                      </shiro:hasPermission>
                                    <shiro:lacksPermission name="minusamount_info">
                                        <fmt:formatNumber value="${empty hqbrmb ? 0 :( hqbrmb-hqbkcje)}" pattern="#"
                                                          minFractionDigits='0'/>
                                    </shiro:lacksPermission>

								</span> <span class="databox-text sonic-silver no-margin">人民币</span>
                            </div>


                        </div>
                    </div>
                </div>
            </div>

            <div class="col-lg-6 col-sm-6 col-xs-6">
                <div
                        class="databox radius-bordered databox-shadowed databox-vertical">
                    <div class="databox-top bg-yellow no-padding">
                        <div class="databox-row row-2"></div>
                        <div class="databox-row row-10">
                            <div class="databox-sparkline">定存宝</div>
                        </div>
                    </div>
                    <div class="databox-bottom no-padding bg-white">
                        <div class="databox-row">
                            <div
                                    class="databox-cell cell-6 text-align-center bordered-right bordered-platinum">
                                <a href="${ctx}/product/ragularaccount/list"> <span
                                        class="databox-number lightcarbon">
                                <c:out value="${empty dcbrs ? 0: dcbrs }"/></span>
                                    <span class="databox-text sonic-silver no-margin">投资人数</span>
                                </a>
                            </div>
                            <div class="databox-cell cell-6 text-align-center">
                                <a href="${ctx}/product/ragularaccount/list">
									<span class="databox-number lightcarbon">
                                         <fmt:formatNumber value="${empty dcbje ? 0 : dcbje}" type="number"
                                                           pattern="#"/>
								</span> <span class="databox-text sonic-silver no-margin">投资金额</span>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>



        </div>

        <div class="row">

            <div class="col-lg-6 col-sm-6 col-xs-6">
                <div
                        class="databox radius-bordered databox-shadowed databox-vertical">
                    <div class="databox-top bg-yellow no-padding">
                        <div class="databox-row row-2"></div>
                        <div class="databox-row row-10">
                            <div class="databox-sparkline">新手宝</div>
                        </div>
                    </div>
                    <div class="databox-bottom no-padding bg-white">
                        <div class="databox-row">
                            <div
                                    class="databox-cell cell-4 text-align-center bordered-right bordered-platinum">
                                <a href="${ctx}/product/privilegeaccount/listPrivilegeAccount"> <span
                                        class="databox-number lightcarbon">
                                    <c:out value="${empty xsbrs  ? 0 : xsbrs }"/></span>
                                    <span class="databox-text sonic-silver no-margin">投资人数</span>
                                </a>
                            </div>
                            <div class="databox-cell cell-4 text-align-center bordered-right bordered-platinum">
								<span class="databox-number lightcarbon">
                                   <fmt:formatNumber value="${empty xsbje ? 0 : xsbje}" type="number"/>
                                </span>
                                <span class="databox-text sonic-silver no-margin">投资金额</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>



        <div class="row">

            <div class="col-lg-6 col-sm-6 col-xs-6">
                <div class="databox radius-bordered databox-shadowed databox-vertical">
                    <div class="databox-top bg-green no-padding">
                        <div class="databox-row row-2"></div>
                        <div class="databox-row row-10">
                            <div class="databox-sparkline">转转赚</div>
                        </div>
                    </div>
                    <div class="databox-bottom no-padding bg-white">
                        <div class="databox-row">
                            <div class="databox-cell cell-6 text-align-center bordered-right bordered-platinum">
                                <span
                                        class="databox-number lightcarbon">
                                <c:out value="${empty zhuanUserCount ? 0: zhuanUserCount }"/></span>
                                <span class="databox-text sonic-silver no-margin">投资人数</span>
                            </div>
                            <div class="databox-cell cell-6 text-align-center">
									<span class="databox-number lightcarbon">
                                         <fmt:formatNumber value="${empty zhuanInvestFund ? 0 : zhuanInvestFund}"
                                                           type="number"
                                                           pattern="#"/>
								</span> <span class="databox-text sonic-silver no-margin">投资金额</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-lg-6 col-sm-6 col-xs-6">
                <div class="databox radius-bordered databox-shadowed databox-vertical">
                    <div class="databox-top bg-green no-padding">
                        <div class="databox-row row-2"></div>
                        <div class="databox-row row-10">
                            <div class="databox-sparkline">随心存</div>
                        </div>
                    </div>
                    <div class="databox-bottom no-padding bg-white">
                        <div class="databox-row">
                            <div class="databox-cell cell-6 text-align-center bordered-right bordered-platinum">
                                 <span
                                         class="databox-number lightcarbon">
                                <c:out value="${empty freeUserCount ? 0: freeUserCount }"/></span>
                                <span class="databox-text sonic-silver no-margin">投资人数</span>
                            </div>
                            <div class="databox-cell cell-6 text-align-center">
									<span class="databox-number lightcarbon">
                                         <fmt:formatNumber value="${empty freeInvestFund ? 0 : freeInvestFund}"
                                                           type="number"
                                                           pattern="#"/>
								</span> <span class="databox-text sonic-silver no-margin">投资金额</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>



        </div>


        <div class="row">

            <div class="col-lg-6 col-sm-6 col-xs-6">
                <div class="databox radius-bordered databox-shadowed databox-vertical">
                    <div class="databox-top bg-pink no-padding">
                        <div class="databox-row row-2"></div>
                        <div class="databox-row row-10">
                            <div class="databox-sparkline">美利金融</div>
                        </div>
                    </div>
                    <div class="databox-bottom no-padding bg-white">
                        <div class="databox-row">
                            <div class="databox-cell cell-6 text-align-center bordered-right bordered-platinum">
                                <span
                                        class="databox-number lightcarbon">
                                <c:out value="${empty mljrUserCount ? 0: mljrUserCount }"/></span>
                                <span class="databox-text sonic-silver no-margin">投资人数</span>
                            </div>
                            <div class="databox-cell cell-6 text-align-center">
									<span class="databox-number lightcarbon">
                                         <fmt:formatNumber value="${empty mljrInvestFund ? 0 : mljrInvestFund}"
                                                           type="number"
                                                           pattern="#"/>
								</span> <span class="databox-text sonic-silver no-margin">投资金额</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-lg-6 col-sm-6 col-xs-6">
                <div class="databox radius-bordered databox-shadowed databox-vertical">
                    <div class="databox-top bg-pink no-padding">
                        <div class="databox-row row-2"></div>
                        <div class="databox-row row-10">
                            <div class="databox-sparkline">中转债权</div>
                        </div>
                    </div>
                    <div class="databox-bottom no-padding bg-white">
                        <div class="databox-row">
                            <div class="databox-cell cell-6 text-align-center">
									<span class="databox-number lightcarbon">
                                         <fmt:formatNumber value="${empty ydzbzlzh ? 0 : ydzbzlzh}"
                                                           type="number"
                                                           pattern="#"/>
								</span> <span class="databox-text sonic-silver no-margin">投资金额</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>


        <%--<div class="row">--%>
        <%--<div class="col-lg-6 col-sm-6 col-xs-6">--%>
        <%--<div--%>
        <%--class="databox radius-bordered databox-shadowed databox-vertical">--%>
        <%--<div class="databox-top bg-red no-padding">--%>
        <%--<div class="databox-row row-2"></div>--%>
        <%--<div class="databox-row row-10">--%>
        <%--<div class="databox-sparkline">其他</div>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<div class="databox-bottom no-padding bg-white">--%>
        <%--<div class="databox-row">--%>
        <%--<div--%>
        <%--class="databox-cell cell-6 text-align-center bordered-right bordered-platinum">--%>
        <%--<span class="databox-number lightcarbon">--%>
        <%--<shiro:hasPermission name="minusamount_info">--%>
        <%--<fmt:formatNumber--%>
        <%--value="${(empty dcbje ? 0 : dcbje) + (empty hqbrmb ? 0 : hqbrmb) + (empty freeInvestFund ? 0 : freeInvestFund) + (empty zhuanInvestFund ? 0 : zhuanInvestFund) + (empty mljrInvestFund ? 0 : mljrInvestFund) }"--%>
        <%--pattern="#"--%>
        <%--minFractionDigits='0'/>--%>
        <%--</shiro:hasPermission>--%>
        <%--<shiro:lacksPermission name="minusamount_info">--%>
        <%--<fmt:formatNumber--%>
        <%--value="${(empty dcbje ? 0 : dcbje) + (empty hqbrmb ? 0 : (hqbrmb -hqbkcje)) + (empty freeInvestFund ? 0 : freeInvestFund) + (empty zhuanInvestFund ? 0 : zhuanInvestFund) + (empty mljrInvestFund ? 0 : mljrInvestFund)}"--%>
        <%--pattern="#"--%>
        <%--minFractionDigits='0'/>--%>
        <%--</shiro:lacksPermission>--%>
        <%--</span> <span class="databox-text sonic-silver no-margin">总投资金额</span>--%>
        <%--</div>--%>
        <%--<div class="databox-cell cell-6 text-align-center">--%>
        <%--<span class="databox-number lightcarbon">--%>
        <%--<fmt:formatNumber value="${empty ztjrs ? 0 : ztjrs}" type="number"/>--%>
        <%--</span><span--%>
        <%--class="databox-text sonic-silver no-margin">总投资人数</span>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--</div>--%>

        <%--<div class="row">--%>
            <%--<div class="col-xs-12 col-md-12">--%>
                <%--<div class="row">--%>
                    <%--<div class="col-lg-12 col-sm-12 col-xs-12">--%>
                        <%--<div class="widget collapsed">--%>
                            <%--<div class="widget-header bordered-bottom bordered-sky">--%>
                                <%--<i class="widget-icon fa fa-arrows-v blue"></i> <span--%>
                                    <%--class="widget-caption">查询条件</span>--%>

                                <%--<div class="widget-buttons">--%>
                                    <%--<a href="#"> </a> <a href="#" data-toggle="collapse"> <i--%>
                                        <%--class="fa fa-plus blue"></i>--%>
                                <%--</a>--%>
                                <%--</div>--%>
                                <%--<!--Widget Buttons-->--%>
                            <%--</div>--%>
                            <%--<!--Widget Header-->--%>
                            <%--<div class="widget-body">--%>
                                <%--<div class="form-inline" role="form">--%>
                                    <%--<div class="form-group">--%>
                                        <%--<label class="control-label no-padding-right"></label> 统计日期:从--%>
                                        <%--<input type="text" name="startTime" class="form-control"--%>
                                               <%--tabindex="1" style="padding-left: 50px;" maxlength="40"--%>
                                               <%--onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'})"> 到--%>
                                        <%--<input--%>
                                                <%--type="text" name="endTime" class="form-control" tabindex="1"--%>
                                                <%--style="padding-left: 50px;" maxlength="40"--%>
                                                <%--onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'})">--%>
                                    <%--</div>--%>

                                    <%--<div class="form-group">--%>
                                        <%--<a href="javascript:searchData();"--%>
                                           <%--class="btn btn-labeled btn-blue"> <i--%>
                                                <%--class="btn-label fa fa-search"></i>查询--%>
                                        <%--</a>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<!--Widget Body-->--%>
                        <%--</div>--%>
                        <%--<!--Widget-->--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="widget">--%>
                    <%--<div class="widget-header  with-footer">--%>
                        <%--<span class="widget-caption">每日充值列表</span>--%>

                        <%--<div class="widget-buttons">--%>
                            <%--<a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>--%>
                            <%--</a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>--%>
                        <%--</a>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="widget-body">--%>
                        <%--<div class="flip-scroll">--%>
                            <%--<table class="table table-hover table-striped table-bordered">--%>
                                <%--<thead style="font-size: 16px; font-weight: bold;">--%>
                                <%--<tr>--%>
                                    <%--<th width="80" style="text-align: center;">序号</th>--%>
                                    <%--<th>活期宝收益</th>--%>
                                    <%--<th>活期宝金额</th>--%>
                                    <%--<th>活期宝综合年化</th>--%>
                                    <%--<th>定存宝收益</th>--%>
                                    <%--<th>定存宝金额</th>--%>
                                    <%--<th>定存宝综合年化</th>--%>
                                    <%--<th>综合年化</th>--%>
                                    <%--<th>总投资人数</th>--%>
                                    <%--<th>统计日期</th>--%>
                                <%--</tr>--%>
                                <%--</thead>--%>
                                <%--<tbody style="font-size: 12px;">--%>
                                <%--<c:forEach items="${page.content}" var="platformApr" varStatus="status">--%>
                                    <%--<tr>--%>
                                        <%--<td class="table_no" width="80" align="center"></td>--%>
                                        <%--<td><c:out value="${platformApr.allDayloanIncome}"/></td>--%>
                                        <%--<td><c:out value="${platformApr.allDayloanFund}"/></td>--%>
                                        <%--<td><c:out value="${platformApr.dayloanApr}"/></td>--%>
                                        <%--<td><c:out value="${platformApr.allDepositIncome}"/></td>--%>
                                        <%--<td><c:out value="${platformApr.allDepositFund}"/></td>--%>
                                        <%--<td><c:out value="${platformApr.depositApr}"/></td>--%>
                                        <%--<td><c:out value="${platformApr.apr}"/></td>--%>
                                        <%--<td><c:out value="${platformApr.totalPersons}"/></td>--%>
                                        <%--<td><fmt:formatDate value="${platformApr.createdDate}"--%>
                                                            <%--pattern="yyyy年MM月dd日"/></td>--%>
                                    <%--</tr>--%>
                                <%--</c:forEach>--%>
                                <%--</tbody>--%>
                            <%--</table>--%>
                            <%--<tags:pagination page="${page}"></tags:pagination>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>


    </div>
</form>
<%@include file="/static/inc/footer.inc" %>
</body>
</html>