'use strict';

app.controller('OrdersCtrl',  function ($stateParams, $scope, OrderREST, OrdersREST,
                                        OrderSearch, OrdersSearchREST, OrderState,
                                        $state, Session, USER_ROLES) {

        $scope.toPage = function (toPage) {
            $state.go('orders', {page: toPage});
        };

        $scope.viewOrder = function (orderId) {
            $state.go('.view', {id: orderId});
        };

        $scope.createOrder = function () {
            $state.go('.new');
        };

        $scope.searchOrder = function () {
            $state.go('.search');
        };

        $scope.searchDisable = function () {
            OrderSearch.params = null;
            loadData();
        };

        $scope.$on('$viewContentLoaded', function () {
            loadData();
        });

        var loadData = function() {
            if (OrderSearch.params === null) {
                OrdersREST.readAll({
                    page: $stateParams.page
                }, initTable);
            } else {
                $scope.params = OrderSearch.params;
                var rest = new OrdersSearchREST($scope.params);
                rest.$search({
                    page: $stateParams.page
                }, initTable);
            }
            roleRestriction();
        };

        var initTable = function(data) {
            $scope.page = data;
            $scope.curPage = $stateParams.page;
            $scope.totalItems = $scope.page.count;
            for (var i = 0; i < $scope.page.currentPage.length; i++)
                $scope.page.currentPage[i].state = OrderState.getlocal($scope.page.currentPage[i].state);
        };

        var roleRestriction = function() {
            $scope.newVisible = false;
            if (Session.userRole == USER_ROLES.admin
                || Session.userRole == USER_ROLES.order_manager)
                $scope.newVisible = true;
        }
    });

app.controller('ViewOrderCtrl', function ($stateParams, $scope, $filter, OrderREST, OrderState,
                                          OrderStateREST, $state, Session, USER_ROLES, ORDER_STATE) {

        $scope.deleteOrder = function() {
            OrderREST.delete({
                    id: $stateParams.id
            }, function(data) {
                $state.go('orders');
            }, function(error) {
            });
        };

        $scope.viewContact = function (contactId) {
            $state.go('.contact', {cid: contactId});
        };

        $scope.editOrder = function() {
            $state.go('.edit', {id: $stateParams.id});
        };

        $scope.changeOrderState = function () {
             $scope.stateChange = false;
             var rest = new OrderStateREST($scope.state);
             rest.$updateState({
                 id: $stateParams.id
             }, function(data) {
                if ((Session.userRole == USER_ROLES.courier 
                        && $scope.state.newState == ORDER_STATE.closed)
                    || (Session.userRole == USER_ROLES.processing_manager 
                        && $scope.state.newState == ORDER_STATE.ready))
                    $state.go('orders');
                else
                    loadData();
             }, function(error) {
                 $scope.stateChange = true;
             });
        };

        $scope.$on('$viewContentLoaded', function () {
            loadData();
        });

        var loadData = function() {
            $scope.stateChange = false;
            OrderREST.readOne({
                id : $stateParams.id
            }, function(data) {
                $scope.possibleStates = OrderState.possible(data.state);
                data.customerName = data.customerName + " " + data.customerSurname;
                data.recipientName = data.recipientName + " " + data.recipientSurname;
                data.state = OrderState.getlocal(data.state);
                data.changes = $filter('orderBy')(data.changes, 'date', 'true');
                for (var i = 0; i < data.changes.length; i++)
                    data.changes[i].newState = OrderState.getlocal(data.changes[i].newState);
                $scope.order = data;
                if ($scope.possibleStates.length > 0)
                    $scope.stateChange = true;
            }, function(error) {
                $state.go('orders');
            });
            roleRestriction();
        };

        var roleRestriction = function() {
            $scope.deleteVisible = false;
            $scope.editVisible = false;
            if (Session.userRole == USER_ROLES.admin)
                $scope.deleteVisible = true;
            if (Session.userRole == USER_ROLES.admin
                || Session.userRole == USER_ROLES.supervisor
                || Session.userRole == USER_ROLES.order_manager)
                $scope.editVisible = true;
        }
    });

app.controller('NewOrderCtrl', function ($scope, ContactNamesREST, OrderREST, UserNamesREST, $state) {

        $scope.saveOrder = function () {
            var rest = new OrderREST($scope.order);
            rest.$create({
            }, function(data) {
                $state.go('orders');
            }, function(error) {
            });
        };
        $scope.$on('$viewContentLoaded', function () {
            $scope.head = "Создать заказ";
            ContactNamesREST.getNames({
            }, function(data) {
                $scope.contacts = data;
                UserNamesREST.getForSelect({
                }, function(data) {
                    $scope.users = data;
                }, function(error) {
                    $state.go('orders');
                });
            }, function(error) {
                $state.go('orders');
            });
        });
    });

app.controller('EditOrderCtrl', function ($stateParams, $scope, ContactNamesREST, OrderREST, UserNamesREST, $state) {

        $scope.saveOrder = function () {
            var rest = new OrderREST($scope.order);
            rest.$update({
                id : $stateParams.id
            }, function(data) {
                $state.go('orders.view');
            }, function(error) {
            });
        };
        $scope.$on('$viewContentLoaded', function () {
            $scope.head = "Редактировать заказ";
            ContactNamesREST.getNames({
            }, function(data) {
                //if names received
                $scope.contacts = data;
                UserNamesREST.getForSelect({
                }, function(data) {
                    //if users received
                    $scope.users = data;
                    OrderREST.readOne({
                        id : $stateParams.id
                    }, function(data) {
                        //if order received
                        $scope.order = {};
                        $scope.order.id = data.id;
                        $scope.order.customerId = data.customerId;
                        $scope.order.recipientId = data.recipientId;
                        $scope.order.processingManagerId = data.processingManagerId;
                        $scope.order.deliveryManagerId = data.deliveryManagerId;
                        $scope.order.description = data.description;
                        $scope.order.cost = data.cost;
                    }, function(error) {
                        $state.go('orders');
                    });
                }, function(error) {
                    $state.go('orders');
                });
            }, function(error) {
                $state.go('orders');
            });
         });
    });

app.controller('SearchOrderCtrl', function ($scope, OrderREST, ContactNamesREST, OrderSearch, $state) {
        $scope.searchOrder = function() {
            OrderSearch.params = $scope.search;
            $state.go("orders", {page: 1});
        },
        $scope.$on('$viewContentLoaded', function () {
            $scope.search = {};
            $scope.search.dateOp = "GREATER";
            ContactNamesREST.getNames({
            }, function(data){
                $scope.contacts = data;
                if (OrderSearch.params !== null) {
                    $scope.search = OrderSearch.params;
                }
            }, function(error){
                $state.go('orders');
            });
        });
    });