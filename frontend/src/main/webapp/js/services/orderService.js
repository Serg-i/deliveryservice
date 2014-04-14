'use strict';

app.factory("OrderREST", function($resource) {
        return $resource("/backend/api/orders/:id", {}, {
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

app.factory("OrdersREST", function($resource) {
    return $resource("/backend/api/orders/p/:page", {}, {
        readAll: {
            method: "GET",
            params: {page: '@page'},
            headers: {
                'Content-Type': 'application/json'
            }
        }
    });
});