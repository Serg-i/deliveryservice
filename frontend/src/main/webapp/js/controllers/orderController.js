'use strict';

app.controller('OrdersCtrl',  function ($stateParams, $scope, OrderREST, OrdersREST, $state) {

        $scope.toPage = function (topage) {
            $state.go('orders', {page: topage});
        };

        $scope.viewOrder = function (orderId) {
            $state.go('.view', {id: orderId});
        };

        $scope.createOrder = function () {
            $state.go('.new');
        };

        $scope.searchOrder = function () {
            $state.go('search_order');
        };

        $scope.$on('$viewContentLoaded', function () {
            $scope.page = OrdersREST.readAll({
                    page: $stateParams.page
                }, function(data) {
                    $scope.page = data;
                    $scope.curPage = $stateParams.page;
                    $scope.totalItems = $scope.page.count;
                });
        });
    });

app.controller('ViewOrderCtrl', function ($stateParams, $scope, OrderREST, OrderState, OrderStateREST, $state) {

        $scope.deleteOrder = function() {
            OrderREST.delete({id: $stateParams.id});
            $state.go('orders');
        };
        $scope.editOrder = function() {
            $state.go('.edit', {id: $stateParams.id});
        };
        $scope.changeOrderState = function () {
             $scope.stateChange = false;
             var rest = new OrderStateREST($scope.state);
             rest.$updateState({ id: $stateParams.id }, function(data){
                $state.transitionTo($state.current, $stateParams, {
                    reload: true,
                    inherit: false,
                    notify: true
                });
            });
        };
        $scope.$on('$viewContentLoaded', function () {
            $scope.stateChange = false;
            OrderREST.readOne({
                id : $stateParams.id
            }, function(data) {
                $scope.possibleStates = OrderState.possible(data.state);
            	data.name = data.name + " " + data.surname;
            	data.state = OrderState.getlocal(data.state);
            	for (var i = 0; i<data.changes.length; i++)
            		data.changes[i].newState = OrderState.getlocal(data.changes[i].newState);
                $scope.order = data;
                if ($scope.possibleStates.length > 0)
                    $scope.stateChange = true;
            });
        });
    });

app.controller('NewOrderCtrl', function ($scope, ContactREST, OrderREST, UserREST, $state) {

        $scope.saveOrder = function () {
            $state.go('orders');
            var rest = new OrderREST($scope.order);
            rest.$create();
        };
        $scope.$on('$viewContentLoaded', function () {
            $scope.head = "Создать заказ";
            $scope.contacts = ContactREST.getNames();
            $scope.users = UserREST.getForSelect();
        });
    });

app.controller('EditOrderCtrl', function ($stateParams, $scope, ContactREST, OrderREST, UserREST, $state) {

        $scope.saveOrder = function () {
            $state.go('orders.view');
            var rest = new OrderREST($scope.order);
            rest.$update({id : $stateParams.id});
        };
        $scope.$on('$viewContentLoaded', function () {
            $scope.head = "Редактировать заказ";
            $scope.contacts = ContactREST.getNames();
            $scope.users = UserREST.getForSelect();
            OrderREST.readOne({
                id : $stateParams.id
            }, function(data) {
                $scope.order = {};
                $scope.order.id = data.id;
                $scope.order.customerId = data.customerId;
                $scope.order.recipientId = data.recipientId;
                $scope.order.processingManagerId = data.processingManagerId;
                $scope.order.deliveryManagerId = data.deliveryManagerId;
                $scope.order.description = data.description;
                $scope.order.cost = data.cost;
            });
        });
    });

app.controller('SearchOrderCtrl', function ($scope, OrderREST,  $state) {

        // callback for ng-click 'searchOrder':
        $scope.searchOrder = function () {
            //TODO  $scope.orders = OrderREST.search();
            $state.go('orders');
        };
    });