'use strict';

app.controller('appCtrl', ['$rootScope', 'USER_ROLES', 'AuthService','Session',
    function ($rootScope, USER_ROLES, AuthService, Session) {

        $rootScope.currentUser = {
            name: '',
            role: ''
        };
        Session.userRole= USER_ROLES.guest;
        Session.username= 'GUEST';

        $rootScope.currentUser.name = Session.username;
        $rootScope.currentUser.role = Session.userRole;
    }]);