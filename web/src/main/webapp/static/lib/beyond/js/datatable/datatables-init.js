var InitiateSimpleDataTable = function () {
    return {
        init: function () {
          var oTable = $('#simpledatatable').dataTable({
        	  	"sDom": "Tft<'row DTTTFooter'<'col-sm-6'li><'col-sm-6'p>>",
            "sPaginationType": "bootstrap",
            "bFilter": false, //过滤功能
            "bPaginate": true, //翻页功能
            "bLengthChange": true, //改变每页显示数据数量
            "bProcessing": true, //当datatable获取数据时候是否显示正在处理提示信息。
            "bSort": false, //排序功能
            "oLanguage": {
	            	"sLengthMenu": "每页 _MENU_ 条&nbsp;&nbsp;",
	            	"sZeroRecords": "抱歉， 没有找到",
	            	"sInfo": "共 _TOTAL_ 条",
	            	"sInfoEmpty": "没有数据",
	            	"sProcessing": "正在加载中...",  
	            	"sInfoFiltered": "(从 _MAX_ 条数据中检索)",
	            	"oPaginate": {
	            		"sFirst": "首页",
	            		"sPrevious": "上一页",
	            		"sNext": "下一页",
	            		"sLast": "尾页"
	            	},
	            	"sZeroRecords": "没有检索到数据",
            },
                "oTableTools": {
                    "aButtons": [
                    ],
                },
                
            });

            //Check All Functionality
            jQuery('#simpledatatable .group-checkable').change(function () {
                var set = $(".checkboxes");
                var checked = jQuery(this).is(":checked");
                jQuery(set).each(function () {
                    if (checked) {
                        $(this).prop("checked", true);
                        $(this).parents('tr').addClass("active");
                    } else {
                        $(this).prop("checked", false);
                        $(this).parents('tr').removeClass("active");
                    }
                });

            });
            jQuery('#simpledatatable tbody tr .checkboxes').change(function () {
                $(this).parents('tr').toggleClass("active");
            });

        }

    };

}();
