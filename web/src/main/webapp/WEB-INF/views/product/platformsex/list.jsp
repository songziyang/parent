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
        <li><a href="#">性别年龄统计</a></li>
    </ul>
</div>
<!-- /Page Breadcrumb -->
<!-- Page Header -->
<div class="page-header position-relative">
    <div class="header-title">
        <h1>性别年龄统计</h1>
    </div>
    <!--Header Buttons-->
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#"> <i class="fa fa-arrows-h"></i>
        </a> <a class="refresh" id="refresh-toggler"
                href="${ctx}/product/platformTrading/list"> <i
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
            <div id="main1" style="width:50%;height:400px;float:left;"></div>
            <div id="main2" style="width:50%;height:400px;float:left;"></div>
            <script type="text/javascript">

                // 基于准备好的dom，初始化echarts实例
                var myChart = echarts.init(document.getElementById('main1'))

                option = {
                    title: {
                        text: '男女比例',
                        subtext: '男女比例',
                        x: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: ['男', '女']
                    },
                    series: [
                        {
                            name: '男女比例',
                            type: 'pie',
                            radius: '55%',
                            center: ['50%', '60%'],
                            data: ${sexnum},
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);


                // 基于准备好的dom，初始化echarts实例
                var myChart = echarts.init(document.getElementById('main2'))

                option1 = {
                    title: {
                        text: '年龄比例',
                        subtext: '年龄比例',
                        x: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: ['0-25', '25-35', '35-45', '45-55', '55-65','65-100']
                    },
                    series: [
                        {
                            name: '年龄比例',
                            type: 'pie',
                            radius: '55%',
                            center: ['50%', '60%'],
                            data: ${agenum},
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option1);
            </script>
        </div>
    </div>
</form>
<%@include file="/static/inc/footer.inc" %>
</body>
</html>