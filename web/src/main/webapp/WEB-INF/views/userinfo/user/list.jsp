<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@include file="/static/inc/main.inc" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path;
%>
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
            margin-bottom: 10;
        }
    </style>

    <script type="text/javascript">
        $(document).ready(
                function () {
                    var filename = '<c:out value="${fileName}"/>';
                    if (filename != "") {
                        //window.location.href = "http://keng.yinduoziben.com/static/download/" + filename;
                        window.location.href = "http://localhost:8080/static/download/"+ filename;
                    }
                });

        function exprotExcel(condition) {
            var url = "${ctx}/userinfo/user/listUserExportExcel/" + condition
            window.location.href = url;
        }
    </script>

</head>
<body>

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
        </a> <a class="refresh" id="refresh-toggler"
                href="${ctx}/userinfo/user/listUser"> <i
            class="glyphicon glyphicon-refresh"></i>
    </a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
            class="glyphicon glyphicon-fullscreen"></i>
    </a>
    </div>
    <!--Header Buttons End-->
</div>
<!-- /Page Header -->
<!-- Page Body -->
<form action="${ctx}/userinfo/user/listUser" method="post"
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
                                        <label class="control-label no-padding-right">身份证号</label> <input
                                            type="text" name="idCard" class="form-control"
                                            placeholder="身份证号">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right">推荐人手机号</label>
                                        <input type="text" name="referralMobile" class="form-control"
                                               placeholder="推荐人手机号">
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label no-padding-right"></label>&nbsp&nbsp
                                        注册日期:从 <input type="text" name="startRegiest"
                                                      class="form-control" tabindex="1"
                                                      style="padding-left: 50px;" required maxlength="40"
                                                      onfocus="WdatePicker({skin:'twoer'})"> 到 <input
                                            type="text" name="endRegiest" class="form-control"
                                            tabindex="1" style="padding-left: 50px;" required
                                            maxlength="40" onfocus="WdatePicker({skin:'twoer'})">
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label no-padding-right">投资用户</label> <select
                                            class="form-control" name="isInvUser">
                                        <option value="">全部</option>
                                        <option value="1">是</option>
                                        <option value="2">否</option>
                                    </select>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label no-padding-right">锁定状态</label> <select
                                            class="form-control" name="isLogin">
                                        <option value="">全部</option>
                                        <option value="0">未锁</option>
                                        <option value="-1">锁定</option>
                                    </select>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right">身份证验证次数</label>
                                        <select class="form-control" name="cardVerifyNum">
                                            <option value="">全部</option>
                                            <option value="0">0</option>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                        </select>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label no-padding-right">用户类型</label> <select
                                            class="form-control" name="userType">
                                        <option value="">全部</option>
                                        <option value="0">普通用户</option>
                                        <option value="1">老用户</option>
                                        <option value="2">股东用户</option>
                                        <option value="3">业务员用户</option>
                                        <option value="4">新鲜说用户</option>
                                        <option value="5">麦罗用户</option>
                                        <option value="6">涌金门</option>
                                        <option value="7">金贸街</option>
                                    </select>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label no-padding-right">用户等级</label> <select
                                            class="form-control" name="userLeve">
                                        <option value="">全部</option>
                                        <c:forEach items="${vipGrades}" var="vipGrade">
                                            <option value="${vipGrade.id}">${vipGrade.gradeName}</option>
                                        </c:forEach>
                                    </select>
                                    </div>

                                    <div class="form-group" style="margin-left: 10px">
                                        <label class="control-label no-padding-right">是否红名</label> <select
                                            class="form-control" name="status">
                                        <option value="">全部</option>
                                        <option value="1">红名</option>
                                        <option value="2">未红名</option>
                                    </select>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right"></label>
                                        &nbsp&nbsp
                                        累计积分:从
                                        <input type="text" name="startTotalIntegral"
                                               class="form-control" tabindex="1"
                                               style="width: 100px;" maxlength="40" />
                                        到
                                        <input type="text" name="endTotalIntegral" class="form-control"
                                               tabindex="1" style="width: 100px;"
                                               maxlength="40">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label no-padding-right"></label>
                                        &nbsp&nbsp
                                        剩余积分:从
                                        <input type="text" name="startIntegral"
                                               class="form-control" tabindex="1"
                                               style="width: 100px;" maxlength="40" />
                                        到
                                        <input type="text" name="endIntegral" class="form-control"
                                               tabindex="1" style="width: 100px;"
                                               maxlength="40">
                                    </div>
                                    <div class="form-group" style="margin-left: 10px">
                                        <label class="control-label no-padding-right">是否开通电子账户</label>
                                        <select class="form-control" name="hasAccountId">
                                            <option value="">全部</option>
                                            <option value="1">是</option>
                                            <option value="0">否</option>
                                        </select>
                                    </div>
                                    <div class="form-group" style="margin-left: 10px">
                                        <label class="control-label no-padding-right">电子账户类型</label>
                                        <select class="form-control" name="accountType">
                                            <option value="">全部</option>
                                            <option value="1">普通用户</option>
                                            <option value="2">债权用户</option>
                                            <option value="3">美利用户</option>
                                            <option value="4">红包账户</option>
                                            <option value="5">手续费账户</option>
                                        </select>
                                    </div>
                                    <div class="form-group" style="margin-left: 10px">
                                        <label class="control-label no-padding-right">是否买单侠用户</label>
                                        <select class="form-control" name="userSource">
                                            <option value="">全部</option>
                                            <option value="1">是</option>
                                            <option value="0">否</option>
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
                        <span class="widget-caption">用户信息管理</span>

                        <div class="widget-buttons">
                            <a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
                            </a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
                        </a>
                        </div>
                    </div>
                    <div class="widget-body">
                        <div class="flip-scroll">
                            <div style="text-align: right; margin-bottom: 10px;">
                                <shiro:hasPermission name="user_del">
                                    <a class="btn btn-labeled btn-danger"
                                       href="javascript:removeData('${ctx}/userinfo/user/deleteUsers');"><i
                                            class="btn-label glyphicon glyphicon-trash"></i>删除</a>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="user_exportexcel">
                                    <a
                                            href="javascript:exprotExcel('<c:out value="${condition}"/>');"
                                            class="btn btn-labeled btn-success"> <i
                                            class="btn-label glyphicon glyphicon-download-alt"></i>导出Excel
                                    </a>
                                </shiro:hasPermission>
                            </div>

                            <table class="table table-hover table-striped table-bordered">
                                <thead style="font-size: 16px; font-weight: bold;">
                                <tr>
                                    <th width="80" style="text-align: center;">序号</th>
                                    <th>用户名</th>
                                    <th>手机号</th>
                                    <th>年龄</th>
                                    <th>性别</th>
                                    <th>推荐人手机</th>
                                    <th>投资用户</th>
                                    <th>用户类型</th>
                                    <th>用户等级</th>
                                    <th>电子账户类型</th>
                                    <th>是否买单侠用户</th>
                                    <th>累计积分</th>
                                    <th>可用积分</th>
                                    <th>注册时间</th>
                                    <th width="20"><label> <input type="checkbox"><span class="text"></span>
                                    </label></th>
                                    <th width="200px" style="text-align: center;">操作</th>
                                </tr>
                                </thead>
                                <tbody style="font-size: 12px;">
                                <c:forEach items="${page.content}" var="user"
                                           varStatus="status">
                                    <tr>
                                        <td class="table_no" width="80" align="center"></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${user.userLeve.gradeNum eq 1}"><img src="${ctx}/static/lib/images/vip1.png" /></c:when>
                                                <c:when test="${user.userLeve.gradeNum eq 2}"><img src="${ctx}/static/lib/images/vip2.png" /></c:when>
                                            </c:choose>
                                            <a href="${ctx}/userinfo/user/listUserInfo/<c:out value='${user.id}'/>"
                                               data-simpletooltip="init"
                                               title='<c:out value="${user.remark}" />'
                                               <c:if test="${not empty user.remark}">style='color: red;'</c:if>>
                                                <c:out value="${user.username}"/>
                                            </a>
                                        </td>
                                        <td>
                                            <a href="${ctx}/userinfo/user/listUserInfo/<c:out value='${user.id}'/>"
                                               data-simpletooltip="init"
                                               title='<c:out value="${user.remark}" />'
                                               <c:if test="${not empty user.remark}">style='color: red;'</c:if>>
                                                <c:out value="${user.mobile}"/>
                                            </a>
                                        </td>
                                        <td><c:out value="${user.age}" /> </td>
                                        <td><c:out value="${user.gender}" /> </td>
                                        <td><c:out value="${user.referralMobile}"/></td>
                                        <td>
                                            <c:if test="${user.userInvestinfo.allInvest gt 0 }">
                                                是
                                            </c:if>
                                            <c:if test="${user.userInvestinfo.allInvest le 0 }">
                                                否
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:if test="${user.userType eq 0 }">
                                                普通用户
                                            </c:if>
                                            <c:if test="${user.userType eq 1 }">
                                                老用户
                                            </c:if>
                                            <c:if test="${user.userType eq 2 }">
                                                股东用户
                                            </c:if>
                                            <c:if test="${user.userType eq 3 }">
                                                业务员用户
                                            </c:if>
                                            <c:if test="${user.userType eq 4 }">
                                                新鲜说用户
                                            </c:if>
                                            <c:if test="${user.userType eq 5 }">
                                                麦罗用户
                                            </c:if>
                                            <c:if test="${user.userType eq 6 }">
                                                涌金门
                                            </c:if>
                                            <c:if test="${user.userType eq 7 }">
                                                金贸街
                                            </c:if>
                                        </td>

                                        <td>
                                                ${user.userLeve.gradeName}
                                        </td>

                                        <td>
                                            <c:choose>
                                                <c:when test="${empty user.accountType }">

                                                </c:when>
                                                <c:when test="${user.accountType == 1 }">
                                                    普通用户
                                                </c:when>
                                                <c:when test="${user.accountType == 2 }">
                                                    债权用户
                                                </c:when>
                                                <c:when test="${user.accountType == 3 }">
                                                    美利用户
                                                </c:when>
                                                <c:when test="${user.accountType == 4 }">
                                                    红包账户
                                                </c:when>
                                                <c:when test="${user.accountType == 5 }">
                                                    手续费账户
                                                </c:when>
                                                <c:otherwise></c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td><c:out value="${user.userSource eq -1 ? '是': '否'}"/></td>
                                        <td>
                                            <c:out value="${empty user.userIntegral || empty user.userIntegral.totalIntegral? 0 : user.userIntegral.totalIntegral}"/>
                                        </td>
                                        <td>
                                            <c:out value="${empty user.userIntegral || empty user.userIntegral.integral? 0 : user.userIntegral.integral}"/>
                                        </td>
                                        <td><fmt:formatDate value="${user.createDate}"
                                                            pattern="yyyy年MM月dd日 HH时mm分"/></td>
                                        <td width="20"><label> <input type="checkbox"
                                                                      name="ids"
                                                                      value='<c:out value="${user.id}" />'> <span
                                                class="text"></span>
                                        </label></td>
                                        <td width="200px" align="center"><shiro:hasPermission
                                                name="user_listinfo">
                                            <a
                                                    href='${ctx}/userinfo/user/listUserInfo/<c:out value="${user.id}" />'
                                                    class="btn btn-info btn-xs edit"><i
                                                    class="fa fa-edit"></i> 详细</a>
                                            &nbsp;&nbsp;
                                        </shiro:hasPermission> <shiro:hasPermission
                                                name="user_del">
                                            <a class="btn btn-danger btn-xs delete"
                                               onclick="removeData('${ctx}/userinfo/user/deleteUser/','<c:out
                                                       value="${user.id}"/>')"><i
                                                    class="fa fa-trash-o"></i> 删除</a>
                                        </shiro:hasPermission></td>
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