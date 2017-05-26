var app = angular.module('app.routes', [])

.config(function($stateProvider, $urlRouterProvider) {

  // Ionic uses AngularUI Router which uses the concept of states
  // Learn more here: https://github.com/angular-ui/ui-router
  // Set up the various states which the app can be in.
  // Each state's controller can be found in controllers.js
  $stateProvider

  .state('jnnsm_rajasthan_plant_1mw', {
    url: '/dashboardPage',
    views: {
    'header_menu': {
    templateUrl: 'ui/views/nav.html',
     controller: 'navCtrl'
    },
    'tree_menu': {
    templateUrl: 'ui/views/tree_menu.html',
     controller: 'navCtrl'
    },
    'page': {
    templateUrl: 'ui/views/jnnsm_rajasthan_plant_1mw.html',
     controller: 'dashboardCtrl'
    },

  }
  })
  .state('ticketdetails', {
    url: '/ticketdetails/:id',
      views: {
      'header_menu': {
      templateUrl: 'ui/views/nav.html',
       controller: 'navCtrl'
      },
      'tree_menu': {
      templateUrl: 'ui/views/tree_menu.html',
       controller: 'navCtrl'
      },
      'page': {
      templateUrl: 'ui/views/ticketDetails.html',
       controller: 'ticketDtlsCtrl'
      },
    }
  })
  .state('login', {
  url: '/login',
views: {
  'noMenu': {
//  templateUrl: 'ui/views/login.html',
 //  controller: 'loginCtrl'
  },

}
})
.state('weather_station', {
  url: '/wstnPage',
  views: {
  'header_menu': {
  templateUrl: 'ui/views/nav.html',
   controller: 'navCtrl'
  },
    'tree_menu': {
    templateUrl: 'ui/views/tree_menu.html',
     controller: 'navCtrl'
    },
  'page': {
  templateUrl: 'ui/views/weather_station.html',
   controller: 'wstnCtrl'
  },

}
})
.state('energy_meter', {
  url: '/emPage',
  views: {
  'header_menu': {
  templateUrl: 'ui/views/nav.html',
   controller: 'navCtrl'
  },
    'tree_menu': {
    templateUrl: 'ui/views/tree_menu.html',
     controller: 'navCtrl'
    },
  'page': {
  templateUrl: 'ui/views/energy_meter.html',
   controller: 'emCtrl'
  },

}
})
.state('transformer', {
  url: '/trPage',
  views: {
  'header_menu': {
  templateUrl: 'ui/views/nav.html',
   controller: 'navCtrl'
  },
    'tree_menu': {
    templateUrl: 'ui/views/tree_menu.html',
     controller: 'navCtrl'
    },
  'page': {
  templateUrl: 'ui/views/transformer.html',
   controller: 'trCtrl'
  },

}
})
.state('inverter', {
  url: '/invPage',
  views: {
  'header_menu': {
  templateUrl: 'ui/views/nav.html',
   controller: 'navCtrl'
  },
    'tree_menu': {
    templateUrl: 'ui/views/tree_menu.html',
     controller: 'navCtrl'
    },
  'page': {
  templateUrl: 'ui/views/inverter.html',
   controller: 'invCtrl'
  },

}
})
.state('smb', {
  url: '/smbPage',
  views: {
  'header_menu': {
  templateUrl: 'ui/views/nav.html',
   controller: 'navCtrl'
  },
    'tree_menu': {
    templateUrl: 'ui/views/tree_menu.html',
     controller: 'navCtrl'
    },
  'page': {
  templateUrl: 'ui/views/smb.html',
   controller: 'smbCtrl'
  },

}
})
.state('surveillance_connect', {
  url: '/dronePage',
  views: {
  'header_menu': {
  templateUrl: 'ui/views/nav.html',
   controller: 'navCtrl'
  },
    'tree_menu': {
    templateUrl: 'ui/views/tree_menu.html',
     controller: 'navCtrl'
    },
  'page': {
  // templateUrl: 'ui/views/surveillance_connect.html',
   controller: 'droneCtrl'
  },

}
})
.state('analytics_connect', {
  url: '/analyticsPage',
  views: {
  'header_menu': {
  templateUrl: 'ui/views/nav.html',
   controller: 'navCtrl'
  },
    'tree_menu': {
    templateUrl: 'ui/views/tree_menu.html',
     controller: 'navCtrl'
    },
  'page': {
   templateUrl: 'ui/views/analytics_connect.html',
   controller: 'analyticsCtrl'
  },

}
})

$urlRouterProvider.otherwise('/dashboardPage')
});
