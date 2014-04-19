'use strict';

app.factory('AuthService', function ($http, $resource, $rootScope, Session, BasicAuth, $sessionStorage) {
    return {
        login: function (credentials) {
            BasicAuth.setCredentials(credentials.username, credentials.password);
            return $http({method: 'GET', url: '/backend/api/login'}).success(function(data){
                Session.create( data.name, data.role, 'auth-authorized');
                $sessionStorage.$reset();
                $sessionStorage.username = data.name;
                $sessionStorage.password = credentials.password;
                $sessionStorage.role = data.role;
                $rootScope.$broadcast( 'newLogin' );
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
        },
        init: function() {
            var username = $sessionStorage.username;
            var password = $sessionStorage.password;
            var role = $sessionStorage.role;
            
            if (username != null && password != null && role != null) {
                Session.create( username, role, 'auth-authorized');
                BasicAuth.setCredentials(username, password);
                $rootScope.$broadcast( 'newLogin' );
            } else {
                this.initGuest();
            }
        },
        initGuest: function() {
            Session.destroy();
            BasicAuth.setCredentials('','');
            $sessionStorage.$reset();
            $rootScope.$broadcast( 'newLogin' );
        }
    };
});