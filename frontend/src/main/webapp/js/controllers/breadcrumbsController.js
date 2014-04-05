var app = angular.module('myApp.breadcrumbsControllers', ['myApp.breadcrumbsServices']);

app.controller('breadcrumbsCtrl', ['$scope', '$location','BreadCrumbsService',
    function ($scope, $location, BreadCrumbsService) {
        $scope.pushSomething = function() {

            BreadCrumbsService.push( 'home', {
                href: '/',
                label: 'Home'
            } );
        };
    }]);