angular.module('app.wstnCtrl', ['ui.router', 'ngTable'])

.controller('wstnCtrl',  function($http,$scope,$rootScope,$interval,$state,getData) {
  $interval.cancel($rootScope.dashboardInterval);

  $scope.lineData =[{   type: 'area', name: 'ModuleTemp', data: []},
  {   type: 'area', name: 'Irradiance', data: []} ];

  //start=parseInt(start); end=parseInt(end);
  $scope.VnCws = function(start,end){
    //alert("da")
    $scope.getHistory("WinCC_OA.ChennaiSite.EM.Voltage",token,start,end);
    $scope.getHistory("WinCC_OA.ChennaiSite.EM.Current",token,start,end);
  }

  var uiLineChart = function(data){
    Highcharts.chart('wstn-line-chart', {
            chart: {
                zoomType: 'x'
            },
            title: {
                text: 'Module Temperature & Irradiance'
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
                    text: 'Module Temperature,Irradiance'
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
  }

var token=localStorage.getItem("token");
$scope.getHistory = function(tags,token) {

  var emData = {"start": "15mi-ago","tags": [   {       "name": tags,     "order": "desc"  }  ] };
   getData.getValue(tags,token,getData.PST(tags))
   .then(function(res) {

      if(res.res.tags[0].name=="WinCC_OA.ChennaiSite.WSTN.ModuleTemp"){
        $scope.ModuleTemp=res.res.tags[0].results[0].values;
        //console.log(res.res.tags[0].results);
      }
      else if(res.res.tags[0].name=="WinCC_OA.ChennaiSite.WSTN.Irradiance"){
      //  //console.log(res.res)
      $scope.Irradiance= res.res.tags[0].results[0].values;
      }
      $scope.lineData =[{   type: 'area', name: 'ModuleTemp', data: $scope.ModuleTemp},
      {   type: 'line', name: 'Irradiance', data: $scope.Irradiance} ];


      uiLineChart($scope.lineData);
      $scope.barOPRGraph();

   });

};
$scope.barOPRGraph = function(){
var salesAnlysisChart = new FusionCharts({
      type: 'mscombi2d',
      renderAt: 'wstn-bar-chart',
      width: '400',
      height: '400',
      dataFormat: 'json',
      dataSource: {
          "chart": {
              "caption": "Performance Matrix",
              "subCaption": "",
              "xAxisname": "Month",
              "yAxisName": "OPR, IPR ,PR",
              "numberPrefix": "",
              "numberSuffix": "%",
              "showBorder": "0",
              "showValues": "0",
              "paletteColors": "#0075c2,#1aaf5d,#f2c500",
              "bgColor": "#ffffff",
              "showCanvasBorder": "0",
              "canvasBgColor": "#ffffff",
              "captionFontSize": "14",
              "subcaptionFontSize": "14",
              "subcaptionFontBold": "0",
              "divlineColor": "#999999",
              "divLineIsDashed": "1",
              "divLineDashLen": "1",
              "divLineGapLen": "1",
              "showAlternateHGridColor": "0",
              "usePlotGradientColor": "0",
              "toolTipColor": "#ffffff",
              "toolTipBorderThickness": "0",
              "toolTipBgColor": "#000000",
              "toolTipBgAlpha": "80",
              "toolTipBorderRadius": "2",
              "toolTipPadding": "5",
              "legendBgColor": "#ffffff",
              "legendBorderAlpha": '0',
              "legendShadow": '0',
              "legendItemFontSize": '10',
              "legendItemFontColor": '#666666'
          },
          "categories": [
              {
                  "category":

                  [
          {
            "label":"Nov 2"
          },
          {
            "label":"Nov 4"
          },
          {
            "label":"Nov 6"
          },
          {
            "label":"Nov 8"
          },
          {
            "label":"Nov 10"
          },
          {
            "label":"Nov 12"
          },
          {
            "label":"Nov 14"
          }
        ]}
          ],
          "dataset": [
              {
                  "seriesName": "WPR",
                  "showValues": "1",
                  "data": [
                      {
                          "value": "120"
                      },
                      {
                          "value": "110"
                      },
                      {
                          "value": "92"
                      },
                      {
                          "value": "110"
                      },
                      {
                          "value": "98"
                      },
                      {
                          "value": "102"
                      },
                      {
                          "value": "105"
                      }
                    ]
              },
              {
                  "seriesName": "IPR",
                  "showValues": "2",
                  "data": [
                      {
                          "value": "125"
                      },
                      {
                          "value": "115"
                      },
                      {
                          "value": "88"
                      },
                      {
                          "value": "90"
                      },
                      {
                          "value": "100"
                      },
                      {
                          "value": "110"
                      },
                      {
                          "value": "112"
                      }
                  ]
              },
              {
                  "seriesName": "PR",
                  "showValues": "3",
                  "data": [
                      {
                          "value": "79"
                      },
                      {
                          "value": "79"
                      },
                      {
                          "value": "80"
                      },
                      {
                          "value": "64"
                      },
                      {
                          "value": "78"
                      },
                      {
                          "value": "65"
                      },
                      {
                          "value": "75"
                      }
                  ]
              },
              {
                  "seriesName": "PR Adj",
                  "renderAs": "line",
                  "data": [
                      {
                          "value": "80"
                      },
                      {
                          "value": "80"
                      },
                      {
                          "value": "80"
                      },
                      {
                          "value": "70"
                      },
                      {
                          "value": "80"
                      },
                      {
                          "value": "80"
                      },
                      {
                          "value": "82"
                      }

                  ]
              },
              {
                  "seriesName": "OPR",
                  "renderAs": "line",
                  "data": [
                      {
                          "value": "105"
                      },
                      {
                          "value": "100"
                      },
                      {
                          "value": "100"
                      },
                      {
                          "value": "84"
                      },
                      {
                          "value": "100"
                      },
                      {
                          "value": "98"
                      },
                      {
                          "value": "95"
                      } ]
              }
          ]
      }
  }).render();
}
$scope.barOPRGraph();
  // $scope.getHistory("Investor_Performance_Ratio",token);
  // $scope.getHistory("Operating_Performance_Ratio",token);
  // $scope.getHistory("Performance_Ratio",token);
  // $scope.getHistory("Temperature_Corrected_Actual_PR",token);
  // $scope.getHistory("Weather_Performance_Ratio",token);

  $scope.getHistory("WinCC_OA.ChennaiSite.WSTN.Irradiance",token);
  $scope.getHistory("WinCC_OA.ChennaiSite.WSTN.ModuleTemp",token);

})
