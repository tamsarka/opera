var myApp = angular.module('app', ['app.bodyCtrl','app.wstnCtrl','app.navCtrl','app.treeCtrl','app.droneCtrl','app.analyticsCtrl','app.smbCtrl','app.trCtrl','app.dashboardCtrl', 'app.ticketDtlsCtrl' ,'app.loginCtrl','app.emCtrl', 'app.invCtrl','app.routes', 'app.services', 'app.directives','angularjs-datetime-picker', 'ngTable', 'ngResource']);
// myApp.config(['$httpProvider', function($httpProvider) {
//   $httpProvider.defaults.withCredentials = true;
// }])
myApp.run(function($rootScope, $timeout,$http,$window) {  });

myApp.filter('capitalize', function() {
    return function(input) {
      return (!!input) ? input.charAt(0).toUpperCase() + input.substr(1).toLowerCase() : '';
    }
});