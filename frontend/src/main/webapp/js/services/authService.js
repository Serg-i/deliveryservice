'use strict';

app.factory('AuthService', function ($http, $resource, Session, BasicAuth) {
    return {
        login: function (credentials) {

            BasicAuth.setCredentials(credentials.username, credentials.password);
            return $http({method: 'POST', url: '/backend/api/login'}).success(function(data){
                Session.create( data.name, data.role);
            });
        },
        isAuthenticated: function () {
            return !!Session.username;
        },
        isAuthorized: function (authorizedRoles) {
            if (!angular.isArray(authorizedRoles)) {
                authorizedRoles = [authorizedRoles];
            }
            return (this.isAuthenticated() &&
                authorizedRoles.indexOf(Session.userRole) !== -1);
        }
    };
});