'use strict';

app.controller('AuthCtrl', function ($scope, AuthService, $state) {

    $scope.credentials = {
        username: '',
        password: ''
    };
    $scope.login = function (credentials) {
        AuthService.login(credentials).then(function () {
            $state.go('orders', {page: 1});
        }, function () {
            $state.go('login');
        });
    };
});