<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <title>系统登录</title>
    <link href="${ctx}/static/lib/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript" src="${ctx}/static/lib/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/lib/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/lib/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="${ctx}/static/lib/js/sha256.js"></script>
    <script type="text/javascript" src="${ctx}/static/lib/js/angular.min.js" ></script>
    <script type="text/javascript" src="${ctx}/static/lib/js/angular-cookies.min.js" ></script>
    <script type="text/javascript" src="${ctx}/static/lib/js/angular-messages.js"></script>
    <!-- 登录服务 -->
    <script type="text/javascript" src="${ctx}/static/admin/controllers/loginController.js" ></script>
    <script type="text/javascript" src="${ctx}/static/admin/services/loginService.js" ></script>
    <script type="text/javascript" src="${ctx}/static/admin/directives/loginDirective.js" ></script>


    <style type="text/css">
        * {font-family: "Verdana", "Tahoma", "Lucida Grande", "Microsoft YaHei", "Hiragino Sans GB", sans-serif;}
        body {
            background: url(${ctx}/static/lib/images/loginbg_01.jpg) no-repeat center center fixed;
            -webkit-background-size: cover;
            -moz-background-size: cover;
            -o-background-size: cover;
            background-size: cover;
        }
        a:link {color: #285e8e;}
        .main_box {
            position: absolute; top:50%; left:50%; margin-top:-260px; margin-left: -300px; padding: 30px; width:600px; height:460px;
            background: #FAFAFA; background: rgba(255,255,255,0.5); border: 1px #DDD solid;
            border-radius: 5px;
            -webkit-box-shadow: 1px 5px 8px #888888; -moz-box-shadow: 1px 5px 8px #888888; box-shadow: 1px 5px 8px #888888;
        }
        .main_box .setting {position: absolute; top: 5px; right: 10px; width: 10px; height: 10px;}
        .main_box .setting a {color: #FF6600;}
        .main_box .setting a:hover {color: #555;}
        .login_logo {margin-bottom: 20px; height: 45px; text-align: center;}
        .login_logo img {height: 45px;}
        .login_msg {text-align: center; font-size: 16px;}
        .login_form {padding-top: 20px; font-size: 16px;}
        .login_box .form-control {display: inline-block; *display: inline; zoom: 1; width: auto; font-size: 18px;}
        .login_box .form-control.x319 {width: 319px;}
        .login_box .form-control.x164 {width: 164px;}
        .login_box .form-group {margin-bottom: 20px;}
        .login_box .form-group label.t {width: 120px; text-align: right; cursor: pointer;}
        .login_box .form-group.space {padding-top: 15px; border-top: 1px #FFF dotted;}
        .login_box .form-group img {margin-top: 1px; height: 32px; vertical-align: top;}
        .login_box .m {cursor: pointer;}
        .bottom {text-align: center; font-size: 12px;}
    </style>
    <script type="text/javascript">
        var ctx = "${ctx}";
        var COOKIE_NAME = 'sys__username';
    </script>
</head>
<body>

<div   ng-app="admin.login" ng-controller="loginCtrl" >
    <div id="msg" class="row" ng-show="msgShow">
        <div class="col-xs-4"></div>
        <div class="col-xs-4 text-center" style="background-color:#eca100;height:30px;color:#ffffff;line-height:30px; border-radius: 0px 0px 5px 5px;border:solid 1px #ba8800;box-shadow:1px 1px 1px gray">{{msg}}</div>
        <div class="col-xs-4"></div>
    </div>

    <div class="main_box">
        <div class="setting"><a href="javascript:;" ng-click="choose_bg();" title="更换背景"><span class="glyphicon glyphicon-th-large"></span></a></div>
        <div class="login_box">
            <div class="login_logo">
                <img src="${ctx}/static/lib/images/logo.png" >
            </div>
            <div class="login_form">
                <form ng-submit="login()" id="login_form" name="login_form" method="post" novalidate>
                    <div class="form-group">
                        <label for="j_username" class="t">用户名：</label> <input id="username" value="" name="username" ng-model="user.username" type="text" class="form-control x319 in" autocomplete="off" placeholder="请输入用户名" required ng-trim="true" login-input data-toggle="tooltip" data-placement="right" title="">
                    </div>
                    <div class="form-group">
                        <label for="j_password" class="t">密　码：</label> <input id="password" value=""  name="password" ng-model="user.password" type="password" class="form-control x319 in" required ng-trim="true" placeholder="请输入密码" login-input  data-toggle="tooltip" data-placement="right" title="">
                    </div>
                    <%--<div class="form-group">
                        <label for="j_captcha" class="t">验证码：</label> <input id="captcha" maxlength="5" name="captcha" ng-model="user.captcha" type="text" class="form-control x164 in" placeholder="请输入验证码" required ng-trim="true" login-input   data-toggle="tooltip" data-placement="bottom" title="">
                        <img captcha-img ng-src="{{captchaImg}}" style="width: 150px; height: 32px;cursor:pointer" title="单击刷新" ng-click="captchaImgClick()"/>
                    </div>--%>
                    <div class="form-group">
                         <label for="j_captcha" class="t">数字令牌：</label> <input id="captcha" maxlength="6" name="captcha" ng-model="user.captcha" type="text" class="form-control x319 in" placeholder="请输入数字令牌" required ng-trim="true" login-input   data-toggle="tooltip" data-placement="bottom" title="">
                     </div>
                    <div class="form-group">
                        <label class="t"></label>
                        <label for="j_remember" class="m"><input id="remember" type="checkbox" ng-model="user.remember">&nbsp;记住登陆账号!</label>
                    </div>
                    <div class="form-group space">
                        <label class="t"></label>　　　
                        <input type="submit" id="login_ok" value="&nbsp;登&nbsp;录&nbsp;" class="btn btn-primary btn-lg">&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="reset" value="&nbsp;重&nbsp;置&nbsp;" class="btn btn-default btn-lg">
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>