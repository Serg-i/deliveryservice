'use strict';

app.controller('ContactsCtrl',  function ($stateParams, $scope, ContactREST, ContactsREST,
    ContactSearch, ContactsSearchREST, $state) {

    $scope.toPage = function (toPage) {
        $state.go('contacts', {page: toPage});
    };
    $scope.viewContact = function(contactId){
        $state.go('.view', {id: contactId});
    }
    $scope.createContact = function () {
        $state.go('.new');
    };

    $scope.searchContact = function () {
        $state.go('.search');
    };

    $scope.$on('$viewContentLoaded', function () {
        loadData();
    });

    var loadData = function() {
        if (ContactSearch.params === null) {
            ContactsREST.readAll({
                page: $stateParams.page
            }, initTable);
        } else {
            $scope.params = ContactSearch.params;
            var rest = new ContactsSearchREST($scope.params);
            rest.$search({
                page: $stateParams.page
            }, initTable);
        }
    };

    var initTable = function(data) {
        $scope.page = data;
        $scope.curPage = $stateParams.page;
        $scope.totalItems = $scope.page.count;
    };



app.controller('ViewContactCtrl', function ($stateParams, $scope, ContactREST,
                                          $state, Session, USER_ROLES) {

    $scope.deleteContact = function() {
        ContactREST.delete({
            id: $stateParams.id
        }, function(data) {
            $state.go('contacts');
        }, function(error) {
        });
    };

    $scope.editContact = function() {
        $state.go('.edit', {id: $stateParams.id});
    };



    $scope.$on('$viewContentLoaded', function () {
        loadData();
    });

    var loadData = function() {
        $scope.stateChange = false;
        ContactREST.readOne({
            id : $stateParams.id
        }, function(data) {
           // $scope.possibleStates = OrderState.possible(data.state);
            data.name = data.name + " " + data.surname;

        }, function(error) {
            $state.go('contacts');
        });
    };


});

});