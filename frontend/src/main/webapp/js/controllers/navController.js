'use strict';

app.controller('navBarCtrl', ['$scope', '$location',
    function ($scope, $location) {

    $scope.setActiveClass = function (page) {
        var currentRoute = $location.path().substring(1) || 'api/login';
        return page === currentRoute ? 'active' : '';
    };
}]);