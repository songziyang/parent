<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script src="${ctx}/static/lib/beyond/js/jquery-2.0.3.min.js"></script>
<script>
	var ctx= '${ctx}';
	$(document).ready(function() {
		$.post("${ctx}/admin/menu/listRoleMenuJSON",function(data){
			//console.log(data);
			var json=eval(data);
			var parentLi = $("#menu-tree");
			$.each(json, function(i, item) {
				if(json[i].parentMenu == null){
				if(i==0){
					var li=$('<li/>').addClass("active open");
				}else{
					var li=$('<li/>').addClass("active");
				}
				li.append('<a href="${ctx}" class="menu-dropdown">'
						+'<i class="menu-icon fa fa-list"></i>'
						+'<span class="menu-text">'+json[i].name+'</span><i class="menu-expand"></i></a>');
				var ul=$('<ul/>').attr('class','submenu');
				$.each(json, function(j, item) {
					if(json[j].parentMenu !=null && json[j].parentMenu.id ==json[i].id){
						ul.append('<li onclick="javascript:changeClass(this);" ><a target="rightFrame" href="${ctx}/'+json[j].url+'"><span class="menu-text">'
								+json[j].name+'</span></a></li>');
					}
				});
					li.append(ul);
					parentLi.append(li);
				}
	        }); 
			//console.log(parentLi);
		});
		
	}); 
	

	function changeClass(obj) {
		$('.submenu').find('li').each(function(index, element) {
			$(element).removeClass('active');
		});
		$(obj).addClass('active');
	}
</script>

<div class="page-sidebar" id="sidebar">
	<div class="sidebar-header-wrapper">
		<input type="text" style="color: #2dc3e8;font-size: 15px;" class="searchinput" readonly="readonly" value="<c:out value="${realname}" />" /> <i
			class="searchicon fa fa-user-md"></i>

	</div>
	<ul class="nav sidebar-menu" id="menu-tree">
	</ul>

	
</div>

