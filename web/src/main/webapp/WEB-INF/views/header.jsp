<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!-- Navbar -->
<div class="navbar">
	<div class="navbar-inner">
		<div class="navbar-container">
			<!-- Navbar Barnd -->
			<div class="navbar-header pull-left">
				<a href="#" class="navbar-brand"> <small> <img
						src="${ctx}/static/lib/beyond/img/logo.png" alt="" />
				</small>
				</a>
			</div>
			<!-- /Navbar Barnd -->
			<!-- Sidebar Collapse -->
			<div class="sidebar-collapse" id="sidebar-collapse">
				<i class="collapse-icon fa fa-bars"></i>
			</div>
			<!-- /Sidebar Collapse -->

			<div class="navbar-header pull-right">
				<div class="navbar-account">
					<ul class="account-area" style="right:  0px;">
						<li>
							    <a class="wave in dropdown-toggle"  title="退出系统" href="${ctx}/logout">
                                    <i class="icon fa  fa-power-off"></i>
                                </a>
						</li>
					</ul>
				</div>
			</div>

		</div>
	</div>
</div>
<!-- /Navbar -->