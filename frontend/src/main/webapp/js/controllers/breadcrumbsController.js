'use strict';

app.controller('breadcrumbsCtrl', ['$scope', '$location','BreadCrumbsService',
    function ($scope, $location, BreadCrumbsService) {
        $scope.pushSomething = function() {

            BreadCrumbsService.push( 'home', {
                href: '#/api/orders',
                label: 'Home'
            } );
        };
    }]);