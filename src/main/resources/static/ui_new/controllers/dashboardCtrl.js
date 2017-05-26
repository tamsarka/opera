angular.module('app.dashboardCtrl', ['ui.router', 'ngTable'])

/*.controller('dashboardCtrl',['NgTableParams','$http','$scope','$rootScope','$interval','$state','$window','getData',function(NgTableParams,$http,$scope,$rootScope,$interval,$state,$window,getData) {*/
 .controller('dashboardCtrl',['$http','$scope','$rootScope','$interval','$state','$window','getData','NgTableParams', '$timeout', function($http,$scope,$rootScope,$interval,$state,$window, getData, NgTableParams, $timeout) {
var assetData = [];


$rootScope.analytics_flag=false;
if($rootScope.flag==true)
{
  $window.location.reload();
  $rootScope.flag=false;
}
$scope.donutData=[{"label":"Expected","value":3.9},{"label":"Actual","value":3.98}];
$scope.lineData =[{   type: 'area', name: 'AG/SG',      data: []     },
{   type: 'line', name: 'SG',      data:[]     },
{   type: 'line', name: 'AG',      data: []     },
{   type: 'line', name: 'UI',      data: []     }];


//$rootScope.token=localStorage.getItem("token");
var donut =function(DOMIDname,caption,data,actValue){
  var revenueChart = new FusionCharts({
      type: 'doughnut2d',
      renderAt: DOMIDname,
      width: '300',
      height: '300',
      dataFormat: 'json',
      dataSource: {
          "chart": {
              "caption": caption,
              "subCaption": "",
              "numberPrefix": "",
              "paletteColors": "#0075c2,#f16c00,#8e0000",
              "bgColor": "#ffffff",
              "showBorder": "0",
              "use3DLighting": "0",
              "showShadow": "0",
              "enableSmartLabels": "0",
              "startingAngle": "310",
              "showLabels": "0",
              "showPercentValues": "1",
              "showLegend": "1",
              "legendShadow": "0",
              "legendBorderAlpha": "0",
              "defaultCenterLabel": actValue+"MWH",
              "centerLabel": " $Value MWH",
              "centerLabelBold": "1",
              "showTooltip": "0",
              "decimals": "2",
              "captionFontSize": "14",
              "subcaptionFontSize": "14",
              "subcaptionFontBold": "0"
          },
          "data": data
      }
  }).render();
}

var uiLineChart = function(data,data1,data2,data3){
  
Highcharts.chart('multi-axis-line', {
          chart: {
            zoomType: 'xy'
        },
        title: {
            text: 'Generation and UI History'
        },
        
        xAxis: [{
            type: 'datetime'
        }],
        yAxis: [{ // Primary yAxis
            
            title: {
                text: "AG",
                style: {
                    color: Highcharts.getOptions().colors[2]
                }
            },
            opposite: true

        }, { // Secondary yAxis
            gridLineWidth: 0,
            title: {
                text: "SG",
                style: {
                    color: Highcharts.getOptions().colors[0]
                }
            },
            labels: {
                format: '{value} %',
                style: {
                    color: Highcharts.getOptions().colors[0]
                }
            }

        }, 
        { // Secondary yAxis
            gridLineWidth: 0,
            title: {
                text: "UI",
                style: {
                    color: Highcharts.getOptions().colors[0]
                }
            },
            labels: {
                format: '{value} %',
                style: {
                    color: Highcharts.getOptions().colors[0]
                }
            }

        },
        { // Tertiary yAxis
            gridLineWidth: 0,
            title: {
                text: "AG/SG",
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            },
            labels: {
                format: '{value} %',
                style: {
                    color: Highcharts.getOptions().colors[1]
                }
            },
            opposite: true
        },
        ],
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
            name: 'AG',
            type: 'line',
            yAxis: 0,
            data: data,
            tooltip: {
                valueSuffix: ' %'
            }

        }, {
            name: 'SG',
            type: 'area',
            yAxis: 1,
            data: data1,
            tooltip: {
                valueSuffix: ' %'
            }

        }, {
            name: 'UI',
            type: 'line',
            data: data2,
            yAxis: 2,
            tooltip: {
                valueSuffix: ' %'
            }
        },
        {
            name: 'AG/SG',
            type: 'line',
             yAxis: 3,
            data: data3,
            tooltip: {
                valueSuffix: ' %'
            }
        }]
    });
  }


var token=localStorage.getItem("token");

$scope.getValueLatest = function(tags,token) {
  var latestPoint ={"start": "1y-ago","tags": [   {       "name": tags,     "order": "desc",     "limit": 1     }  ] };
   getData.getValue(tags,token,latestPoint)
   .then(function(res) {
    //console.log("latest");
    if(res.res.tags[0].name=="WinCC_OA.ChennaiSite.ExpectedEnergy"){
      //alert(res.res.tags[0].name);

      $scope.expEnergy = parseFloat(res.res.tags[0].results[0].values[0][1]/1000000).toFixed(2);
    }
    else if(res.res.tags[0].name=="WinCC_OA.ChennaiSite.ActualPR"){
        $scope.apr = parseFloat(res.res.tags[0].results[0].values[0][1]/1000000).toFixed(2);
    }
    else if(res.res.tags[0].name=="WinCC_OA.ChennaiSite.ActualEnergy"){
      var magfac=0;
      var emission = parseFloat(res.res.tags[0].results[0].values[0][1]/1000).toFixed(2);
      $scope.co2= (emission*0.9).toFixed(2);
      $scope.nox=(emission*0.02).toFixed(2);
      $scope.so2=(emission*0.0012).toFixed(2);
      $scope.actualEnergy = parseFloat(res.res.tags[0].results[0].values[0][1]/1000000).toFixed(2);
      var d= new Date().getHours();
      d=parseFloat(d*12/100);
       exp =$scope.actualEnergy;
       exp = parseFloat(d)+parseFloat(exp);
      $scope.donutData=[{"label":"Expected","value":exp},{"label":"Actual","value":$scope.actualEnergy}];
      donut("donut-chart1","November",$scope.donutData,$scope.actualEnergy);
      donut("donut-chart2","Year: 2016",$scope.donutData,$scope.actualEnergy);
      donut("donut-chart3","From Inception",$scope.donutData,$scope.actualEnergy);
    }
   });
};

/*var self = this;*/
var data = [{name: "Meter_Frequency_High", value: 5},{name: "Inverter1_High_Temp", value: 850},{name: "SMB1_Comm_Fail", value: 590} ];



$scope.getTableData = function(){
  getData.getTableContent().then(function(res){
    if(res.status == '200'){ 
      assetData = res.data;
      console.clear();

      getData.getOpenTicket().then(function(ticketRes){
        if(ticketRes.status == '200'){
          angular.forEach(assetData, function(asset_value, asset_key){
            angular.forEach(ticketRes.data, function(ticketRes_value, ticketRes_key){              
              if(asset_value.assetName == ticketRes_value.assetName){
                asset_value.incidentID = ticketRes_value.incidentID;    
              }else{
                asset_value.incidentID = null;    
              }
            });
          });
        }
        else{

        }
      });



      
      
      $scope.tableParams = new NgTableParams({
          page: 1, // show first page
          count: 5 // count per page
        }, { 
          data: assetData
        });
    }else{      
      console.log("Error");
      assetData = null;
      $scope.tableParams = new NgTableParams({
        page: 1, // show first page
        count: 5 // count per page
      }, { 
        data: assetData
      });
    }
  });  
  // ==
};

// $scope.getTableData = function(){  
//       $scope.tableParams = new ngTableParams({
//                 page: 1,
//                 count: 5,
//             }, {
//                 dataset: function ($defer, params) {
//                     var page = params.page();
//                     var size = params.count();
//                     var testUrl = 'https://api.github.com/search/repositories';
//                     var search = {
//                       q: 'angular',
//                       page: page,
//                       per_page: size
//                     }
//                     getData.getTableContent().then(function(res){
//                       if(res.status == '200'){
//                         params.total(res.data.length); 
//                         var assetData = res.data;
//                       }else{      
//                         console.log("Error");
//                       }
//                     // $http.get(testUrl, {headers: { 'Content-Type': 'application/json'} })
//                     //      .then(function(res) {
//                     //         params.total(res.data.total_count);
//                     //         $defer.resolve(res.data.items);
//                     //     }, function(reason) {
//                     //         $defer.reject();
//                     //     }
//                     // );
//                     });
//                 }
//               });
// };

// var assetData= [
//   {
//     "actualFailureArea": "Test2",
//     "assetName": "SMB1",
//     "equipmentStatus": "Faulty",
//     "functionalID": "LT008-433-510",
//     "incidentID": 0,
//     "indicatedFailureArea": "Engine",
//     "logDate": "2017-03-17T10:01:51.122Z",
//     "modelNumber": "STR_PROD_ROB_10",
//     "occurenceTime": "2017-03-17T10:01:51.122Z",
//     "priority": "High",
//     "resolutionComments": "Test",
//     "resolvedBy": "string",
//     "resolvedDate": "2017-03-17T10:01:51.122Z",
//     "serialNumber": "64164664",
//     "ticketStatusCurr": "OPEN",
//     "comments": "comment for 1",
//     "updatedByName": "string",
//     "updatedTime": "2017-03-17T10:01:51.122Z",
//     "acknowledged": false,
//     "snooze": "snooze",
//     "setAlarm": false
//   },
//   {
//     "actualFailureArea": "Sest2",
//     "assetName": "TMB1",
//     "equipmentStatus": "Gulty",
//     "functionalID": "XT008-433-510",
//     "incidentID": 1,
//     "indicatedFailureArea": "XEngine",
//     "logDate": "2017-03-19T10:01:51.122Z",
//     "modelNumber": "SPOR_PROD_ROB_10",
//     "occurenceTime": "2017-03-12T10:01:51.122Z",
//     "priority": "Low",
//     "resolutionComments": "Test 2",
//     "resolvedBy": "string x",
//     "resolvedDate": "2017-03-16T10:01:51.122Z",
//     "serialNumber": "64164667",
//     "ticketStatusCurr": "OPEN1",
//     "comments": "Test 4",
//     "updatedByName": "string name",
//     "updatedTime": "2016-03-18T10:01:51.122Z",
//     "acknowledged": false,
//     "snooze": "snooze",
//     "setAlarm": false
//   }
// ];

$scope.hrs = [];
$scope.mins = [];
$scope.secs = [];

for (var i= 1 ; i <= 24; i++){
  $scope.hrs.push(i);  
}
 
for (var i= 0 ; i < 60; i++){  
  $scope.mins.push(i);  
}

for (var i= 0 ; i < 60; i++){
  // ('0' + i).slice(-2)
  $scope.secs.push(i);  
}

$scope.dateTime = "loading clock..."; // initialise the time variable
$scope.tickInterval = 1000 //ms

var tick = function() {
    $scope.dateTime = Date.now() // get the current time
    $timeout(tick, $scope.tickInterval); // reset the timer
}

// Start the timer
$timeout(tick, $scope.tickInterval);


$scope.generateTicket = function(data){
  console.clear();  
  $scope.myModal = data;
  console.log("myModal Data", $scope.myModal);
}

/* cearetd: 20 March' 2017
   function: update the isAck status of Alarm & Warning Table*/

$scope.alartAck = function(user, index){
  user.loader = true;  
  var obj ={};
  obj = user;
  // obj.ackFlag = true;
  // user.ackFlag = true;
  getData.updateAcknowledge(obj).then(function(res){
    if(res.status == '200'){          
      user.loader = false;
      user.ackFlag = true; 
      document.getElementById("button_"+user.functionalID+"_"+index).disabled = false;     
    }else{      
      user.loader = false;         
      console.log("Error");
    }
  }); 
};

$scope.saveTicket = function(data){
  var ticket = {};
  ticket.actualFailureArea = data.actualFailureArea;
  ticket.equipmentStatus = data.equipmentStatus;
  ticket.functionalID = data.functionalID;
  ticket.incidentID = data.incidentID;
  ticket.logDate = data.logDate;
  ticket.modelNumber = data.modelNumber;
  ticket.occurenceTime = data.occurenceTime;
  ticket.assetName = data.assetName;
  ticket.primaryDesc = data.comments;
  ticket.incidentID = 0;
  ticket.priority = data.priority;
  ticket.resolutionComments = data.ResolutionComment;
  ticket.resolvedBy = data.resolvedBy;
  ticket.resolvedDate = data.resolvedDate;
  ticket.serialNumber = data.serialNumber;
  ticket.ticketStatusCurr = data.ticketStatusCurr;
  ticket.updatedByName = data.updatedByName;
  ticket.updatedTime = data.updatedTime;

  data.ticketStatusCurr = "in progress";


  getData.createTicket(ticket).then(function(res){
    if(res.status == '200'){
      // $scope.getTableData();
      data.incidentID = res.data.incidentID;
      getData.assetStatusUpdate(data).then(function(resStatus){
        if(resStatus.status == '200'){
          $('#ticketModal').modal('hide');
        }
        else{

        }
      })      
    }else{      
      console.log("Error");
    }
  });

}


$scope.clrTicket = function(data){  
  console.log("myData", data);
  $scope.myModal.Comments= '';
  $scope.myModal.ResolutionComment= '';
  $scope.myModal.IndicatedFailureArea= '';
};

$scope.setTime = function(user, index){
  var d = new Date()

  $scope.almModal = {};
  $scope.almModal.data = user;
  $scope.almModal.index = index 

  $scope.almModal.hrs = d.getHours();
  $scope.almModal.mins = d.getMinutes();
  $scope.almModal.secs = d.getSeconds();

  console.clear();
  console.log("alarm  Data", $scope.almModal);

};

$scope.saveAlarm = function(data,index){
  console.clear();
  console.log(data);

  getData.sonnzeUpdate(data).then(function(res){
    if(res.status == '200'){      
      $('#alarmModal').modal('hide');
      assetData.splice(data.data, 1);
    }
    else{

    }
  });
      
}



/****************************************************/

$scope.getHistory = function(tags,token) {
  var d = new Date();
  var start = d.getTime()-24*60*60*1000;
  var end = d.getTime();
  var timeBound ={ "cache_time": 0, "tags": [   {  "name": tags,  "order": "desc"  } ],
  "start": start ,
  "end": end
}
var duration =24*60*60*1000;
   getData.getValue(tags,token,getData.PSTD(tags,duration))
   .then(function(res) {
     //debugger;
      if(res.res.tags[0].name=="WinCC_OA.ChennaiSite.AG_SG_RATIO"){

        $scope.AG_SG = res.res.tags[0].results[0].values;

      }
      else if(res.res.tags[0].name=="WinCC_OA.ChennaiSite.SG"){
      //  //console.log(res.res.tags[0].results[0].values);
        $scope.SG1 = res.res.tags[0].results[0].values;
        //console.log(res.res.tags[0].results[0].values);

      }
      else if(res.res.tags[0].name=="WinCC_OA.ChennaiSite.INST_AG"){
          $scope.AG = res.res.tags[0].results[0].values;

      }
      else if (res.res.tags[0].name=="WinCC_OA.ChennaiSite.UI") {
          $scope.UI = res.res.tags[0].results[0].values;
        }
    else if (res.res.tags[0].name=="WinCC_OA.ChennaiSite.AG_SG") {
            $scope.AG_SG1 = res.res.tags[0].results[0].values;
        }
      $scope.lineData =[{   type: 'line', name: 'SG', data: $scope.SG1},
      {   type: 'line', name: 'AG', data: $scope.AG},
    {   type: 'line', name: 'UI', data: $scope.UI},
  {   type: 'area', name: 'AG/SG', data:$scope.AG_SG1} ];
      uiLineChart($scope.AG,$scope.SG1,$scope.UI,$scope.AG_SG1);
});
}
$scope.backend1 =function(){
  $http({
  method: 'GET',
  url: 'https://test-orch-solar.run.aws-usw02-pr.ice.predix.io/services/solar/kpi_data/chennai'
}).then(function successCallback(response) {
  $scope.INSTDeviation=response.data.frequencyBlock[0].keyValue;
  $scope.timestamp= (new Date()).toDateString();
  $scope.AVGDeviation= response.data.frequencyBlock[1].keyValue;
  $scope.AG_DC_RATIO=response.data.ratioBlock[0].keyValue;
  $scope.AG_SG_RATIO=response.data.ratioBlock[1].keyValue;
  $scope.DG=parseFloat(response.data.currentBlock[0].keyValue).toFixed(2);
  $scope.SG=response.data.currentBlock[1].keyValue;
  $scope.CSG=response.data.currentBlock[2].keyValue;
  $scope.INST_AG=response.data.currentBlock[3].keyValue;
  $scope.AVG_AG=response.data.currentBlock[4].keyValue;
  $scope.UId=response.data.currentBlock[5].keyValue;
  $scope.UI_RATE=response.data.currentBlock[6].keyValue;
  $scope.NET_GAIN=response.data.currentBlock[7].keyValue;
  $scope.blockTable=response.data.historyBlock;
  if($scope.blockTable==undefined||$scope.blockTable.length==0){
    //todo
    $scope.blockTable=[];
  }
    // this callback will be called asynchronously
    // when the response is available
  }, function errorCallback(response) {
    // called asynchronously if an error occurs
    // or server returns response with an error status.
  });
}
$scope.backend1();
$scope.getHistory("WinCC_OA.ChennaiSite.SG",token);
$scope.getHistory("WinCC_OA.ChennaiSite.INST_AG",token);
$scope.getHistory("WinCC_OA.ChennaiSite.UI",token);
$scope.getHistory("WinCC_OA.ChennaiSite.AG_SG",token);

//$scope.getHistory("WinCC_OA.ChennaiSite.INST_AG",token);


$scope.getValueLatest("WinCC_OA.ChennaiSite.ExpectedEnergy",token);
$scope.getValueLatest("WinCC_OA.ChennaiSite.ActualEnergy",token);
$scope.getValueLatest("WinCC_OA.ChennaiSite.ActualPR",token);
$scope.getValueLatest("WinCC_OA.ChennaiSite.APR",token);
$scope.getValueLatest("WinCC_OA.ChennaiSite.OPR",token);
$scope.getValueLatest("WinCC_OA.ChennaiSite.INST_AG",token);
$scope.getValueLatest("WinCC_OA.ChennaiSite.AVG_AG",token);
$scope.getValueLatest("WinCC_OA.ChennaiSite.UI",token);
$scope.getValueLatest("WinCC_OA.ChennaiSite.NET_GAIN",token);
$scope.getValueLatest("WinCC_OA.ChennaiSite.AG_DC_RATIO",token);
$scope.getValueLatest("WinCC_OA.ChennaiSite.AG_SG_RATIO",token);
$scope.getValueLatest("WinCC_OA.ChennaiSite.DEVIATION",token);
$scope.getValueLatest("WinCC_OA.ChennaiSite.UI_RATE",token);
$scope.getValueLatest("WinCC_OA.ChennaiSite.DC",token);
$rootScope.dashboardInterval = $interval( function(){
  //console.log("calling in interval")
$scope.backend1();

$scope.getHistory("WinCC_OA.ChennaiSite.SG",token);
$scope.getHistory("WinCC_OA.ChennaiSite.INST_AG",token);
$scope.getHistory("WinCC_OA.ChennaiSite.UI",token);
$scope.getHistory("WinCC_OA.ChennaiSite.AG_SG",token);
//WinCC_OA.ChennaiSite.ActualPR

//$scope.getHistory("WinCC_OA.ChennaiSite.INST_AG",token);


$scope.getValueLatest("WinCC_OA.ChennaiSite.ExpectedEnergy",token);
$scope.getValueLatest("WinCC_OA.ChennaiSite.ActualEnergy",token);
$scope.getValueLatest("WinCC_OA.ChennaiSite.ActualPR",token);
$scope.getValueLatest("WinCC_OA.ChennaiSite.APR",token);
$scope.getValueLatest("WinCC_OA.ChennaiSite.OPR",token);
$scope.getValueLatest("WinCC_OA.ChennaiSite.INST_AG",token);
$scope.getValueLatest("WinCC_OA.ChennaiSite.AVG_AG",token);
$scope.getValueLatest("WinCC_OA.ChennaiSite.UI",token);
$scope.getValueLatest("WinCC_OA.ChennaiSite.NET_GAIN",token);
$scope.getValueLatest("WinCC_OA.ChennaiSite.AG_DC_RATIO",token);
$scope.getValueLatest("WinCC_OA.ChennaiSite.AG_SG_RATIO",token);
$scope.getValueLatest("WinCC_OA.ChennaiSite.DEVIATION",token);
$scope.getValueLatest("WinCC_OA.ChennaiSite.UI_RATE",token);
$scope.getValueLatest("WinCC_OA.ChennaiSite.DC",token);
},1000*60*2);




}])
