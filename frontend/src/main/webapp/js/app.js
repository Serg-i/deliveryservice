'use strict';

var app = angular.module('myApp', [
    'ui.router',
    'ngResource',
    'ui.select2',
    'ui.bootstrap',
    'ncy-angular-breadcrumb',
    'ngStorage',
    'checklist-model'
])
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
            .state('orders', {
                url: '/api/orders/{page}',
                templateUrl: 'partials/orders.html',
                controller: 'OrdersCtrl',
                data: {
                    ncyBreadcrumbLabel: 'Заказы',
                    authorizedRoles: [
                        USER_ROLES.admin,
                        USER_ROLES.supervisor,
                        USER_ROLES.order_manager,
                        USER_ROLES.processing_manager,
                        USER_ROLES.courier
                    ]
                }
            })
            .state('orders.view', {
                url: '/view/{id}',
                views: {
                    "@": {
                        templateUrl: 'partials/order-view.html',
                        controller: 'ViewOrderCtrl'
                    }
                },
                data: {
                    ncyBreadcrumbLabel: 'Заказ',
                    authorizedRoles: [
                        USER_ROLES.admin,
                        USER_ROLES.supervisor,
                        USER_ROLES.order_manager,
                        USER_ROLES.processing_manager,
                        USER_ROLES.courier
                    ]
                }
            })
            .state('orders.view.edit', {
                url: '/edit',
                views: {
                     "@": {
                        templateUrl: 'partials/order-edit.html',
                        controller: 'EditOrderCtrl'
                     }
                },
                data: {
                    ncyBreadcrumbLabel: 'Редактировать',
                    authorizedRoles: [
                        USER_ROLES.admin,
                        USER_ROLES.supervisor,
                        USER_ROLES.order_manager,
                        USER_ROLES.processing_manager,
                        USER_ROLES.courier
                    ]
                }
            })
            .state('orders.new', {
                url: '/new',
                views: {
                     "@": {
                        templateUrl: 'partials/order-edit.html',
                        controller: 'NewOrderCtrl'
                     }
                },
                data: {
                    ncyBreadcrumbLabel: 'Новый',
                    authorizedRoles: [
                        USER_ROLES.admin,
                        USER_ROLES.supervisor,
                        USER_ROLES.order_manager,
                        USER_ROLES.processing_manager,
                        USER_ROLES.courier
                    ]
                }
            })
            .state('orders.search', {
                url: '/api/search_order',
                views: {
                    "@": {
                        templateUrl: 'partials/order-search.html',
                        controller: 'SearchOrderCtrl'
                    }
                },
                data: {
                    ncyBreadcrumbLabel: 'Фильтры',
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
                url: '/api/users/{page}',
                templateUrl: 'partials/users.html',
                controller: 'UsersCtrl',
                data: {
                    ncyBreadcrumbLabel: 'Пользователи',
                    authorizedRoles: [
                        USER_ROLES.admin
                    ]
                }
            })
            .state('users.new', {
                url: '/new',
                views: {
                    "@": {
                        templateUrl: 'partials/user-edit.html',
                        controller: 'NewUserCtrl'
                    }
                },
                data: {
                    ncyBreadcrumbLabel: 'Новый',
                    authorizedRoles: [
                        USER_ROLES.admin
                    ]
                }
            })
            .state('users.edit', {
                url: '/edit/{id}',
                views: {
                    "@": {
                        templateUrl: 'partials/user-edit.html',
                        controller: 'EditUserCtrl'
                    }
                },
                data: {
                    ncyBreadcrumbLabel: 'Редактировать',
                    authorizedRoles: [
                        USER_ROLES.admin
                    ]
                }
            })
            .state('contacts', {
                url: '/api/contacts/{page}',
                templateUrl: 'partials/contacts.html',
                controller: 'ContactsCtrl',
                data: {
                    ncyBreadcrumbLabel: 'Контакты',
                    authorizedRoles: [
                        USER_ROLES.admin,
                        USER_ROLES.supervisor,
                        USER_ROLES.order_manager
                    ]
                }
            })
            .state('contacts.view', {
                url: '/view/{id}',
                views: {
                    "@": {
                        templateUrl: 'partials/contact-view.html',
                        controller: 'ViewContactCtrl'
                    }
                },
                data: {
                    ncyBreadcrumbLabel: 'Контакт',
                    authorizedRoles: [
                        USER_ROLES.admin,
                        USER_ROLES.supervisor,
                        USER_ROLES.order_manager
                        ]
                }
            })
            .state('contacts.new', {
                url: '/new',
                views: {
                    "@": {
                        templateUrl: 'partials/contact-edit.html',
                        controller: 'NewContactCtrl'
                    }
                },
                data: {
                    ncyBreadcrumbLabel: 'Новый',
                    authorizedRoles: [
                        USER_ROLES.admin,
                        USER_ROLES.supervisor,
                        USER_ROLES.order_manager
                    ]
                }
            })
            .state('contacts.edit', {
                url: '/edit/{id}',
                views: {
                    "@": {
                        templateUrl: 'partials/contact-edit.html',
                        controller: 'EditContactCtrl'
                    }
                },
                data: {
                    ncyBreadcrumbLabel: 'Редактировать',
                    authorizedRoles: [
                        USER_ROLES.admin,
                        USER_ROLES.supervisor,
                        USER_ROLES.order_manager
                    ]
                }
            })
            .state('contacts.search', {
                url: '/api/search_contact',
                views: {
                    "@": {
                        templateUrl: 'partials/contact-search.html',
                        controller: 'SearchContactCtrl'
                    }
                },
                data: {
                    ncyBreadcrumbLabel: 'Фильтры',
                    authorizedRoles: [
                        USER_ROLES.admin,
                        USER_ROLES.supervisor,
                        USER_ROLES.order_manager
                    ]
                }
            })
            .state('contacts.mail', {
                url: '/api/mail',
                views: {
                    "@": {
                        templateUrl: 'partials/email.html',
                        controller: 'MailCtrl'
                    }
                },
                data: {
                    ncyBreadcrumbLabel: 'Почта',
                    authorizedRoles: [
                        USER_ROLES.admin,
                        USER_ROLES.supervisor
                    ]
                }
            });

        $urlRouterProvider.otherwise('/api/login');
    });
        app.run(function ($rootScope, $state, AuthService ) {

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

