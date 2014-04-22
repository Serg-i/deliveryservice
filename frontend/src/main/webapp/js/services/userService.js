'use strict';

app.factory("UserREST", function($resource) {
    return $resource("/backend/api/users/:id", {}, {
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

app.factory("UsersREST", function($resource) {
     return $resource("/backend/api/users/p/:page", {}, {
         readAll: {
             method: "GET",
             params: {page: '@page'},
             headers: {
                 'Content-Type': 'application/json'
             }
         }
     });
 });


