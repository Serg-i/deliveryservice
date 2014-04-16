'use strict';

app.controller('AlertCtrl', function ($scope, EVENTS) {

    $scope.alerts = [];

    var addAlert = function(message) {
        $scope.alerts.push({type: 'danger', msg: message});
    }

    $scope.closeAlert = function(index) {
        $scope.alerts.splice(index, 1);
    };

    $scope.$on( "http-error-event", function(event, message) {
        switch (message){
            case EVENTS.notAuthenticated:
                addAlert('401 - Ошибка авторизации!');
                break;
            case EVENTS.notAuthorized:
                addAlert('403 - Операция запрещена!');
                break;
            case EVENTS.notFound:
                addAlert('403 - Страница не найдена!');
                break;
            case EVENTS.internalServerError:
                addAlert('403 - Внутренняя ошибка сервера!');
                break;
            default:
                addAlert('Неизвестная ошибка!');
        }
    } );
});