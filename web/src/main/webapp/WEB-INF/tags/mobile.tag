<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="page" type="org.springframework.data.domain.Page"
	required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	int current = page.getNumber();
	int pagesize = page.getSize();
	int begin = 1;
	int end = page.getTotalPages();
	long totalnum = page.getTotalElements();
	int min = 1;
	int max = 1;

	if (current - begin >= 2)
		min = current - 1;
	else
		min = begin;

	if (end - current >= 3)
		max = current + 3;
	else
		max = end;

	request.setAttribute("current", current);
	request.setAttribute("pagesize", pagesize);
	request.setAttribute("totalnum", totalnum);
	request.setAttribute("end", end);
	request.setAttribute("min", min);
	request.setAttribute("max", max);
%>

<div class="modal-footer no-margin-top" style="text-align: center;">
	<div class="row" style="text-align: center;">
		<div class="col-xs-12" style="text-align: center;">
			<ul class="pagination no-margin">
				<%
					if (page.hasPreviousPage()) {
				%>
				<li><a href='?pageCurrent=0&condition=${condition}'
					class="btn btn-default btn-sm">首页</a></li>
				<li><a href='?pageCurrent=${current-1}&condition=${condition}'
					class="btn btn-default btn-sm">上一页</a></li>
				<%
					} else {
				%>
				<li class="pre disabled"><a href="#"
					class="btn btn-default btn-sm">首页</a></li>
				<li class="disabled"><a href="#" class="btn btn-default btn-sm">上一页</a></li>
				<%
					}
				%>


				<c:forEach var="i" begin="${min}" end="${max}">
					<c:choose>
						<c:when test="${(i-1)== current}">
							<li class="active"><a
								href='?pageCurrent=${i-1}&condition=${condition}'
								class="btn btn-default btn-sm">${i}</a></li>
						</c:when>
						<c:otherwise>
							<li><a href='?pageCurrent=${i-1}&condition=${condition}'
								class="btn btn-default btn-sm">${i}</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>


				<%
					if (page.hasNextPage()) {
				%>
				<li><a href='?pageCurrent=${current+1}&condition=${condition}'
					class="btn btn-default btn-sm">下一页</a></li>
				<li><a
					href='?pageCurrent=${page.totalPages-1}&condition=${condition}'
					class="btn btn-default btn-sm">尾页</a></li>
				<%
					} else {
				%>
				<li class="disabled"><a href="#" class="btn btn-default btn-sm">下一页</a></li>
				<li class="next disabled"><a href="#"
					class="btn btn-default btn-sm">尾页</a></li>
				<%
					}
				%>

			</ul>
		</div>
	</div>
	<input type="hidden" name="pageCurrent"
		value='<c:out value="${pageCurrent}" />'>
</div>
<script type="text/javascript">
	jQuery(function($) {
		var current = $
		{
			current
		}
		;
		var pagesize = $
		{
			pagesize
		}
		;
		//复选框
		$('table th input:checkbox').on(
				'click',
				function() {
					//console.log("-----test----")
					var that = this;
					$(this).closest('table').find('tr > td input:checkbox')
							.each(function() {
								this.checked = that.checked;
								$(this).closest('tr').toggleClass('selected');
							});
				});
		//序号
		$('table tbody tr .table_no').each(function(index, e) {
			$(this).html(index + 1 + (current * pagesize));
			//console.log('index:'+index+'current:'+current+'pagesize:'+pagesize);
		});
	})
</script>

