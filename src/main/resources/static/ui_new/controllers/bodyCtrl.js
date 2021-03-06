angular.module('app.bodyCtrl', ['ui.router', 'ngTable'])


.controller('bodyCtrl',  function($http,$scope,$rootScope,$interval,$state,$location) {


  //alert($state.current.name);
  if($location.path()==='/dashboardPage'){
    $rootScope.img ='';
  }
  else{
    $rootScope.img ='';
  }

  $(function() {
      $('#side-menu').metisMenu();
  });
  $scope.expandMenue = function(){
      var offsetWidth = document.getElementById('menu-wrapper').offsetWidth;
      if (document.getElementById('menu-wrapper').offsetWidth > 50) {
        console.log(' smaller or euqal to 250  && it is', offsetWidth);
        document.getElementById('menu-wrapper').classList.remove('col-md-3');
        document.getElementById('menu-wrapper').classList.remove('col-sm-3');
        document.getElementById('menu-wrapper').classList.remove('col-xs-3');

        document.getElementById('menu-wrapper').style.width = '50px';
        document.getElementById('tree_menu').style.display = 'none';
        document.getElementById('menu-wrapper').style.overflow = 'hidden';
        document.getElementById('page-wrapper').style.marginTop = '-40%';
        document.getElementById('page-wrapper').style.marginLeft = '3%';
      } else {
        console.log(' greater then 250 ');
        console.log(' not smaller or euqal to 250  && it is', offsetWidth);
        document.getElementById('menu-wrapper').classList.add('col-md-3');
        document.getElementById('menu-wrapper').classList.add('col-sm-3');
        document.getElementById('menu-wrapper').classList.add('col-xs-3');

        document.getElementById('page-wrapper').style.marginTop = '0%';
        document.getElementById('tree_menu').style.display = 'block';
        document.getElementById('menu-wrapper').style.width = '250px';
        document.getElementById('menu-wrapper').style.overflowY = 'scroll';
        // var  treeDiv = document.getElementById('menu-wrapper');
        // console.clear();
        // console.log(treeDiv.style);
        // console.clear();
      }
  }
  //Loads the correct sidebar on window load,
  //collapses the sidebar on window resize.
  // Sets the min-height of #page-wrapper to window size
  $(function() {
      document.getElementById('expandMenue').addEventListener('click',$scope.expandMenue);
      document.getElementById('page-wrapper').style.height =  window.innerHeight - 80 + 'px';
      document.getElementById('page-wrapper').style.overflowY = 'scroll';
      document.getElementById('menu-wrapper').style.height =  window.innerHeight - 80 + 'px';
      document.getElementById('menu-wrapper').style.overflowY = 'scroll';
      $(window).bind("load resize", function() {
          var topOffset = 50;
          var width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
          // if (width < 768) {
          //     $('div.navbar-collapse').addClass('collapse');
          //     topOffset = 100; // 2-row-menu
          // } else {
          //     $('div.navbar-collapse').removeClass('collapse');
          // } 
          document.getElementById('page-wrapper').style.height =  window.innerHeight - 90 + 'px';
          document.getElementById('page-wrapper').style.overflowY = 'scroll';
          document.getElementById('menu-wrapper').style.height =  window.innerHeight - 90 + 'px';
          document.getElementById('menu-wrapper').style.overflowY = 'scroll';
          if (this.window.innerWidth < 500) {
            console.log(' smaller then 250 ');
            document.getElementById('menu-wrapper').style.width = '50px';
            document.getElementById('tree_menu').style.display = 'none';
            document.getElementById('menu-wrapper').style.overflow = 'hidden';
          } else {
            console.log(' greater then 250 ');
            document.getElementById('tree_menu').style.display = 'block';
            document.getElementById('menu-wrapper').style.width = '250px';
            document.getElementById('menu-wrapper').style.overflowY = 'scroll';
          } 
          var height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
          height = height - topOffset;
          if (height < 1) height = 1;
          if (height > topOffset) {
              $("#page-wrapper").css("min-height", (height) + "px");
          }
      });

      var url = window.location;
      // var element = $('ul.nav a').filter(function() {
      //     return this.href == url;
      // }).addClass('active').parent().parent().addClass('in').parent();
      var element = $('ul.nav a').filter(function() {
          return this.href == url;
      }).addClass('').parent();

      while (true) {
          if (element.is('li')) {
              element = element.parent().addClass('in').parent();
          } else {
              break;
          }
      }
  });


})