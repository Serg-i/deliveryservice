'use strict';

/* Services */

angular.module("myApp.orderService", ["ngResource"]).
    factory("OrderREST", function($resource) {
        return $resource("/api/orders/:id", {}, {
            readAll: {method: "GET", params: {id: ''}, isArray: true},
            readOne: {method: 'GET', params: {id: '@id'}},
            create: {method:'POST', params: {id: ''}},
            update: {method:'PUT', params: {id: '@id'}},
            delete: {method:'DELETE', params: {id: '@id'}}
        });
    });
