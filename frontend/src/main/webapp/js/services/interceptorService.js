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
                $rootScope.$broadcast('http-error-event', EVENTS.notAuthenticated);
            }
            if (response.status === 403) {
                $rootScope.$broadcast('http-error-event', EVENTS.notAuthorized);
            }
            if (response.status === 404) {
                $rootScope.$broadcast('http-error-event', EVENTS.notFound);
            }
            if (response.status === 500) {
                $rootScope.$broadcast('http-error-event', EVENTS.internalServerError);
            }
            return $q.reject(response);
        }
    };
});