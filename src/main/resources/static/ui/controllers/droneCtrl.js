angular.module('app.droneCtrl', ['ui.router', 'ngTable'])

.controller('droneCtrl',  function($http,$scope,$rootScope,$interval,$state,$window) {



$interval.cancel($rootScope.dashboardInterval);
$rootScope.flag==true;
$scope.img123=[];
/*if($rootScope.flag==true)
{
  $window.location.reload();
  //$route.reload();
  $rootScope.flag=false;
}*/
var gaugeOptions = {

        chart: {
            type: 'solidgauge'
        },

        title: null,

        pane: {
            center: ['50%', '85%'],
            size: '140%',
            startAngle: -90,
            endAngle: 90,
            background: {
                backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || '#EEE',
                innerRadius: '60%',
                outerRadius: '100%',
                shape: 'arc'
            }
        },

        tooltip: {
            enabled: false
        },

        // the value axis
        yAxis: {
            stops: [
                [0.1, '#DF5353'], //red
                [0.5, '#DDDF0D'], // yellow
                [0.9, '#55BF3B'] // green
            ],
            lineWidth: 0,
            minorTickInterval: null,
            tickAmount: 2,
            title: {
                y: -70
            },
            labels: {
                y: 16
            }
        },

        plotOptions: {
            solidgauge: {
                dataLabels: {
                    y: 5,
                    borderWidth: 0,
                    useHTML: true
                }
            }
        }
    };

    // The speed gauge
    var guageChart = function(id,gaugeOptions,min,max,title,seriesName,dataInarray){
    var chartSpeed = Highcharts.chart(id, Highcharts.merge(gaugeOptions, {
        yAxis: {
            min: min,
            max: max,
            title: {
                text: title
            }
        },

        credits: {
            enabled: false
        },

        series: [{
            name: seriesName,
            data: dataInarray,
            dataLabels: {
                format: '<div style="text-align:center"><span style="font-size:25px;color:' +
                    ((Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black') + '">{y}</span><br/>' +
                       '<span style="font-size:12px;color:silver">'+seriesName+'</span></div>'
            },
            tooltip: {
                valueSuffix: ''
            }
        }]

    }));
  }
  $scope.altimeter = function(value){
  var altimet = new FusionCharts({
        type: 'vbullet',
        renderAt: 'container-altitude',
        width: '100',
        height: '200',
        dataFormat: 'json',
        dataSource: {
            "chart": {
                "lowerLimit": "0",
                "upperLimit": "50",
                "numberPrefix": "",
                "numberSuffix": "Mts",
                "plotFillColor": "#0075c2",
                "targetColor": "#8e0000",
                "showHoverEffect": "1",
                "showBorder": "0",
                "bgColor": "#ffffff",
                "showShadow": "0",
                "colorRangeFillMix": "{light}",
                "chartBottomMargin":"20"
            },
            "colorRange": {
                "color": [
                    {
                        "minValue": "36",
                        "maxValue": "50",
                        "code": "#e44a00",
                        "alpha": "70"
                    },
                    {
                        "minValue": "20",
                        "maxValue": "35",
                        "code": "#f2c500",
                        "alpha": "70"
                    },
                    {
                        "minValue": "0",
                        "maxValue": "19",
                        "code": "#1aaf5d",
                        "alpha": "70"
                    }
                ]
            },
            "value": value,
            "target": "0"
        }
    }).render();
  };
  $scope.altimeter("0");
var aws_access_key_id = 'AKIAIGIEK4WW2EZGZSVA';

var aws_secret_access_key = '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';

var params ={accessKeyId :aws_access_key_id,secretAccessKey :aws_secret_access_key};

var imageArray=[];
var imageArrayH=[];
var dataInarray=[0]
//$scope.btp=localStorage.getItem("btp") || "Disconnected";
guageChart("container-battery",gaugeOptions,0,100,'Health','Battery %',dataInarray);
guageChart("container-speed",gaugeOptions,0,50,'Velocity','KM/H',dataInarray);


var client = new Paho.MQTT.Client("iot.eclipse.org", Number(80), "/ws", "clientId");


// set callback handlers
client.onConnectionLost = onConnectionLost;
client.onMessageArrived = onMessageArrived;

//Save current temperature
var temp = 0.0;
$scope.velocity ="";
//Options object for connection
var connect_options = {

    timeout: 3,
    onSuccess: function () {
        // Connection succeeded; subscribe to our topic
        //console.log('Connected!');
        client.subscribe('dronedatastream_mnm', {qos: 0});
        //console.log("subscribed")
        guageChart("container-battery",gaugeOptions,0,100,'Health','Battery %',dataInarray);
        guageChart("container-speed",gaugeOptions,0,50,'Velocity','KM/H',dataInarray);
    },

    onFailure: function (message) {
      guageChart("container-battery",gaugeOptions,0,100,'Health','Battery %',[0]);
      guageChart("container-speed",gaugeOptions,0,50,'Velocity','KM/H',[0]);
        //alert("Connection failed: " + message.errorMessage);
    }
};
$scope.state="Disconnected";
// connect the client
client.connect(connect_options);

// called when the client loses its connection
function onConnectionLost(responseObject) {
  if (responseObject.errorCode !== 0) {
    //console.log("Connection Lost:"+responseObject.errorMessage);
    $scope.altimeter("0");
    guageChart("container-battery",gaugeOptions,0,100,'Health','Battery %',[0]);
    guageChart("container-speed",gaugeOptions,0,50,'Velocity','KM/H',[0]);
  }
}

$scope.btp=0,
$scope.xvel=0,
$scope.yvel=0,
$scope.zvel=0,
$scope.pitch=0,
$scope.roll=0,
$scope.yaw=0,
$scope.altitudeMeters=0;
$scope.theta=0;
 $scope.psi=0;
  $scope.phi=0;
// called when a message arrives

function onMessageArrived(message) {

  // //console.log(message.payloadString);
//alert(message.payloadString);
    var message = JSON.parse(message.payloadString);




   $scope.altitudeMeters=message.demo.altitudeMeters;
    $scope.btp = message.demo.batteryPercentage;
   $scope.state =message.demo.controlState;
    $scope.xvel =message.demo.xVelocity;
   $scope.yvel =message.demo.yVelocity;
    $scope.zvel =message.demo.zVelocity;
    //cosnole.log($scope.xvel);
    $scope.pitch =message.demo.rotation.pitch;

  $scope.roll =message.demo.rotation.roll;
   $scope.yaw=message.demo.rotation.yaw;
   $scope.theta=message.demo.rotation.theta;
    $scope.psi=message.demo.rotation.psi;
     $scope.phi=message.demo.rotation.phi;

    var btp=$scope.btp;
    var vel2 = Math.pow($scope.yvel, 2) +Math.pow($scope.xvel, 2)+Math.pow($scope.zvel, 2)
    var vel=Math.sqrt(vel2)+1;
    vel=[vel];
    if(vel=="NAN"){
      vel=[0];
    }
    btp=[btp];
   if($scope.btp=="NAN"){
     btp=[0];
   }
   //alert($scope.altitudeMeters);
   $scope.altitudeMeters=$scope.altitudeMeters+0.5;
   $scope.altimeter($scope.altitudeMeters);
   guageChart("container-battery",gaugeOptions,0,100,'Health','Battery %',btp);
   guageChart("container-speed",gaugeOptions,0,50,'Velocity','KM/H',vel);
  //$scope.btp=message.demo.batteryPercentage;
}
$scope.paramsD = {    Bucket: 'droneImageBucket007_mnm' };
$scope.paramH ={    Bucket: 'humanimagebucket007_mnm' };
fetchInterval = $interval(awsFetch, 20*1000);
function awsFetch(){
  //$scope.img123=[];
  if($state.current.name!=='drone'){
$interval.cancel(fetchInterval);
return ;
}



  var s3 = new AWS.S3(params);

  $scope.listBucketdata= function(paramBucket){

  s3.listObjects(paramBucket, function (err, data) {
    if (err) {
        //console.log(err, err.stack);

    } else {
      //  //console.log(data.Contents[0].Key);

      //alert(data.Contents[0].Key);
        var imgUrl='';

        for(var i=$scope.img123.length;i<data.Contents.length;i++){
          if(data.Contents[i].Key.includes("human")){
            imgUrl='https://s3.amazonaws.com/droneImageBucket007_mnm/'+data.Contents[i].Key;
              imgUrl=imgUrl.toString();
              imageArrayH.push(imgUrl);
          }
          else{
            imgUrl='https://s3.amazonaws.com/droneImageBucket007_mnm/'+data.Contents[i].Key;
              imgUrl=imgUrl.toString();

             imageArray.push(imgUrl);
          }


        }

        $scope.img123=imageArray;
        $scope.human123=imageArrayH;
        //$window.location.reload();

    }
$state.go('drone');

  });
}


$scope.listBucketdata($scope.paramsD);
//$scope.listBucketdata($scope.paramH);


}
awsFetch();

//Do this when button is clicked.
// $( "#publish_pb" ).click(function() {
//     temp += Math.random() * (10.0);
//     var msg_str = temp.toFixed(2);
//     message = new Paho.MQTT.Message(msg_str);
//     message.destinationName = "temp/random";
//     client.send(message);
//     //console.log('publishing new value:' + msg_str);
// });

 //Connect Option




})
