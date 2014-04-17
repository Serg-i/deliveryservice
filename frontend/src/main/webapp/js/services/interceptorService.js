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
            switch (response.status){
                case 401:
                    $rootScope.$broadcast('http-error-event', EVENTS.notAuthenticated);
                    break;
                case 403:
                    $rootScope.$broadcast('http-error-event', EVENTS.notAuthorized);
                    break;
                case 404:
                    $rootScope.$broadcast('http-error-event', EVENTS.notFound);
                    break;
                case 500:
                    $rootScope.$broadcast('http-error-event', EVENTS.internalServerError);
                    break;
                default:
                    if (response.status >= 400)
                        $rootScope.$broadcast('http-error-event', EVENTS.clientError);
                    else
                        if (response.status >= 500)
                            $rootScope.$broadcast('http-error-event', EVENTS.serverError);
            }
            return $q.reject(response);
        }
    };
});