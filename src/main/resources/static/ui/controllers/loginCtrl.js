angular.module('app.loginCtrl', ['ui.router', 'ngTable'])



.controller('loginCtrl',  function($http,$scope,$rootScope,$interval,$state,$window,$location) {
  

$interval.cancel($rootScope.dashboardInterval);
  $rootScope.img ='images.jpg';
  localStorage.setItem("username","");
  $interval.cancel($rootScope.dashboardInterval);
  $scope.userError="";
  $rootScope.loginHide =  true;
  $scope.userName='';
  $scope.userPassword='';
  // $rootScope.UAAURL='https://7dacf889-0c4b-4f09-9a2a-0dcd042ee186.predix-uaa.run.aws-usw02-pr.ice.predix.io/oauth/authorize?response_code=code&client_id=acsbootstrap&state=?clientId=acsbootstrap&uaaUrl=https://7dacf889-0c4b-4f09-9a2a-0dcd042ee186.predix-uaa.run.aws-usw02-pr.ice.predix.io&redirect_uri=https%3A%2F%2Fpredix-toolkit.run.aws-usw02-pr.ice.predix.io%2F%3FclientId%3Dacsbootstrap%26uaaUrl%3Dhttps%3A%2F%2F7dacf889-0c4b-4f09-9a2a-0dcd042ee186.predix-uaa.run.aws-usw02-pr.ice.predix.io%2F%23!%2FuserAuthcode';
  // $rootScope.TMSURL='https://time-series-store-predix.run.aws-usw02-pr.ice.predix.io/v1/datapoints';
  $rootScope.flag=true;
  $rootScope.token='';

  var urlEncode = function(data) {
    var  pairs = [];
    for (var key in data) {
        if (data.hasOwnProperty(key)) {
            if(typeof data[key] === 'undefined')
                continue;
            pairs.push(encodeURIComponent(key) + "=" + encodeURIComponent(data[key]));
        }
    }
    return pairs.join("&");
};
  $scope.clientCreds = function(){
    console.log("authorize");
    console.log("*************************");
    console.log($location.absUrl());
    console.log($location.absUrl().split('=')[1].split('&')[0]);
    var code=$location.absUrl().split('=')[1].split('&')[0];
    localStorage.setItem("code",code);
    console.log("local storage"+code);
    var absUrl = $location.absUrl();
    console.log(absUrl);
  var Clientdata = {
      // 'client_id':'acsbootstrap'
     /* 'grant_type':'client_credentials'*/
  };

  // $http.get($rootScope.UAAURL+urlEncode(Clientdata), {
      /*headers: {
              'Content-Type': 'application/x-www-form-urlencoded',
              'authorization' : 'Basic YWNzYm9vdHN0cmFwOmFjc2Jvb3RzdHJhcA=='
          },
          data:{
            'client_id':'acsbootstrap',
            'grant_type':'client_credentials'
          }*/
      // })
  // .then(function(response) {
  //     token = response.data.access_token;
  //     $rootScope.token=token;
  //     ////console.log(token);
  //     localStorage.setItem("token", token);
  //     //$state.go('dashboard');
  //   //  $scope.getTrain(token);
  // }, function(response) {
  //     //console.log( response);
  // });

}

  $scope.loginValidator = function (name,password) {

  //  console.log("Inside loginValidator **************************");
 //  localStorage.setItem("username",name);
        /*var userdata = {
            'username': name,
            'password' : password,
            'grant_type' : 'password'
        };*/
       //  $http.get($rootScope.UAAURL, {
       //      headers: {
       //              'Content-Type': 'application/x-www-form-urlencoded',
       //              'authorization' : 'Basic YWNzYm9vdHN0cmFwOmFjc2Jvb3RzdHJhcA=='
       //          }
       //      })
       //  .then(function(response) {
       //    ////debugger;
       //    console.log("response"+JSON.stringify(response));
       //    $scope.userError="authorized";

       // //   $state.go('jnnsm_rajasthan_plant_1mw');
       //    }, function(response) {
       //      //console.log('Responce ' + response);
       //      $scope.userError="Unauthorized";
       //  });


  };

  // Niket

$scope.redirect = function () {
  console.log("!!!!!!!!!!inside redirect function");
  
  // $window.location.href = 'https://7dacf889-0c4b-4f09-9a2a-0dcd042ee186.predix-uaa.run.aws-usw02-pr.ice.predix.io/oauth/authorize?response_code=code&client_id=acsbootstrap&state=?clientId=acsbootstrap&uaaUrl=https://7dacf889-0c4b-4f09-9a2a-0dcd042ee186.predix-uaa.run.aws-usw02-pr.ice.predix.io&redirect_uri=http://localhost:3000/#/login'; 
      var url = "https://7dacf889-0c4b-4f09-9a2a-0dcd042ee186.predix-uaa.run.aws-usw02-pr.ice.predix.io/oauth/authorize?response_type=code&client_id=acsbootstrap&state=?clientId=acsbootstrap&uaaUrl=https://7dacf889-0c4b-4f09-9a2a-0dcd042ee186.predix-uaa.run.aws-usw02-pr.ice.predix.io&redirect_uri=https://test-orch-solar1.run.aws-usw02-pr.ice.predix.io/auth/user/validate'";
     // $http.get(url)
     //            .success(function(data) {
     //              // var code=$location.absUrl().split('=')[1].split('&')[0];
     //              console.log(data);
     //               //$window.location.href='https://7dacf889-0c4b-4f09-9a2a-0dcd042ee186.predix-uaa.run.aws-usw02-pr.ice.predix.io/oauth/authorize?response_type=code&client_id=testclient&state=?clientId=testclient&uaaUrl=https://7dacf889-0c4b-4f09-9a2a-0dcd042ee186.predix-uaa.run.aws-usw02-pr.ice.predix.io&redirect_uri=https%3A%2F%2Fpredix-toolkit.run.aws-usw02-pr.ice.predix.io%2F%3FclientId%3Dtestclient%26uaaUrl%3Dhttps%3A%2F%2F7dacf889-0c4b-4f09-9a2a-0dcd042ee186.predix-uaa.run.aws-usw02-pr.ice.predix.io%2F%23!%2FuserAuthcode'
     //               $window.location.href='https://test-orch-solar1.run.aws-usw02-pr.ice.predix.io/auth/user/validate'
     //            // $scope.clientCreds();
                
                 
     //            })
     //            .error(function(data) {
     //                alert(data);
     //                console.log('Error: ' + data);
     //            });



//$window.location.href = 'https://7dacf889-0c4b-4f09-9a2a-0dcd042ee186.predix-uaa.run.aws-usw02-pr.ice.predix.io/oauth/authorize?response_type=code&client_id=acsbootstrap&state=?clientId=acsbootstrap&uaaUrl=https://7dacf889-0c4b-4f09-9a2a-0dcd042ee186.predix-uaa.run.aws-usw02-pr.ice.predix.io&redirect_uri=https://test-orch-solar1.run.aws-usw02-pr.ice.predix.io/auth/user/validate';
 
//   //var code=$location.absUrl().split('=')[1].split('&')[0];
  console.log("tokennn  "+$location.absUrl());
}
//$scope.clientCreds();


})
