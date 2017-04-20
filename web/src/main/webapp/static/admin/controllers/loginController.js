/**
 * Created by 刘驰 on 14-5-8. 
 */
angular.module('admin.login', ['ngCookies'])
    .controller('loginCtrl',['$scope','$window','$rootScope','$http','$location','loginService','$cookieStore',
      function($scope,$window,$rootScope,$http,$location,loginService,$cookieStore){

        //表单用户对象
        $scope.user = {};
        //错误提示信息显示开关
        $scope.msgShow = false;
        //验证图片地址
        $scope.captchaImg = ctx+'/kaptcha.jpg';

        //页面初始加载
        $scope.$watch('$viewContentLoaded', function() {  
            //$cookies.username = "admin1";
            //加载背景图片
            $scope.choose_bg();
            console.log("cookies::"+$cookieStore.get('username'));
            //如果登录名保存在cookies中
            if($cookieStore.get("username")){
              //cookies中取用户名赋给用户名文本框
              $scope.user.username = $cookieStore.get("username");
              $('#password').focus();
              $('#remember').attr('checked',true);
            }else{
              angular.element('#username').focus();
            }  
        }); 

        //刷新验证码图片
        $scope.captchaImgClick = function(){
          $scope.captchaImg = ctx + '/kaptcha.jpg?now=' + Math.floor(Math.random() * 100);
        }

        //切换背景图片
        $scope.choose_bg = function(){
        	var bg = Math.floor(Math.random() * 9 + 1);
        	angular.element('body').css('background-image', 'url('+ctx+'/static/lib/images/loginbg_0'+ bg +'.jpg)');
        	
        }


        //登录方法
        $scope.login = function(error){
           var user = {
        	    captcha: $scope.user.captcha,
              password: $scope.user.password,
              username: $scope.user.username,
              remember: $scope.user.remember
          };

          console.log("remember::"+user.remember);
          
          //如果通过验证
          if(loginService.check(user)){
            $("#login_ok").attr("disabled", true).val('登陆中..');
            loginService.login(user,function(data){
              // data = {
              //   ret:0,
              //   msg:"用户名或密码错误！"
              // }
              //console.log(data);
              //登录失败显示提示信息
              if(data!="" && data.ret == 1){
                  $("#login_ok").attr("disabled", false).val('登陆');
                  $scope.msgShow = true;
                  $scope.msg = data.msg;
                  //错误后刷新验证码
                  $scope.captchaImgClick();
              }else if(data !="" && data.ret == 0){
                //登录成功后跳转到首页
                $window.location.href = ctx + "/main";
              }
            
            },function(){
                  $scope.msg = "服务器暂无响应，请联系管理员！";
            });
          }

          //如果通过验证
          


          

          // //获取验证信息
          // var message = loginService.check(user);
          // if(message != "" && message != undefined){
          //   $scope.msg = message; 
          //   $scope.msgShow = true;
          // }else{
          //   $scope.msgShow = false;
            // loginService.login(user,function(data){
            //   // data = {
            //   //   ret:1,
            //   //   msg:"用户名或密码错误！"
            //   // }
            //   console.log(data);
            //   //登录失败显示提示信息
            //   if(data!="" && data.ret == 1){
            //       $scope.msgShow = true;
            //       $scope.msg = data.msg;
            //       //错误后刷新验证码
            //       $scope.captchaImgClick();
            //   }else if(data !="" && data.ret == 0){
            //     //登录成功后跳转到首页
            //     $window.location.href = ctx + "/admin/main";
            //   }
              
            // },function(){
            //       $scope.msg = "服务器暂无响应，请联系管理员！";
            // });
          //}
       }

    }]);


