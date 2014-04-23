'use strict';

app.controller('UsersCtrl',  function ($scope,$state,UserREST,UsersREST, Session,USER_ROLES,$stateParams) {

        $scope.toPage = function (toPage) {
            $state.go('users', {page: toPage});
        };


        $scope.deleteUsers=function(usersIds){
            var length=usersIds.length;
            for(var i=0;i<length;++i){
                var currentId=usersIds[i];
                deleteUser(currentId);
            }
            while($scope.usersToDelete.length > 0) {
                $scope.usersToDelete.pop();
            }
        };

        function deleteUser(currentId){
            var deleteFromList = function () {
                var data = $scope.page;
                var usersList = data.currentPage;
                for (var i = 0; i < usersList.length; ++i) {
                    if (usersList[i].id == currentId) {
                        usersList.splice(i, 1);
                        break;
                    }
                }
            };
            UserREST.delete({
                id: currentId
            },deleteFromList)
        }

        $scope.editUser = function(userId){
            $state.go('.edit', {id: userId});
    };

        $scope.viewUser = function (userId) {
            $state.go('.view', {id: userId});
        };

        $scope.createUser = function () {
            $state.go('.new');
        };


        $scope.$on('$viewContentLoaded', function () {
            loadData();
        });

        var loadData = function() {
            $scope.usersToDelete=[];
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
            if (Session.userRole == USER_ROLES.admin){
                $scope.newVisible = true;
            }
        };

    });



app.controller('NewUserCtrl', function ($scope, UserREST,ContactNamesREST,$state,Session,USER_ROLES) {

        $scope.saveUser = function () {
            var rest = new UserREST($scope.user);
            rest.$create({
                }, function(data) {
                $state.go('users');
            }, function(error) {
            });
        };
        $scope.$on('$viewContentLoaded', function () {
            $scope.head = "Создать нового пользователя";
            loadContactNames();
            roleRestriction();
        });

        var loadContactNames=function(){
            ContactNamesREST.getNames({
            }, function(data) {
                $scope.contacts = data;
            }, function(error) {
                $state.go('users');
            });
        }

        var roleRestriction = function() {
            $scope.newVisible = false;
            if (Session.userRole == USER_ROLES.admin){
                $scope.newVisible = true;
            }
        };
    });

app.controller('EditUserCtrl', function ($stateParams, $scope, UserREST, $state,ContactNamesREST,Session,USER_ROLES) {

    $scope.saveUser = function () {
        var rest = new UserREST($scope.user);
        rest.$update({
        }, function(data) {
            $state.go('users');
        }, function(error) {
        });
        $scope.$broadcast('savePhones');
    };

    $scope.$on('$viewContentLoaded', function () {
        $scope.head = "Редактировать данные пользователя";
        loadContactNames();
        loadUserData();
        roleRestriction();
    });

    var loadContactNames=function(){
        ContactNamesREST.getNames({
        }, function(data) {
            $scope.contacts = data;
        }, function(error) {
            $state.go('users');
        });
    }
    var loadUserData = function() {
        UserREST.readOne({
            id : $stateParams.id
        }, function(data) {
            $scope.user = data;
        }, function(error) {
            $state.go('users');
        });
    };

    var roleRestriction = function() {
        $scope.newVisible = false;
        if (Session.userRole == USER_ROLES.admin){
            $scope.newVisible = true;
        }
    };
});