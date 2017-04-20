<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <script type="text/javascript">
        $(function () {

            $('input[level=1]').click(function () {
                var input_m = $(this).parents('module').find('input').first();
                $(this).parents('module').find('input:gt(0)').each(function (index, element) {
                    input_m.prop('checked') ? $(element).prop('checked', 'checked') : $(element).removeAttr('checked');
                });
            })

        });
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

<form action="${ctx}/userinfo/channelmanager/saveAuthorization" method="post">

    <input type="hidden" name="type" value='<c:out value="${type}"/>'>

    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"
                aria-hidden="true">×
        </button>
        <h4 class="modal-title">
            <c:if test="${type eq 1}">充值限制管理</c:if>
            <c:if test="${type eq 2}">提现限制管理</c:if>
        </h4>
    </div>
    <div class="modal-body">
        <div style="width: 100%; height: 40%;">
            <div class="table-scrollable">
                <div class="row">
                    <div class="col-sm-12">
                        <module>
                            <div class="checkbox" style="clear: both;">
                                <label> <input level="1" type="checkbox" class="colored-blue">
                                    <span class="text">全部</span>
                                </label>
                            </div>
                            <div class="checkbox" style="margin-top: 10px; margin-left: 20px; clear: both;">

                                <div class="checkbox" style="height:24px;float: left;padding: 0px;margin: 0px;">
                                    <label>
                                        <input level="2" type="checkbox" class="colored-blue" value='中国工商银行'
                                               name="limitinfos">
                                        <span class="text">中国工商银行</span>
                                    </label>
                                </div>

                                <div class="checkbox" style="height:24px;float: left;padding: 0px;margin: 0px;">
                                    <label>
                                        <input level="2" type="checkbox" class="colored-blue" value='中国农业银行'
                                               name="limitinfos">
                                        <span class="text">中国农业银行</span>
                                    </label>
                                </div>

                                <div class="checkbox" style="height:24px;float: left;padding: 0px;margin: 0px;">
                                    <label>
                                        <input level="2" type="checkbox" class="colored-blue" value='中国建设银行'
                                               name="limitinfos">
                                        <span class="text">中国建设银行</span>
                                    </label>
                                </div>

                                <div class="checkbox" style="height:24px;float: left;padding: 0px;margin: 0px;">
                                    <label>
                                        <input level="2" type="checkbox" class="colored-blue" value='中国银行'
                                               name="limitinfos">
                                        <span class="text">中国银行</span>
                                    </label>
                                </div>

                                <div class="checkbox" style="height:24px;float: left;padding: 0px;margin: 0px;">
                                    <label>
                                        <input level="2" type="checkbox" class="colored-blue" value='中国交通银行'
                                               name="limitinfos">
                                        <span class="text">中国交通银行</span>
                                    </label>
                                </div>

                                <div class="checkbox" style="height:24px;float: left;padding: 0px;margin: 0px;">
                                    <label>
                                        <input level="2" type="checkbox" class="colored-blue" value='兴业银行'
                                               name="limitinfos">
                                        <span class="text">兴业银行</span>
                                    </label>
                                </div>

                                <div class="checkbox" style="height:24px;float: left;padding: 0px;margin: 0px;">
                                    <label>
                                        <input level="2" type="checkbox" class="colored-blue" value='中信银行'
                                               name="limitinfos">
                                        <span class="text">中信银行</span>
                                    </label>
                                </div>

                                <div class="checkbox" style="height:24px;float: left;padding: 0px;margin: 0px;">
                                    <label>
                                        <input level="2" type="checkbox" class="colored-blue" value='中国光大银行'
                                               name="limitinfos">
                                        <span class="text">中国光大银行</span>
                                    </label>
                                </div>

                                <div class="checkbox" style="height:24px;float: left;padding: 0px;margin: 0px;">
                                    <label>
                                        <input level="2" type="checkbox" class="colored-blue" value='中国邮政储蓄银行'
                                               name="limitinfos">
                                        <span class="text">中国邮政储蓄银行</span>
                                    </label>
                                </div>

                                <div class="checkbox" style="height:24px;float: left;padding: 0px;margin: 0px;">
                                    <label>
                                        <input level="2" type="checkbox" class="colored-blue" value='上海银行'
                                               name="limitinfos">
                                        <span class="text">上海银行</span>
                                    </label>
                                </div>

                                <div class="checkbox" style="height:24px;float: left;padding: 0px;margin: 0px;">
                                    <label>
                                        <input level="2" type="checkbox" class="colored-blue" value='浦东发展银行'
                                               name="limitinfos">
                                        <span class="text">浦东发展银行</span>
                                    </label>
                                </div>


                                <div class="checkbox" style="height:24px;float: left;padding: 0px;margin: 0px;">
                                    <label>
                                        <input level="2" type="checkbox" class="colored-blue" value='招商银行'
                                               name="limitinfos">
                                        <span class="text">招商银行</span>
                                    </label>
                                </div>

                                <div class="checkbox" style="height:24px;float: left;padding: 0px;margin: 0px;">
                                    <label>
                                        <input level="2" type="checkbox" class="colored-blue" value='民生银行'
                                               name="limitinfos">
                                        <span class="text">民生银行</span>
                                    </label>
                                </div>

                                <div class="checkbox" style="height:24px;float: left;padding: 0px;margin: 0px;">
                                    <label>
                                        <input level="2" type="checkbox" class="colored-blue" value='华夏银行'
                                               name="limitinfos">
                                        <span class="text">华夏银行</span>
                                    </label>
                                </div>


                            </div>
                        </module>

                        <br/>
                        <br/>
                        <br/>
                        <br/>

                        <div class="row">
                            <div class="col-sm-12">
                                <div class="form-group">
								<span class="form-group input-icon icon-right"> <label>提示信息</label>
										<textarea style="padding-left: 10px;" class="form-control"
                                                  rows="4" name="promptInfo" maxlength="100"
                                                  data-bv-stringlength-max="100"
                                                  data-bv-stringlength-message="提示信息不能超过100个字"></textarea>
								</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button type="submit" class="btn btn-success">确认</button>
        <button type="button" class="btn btn-blue" data-dismiss="modal">取消</button>
    </div>
</form>
<%@include file="/static/inc/footer.inc" %>
</body>
</html>