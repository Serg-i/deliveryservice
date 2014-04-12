'use strict';

app.controller('appCtrl', ['$scope', 'USER_ROLES', 'AuthService','Session',
    function ($scope, USER_ROLES, AuthService, Session) {

        if(Session.username == null || Session.userRole == null){
            Session.userRole= USER_ROLES.guest;
            Session.username= 'GUEST';
        }
        $scope.currentUser = null;
        $scope.userRoles = USER_ROLES;
        $scope.isAuthorized = AuthService.isAuthorized;
    }]);