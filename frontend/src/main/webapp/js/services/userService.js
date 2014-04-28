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

app.factory("UserNamesREST", function($resource) {
    return $resource("/backend/api/users/names", {}, {
        getForSelect: {
            method: "GET",
            params: {},
            isArray: false,
            headers: {
                'Content-Type': 'application/json'
            }
        }
    });
});
app.factory("UserRole", function(USER_ROLES, USER_LOCAL_ROLES) {
    return {
        getlocal: function (role) {
            switch (role) {
                case USER_ROLES.supervisor:
                    return USER_LOCAL_ROLES.supervisor; break;
                case USER_ROLES.admin:
                    return USER_LOCAL_ROLES.admin; break;
                case USER_ROLES.order_manager:
                    return USER_LOCAL_ROLES.order_manager; break;
                case USER_ROLES.processing_manager:
                    return USER_LOCAL_ROLES.processing_manager; break;
                case USER_ROLES.courier:
                    return USER_LOCAL_ROLES.courier; break;
                case USER_ROLES.guest:
                    return USER_LOCAL_ROLES.guest; break;
            }
            return null;
        }
    };
});