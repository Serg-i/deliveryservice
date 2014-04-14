'use strict';

app.controller('OrdersCtrl',  function ($scope, OrderREST, OrdersREST,  BreadCrumbsService, $state) {

        $scope.editOrder = function (orderId) {
            $state.go('edit_order');
            $scope.order = OrderREST.readOne({ id: orderId });
        };

        $scope.deleteOrder = function (checkedOrders) {
            for (var orderId in checkedOrders) {
                OrderREST.delete({ id: orderId });
            }
            $scope.page = OrdersREST.readAll({ page: 1 });
        };

        $scope.createOrder = function () {
            $state.go('new_order');
            BreadCrumbsService.push( 'home', {href: '#/api/new_order',label: 'Новый заказ'} );
        };

        $scope.searchOrder = function () {
            $state.go('search_order');
            BreadCrumbsService.push( 'home', {href: '#/api/search_order',label: 'Поиск заказа'} );
        };

        $scope.$on('$viewContentLoaded', function () {
            $scope.page = OrdersREST.readAll({ page: 1 });
        });
    });

app.controller('NewOrderCtrl', function ($scope, ContactREST, OrderREST, UserREST, $state) {

        $scope.saveOrder = function () {
            $state.go('orders');
            var rest = new OrderREST($scope.order);
            rest.$create();
        };
        $scope.$on('$viewContentLoaded', function () {
            $scope.contacts = ContactREST.getNames();
            $scope.users = UserREST.getForSelect();
        });
    });

app.controller('EditOrderCtrl', function ($scope, OrderREST,  $state) {

        $scope.saveOrder = function (orderId) {
            OrderREST.update({ id: orderId });
            $state.go('orders');
        };
    });

app.controller('SearchOrderCtrl', function ($scope, OrderREST,  $state) {

        // callback for ng-click 'searchOrder':
        $scope.searchOrder = function () {
            //TODO  $scope.orders = OrderREST.search();
            $state.go('orders');
        };
    });