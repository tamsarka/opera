angular.module('app.emCtrl', ['ui.router', 'ngTable'])



.controller('emCtrl',  function($http,$scope,$rootScope,$interval,$state,getData ) {
$interval.cancel($rootScope.dashboardInterval);

$scope.dp1 = function(start,end){
  //alert("da")
  $scope.getHistory("WinCC_OA.ChennaiSite.EM.Power",token,start,end);
  $scope.getHistory("WinCC_OA.ChennaiSite.OPR",token,start,end);
}
$scope.VnCem = function(start,end){
  //alert("da")
  $scope.getHistory("WinCC_OA.ChennaiSite.EM.Voltage",token,start,end);
  $scope.getHistory("WinCC_OA.ChennaiSite.EM.Current",token,start,end);
}

$scope.lineData =[{   type: 'area', name: 'ModuleTemp', data: []},
{   type: 'area', name: 'Irradiance', data: []} ];
var token=localStorage.getItem("token");
var lineChart = function(id,data,title,yAxisTitle){
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
}



      $scope.getHistory = function(tags,token) {
        var d = new Date().getTime()-8*3600*1000;
        //var start = new Date(d.getFullYear(),d.getMonth(),d.getDate(),7,30,0,0).getMilliseconds();
        //var end = d.getMilliseconds();
        // var emData = {"start": "15mi-ago","tags": [   {  "name": tags,  "order": "desc" }  ] };
        //alert(d);

         getData.getValue(tags,token,getData.PST(tags))
         .then(function(res) {

            if(res.res.tags[0].name=="WinCC_OA.ChennaiSite.EM.Power"){
             console.log(res.res.tags[0].results[0].values[0]);
                $scope.emPower=res.res.tags[0].results[0].values;

            }
            else if(res.res.tags[0].name=="WinCC_OA.ChennaiSite.OPR"){
              $scope.OPR=res.res.tags[0].results[0].values;
            }
            else if(res.res.tags[0].name=="WinCC_OA.ChennaiSite.EM.Voltage"){
              $scope.emVoltage=res.res.tags[0].results[0].values;
            }
            else if(res.res.tags[0].name=="WinCC_OA.ChennaiSite.EM.Current"){
                $scope.emCurrent=res.res.tags[0].results[0].values;
              }
              $scope.lineData =[{   type: 'line', name: 'OPR', data: $scope.OPR},
              {   type: 'line', name: 'Power', data: $scope.emPower} ];
              $scope.voltCurr =[{   type: 'line', name: 'Current', data: $scope.emCurrent},
              {   type: 'line', name: 'Voltage', data: $scope.emVoltage} ];
              //id,data,title,yAxisTitle
            lineChart("line-chart-multi",$scope.lineData,"Power & OPR","Power(KW), OPR");
            lineChart("VoltageCurrent",$scope.voltCurr,"Current & Voltage","Current(Amp), Voltage(KV)");

         });

      };
      $scope.getHistory("WinCC_OA.ChennaiSite.EM.Power",token);
      $scope.getHistory("WinCC_OA.ChennaiSite.OPR",token);
      $scope.getHistory("WinCC_OA.ChennaiSite.EM.Voltage",token);
      $scope.getHistory("WinCC_OA.ChennaiSite.EM.Current",token);

})
