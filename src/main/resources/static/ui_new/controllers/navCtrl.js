angular.module('app.navCtrl', ['ui.router', 'ngTable'])

.controller('navCtrl',  function($http,$scope,$rootScope,$interval,$state) {
  //$rootScope.SMB="SMB";
$rootScope.username=localStorage.getItem("username");
$rootScope.dashboard=function(){
  $state.go('dashboard');
}
$scope.emd=function(){
  $state.go('em')
}
 $scope.query = {}
    $scope.queryBy = '$'
    $scope.employees = [
      {
        "name" : "L&T Solar Plants",
        "company" : "Syntel India Pvt. Ltd",
        "designation" : "Associate"
      },
      {
        "name" : "WSTN",
        "company" : "Britanica Software Ltd.",
        "designation" : "Software Engineer"
      }
    ];
    $scope.orderProp="name"; 
$scope.smb=function(sno){
//  if($rootScope.username=='admin'){
  //alert(sno)
    $rootScope.SMB=sno;
  $state.go('smb');
  //$rootScope.smbName(sno);
  //$scope.alert;
//}
// else{
// $state.go('dashboard');
// }
}

})
