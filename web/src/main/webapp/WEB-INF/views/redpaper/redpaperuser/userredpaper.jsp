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
			<li><a href="#">红包发放</a></li>
		</ul>
	</div>
	<!-- /Page Breadcrumb -->
	<!-- Page Header -->
	<div class="page-header position-relative">
		<div class="header-title">
			<h1>发放用户列表</h1>
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

					<div class="widget">
						<div class="widget-header  with-footer">
							<span class="widget-caption">发放用户列表</span>
							<div class="widget-buttons">
								<a href="#" data-toggle="maximize"> <i class="fa fa-expand"></i>
								</a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"></i>
								</a>
							</div>
						</div>


						<div class="widget-body">
							<div class="flip-scroll">
								<div style="text-align: right; margin-bottom: 10px;">
									<a href="${ctx}/redpapermanager/redpaperuser/listRedpaper"
										class="btn btn-labeled btn-blue"> <i
										class="btn-label glyphicon glyphicon-arrow-left"></i>返回
									</a>
								</div>

								<table class="table table-hover table-striped table-bordered">
									<thead style="font-size: 16px; font-weight: bold;">
										<tr>
											<th width="80">序号</th>
											<th>红包名称</th>
											<th>用户名称</th>
											<th>红包类别</th>
											<th>活动开始日期</th>
											<th>活动结束日期</th>
											<th>领取有效天数</th>
											<th>使用有效天数</th>
											<th>每份数量</th>
											<th>状态</th>
										</tr>
									</thead>
									<tbody style="font-size: 12px;">
										<c:forEach items="${page.content}" var="redpaperuser"
											varStatus="status">
											<tr>
												<td><c:out value="${status.count}" /></td>
												<td><c:out
														value="${redpaperuser.redPaper.redPaperName}" /></td>
												<td>
													<c:choose>
														<c:when test="${redpaperuser.user.userLeve.gradeNum eq 1}"><img src="${ctx}/static/lib/images/vip1.png" /></c:when>
														<c:when test="${redpaperuser.user.userLeve.gradeNum eq 2}"><img src="${ctx}/static/lib/images/vip2.png" /></c:when>
													</c:choose>
													<c:out value="${redpaperuser.user.username}" />
												</td>
												<td><c:choose>
														<c:when test="${redpaperuser.redPaper.redPaperType==1}">  
												        现金
												   </c:when>
														<c:when test="${redpaperuser.redPaper.redPaperType==2}">  
												         投资   
												    </c:when>
														<c:when test="${redpaperuser.redPaper.redPaperType==3}">  
												        贡献值  
												    </c:when>
														<c:when test="${redpaperuser.redPaper.redPaperType==4}">  
												       利率 
												    </c:when>
														<c:when test="${redpaperuser.redPaper.redPaperType==5}">  
												       日加息券
												    </c:when>
													</c:choose></td>
												<td><fmt:formatDate value="${redpaperuser.fbDate}"
														pattern="yyyy-MM-dd" /></td>
												<td><fmt:formatDate value="${redpaperuser.endDate}"
														pattern="yyyy-MM-dd" /></td>
												<td><c:out value="${redpaperuser.redPaper.receiveDays}" /></td>
												<td><c:out value="${redpaperuser.redPaper.useDays}" /></td>
												<td><c:out value="${redpaperuser.redPaper.giveValue}" /></td>
												<td><c:choose>
														<c:when test="${redpaperuser.state==0}">  
												        未领用
												   </c:when>
														<c:when test="${redpaperuser.state==1}">  
												         未使用   
												    </c:when>
														<c:when test="${redpaperuser.state==2}">  
												        已冻结  
												    </c:when>
														<c:when test="${redpaperuser.state==3}">  
												       已使用 
												    </c:when>
														<c:when test="${redpaperuser.state==4}">  
												       已过期
												    </c:when>
													</c:choose></td>
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