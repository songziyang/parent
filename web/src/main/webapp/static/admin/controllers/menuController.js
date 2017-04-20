/**
 * Created by 刘驰 on 14-5-8.
 */
angular.module('admin.menu', [])
    .controller('menuCtrl',['$scope','$window','$rootScope','$http','$location','menuService',
      function($scope,$window,$rootScope,$http,$location,loginService){
    	
        //表单用户对象
        $scope.menus = [
        		{
        			title:'系统管理',
        			icon:'icon-list',
        			submenus:[
        				{
        					title:'用户管理',
        					url:ctx+'/admin/menu1-1'
        				},
        				{
        					title:'权限管理',
        					url:ctx+'/admin/role/'
        				},
        				{
        					title:'部门管理',
        					url:ctx+'/admin/menu1-2'
        				}
        			]        			
        		},{
        			title:'表单',
        			icon:'icon-edit',
        			submenus:[
        				{
        					title:'表单组件',
        					url:ctx+'/admin/menu2-1'
        				},
        				{
        					title:'向导提示 &amp; 验证',
        					url:ctx+'/admin/menu2-2'
        				},
        				{
        					title:'编辑器',
        					url:ctx+'/admin/menu2-3'
        				},
        				{
        					title:'文件上传',
        					url:ctx+'/admin/menu2-4'
        				}
        			]
        		}
        	];
       
        $scope.test = function(){
        	console.log('------admin menu------'+$scope.menus.length);
        }
    }]);


