/**
 * Created by 刘驰 on 14-5-8.
 * 登录模块service 
 */
angular.module('admin.login')
    .service('loginService', ['$http','$cookieStore',function($http,$cookieStore) {
	
    var loginService ={};
    /**
     * 检查input数据项
     * @param user 注册用户
     * @returns {string} 出错信息
     */
    loginService.check = function(user){
        if(typeof(user.username)=="undefined" || user.username == ""){
            $("#username").attr('title','请输入用户名').tooltip('show');
            return false;
        }
        if(typeof(user.password)=="undefined" || user.password == ""){
            $("#password").attr('title','请输入密码').tooltip('show');
            return false;
        }
        if(typeof(user.captcha)=="undefined" || user.captcha == ""){
            $("#captcha").attr('title','请输入验证码').tooltip('show');
            return false;
        }
        if(user.remember){
            $cookieStore.put('username',user.username);
        }else{
            $cookieStore.remove('username');
        }
        return true;
    };

    loginService.setCookie = function(){
        var $remember = $("#remember");
//      if ($remember.attr('checked')) {
//      $.cookie(COOKIE_NAME, $("#j_username").val(), { path: '/', expires: 15 });
//      } else {
//          $.cookie(COOKIE_NAME, null, { path: '/' });  //删除cookie
//      }
    }
    /**
     * 注册用户提交
     * @param user 注册用户
     * @param callback  回调方法
     * @returns {*|Error}
     */
    loginService.login = function(user,successCallback,errorCallback){
       return $http.post(ctx+'/login',user)
            .success(successCallback)
            .error(errorCallback);
    };
    return loginService;
}]);

