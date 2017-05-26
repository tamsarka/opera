angular.module('app.invCtrl', ['ui.router', 'ngTable'])

.controller('invCtrl',  function($http,$scope,$rootScope,$interval,$state,getData) {
$scope.dp = function(start,end){
  //alert("da")
  $scope.getHistory("WinCC_OA.ChennaiSite.INV1.Power",token,start,end);
  $scope.getHistory("WinCC_OA.ChennaiSite.INV1.OPR",token,start,end);
}
$scope.VnC = function(start,end){
  //alert("da")
  $scope.getHistory("WinCC_OA.ChennaiSite.INV1.Voltage",token,start,end);
  $scope.getHistory("WinCC_OA.ChennaiSite.INV1.Current",token,start,end);
}


$interval.cancel($rootScope.dashboardInterval);
$scope.lineData =[{   type: 'area', name: 'ModuleTemp', data: []},
{   type: 'area', name: 'Irradiance', data: []} ];
var token=localStorage.getItem("token");
/*var lineChart = function(id,data,title,yAxisTitle){
  Highcharts.chart(id, {
          chart: {
              zoomType: 'x'
          },
          title: {
              text: title
          },
          subtitle: {
              text: document.ontouchstart === undefined ?
                      'Click and drag in the plot area to zoom in' : 'Pinch the chart to zoom in'
          },
          xAxis: {
              type: 'datetime'
          },
          yAxis: {
              title: {
                  text: yAxisTitle
              }
          },
          legend: {
              enabled: true
          },
          plotOptions: {
              area: {
                  fillColor: {
                      linearGradient: {
                          x1: 0,
                          y1: 0,
                          x2: 0,
                          y2: 1
                      },
                      stops: [
                          [0, Highcharts.getOptions().colors[0]],
                          [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                      ]
                  },
                  marker: {
                      radius: 2
                  },
                  lineWidth: 1,
                  states: {
                      hover: {
                          lineWidth: 1
                      }
                  },
                  threshold: null
              }
          },

          series: data
      });
}*/
var lineChart = function(id,data,data1,title,yAxisTitle){
  
Highcharts.chart('line-chart-multi', {
          chart: {
            zoomType: 'xy'
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
            name: 'OPR',
            type: 'line',
            yAxis: 1,
            data: data,
            tooltip: {
                valueSuffix: ' V'
            }

        }, {
            name: 'Power',
            type: 'line',
            yAxis: 2,
            data: data1,
            marker: {
                enabled: false
            },
            tooltip: {
                valueSuffix: ' Amps'
            }

        }]
    });
  }
var lineChart1 = function(id,data,data1,title,yAxisTitle){
  
Highcharts.chart('VoltageCurrent', {
          chart: {
            zoomType: 'xy'
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
            name: 'Current',
            type: 'line',
            yAxis: 1,
            data: data,
            tooltip: {
                valueSuffix: ' V'
            }

        }, {
            name: 'Voltage',
            type: 'line',
            yAxis: 2,
            data: data1,
            marker: {
                enabled: false
            },
            tooltip: {
                valueSuffix: ' Amps'
            }

        }]
    });
  }

      $scope.getHistory = function(tags,token,start,end) {

        // var d = start;
        // var start = new Date(d.getFullYear(),d.getMonth(),d.getDate(),7,30,0,0).getMilliseconds();
        // var end = d.getMilliseconds();
        // var emData = {"start": "1y-ago","tags": [   {       "name": tags,     "order": "desc",     "limit": 300     }  ] };

      //   var timeBound ={ "cache_time": 0, "tags": [   {  "name": tags,  "order": "desc"  } ],
      //   "start": 1477817081000 ,
      //   "end": 1478817081000
      // }
         getData.getValue(tags,token,getData.PST(tags,start,end))
         .then(function(res) {
            if(res.res.tags[0].name=="WinCC_OA.ChennaiSite.INV1.Power"){
              ////console.log("res",res.res.tags);
                $scope.invPower=res.res.tags[0].results[0].values;
            }
            else if(res.res.tags[0].name=="WinCC_OA.ChennaiSite.OPR"){

            $scope.invOPR=res.res.tags[0].results[0].values;
            }
            else if(res.res.tags[0].name=="WinCC_OA.ChennaiSite.INV1.Voltage"){
            $scope.invVoltage=res.res.tags[0].results[0].values;
            }
            else if(res.res.tags[0].name=="WinCC_OA.ChennaiSite.INV1.Current"){
            $scope.invCurrent=res.res.tags[0].results[0].values;
            }

            $scope.oprInvData =[{   type: 'line', name: 'OPR', data: $scope.invOPR},
            {   type: 'line', name: 'Power', data: $scope.invPower} ];
            $scope.invVoltCurr =[{   type: 'line', name: 'Current', data: $scope.invCurrent},
            {   type: 'line', name: 'Voltage', data: $scope.invVoltage} ];
            //id,data,title,yAxisTitle
          lineChart("line-chart-multi",$scope.invOPR,$scope.invCurrent,"Power & OPR","Power(KW), OPR");
          lineChart1("VoltageCurrent",$scope.invCurrent,$scope.invVoltage,"Current & Voltage","Current(Amp), Voltage(KV)");

         });

      };
      $scope.getHistory("WinCC_OA.ChennaiSite.INV1.Power",token);
      $scope.getHistory("WinCC_OA.ChennaiSite.OPR",token);
      $scope.getHistory("WinCC_OA.ChennaiSite.INV1.Voltage",token);
      $scope.getHistory("WinCC_OA.ChennaiSite.INV1.Current",token);


})