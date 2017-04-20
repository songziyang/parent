<%
	String basepath = request.getContextPath();
%>

<link rel="shortcut icon" href="<%=basepath %>/static/lib/beyond/img/favicon.png" type="image/x-icon">
 <!--Basic Styles-->
<link href="<%=basepath %>/static/lib/beyond/css/bootstrap.min.css" rel="stylesheet" />
<link id="bootstrap-rtl-link" href="" rel="stylesheet" />
<link href="<%=basepath %>/static/lib/beyond/css/font-awesome.min.css" rel="stylesheet" />
<link href="<%=basepath %>/static/lib/beyond/css/weather-icons.min.css" rel="stylesheet" />

 <!--Beyond styles-->
<link id="beyond-link" href="<%=basepath %>/static/lib/beyond/css/beyond.min.css" rel="stylesheet" />
<link href="<%=basepath %>/static/lib/beyond/css/demo.min.css" rel="stylesheet" />
<link href="<%=basepath %>/static/lib/beyond/css/typicons.min.css" rel="stylesheet" />
<link href="<%=basepath %>/static/lib/beyond/css/animate.min.css" rel="stylesheet" />
<link id="skin-link" href="" rel="stylesheet" type="text/css" />
<link href="<%=basepath %>/static/lib/css/main.css" rel="stylesheet" />

<script type="text/javascript">
	var ctx = '<%=basepath %>';
	function showTip(){
		var tip = '<c:out value="${sessionScope.message}" />';
		
		var error = '<c:out value="${sessionScope.error}" />';
		
		if(tip!='' && tip.length>0){
			Notify(tip, 'top-left', '5000', 'success', 'fa-check', true);
		}
		
		if(error!='' && error.length>0){
			Notify(error, 'top-left', '5000', 'warning', 'fa-warning', true);
		}
		<c:set value="" var="message" scope="session" />
		
		<c:set value="" var="error" scope="session" />
	}
</script>
<script src="<%=basepath %>/static/lib/beyond/js/skins.min.js"></script>

<script src="<%=basepath%>/static/lib/beyond/js/jquery-2.0.3.min.js"></script>

<script src="<%=basepath%>/static/lib/js/checkbox.js"></script>

<script src="<%=basepath%>/static/lib/My97DatePicker/WdatePicker.js"></script>

<script src="<%=basepath%>/static/lib/ueditor/ueditor.config.js"></script>

<script src="<%=basepath%>/static/lib/ueditor/ueditor.parse.js"></script>

<script src="<%=basepath%>/static/lib/ueditor/ueditor.all.js"></script>