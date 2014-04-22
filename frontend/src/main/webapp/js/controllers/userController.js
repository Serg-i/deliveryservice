'use strict';

app.controller('UsersCtrl',  function ($scope,$state,UserREST,UsersREST, Session,USER_ROLES,$stateParams) {


        $scope.toPage = function (toPage) {
            $state.go('users', {page: toPage});
        };

        $scope.viewUser = function (userId) {
            $state.go('.view', {id: orderId});
        };

        $scope.createUser = function () {
            $state.go('.new');
        };


        $scope.$on('$viewContentLoaded', function () {
            loadData();
        });

        var loadData = function() {
            UsersREST.readAll({
                page: $stateParams.page
                }, initTable);
            roleRestriction();
        };

        var initTable = function(data) {
            $scope.page = data;
            $scope.curPage = $stateParams.page;
            $scope.totalItems = $scope.page.count;
        };

        var roleRestriction = function() {
            $scope.newVisible = false;
            if (Session.userRole == USER_ROLES.admin || Session.userRole == USER_ROLES.supervisor){
                $scope.newVisible = true;
            }
        };

    });

app.controller('ViewUserCtrl', function ($stateParams, $scope, $filter, OrderREST, OrderState,
                                          OrderStateREST, $state, Session,USER_ROLES) {

        $scope.deleteOrder = function() {
            UserREST.delete({
                    id: $stateParams.id
            }, function(data) {
                $state.go('orders');
            }, function(error) {
            });
        };

        $scope.editOrder = function() {
            $state.go('.edit', {id: $stateParams.id});
        };

        $scope.$on('$viewContentLoaded', function () {
            loadData();
        });

        var loadData = function() {
            $scope.stateChange = false;
            UserREST.readOne({
                id : $stateParams.id
            }, function(data) {
                $scope.user = data;
            }, function(error) {
                $state.go('users');
            });
            //roleRestriction();
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



app.controller('NewUserCtrl', function ($scope, UserREST) {

        $scope.saveOrder = function () {
            var rest = new UserREST($scope.user);
            rest.$create({
            }, function(data) {
                $state.go('users');
            }, function(error) {
            });
        };
        $scope.$on('$viewContentLoaded', function () {
            $scope.head = "Создать заказ";
            ContactNamesREST.getNames({
            }, function(data) {
                $scope.contacts = data;
                UserREST.getForSelect({
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