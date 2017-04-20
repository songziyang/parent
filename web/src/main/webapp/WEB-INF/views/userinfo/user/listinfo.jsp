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
    <script type="text/javascript">
        function checkUserInfo() {
            var condition = $("#condition").val();
            if (null == condition || "" == condition) {
                alert("请填写用户信息！！！");
            } else {
                var url = "${ctx}/userinfo/user/findReferralUser";
                $.post(url, {"condition": $("#condition").val()}, function (data) {
                    var html = "";
                    if (data == "" || data.length == 0) {
                        html = '<p style="color: red">没有查到该用户，请重新输入用户信息</p>';
                    } else {
                        $(data).each(function (index) {
                            var user = data[index];
                            html += '<p>' +
                                    '用户名: ' + user.username + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' +
                                    '手机号: ' + user.mobile + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' +
                                    '<button type="button" class="btn btn-primary btn-success" onclick="selectUser(' + user.id + ');">设置</button>' +
                                    '</p>';
                        })
                    }
                    $("#ct1").html(html);
                });
            }
        }

        function selectUser(id) {
            bootbox.dialog({
                message: "您确定要设置该推荐人么？",
                title: "设置提示",
                className: "modal-darkorange",
                buttons: {
                    success: {
                        label: "确认",
                        className: "btn-default",
                        callback: function () {
                            window.location.href = '${ctx}/userinfo/user/userRefferral/' + $("#hid_userId").val() + '/' + id;
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

        function isLogining(islogin, id) {
            bootbox.dialog({
                message: islogin == 1 ? "您确定要解锁该用户么？" : "您确定要锁定该用户么？",
                title: islogin == 1 ? "解锁提示" : "锁定提示",
                className: "modal-darkorange",
                buttons: {
                    success: {
                        label: "确认",
                        className: "btn-default",
                        callback: function () {
                            if (islogin == 1) {
                                window.location.href = '${ctx}/userinfo/user/userLogin/' + id;
                            }
                            if (islogin == 2) {
                                window.location.href = '${ctx}/userinfo/user/userUnLogin/' + id;
                            }
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

        function setUserType(id) {
            bootbox.dialog({
                message: "您确定要设置该用户类型么？",
                title: "设置提示",
                className: "modal-darkorange",
                buttons: {
                    success: {
                        label: "确认",
                        className: "btn-default",
                        callback: function () {
                            window.location.href = '${ctx}/userinfo/user/userType/' + id + '/' + $("#userType").val();
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


        function setUserLeve(id) {
            bootbox.dialog({
                message: "您确定要设置该用户等级么？",
                title: "设置提示",
                className: "modal-darkorange",
                buttons: {
                    success: {
                        label: "确认",
                        className: "btn-default",
                        callback: function () {
                            window.location.href = '${ctx}/userinfo/user/userLeve/' + id + '/' + $("#userLeve").val();
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

        function setUserMobile(id) {
            bootbox.dialog({
                message: "您确定要设置该用户手机号么？",
                title: "设置提示",
                className: "modal-darkorange",
                buttons: {
                    success: {
                        label: "确认",
                        className: "btn-default",
                        callback: function () {
                            window.location.href = '${ctx}/userinfo/user/userMobile/' + id + '?userMobile=' + $("#userMobile").val();
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

        function setWechatNumber(id) {
            bootbox.dialog({
                message: "您确定要设置该微信号么？",
                title: "设置提示",
                className: "modal-darkorange",
                buttons: {
                    success: {
                        label: "确认",
                        className: "btn-default",
                        callback: function () {
                            window.location.href = '${ctx}/userinfo/user/wechatNumber/' + id + '?wechatNumber=' + $("#wechatNumber").val();
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
        function setQqNumber(id) {
            bootbox.dialog({
                message: "您确定要设置该QQ号么？",
                title: "设置提示",
                className: "modal-darkorange",
                buttons: {
                    success: {
                        label: "确认",
                        className: "btn-default",
                        callback: function () {
                            window.location.href = '${ctx}/userinfo/user/qqNumber/' + id + '?qqNumber=' + $("#qqNumber").val();
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

        function setBuyLimit(id) {
            var str = $("#buyLimit").val();
            var r = /^\+?[0-9][0-9]*$/;　　//正整数
            if (!r.test(str)) {
                Notify("格式错误！", 'top-left', '5000', 'warning', 'fa-warning', true);
                return;
            }
            bootbox.dialog({
                message: "您确定要设置购买限额么？",
                title: "设置提示",
                className: "modal-darkorange",
                buttons: {
                    success: {
                        label: "确认",
                        className: "btn-default",
                        callback: function () {
                            window.location.href = '${ctx}/userinfo/user/buyLimit/' + id + '?buyLimit=' + str;
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

        function setRedeemLimit(id) {
            var str = $("#redeemLimit").val();
            var r = /^\+?[0-9][0-9]*$/;　　//正整数
            if (!r.test(str)) {
                Notify("格式错误！", 'top-left', '5000', 'warning', 'fa-warning', true);
                return;
            }
            bootbox.dialog({
                message: "您确定要设置该赎回限额么？",
                title: "设置提示",
                className: "modal-darkorange",
                buttons: {
                    success: {
                        label: "确认",
                        className: "btn-default",
                        callback: function () {
                            window.location.href = '${ctx}/userinfo/user/redeemLimit/' + id + '?redeemLimit=' + str;
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


        function unbindCard(id) {
            bootbox.dialog({
                message: "您确定要解锁绑卡吗？",
                title: "设置提示",
                className: "modal-darkorange",
                buttons: {
                    success: {
                        label: "确认",
                        className: "btn-default",
                        callback: function () {
                            window.location.href = '${ctx}/userinfo/user/unbindCard/' + id;
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
        function setUserReferral() {
            $("#hid_refferral").click();
        }

        function ensureAddsignNumber(userId) {
            $.ajax({
                url: '${ctx}/activity/qingming/addSignNumber/' + userId,
                method : "GET",
                success: function(data) {
                    var flag = data.flag;
                    if (flag == "noIdCard") {
                        alert("该用户未实名认证, 无法继续添加.")
                    } else if (flag == "noBankCard") {
                        alert("该用户未绑定银行卡, 无法继续添加")
                    } else {
                        $("#sign").text(data.sign);
                        $("#surplusSign").text(data.surplusSign);
                        alert("操作成功, 目前用户活动总抽奖次数为" + data.sign + "次, 剩余活动抽奖次数为" + data.surplusSign + "次");
                    }
                }
            })
        }

        function addSignNumber(userId) {
            bootbox.dialog({
                message: "您确定要为该用户添加抽奖次数吗？",
                title: "设置提示",
                className: "modal-darkorange",
                buttons: {
                    success: {
                        label: "确认",
                        className: "btn-default",
                        callback: function () {
                            ensureAddsignNumber(userId)
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

        function setAccountType(id) {
            bootbox.dialog({
                message: "您确定要设置该用户电子账户类型么？",
                title: "设置提示",
                className: "modal-darkorange",
                buttons: {
                    success: {
                        label: "确认",
                        className: "btn-default",
                        callback: function () {
                            window.location.href = '${ctx}/userinfo/user/accountttype/' + id + '/' + $("#accountType").val();
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


<div id="rechargeModal" class="modal fade modal-darkorange"
     tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content"></div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>


<div id="myModal" class="modal fade modal-darkorange" tabindex="-1"
     role="dialog" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content"></div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<!-- Page Breadcrumb -->
<div class="page-breadcrumbs">
    <ul class="breadcrumb" style="line-height: 40px;">
        <li><i class="fa fa-home"></i> <a href="#">前台用户管理</a></li>
        <li><a href="#">用户信息管理</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>用户信息管理</h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
        </a> <a class="refresh" id="refresh-toggler" href=""> <i
            class="glyphicon glyphicon-refresh"></i>
    </a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
            class="glyphicon glyphicon-fullscreen"></i>
    </a>
    </div>
    <!--Header Buttons End-->
</div>
<!-- /Page Header -->
<!-- Page Body -->
<div class="modal fade" id="myModal2" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">×
                </button>
                <h4 class="modal-title" id="myModalLabel">请查找用户信息</h4>
            </div>
            <div class="modal-body">
						<span class="form-group input-icon icon-right">
							<label>用户名或手机号：
                                <sup style="color: red;">&nbsp;*</sup>
                            </label>
							<input type="text" name="condition" id="condition"
                                   class="form-control" tabindex="1"
                                   style="padding-left: 30px; text-align: center" required>
							<div style="margin-top: 20px;" id="ct1"></div>
                        </span>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary"
                        onclick="javaScript:checkUserInfo();">搜索
                </button>
            </div>
        </div>
    </div>
</div>
<div class="page-body">
    <div class="row">
        <div class="col-xs-12 col-md-12">
            <div class="row">
                <div class="col-lg-12 col-sm-12 col-xs-12">
                    <div class="widget">
                        <div class="widget-header bordered-bottom bordered-sky">
                            <i class="widget-icon fa fa-arrows-v blue"></i> <span
                                class="widget-caption">用户基础信息</span>

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
                                            <input type="hidden" id="hid_userId" value="${user.id}"/>

                                            <div class="ticket-type  col-sm-3  ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">用户名：</span> <span
                                                    class="user-company">

                                                <c:choose>
                                                    <c:when test="${user.userLeve.gradeNum eq 1}"><img src="${ctx}/static/lib/images/vip1.png" /></c:when>
                                                    <c:when test="${user.userLeve.gradeNum eq 2}"><img src="${ctx}/static/lib/images/vip2.png" /></c:when>
                                                </c:choose>
                                                <c:out
                                                    value="${user.username}"/></span>
                                            </div>
                                            <div class="ticket-type   col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">手机号：</span> <span
                                                    class="user-company">
                                                    <input type="text" id="userMobile" value="${user.mobile}"/></span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span>
                                                <shiro:hasPermission name="user_mobile">
                                                    <a class="btn btn-labeled btn-sky"
                                                       href="javascript:setUserMobile('<c:out value="${user.id}"/>');"/>
                                                    <i class="btn-label glyphicon glyphicon-wrench"></i>设置</a>
                                                </shiro:hasPermission>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">注册：</span> <span
                                                    class="user-company"><fmt:formatDate
                                                    value="${user.createDate}" pattern="yyyy年MM月dd日HH时mm分"/></span>
                                            </div>
                                        </div>
                                    </li>
                                    <li class="ticket-item">
                                        <div class="row">
                                            <div class="ticket-time   col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">真实姓名：</span> <span
                                                    class="user-company"><c:out
                                                    value="${user.realName }"/></span>
                                            </div>
                                            <div class="ticket-time   col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">登陆错误次数：</span> <span
                                                    class="user-company"><c:out
                                                    value="${empty user.loginNum ? 0 : user.loginNum}"/></span>
                                            </div>
                                            <div class="ticket-type   col-sm-3">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">推荐人手机号：</span> <span
                                                    class="user-company" id="referralMobile"><c:out
                                                    value="${user.referralMobile}"/></span>
                                            </div>
                                            <c:if test="${user.referralMobile eq null or user.referralMobile eq ''}">
                                                <div class="ticket-type  col-sm-3 ">
                                                    <span class="divider hidden-xs"></span>
                                                    <shiro:hasPermission name="user_referral">
                                                        <a class="btn btn-labeled btn-sky"
                                                           href="javascript:setUserReferral();"/>
                                                        <i class="btn-label glyphicon glyphicon-wrench"></i>设置</a>
                                                    </shiro:hasPermission>
                                                </div>
                                            </c:if>
                                        </div>
                                    </li>
                                    <li class="ticket-item">
                                        <div class="row">
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">锁定状态：</span> <span
                                                    class="user-company"><c:out
                                                    value="${user.isLogin eq -1 ? '锁定' :'未锁'}"/></span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">锁定时间：</span> <span
                                                    class="user-company">
                                                <fmt:formatDate
                                                        value="${empty user.lockTimeDate ? null :user.lockTimeDate}"
                                                        pattern="yyyy年MM月dd日 HH时mm分"/></span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span>
                                                <c:if test="${user.isLogin eq -1 }">
                                                    <shiro:hasPermission name="user_login">
                                                        <a class="btn btn-labeled btn-success"
                                                           href="javascript:isLogining('1','<c:out value="${user.id}" />');"><i
                                                                class="btn-label glyphicon glyphicon-wrench"></i>解锁</a>
                                                    </shiro:hasPermission>
                                                </c:if>

                                                <c:if test="${user.isLogin ne -1 }">
                                                    <shiro:hasPermission name="user_unlogin">
                                                        <a class="btn btn-labeled btn-danger"
                                                           href="javascript:isLogining('2','<c:out value="${user.id}" />');"/>
                                                        <i class="btn-label glyphicon glyphicon-lock"></i>锁定</a>
                                                    </shiro:hasPermission>
                                                </c:if>
                                            </div>
                                            <div class="ticket-type  col-sm-3  ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">推荐码：</span> <span
                                                    class="user-company"><c:out
                                                    value="${user.referralCode}"/></span>
                                            </div>
                                        </div>
                                    </li>
                                    <li class="ticket-item">
                                        <div class="row">
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company"
                                                    style="float: left; padding-right: 10px;">用户类型:</span> <select
                                                    id="userType" name="userType" class="form-control"
                                                    style="width: 120px; float: left">
                                                <option value="0"
                                                        <c:if test="${ user.userType == 0}">selected="selected"</c:if>>
                                                    普通用户
                                                </option>
                                                <option value="1"
                                                        <c:if test="${ user.userType == 1 }">selected="selected"</c:if>>
                                                    老用户
                                                </option>
                                                <option value="2"
                                                        <c:if test="${ user.userType == 2 }">selected="selected"</c:if>>
                                                    股东用户
                                                </option>
                                                <option value="3"
                                                        <c:if test="${ user.userType == 3 }">selected="selected"</c:if>>
                                                    业务员用户
                                                </option>
                                                <option value="4"
                                                        <c:if test="${ user.userType == 4 }">selected="selected"</c:if>>
                                                    新鲜说用户
                                                </option>
                                                <option value="5"
                                                        <c:if test="${ user.userType == 5 }">selected="selected"</c:if>>
                                                    麦罗用户
                                                </option>
                                                <option value="6"
                                                        <c:if test="${ user.userType == 6 }">selected="selected"</c:if>>
                                                    涌金门
                                                </option>
                                                <option value="7"
                                                        <c:if test="${ user.userType == 7 }">selected="selected"</c:if>>
                                                    金贸街
                                                </option>
                                            </select>
                                            </div>

                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span>
                                                <shiro:hasPermission name="user_type">
                                                    <a class="btn btn-labeled btn-sky"
                                                       href="javascript:setUserType('<c:out value="${user.id}"/>');"/>
                                                    <i class="btn-label glyphicon glyphicon-wrench"></i>设置</a>
                                                </shiro:hasPermission>
                                            </div>

                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company"
                                                    style="float: left; padding-right: 10px;">用户等级:</span> <select
                                                    id="userLeve" name="userLeve" class="form-control"
                                                    style="width: 120px; float: left">
                                                <c:forEach items="${vipGrades}" var="vipGrade">
                                                    <option value="${vipGrade.id}"
                                                            <c:if test="${ vipGrade.id == user.userLeve.id}">selected="selected"</c:if>>
                                                            ${vipGrade.gradeName}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                            </div>

                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span>
                                                <shiro:hasPermission name="user_leve">
                                                    <a class="btn btn-labeled btn-sky"
                                                       href="javascript:setUserLeve('<c:out value="${user.id}"/>');"/>
                                                    <i class="btn-label glyphicon glyphicon-wrench"></i>设置</a>
                                                </shiro:hasPermission>
                                            </div>
                                        </div>
                                    </li>
                                    <li class="ticket-item">
                                        <div class="row">
                                            <div class="ticket-type   col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">微信号：</span> <span
                                                    class="user-company">
                                                    <input type="text" id="wechatNumber" value="${user.wechatNumber}"/></span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span>
                                                <shiro:hasPermission name="user_wechatnumber">
                                                    <a class="btn btn-labeled btn-sky"
                                                       href="javascript:setWechatNumber('<c:out value="${user.id}"/>');"/>
                                                    <i class="btn-label glyphicon glyphicon-wrench"></i>设置</a>
                                                </shiro:hasPermission>
                                            </div>
                                            <div class="ticket-type   col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">QQ号：</span> <span
                                                    class="user-company">
                                                    <input type="text" id="qqNumber" value="${user.qqNumber}"/></span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span>
                                                <shiro:hasPermission name="user_qqnumber">
                                                    <a class="btn btn-labeled btn-sky"
                                                       href="javascript:setQqNumber('<c:out value="${user.id}"/>');"/>
                                                    <i class="btn-label glyphicon glyphicon-wrench"></i>设置</a>
                                                </shiro:hasPermission>
                                            </div>
                                        </div>
                                    </li>

                                    <li class="ticket-item">
                                        <div class="row">
                                            <div class="ticket-type   col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">购买限额：</span> <span
                                                    class="user-company">
                                                    <input type="text" id="buyLimit"
                                                           value="${user.userInvestinfo.buyLimit}"/></span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span>
                                                <shiro:hasPermission name="user_buylimit">
                                                    <a class="btn btn-labeled btn-sky"
                                                       href="javascript:setBuyLimit('<c:out value="${user.id}"/>');"/>
                                                    <i class="btn-label glyphicon glyphicon-wrench"></i>设置</a>
                                                </shiro:hasPermission>
                                            </div>
                                            <div class="ticket-type   col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">赎回限额：</span> <span
                                                    class="user-company">
                                                    <input type="text" id="redeemLimit"
                                                           value="${user.userInvestinfo.redeemLimit}"/></span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span>
                                                <shiro:hasPermission name="user_redeemlimit">
                                                    <a class="btn btn-labeled btn-sky"
                                                       href="javascript:setRedeemLimit('<c:out value="${user.id}"/>');"/>
                                                    <i class="btn-label glyphicon glyphicon-wrench"></i>设置</a>
                                                </shiro:hasPermission>
                                            </div>
                                        </div>
                                    </li>

                                    <li class="ticket-item">
                                        <div class="row">
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span>
                                                <shiro:hasPermission name="user_recharge">
                                                    <a class="btn btn-labeled btn-sky"
                                                       data-target="#rechargeModal" data-toggle="modal"
                                                       href='${ctx}/userinfo/user/showRechargeUser/<c:out value="${user.id}" />'/>
                                                    <i class="btn-label glyphicon glyphicon-usd"></i>充值</a>
                                                </shiro:hasPermission>
                                            </div>
                                            <div class="ticket-type   col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">绑卡错误次数：</span> <span
                                                    class="user-company">
                                                           <c:out value="${user.userInfo.egg}"/></span>
                                            </div>
                                            <shiro:hasPermission name="user_unbindcard">
                                                <c:if test="${user.userInfo.egg >= 3}">
                                                    <div class="ticket-type  col-sm-3 ">
                                                        <span class="divider hidden-xs"></span>
                                                        <a class="btn btn-labeled btn-sky"
                                                           href="javascript:unbindCard('<c:out value="${user.id}"/>');"/>
                                                        <i class="btn-label glyphicon glyphicon-wrench"></i>解锁绑卡</a>
                                                        </div>
                                                </c:if>
                                            </shiro:hasPermission>
                                            <div style="display: none;">
                                                <input type="hidden" id="hid_refferral"
                                                       data-toggle="modal"
                                                       data-target="#myModal2"/>
                                            </div>
                                        </div>
                                    </li>

                                    <li class="ticket-item">
                                        <div class="row">
                                            <div class="ticket-type   col-sm-3 ">
                                                <span class="divider hidden-xs"></span>
                                                <span class="user-company">电子账户：</span>
                                                <span class="user-company">
                                                    <c:out value="${user.accountid}"></c:out>
                                                </span>
                                            </div>
                                            <div class="ticket-type   col-sm-3 ">
                                                <span class="divider hidden-xs"></span>
                                                <span class="user-company">电子账户手机号：</span>
                                                <span class="user-company">
                                                    <c:out value="${user.accountMobile}"></c:out>
                                                </span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company"
                                                    style="float: left; padding-right: 10px;">电子账户类型:</span>
                                                <select
                                                    id="accountType" name="accountType" class="form-control"
                                                    style="width: 120px; float: left">
                                                    <option value="1"
                                                        <c:if test="${ user.accountType eq 1}">selected="selected"</c:if>>
                                                            普通用户
                                                    </option>
                                                    <option value="2"
                                                            <c:if test="${ user.accountType eq 2}">selected="selected"</c:if>>
                                                            债权用户
                                                    </option>
                                                    <option value="3"
                                                            <c:if test="${ user.accountType eq 3}">selected="selected"</c:if>>
                                                            美利用户
                                                    </option>
                                                    <option value="4"
                                                            <c:if test="${ user.accountType eq 4}">selected="selected"</c:if>>
                                                            红包账户
                                                    </option>
                                                    <option value="5"
                                                            <c:if test="${ user.accountType eq 5}">selected="selected"</c:if>>
                                                            手续费账户
                                                    </option>
                                                </select>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span>
                                                <shiro:hasPermission name="user_accounttype">
                                                    <a class="btn btn-labeled btn-sky"
                                                       href="javascript:setAccountType('<c:out value="${user.id}"/>');"/>
                                                    <i class="btn-label glyphicon glyphicon-wrench"></i>设置</a>
                                                </shiro:hasPermission>
                                            </div>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <!--Widget Body-->
                    </div>
                    <!--Widget-->
                </div>
            </div>


            <div class="row">
                <div class="col-lg-12 col-sm-12 col-xs-12">
                    <div class="widget">
                        <div class="widget-header bordered-bottom bordered-sky">
                            <i class="widget-icon fa fa-arrows-v blue"></i> <span
                                class="widget-caption">用户验证信息</span>

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
                                            <div class="ticket-time   col-sm-2 ">
                                                <div class="divider hidden-md hidden-sm hidden-xs"></div>
                                                <i class="glyphicon glyphicon-user"></i> <span
                                                    class="user-company">身份验证</span>
                                            </div>
                                            <div class="ticket-type  col-sm-5 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">身份证号：</span> <span
                                                    class="user-company"><c:out
                                                    value="${user.idCard}"/></span>
                                            </div>
                                            <div class="ticket-type  col-sm-2 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">验证状态：</span>
                                                <c:if test="${!empty user.idCard }">
                                                    <div class="ticket-state bg-palegreen">
                                                        <i class="fa fa-check"></i>
                                                    </div>
                                                </c:if>
                                                <c:if test="${empty user.idCard }">
                                                    <div class="ticket-state bg-darkorange">
                                                        <i class="fa fa-times"></i>
                                                    </div>
                                                </c:if>
                                            </div>
                                            <div class="ticket-type  col-sm-1 "></div>
                                            <c:if test="${empty user.idCard }">
                                                <shiro:hasPermission name="user_verify">
                                                    <div class="ticket-type  col-sm-2 ">
                                                        <span class="divider hidden-xs"></span> <a
                                                            class="btn btn-labeled btn-success" data-toggle="modal"
                                                            data-target="#myModal"
                                                            href="${ctx}/userinfo/user/showValidataCard/<c:out value="${user.id}" />"><i
                                                            class="btn-label glyphicon glyphicon-wrench"></i>验证</a>
                                                    </div>
                                                </shiro:hasPermission>
                                            </c:if>
                                        </div>
                                    </li>
                                </ul>
                            </div>

                        </div>
                        <!--Widget Body-->
                    </div>
                    <!--Widget-->
                </div>
            </div>


            <div class="row">
                <div class="col-lg-12 col-sm-12 col-xs-12">
                    <div class="widget">
                        <div class="widget-header bordered-bottom bordered-sky">
                            <i class="widget-icon fa fa-arrows-v blue"></i> <span
                                class="widget-caption">用户资金信息</span>

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
                                    <!-- -->
                                    <li class="ticket-item">
                                        <div class="row">
                                            <div class="ticket-time   col-sm-3 ">
                                                <div class="divider hidden-md hidden-sm hidden-xs"></div>
                                                <i class="glyphicon glyphicon-th-list"></i> <span
                                                    class="user-company">用户积分</span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">累计积分：</span> <span
                                                    class="user-company">
                                                 <a href="${ctx}/redpacket/integralrecord/listIntegralRecord?mobile=${user.mobile}">
                                                     <c:out
                                                             value="${empty user.userIntegral.totalIntegral ? 0 : user.userIntegral.totalIntegral }"/>
                                                 </a>
                                            </span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">可用积分：</span> <span
                                                    class="user-company">
                                                 <a href="${ctx}/redpacket/integralrecord/listIntegralRecord?mobile=${user.mobile}">
                                                     <c:out
                                                             value="${empty user.userIntegral.integral ? 0 : user.userIntegral.integral }"/>
                                                 </a>
                                            </span>
                                            </div>

                                        </div>
                                    </li>


                                    <li class="ticket-item">
                                        <div class="row">
                                            <div class="ticket-time   col-sm-3 ">
                                                <div class="divider hidden-md hidden-sm hidden-xs"></div>
                                                <i class="glyphicon glyphicon-th-list"></i> <span
                                                    class="user-company">
                                                    <a href="${ctx}/userinfo/usermoneyinfo/findUserFundRecord/${user.id}?fundType=-1">
                                                        充值提现</a></span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">
                                                     <a href="${ctx}/userinfo/usermoneyinfo/findUserFundRecord/${user.id}?fundType=0">
                                                         累计充值</a>：</span> <span
                                                    class="user-company"><c:out
                                                    value="${empty user.userInfo.allRecharge ? 0 : user.userInfo.allRecharge}"/></span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">
                                                 <a href="${ctx}/userinfo/usermoneyinfo/findUserFundRecord/${user.id}?fundType=1">
                                                     累计提现</a>：</span> <span
                                                    class="user-company"><c:out
                                                    value="${empty user.userInfo.alliWthdraw ? 0 : user.userInfo.alliWthdraw }"/></span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">累计提现扣费：</span> <span
                                                    class="user-company"><c:out
                                                    value="${empty user.userInfo.mangeFee ? 0 : user.userInfo.mangeFee }"/></span>
                                            </div>
                                        </div>
                                    </li>


                                    <li class="ticket-item">
                                        <div class="row">
                                            <div class="ticket-time   col-sm-3 ">
                                                <div class="divider hidden-md hidden-sm hidden-xs"></div>
                                                <i class="glyphicon glyphicon-th-list"></i>
                                                <span class="user-company">
                                                    <a href="${ctx}/userinfo/usermoneyinfo/usermoneylist?mobile=${user.mobile}">用户资金</a>
                                                </span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">总资金：</span> <span
                                                    class="user-company">
                                                <c:out value="${empty user.userMoney.totalFund ? 0 : user.userMoney.totalFund }"/></span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">可用资金：</span> <span
                                                    class="user-company">
                                                <c:out value="${empty user.userMoney.usableFund ? 0 : user.userMoney.usableFund}"/></span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">冻结资金：</span> <span
                                                    class="user-company">
                                                <c:out value="${empty user.userMoney.freezeFund ? 0 : user.userMoney.freezeFund}"/></span>
                                            </div>
                                        </div>
                                    </li>

                                    <li class="ticket-item">
                                        <div class="row">
                                            <div class="ticket-time   col-sm-3 ">
                                                <div class="divider hidden-md hidden-sm hidden-xs"></div>
                                                <i class="glyphicon glyphicon-th-list"></i> <span
                                                    class="user-company">
                                                    <a href="${ctx}/traderecord/expmoney/list?mobile=${user.mobile}">
                                                        用户体验金
                                                    </a></span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">总体验金：</span> <span
                                                    class="user-company">
                                                <c:out value="${empty user.userExMoney.amount ? 0 : user.userExMoney.amount}"/></span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">可用体验金：</span> <span
                                                    class="user-company">
                                                <c:out value="${empty user.userExMoney.ableMoney ? 0 : user.userExMoney.ableMoney}"/></span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">冻结体验金：</span> <span
                                                    class="user-company">
                                                <c:out value="${empty user.userExMoney.blockedMoney ? 0 : user.userExMoney.blockedMoney}"/></span>
                                            </div>
                                        </div>
                                    </li>

                                    <li class="ticket-item">
                                        <div class="row">
                                            <div class="ticket-time   col-sm-3 ">
                                                <div class="divider hidden-md hidden-sm hidden-xs"></div>
                                                <i class="glyphicon glyphicon-th-list"></i> <span
                                                    class="user-company">用户投资</span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">总投资金额：</span> <span
                                                    class="user-company">
                                                <c:out value="${empty user.userInvestinfo.allInvest ? 0 : user.userInvestinfo.allInvest }"/></span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">
                                                    <a href="${ctx}/traderecord/current/listCurrentRecored?mobile=${user.mobile}">
                                                        活期宝</a>：</span> <span
                                                    class="user-company">
                                                <c:out value="${empty user.userInvestinfo.allInvestDayloan ? 0 : user.userInvestinfo.allInvestDayloan }"/></span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">
                                                    <a href="${ctx}/traderecord/ragular/listRagularRecored?mobile=${user.mobile}">
                                                        定存宝</a>：
                                            </span> <span
                                                    class="user-company">
                                                <c:out value="${empty user.userInvestinfo.allInvestDeposit ? 0 : user.userInvestinfo.allInvestDeposit }"/></span>
                                            </div>
                                        </div>
                                    </li>

                                    <li class="ticket-item">
                                        <div class="row">
                                            <div class="ticket-time   col-sm-3 ">
                                                <div class="divider hidden-md hidden-sm hidden-xs"></div>
                                                <i class="glyphicon glyphicon-th-list"></i> <span
                                                    class="user-company">用户投资</span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">债权转让：</span> <span
                                                    class="user-company">
                                                <c:out value="${empty user.userInvestinfo.allInvestTransfer ? 0 : user.userInvestinfo.allInvestTransfer }"/></span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">基金投资：</span> <span
                                                    class="user-company">
                                                <c:out value="${empty user.userInvestinfo.allInvestWjb ? 0 : user.userInvestinfo.allInvestWjb }"/></span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">私人投资：</span> <span
                                                    class="user-company">
                                                <c:out value="${empty user.userInvestinfo.allInvestSelf ? 0 : user.userInvestinfo.allInvestSelf }"/></span>
                                            </div>
                                        </div>
                                    </li>

                                    <li class="ticket-item">
                                        <div class="row">
                                            <div class="ticket-time   col-sm-3 ">
                                                <div class="divider hidden-md hidden-sm hidden-xs"></div>
                                                <i class="glyphicon glyphicon-th-list"></i> <span
                                                    class="user-company">用户投资</span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">
                                                 <a href="${ctx}/traderecord/free/list?mobile=${user.mobile}">
                                                        随心存</a>：
                                                </span> <span
                                                    class="user-company">
                                                <c:out value="${empty user.userInvestinfo.allInvestFree ? 0 : user.userInvestinfo.allInvestFree }"/></span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                            </div>
                                        </div>
                                    </li>

                                    <li class="ticket-item">
                                        <div class="row">
                                            <div class="ticket-time   col-sm-3 ">
                                                <div class="divider hidden-md hidden-sm hidden-xs"></div>
                                                <i class="glyphicon glyphicon-th-list"></i> <span
                                                    class="user-company">用户收益</span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">
                                                    <a href="${ctx}/traderecord/income/list?mobile=${user.mobile}">总收益</a>：</span> <span
                                                    class="user-company">
                                                <c:out value="${empty user.userIncome.allIncome  ? 0 : user.userIncome.allIncome  }"/></span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">
                                                    <a href="${ctx}/traderecord/income/list?mobile=${user.mobile}&ptype=1">
                                                        活期收益</a>：</span> <span
                                                    class="user-company">
                                                <c:out value="${empty user.userIncome.allIncomeDayloan  ? 0 : user.userIncome.allIncomeDayloan  }"/></span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">
                                                <a href="${ctx}/traderecord/income/list?mobile=${user.mobile}&ptype=2">定存收益</a>：</span> <span
                                                    class="user-company">
                                                <c:out value="${empty user.userIncome.allIncomeDeposit ? 0 : user.userIncome.allIncomeDeposit }"/></span>
                                            </div>
                                        </div>
                                    </li>

                                    <li class="ticket-item">
                                        <div class="row">
                                            <div class="ticket-time   col-sm-3 ">
                                                <div class="divider hidden-md hidden-sm hidden-xs"></div>
                                                <i class="glyphicon glyphicon-th-list"></i> <span
                                                    class="user-company">用户收益</span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">基金收益：</span> <span
                                                    class="user-company">
                                                <c:out value="${empty user.userIncome.allIncomeWjb  ? 0 : user.userIncome.allIncomeWjb  }"/></span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">活动收益：</span> <span
                                                    class="user-company">
                                                <c:out value="${empty user.userIncome.allIncomeActivity ? 0 : user.userIncome.allIncomeActivity  }"/></span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">私人定制收益：</span> <span
                                                    class="user-company">
                                                <c:out value="${empty user.userIncome.allIncomeSelf ? 0 : user.userIncome.allIncomeSelf }"/></span>
                                            </div>
                                        </div>
                                    </li>

                                    <li class="ticket-item">
                                        <div class="row">
                                            <div class="ticket-time   col-sm-3 ">
                                                <div class="divider hidden-md hidden-sm hidden-xs"></div>
                                                <i class="glyphicon glyphicon-th-list"></i> <span
                                                    class="user-company">用户额外收益</span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">加息收益：</span> <span
                                                    class="user-company">
                                                <c:out value="${empty user.userIncome.allIncomeInterest ? 0 : user.userIncome.allIncomeInterest }"/></span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">体验金收益：</span> <span
                                                    class="user-company">
                                                <c:out value="${empty user.userIncome.allIncomeInvest ? 0 : user.userIncome.allIncomeInvest }"/></span>
                                            </div>
                                            <div class="ticket-type  col-sm-3 ">
                                                <span class="divider hidden-xs"></span> <span
                                                    class="user-company">VIP收益：</span> <span
                                                    class="user-company">
                                                <c:out value="${empty user.userIncome.allIncomeVip ? 0 : user.userIncome.allIncomeVip }"/></span>
                                            </div>
                                        </div>
                                    </li>


                                </ul>
                            </div>

                        </div>
                        <!--Widget Body-->
                    </div>
                    <!--Widget-->
                </div>
            </div>


            <div class="row">
                <div class="col-xs-12 col-md-12">
                    <div class="widget">
                        <div class="widget-header  bordered-bottom bordered-sky">
                            <span class="widget-caption">用户描述</span>
                        </div>
                        <div class="widget-body">
                            <div id="registration-form">
                                <form action="${ctx}/userinfo/user/saveDescribe" id="editForm"
                                      name="editForm" method="post" role="form">
                                    <input type="hidden" name="userId" value='<c:out value="${user.id}"/>'>

                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>用户描述</label>
												<textarea style="padding-left: 100px;" class="form-control"
                                                          rows="4" name="remark" maxlength="100"
                                                          data-bv-stringlength-max="100"
                                                          data-bv-stringlength-message="不能超过100个字"><c:out
                                                        value="${user.remark}"/></textarea>
											</span>
                                            </div>
                                        </div>
                                    </div>
                                    <shiro:hasPermission name="user_describe">
                                        <div style="text-align: right; margin-bottom: 10px;">
                                            <button type="submit" class="btn btn-labeled btn-success">
                                                <i class="btn-label glyphicon glyphicon-ok"></i>保存
                                            </button>
                                        </div>
                                    </shiro:hasPermission>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12 col-sm-12 col-xs-12">
                    <div class="widget">
                        <div class="widget-header bordered-bottom bordered-sky">
                            <i class="widget-icon fa fa-arrows-v blue"></i> <span
                                class="widget-caption">用户银行卡信息</span>

                            <div class="widget-buttons">
                                <a href="#"> </a> <a href="#" data-toggle="collapse"> <i
                                    class="fa fa-plus blue"></i>
                            </a>
                            </div>
                            <!--Widget Buttons-->
                        </div>
                        <!--Widget Header-->
                        <div class="widget-body">
                            <div class="flip-scroll">
                                <table class="table table-hover table-striped table-bordered">
                                    <thead style="font-size: 16px; font-weight: bold;">
                                    <tr>
                                        <th>银行卡号</th>
                                        <th>银行名称</th>
                                        <th>开户行</th>
                                        <th>开户行省份</th>
                                        <th>开户行城市</th>
                                        <th>是否是默认银行卡</th>
                                        <th>是否是电子账户</th>
                                    </tr>
                                    </thead>
                                    <tbody style="font-size: 12px;">
                                    <c:forEach items="${userCards}" var="userCard"
                                               varStatus="status">
                                        <c:if test="${userCard.state == 0}">
                                            <tr>
                                                <td><c:out value="${userCard.cardNo}"/></td>
                                                <td><c:out value="${userCard.bankName}"/></td>
                                                <td><c:out value="${userCard.bankOpening}"/></td>
                                                <td><c:out value="${userCard.bankProvince}"/></td>
                                                <td><c:out value="${userCard.bankCity}"/></td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${userCard.type == 0}">否</c:when>
                                                        <c:when test="${userCard.type == 1}">是</c:when>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${userCard.cardtype == 0}">否</c:when>
                                                        <c:when test="${userCard.cardtype == 1}">是</c:when>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
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