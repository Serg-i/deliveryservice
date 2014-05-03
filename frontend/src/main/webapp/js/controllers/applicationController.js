'use strict';

app.controller('appCtrl',
    function ($rootScope, $scope, USER_ROLES, AuthService, Session, BasicAuth,
                           OrderSearch, ContactSearch, UserRole, CheckedContacts) {

        $scope.currentUser = {
            name: '',
            role: ''
        };
        $scope.rusRole = {
            role: ''
        };

        $scope.$on( 'newLogin', function() {
            $scope.currentUser.name = Session.username;
            $scope.currentUser.role = Session.userRole;
            $scope.currentUser.isAuthorized = Session.isAuthorized;
            $scope.rusRole.role = UserRole.getlocal(Session.userRole);
            CheckedContacts.update([]);
            OrderSearch.params = null;
            ContactSearch.params = null;
        } );

        AuthService.init();


        $scope.signOut = function () {
            AuthService.initGuest();
        };
    });