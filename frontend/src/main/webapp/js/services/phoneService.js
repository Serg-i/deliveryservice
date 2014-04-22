'use strict';

app.factory("PhoneREST", function($resource) {
    return $resource("/backend/api/contacts/:cid/phones/:pid", {}, {
        getAll: {
            method:'GET',
            params: {cid: '@cid', pid: ''},
            isArray: true,
            headers: {
                'Content-Type': 'application/json'
            }
        },
        create: {
            method:'POST',
            params: {cid: '@cid', pid: ''},
            headers: {
                'Content-Type': 'application/json'
            }
        },
        update: {
            method:'PUT',
            params: {cid: '@cid', pid: '@pid'},
            headers: {
                'Content-Type': 'application/json'
            }
        },
        delete: {
            method:'DELETE',
            params: {cid: '@cid', pid: '@pid'},
            headers: {
                'Content-Type': 'application/json'
            }
        }
    });
});