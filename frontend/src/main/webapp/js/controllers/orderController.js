var app = angular.module('myApp.orderControllers', []);

app.controller('OrdersCtrl', ['$scope', 'OrderREST', '$location',
    function ($scope, OrderREST,  $location) {

        // callback for ng-click 'editOrder':
        $scope.editOrder = function (orderId) {
            $scope.order = OrderREST.readOne({ id: orderId });
            $location.path('/api/edit_order/');
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
            $location.path('/api/new_order');
        };

        // callback for ng-click 'searchOrder':
        $scope.searchOrder = function () {
            $location.path('/api/search_order');
        };
        $scope.orders = OrderREST.readAll();
    }]);

app.controller('NewOrderCtrl', ['$scope', 'OrderREST',  '$location',
    function ($scope, OrderREST,  $location) {

        // callback for ng-click 'saveOrder':
        $scope.saveOrder = function () {
            OrderREST.create();
            $location.path('/api/orders');
        };
    }]);

app.controller('EditOrderCtrl', ['$scope', 'OrderREST', '$location',
    function ($scope, OrderREST,  $location) {

        // callback for ng-click 'saveOrder':
        $scope.saveOrder = function () {
            OrderREST.update({ id: $scope.order.id });
            $location.path('/api/orders');
        };
    }]);

app.controller('SearchOrderCtrl', ['$scope', 'OrderREST',   '$location',
    function ($scope, OrderREST,  $location) {

        // callback for ng-click 'searchOrder':
        $scope.searchOrder = function () {
            //TODO  $scope.orders = OrderREST.search();
            $location.path('/api/orders');
        };
    }]);