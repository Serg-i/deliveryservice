
angular.module('myApp.navList', []).

    controller('navCtrl', ['$scope','$routeParams', '$location',
    function ($scope, $routeParams, $location) {

    $scope.navClass = function (page) {
        var currentRoute = $location.path().substring(1) || 'login';
        return page === currentRoute ? 'active' : '';
    };
}]);