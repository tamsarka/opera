angular.module('app.services', [])

.factory('BlankFactory', [function(){

}])
.service("TreeService", ["$http", "URLConfig", function ($http, URLConfig) {
        this.getTree = function () {
            return $http.get(URLConfig.tree);
        };
    }])
    .factory("URLConfig", [function () {
        return {
            tree: "ui/content/json/tree.json"
        }
    }])
.factory('getData',function($http, $log, $q, $resource, $location) {
  //debugger;
  return {
   getValue: function(tag,token,dataB) {
     var deferred = $q.defer();
     $http({
        headers: {
            'Access-Control-Allow-Origin': 'https://time-series-store-predix.run.aws-usw02-pr.ice.predix.io',
            'Content-Type': 'application/json',
            'Predix-Zone-Id' : 'df9cb35c-d735-4306-98b3-160994169c88',
           // 'authorization' : 'Bearer ' + token
             'authorization' : 'Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6ImxlZ2FjeS10b2tlbi1rZXkiLCJ0eXAiOiJKV1QifQ.eyJqdGkiOiJlNTE0MGQxZjg3NWI0MWI5YmUwODZjNjU3MWY5MzgzOSIsInN1YiI6ImJmZmFmZDJmLWVmZmItNDc0Yy1hZTY0LThiNDk1MjlmOTVkNiIsInNjb3BlIjpbInRpbWVzZXJpZXMuem9uZXMuNTNhNTdjMGUtMWUxZS00NTFjLWFmMTktYmZjMWQzZDQwNzk2LnVzZXIiLCJwcmVkaXgtYXNzZXQuem9uZXMuNjlmNTQ0YTItNDRiOC00ZDFkLWJkOTQtYjRlOGQwZDcwOWY1LnVzZXIiLCJ2aWV3cy5hZG1pbi51c2VyIiwidGltZXNlcmllcy56b25lcy5lOWFjYTM0Ny04NmZhLTQzNDItYTMyNy1mOTk2M2UwMGMyMjkucXVlcnkiLCJzY2ltLnVzZXJpZHMiLCJwcmVkaXgtYXNzZXQuem9uZXMuYmFmNDhiMjItOTZjYS00ODBlLTgwNTEtMDEzN2U5NjRjNTZmLnVzZXIiLCJvcGVuaWQiLCJ0aW1lc2VyaWVzLnpvbmVzLjUzYTU3YzBlLTFlMWUtNDUxYy1hZjE5LWJmYzFkM2Q0MDc5Ni5xdWVyeSIsInRpbWVzZXJpZXMuem9uZXMuZGY5Y2IzNWMtZDczNS00MzA2LTk4YjMtMTYwOTk0MTY5Yzg4LnVzZXIiLCJ0aW1lc2VyaWVzLnpvbmVzLmU5YWNhMzQ3LTg2ZmEtNDM0Mi1hMzI3LWY5OTYzZTAwYzIyOS51c2VyIiwidWFhLnVzZXIiLCJwYXNzd29yZC53cml0ZSIsInZpZXdzLnpvbmVzLmNkMjYyMDVkLTI5NjgtNDdjMy1hYWY0LTFkNGM1ZTIwOWM3YS51c2VyIiwidGltZXNlcmllcy56b25lcy5kZjljYjM1Yy1kNzM1LTQzMDYtOThiMy0xNjA5OTQxNjljODgucXVlcnkiXSwiY2xpZW50X2lkIjoiYWNzYm9vdHN0cmFwIiwiY2lkIjoiYWNzYm9vdHN0cmFwIiwiYXpwIjoiYWNzYm9vdHN0cmFwIiwiZ3JhbnRfdHlwZSI6InBhc3N3b3JkIiwidXNlcl9pZCI6ImJmZmFmZDJmLWVmZmItNDc0Yy1hZTY0LThiNDk1MjlmOTVkNiIsIm9yaWdpbiI6InVhYSIsInVzZXJfbmFtZSI6ImFkbWluIiwiZW1haWwiOiJhZG1pbkBsbnRpbmZvdGVjaC5jb20iLCJhdXRoX3RpbWUiOjE0ODkwNjE3MDMsInJldl9zaWciOiIzZGFkYTg3YiIsImlhdCI6MTQ4OTA2MTcwMywiZXhwIjoxNjg5MDYxNzAzLCJpc3MiOiJodHRwczovLzdkYWNmODg5LTBjNGItNGYwOS05YTJhLTBkY2QwNDJlZTE4Ni5wcmVkaXgtdWFhLnJ1bi5hd3MtdXN3MDItcHIuaWNlLnByZWRpeC5pby9vYXV0aC90b2tlbiIsInppZCI6IjdkYWNmODg5LTBjNGItNGYwOS05YTJhLTBkY2QwNDJlZTE4NiIsImF1ZCI6WyJzY2ltIiwib3BlbmlkIiwidmlld3Muem9uZXMuY2QyNjIwNWQtMjk2OC00N2MzLWFhZjQtMWQ0YzVlMjA5YzdhIiwidmlld3MuYWRtaW4iLCJ0aW1lc2VyaWVzLnpvbmVzLjUzYTU3YzBlLTFlMWUtNDUxYy1hZjE5LWJmYzFkM2Q0MDc5NiIsInBhc3N3b3JkIiwidGltZXNlcmllcy56b25lcy5kZjljYjM1Yy1kNzM1LTQzMDYtOThiMy0xNjA5OTQxNjljODgiLCJ1YWEiLCJwcmVkaXgtYXNzZXQuem9uZXMuYmFmNDhiMjItOTZjYS00ODBlLTgwNTEtMDEzN2U5NjRjNTZmIiwicHJlZGl4LWFzc2V0LnpvbmVzLjY5ZjU0NGEyLTQ0YjgtNGQxZC1iZDk0LWI0ZThkMGQ3MDlmNSIsInRpbWVzZXJpZXMuem9uZXMuZTlhY2EzNDctODZmYS00MzQyLWEzMjctZjk5NjNlMDBjMjI5IiwiYWNzYm9vdHN0cmFwIl19.LwvcefQI18G9U47symhxwTAQGNR5Q2uGYDbE8FJdqTiovYB0gJQftHlg104Avk92HPnlV9NJNgr3vBCa5UzrAxIyjif1qhy_-eo5WpX71_99cQR6k8R0gbbVP853b9fCVQSXfJqi6xlqX7Ct4ePIDtkn4uDWAMVJu6TcwJ-ko6Xs-WOgaay8XEf6wOmNnhC3xLGL_xHEB4v8fL_S4OPcx-xFBe7Vdduhqe6VHGNRV7shhE7fiV1uZFaadZP9p3pqeu1Gv7nM5_0Bw9V6aaLmCLpdDlgZbIUnz7nKaZ2H9IPxubFGHpfGZOnkoYjdXdRTkPIPx4M3nJjdvvB7VzG78A'
        },
        data:dataB,
        method: 'POST',

        url: 'https://time-series-store-predix.run.aws-usw02-pr.ice.predix.io/v1/datapoints'
     }).success(function(res) {
      // debugger;
          deferred.resolve({
             res
             });
       }).error(function(msg) {
        // debugger;
          deferred.reject(msg);
          $log.error(msg);
       });
     return deferred.promise;
   },
   PST : function(tags,start,end){

     var currentTime = new Date().getTime()-8*3600*1000;
     if(start==undefined||start==null||start.length==0||start==""){
      // start =new Date().getTime()-8*3600*1000-18*60*1000;
       start ='40d-ago';
     }
     else{
       //console.log(start);

       start=new Date(start).getTime()-8*3600*1000;
       if (start >currentTime ) start=currentTime;
       //console.log(start);
     }
     if(end==undefined||end==null||end.length==0||end==""){

       end = new Date().getTime()-8*3600*1000;
     }
     else{
        end = new Date(end).getTime()-8*3600*1000;
        if(end >currentTime ) end=currentTime;
     }
     if(start>end){
       temp = start;
       start =end;
       end=temp;

     }
    // alert(start); alert(end);
     var d = new Date().getTime()-8*3600*1000;
     var timeBound ={ "cache_time": 0, "tags": [   {  "name": tags,  "order": "desc"  } ],
     "start": start ,
     "end": end
   }
     return timeBound;
   },
   PSTD : function(tags,duration){
     var d = new Date().getTime()-8*3600*1000;
     var timeBound ={ "cache_time": 0, "tags": [   {  "name": tags,  "order": "desc"  } ],
    // "start": d-duration ,
    "start":'28d-ago',
     "end": d
   }
     return timeBound;
   },
   getTableContent : function () {
	    var url = $location.protocol()+"://"+ $location.host()+":"+$location.port()+"/api/getAllAssetList";
      console.clear();
        return $http.get(url, {headers : {'Content-Type':'text/plain'}}).success(function (response) {
            return response;
        }).error(function (err, status) {
            console.log("error in get", err , status);
        });
    },
    getOpenTicket : function () {
    	var url = $location.protocol()+"://"+ $location.host()+":"+$location.port()+"/api/openTicketList";
        console.clear();
        return $http.get(url, {headers : {'Content-Type':'text/plain'}}).success(function (response) {
            return response;
        }).error(function (err, status) {
            console.log("error in get", err , status);
        });
    },
    updateAcknowledge : function (data) {
        var obj = {} 
        obj.assetId = data.assetId;
        obj.ackFlag  = true;
        
        var url = $location.protocol()+"://"+ $location.host()+":"+$location.port()+"/"+ obj.assetId +"/"+ obj.ackFlag;
        console.clear();
        return $http.post(url,{headers : {'Content-Type':'text/plain'}}).success(function (response) {
            return response;
        }).error(function (err, status) {
            console.log("error in UPDATE", err , status);
        });
    },
    createTicket : function (data) {
    	var url = $location.protocol()+"://"+ $location.host()+":"+$location.port()+"/api/createticket";
       // console.clear();
       // console.log('data:' , data);
        return $http.post(url, data, {headers : {'Content-Type':'application/json'}}).success(function (response) {
            console.log("in post function", response);

        }).error(function (err, status) {
            console.log("in post function", err , status);
        });
    },/* crateComment : function(data){
    	var url = $location.protocol()+"://"+ $location.host()+":"+$location.port()+"/createcomment";
      return $http.post(url, data,{headers : {'Content-Type':'application/json'}}).success(function (response) {
          console.log("in post function", response);
          return response;  
      }).error(function (err, status) {
          console.log("in post function", err , status);*/
    assetStatusUpdate : function (data) {
    	var url = $location.protocol()+"://"+ $location.host()+":"+$location.port()+"/api/assetStatusUpdate";
        console.clear();
        console.log('data:' , data);
        return $http.post(url, data, {headers : {'Content-Type':'application/json'}}).success(function (response) {
            console.log("in post function", response);

        }).error(function (err, status) {
            console.log("in post function", err , status);
        });
     },
    ticketDetail : function (id) {
    	var url = $location.protocol()+"://"+ $location.host()+":"+$location.port()+"/api/"+id;
        return $http.get(url,{headers : {'Content-Type':'application/json'}}).success(function (response) {
            console.log("in get success function", response);
            return response;
        }).error(function (err, status) {
            console.log("in get err function", err , status);
        });
    },
    sonnzeUpdate : function (data) {
      data.data.snooze = data.hrs + "-" + data.mins + "-" + data.secs;
      var url = $location.protocol()+"://"+ $location.host()+":"+$location.port()+"/api/UPDATE_SNOOZE_BY_ID/"+ data.data.assetId + "/" + data.data.snooze;
      console.clear();
      console.log('data:' , data);
      return $http.post(url,{headers : {'Content-Type':'application/json'}}).success(function (response) {
          console.log("in post function", response);
          return response;  
      }).error(function (err, status) {
          console.log("in post function", err , status);
      });
    },
    getCommentByIncidentId: function(data){
    	var url = $location.protocol()+"://"+ $location.host()+":"+$location.port()+"/api/comment/"+ data;
      return $http.get(url,{headers: {'Content-Type': 'application/json'}}).success(function(response){
        console.log("in post function", response);
        return response;  
      }).error(function(err, status){
        console.log("in post function", err , status);
      });
    },
    crateComment : function(data){
    	var url = $location.protocol()+"://"+ $location.host()+":"+$location.port()+"/createcomment";
      return $http.post(url, data,{headers : {'Content-Type':'application/json'}}).success(function (response) {
          console.log("in post function", response);
          return response;  
      }).error(function (err, status) {
          console.log("in post function", err , status);
      });
    }
  }
})

// .factory('h2Service', function($http) {
//     return {      
//       createTicket : function (data) {
//             var url="http://localhost:9092/api/createticket";
//             return $http.post(url, data, {headers : {'Content-Type':'application/json'}}).success(function (response) {
//                 console.log("in post function", response);

//             }).error(function (err, status) {
//                 console.log("in post function", err , status);
//             });
//         }
//     }
// })

.service('BlankService', [function(){

}]);
