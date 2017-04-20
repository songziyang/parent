/**
 * Created by 刘驰 on 14-5-8.
 */
angular.module('admin.menu')
	//验证码图片点击刷新
    .directive('menu', ['menuService',function(menuService) {
      return {
        restrict: 'E',
        replace: true,
        scope: {
       		menus: '='
        },    
        template:'<ul  class="nav nav-list">'+
        		 	'<li ng-repeat="m in menus" ng-class="{active:$index==0} || {open:$index==0}">'+
        		 		'<a href="#" class="dropdown-toggle"> '+
        		 			'<i class="{{m.icon}}"></i>'+
        		 			'<span class="menu-text"> {{m.title}} </span> '+
        		 			'<b class="arrow icon-angle-down"></b>'+
        		 		'</a>'+
        		 		'<ul class="submenu">'+
        		 			'<li ng-repeat="m in m.submenus" >'+
        		 				'<a ng-href="{{m.url}}">'+
        		 					'<i class="icon-double-angle-right"></i>{{m.title}}'+
        		 				'</a>'+
        		 			'</li>'+
        		 		'</ul>'+
        		 	'</li>'+
        		'</ul>',	

        link: function(scope, element, attrs) {
               console.log("-----link-----");
        },
      }
    }]);


