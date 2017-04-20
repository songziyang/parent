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
        <li><a href="#">基金产品信息</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>
            <c:choose>
                <c:when test="${!empty product.id}">基金产品信息编辑</c:when>
                <c:otherwise>基金产品信息添加</c:otherwise>
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
                        <form action="${ctx}/product/stable/saveStable" id="editForm"
                              name="editForm" method="post" role="form">

                            <input type="hidden" name="id" value='<c:out value="${stable.id}"/>'>
                            <input type="hidden" name="created" value='<c:out value="${stable.created}"/>'>
                            <input type="hidden" name="createUser" value='<c:out value="${stable.createUser}"/>'>
                            <input type="hidden" name="state" value='<c:out value="${stable.state}"/>'/>
                            <input type="hidden" name="fullTime" value='<c:out value="${stable.fullTime}" />'/>
                            <input type="hidden" name="closeTime" value='<c:out value="${stable.closeTime}" />'/>
                            <c:if test="${not empty stable.id}">
                                <input type="hidden" name="type" value='<c:out value="${stable.type}" />'/>
                            </c:if>
                            <div class="form-title">基金产品信息</div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
												<span class="form-group input-icon icon-right">
													<label>产品名称<sup style="color: red;">&nbsp;*</sup>：</label>
													<input type="text" class="form-control" tabindex="1"
                                                           <c:if test="${not empty stable.id}">readonly</c:if>
                                                           name="name" value="<c:out value="${stable.name}"/>"
                                                           style="padding-left: 100px;" required maxlength="40"
                                                           data-bv-stringlength-max="40"
                                                           data-bv-stringlength-message="不能超过40个字"/>
												</span>
                                    </div>
                                </div>
                                <c:if test="${not empty stable.id}">
                                    <div class="col-sm-6">
                                        <div class="form-group">
													<span class="form-group input-icon icon-right">
														<label>利率百分比(%)<sup style="color: red;">&nbsp;*</sup>：</label>
														<input type="text" class="form-control" tabindex="1"
                                                               style="padding-left: 120px;" name="apr"
                                                               value="<c:out value="${stable.apr}"/>" maxlength="5"
                                                               data-bv-stringlength-max="5"
                                                               data-bv-stringlength-message="不能超过5个字"
                                                               data-bv-regexp="true"
                                                               data-bv-regexp-regexp="^\d+(\.\d+)?$"
                                                               data-bv-regexp-message="利率百分比格式错误" min="0"
                                                               data-bv-greaterthan-inclusive="true"
                                                               data-bv-greaterthan-message="利率百分比应大于0" max="100"
                                                               data-bv-lessthan-inclusive="true"
                                                               data-bv-lessthan-message="利率百分比应小于100"/>
													</span>
                                        </div>
                                    </div>
                                </c:if>
                                <div class="col-sm-6">
                                    <div class="form-group">
												<span class="form-group input-icon icon-right"> <label>开放份数<sup
                                                        style="color: red;">&nbsp;*</sup>：
                                                </label> <input type="text" class="form-control" tabindex="1"
                                                                style="padding-left: 100px;" name="copies" required
                                                                <c:if test="${not empty stable.id}">readonly</c:if>
                                                                value="<c:out value='${stable.copies}'/>" maxlength="9"
                                                                data-bv-stringlength-max="9"
                                                                data-bv-stringlength-message="不能超过9个字"
                                                                data-bv-regexp="true" data-bv-regexp-regexp="^\d+$"
                                                                data-bv-regexp-message="开放份数格式错误">
												</span>
                                    </div>
                                </div>
                                <c:if test="${not empty stable.id}">
                                    <div class="col-sm-6">
                                        <div class="form-group">
													<span class="form-group input-icon icon-right"> <label>剩余份数<sup
                                                            style="color: red;">&nbsp;*</sup>：
                                                    </label> <input type="text" class="form-control" tabindex="1"
                                                                    style="padding-left: 100px;" name="remainingCopies"
                                                                    required
                                                                    <c:if test="${not empty stable.id}">readonly</c:if>
                                                                    value="<c:out value='${stable.remainingCopies}'/>"
                                                                    maxlength="9"
                                                                    data-bv-stringlength-max="9"
                                                                    data-bv-stringlength-message="不能超过9个字"
                                                                    data-bv-regexp="true" data-bv-regexp-regexp="^\d+$"
                                                                    data-bv-regexp-message="剩余份数格式错误">
													</span>
                                        </div>
                                    </div>
                                </c:if>
                                <div class="col-sm-6">
                                    <div class="form-group">
												<span class="form-group input-icon icon-right"> <label>最大份数<sup
                                                        style="color: red;">&nbsp;*</sup>：
                                                </label> <input type="text" class="form-control" tabindex="1"
                                                                style="padding-left: 100px;" name="maxCopies" required
                                                                value="<c:out value='${stable.maxCopies}'/>"
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
												<span class="form-group input-icon icon-right"> <label>最小份数<sup
                                                        style="color: red;">&nbsp;*</sup>：
                                                </label> <input type="text" class="form-control" tabindex="1"
                                                                style="padding-left: 100px;" name="minCopies" required
                                                                value="<c:out value='${stable.minCopies}'/>"
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
												<span class="form-group input-icon icon-right"> <label>申购开始日期<sup
                                                        style="color: red;">&nbsp;*</sup>：
                                                </label> <input type="text" class="form-control" tabindex="1"
                                                                id="sStartDate"
                                                                style="padding-left: 130px;" name="sStartDate" required
                                                                maxlength="40" readonly
                                                                value="<fmt:formatDate value="${stable.startDate}" pattern="yyyy-MM-dd" />"
                                                        <c:if test="${empty stable.id }">
                                                            onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'sEndDate\')}',skin:'twoer',dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:function(dp){$('#editForm').data('bootstrapValidator').updateStatus(this.name, 'NOT_VALIDATED').validateField(this.name);}})"
                                                        </c:if>
                                                        />
												</span>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
												<span class="form-group input-icon icon-right"> <label>申购结束日期<sup
                                                        style="color: red;">&nbsp;*</sup>：
                                                </label> <input type="text" class="form-control" tabindex="1"
                                                                id="sEndDate"
                                                                style="padding-left: 130px;" name="sEndDate" required
                                                                maxlength="40" readonly
                                                                value="<fmt:formatDate value="${stable.endDate}" pattern="yyyy-MM-dd" />"
                                                        <c:if test="${empty stable.id }">
                                                            onfocus="WdatePicker({minDate:'#F{$dp.$D(\'sStartDate\')}',skin:'twoer',dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:function(dp){$('#editForm').data('bootstrapValidator').updateStatus(this.name, 'NOT_VALIDATED').validateField(this.name);}})"
                                                        </c:if>
                                                        />
												</span>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
												<span class="form-group input-icon icon-right">
													<label style="color: #999;">锁定期 <sup
                                                            style="color: red;">&nbsp;*</sup>：</label>
												<input type="text" class="form-control" tabindex="1"
                                                       style="padding-left: 120px;" name="days" required
                                                       <c:if test="${not empty stable.id}">readonly</c:if>
                                                       value="${stable.days}"/>
												</span>
                                    </div>
                                </div>
                                <c:if test="${not empty stable.id}">
                                    <div class="col-sm-6">
                                        <div class="form-group">
													<span class="form-group input-icon icon-right">
														<label style="color: #999;">申购状态 <sup
                                                                style="color: red;">&nbsp;*</sup>：</label>
													<input type="text" class="form-control" tabindex="1"
                                                           style="padding-left: 120px;" readonly
                                                    <c:choose>
                                                           <c:when test="${stable.state eq 1}">value="申购中"</c:when>
                                                           <c:when test="${stable.state eq 2}">value="已满标"</c:when>
                                                           <c:when test="${stable.state eq 3}">value="已结束"</c:when>
                                                    </c:choose>>
													</span>
                                        </div>
                                    </div>
                                </c:if>
                            </div>


                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
												<span class="form-group input-icon icon-right"> <label>期数<sup
                                                        style="color: red;">&nbsp;*</sup>：
                                                </label> <input type="text" class="form-control" tabindex="1"
                                                                style="padding-left: 100px;" name="issue" required
                                                                value="<c:out value='${stable.issue}'/>" maxlength="6"
                                                                data-bv-stringlength-max="6"
                                                                data-bv-stringlength-message="不能超过6个字"
                                                                data-bv-regexp="true" data-bv-regexp-regexp="^\d+$"
                                                                data-bv-regexp-message="期数格式错误"
                                                                <c:if test="${not empty stable.id}">readonly</c:if>>
												</span>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-6">
                                    <label style="color: #999;">产品类型
                                        <sup style="color: red;">&nbsp;*</sup>
                                    </label>

                                    <div class="form-group">
                                        <select class="form-control" name="type"
                                                <c:if test="${not empty stable.id}">disabled</c:if>>
                                            <option value="1"
                                                    <c:if test="${stable.type eq 1}">selected="selected"</c:if>>
                                                稳进宝
                                            </option>
                                            <option value="2"
                                                    <c:if test="${stable.type eq 2}">selected="selected"</c:if>>
                                                震震涨
                                            </option>
                                            <option value="3"
                                                    <c:if test="${stable.type eq 3}">selected="selected"</c:if>>
                                                荡荡跌
                                            </option>
                                        </select>
                                    </div>
                                </div>

                            </div>


                            <div style="text-align: right; margin-bottom: 10px;">
                                <button type="submit" class="btn btn-labeled btn-success">
                                    <i class="btn-label glyphicon glyphicon-ok"></i>保存
                                </button>
                                <a href="${ctx}/product/stable/listStable"
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