


var myApp = angular.module('app', ['app.bodyCtrl','app.wstnCtrl','app.navCtrl','app.treeCtrl','app.droneCtrl','app.analyticsCtrl','app.smbCtrl','app.trCtrl','app.dashboardCtrl','app.loginCtrl','app.emCtrl', 'app.invCtrl','app.routes', 'app.services', 'app.directives','angularjs-datetime-picker']);

myApp.run(function($rootScope, $timeout,$http,$window) {

var url = 'https://7dacf889-0c4b-4f09-9a2a-0dcd042ee186.predix-uaa.run.aws-usw02-pr.ice.predix.io/oauth/authorize?response_type=code&client_id=acsbootstrap&uaaUrl=https://7dacf889-0c4b-4f09-9a2a-0dcd042ee186.predix-uaa.run.aws-usw02-pr.ice.predix.io&redirect_uri=http://localhost:9092/auth/user/validate';

var isUserLoggedIn = 'http://localhost:9092/auth/user/userIsLoggedIn';
	
        var userLoggedInFlag = 'false';
        $http.get(isUserLoggedIn)
                .success(function(data) {
                	userLoggedInFlag = JSON.stringify(data);
                  console.log("status "+data);
                  if(userLoggedInFlag == "false"){
                    console.log("hi");
                  	//$window.location.href = url;
                  }
        })
                .error(function(data) {
                	if(userLoggedInFlag === 'false'){
                    	//$window.location.href = url;
                    }
                    console.log('Error: ' + data);
        }); 

        
		

  });
