'use strict';

app.controller('AlertCtrl', function ($scope, EVENTS, $timeout) {

    $scope.mark = 0;
    $scope.index = [];
    $scope.alerts = [];

    var addAlert = function(message) {
        $scope.alerts.push({type: 'danger', msg: message});
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
                addAlert('Ошибка авторизации!');
                break;
            case EVENTS.notAuthorized:
                addAlert('Операция запрещена!');
                break;
            case EVENTS.notFound:
                addAlert('Страница не найдена!');
                break;
            case EVENTS.internalServerError:
                addAlert('Внутренняя ошибка сервера!');
                break;
            case EVENTS.serverError:
                addAlert('Ошибка сервера!');
                break;
            case EVENTS.clientError:
                addAlert('Ошибка клиента!');
                break;
            default:
                addAlert('Неизвестная ошибка!');
        }
    } );
});