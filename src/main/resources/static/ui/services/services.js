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
.factory('getData', function($http, $log, $q) {
  //debugger;
  return {
   getValue: function(tag,token,dataB) {
     var deferred = $q.defer();
     //console.log(dataB);
     var tagName = dataB.tags[0].name;
     var startTime =  dataB.start;
     var order = dataB.tags[0].order;
     var limit = 0; 
     if(undefined !== dataB.tags[0].limit){
    	 limit = dataB.tags[0].limit;
     }
     var httpURL = "/services/solar/tag_data/"+tagName+"?starttime="+startTime;
     if(limit > 0){
    	 httpURL = httpURL + "&taglimit="+limit;
     }
     if(order !== 'desc'){
    	 httpURL = httpURL + "&order="+order;
     }
     
     $http({
       /* headers: {
            'Access-Control-Allow-Origin': '*',
            'Content-Type': 'application/json',
            'Predix-Zone-Id' : 'df9cb35c-d735-4306-98b3-160994169c88',
            'authorization' : 'Bearer ' + token
        },*/
        /*data:dataB,*/
        method: 'GET',
        url: httpURL
        //   url: 'https://time-series-store-predix.run.aws-usw02-pr.ice.predix.io/v1/datapoints'
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
   }
  }


 })


.service('BlankService', [function(){

}]);