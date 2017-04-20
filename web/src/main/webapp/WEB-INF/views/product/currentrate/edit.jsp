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
<script>
    $(function () {
        var timeType = "${currentRate.timeType}";
        //上午
        if (timeType == 0) {
            $("#currentRate").show();
            $("#newcopies").show();
        } else {
            $("#currentRate").hide();
            $("#newcopies").hide();
        }
    });

    function changeViews() {
        var timeType = $("#timeType").val();
        //上午
        if (timeType == 0) {
            $("#currentRate").show();
            $("#newcopies").show();
        } else {
            $("#currentRate").hide();
            $("#newcopies").hide();
        }
    }
</script>
<body>

<!-- Page Breadcrumb -->
<div class="page-breadcrumbs">
    <ul class="breadcrumb" style="line-height: 40px;">
        <li><i class="fa fa-home"></i> <a href="#">产品管理</a></li>
        <li><a href="#">活期宝份数管理</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>
            <c:choose>
                <c:when test="${!empty currentRate.id}">活期宝份数编辑</c:when>
                <c:otherwise>活期宝份数添加</c:otherwise>
            </c:choose>
        </h1>
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
            <div class="widget">
                <div class="widget-header bordered-bottom bordered-palegreen">
                    <span class="widget-caption">表单</span>
                </div>
                <div class="widget-body">
                    <div id="registration-form">
                        <form action="${ctx}/product/currentRate/save" id="editForm"
                              name="editForm" method="post" role="form">
                            <input type="hidden" name="id" value='<c:out value="${currentRate.id}"/>'>

                            <div class="form-title">活期宝份数</div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <label style="color: #999;">时间类型 </label>

                                    <div class="form-group">
                                        <select class="form-control" id="timeType" name="timeType"
                                                onchange="javascript:changeViews();">
                                            <option value="0"
                                                    <c:if test="${currentRate.timeType eq 0}">selected="selected"</c:if>>
                                                每天首发
                                            </option>
                                            <option value="1"
                                                    <c:if test="${currentRate.timeType eq 1}">selected="selected"</c:if>>
                                                每天其他
                                            </option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <label style="color: #999;">是否作为发布份数 </label>

                                    <div class="form-group">
                                        <select class="form-control" id="releaseType" name="releaseType">
                                            <option value="0"
                                                    <c:if test="${currentRate.releaseType eq 0}">selected="selected"</c:if>>
                                                否
                                            </option>
                                            <option value="1"
                                                    <c:if test="${currentRate.releaseType eq 1}">selected="selected"</c:if>>
                                                是
                                            </option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6" id="copies">
                                    <div class="form-group">
										<span class="form-group input-icon icon-right">
											<label>发布份数
                                                <sup style="color: red;">&nbsp;*</sup>：
                                            </label>
											<input type="text" class="form-control" tabindex="1"
                                                   name="copies" value="<c:out value="${currentRate.copies}"/>"
                                                   style="padding-left: 100px;" maxlength="9"
                                                   data-bv-stringlength-max="9" required
                                                   data-bv-stringlength-message="不能超过9个字"
                                                   data-bv-regexp="true" data-bv-regexp-regexp="^\d+$"
                                                   data-bv-regexp-message="发布份数格式错误"
                                                    />
										</span>
                                    </div>
                                </div>
                                <div class="col-sm-6" id="currentRate">
                                    <div class="form-group">
									<span class="form-group input-icon icon-right">
										<label>发布利率(%)
                                            <sup style="color: red;">&nbsp;*</sup>：
                                        </label>
										<input type="text" class="form-control" tabindex="1" id="ipt_currentRate"
                                               style="padding-left: 120px;" name="currentRate" required
                                               value="<c:out value="${currentRate.currentRate}"/>" maxlength="5"
                                               data-bv-stringlength-max="5"
                                               data-bv-stringlength-message="不能超过5个字" data-bv-regexp="true"
                                               data-bv-regexp-regexp="^\d+(\.\d+)?$"
                                               data-bv-regexp-message="发布利率格式错误" min="0"
                                               data-bv-greaterthan-inclusive="true"
                                               data-bv-greaterthan-message="发布利率应大于0" max="100"
                                               data-bv-lessthan-inclusive="true"
                                               data-bv-lessthan-message="发布利率应小于100">
									</span>
                                    </div>
                                </div>
                            </div>
                            
                            <%--<div class="row">--%>
                                <%--<div class="col-sm-6" id="newcopies">--%>
                                    <%--<div class="form-group">--%>
										<%--<span class="form-group input-icon icon-right">--%>
											<%--<label>新手份数--%>
                                                <%--<sup style="color: red;">&nbsp;*</sup>：--%>
                                            <%--</label>--%>
											<%--<input type="text" class="form-control" tabindex="1"--%>
                                                   <%--name="newCopies" value="<c:out value="${currentRate.newCopies}"/>"--%>
                                                   <%--style="padding-left: 100px;" maxlength="9"--%>
                                                   <%--data-bv-stringlength-max="9" required--%>
                                                   <%--data-bv-stringlength-message="不能超过9个字"--%>
                                                   <%--data-bv-regexp="true" data-bv-regexp-regexp="^\d+$"--%>
                                                   <%--data-bv-regexp-message="新手份数格式错误"--%>
                                                    <%--/>--%>
										<%--</span>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>

                            <div style="text-align: right; margin-bottom: 10px;">
                                <button type="submit" class="btn btn-labeled btn-success">
                                    <i class="btn-label glyphicon glyphicon-ok"></i>保存
                                </button>
                                <a href="${ctx}/product/currentRate/list" class="btn btn-labeled btn-blue">
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