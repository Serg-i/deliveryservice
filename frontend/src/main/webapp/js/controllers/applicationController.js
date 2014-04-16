'use strict';

app.controller('appCtrl', ['$rootScope', 'USER_ROLES', 'AuthService','Session','BasicAuth',
    function ($scope, USER_ROLES, AuthService, Session, BasicAuth) {

        $scope.currentUser = {
            name: '',
            role: ''
        };

        var convertToRus = function(role) {
            switch (role){
                case USER_ROLES.admin:
                    return "Администратор";
                case USER_ROLES.supervisor:
                    return "Супервизор";
                case USER_ROLES.order_manager:
                    return "Менеджер по приему заказов";
                case USER_ROLES.processing_manager:
                    return "Менеджер по обработке заказов";
                case USER_ROLES.courier:
                    return "Курьер";
                case USER_ROLES.guest:
                    return "Гость";
                default:
                    return "";
            }
            return "";
        }

        var initGuest = function() {
            Session.destroy();
            $scope.currentUser.name = Session.username;
            $scope.currentUser.role = convertToRus(Session.userRole);
            $scope.currentUser.isAuthorized = Session.isAuthorized;

            BasicAuth.setCredentials('','');
        }

        initGuest();

        $scope.$on( 'newLogin', function() {
            $scope.currentUser.name = Session.username;
            $scope.currentUser.role = convertToRus(Session.userRole);
            $scope.currentUser.isAuthorized = Session.isAuthorized;
        } );

        $scope.signOut = function () {
            initGuest();
        };
    }]);