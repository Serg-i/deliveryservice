'use strict';

/* Services */

angular.module('myApp.breadcrumbsServices', []).
    factory('BreadCrumbsService', function($rootScope, $log) {
    var data = {};
    var ensureIdIsRegistered = function(id) {
        if (angular.isUndefined(data[id])) {
            data[id] = [];
        }
    };
    return {
        push: function(id, item) {
            ensureIdIsRegistered(id);
            data[id].push(item);
            $log.log( "$broadcast" );
            $rootScope.$broadcast( 'breadcrumbsRefresh' );
        },
        get: function(id) {
            ensureIdIsRegistered(id);
            return angular.copy(data[id]);
        },
        setLastIndex: function( id, idx ) {
            ensureIdIsRegistered(id);
            if ( data[id].length > 1+idx ) {
                data[id].splice( 1+idx, data[id].length - idx );
            }
        }
    };
})