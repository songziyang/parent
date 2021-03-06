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

    <script type="text/javascript">
        $(function () {
            var purviewIds =  '<c:out value="${voucher.productDays}"/>';
            var ids = purviewIds.split(",");
            $('input[levels=1]').each(function(index,element){
                for (var i = 0; i < ids.length; i++) {
                    if ($(element).val() == ids[i]) {
                        $(element).prop('checked', 'checked');
                    }
                }
            });
        })
    </script>
</head>
<body>

<!-- Page Breadcrumb -->
<div class="page-breadcrumbs">
    <ul class="breadcrumb" style="line-height: 40px;">
        <li><i class="fa fa-home"></i> <a href="#">红包管理</a></li>
        <li><a href="#">代金券管理</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>
            <c:choose>
                <c:when test="${!empty voucher.id}">代金券编辑</c:when>
                <c:otherwise>代金券添加</c:otherwise>
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
                        <form action="${ctx}/redpacket/redpacketVoucher/save" id="editForm"
                              name="editForm" method="post" role="form">
                            <input type="hidden" name="id" value='<c:out value="${voucher.id}"/>'>

                            <div class="form-title">代金券</div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <label style="color: #999;">触发种类</label>

                                    <div class="form-group">
                                        <select class="form-control" name="triggerType"
                                                <c:if test="${voucher.id ne null}">disabled</c:if>>
                                            <option value="0"
                                                    <c:if test="${voucher.triggerType eq 0}">selected="selected"</c:if>>
                                                手动
                                            </option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
										<span class="form-group input-icon icon-right">
											<label>红包名称
                                                <sup style="color: red;">&nbsp;*</sup>：
                                            </label>
											<input type="text" class="form-control" tabindex="1"
                                                   name="name" value="<c:out value="${voucher.name}"/>"
                                                   style="padding-left: 100px;" required maxlength="25"
                                                   data-bv-stringlength-max="25"
                                                   data-bv-stringlength-message="不能超过25个字"/>
										</span>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
										<span class="form-group input-icon icon-right">
											<label>使用有效天数
                                                <sup style="color: red;">&nbsp;*</sup>：
                                            </label>
											<input type="text" class="form-control" tabindex="1"
                                                   style="padding-left: 120px;" name="useDays" required
                                                   value="<c:out value='${voucher.useDays}'/>" maxlength="10"
                                                   data-bv-stringlength-max="10"
                                                   data-bv-stringlength-message="不能超过10个字"
                                                   data-bv-regexp="true" data-bv-regexp-regexp="^\d+$"
                                                   data-bv-regexp-message="使用有效天数格式错误">
										</span>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
										<span class="form-group input-icon icon-right">
											<label>活动开始时间
                                                <sup style="color: red;">&nbsp;*</sup>：
                                            </label>
											<input type="text" class="form-control" tabindex="1"
                                                   id="beginTime" required maxlength="40"
                                                   style="padding-left: 130px;" name="aBeginTime"
                                                   value="<fmt:formatDate value="${voucher.beginDate}" pattern="yyyy-MM-dd" />"
                                                   onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'finishTime\')}',skin:'twoer',dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:function(dp){$('#editForm').data('bootstrapValidator').updateStatus(this.name, 'NOT_VALIDATED').validateField(this.name);}})"/>
										</span>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
										<span class="form-group input-icon icon-right">
											<label>活动截止时间
                                                <sup style="color: red;">&nbsp;*</sup>：
                                            </label>
											<input type="text" class="form-control" tabindex="1"
                                                   id="finishTime" required maxlength="40"
                                                   style="padding-left: 130px;" name="aFinishTime"
                                                   value="<fmt:formatDate value="${voucher.endDate}" pattern="yyyy-MM-dd" />"
                                                   onfocus="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\')}',skin:'twoer',dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:function(dp){$('#editForm').data('bootstrapValidator').updateStatus(this.name, 'NOT_VALIDATED').validateField(this.name);}})"/>
										</span>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
										<span class="form-group input-icon icon-right">
											<label>金额
                                                <sup style="color: red;">&nbsp;*</sup>：
                                            </label>
											<input type="text" class="form-control" tabindex="1"
                                                   style="padding-left: 120px;" name="fund" required
                                                   value="<c:out value="${voucher.fund}"/>" maxlength="10"
                                                   data-bv-stringlength-max="10"
                                                   data-bv-stringlength-message="不能超过10个字" data-bv-regexp="true"
                                                   data-bv-regexp-regexp="^\d+(\.\d+)?$"
                                                   data-bv-regexp-message="金额格式错误"/>
										</span>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
										<span class="form-group input-icon icon-right">
											<label>满可用金额
                                                <sup style="color: red;">&nbsp;*</sup>：
                                            </label>
											<input type="text" class="form-control" tabindex="1"
                                                   style="padding-left: 120px;" name="limitFund" required
                                                   value="<c:out value="${voucher.limitFund}"/>" maxlength="10"
                                                   data-bv-stringlength-max="10"
                                                   data-bv-stringlength-message="不能超过10个字" data-bv-regexp="true"
                                                   data-bv-regexp-regexp="^\d+$"
                                                   data-bv-regexp-message="满可用金额错误"/>
										</span>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-6">
                                    <label style="color: #999;">产品天数:</label>

                                        <div style="margin-left: 60px; height: auto; width: 80%">
                                            <div class="checkbox"
                                                 style="height:24px;float: left;padding: 0px;margin: 0px;">
                                                <label> <input levels="1" type="checkbox" class="colored-blue"   name="productDaysArr" value='30'  <c:if test="${voucher.id ne null}">disabled</c:if> >
                                                    <span class="text">30</span>
                                                </label>
                                            </div>

                                            <div class="checkbox"
                                                 style="height:24px;float: left;padding: 0px;margin: 0px;">
                                                <label> <input levels="1" type="checkbox" class="colored-blue" name="productDaysArr" value='90'  <c:if test="${voucher.id ne null}">disabled</c:if> >
                                                    <span class="text">90</span>
                                                </label>
                                            </div>

                                            <div class="checkbox"
                                                 style="height:24px;float: left;padding: 0px;margin: 0px;">
                                                <label> <input levels="1" type="checkbox" class="colored-blue" name="productDaysArr" value='180'  <c:if test="${voucher.id ne null}">disabled</c:if> >
                                                    <span class="text">180</span>
                                                </label>
                                            </div>

                                            <div class="checkbox"
                                                 style="height:24px;float: left;padding: 0px;margin: 0px;">
                                                <label> <input levels="1" type="checkbox" class="colored-blue"   name="productDaysArr" value='365'    <c:if test="${voucher.id ne null}">disabled</c:if> >
                                                    <span class="text">365</span>
                                                </label>
                                            </div>
                                        </div>

                                </div>
                            </div>


                            <div style="text-align: right; margin-bottom: 10px;">
                                <button type="submit" class="btn btn-labeled btn-success">
                                    <i class="btn-label glyphicon glyphicon-ok"></i>保存
                                </button>
                                <a href="${ctx}/redpacket/redpacketVoucher/list" class="btn btn-labeled btn-blue">
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