angular.module('app.smbCtrl', ['ui.router', 'ngTable'])


.controller('smbCtrl',  function($http,$scope,$rootScope,$interval,$state,getData) {
  $interval.cancel($rootScope.dashboardInterval);
  $rootScope.smbName = function(sno){
  //  alert("called",sno);
    $scope.SMB=sno;
  }


  $scope.VnCsmb = function(start,end){
    $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.Voltage",token,start,end);
    $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR1.Current",token,start,end);
    $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR2.Current",token,start,end);
    $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR3.Current",token,start,end);
    $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR4.Current",token,start,end);
    $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR5.Current",token,start,end);
    $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR6.Current",token,start,end);
    $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR7.Current",token,start,end);
    $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR8.Current",token,start,end);
    //alert("da")

  }


  $scope.lineData =[{   type: 'area', name: 'ModuleTemp', data: []},
  {   type: 'area', name: 'Irradiance', data: []} ];
  var token=localStorage.getItem("token");
  var lineChart = function(id,data,data1,data2,data3,data4,data5,data6,data7,data8,title,yAxisTitle){
  
Highcharts.chart('multi-axis-line', {
          chart: {
            zoomType: 'xy'
        },
        title: {
            text: 'SMB Data'
        },
        
        xAxis: [{
            type: 'datetime'
        }],
        yAxis: [{ // Primary yAxis
            
            title: {
                text: title,
                style: {
                    color: Highcharts.getOptions().colors[2]
                }
            },
            opposite: true

        }, { // Secondary yAxis
            gridLineWidth: 0,
            title: {
                text: yAxisTitle,
                style: {
                    color: Highcharts.getOptions().colors[0]
                }
            },
            labels: {
                format: '{value} V',
                style: {
                    color: Highcharts.getOptions().colors[0]
                }
            }

        }, { // Tertiary yAxis
            gridLineWidth: 0,
            title: {
                text: yAxisTitle,
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
        series: [{
            name: 'Voltage',
            type: 'line',
            yAxis: 1,
            data: data,
            tooltip: {
                valueSuffix: ' V'
            }

        }, {
            name: 'Current1',
            type: 'line',
            yAxis: 2,
            data: data1,
            marker: {
                enabled: false
            },
            tooltip: {
                valueSuffix: ' Amps'
            }

        }, {
            name: 'Current2',
            type: 'line',
            data: data2,
            tooltip: {
                valueSuffix: ' Amps'
            }
        },
        {
            name: 'Current3',
            type: 'line',
            data: data3,
            tooltip: {
                valueSuffix: ' Amps'
            }
        },
        {
            name: 'Current4',
            type: 'line',
            data: data4,
            tooltip: {
                valueSuffix: ' Amps'
            }
        },
        {
            name: 'Current5',
            type: 'line',
            data: data5,
            tooltip: {
                valueSuffix: ' Amps'
            }
        },
        {
            name: 'Current6',
            type: 'line',
            data: data6,
            tooltip: {
                valueSuffix: ' Amps'
            }
        },
        {
            name: 'Current7',
            type: 'line',
            data: data7,
            tooltip: {
                valueSuffix: ' Amps'
            }
        },
        {
            name: 'Current8',
            type: 'line',
            data: data8,
            tooltip: {
                valueSuffix: ' Amps'
            }
        }]
    });
  }
  $rootScope.getParameters1 =function(res){
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
var value1=[];
var key1=[];
    var ip=JSON.parse(JSON.stringify(response.data[0].parameters));
    for (key in ip) {
    if (ip.hasOwnProperty(key)) {
        //console.log(key + " = " + ip[key]);
     keyvalue=key;
        var len = ip[key].length;
        var value = ip[key].substring(11,len);
     
          value1.push(value);
          key1.push(key);
function getEventTarget(e) {
        e = e || window.event;
        return e.target || e.srcElement; 
    }
}
$scope.token=localStorage.getItem('token');
    for(var l=0;l<value1.length;l++){
         $scope.getHistory(value1[l],$scope.token);
    }

    }
    });
  }


        $scope.getHistory = function(tags,token) {
          var d = new Date();
          var start = new Date(d.getFullYear(),d.getMonth(),d.getDate(),7,30,0,0).getMilliseconds();
          var end = d.getMilliseconds();
          var emData = {"start": "10h-ago","tags": [   {       "name": tags,     "order": "desc",     "limit": 300     }  ] };;

        //   var timeBound ={ "cache_time": 0, "tags": [   {  "name": tags,  "order": "desc"  } ],
        //   "start": 1477817081000 ,
        //   "end": 1478817081000
        // }c
          console.log(getData.PST(tags));
           getData.getValue(tags,token,getData.PST(tags))
           .then(function(res) {

              if(res.res.tags[0].name=="WinCC_OA.ChennaiSite.SMB1.Voltage"){
                console.log(res.res.tags)
                  $scope.smbVoltage=res.res.tags[0].results[0].values;
              }
              else if(res.res.tags[0].name=="WinCC_OA.ChennaiSite.SMB1.STR1.Current"){

              $scope.SMB1STR1Current=res.res.tags[0].results[0].values;
              }
              else if(res.res.tags[0].name=="WinCC_OA.ChennaiSite.SMB1.STR2.Current"){
              $scope.SMB1STR2Current=res.res.tags[0].results[0].values;
              }
              else if(res.res.tags[0].name=="WinCC_OA.ChennaiSite.SMB1.STR3.Current"){
              $scope.SMB1STR3Current=res.res.tags[0].results[0].values;
              }
              else if(res.res.tags[0].name=="WinCC_OA.ChennaiSite.SMB1.STR4.Current"){
              $scope.SMB1STR4Current=res.res.tags[0].results[0].values;
              }
              else if(res.res.tags[0].name=="WinCC_OA.ChennaiSite.SMB1.STR5.Current"){
              $scope.SMB1STR5Current=res.res.tags[0].results[0].values;
              }
              else if(res.res.tags[0].name=="WinCC_OA.ChennaiSite.SMB1.STR6.Current"){
              $scope.SMB1STR6Current=res.res.tags[0].results[0].values;
              }
              else if(res.res.tags[0].name=="WinCC_OA.ChennaiSite.SMB1.STR7.Current"){
              $scope.SMB1STR7Current=res.res.tags[0].results[0].values;
              }
              else if(res.res.tags[0].name=="WinCC_OA.ChennaiSite.SMB1.STR8.Current"){
              $scope.SMB1STR8Current=res.res.tags[0].results[0].values;
              }

/*

              $scope.smbInvData =[
              {   type: 'line', name: 'Voltage', data: $scope.smbVoltage},
              {   type: 'line', name: 'STR1', data: $scope.SMB1STR1Current},
              {   type: 'line', name: 'STR2', data: $scope.SMB1STR2Current},
              {   type: 'line', name: 'STR3', data: $scope.SMB1STR3Current},
              {   type: 'line', name: 'STR4', data: $scope.SMB1STR4Current},
              {   type: 'line', name: 'STR5', data: $scope.SMB1STR5Current},
              {   type: 'line', name: 'STR6', data: $scope.SMB1STR6Current},
              {   type: 'line', name: 'STR7', data: $scope.SMB1STR7Current},
              {   type: 'line', name: 'STR8', data: $scope.SMB1STR8Current}  
              ];*/

              //id,data,title,yAxisTitle

    lineChart("multi-axis-line",$scope.smbVoltage,$scope.SMB1STR1Current,$scope.SMB1STR2Current,$scope.SMB1STR3Current,$scope.SMB1STR4Current,$scope.SMB1STR5Current,$scope.SMB1STR6Current,$scope.SMB1STR7Current,$scope.SMB1STR8Current,"Current & Voltage","Current(Amp), Voltage(KV)");
    
           });

        };
        //$scope.getHistory("WinCC_OA.ChennaiSite.SMB1.Power",token);
        $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.Voltage",token);
        $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR1.Current",token);
        $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR2.Current",token);
        $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR3.Current",token);
        $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR4.Current",token);
        $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR5.Current",token);
        $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR6.Current",token);
        $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR7.Current",token);
        $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR8.Current",token);

        smbfetchInterval = $interval(smbFetch, 2*60*1000);
        function smbFetch(){
          //$scope.img123=[];
        //alert($state.current.name)
          //console.log($state.current.name)
                if($state.current.name!=='smb'){
              $interval.cancel(smbfetchInterval);
              return ;
              }
              $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.Voltage",token);
              $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR1.Current",token);
              $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR2.Current",token);
              $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR3.Current",token);
              $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR4.Current",token);
              $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR5.Current",token);
              $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR6.Current",token);
              $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR7.Current",token);
              $scope.getHistory("WinCC_OA.ChennaiSite.SMB1.STR8.Current",token);
            }
})
