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
    <style type="text/css">
        .form-inline .form-group {
            margin-bottom: 10px;
        }
    </style>
</head>
<body>

<div id="myModal" class="modal fade modal-success" tabindex="-1"
     role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true"
     style="display: none;">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<div class="page-breadcrumbs">
    <ul class="breadcrumb" style="line-height: 40px;">
        <li><i class="fa fa-home"></i> <a href="#">前台用户管理</a></li>
        <li><a href="#">三方渠道管理</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>三方渠道管理</h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
        </a> <a class="refresh" id="refresh-toggler"
                href="${ctx}/userinfo/channelmanager/listChannelManager"> <i
            class="glyphicon glyphicon-refresh"></i>
    </a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
            class="glyphicon glyphicon-fullscreen"></i>
    </a>
    </div>
    <!--Header Buttons End-->
</div>
<!-- /Page Header -->
<!-- Page Body -->
<form action="${ctx}/userinfo/channelmanager/listChannelManager"
      method="post" id="listForm" name="listForm">
    <div class="page-body">
        <div class="row">
            <div class="col-xs-12 col-md-12">
                <div class="widget">
                    <div class="widget-header  with-footer">
                        <span class="widget-caption">三方渠道管理</span>

                        <div class="widget-buttons">
                            <a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
                            </a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
                        </a>
                        </div>
                    </div>
                    <div class="widget-body">
                        <div class="flip-scroll">

                            <div style="text-align: right; margin-bottom: 10px;">
                                <shiro:hasPermission name="channelmanager_edit">
                                    <a href="${ctx}/userinfo/channelmanager/channelmanager/1" data-toggle="modal"
                                       data-target="#myModal"
                                       class="btn btn-labeled btn-success"> <i
                                            class="btn-label glyphicon glyphicon-plus"></i>充值渠道
                                    </a>
                                    <a href="${ctx}/userinfo/channelmanager/channelmanager/2" data-toggle="modal"
                                       data-target="#myModal"
                                       class="btn btn-labeled btn-success"> <i
                                            class="btn-label glyphicon glyphicon-plus"></i>提现渠道
                                    </a>
                                </shiro:hasPermission>
                            </div>

                            <table class="table table-hover table-striped table-bordered">
                                <thead style="font-size: 16px; font-weight: bold;">
                                <tr>
                                    <th width="80" style="text-align: center;">序号</th>
                                    <th>类型</th>
                                    <th>限制银行</th>
                                    <th>提示信息</th>
                                    <th>设置时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody style="font-size: 12px;">
                                <c:forEach items="${page.content}" var="channel" varStatus="status">
                                    <tr>
                                        <td class="table_no" width="80" align="center"></td>
                                        <td>
                                            <c:if test="${channel.type eq 1}">
                                                充值
                                            </c:if>
                                            <c:if test="${channel.type eq 2}">
                                                提现
                                            </c:if>
                                        </td>
                                        <td><c:out value="${channel.limitInfo}"/></td>
                                        <td><c:out value="${channel.promptInfo}"/></td>
                                        <td><fmt:formatDate value="${channel.createDate}" pattern="yyyy年MM月dd日 HH时mm分"/></td>
                                        <td width="200" align="center">
                                            <shiro:hasPermission name="channelmanager_del">
                                                <a onclick="removeData('${ctx}/userinfo/channelmanager/deleteChannel/','<c:out value="${channel.id}" />')"
                                                   class="btn btn-danger btn-xs delete" ><i class="fa fa-trash-o" ></i>删除限制</a>
                                            </shiro:hasPermission>
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