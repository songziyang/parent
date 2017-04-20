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
            var typeId = '<c:out value="${productInfo.type.id}"/>';
            var cylcleDays = $("#hid_cylcleDays").val();
            cylcleDaysToggle(typeId);
            changeDays(typeId, cylcleDays);
            if (typeId == "" || typeId == undefined) {
                queryProductName(2);
            }
        });

        /**
         * 获得产品对应天数的JSON
         */
        function getProductDaysJSON() {
            return {
                "1": [1],   //活期宝
                "2": [30, 90, 180, 365],    //定存宝
                "3": [90, 180, 365],    //麦多宝1号
                "4": [90, 180, 365],    //麦多宝2号
                "5": [90, 180, 365],    //银多-涌金门特供
                "6": [90, 180, 365],    //银多-金贸街特供
                "7": [15]   //新年福利包
            }
        }

        /**
         * 获得视图JSON
         */
        function getViewsJSON() {
            return {
                "1": ["interestRate", "copies"],    //活期宝
                "2": ["cylcleDays", "interestRate", "copies" ,"activityRate" ],  //定存宝
                "3": ["cylcleDays"],    //麦多宝1号
                "4": ["cylcleDays"],    //麦多宝2号
                "5": ["cylcleDays", "interestRate"],    //银多-涌金门特供
                "6": ["cylcleDays", "interestRate"],    //银多-金贸街特供
                "7": ["cylcleDays", "interestRate", "copies"]   //新年福利包
            }
        }

        //更改视图
        function changeViews() {
            var typeId = $("#type").val();
            var cylcleDays = $("#hid_cylcleDays").val();
            cylcleDaysToggle(typeId);
            queryProductName(typeId);
            changeDays(typeId, cylcleDays);
        }

        function cylcleDaysToggle(typeId) {
            if (typeId == "" || typeId == undefined) {
                typeId = 2;
            }
            var views = getViewsJSON();

            $("#cylcleDays").hide();
            $("#interestRate").hide();
            $("#copies").hide();
            $("#activityRate").hide();

            $.each(views, function(index, value) {
               if (typeId == index) {
                   $.each(value, function(innerIndex, innerValue) {
                       $("#" + innerValue).show();
                   });
                   return;
               }
            });
        }

        function changeDays(typeId, cylcleDays) {
            if (typeId == "" || typeId == undefined) {
                typeId = 2;
            }
            var days = getProductDaysJSON();
            $.each(days, function(index, value) {
                if (index == typeId) {
                    var $html = "";
                    $.each(value, function(innerIndex, innverValue) {
                        $html += "<option value='" + innverValue + "' ";
                        if (cylcleDays == innverValue) {
                            $html += "selected='selected'";
                        }
                        $html += ">" + innverValue + "</option>";
                    });
                    $("select[name='cylcleDays']").html($html);
                    return;
                }
            });
        }

        function queryProductName(typeId) {
            //如果是新建
            $.get("<%=request.getContextPath()%>/product/productInfo/queryProductName/" + typeId, function (productName) {
                $("#name").val(productName);
            })
        }
    </script>
</head>
<body>

<!-- Page Breadcrumb -->
<div class="page-breadcrumbs">
    <ul class="breadcrumb" style="line-height: 40px;">
        <li><i class="fa fa-home"></i> <a href="#">产品管理</a></li>
        <li><a href="#">产品信息</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>
            <c:choose>
                <c:when test="${!empty productInfo.id}">产品信息复制</c:when>
                <c:otherwise>产品信息添加</c:otherwise>
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
        <%--<div class="col-lg-4 col-sm-6 col-xs-6">
            <div class="databox radius-bordered databox-shadowed databox-graded databox-vertical">
                <div class="databox-top bg-orange">
                    <div class="databox-icon">
                        <i class="fa fa-clock-o"></i>
                    </div>
                </div>
                <div class="databox-bottom text-align-center">
                    <span class="databox-text">活期宝投预总数</span>
                    <span
                        class="databox-text"><c:out value="${empty currentPrepayCopies? 0: currentPrepayCopies}" />
                    </span>
                </div>
            </div>
        </div>--%>
        <div class="col-xs-12 col-md-12">
            <div class="widget">
                <div class="widget-header bordered-bottom bordered-palegreen">
                    <span class="widget-caption">表单</span>
                </div>
                <div class="widget-body">
                    <div id="registration-form">
                        <form action="${ctx}/product/productInfo/save" id="editForm"
                              name="editForm" method="post" role="form">
                            <input type="hidden" name="id" value='<c:out value="${productInfo.id}"/>'>
                            <input type="hidden" id="hid_cylcleDays" value="<c:out value='${productInfo.cylcleDays}' />" />
                            <div class="form-title">产品信息</div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <label style="color: #999;">产品类型 </label>

                                    <div class="form-group">
                                        <select class="form-control" id="type" name="type.id"
                                                onchange="javascript:changeViews();" disabled>
                                            <c:forEach var="productType" items="${productTypes}">
                                                <c:set var="typeName" value="${productType.type}"/>
                                                <c:set var="typeId" value="${productType.id}"/>
                                                <option value="${typeId}"
                                                        <c:if test="${productInfo.type.id eq typeId}">selected="selected"</c:if>>
                                                    <c:out value="${typeName}"/>
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <label style="color: #999;">产品类别 </label>

                                    <div class="form-group">
                                        <select class="form-control" name="productClas" disabled>
                                            <option value="1"
                                                    <c:if test="${productInfo.productClas eq 1}">selected="selected"</c:if>>
                                                银多类别
                                            </option>
                                            <option value="2"
                                                    <c:if test="${productInfo.productClas eq 2}">selected="selected"</c:if>>
                                                机构类别
                                            </option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <label style="color: #999;">发布方式 </label>

                                    <div class="form-group">
                                        <select class="form-control" name="createWay" >
                                            <option value="0"
                                                    <c:if test="${productInfo.createWay eq 0}">selected="selected"</c:if>>
                                                手动
                                            </option>
                                            <option value="1"
                                                    <c:if test="${productInfo.createWay eq 1}">selected="selected"</c:if>>
                                                自动
                                            </option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-sm-6" id="cylcleDays">
                                    <label style="color: #999;">天数 </label>

                                    <div class="form-group">
                                        <select class="form-control" name="cylcleDays" disabled></select>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
										<span class="form-group input-icon icon-right">
											<label>产品名称
                                                <sup style="color: red;">&nbsp;*</sup>：
                                            </label>
											<input type="text" class="form-control" tabindex="1" id="name" disabled
                                                   name="name" value="<c:out value="${productInfo.name	}"/>"
                                                   style="padding-left: 100px;" required maxlength="25"
                                                   data-bv-stringlength-max="25"
                                                   data-bv-stringlength-message="不能超过25个字"/>
										</span>
                                    </div>
                                </div>
                                <div class="col-sm-6" id="copies">
                                    <div class="form-group">
										<span class="form-group input-icon icon-right">
											<label>发布金额
                                                <sup style="color: red;">&nbsp;*</sup>：
                                            </label>
											<input type="text" class="form-control" tabindex="1" id="funds"
                                                   name="funds" value="<c:out value="${productInfo.funds}"/>"
                                                   style="padding-left: 100px;" required maxlength="11"
                                                   data-bv-stringlength-max="11"
                                                   data-bv-stringlength-message="不能超过11个字"
                                                   data-bv-regexp="true" data-bv-regexp-regexp="^\d+$"
                                                   data-bv-regexp-message="发布金额格式错误"
                                                    />
										</span>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6" id="interestRate">
                                    <div class="form-group">
										<span class="form-group input-icon icon-right">
											<label>年化利率(%)
                                                <sup style="color: red;">&nbsp;*</sup>：
                                            </label>
											<input type="text" class="form-control" tabindex="1"
                                                   style="padding-left: 120px;" name="interestRate" required
                                                   value="<c:out value="${productInfo.interestRate}"/>" maxlength="5"
                                                   data-bv-stringlength-max="5"
                                                   data-bv-stringlength-message="不能超过5个字" data-bv-regexp="true"
                                                   data-bv-regexp-regexp="^\d+(\.\d+)?$"
                                                   data-bv-regexp-message="年化利率格式错误" min="0"
                                                   data-bv-greaterthan-inclusive="true"
                                                   data-bv-greaterthan-message="年化利率应大于0" max="100"
                                                   data-bv-lessthan-inclusive="true"
                                                   data-bv-lessthan-message="年化利率应小于100">
										</span>
                                    </div>
                                </div>

                                <div class="col-sm-6" id="activityRate" >
                                    <div class="form-group">
										<span class="form-group input-icon icon-right">
											<label>活动利率(%)
                                                <sup style="color: red;">&nbsp;*</sup>：
                                            </label>
											<input type="text" class="form-control" tabindex="1"
                                                   style="padding-left: 120px;" name="activityRate" required
                                                   value="<c:out value="${productInfo.activityRate}"/>" maxlength="5"
                                                   data-bv-stringlength-max="5"
                                                   data-bv-stringlength-message="不能超过5个字" data-bv-regexp="true"
                                                   data-bv-regexp-regexp="^\d+(\.\d+)?$"
                                                   data-bv-regexp-message="活动利率格式错误" min="0"
                                                   data-bv-greaterthan-inclusive="true"
                                                   data-bv-greaterthan-message="活动利率应大于0" max="100"
                                                   data-bv-lessthan-inclusive="true"
                                                   data-bv-lessthan-message="活动利率应小于100">
										</span>
                                    </div>
                                </div>

                            </div>
                            <div style="text-align: right; margin-bottom: 10px;">
                                <button type="submit" class="btn btn-labeled btn-success">
                                    <i class="btn-label glyphicon glyphicon-ok"></i>保存
                                </button>
                                <a href="${ctx}/product/productInfo/list" class="btn btn-labeled btn-blue">
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