'use strict';

var app = angular.module('myApp.breadcrumbsDirectives', ['myApp.breadcrumbsServices']);


app.directive('breadCrumbs', function($log, BreadCrumbsService) {
    return {
        restrict: 'A',
        template: '<ul class="breadcrumb"><li ng-repeat=\'bc in breadcrumbs\' ng-class="{\'active\': {{$last}} }"><a ng-click="unregisterBreadCrumb( $index )" ng-href="{{bc.href}}">{{bc.label}}</a><span class="divider" ng-show="! $last"></span></li></ul>',
        replace: true,
        compile: function(tElement, tAttrs) {
            return function($scope, $elem, $attr) {
                var bc_id = $attr['id'],
                    resetCrumbs = function() {
                        $scope.breadcrumbs = [];
                        angular.forEach(BreadCrumbsService.get(bc_id), function(v) {
                            $scope.breadcrumbs.push(v);
                        });
                    };
                resetCrumbs();
                $scope.unregisterBreadCrumb = function( index ) {
                    BreadCrumbsService.setLastIndex( bc_id, index );
                    resetCrumbs();
                };
                $scope.$on( 'breadcrumbsRefresh', function() {
                    $log.log( "$on" );
                    resetCrumbs();
                } );
            }
        }
    };

});