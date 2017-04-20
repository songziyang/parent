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
  <link href="${ctx}/static/lib/simpletooltip/src/css/simpletooltip.min.css" rel="stylesheet"/>
  <style type="text/css">
    .form-inline .form-group {
      margin-bottom: 10px;
    }
  </style>
  <script type="text/javascript">
    $(document).ready(
            function () {
              var filename = '<c:out value="${fileName}"/>';
              if (filename != "") {
//                        window.location.href = "http://keng.yinduoziben.com/static/download/" + filename;
                window.location.href = "http://localhost:8080/static/download"+ filename;
              }
            });

    function exportExcel(province, city) {
        var p = encodeURI(encodeURI(province));
        var c = encodeURI(encodeURI(city));
        window.location.href = "${ctx}/platform/platforminvestmap/exportExcel/" + p + "/" + c + "/";
    }

  </script>
</head>
<body>

<!-- Page Breadcrumb -->
<div class="page-breadcrumbs">
  <ul class="breadcrumb" style="line-height: 40px;">
    <li><i class="fa fa-home"></i> <a href="#">统计管理</a></li>
    <li><a href="#">在投分布</a></li>
  </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
  <div class="header-title">
    <h1>在投分布</h1>
  </div>
  <!--Header Buttons-->
  <div class="header-buttons">
    <a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
    </a> <a class="refresh" id="refresh-toggler"
            href="${ctx}/platform/platforminvestmap/listInvest/${province}"> <i
          class="glyphicon glyphicon-refresh"></i>
  </a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
          class="glyphicon glyphicon-fullscreen"></i>
  </a>
  </div>
  <!--Header Buttons End-->
</div>
<!-- /Page Header -->


<!-- Page Body -->
<form action="${ctx}/platform/platforminvestmap/listInvest/${province}" method="post" id="listForm" name="listForm">
  <div class="page-body">
    <div class="row">
      <div class="col-xs-12 col-md-12">
        <div class="widget">
          <div class="widget-header  with-footer">
            <span class="widget-caption">在投分布列表-${province}</span>

            <div class="widget-buttons">
              <a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
              </a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
            </a>
            </div>
          </div>
          <div class="widget-body">
            <div class="flip-scroll">
              <div style="text-align: right; margin-bottom: 10px;">
                <a href="javascript:exportExcel('<c:out value="${province}" />', 'all')"
                   class="btn btn-labeled btn-success"> <i
                        class="btn-label glyphicon glyphicon-envelope"></i>导出Excel
                </a>
              </div>
              <%--<div style="margin-bottom: 10px; text-align: center; color: red; font-size: 15px">--%>
                <%--总充值人数:<c:out value="${empty userCount? 0: userCount}" />--%>
              <%--</div>--%>
              <table class="table table-hover table-striped table-bordered">
                <thead style="font-size: 16px; font-weight: bold;">
                <tr>
                  <th width="80" style="text-align: center;">序号</th>
                  <th style="text-align: center;">城市</th>
                  <th style="text-align: center;">人数</th>
                  <th style="text-align: center;">金额(万元)</th>
                  <th style="text-align: center;">操作</th>
                </tr>
                </thead>
                <tbody style="font-size: 12px;">
                <c:forEach items="${page.content}" var="investmap" varStatus="status">
                  <tr>
                    <td class="table_no" width="80" align="center"></td>
                    <td>${ investmap[2] }</td>
                    <td>${ investmap[0] }</td>
                    <td>
                      <fmt:formatNumber value="${ investmap[1] }" var="fund" pattern="0.000000"></fmt:formatNumber>
                      <c:out value="${ fund }"></c:out>
                    </td>
                    <td width="220" align="center">
                        <a href = "javascript:exportExcel('<c:out value="${province}" />', '<c:out value="${investmap[2]}" />')"
                           class="btn btn-info btn-xs edit"><i class="fa fa-edit"></i>导出</a>
                        &nbsp;&nbsp;
                    </td>
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