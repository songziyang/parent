<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
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
</head>
<body>

	<!-- Page Breadcrumb -->
	<div class="page-breadcrumbs">
		<ul class="breadcrumb" style="line-height: 40px;">
			<li><i class="fa fa-home"></i> <a href="#">红包管理</a></li>
			<li><a href="#">红包维护</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>红包列表</h1>
		</div>
		<!--Header Buttons-->
		<div class="header-buttons">
			<a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
			</a> <a class="refresh" id="refresh-toggler"
				href="${ctx}/redpapermanager/redpaperuser/listRedpaper"> <i
				class="glyphicon glyphicon-refresh"></i>
			</a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
				class="glyphicon glyphicon-fullscreen"></i>
			</a>
		</div>
		<!--Header Buttons End-->
	</div>
	<!-- /Page Header -->
	<!-- Page Body -->
	<form action="${ctx}/redpapermanager/redpaperuser/listRedpaper"
		method="post" id="listForm" name="listForm">
		<div class="page-body">
			<div class="row">
				<div class="col-xs-12 col-md-12">
					<div class="row">
						<div class="col-lg-12 col-sm-12 col-xs-12">
							<div class="widget collapsed">
								<div class="widget-header bordered-bottom bordered-sky">
									<i class="widget-icon fa fa-arrows-v blue"></i> <span
										class="widget-caption">查询条件</span>
									<div class="widget-buttons">
										<a href="#"> </a> <a href="#" data-toggle="collapse"> <i
											class="fa fa-plus blue"></i>
										</a>
									</div>
									<!--Widget Buttons-->
								</div>
								<!--Widget Header-->
								<div class="widget-body">
									<div class="form-inline" role="form">
										<div class="form-group">
											<label class="control-label no-padding-right"></label>活动日期：从
											<input type="text" name="activityStartDate"
												class="form-control" tabindex="1"
												style="padding-left: 50px;" required maxlength="40"
												onfocus="WdatePicker({skin:'twoer'})"> 到 <input
												type="text" name="activityEndDate" class="form-control"
												tabindex="1" style="padding-left: 50px;" required
												maxlength="40" onfocus="WdatePicker({skin:'twoer'})">
										</div>
										<div class="form-group">
											<label class="control-label no-padding-right">红包状态</label> <select
												class="form-control" name="status" style="width: 200px;">
												<option value="0">使用中</option>
												<option value="-1">已停用</option>
											</select>
										</div>

										<div class="form-group">
											&nbsp;&nbsp; <a href="javascript:searchData();"
												class="btn btn-labeled btn-blue"> <i
												class="btn-label fa fa-search"></i>查询
											</a>
										</div>
									</div>
								</div>
								<!--Widget Body-->
							</div>
							<!--Widget-->
						</div>
					</div>
					<div class="widget">
						<div class="widget-header  with-footer">
							<span class="widget-caption">红包列表</span>
							<div class="widget-buttons">
								<a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
								</a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
								</a>
							</div>
						</div>
						<div class="widget-body">
							<div class="flip-scroll">
								<div style="text-align: right; margin-bottom: 10px;">
									<shiro:hasPermission name="userredpaper_give">
										<a class="btn btn-labeled btn-success"
											href="${ctx}/redpapermanager/redpaperuser/sendredpacket"><i
											class="btn-label glyphicon glyphicon-envelope"></i>红包发放</a>
									</shiro:hasPermission>
								</div>

								<table class="table table-hover table-striped table-bordered">
									<thead style="font-size: 16px; font-weight: bold;">
										<tr>
											<th width="80" style="text-align: center;">序号</th>
											<th>红包名称</th>
											<th>红包状态</th>
											<th>触发类别</th>
											<th>红包类别</th>
											<th>活动开始日期</th>
											<th>活动结束日期</th>
											<th>领取有效天数</th>
											<th>使用有效天数</th>
											<th>投资有效天数</th>
											<th>每份数量</th>
											<th width="200px" style="text-align: center;">操作</th>
										</tr>
									</thead>
									<tbody style="font-size: 12px;">
										<c:forEach items="${page.content}" var="redpaper"
											varStatus="status">
											<tr>
												<td class="table_no" width="80" align="center"></td>
												<td><c:out value="${redpaper.redPaperName}" /></td>
												<td><c:out
														value="${redpaper.status eq 0 ? '使用中':'已停用'}" /></td>
												<td><c:choose>
														<c:when test="${redpaper.detonateEvent==0}">  
												       		手动
												   </c:when>
														<c:when test="${redpaper.detonateEvent==1}">  
												         注册   
												    </c:when>
														<c:when test="${redpaper.detonateEvent==2}">  
												        充值
												    </c:when>
														<c:when test="${redpaper.detonateEvent==3}">  
												       投资 
												    </c:when>
														<c:when test="${redpaper.detonateEvent==4}">  
												       普通推荐 
												    </c:when>
														<c:when test="${redpaper.detonateEvent==5}">  
												       VIP推荐 
												    </c:when>
														<c:when test="${redpaper.detonateEvent==6}">  
												       升级VIP 
												    </c:when>
													</c:choose></td>
												<td><c:choose>
														<c:when test="${redpaper.redPaperType==1}">  
												        现金
												   </c:when>
														<c:when test="${redpaper.redPaperType==2}">  
												         投资   
												    </c:when>
														<c:when test="${redpaper.redPaperType==3}">  
												        贡献值  
												    </c:when>
														<c:when test="${redpaper.redPaperType==4}">  
												       利率 
												    </c:when>
														<c:when test="${redpaper.redPaperType==5}">  
												     	日加息券
												    </c:when>
													</c:choose></td>
												<td><fmt:formatDate value="${redpaper.fbDate}"
														pattern="yyyy-MM-dd" /></td>
												<td><fmt:formatDate value="${redpaper.endDate}"
														pattern="yyyy-MM-dd" /></td>
												<td><c:out value="${redpaper.receiveDays}" /></td>
												<td><c:out value="${redpaper.useDays}" /></td>
												<td><c:out value="${redpaper.investDays}" /></td>
												<td><c:out value="${redpaper.giveValue}" /></td>
												<td style="text-align: center;"><shiro:hasPermission
														name="userredpaper_info">
														<a
															href='${ctx}/redpapermanager/redpaperuser/redpareuserbyid/<c:out value="${redpaper.id}"/>'
															class="btn btn-info btn-xs edit"><i
															class="fa fa-edit"></i> 查看</a>
													</shiro:hasPermission></td>
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
	<%@include file="/static/inc/footer.inc"%>
</body>
</html>