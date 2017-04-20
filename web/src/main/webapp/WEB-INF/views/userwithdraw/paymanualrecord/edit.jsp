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
    <script src="${ctx}/static/lib/js/add.js"></script>
    <style>
        .form-group label {
            position: absolute;
            left: 10px;
            top: 7px;
            color: #999;
        }
    </style>
    <script type="text/javascript">
        function changeViews() {
            var typeId = $("#type").val();
            cylcleDaysToggle(typeId);
            queryProductName(typeId);
        }

        $(function () {
            var typeId = '<c:out value="${productInfo.type.id}"/>';
            cylcleDaysToggle(typeId);
            if (typeId == "" || typeId == undefined) {
                queryProductName(1);
            }

        });

        function cylcleDaysToggle(typeId) {
            //天标
            if (typeId == 1) {
                $("#cylcleDays").hide();
                $("#interestRate").show();
                $("#copies").show();
            }
            //定存宝
            else if (typeId == 2) {
                $("#cylcleDays").show();
                $("#interestRate").show();
                $("#copies").show();
            }
            //麦多宝
            else if (typeId == 3 || typeId == 4) {
                $("#cylcleDays").show();
                $("#interestRate").hide();
                $("#copies").hide();
            }
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
        <li><i class="fa fa-home"></i> <a href="#">放款管理</a></li>
        <li><a href="#">民生代付申请</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>
            <c:choose>
                <c:when test="${!empty productInfo.id}">申请编辑</c:when>
                <c:otherwise>申请添加</c:otherwise>
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
                        <form action="${ctx}/userwithdraw/payManualRecord/save" id="editForm"
                              name="editForm" method="post" role="form">
                            <input type="hidden" name="id" value='<c:out value="${payManualRecord.id}"/>'>

                            <div class="form-title">申请信息</div>

                            <div class="row">
                                <div class="col-sm-6">
                                    <label style="color: #999;">收款银行名称</label>

                                    <div class="form-group">
                                        <select class="form-control" name="bankName" required>
                                            <option value="">请选择</option>
                                            <option value="邮政储蓄银行"
                                                    <c:if test="${payManualRecord.bankName eq '邮政储蓄银行'}">selected="selected"</c:if>>
                                                邮政储蓄银行
                                            </option>
                                            <option value="工商银行"
                                                    <c:if test="${payManualRecord.bankName eq '工商银行'}">selected="selected"</c:if>>
                                                工商银行
                                            </option>
                                            <option value="农业银行"
                                                    <c:if test="${payManualRecord.bankName eq '农业银行'}">selected="selected"</c:if>>
                                                农业银行
                                            </option>
                                            <option value="中国银行"
                                                    <c:if test="${payManualRecord.bankName eq '中国银行'}">selected="selected"</c:if>>
                                                中国银行
                                            </option>
                                            <option value="建设银行"
                                                    <c:if test="${payManualRecord.bankName eq '建设银行'}">selected="selected"</c:if>>
                                                建设银行
                                            </option>
                                            <option value="交通银行"
                                                    <c:if test="${payManualRecord.bankName eq '交通银行'}">selected="selected"</c:if>>
                                                交通银行
                                            </option>
                                            <option value="中信银行"
                                                    <c:if test="${payManualRecord.bankName eq '中信银行'}">selected="selected"</c:if>>
                                                中信银行
                                            </option>
                                            <option value="光大银行"
                                                    <c:if test="${payManualRecord.bankName eq '光大银行'}">selected="selected"</c:if>>
                                                光大银行
                                            </option>
                                            <option value="华夏银行"
                                                    <c:if test="${payManualRecord.bankName eq '华夏银行'}">selected="selected"</c:if>>
                                                华夏银行
                                            </option>
                                            <option value="民生银行"
                                                    <c:if test="${payManualRecord.bankName eq '民生银行'}">selected="selected"</c:if>>
                                                民生银行
                                            </option>
                                            <option value="广发银行"
                                                    <c:if test="${payManualRecord.bankName eq '广发银行'}">selected="selected"</c:if>>
                                                广发银行
                                            </option>
                                            <option value="平安银行"
                                                    <c:if test="${payManualRecord.bankName eq '平安银行'}">selected="selected"</c:if>>
                                                平安银行
                                            </option>
                                            <option value="招商银行"
                                                    <c:if test="${payManualRecord.bankName eq '招商银行'}">selected="selected"</c:if>>
                                                招商银行
                                            </option>
                                            <option value="兴业银行"
                                                    <c:if test="${payManualRecord.bankName eq '兴业银行'}">selected="selected"</c:if>>
                                                兴业银行
                                            </option>
                                            <option value="浦发银行"
                                                    <c:if test="${payManualRecord.bankName eq '浦发银行'}">selected="selected"</c:if>>
                                                浦发银行
                                            </option>
                                            <option value="上海银行"
                                                    <c:if test="${payManualRecord.bankName eq '上海银行'}">selected="selected"</c:if>>
                                                上海银行
                                            </option>
                                            <option value="北京银行"
                                                    <c:if test="${payManualRecord.bankName eq '北京银行'}">selected="selected"</c:if>>
                                                北京银行
                                            </option>
                                            <option value="上海农商银行"
                                                    <c:if test="${payManualRecord.bankName eq '上海农商银行'}">selected="selected"</c:if>>
                                                上海农商银行
                                            </option>
                                        </select>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
										<span class="form-group input-icon icon-right">
											<label>收款人
                                                <sup style="color: red;">&nbsp;*</sup>：
                                            </label>
											<input type="text" class="form-control" tabindex="1" id="accountName"
                                                   name="accountName"
                                                   value="<c:out value="${payManualRecord.accountName}"/>"
                                                   style="padding-left: 100px;" required maxlength="25"
                                                   data-bv-stringlength-max="25"
                                                   data-bv-stringlength-message="不能超过25个字"/>
										</span>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
										<span class="form-group input-icon icon-right">
											<label>收款人账号
                                                <sup style="color: red;">&nbsp;*</sup>：
                                            </label>
											<input type="text" class="form-control" tabindex="1" id="accountNo"
                                                   name="accountNo"
                                                   value="<c:out value="${payManualRecord.accountNo}"/>"
                                                   style="padding-left: 150px;" required maxlength="25"
                                                   data-bv-stringlength-max="25"
                                                   data-bv-stringlength-message="不能超过25个字"/>
										</span>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
										<span class="form-group input-icon icon-right">
											<label>身份证号码
                                                <sup style="color: red;">&nbsp;*</sup>：
                                            </label>
											<input type="text" class="form-control" tabindex="1" id="transCardId"
                                                   name="transCardId"
                                                   value="<c:out value="${payManualRecord.transCardId}"/>"
                                                   style="padding-left: 150px;" required maxlength="18"
                                                   data-bv-stringlength-max="18"
                                                   data-bv-stringlength-message="不能超过18个字"/>
										</span>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
										<span class="form-group input-icon icon-right">
											<label>银行预留手机号
                                                <sup style="color: red;">&nbsp;*</sup>：
                                            </label>
											<input type="text" class="form-control" tabindex="1" id="transMobile"
                                                   name="transMobile"
                                                   value="<c:out value="${payManualRecord.transMobile}"/>"
                                                   style="padding-left: 150px;" required maxlength="11"
                                                   data-bv-stringlength-max="11"
                                                   data-bv-stringlength-message="不能超过11个字"/>
										</span>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="form-group">
										<span class="form-group input-icon icon-right">
											<label>交易金额
                                                <sup style="color: red;">&nbsp;*</sup>：
                                            </label>
											<input type="text" class="form-control" tabindex="1"
                                                   style="padding-left: 120px;" name="tranAmt" required
                                                   value="<c:out value="${payManualRecord.tranAmt}"/>" maxlength="14"
                                                   data-bv-stringlength-max="14"
                                                   data-bv-stringlength-message="不能超过14个字" data-bv-regexp="true"
                                                   data-bv-regexp-regexp="^\d+(\.\d{1,2})?$"
                                                   data-bv-regexp-message="交易金额格式错误" min="0"
                                                   data-bv-greaterthan-inclusive="true"
                                                   data-bv-greaterthan-message="交易金额应大于0" max="1000000000000"
                                                   data-bv-lessthan-inclusive="true"
                                                   data-bv-lessthan-message="交易金额应小于1000000000000">
										</span>
                                    </div>
                                </div>
                            </div>
                            <div style="text-align: right; margin-bottom: 10px;">
                                <button type="submit" class="btn btn-labeled btn-success">
                                    <i class="btn-label glyphicon glyphicon-ok"></i>保存
                                </button>
                                <a href="${ctx}/userwithdraw/payManualRecord/list" class="btn btn-labeled btn-blue">
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