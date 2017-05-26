angular.module('app.ticketDtlsCtrl', ['ui.router', 'ngTable'])

/*.controller('dashboardCtrl',['NgTableParams','$http','$scope','$rootScope','$interval','$state','$window','getData',function(NgTableParams,$http,$scope,$rootScope,$interval,$state,$window,getData) {*/
.controller('ticketDtlsCtrl',['$http','$scope','$rootScope','$interval','$state','$window','getData','NgTableParams', '$stateParams',
	function($http,$scope,$rootScope,$interval,$state,$window, getData, NgTableParams, $stateParams) {
	$rootScope.analytics_flag=false;
	if($rootScope.flag==true)
	{
	  $window.location.reload();
	  $rootScope.flag=false;
	}

	$scope.setTab = function (tabId) {
        $rootScope.tab1 = tabId;
        $scope.tab = tabId;

        if(tabId == 1){

        };
	};

	$scope.isSet = function (tabId) {
      	if ($rootScope.tab1 === tabId) {
          return true;
      	}
  	};

  	$scope.getTicketDetails = function(){
  		getData.ticketDetail($stateParams.id).then(function(res){   
	    	console.clear();
	      	if(res.status == '200'){ 
	        	$scope.ticket = res.data;
	      	}else{     
	        	console.log("Error in Ticket Data");
	      	}
	    });
  	};

  	$scope.getComment = function(){
  		getData.getCommentByIncidentId($stateParams.id).then(function(res){
  			if(res.status== '200'){
  				$scope.ticketComment = res.data;
  			}else{
  				console.log("Error in Ticket Comment");
  			}
  		});
  	};

  	$scope.sendComment = function(data){
  		var ticketNewComment = {};
  		ticketNewComment.logid = 0;
  		ticketNewComment.commenteByUser = "user";
  		ticketNewComment.commentDesc = data.newComment;
  		ticketNewComment.lastupdatedDate = new Date();
  		ticketNewComment.ticketincidentid = data.incidentID;
  		getData.crateComment(ticketNewComment).then(function(res){
			if(res.status == '200'){
				$scope.ticket.newComment = "";
				$scope.getComment();
	  		}else{

	  		};
  		});
  	};	

}])
