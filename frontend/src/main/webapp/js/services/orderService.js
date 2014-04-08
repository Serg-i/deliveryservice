'use strict';

/* Services */

angular.module("myApp.orderService", ["ngResource"]).
    factory("OrderREST", function($resource) {
        return $resource("/api/orders/:id", {}, {
            readAll: {
                method: "GET",
                params: {id: ''},
                isArray: true,
                headers: {
                    'Content-Type': 'application/json'
                }
            },
            readOne: {
                method: 'GET',
                params: {id: '@id'},
                headers: {
                    'Content-Type': 'application/json'
                }
            },
            create: {
                method:'POST',
                params: {id: ''},
                headers: {
                'Content-Type': 'application/json'
                }
            },
            update: {
                method:'PUT',
                params: {id: '@id'},
                headers: {
                    'Content-Type': 'application/json'
                }
            },
            delete: {
                method:'DELETE',
                params: {id: '@id'},
                headers: {
                    'Content-Type': 'application/json'
                }
            }
        });
    });
