'use strict';

var app = angular.module('myApp', [
    'ui.router',
    'ngResource'
]);
    app.config(function($stateProvider, $urlRouterProvider, USER_ROLES) {

        $stateProvider
            .state('login', {
                url: '/api/login',
                templateUrl: 'partials/login.html',
                controller: 'AuthCtrl',
                data: {
                    authorizedRoles: [
                        USER_ROLES.guest,
                        USER_ROLES.admin,
                        USER_ROLES.supervisor,
                        USER_ROLES.order_manager,
                        USER_ROLES.processing_manager,
                        USER_ROLES.courier
                    ]
                }
            })
            .state('edit_order', {
                url: '/api/edit_order',
                templateUrl: 'partials/order.html',
                controller: 'EditOrderCtrl',
                data: {
                    authorizedRoles: [
                        USER_ROLES.admin,
                        USER_ROLES.supervisor,
                        USER_ROLES.order_manager,
                        USER_ROLES.processing_manager,
                        USER_ROLES.courier
                    ]
                }
            })
            .state('new_order', {
                url: '/api/new_order',
                templateUrl: 'partials/order.html',
                controller: 'NewOrderCtrl',
                data: {
                    authorizedRoles: [
                        USER_ROLES.admin,
                        USER_ROLES.supervisor,
                        USER_ROLES.order_manager,
                        USER_ROLES.processing_manager,
                        USER_ROLES.courier
                    ]
                }
            })
            .state('orders', {
                url: '/api/orders',
                templateUrl: 'partials/orders.html',
                controller: 'OrdersCtrl',
                data: {
                    authorizedRoles: [
                        USER_ROLES.admin,
                        USER_ROLES.supervisor,
                        USER_ROLES.order_manager,
                        USER_ROLES.processing_manager,
                        USER_ROLES.courier
                    ]
                }
            })
            .state('search_order', {
                url: '/api/search_order',
                templateUrl: 'partials/order-search.html',
                controller: 'SearchOrderCtrl',
                data: {
                    authorizedRoles: [
                        USER_ROLES.admin,
                        USER_ROLES.supervisor,
                        USER_ROLES.order_manager,
                        USER_ROLES.processing_manager,
                        USER_ROLES.courier
                    ]
                }
            })
            .state('users', {
                url: '/api/users',
                templateUrl: 'partials/users.html',
                controller: 'UsersCtrl',
                data: {
                    authorizedRoles: [
                        USER_ROLES.admin,
                        USER_ROLES.supervisor
                    ]
                }
            })
            .state('contacts', {
                url: '/api/contacts',
                templateUrl: 'partials/contacts.html',
                controller: 'ContactsCtrl',
                data: {
                    authorizedRoles: [
                        USER_ROLES.admin,
                        USER_ROLES.supervisor,
                        USER_ROLES.order_manager
                    ]
                }
            });

        $urlRouterProvider.otherwise('/api/login');
    });
        app.run(function ($rootScope, $state, AUTH_EVENTS, AuthService ) {

            $rootScope.$on('$stateChangeStart', function (event, next) {
                var authorizedRoles = next.data.authorizedRoles;
                if (!AuthService.isAuthorized(authorizedRoles)) {
                    event.preventDefault();
                    if (AuthService.isAuthenticated()) {
                        // user is not allowed
                        alert("No rights!");
                    } else {
                        // user is not logged in
                        $state.go('login');
                    }
                }
            });
        })
    ;

