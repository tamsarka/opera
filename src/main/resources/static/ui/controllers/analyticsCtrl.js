angular.module('app.analyticsCtrl', ['ui.router', 'ngTable'])


.controller('analyticsCtrl',  function($http,$scope,$rootScope,$interval,$state,getData) {

 document.getElementById('colorTree').style.color="#21295C";
 //document.getElementById('colorTree').style.backgroundColor="#ccd6eb";
 var button = document.createElement('button');
  button.innerHTML = 'x';

  button.style.cssText = 'top: 46px;position: absolute;margin-left: 211px; pointer-events: visible;height: auto;';

  button.onclick = function(){
     $rootScope.analytics_flag=false;
      document.getElementById('colorTree').style.color="#065A82";
      // document.getElementById('colorTree').style.backgroundColor="#ffffff";
       document.body.removeChild(button);
  };
  // where do we want to have the button to appear?
  // you can append it to another element just by doing something like
  // document.getElementById('foobutton').appendChild(button);
  document.body.appendChild(button);
 $scope.SeriesCount=0;
 $scope.resultArr=[];
 $scope.seriesArr=[];
  $scope.chart;
   $scope.options= {
             chart: {
            zoomType: 'xy'
        },
        title: {
            text: ''
        },
        
        xAxis: [{
            type: 'datetime'
        }],
        yAxis: [{ // Primary yAxis
            
            title: {
                text: "",
                style: {
                    color: Highcharts.getOptions().colors[2]
                }
            },
            opposite: true

        }, { // Secondary yAxis
            gridLineWidth: 0,
            title: {
                text: "",
                style: {
                    color: Highcharts.getOptions().colors[0]
                }
            },
            labels: {
                format: '{value} W',
                style: {
                    color: Highcharts.getOptions().colors[0]
                }
            }

        }, { // Tertiary yAxis
            gridLineWidth: 0,
            title: {
                text: "",
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            },
            labels: {
                format: '{value} Amp',
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            },
            opposite: true
        },{ // Tertiary yAxis
            gridLineWidth: 0,
            title: {
                text: "yAxisTitle",
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            },
            labels: {
                format: '{value} Amps',
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            },
            opposite: true
        },
        { // Tertiary yAxis
            gridLineWidth: 0,
            title: {
                text: "yAxisTitle",
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            },
            labels: {
                format: '{value} Amps',
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            },
            opposite: true
        }],
        tooltip: {
            shared: true
        },
        legend: {
            layout: 'horizontal',
            align: 'left',
            x: 5,
            verticalAlign: 'bottom',
            y: 25,
            floating: true,
            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
        },

            series: []
        };
  
  $scope.getHistory = function(tags,token,key) {
      var d = new Date();
      var start = new Date(d.getFullYear(),d.getMonth(),d.getDate(),7,30,0,0).getMilliseconds();
      var end = d.getMilliseconds();
      var emData = {"start": "1y-ago","tags": [   {    "name": tags,     "order": "desc",     "limit": 300     }  ] };;

      console.log("Count"+$scope.SeriesCount);
      console.log("getData"+JSON.stringify(getData.PST(tags)));
      getData.getValue(tags,token,getData.PST(tags))
      .then(function(res) {

      $scope.resultArr.push(res.res.tags[0].results[0].values);

      $scope.chart = $('#multi-axis-line').highcharts($scope.options).highcharts();
      $scope.seriesArr.push({name: 'Series '+ $scope.SeriesCount,data:  $scope.resultArr[ $scope.SeriesCount]});
      console.log($scope.seriesArr);

      /*$scope.chart.addSeries(
         $scope.seriesArr[$scope.SeriesCount]
      ,false);*/
       var axisNum = $scope.SeriesCount;
      axisNum = (axisNum < 5 ) ?  axisNum : 0
      $scope.drawChart($scope.resultArr[$scope.SeriesCount],key,axisNum,$scope.SeriesCount);
      
      
      //$scope.chart.redraw();
      $scope.SeriesCount++;
      localStorage.setItem("resultArr", $rootScope.resultArr);
      $scope.smbInvData =[{ type: 'line', name: "Voltage", data: $scope.resultArr}];
      $scope.smbInvData.push({ type: 'line', name: "Power", data: $scope.resultArr});
      //id,data,title,yAxisTitle

      //lineChart("smb-line-chart-multi",$scope.smbInvData,key,"Current(Amp), Voltage(KV)");

  });

};
  $rootScope.openNav =function() {
    document.getElementById("mySidenav").style.width = "19%";
    $rootScope.analytics_flag=true;
    //document.getElementById('bs-example-navbar-collapse-1').appendChild(document.getElementById("mySidenav"));
}

$scope.drawChart = function (data, name,axis,color) {
    // 'series' is an array of objects with keys: 'name' (string) and 'data' (array)
    var newSeriesData = {
        name: name,
        data: data,
         yAxis: axis,

         color: Highcharts.getOptions().colors[color]
             
    };

    // Add the new data to the series array
    $scope.options.series.push(newSeriesData);

    // If you want to remove old series data, you can do that here too

    // Render the chart
     $scope.chart = $('#multi-axis-line').highcharts($scope.options).highcharts();
      $scope.loading = false;
};
$rootScope.closeNav=function() {
  $rootScope.analytics_flag = false;
  console.log("inside close"+$rootScope.analytics_flag);
    document.getElementById("mySidenav").style.width = "0%";

}


$scope.roleListNew1 =function(){
  $http({
  method: 'GET',
  url: 'https://localhost:9092/services/group/nav'
}).then(function successCallback(response) {
  //console.log("backend1");
  
   $scope.roles=JSON.stringify(response.data[0].children[0].children[3].children);
   console.log($scope.roles+"********");
   $scope.roleList1=JSON.parse($scope.roles);
 //$scope.roleList = $rootScope.roles;
  
    // this callback will be called asynchronously
    // when the response is availableget
  }, function errorCallback(response) {
    // called asynchronously if an error occurs
    // or server returns response with an error status.f
  });
}
   $scope.roleListNew1();
$rootScope.getParameters =function(res){
  console.log("res: "+res);
   $scope.token=localStorage.getItem("token");
  $http({
  method: 'GET',
  url: 'https://predix-asset.run.aws-usw02-pr.ice.predix.io/asset?filter=assetId='+res+'&fields=parameters',
   headers: {
                    'Authorization' : 'Bearer '+ $scope.token,
                    'Content-Type': 'application/json',
                    'Predix-Zone-Id': 'baf48b22-96ca-480e-8051-0137e964c56f'
                }
}).then(function successCallback(response) {
  //console.log(response.data.frequencyBlock[0].keyValue);
  // $scope.parameters=JSON.stringify(response.data);

    var ip=JSON.parse(JSON.stringify(response.data[0].parameters));
   /* var options=[];
    var value1=[];
    var key1=[];
    console.log("Grid data: "+ ip.length());
    //----
    for(var i=0; i< ip.length; i++)
    {
      console.log('ip values ::: ' + ip[i][0] + ip[i][1]);
    }
    //----
    for (key in ip) {
        if (ip.hasOwnProperty(key)) {

        keyvalue=key;
        var len = ip[key].length;
        var value = ip[key].substring(11,len);
        options.push({label:key,tagName:value});
        
        value1.push(value);
        key1.push(key);
        console.log("Value: "+ value1 + "Key: "+ key1);
        var b = document.createElement('button');

        b.innerHTML = key;
        b.style.cssText = ' background-color: #086e87;border: none;color: white; text-align: center;text-decoration: none;ddisplay: inline-block; font-size: 16px;margin: 4px 2px; cursor: pointer;width:auto;height:auto;padding:10px;';

        var wrapper = document.getElementById("asset");
        wrapper.appendChild(b);*/
      var keys = Object.keys(ip);
      clearBox();
      for (var i = 0; i < keys.length; i++) {
        var len = ip[keys[i]].length;
        var val = ip[keys[i]].substring(11,len);
    
        var btn = document.createElement('button');
        btn.style.cssText = ' background-color: #4c5152;border: none;color: white; text-align: center;text-decoration: none;ddisplay: inline-block; font-size: 12px;margin: 4px 2px; cursor: pointer;width:auto;height:auto;padding:10px;';
       
        btn.innerHTML = keys[i];
        btn.addEventListener('click',(function(x,y){
          return function(){
             $scope.loading = true;
            $scope.getHistory(x,$scope.token,res+y);
            $scope.ans= localStorage.getItem('resultArr');       
          } 
        })(val,keys[i])
        
        );

        document.getElementById('asset').appendChild(btn);

      }

     function clearBox(){
       document.getElementById('asset').innerHTML = "";
    }
     /*$scope.VnCsmb = function(start,end){
        $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.Voltage",$scope.token,start,end);
        $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR1.Current",$scope.token,start,end);
        $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR2.Current",$scope.token,start,end);
        $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR3.Current",$scope.token,start,end);
        $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR4.Current",$scope.token,start,end);
        $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR5.Current",$scope.token,start,end);
        $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR6.Current",$scope.token,start,end);
        $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR7.Current",$scope.token,start,end);
        $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR8.Current",$scope.token,start,end);
        //alert("da")

      }*/
        /*for(var l=0;l<value1.length;l++){
          $scope.getHistory(value1[l],$scope.token);
        }*/
      function getEventTarget(e) {
              e = e || window.event;
              return e.target || e.srcElement; 
          }
     // }
   /*   $scope.getTagName = function(){
          (function(x){
             return function(){
                console.log(x);
             }
          })(value)
      }*/

      
   // });

 //$scope.roleList = $rootScope.roles;
  
    // this callback will be called asynchronously
    // when the response is available
  }, function errorCallback(response) {
    // called asynchronously if an error occurs
    // or server returns response with an error status.
  });
}
  
  
})
