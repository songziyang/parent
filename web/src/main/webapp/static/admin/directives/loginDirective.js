/**
 * Created by 刘驰 on 14-5-8.
 */
	angular.module('admin.login')
// 	//验证码图片点击刷新
//     .directive('captchaImg', ['loginService',function(loginService) {
//       return function(scope, element, attr) {
//          element.bind("click",function(event){
//          	loginService.test();
//          	scope.test();
//          	alert(scope.msg);
//             var path = ctx + "/kaptcha.jpg?now=" + Math.floor(Math.random() * 100);
//             element.attr('src',path);
//           });
//       }
//     }]);
		.directive('loginInput',function($document){
			return {
				restrict : 'A',
				link : function(scope, element, attrs) {
					element.bind("blur",function(event){
       					if(element.val().length < 1){
       						element.css('border-color','#ff0000');	
       					}else{
       						element.css('border-color','#ccc');
       					}
       				});
       				element.bind("focus",function(event){
       					if(element.val().length > 0)
       						element.css('border-color','#66afe9');		
       				});
       				element.bind("keydown",function(event){
       					if(element.val().length > 0){
       						element.tooltip('destroy');
       						element.css('border-color','#66afe9');	
       					}
       				});
				}
			}
   		});


