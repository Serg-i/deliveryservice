'use strict';

// Declare app level module which depends on filters, and services
var app = angular.module('myApp', [
  'ngRoute',
  'myApp.directives',
  'myApp.navList',
  'myApp.orderControllers',
  'myApp.orderService',
  'myApp.userControllers',
  'myApp.contactControllers'
]).
config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/login', {templateUrl: 'partials/login.html', label: 'login', controller: 'LoginCtrl'});

        $routeProvider.when('/api/edit_order', {templateUrl: 'partials/order.html', label: 'edit_order' , controller: 'EditOrderCtrl'});
        $routeProvider.when('/api/new_order', {templateUrl: 'partials/order.html', label: 'new_order', controller: 'NewOrderCtrl'});
        $routeProvider.when('/api/orders', {templateUrl: 'partials/orders.html', label: 'orders', controller: 'OrdersCtrl'});
        $routeProvider.when('/api/search_order', {templateUrl: 'partials/order-search.html', label: 'search_order', controller: 'SearchOrderCtrl'});

        $routeProvider.when('/api/users', {templateUrl: 'partials/users.html', label: 'users', controller: 'UsersCtrl'});

        $routeProvider.when('/api/contacts', {templateUrl: 'partials/contacts.html', label: 'contacts', controller: 'ContactsCtrl'});

        $routeProvider.otherwise({redirectTo: '/login'});
}]);
