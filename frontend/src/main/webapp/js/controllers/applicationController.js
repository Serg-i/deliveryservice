'use strict';

app.controller('appCtrl', ['$rootScope', 'USER_ROLES', 'AuthService','Session',
    function ($scope, USER_ROLES, AuthService, Session) {

        $scope.currentUser = {
            name: '',
            role: ''
        };
        Session.userRole= USER_ROLES.guest;
        Session.username= 'GUEST';

        $scope.currentUser.name = Session.username;
        $scope.currentUser.role = Session.userRole;

        $scope.$on( 'newLogin', function() {
            $scope.currentUser.name = Session.username;
            $scope.currentUser.role = Session.userRole;
        } );
    }]);