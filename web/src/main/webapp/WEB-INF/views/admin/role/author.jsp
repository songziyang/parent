<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<script type="text/javascript">
    $(function () {
    		
    		var purviewIds =  '<c:out value="${role.purviews}"/>';
    		var ids = purviewIds.split(",");
    		$('input[level=3]').each(function(index,element){
    			for (var i = 0; i < ids.length; i++) {
					if ($(element).val() == ids[i]) {
						 $(element).prop('checked', 'checked');
						 $(element).parents('controller').find('input[level=2]').prop('checked', 'checked')
						 $(element).parents('module').find('input[level=1]').prop('checked', 'checked')
					}
				}
    		 });
    		
    		
        $('input[level=1]').click(function () {
            var input_m = $(this).parents('module').find('input').first();
            $(this).parents('module').find('input:gt(0)').each(function(index,element){
            		input_m.prop('checked') ? $(element).prop('checked', 'checked') :$(element).removeAttr('checked');
            });
        })

        $('input[level=2]').click(function () {
         	var input_m = $(this).parents('module').find('input').first();
         	var input_c = $(this).parents('controller').find('input').first();
            $(this).parents('controller').find('input:gt(0)').each(function(index,element){
            		input_c.prop('checked') ? $(element).prop('checked', 'checked'):$(element).removeAttr('checked');
            });
           
            $(this).parents('module').find('input:gt(0)').each(function(index,element){
	        		if($(element).prop('checked')){
	        			input_m.prop('checked', 'checked');
	        			return false;
	        		} 
	        		input_m.removeAttr('checked');
             });
        })

        $('input[level=3]').click(function () {
            var input_m = $(this).parents('module').find('input').first();
            var input_c = $(this).parents('controller').find('input').first();
            if($(this).prop('checked')){
                input_m.prop('checked', 'checked');
                input_c.prop('checked', 'checked');
            }else{
	            	$(this).parents('controller').find('input[level=3]').each(function(index,element){
		        		if($(element).prop('checked')){
		        			input_c.prop('checked', 'checked');
		        			return false;
		        		} 
		        		input_c.removeAttr('checked')
		        		$(this).parents('module').find('input:gt(0)').each(function(index,element){
			            	var input_m = $(this).parents('module').find('input').first();
			        		if($(element).prop('checked')){
			        			input_m.prop('checked', 'checked');
			        			return false;
			        		} 
			        		input_m.removeAttr('checked');
		             });
	            	});
            }
        })
    })
</script>
<style type="text/css">
.table-scrollable {
	width: 100%;
	height: 100%;
	overflow-x: hidden;
	overflow-y: auto;
	border: 0px;
}
</style>
</head>
<body>

 <form action="${ctx}/admin/role/saveAuthorization" method="post">
        	 <input type="hidden" name="id" value='<c:out value="${role.id}"/>'   >
         <input type="hidden" name="created" value='<c:out value="${role.created}"/>'   >
         <input type="hidden" name="name" value='<c:out value="${role.name}"/>'   >
         <input type="hidden" name="remark" value='<c:out value="${role.remark}"/>'   >
         
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h4 class="modal-title">授权列表</h4>
		</div>
		<div class="modal-body">
			<div style="width: 100%; height: 60%;">
			<div class="table-scrollable">
				<div class="row">
					<div class="col-sm-12">
						<c:forEach items="${menuLst }" var="menus">
							<c:if test="${menus.parentMenu ==null }">
								<module>
								<div class="checkbox" style="clear: both;">
									<label> <input level="1" type="checkbox"  class="colored-blue">
										<span class="text"><c:out value="${ menus.name}" /></span>
									</label>
								</div>
								<c:forEach items="${menuLst }" var="menu">
									<c:if test="${ menu.parentMenu.id eq menus.id }">
										<controller>
										<div class="checkbox" style="margin-top: 10px; margin-left: 20px; clear: both;">
											<label> 
											<input level="2" type="checkbox"  class="colored-blue"> 
											<span class="text"> <c:out value="${ menu.name}" /></span>
											</label>
										</div>
										<div style="margin-left: 60px; height: auto; width: 80%">
											<c:forEach items="${purviewLst }" var="purviews">
												<c:if
													test="${ fn:split(menu.purflag,'_')[0]  eq fn:split(purviews.flag,'_')[0] }">
													<div class="checkbox" style="height:24px;float: left;padding: 0px;margin: 0px;">
														<label> <input level="3" type="checkbox"  class="colored-blue"  name="purviewids"
															value='<c:out value= "${purviews.id}" />'>
															 <span class="text"><c:out value="${purviews.name}" /></span>
														</label>
													</div>
												</c:if>
											</c:forEach>
										</div>
										</controller>
									</c:if>
								</c:forEach> </module>
							</c:if>
						</c:forEach>
					</div>
				</div>
			</div>
			</div>
		</div>
		<div class="modal-footer">
			 <button type="submit" class="btn btn-success" >授权</button>
			 <button type="button" class="btn btn-blue" data-dismiss="modal">取消</button>
		</div>
	</form>

	<%@include file="/static/inc/footer.inc"%>
</body>
</html>