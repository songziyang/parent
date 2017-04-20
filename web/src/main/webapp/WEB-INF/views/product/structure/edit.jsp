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
    </style>
</head>
<body>

<!-- Page Breadcrumb -->
<div class="page-breadcrumbs">
    <ul class="breadcrumb" style="line-height: 40px;">
        <li><i class="fa fa-home"></i> <a href="#">产品管理</a></li>
        <li><a href="#">转转赚产品</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>
            <c:choose>
                <c:when test="${!empty product.id}">转转赚产品编辑</c:when>
                <c:otherwise>转转赚产品添加</c:otherwise>
            </c:choose>
        </h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
        </a>
        <c:choose>
            <c:when test="${empty structure.id}">
                <a class="refresh" id="refresh-toggler" href="${ctx}/product/structure/create">
            </c:when>
            <c:otherwise>
                <a class="refresh" id="refresh-toggler" href="${ctx}/product/structure/edit/${structure.id}">
            </c:otherwise>
        </c:choose>
        <i class="glyphicon glyphicon-refresh"></i>
    </a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
            class="glyphicon glyphicon-fullscreen"></i>
    </a>
    </div>
    <!--Header Buttons End-->
</div>
<!-- /Page Header -->
<!-- Page Body -->
<div class="page-body">
    <div class="row">
        <div class="col-xs-12 col-md-12">
            <div class="widget">
                <div class="widget-header bordered-bottom bordered-palegreen">
                    <span class="widget-caption">表单</span>
                </div>
                <div class="widget-body">
                    <div id="registration-form">
                        <form action="${ctx}/product/structure/save" id="editForm"
                              name="editForm" method="post" role="form">

                            <input type="hidden" name="id" value='<c:out value="${structure.id}"/>'>
                            <input type="hidden" name="created" value='<c:out value="${structure.created}"/>'>
                            <input type="hidden" name="createUser" value='<c:out value="${structure.createUser}"/>'>
                            <input type="hidden" name="state" value='<c:out value="${structure.state}"/>'/>
                            <div class="form-title">转转赚产品</div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <span class="form-group input-icon icon-right">
                                            <label>产品名称<sup style="color: red;">&nbsp;*</sup>：</label>
                                            <input type="text" class="form-control" tabindex="1"
                                                   <c:if test="${not empty structure.id}">readonly</c:if>
                                                   name="name" value="<c:out value="${structure.name}"/>"
                                                   style="padding-left: 100px;" required maxlength="40"
                                                   data-bv-stringlength-max="40"
                                                   data-bv-stringlength-message="不能超过40个字"/>
                                        </span>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <span class="form-group input-icon icon-right">
                                            <label>产品期数<sup style="color: red;">&nbsp;*</sup>：</label>
                                            <input type="text" class="form-control" tabindex="1"
                                                   <c:if test="${not empty structure.id}">readonly</c:if>
                                                   name="issue" value="<c:out value="${structure.issue}"/>"
                                                   style="padding-left: 100px;" required maxlength="40"
                                                   data-bv-stringlength-max="40"
                                                   data-bv-stringlength-message="不能超过40个字"/>
                                        </span>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <span class="form-group input-icon icon-right">
                                            <label>开放份数<sup style="color: red;">&nbsp;*</sup>：</label>
                                            <input type="text" class="form-control" tabindex="1"
                                                    style="padding-left: 100px;" name="copies" required
                                                    <c:if test="${not empty structure.id}">readonly</c:if>
                                                    value="<c:out value='${structure.copies}'/>" maxlength="9"
                                                    data-bv-stringlength-max="9"
                                                    data-bv-stringlength-message="不能超过9个字"
                                                    data-bv-regexp="true" data-bv-regexp-regexp="^\d+$"
                                                    data-bv-regexp-message="开放份数格式错误">
                                        </span>
                                    </div>
                                </div>
                                <c:if test="${not empty structure.id}">
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <span class="form-group input-icon icon-right"> <label>剩余份数<sup style="color: red;">&nbsp;*</sup>：</label>
                                            <input type="text" class="form-control" tabindex="1"
                                                    style="padding-left: 100px;" name="remainingCopies"
                                                    required readonly
                                                    value="<c:out value='${structure.remainingCopies}'/>"
                                                    maxlength="9"
                                                    data-bv-stringlength-max="9"
                                                    data-bv-stringlength-message="不能超过9个字"
                                                    data-bv-regexp="true" data-bv-regexp-regexp="^\d+$"
                                                    data-bv-regexp-message="剩余份数格式错误">
                                            </span>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <span class="form-group input-icon icon-right"> <label>普通用户最大份数<sup
                                                style="color: red;">&nbsp;</sup>：
                                        </label>
                                        <input type="text" class="form-control" tabindex="1"
                                                placeholder="选填,默认为${defaultMaxCopies}"
                                                style="padding-left: 150px;" name="maxCopies"
                                                value="<c:out value='${structure.maxCopies}'/>"
                                                maxlength="9"
                                                data-bv-lessthan="true"
                                                data-bv-lessthan-value="copies"
                                                data-bv-lessthan-message="最大份数应小于等于开放份数"
                                                data-bv-stringlength-max="9"
                                                data-bv-stringlength-message="不能超过9个字"
                                                data-bv-regexp="true" data-bv-regexp-regexp="^\d+$"
                                                data-bv-regexp-message="最大份数格式错误">
                                        </span>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <span class="form-group input-icon icon-right"> <label>vip用户最大份数<sup
                                                style="color: red;">&nbsp;</sup>：
                                        </label>
                                        <input type="text" class="form-control" tabindex="1"
                                               style="padding-left: 150px;" name="vipMaxCopies"
                                               placeholder="选填,默认为${defaultVipMaxCopies}"
                                               value="<c:out value='${structure.vipMaxCopies}'/>"
                                               maxlength="9"
                                               data-bv-lessthan="true"
                                               data-bv-lessthan-value="copies"
                                               data-bv-lessthan-message="vip最大份数应小于等于开放份数"
                                               data-bv-stringlength-max="9"
                                               data-bv-stringlength-message="不能超过9个字"
                                               data-bv-regexp="true" data-bv-regexp-regexp="^\d+$"
                                               data-bv-regexp-message="vip最大份数格式错误">
                                        </span>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <span class="form-group input-icon icon-right"> <label>最小份数<sup
                                                style="color: red;">&nbsp;</sup>：
                                        </label>
                                        <input type="text" class="form-control" tabindex="1"
                                               style="padding-left: 100px;" name="minCopies"
                                               value="<c:out value='${structure.minCopies}'/>"
                                               placeholder="选填,默认为${defaultMinCopies}"
                                               maxlength="9"
                                               data-bv-lessthan="true"
                                               data-bv-lessthan-value="maxCopies"
                                               data-bv-lessthan-message="最小份数应小于等于最大份数"
                                               data-bv-stringlength-max="9"
                                               data-bv-stringlength-message="不能超过9个字"
                                               data-bv-regexp="true" data-bv-regexp-regexp="^\d+$"
                                               data-bv-regexp-message="最小份数格式错误">
                                        </span>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <span class="form-group input-icon icon-right">
                                            <label>利率(%)<sup style="color: red;">&nbsp;</sup>：</label>
                                            <input type="text" class="form-control" tabindex="1"
                                                   placeholder="选填,默认为${defaultApr}"
                                                   style="padding-left: 80px;" name="apr"
                                                   value="<c:out value="${structure.apr}"/>" maxlength="5"
                                                   data-bv-stringlength-max="5"
                                                   data-bv-stringlength-message="不能超过5个字"
                                                   data-bv-regexp="true"
                                                   data-bv-regexp-regexp="^\d+(\.\d+)?$"
                                                   data-bv-regexp-message="利率格式错误" min="0"
                                                   data-bv-greaterthan-inclusive="true"
                                                   data-bv-greaterthan-message="利率应大于0" max="100"
                                                   data-bv-lessthan-inclusive="true"
                                                   data-bv-lessthan-message="利率应小于100"/>
                                        </span>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <span class="form-group input-icon icon-right">
                                            <label>助力加息最高限制(%)<sup style="color: red;">&nbsp;</sup>：</label>
                                            <input type="text" class="form-control" tabindex="1"
                                                   placeholder="选填,默认为${defaultHelpMaxApr}"
                                                   style="padding-left: 150px;" name="helpMaxApr"
                                                   value="<c:out value="${structure.helpMaxApr}"/>" maxlength="5"
                                                   data-bv-stringlength-max="5"
                                                   data-bv-stringlength-message="不能超过5个字"
                                                   data-bv-regexp="true"
                                                   data-bv-regexp-regexp="^\d+(\.\d+)?$"
                                                   data-bv-regexp-message="助力加息最高限制格式错误" min="0"
                                                   data-bv-greaterthan-inclusive="true"
                                                   data-bv-greaterthan-message="助力加息最高限制应大于0" max="100"
                                                   data-bv-lessthan-inclusive="true"
                                                   data-bv-lessthan-message="助力加息最高限制应小于100"/>
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <span class="form-group input-icon icon-right"> <label>申购开始日期<sup
                                                style="color: red;">&nbsp;*</sup>：
                                        </label>
                                        <input type="text" class="form-control" tabindex="1" id="sStartDate"
                                                style="padding-left: 130px;" name="startDates" required
                                                maxlength="40"
                                               <c:if test="${not empty structure.id}">readonly</c:if>
                                                value="<fmt:formatDate value="${structure.sDate}" pattern="yyyy-MM-dd" />"
                                        <c:if test="${empty structure.id }">
                                            onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:function(dp){$('#editForm').data('bootstrapValidator').updateStatus(this.name, 'NOT_VALIDATED').validateField(this.name);}})"
                                        </c:if>
                                                />
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <div style="text-align: right; margin-bottom: 10px;">
                                <button type="submit" class="btn btn-labeled btn-success">
                                    <i class="btn-label glyphicon glyphicon-ok"></i>保存
                                </button>
                                <a href="${ctx}/product/structure/list"
                                   class="btn btn-labeled btn-blue"> <i
                                        class="btn-label glyphicon glyphicon-arrow-left"></i>返回
                                </a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/static/inc/footer.inc" %>
</body>
</html>