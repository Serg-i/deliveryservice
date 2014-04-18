'use strict';

app.factory("ContactREST", function($resource) {
    return $resource("/backend/api/contacts/:id:rpath", {}, {

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
app.factory("ContactsREST", function($resource) {
    return $resource("/backend/api/contacts/p/:page", {}, {
        readAll: {
            method: "GET",
            params: {page: '@page'},
            headers: {
                'Content-Type': 'application/json'
            }
        }
    });
});

app.factory("ContactsSearchREST", function($resource) {
    return $resource("/backend/api/contacts/search/p/:page", {}, {
        search: {
            method:'POST',
            params: {page: '@page'},
            headers: {
                'Content-Type': 'application/json'
            }
        }
    });
});

app.service('ContactSearch', function() {
    return {params: null};
});
