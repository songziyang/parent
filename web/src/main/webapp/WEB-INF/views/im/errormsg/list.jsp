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
  <%--<link href="${ctx}/static/lib/simpletooltip/src/css/simpletooltip.min.css" rel="stylesheet"/>--%>
  <%--<script src="${ctx}/static/lib/simpletooltip/src/js/simpletooltip.js"></script>--%>
  <%--<style type="text/css">--%>
    <%--.form-inline .form-group {--%>
      <%--margin-bottom: 10;--%>
    <%--}--%>
  <%--</style>--%>
</head>
<body>

<div class="page-breadcrumbs">
  <ul class="breadcrumb" style="line-height: 40px;">
    <li><i class="fa fa-home"></i> <a href="#">异常信息管理</a></li>
    <li><a href="#">即信异常信息</a></li>
  </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
  <div class="header-title">
    <h1>即信异常信息</h1>
  </div>
  <!--Header Buttons-->
  <div class="header-buttons">
    <a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
    </a> <a class="refresh" id="refresh-toggler"
            href="${ctx}/im/errormsg/errorMsg"> <i
          class="glyphicon glyphicon-refresh"></i>
  </a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
          class="glyphicon glyphicon-fullscreen"></i>
  </a>
  </div>
  <!--Header Buttons End-->
</div>
<!-- /Page Header -->
<!-- Page Body -->
<form action="${ctx}/userinfo/userinfo/signnlist" method="post"
      id="listForm" name="listForm">
  <div class="page-body">
    <div class="row">
      <div class="col-xs-12 col-md-12">
        <div class="row">
          <div class="widget">
          <div class="widget-header  with-footer">
            <span class="widget-caption">异常信息列表</span>

            <div class="widget-buttons">
              <a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
              </a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
            </a>
            </div>
          </div>
            <div class="widget-body">

            <div class="col-lg-4 col-md-3 col-sm-6 col-xs-12">
              <div class="databox radius-bordered databox-shadowed databox-graded">
                <div class="databox-right
                  <c:if test="${statList[0] eq 0 }">bg-success</c:if>
                  <c:if test="${statList[0] gt 0 and statList[0] le 10 }">bg-warning</c:if>
                  <c:if test="${statList[0] gt 10}">bg-danger</c:if>
                  ">
                  <div class="databox-piechart" style="padding-top:14px;">
                    <div data-toggle="easypiechart" class="easyPieChart" data-barcolor="#fff" data-linecap="butt" data-percent="50" data-animate="500" data-linewidth="3" data-size="47" data-trackcolor="rgba(255,255,255,0.1)">
                      <span class="white font-150">标的登记异常</span></div>
                  </div>
                </div>
                <div class="databox-left" style="padding-top:20px;">
                  <a href="${ctx}/im/errormsg/errorMsg/1">
                    <span class="databox-number themesecondary">${statList[0]}</span>
                  </a>
                </div>
              </div>
            </div>
            <div class="col-lg-4 col-md-3 col-sm-6 col-xs-12">
              <div class="databox radius-bordered databox-shadowed databox-graded">
                <div class="databox-right
                  <c:if test="${statList[1] eq 0 }">bg-success</c:if>
                  <c:if test="${statList[1] gt 0 and statList[1] le 10 }">bg-warning</c:if>
                  <c:if test="${statList[1] gt 10}">bg-danger</c:if>
                  ">
                  <div class="databox-piechart" style="padding-top:14px;">
                    <div data-toggle="easypiechart" class="easyPieChart" data-barcolor="#fff" data-linecap="butt" data-percent="50" data-animate="500" data-linewidth="3" data-size="47" data-trackcolor="rgba(255,255,255,0.1)">
                      <span class="white font-150">投标申请异常</span></div>
                  </div>
                </div>
                <div class="databox-left" style="padding-top:20px;">
                  <a href="${ctx}/im/errormsg/errorMsg/2">
                    <span class="databox-number themesecondary">${statList[1]}</span>
                  </a>
                </div>
              </div>
            </div>
            <div class="col-lg-4 col-md-3 col-sm-6 col-xs-12">
              <div class="databox radius-bordered databox-shadowed databox-graded">
                <div class="databox-right
                  <c:if test="${statList[2] eq 0 }">bg-success</c:if>
                  <c:if test="${statList[2] gt 0 and statList[2] le 10 }">bg-warning</c:if>
                  <c:if test="${statList[2] gt 10}">bg-danger</c:if>
                  ">
                  <div class="databox-piechart" style="padding-top:14px;">
                    <div data-toggle="easypiechart" class="easyPieChart" data-barcolor="#fff" data-linecap="butt" data-percent="50" data-animate="500" data-linewidth="3" data-size="47" data-trackcolor="rgba(255,255,255,0.1)">
                      <span class="white font-150">投标放款异常</span></div>
                  </div>
                </div>
                <div class="databox-left" style="padding-top:20px;">
                  <a href="${ctx}/im/errormsg/errorMsg/3">
                    <span class="databox-number themesecondary">${statList[2]}</span>
                  </a>
                </div>
              </div>
            </div>
            <div class="col-lg-4 col-md-3 col-sm-6 col-xs-12">
              <div class="databox radius-bordered databox-shadowed databox-graded">
                <div class="databox-right
                  <c:if test="${statList[3] eq 0 }">bg-success</c:if>
                  <c:if test="${statList[3] gt 0 and statList[3] le 10 }">bg-warning</c:if>
                  <c:if test="${statList[3] gt 10}">bg-danger</c:if>
                  ">
                  <div class="databox-piechart" style="padding-top:14px;">
                    <div data-toggle="easypiechart" class="easyPieChart" data-barcolor="#fff" data-linecap="butt" data-percent="50" data-animate="500" data-linewidth="3" data-size="47" data-trackcolor="rgba(255,255,255,0.1)">
                      <span class="white font-150">债权转让异常</span></div>
                  </div>
                </div>
                <div class="databox-left" style="padding-top:20px;">
                  <a href="${ctx}/im/errormsg/errorMsg/4">
                    <span class="databox-number themesecondary">${statList[3]}</span>
                  </a>
                </div>
              </div>
            </div>
            <div class="col-lg-4 col-md-3 col-sm-6 col-xs-12">
              <div class="databox radius-bordered databox-shadowed databox-graded">
                <div class="databox-right
                  <c:if test="${statList[4] eq 0 }">bg-success</c:if>
                  <c:if test="${statList[4] gt 0 and statList[4] le 10 }">bg-warning</c:if>
                  <c:if test="${statList[4] gt 10}">bg-danger</c:if>
                  ">
                  <div class="databox-piechart" style="padding-top:14px;">
                    <div data-toggle="easypiechart" class="easyPieChart" data-barcolor="#fff" data-linecap="butt" data-percent="50" data-animate="500" data-linewidth="3" data-size="47" data-trackcolor="rgba(255,255,255,0.1)">
                      <span class="white font-150">到期还款异常</span></div>
                  </div>
                </div>
                <div class="databox-left" style="padding-top:20px;">
                  <a href="${ctx}/im/errormsg/errorMsg/5">
                    <span class="databox-number themesecondary">${statList[4]}</span>
                  </a>
                </div>
              </div>
            </div>
            <div class="col-lg-4 col-md-3 col-sm-6 col-xs-12">
              <div class="databox radius-bordered databox-shadowed databox-graded">
                <div class="databox-right
                  <c:if test="${statList[5] eq 0 }">bg-success</c:if>
                  <c:if test="${statList[5] gt 0 and statList[5] le 10 }">bg-warning</c:if>
                  <c:if test="${statList[5] gt 10}">bg-danger</c:if>
                  ">
                  <div class="databox-piechart" style="padding-top:14px;">
                    <div data-toggle="easypiechart" class="easyPieChart" data-barcolor="#fff" data-linecap="butt" data-percent="50" data-animate="500" data-linewidth="3" data-size="47" data-trackcolor="rgba(255,255,255,0.1)">
                      <span class="white font-150">结束债权异常</span></div>
                  </div>
                </div>
                <div class="databox-left" style="padding-top:20px;">
                  <a href="${ctx}/im/errormsg/errorMsg/6">
                    <span class="databox-number themesecondary">${statList[5]}</span>
                  </a>
                </div>
              </div>
            </div>

            <div class="flip-scroll">
              <table class="table table-hover table-striped table-bordered">
                <thead style="font-size: 16px; font-weight: bold;">
                <tr>
                  <th width="80" style="text-align: center;">序号</th>
                  <th>交易日期</th>
                  <th>交易时间</th>
                  <th>交易流水号</th>
                  <th>订单号</th>
                  <th>响应代码</th>
                  <th>响应描述</th>
                  <th>响应时间</th>
                </tr>
                </thead>
                <tbody style="font-size: 12px;">
                <c:forEach items="${page.content}" var="details" varStatus="status">
                  <tr>
                    <td class="table_no" width="80" align="center"></td>
                    <td>${ details[0] }</td>
                    <td>${ details[1] }</td>
                    <td>${ details[2] }</td>
                    <td>${ details[3] }</td>
                    <td>${ details[4] }</td>
                    <td>${ details[5] }</td>
                    <td>${ details[6] }</td>
                  </tr>
                </c:forEach>
                </tbody>
              </table>
              <tags:pagination page="${page}"></tags:pagination>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</form>
<%@include file="/static/inc/footer.inc" %>
</body>
</html>