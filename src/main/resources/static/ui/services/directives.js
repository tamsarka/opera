angular.module('app.directives', [])

.directive('blankDirective', [function(){

}])
.directive('nodeTree', function () {
        return {
            template: '<node ng-repeat="node in tree"></node>',
            replace: true,
            restrict: 'E',
            scope: {
                tree: '=children'
            }
        };
    })

.directive("treeModel",function($compile,$state,$rootScope){
    return{
  restrict:"A",
  link:function(a,g,c){
   
    var e=c.treeModel,h=c.nodeLabel||"label",
    d=c.nodeChildren||"children",k='<ul><li data-ng-repeat="node in '+e+'"><i class="collapsed" data-ng-show="node.'+d+'.length && node.collapsed" data-ng-click="selectNodeHead(node, $event)"></i><i class="expanded" data-ng-show="node.'+d+'.length && !node.collapsed" data-ng-click="selectNodeHead(node, $event)"></i><i class="normal" data-ng-hide="node.'+
d+'.length"></i> <span data-ng-class="node.selected" data-ng-click="selectNodeLabel(node, $event)">{{node.'+h+'}}</span><div data-ng-hide="node.collapsed" data-tree-model="node.'+d+'" data-node-id='+(c.nodeId||"id")+" data-node-label="+h+" data-node-children="+d+"></div></li></ul>";e&&e.length&&(c.angularTreeview?(a.$watch(e,function(m,b){g.empty().html($compile(k)(a))},!1),a.selectNodeHead=a.selectNodeHead||function(a,b){b.stopPropagation&&b.stopPropagation();b.preventDefault&&b.preventDefault();b.cancelBubble=
!0;b.returnValue=!1;a.collapsed=!a.collapsed},a.selectNodeLabel=a.selectNodeLabel||function(c,b){b.stopPropagation&&b.stopPropagation();b.preventDefault&&b.preventDefault();b.cancelBubble=!0;b.returnValue=!1;a.currentNode&&a.currentNode.selected&&(a.currentNode.selected=void 0);c.selected="selected";a.currentNode=c;var currentPage=a.currentNode.roleName.toLowerCase();
currentPage = currentPage.split(" ").join("_");
var inverter="inverter";var smb="smb";var analytics="analytics_connect";
if((currentPage.indexOf(inverter) !== -1) || (currentPage.indexOf(smb) !== -1 )) {
    currentPage=currentPage.slice(0,-1);
    console.log($rootScope.analytics_flag+" 1 analytics_flag ");
    if($rootScope.analytics_flag == false){

        $state.go(currentPage); 
      //  $rootScope.getParameters(a.currentNode.roleName);
    }else{
        $rootScope.getParameters(a.currentNode.roleName);
        console.log(' Analytics open New ')
    }
       
}else if(currentPage.indexOf(analytics) !== -1)
{   
    console.log($rootScope.analytics_flag+" 2 analytics_flag ");
    $rootScope.analytics_flag=true; 
   
    $state.go(currentPage);   
}else{
    console.log($rootScope.analytics_flag+" 3 analytics_flag ");
    if( $rootScope.analytics_flag == false){
        $state.go(currentPage); 
    }else{
        $rootScope.getParameters(currentPage);
        console.log(' Analytics open ')
    }
                        
}

}):g.html($compile(k)(a))) }} })
    
.directive('loading', function () {
      return {
        restrict: 'E',
        replace:true,
        template: '<div class="loading" style=" margin-left: 415px;"><img src="http://www.nasa.gov/multimedia/videogallery/ajax-loader.gif" width="20" height="20" />LOADING...</div>',
        link: function (scope, element, attr) {
              scope.$watch('loading', function (val) {
                  if (val)
                      $(element).show();
                  else
                      $(element).hide();
              });
        }
      }
  })
    .directive('node', function ($compile) {
        return {
            restrict: 'E',
            replace: true,
            templateUrl: 'views/node.html', // HTML for a single node.
            link: function (scope, element) {
                /*
                 * Here we are checking that if current node has children then compiling/rendering children.
                 * */
                if (scope.node && scope.node.children && scope.node.children.length > 0) {
                    scope.node.childrenVisibility = true;
                    var childNode = $compile('<ul class="tree" ng-if="!node.childrenVisibility"><node-tree children="node.children"></node-tree></ul>')(scope);
                    element.append(childNode);
                } else {
                    scope.node.childrenVisibility = false;
                }
            },
            controller: ["$scope","$http","$state", function ($scope,$http,$state) {
                // This function is for just toggle the visibility of children
                $scope.toggleVisibility = function (node) {
                    if (node.children) {
                        node.childrenVisibility = !node.childrenVisibility;
                    }
                };
                // Here we are generating popup window for the OPEN button
                 $scope.go = function(asset) {

                  
                    window.confirm(asset);
                    var currentPage=asset.toLowerCase();

                    $state.go(currentPage);
                    };
                // Here We are marking check/un-check all the nodes.
                $scope.checkNode = function (node) {
                    node.checked = !node.checked;
                    function checkChildren(c) {
                        angular.forEach(c.children, function (c) {
                            c.checked = node.checked;
                            checkChildren(c);
                        });
                    }

                    checkChildren(node);
                };
            }]
        };
    });
