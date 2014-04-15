'use strict';

app.controller('appCtrl', ['$rootScope', 'USER_ROLES', 'AuthService','Session','BasicAuth','BreadCrumbsService',
    function ($scope, USER_ROLES, AuthService, Session, BasicAuth, BreadCrumbsService) {

        $scope.currentUser = {
            name: '',
            role: ''
        };

        var initGuest = function() {
            Session.destroy();
            $scope.currentUser.name = Session.username;
            $scope.currentUser.role = Session.userRole;
            $scope.currentUser.isAuthorized = Session.isAuthorized;

            BasicAuth.setCredentials('','');
        }

        initGuest();

        $scope.$on( 'newLogin', function() {
            $scope.currentUser.name = Session.username;
            $scope.currentUser.role = Session.userRole;
            $scope.currentUser.isAuthorized = Session.isAuthorized;
        } );

        $scope.signOut = function () {
            initGuest();
        };
    }]);