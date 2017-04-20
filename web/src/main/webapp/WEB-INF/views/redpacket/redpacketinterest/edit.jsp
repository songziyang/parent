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
    <script>
        $(function () {
            var productType = "${redpacketInterest.productType.id}";
            changeHtml(productType);
            changeProductDays(productType)
        });

        function changeViews() {
            var productType = $("#productType").val();
            changeHtml(productType);
            changeInvestDays(productType);
            changeProductDays(productType);
        }

        function changeInvestDays(productType) {
            if (productType == 1) {
                $("#ipt_investDays").val(1);
            } else {
                $("#ipt_investDays").val("");
            }
        }

        function changeHtml(productType) {
            var _html = "";
            if (productType == 2) {
                _html += "<option value=\"2\">定存宝加息券</option>";
            } else {
                _html += "<option value=\"1\">活期宝加息券</option>";
            }
            $("#redpacketType").html(_html);
        }


        function changeProductDays(productType) {
            var _html = "";
            if (productType == 2) {
                _html += "<option value=''>请选择</option>";
                _html += "<option value=\"30\"  <c:if test='${redpacketInterest.productDays eq 30 }'>selected='selected'</c:if>  >30</option>";
                _html += "<option value=\"90\"  <c:if test='${redpacketInterest.productDays eq 90 }'>selected='selected'</c:if>  >90</option>";
                _html += "<option value=\"180\" <c:if test='${redpacketInterest.productDays eq 180 }'>selected='selected'</c:if> >180</option>";
                _html += "<option value=\"365\" <c:if test='${redpacketInterest.productDays eq 365 }'>selected='selected'</c:if> >365</option>";
            } else {
                _html += "<option value=''>请选择</option>";
                _html += "<option value=\"1\"   <c:if test='${redpacketInterest.productDays eq 1 }'>selected='selected'</c:if>  >1</option>";
            }
            $("#productDays").html(_html);
        }


    </script>
</head>
<body>

<!-- Page Breadcrumb -->
<div class="page-breadcrumbs">
    <ul class="breadcrumb" style="line-height: 40px;">
        <li><i class="fa fa-home"></i> <a href="#">红包管理</a></li>
        <li><a href="#">加息券管理</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>
            <c:choose>
                <c:when test="${!empty redpacketInterest.id}">加息券编辑</c:when>
                <c:otherwise>加息券添加</c:otherwise>
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
                        <form action="${ctx}/redpacket/redpacketInterest/save" id="editForm"
                              name="editForm" method="post" role="form">
                            <input type="hidden" name="id" value='<c:out value="${redpacketInterest.id}"/>'>

                            <div class="form-title">加息券</div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <label style="color: #999;">产品类型</label>

                                    <div class="form-group">
                                        <select class="form-control" id="productType" name="productType.id"
                                                onchange="javascript:changeViews();"
                                                <c:if test="${redpacketInterest.id ne null}">disabled</c:if>>
                                            <c:forEach var="productType" items="${productTypes}">
                                                <c:set var="typeName" value="${productType.type}"/>
                                                <c:set var="typeId" value="${productType.id}"/>
                                                <option value="${typeId}"
                                                        <c:if test="${redpacketInterest.productType.id eq typeId}">selected="selected"</c:if>>
                                                    <c:out value="${typeName}"/>
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <label style="color: #999;">红包类型</label>

                                    <div class="form-group">
                                        <select class="form-control" id="redpacketType" name="redpacketType"
                                                <c:if test="${redpacketInterest.id ne null}">disabled</c:if>>
                                            <option value="1">活期宝加息券</option>
                                            <option value="2">定存宝加息券</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <label style="color: #999;">触发种类</label>

                                    <div class="form-group">
                                        <select class="form-control" name="triggerType"
                                                <c:if test="${redpacketInterest.id ne null}">disabled</c:if>>
                                            <option value="0"
                                                    <c:if test="${redpacketInterest.triggerType eq 0}">selected="selected"</c:if>>
                                                手动
                                            </option>
                                            <option value="1"
                                                    <c:if test="${redpacketInterest.triggerType eq 1}">selected="selected"</c:if>>
                                                注册
                                            </option>
                                            <option value="2"
                                                    <c:if test="${redpacketInterest.triggerType eq 2}">selected="selected"</c:if>>
                                                充值
                                            </option>
                                            <option value="3"
                                                    <c:if test="${redpacketInterest.triggerType eq 3}">selected="selected"</c:if>>
                                                投资
                                            </option>
                                            <option value="4"
                                                    <c:if test="${redpacketInterest.triggerType eq 4}">selected="selected"</c:if>>
                                                推荐
                                            </option>
                                            <option value="8"
                                                    <c:if test="${redpacketInterest.triggerType eq 8}">selected="selected"</c:if>>
                                                分享
                                            </option>
                                            <option value="9"
                                                    <c:if test="${redpacketInterest.triggerType eq 9}">selected="selected"</c:if>>
                                                7日活动
                                            </option>
                                            <option value="12"
                                                    <c:if test="${redpacketInterest.triggerType eq 12}">selected="selected"</c:if>>
                                                定存排行活动
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
                                                   name="name" value="<c:out value="${redpacketInterest.name}"/>"
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
                                                   value="<c:out value='${redpacketInterest.useDays}'/>" maxlength="10"
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
                                                   value="<fmt:formatDate value="${redpacketInterest.activityBeginDate}" pattern="yyyy-MM-dd" />"
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
                                                   value="<fmt:formatDate value="${redpacketInterest.activityFinishDate}" pattern="yyyy-MM-dd" />"
                                                   onfocus="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\')}',skin:'twoer',dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:function(dp){$('#editForm').data('bootstrapValidator').updateStatus(this.name, 'NOT_VALIDATED').validateField(this.name);}})"/>
										</span>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
										<span class="form-group input-icon icon-right">
											<label>赠送值
                                                <sup style="color: red;">&nbsp;*</sup>：
                                            </label>
											<input type="text" class="form-control" tabindex="1"
                                                   style="padding-left: 120px;" name="giveValue" required
                                                   value="<c:out value="${redpacketInterest.giveValue}"/>"
                                                   maxlength="10"
                                                   data-bv-stringlength-max="10"
                                                   data-bv-stringlength-message="不能超过10个字" data-bv-regexp="true"
                                                   data-bv-regexp-regexp="^\d+(\.\d+)?$"
                                                   data-bv-regexp-message="赠送值格式错误"/>
										</span>
                                    </div>
                                </div>

                                <div class="col-sm-6">
                                    <label style="color: #999;">产品天数</label>
                                    <div class="form-group">
                                        <select class="form-control" name="productDays" id = "productDays"  <c:if test="${redpacketInterest.id ne null}">disabled</c:if> >
                                            <option value="">请选择</option>
                                            <option value="1"
                                                    <c:if test="${redpacketInterest.productDays eq 1}">selected="selected"</c:if>>
                                                1
                                            </option>
                                            <option value="30"
                                                    <c:if test="${redpacketInterest.productDays eq 30}">selected="selected"</c:if>>
                                                30
                                            </option>
                                            <option value="90"
                                                    <c:if test="${redpacketInterest.productDays eq 90}">selected="selected"</c:if>>
                                                90
                                            </option>
                                            <option value="180"
                                                    <c:if test="${redpacketInterest.productDays eq 180}">selected="selected"</c:if>>
                                                180
                                            </option>
                                            <option value="365"
                                                    <c:if test="${redpacketInterest.productDays eq 365}">selected="selected"</c:if>>
                                                365
                                            </option>
                                        </select>
                                    </div>
                                </div>

                                <div class="col-sm-6" id="investDays" style="display: none;">
                                    <div class="form-group">
										<span class="form-group input-icon icon-right">
											<label>投资有效天数
                                                <sup style="color: red;">&nbsp;*</sup>：
                                            </label>
											<input type="text" class="form-control" tabindex="1" id="ipt_investDays"
                                                   style="padding-left: 120px;" name="investDays" required
                                            <%-- 活期宝投资天数是1, 其余是null --%>
                                            <c:choose>
                                                   <c:when test="${empty redpacketInterest.id}">value="1"</c:when>
                                                   <c:otherwise>value="<c:out value='${redpacketInterest.investDays}'/>"
                                            </c:otherwise>
                                            </c:choose>
                                                   maxlength="10"
                                                   data-bv-stringlength-max="10"
                                                   data-bv-stringlength-message="不能超过10个字"
                                                   data-bv-regexp="true" data-bv-regexp-regexp="^\d+$"
                                                   data-bv-regexp-message="投资有效天数格式错误">
										</span>
                                    </div>
                                </div>

                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="form-group">
										<span class="form-group input-icon icon-right">
											<label>备注：</label>
											<textarea style="padding-left: 120px;" class="form-control"
                                                      rows="4" name="remark" maxlength="200"
                                                      data-bv-stringlength-max="200"
                                                      data-bv-stringlength-message="不能超过200个字"><c:out
                                                    value="${redpacketInterest.remark}"/></textarea>
										</span>
                                    </div>
                                </div>
                            </div>
                            <div style="text-align: right; margin-bottom: 10px;">
                                <button type="submit" class="btn btn-labeled btn-success">
                                    <i class="btn-label glyphicon glyphicon-ok"></i>保存
                                </button>
                                <a href="${ctx}/redpacket/redpacketInterest/list" class="btn btn-labeled btn-blue">
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