angular.module('app.trCtrl', ['ui.router', 'ngTable'])


.controller('trCtrl',  function($http,$scope,$rootScope,$interval,$state) {
$interval.cancel($rootScope.dashboardInterval);

})
