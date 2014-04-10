'use strict';

var app = angular.module('myApp', [
    'ui.router',
    'ngResource'
]);
    app.config(function($stateProvider, $urlRouterProvider) {

        $stateProvider
            .state('login', {
                url: '/api/login',
                templateUrl: 'partials/login.html',
                controller: 'AuthCtrl'
            })
            .state('edit_order', {
                url: '/api/edit_order',
                templateUrl: 'partials/order.html',
                controller: 'EditOrderCtrl'
            })
            .state('new_order', {
                url: '/api/new_order',
                templateUrl: 'partials/order.html',
                controller: 'NewOrderCtrl'
            })
            .state('orders', {
                url: '/api/orders',
                templateUrl: 'partials/orders.html',
                controller: 'OrdersCtrl'
            })
            .state('search_order', {
                url: '/api/search_order',
                templateUrl: 'partials/order-search.html',
                controller: 'SearchOrderCtrl'
            })
            .state('users', {
                url: '/api/users',
                templateUrl: 'partials/users.html',
                controller: 'UsersCtrl'
            })
            .state('contacts', {
                url: '/api/contacts',
                templateUrl: 'partials/contacts.html',
                controller: 'ContactsCtrl'
            })

        $urlRouterProvider.otherwise('/api/login');
    });

