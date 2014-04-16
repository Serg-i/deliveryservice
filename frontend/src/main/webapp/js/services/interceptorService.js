'use strict';

app.config(function ($httpProvider) {
    $httpProvider.interceptors.push([
        '$injector',
        function ($injector) {
            return $injector.get('ErrorInterceptor');
        }
    ]);
});
app.factory('ErrorInterceptor', function ($rootScope, $q, EVENTS) {
    return {
        responseError: function (response) {
            if (response.status === 401) {
                $rootScope.$broadcast(EVENTS.notAuthenticated,
                    response);
            }
            if (response.status === 403) {
                $rootScope.$broadcast(EVENTS.notAuthorized,
                    response);
            }
            if (response.status === 404) {
                $rootScope.$broadcast(EVENTS.notFound,
                    response);
            }
            if (response.status === 500) {
                $rootScope.$broadcast(EVENTS.internalServerError,
                    response);
            }
            return $q.reject(response);
        }
    };
});