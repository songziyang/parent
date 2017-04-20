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
        <style>
            .form-group label {
                position: absolute;
                left: 10px;
                top: 7px;
                color: #999;
            }
            .tickets-container {
                padding: 0px;
            }
        </style>
        <script>
            $(function() {
                $("#btn_success").click(function() {
                    $("#form").attr("action", "${ctx}/userinfo/banksDel/validate/success");
                    $("#btn_submit").click();
                });

                $("#btn_failure").click(function() {
                    if ($("#reason").val() == '') {
                        alert("请填写审核失败原因");
                        return;
                    }
                    $("#form").attr("action", "${ctx}/userinfo/banksDel/validate/failure");
                    $("#btn_submit").click();
                });
            });
        </script>
    </head>
    <body>
        <!-- Page Breadcrumb -->
        <div class="page-breadcrumbs">
            <ul class="breadcrumb" style="line-height: 40px;">
                <li><i class="fa fa-home"></i> <a href="#">前台用户管理</a></li>
                <li><a href="#">银行卡解绑</a></li>
            </ul>
        </div>
        <!-- /Page Breadcrumb -->
        <!-- Page Header -->
        <div class="page-header position-relative">
            <div class="header-title">
                <h1>银行卡解绑</h1>
            </div>
            <!--Header Buttons-->
            <div class="header-buttons">
                <a class="sidebar-toggler" href="#">
                    <i class="fa fa-arrows-h"></i>
                </a>
                <a class="refresh" id="refresh-toggler" href="">
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
        <div class="page-body">
            <div class="row">
                <div class="col-xs-12 col-md-12">
                    <div class="row">
                        <div class="col-lg-12 col-sm-12 col-xs-12">
                            <div class="widget">
                                <div class="widget-header bordered-bottom bordered-sky">
                                    <i class="widget-icon fa fa-arrows-v blue"></i> <span
                                        class="widget-caption">银行卡解绑</span>

                                    <div class="widget-buttons">
                                        <a href="#"> </a> <a href="#" data-toggle="collapse"> <i
                                            class="fa fa-plus blue"></i>
                                    </a>
                                    </div>
                                    <!--Widget Buttons-->
                                </div>
                                <!--Widget Header-->
                                <div class="widget-body">

                                    <div class="tickets-container">
                                        <ul class="tickets-list">
                                            <li class="ticket-item">
                                                <div class="row">
                                                    <div class="ticket-type   col-sm-3 ">
                                                        <span class="divider hidden-xs"></span> <span
                                                            class="user-company">真实姓名：</span> <span
                                                            class="user-company">
                                                        <c:out value="${banksDel.user.realName}"/>
                                                        </span>
                                                    </div>
                                                    <div class="ticket-type   col-sm-3 ">
                                                        <span class="divider hidden-xs"></span> <span
                                                            class="user-company">身份证号：</span> <span
                                                            class="user-company">
                                                        <c:out value="${banksDel.user.idCard}"/>
                                                        </span>
                                                    </div>
                                                    <div class="ticket-type   col-sm-3 ">
                                                        <span class="divider hidden-xs"></span> <span
                                                            class="user-company">手机号：</span> <span
                                                            class="user-company">
                                                        <c:out value="${banksDel.user.mobile}"/>
                                                        </span>
                                                    </div>
                                                    <div class="ticket-type  col-sm-3  ">
                                                        <span class="divider hidden-xs"></span> <span
                                                            class="user-company">银行卡号：</span> <span
                                                            class="user-company"><c:out
                                                            value="${userBanks.cardNo}"/></span>
                                                    </div>
                                                </div>
                                            </li>
                                            <li class="ticket-item">
                                                <div class="row">
                                                    <div class="ticket-type  col-sm-3  ">
                                                        <span class="divider hidden-xs"></span> <span
                                                            class="user-company">银行名称：</span> <span
                                                            class="user-company"><c:out
                                                            value="${userBanks.bankName}"/></span>
                                                    </div>
                                                    <div class="ticket-type   col-sm-3 ">
                                                        <span class="divider hidden-xs"></span> <span
                                                            class="user-company">开户行省份：</span> <span
                                                            class="user-company">
                                                        <c:out value="${userBanks.bankProvince}"/>
                                                        </span>
                                                    </div>
                                                    <div class="ticket-type   col-sm-3 ">
                                                        <span class="divider hidden-xs"></span> <span
                                                            class="user-company">开户行城市：</span> <span
                                                            class="user-company">
                                                        <c:out value="${userBanks.bankCity}"/>
                                                        </span>
                                                    </div>
                                                    <div class="ticket-type   col-sm-3 ">
                                                        <span class="divider hidden-xs"></span> <span
                                                            class="user-company">开户行：</span> <span
                                                            class="user-company">
                                                        <c:out value="${userBanks.bankOpening}"/>
                                                        </span>
                                                    </div>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                    <br/>
                                    <div style='overflow:hidden ;text-align:center'>
                                        <a href="${banksDel.idCardUrl}" target="_blank">
                                            <img style="width:800px; height: 480px;" src="${banksDel.idCardUrl}" title="点击查看图片" />
                                        </a>
                                        <a href="${banksDel.bankUrl}" target="_blank">
                                            <img style="width:800px; height: 480px;" src="${banksDel.bankUrl}"  title="点击查看图片"/>
                                        </a>
                                    </div>
                                    <br/>
                                    <form id="form" method="POST">
                                        <input type="hidden" name="banksDelId" value="${banksDel.id}"/>
                                        <c:if test="${banksDel.status ne 1}">
                                            <div class="row">
                                                <div class="col-sm-12">
                                                    <div class="form-group">
                                                        <span class="form-group input-icon icon-right">
                                                            <label>失败原因:</label>
                                                            <textarea style="padding-left: 100px;" class="form-control"
                                                                  rows="4" name="reason" maxlength="100" id="reason"
                                                                  data-bv-stringlength-max="100"
                                                                  <c:if test="${banksDel.status eq 2}">readonly</c:if>
                                                                  data-bv-stringlength-message="不能超过100个字"><c:out
                                                                value="${banksDel.reason}"/></textarea>
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:if>
                                        <c:if test="${banksDel.status eq 0}">
                                            <div style="text-align: right; margin-bottom: 10px;">
                                                <button type="button" id="btn_success" class="btn btn-labeled btn-success">
                                                    <i class="btn-label glyphicon glyphicon-ok"></i>审核成功
                                                </button>
                                                <button type="button" id="btn_failure" class="btn btn-labeled btn-danger">
                                                    <i class="btn-label glyphicon glyphicon-remove"></i>审核失败
                                                </button>
                                            </div>
                                        </c:if>
                                        <div style="display: none;">
                                            <button type="submit" id="btn_submit" class="btn btn-labeled btn-success">
                                                <i class="btn-label glyphicon glyphicon-ok"></i>
                                            </button>
                                        </div>
                                        <br/>
                                    </form>
                                </div>
                                <!--Widget Body-->
                            </div>
                            <!--Widget-->
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="/static/inc/footer.inc" %>
    </body>
</html>