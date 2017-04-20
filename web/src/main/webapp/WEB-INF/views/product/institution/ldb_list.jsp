<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="/static/inc/main.inc"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh">
<head>
<title>银多资本</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
.form-inline .form-group {
	margin-bottom: 10;
}
</style>
<script type="text/javascript">

function deleteProduct(url){
	bootbox.dialog({
        message: "您确定要作废该产品吗？",
        title: "作废提示",
        className: "modal-darkorange",
        buttons: {
            success: {
                label: "确认",
                className: "btn-default",
                callback: function () {
                		window.location.href = url;
                }
            },
            "取消": {
                className: "btn-default",
                callback: function () { }
            }
        }
    });
}
</script>
</head>
<body>

	<!-- Page Breadcrumb -->
	<div class="page-breadcrumbs">
		<ul class="breadcrumb" style="line-height: 40px;">
			<li><i class="fa fa-home"></i> <a href="#">机构管理</a></li>
			<li><a href="#">利多宝</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>利多宝</h1>
		</div>
		<!--Header Buttons-->
		<div class="header-buttons">
			<a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
			</a> <a class="refresh" id="refresh-toggler" href="${ctx}/product/institution/listLiduobao"> <i
				class="glyphicon glyphicon-refresh"></i>
			</a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
				class="glyphicon glyphicon-fullscreen"></i>
			</a>
		</div>
		<!--Header Buttons End-->
	</div>
	<!-- /Page Header -->
	<!-- Page Body -->
	<form action="${ctx}/product/institution/listLiduobao" method="post"
		id="listForm" name="listForm">
		<input type="hidden" name="status" value="0" />
		<div class="page-body">
			<div class="row">
				<div class="col-lg-2 col-sm-2 col-xs-2">
					<div
						class="databox radius-bordered databox-shadowed databox-graded databox-vertical">
						<div class="databox-top bg-green">
							<div class="databox-icon">
								<i class="fa fa-clock-o"></i>
							</div>
						</div>
						<div class="databox-bottom text-align-center">
							<span class="databox-text">累计购买份数</span>
							<span class="databox-text">
								<fmt:formatNumber type="number" value="${totalBuyCopies} " maxFractionDigits="0"/>
							</span>
						</div>
					</div>
				</div>
				<div class="col-lg-2 col-sm-2 col-xs-2">
					<div
						class="databox radius-bordered databox-shadowed databox-graded databox-vertical">
						<div class="databox-top bg-green">
							<div class="databox-icon">
								<i class="fa fa-clock-o"></i>
							</div>
						</div>
						<div class="databox-bottom text-align-center">
							<span class="databox-text">昨日购买份数</span> 
							<span class="databox-text">
								<fmt:formatNumber type="number" value="${yesterdayBuyCopies} " maxFractionDigits="0"/>
							</span>
						</div>
					</div>
				</div>

				<div class="col-lg-2 col-sm-2 col-xs-2">
					<div
						class="databox radius-bordered databox-shadowed databox-graded databox-vertical">
						<div class="databox-top bg-green">
							<div class="databox-icon">
								<i class="fa fa-clock-o"></i>
							</div>
						</div>
						<div class="databox-bottom text-align-center">
							<span class="databox-text">到期份数</span>
							<span class="databox-text">
								<fmt:formatNumber type="number" value="${closingBuyCopies} " maxFractionDigits="0"/>
							</span>
						</div>
					</div>
				</div>
				
				<div class="col-lg-2 col-sm-2 col-xs-2">
					<div
						class="databox radius-bordered databox-shadowed databox-graded databox-vertical">
						<div class="databox-top bg-green">
							<div class="databox-icon">
								<i class="fa fa-clock-o"></i>
							</div>
						</div>
						<div class="databox-bottom text-align-center">
							<span class="databox-text">昨日到期份数</span>
							<span class="databox-text">
								<fmt:formatNumber type="number" value="${yesterdayClosingCopies} " maxFractionDigits="0"/>
							</span>
						</div>
					</div>
				</div>
				<div class="col-lg-2 col-sm-2 col-xs-2">
					<div
						class="databox radius-bordered databox-shadowed databox-graded databox-vertical">
						<div class="databox-top bg-green">
							<div class="databox-icon">
								<i class="fa fa-clock-o"></i>
							</div>
						</div>
						<div class="databox-bottom text-align-center">
							<span class="databox-text">总收益</span>
							<span class="databox-text">
								<fmt:formatNumber type="number" value="${expireRevenue} " maxFractionDigits="2"/>
							</span>
						</div>
					</div>
				</div>
				<div class="col-lg-2 col-sm-2 col-xs-2">
					<div
						class="databox radius-bordered databox-shadowed databox-graded databox-vertical">
						<div class="databox-top bg-green">
							<div class="databox-icon">
								<i class="fa fa-clock-o"></i>
							</div>
						</div>
						<div class="databox-bottom text-align-center">
							<span class="databox-text">昨日收益</span>
							<span class="databox-text">
								<fmt:formatNumber type="number" value="${yesterdayRevenue} " maxFractionDigits="2"/>
							</span>
						</div>
					</div>
				</div>
			</div>
			
		</div>
	</form>
	<%@include file="/static/inc/footer.inc"%>
</body>
</html>