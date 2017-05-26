angular.module('app.treeCtrl', ['ui.router', 'ngTable'])


.controller("TreeController", ["TreeService",'$scope','$rootScope','$http' ,function (TreeService,$rootScope,$scope,$http) {
 $scope.token= localStorage.getItem('token');
$scope.roleListNew =function(){
  $http({
  method: 'GET',
  url: 'https://test-orch-solar1.run.aws-usw02-pr.ice.predix.io/services/group/nav'
  
}).then(function successCallback(response) {
  console.log("backend1");
  //console.log(response.data.frequencyBlock[0].keyValue);

   $scope.roles=JSON.stringify(response.data);
   $scope.roleList=JSON.parse($scope.roles);
 //$scope.roleList = $rootScope.roles;
   // console.log("dataaaa  "+ JSON.stringify( $scope.roleList));
    // this callback will be called asynchronously
    // when the response is availableget
  }, function errorCallback(response) {
    // called asynchronously if an error occurs
    // or server returns response with an error status.
  });
}
   $scope.roleListNew();
   
        var tc = this;
        buildTree();
        function buildTree() {
            TreeService.getTree().then(function (result) {
                tc.tree = result.data;
            }, 
            function (result) {
                alert(result);
            })
        };
        
    }])
