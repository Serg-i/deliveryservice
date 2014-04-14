'use strict';

app.factory("UserREST", function($resource) {
    return $resource("/backend/api/users/:sub:id", {}, {
        getForSelect: {
            method: "GET",
            params: {id: '', sub: 'names'},
            isArray: false,
            headers: {
                'Content-Type': 'application/json'
            }
        }
    });
});
