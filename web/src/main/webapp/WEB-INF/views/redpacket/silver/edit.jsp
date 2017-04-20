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
        <li><i class="fa fa-home"></i> <a href="#" 活动管理</a></li>
        <li><a href="#">奖品管理</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>
            <c:choose>
                <c:when test="${!empty silver.id}">奖品编辑</c:when>
                <c:otherwise>奖品添加</c:otherwise>
            </c:choose>
        </h1>
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
<div class="page-body">
    <div class="row">
        <div class="col-xs-12 col-md-12">
            <div class="widget">
                <div class="widget-header bordered-bottom bordered-palegreen">
                    <span class="widget-caption">表单</span>
                </div>
                <div class="widget-body">
                    <div id="registration-form">
                        <form action="${ctx}/redpacket/silver/saveSilver" id="editForm"  name="editForm" method="post" role="form">

                            <input type="hidden" name="id" value='<c:out value="${silver.id}"/>'>
                            <input type="hidden" name="status" value='<c:out value="${silver.status}"/>'>
                            <input type="hidden" name="created" value='<c:out value="${silver.created}"/>'>
                            <input type="hidden" name="createUser" value='<c:out value="${silver.createUser}"/>'>

                            <div class="form-title">奖品信息</div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>奖品名称<sup
                                                    style="color: red;">&nbsp;*</sup>：
                                            </label> <input type="text" class="form-control" tabindex="1"
                                                            name="name" value="<c:out value="${silver.name}"/>"
                                                            style="padding-left: 100px;" required maxlength="100"
                                                            data-bv-stringlength-max="100"
                                                            data-bv-stringlength-message="不能超过100个字">
											</span>
                                    </div>
                                </div>

                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>奖品价格<sup
                                                    style="color: red;">&nbsp;*</sup>：
                                            </label> <input type="text" class="form-control" tabindex="1"
                                                            style="padding-left: 100px;" name="price"
                                                            value="<c:out value="${silver.price}"/>" required
                                                            maxlength="20" data-bv-stringlength-max="20"
                                                            data-bv-stringlength-message="不能超过20个字">
											</span>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>银币数量<sup
                                                    style="color: red;">&nbsp;*</sup>：
                                            </label> <input type="text" class="form-control" tabindex="1"
                                                            style="padding-left: 100px;" name="exchange" required
                                                            value="<c:out value="${silver.exchange}"/>"
                                                            maxlength="11"
                                                            data-bv-stringlength-max="11"
                                                            data-bv-stringlength-message="不能超过11个字"
                                                            data-bv-regexp="true"
                                                            data-bv-regexp-regexp="^\d+$"
                                                            data-bv-regexp-message="银币数量格式错误"
                                                    >
											</span>
                                    </div>
                                </div>

                            </div>

                            <div style="text-align: right; margin-bottom: 10px;">
                                <button type="submit" class="btn btn-labeled btn-success">
                                    <i class="btn-label glyphicon glyphicon-ok"></i>保存
                                </button>
                                <a href="${ctx}/redpacket/silver/listSilver"
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