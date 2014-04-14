'use strict';

app.controller('AuthCtrl', function ($scope, $rootScope, AuthService, $state) {

    $scope.credentials = {
        username: '',
        password: ''
    };
    $scope.login = function (credentials) {
        AuthService.login(credentials).then(function () {
            $state.go('orders');
            $rootScope.$broadcast( 'newLogin' );
        }, function () {
            $state.go('login');
        });
    };
});