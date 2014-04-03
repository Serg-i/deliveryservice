'use strict';

// Declare app level module which depends on filters, and services
angular.module('myApp', [
  'ngRoute',

  'orderControllers',
  'orderService'
]).
config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/login', {templateUrl: 'partials/login.html', controller: 'LoginCtrl'});
        $routeProvider.when('/api/edit_order', {templateUrl: 'partials/order.html', controller: 'EditOrderCtrl'});
        $routeProvider.when('/api/new_order', {templateUrl: 'partials/order.html', controller: 'NewOrderCtrl'});
        $routeProvider.when('/api/orders', {templateUrl: 'partials/orders.html', controller: 'OrdersCtrl'});
        $routeProvider.when('/api/search_order', {templateUrl: 'partials/order-search.html', controller: 'SearchOrderCtrl'});
        $routeProvider.otherwise({redirectTo: '/login'});
}]);
