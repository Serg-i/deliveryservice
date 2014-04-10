'use strict';

var app = angular.module('myApp', [
    'ngRoute',
    'ngResource'
]);
    app.config(['$routeProvider', function($routeProvider) {

        $routeProvider
            .when('/api/login', {templateUrl: 'partials/login.html', label: 'login', controller: 'AuthCtrl'})
            .when('/api/edit_order', {templateUrl: 'partials/order.html', label: 'edit_order' , controller: 'EditOrderCtrl'})
            .when('/api/new_order', {templateUrl: 'partials/order.html', label: 'new_order', controller: 'NewOrderCtrl'})
            .when('/api/orders', {templateUrl: 'partials/orders.html', label: 'orders', controller: 'OrdersCtrl'})
            .when('/api/search_order', {templateUrl: 'partials/order-search.html', label: 'search_order', controller: 'SearchOrderCtrl'})

            .when('/api/users', {templateUrl: 'partials/users.html', label: 'users', controller: 'UsersCtrl'})

            .when('/api/contacts', {templateUrl: 'partials/contacts.html', label: 'contacts', controller: app.ContactsCtrl})

            .otherwise({redirectTo: '/api/login'});
}]);

