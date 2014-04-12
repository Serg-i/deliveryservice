'use strict';

app.controller('OrdersCtrl',  function ($scope, OrderREST,  BreadCrumbsService, $state) {

        // callback for ng-click 'editOrder':
        $scope.editOrder = function (orderId) {
            $state.go('edit_order');
            $scope.order = OrderREST.readOne({ id: orderId });
        };

        // callback for ng-click 'deleteOrder':
        $scope.deleteOrder = function (checkedOrders) {
            for (var orderId in checkedOrders) {
                OrderREST.delete({ id: orderId });
            }
            $scope.orders = OrderREST.readAll();
        };

        // callback for ng-click 'createOreder':
        $scope.createOrder = function () {
            $state.go('new_order');
            BreadCrumbsService.push( 'home', {href: '#/api/new_order',label: 'Новый заказ'} );
        };

        // callback for ng-click 'searchOrder':
        $scope.searchOrder = function () {
            $state.go('search_order');
            BreadCrumbsService.push( 'home', {href: '#/api/search_order',label: 'Поиск заказа'} );
        };
        $scope.$on('$viewContentLoaded', function () {
            $scope.orders = OrderREST.readAll();
        });
    });

app.controller('NewOrderCtrl', function ($scope, OrderREST,  $state) {

        // callback for ng-click 'saveOrder':
        $scope.saveOrder = function () {
            $state.go('orders');
            OrderREST.create();
        };
    });

app.controller('EditOrderCtrl', function ($scope, OrderREST,  $state) {
        $scope.order = OrderREST.readOne({ id: orderId });
        // callback for ng-click 'saveOrder':
        $scope.saveOrder = function () {
            OrderREST.update({ id: $scope.order.id });
            $state.go('orders');
        };
    });

app.controller('SearchOrderCtrl', function ($scope, OrderREST,  $state) {

        // callback for ng-click 'searchOrder':
        $scope.searchOrder = function () {
            //TODO  $scope.orders = OrderREST.search();
            $state.go('orders');
            $scope.orders = OrderREST.readAll();
        };
    });