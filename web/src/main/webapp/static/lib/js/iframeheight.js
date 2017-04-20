$(document).ready(function() {
	// 获取父页面的容器
	var par = window.parent.document.getElementById('rightFrame');
	// 当前页面的高度
	var height = window.document.body.offsetHeight;
	// 设置容器高度
	par.style.height = height + 'px';
	
	 $("#editForm").bootstrapValidator({
         feedbackIcons: {
             valid: 'glyphicon glyphicon-ok',
             invalid: 'glyphicon glyphicon-remove',
             validating: 'glyphicon glyphicon-refresh'
         }
	 });
	 
	 showTip();
});


function searchData() {
	document.listForm.submit();
}

function removeData(urls,id){
	if(urls != null && id != null){
		
	}else{
		if(hasChecked(listForm)==false){
			Notify('请先选择要删除的记录！', 'top-left', '5000', 'warning', 'fa-warning', true);
			return ;
		}
	}
	
    bootbox.dialog({
        message: "您确定要删除选中记录吗？",
        title: "删除提示",
        className: "modal-darkorange",
        buttons: {
            success: {
                label: "确认",
                className: "btn-default",
                callback: function () {
                		if(urls != null && id != null){
                			window.location.href = urls+''+id;
                		}else{
                			document.listForm.action =urls; 
                			document.listForm.target="_self";
                			document.listForm.submit();
                			
                		}
                }
            },
            "取消": {
                className: "btn-default",
                callback: function () { }
            }
        }
    });
}


