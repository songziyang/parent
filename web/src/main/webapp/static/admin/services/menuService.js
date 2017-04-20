/**
 * Created by 刘驰 on 14-5-8.
 * 登录模块service
 */
angular.module('admin.menu')
    .service('menuService', ['$http',function($http) {
	
    var menuService ={};

    /**
     * 获取登录用户权限菜单
     * @param successCallback  正确回调方法
     * @param errorCallback  错误回调方法
     * @returns {*|Error}
     */
    menuService.loadMenu = function(user,successCallback,errorCallback){
       return $http.post(ctx+'/admin/login',user)
            .success(successCallback)
            .error(errorCallback);
    };
   
    return menuService;
}]);

