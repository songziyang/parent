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
    <script src="${ctx}/static/lib/echart/echarts.min.js"></script>
    <script src="${ctx}/static/lib/echart/china.js"></script>
    <style type="text/css">
        .form-inline .form-group {
            margin-bottom: 10px;
        }
    </style>
</head>
<body>

<!-- Page Breadcrumb -->
<div class="page-breadcrumbs">
    <ul class="breadcrumb" style="line-height: 40px;">
        <li><i class="fa fa-home"></i> <a href="#">统计管理</a></li>
        <li><a href="#">在投分布</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>在投分布</h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
        </a> <a class="refresh" id="refresh-toggler"
                href="${ctx}/platform/platforminvestmap/listInvestMap"> <i
            class="glyphicon glyphicon-refresh"></i>
    </a> <a class="fullscreen" id="fullscreen-toggler" href="#"> <i
            class="glyphicon glyphicon-fullscreen"></i>
    </a>
    </div>
    <!--Header Buttons End-->
</div>
<!-- /Page Header -->
<!-- Page Body -->
<form action="${ctx}/product/platformTrading/list" method="post" id="listForm" name="listForm">
    <div class="page-body">
        <div>
            <div id="main" style="height: 600px;"></div>
            <script type="text/javascript">

                // 基于准备好的dom，初始化echarts实例
                var myChart = echarts.init(document.getElementById('main'))

                var option = {
                    title: {
                        text: '在投分布',
                        subtext: '实时在投金额和人数分布',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'item'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: ['投资金额(万)', '投资人数(人)']
                    },
                    visualMap: {
                        min: 0,
                        max: 5000,
                        left: 'left',
                        top: 'bottom',
                        text: ['高', '低'],           // 文本，默认为数值文本
                        calculable: true,
                        inRange: {
                            color: ['#FFFFFF', '#eac736', '#d94e5d']
                        }
                    },
                    toolbox: {
                        show: true,
                        orient: 'vertical',
                        left: 'right',
                        top: 'center',
                        feature: {
                            dataView: {readOnly: false},
                            restore: {},
                            saveAsImage: {}
                        }
                    },
                    series: [
                        {
                            name: '投资金额(万)',
                            type: 'map',
                            mapType: 'china',
                            roam: false,
                            label: {
                                normal: {
                                    show: true
                                },
                                emphasis: {
                                    show: true
                                }
                            },
                            data: ${investFunds}
                        },
                        {
                            name: '投资人数(人)',
                            type: 'map',
                            mapType: 'china',
                            label: {
                                normal: {
                                    show: true
                                },
                                emphasis: {
                                    show: true
                                }
                            },
                            data: ${investPersons}
                        }
                    ]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);

                myChart.on('click', function (params) {
                    // 控制台打印数据的名称
                    console.log(params.name);
                    var url = "${ctx}/platform/platforminvestmap/investProvince?pname=" + encodeURI(encodeURI(params.name))
                    window.location.href = url;
                });


            </script>
        </div>
    </div>
</form>
<%@include file="/static/inc/footer.inc" %>
</body>
</html>