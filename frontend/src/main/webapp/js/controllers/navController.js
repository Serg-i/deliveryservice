'use strict';

app.controller('navCtrl', ['$scope','$routeParams', '$location',
    function ($scope, $routeParams, $location) {

    $scope.navClass = function (page) {
        var currentRoute = $location.path().substring(1) || 'api/login';
        return page === currentRoute ? 'active' : '';
    };
}]);