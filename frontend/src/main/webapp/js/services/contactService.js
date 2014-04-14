'use strict';

app.factory("ContactREST", function($resource) {
    return $resource("/backend/api/contacts/:sub:id", {}, {
        getNames: {
            method: "GET",
            params: {id: '', sub: 'names'},
            isArray: true,
            headers: {
                'Content-Type': 'application/json'
            }
        }
    });
});
