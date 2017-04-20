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
        <li><i class="fa fa-home"></i> <a href="#">信息管理</a></li>
        <li><a href="#">站内信管理</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>
            <c:choose>
                <c:when test="${!empty messages.id}">站内信编辑</c:when>
                <c:otherwise>站内信添加</c:otherwise>
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
                        <form action="${ctx}/infoMessage/message/saveMessage" id="editForm"
                              name="editForm" method="post" role="form">

                            <input type="hidden" name="id" value='<c:out value="${messages.id}"/>'>
                            <input type="hidden" name="type" value='<c:out value="${messages.type}"/>'>
                            <input type="hidden" name="status" value='<c:out value="${messages.status}"/>'>
                            <input type="hidden" name="user.id" value='<c:out value="${messages.user.id}"/>'>
                            <input type="hidden" name="deleted" value='<c:out value="${messages.deleted}"/>'>
                            <input type="hidden" name="created" value='<c:out value="${messages.created}"/>'>

                            <div class="form-title">站内信信息</div>

                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>收件人<sup
                                                    style="color: red;">&nbsp;*</sup>：</label>
												<input id="username" type="text" class="form-control" tabindex="1"
                                                       style="padding-left: 100px;"
                                                       name="username"
                                                       value="<c:out value="${null != messages.user.id ? messages.user.username : ''}"/>"
                                                       required maxlength="20"
                                                       data-bv-stringlength-max="30"
                                                       data-bv-stringlength-message="不能超过30个字">
											</span>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>标题<sup
                                                    style="color: red;">&nbsp;*</sup>：</label>
												<input type="text" class="form-control" tabindex="1"
                                                       style="padding-left: 100px;"
                                                       name="title" value="<c:out value="${messages.title}"/>"
                                                       required maxlength="50"
                                                       data-bv-stringlength-max="50"
                                                       data-bv-stringlength-message="不能超过50个字"
                                                        >
											</span>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
											<span class="form-group input-icon icon-right"> <label>内容：</label>
												<textarea style="padding-left: 100px;" class="form-control"
                                                          rows="4" name="content" maxlength="100"
                                                          data-bv-stringlength-max="100"
                                                          data-bv-stringlength-message="不能超过100个字"><c:out
                                                        value="${messages.content}"/></textarea>
											</span>
                                    </div>
                                </div>
                            </div>

                            <div style="text-align: right; margin-bottom: 10px;">
                                <button type="submit" class="btn btn-labeled btn-success">
                                    <i class="btn-label glyphicon glyphicon-ok"></i>保存
                                </button>
                                <a href="${ctx}/infoMessage/message/listMessage" class="btn btn-labeled btn-blue">
                                    <i class="btn-label glyphicon glyphicon-arrow-left"></i>返回
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
<script type="text/javascript">
    $(function () {
        var username = $("#username").val();
        if (username.length > 0) {
            $("#username").prop({disabled: true});
        }
    })
</script>