'use strict';

app.controller('AlertCtrl', function ($scope, EVENTS, $timeout) {

    $scope.mark = 0;
    $scope.index = [];
    $scope.alerts = [];

    var addAlert = function(type, message) {
        $scope.alerts.push({type: type, msg: message});
        $scope.index.push($scope.mark++);
        var mk = $scope.mark - 1;
        $timeout(function () {
            closeWithMark(mk);
        }, 5000);
    };

    var closeWithMark = function(mark) {
        for (var i = 0; i < $scope.alerts.length; i++)
            if ($scope.index[i] == mark) {
                $scope.alerts.splice(i, 1);
                $scope.index.splice(i, 1);
                break;
            }
    };
    
    $scope.closeAlert = function(index) {
        $scope.alerts.splice(index, 1);
        $scope.index.splice(index, 1);
    };

    $scope.$on( "http-error-event", function(event, message) {
        switch (message){
            case EVENTS.notAuthenticated:
                addAlert('danger','Ошибка авторизации!');
                break;
            case EVENTS.notAuthorized:
                addAlert('danger','Операция запрещена!');
                break;
            case EVENTS.notFound:
                addAlert('danger','Страница не найдена!');
                break;
            case EVENTS.internalServerError:
                addAlert('danger','Внутренняя ошибка сервера!');
                break;
            case EVENTS.serverError:
                addAlert('danger','Ошибка сервера!');
                break;
            case EVENTS.clientError:
                addAlert('danger','Ошибка клиента!');
                break;
            default:
                addAlert('danger','Неизвестная ошибка!');
        }
    } );
    $scope.$on( "logic-error-event", function(event, message) {
        switch (message){
            case EVENTS.notChecked:
                addAlert('warning','Ничего не выбрано!');
                break;
            default:
                addAlert('warning','Логическая ошибка!');
        }
    } );
});