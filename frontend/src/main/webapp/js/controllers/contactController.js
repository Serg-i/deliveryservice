'use strict';

app.controller('ContactsCtrl',  function ($scope, ContactREST,  BreadCrumbsService, $state) {

    // callback for ng-click 'editContact':
    $scope.editContact = function (contactId) {
        $state.go('edit_contact');
        $scope.contact = ContactREST.readOne({ id: contactId });
    };

    // callback for ng-click 'deleteContact':
    $scope.deleteContact = function (checkedContacts) {
        for (var contactId in checkedContacts) {
            ContactREST.delete({ id: contactId });
        }
        $scope.contacts = ContactREST.readAll();
    };

    // callback for ng-click 'createContact':
    $scope.createContact = function () {
        $state.go('new_contact');
        BreadCrumbsService.push( 'home', {href: '#/api/new_contact',label: 'Новый контакт'} );
    };

    // callback for ng-click 'searchContact':
    $scope.searchContact = function () {
        $state.go('search_contact');
        BreadCrumbsService.push( 'home', {href: '#/api/search_contact',label: 'Поиск контакта'} );
    };

    $scope.sendEmail = function(checkedOrders){
        // TODO: sendEmail
    };
    $scope.$on('$viewContentLoaded', function () {
        $scope.contacts = ContactREST.readAll();
    });
});

app.controller('NewContactCtrl', function ($scope, ContactREST,  $state) {

    // callback for ng-click 'saveContact':
    $scope.saveContact = function () {
        $state.go('contacts');
        ContactREST.create();
    };
});

app.controller('EditContactCtrl', function ($scope, ContactREST,  $state) {
    $scope.contact = ContactREST.readOne({ id: contactId });
    // callback for ng-click 'saveContact':
    $scope.saveContact = function () {
        ContactREST.update({ id: $scope.contact.id });
        $state.go('contacts');
    };
});

app.controller('SearchContactCtrl', function ($scope, ContactREST,  $state) {

    // callback for ng-click 'searchContact':
    $scope.searchContact = function () {
        //TODO  $scope.contacts = ContactREST.search();
        $state.go('contacts');
        $scope.contacts = ContactREST.readAll();
    };
});

app.controller('SendEmailCtrl', function($scope, ContactREST, $state){
    $scope.sendEmail = function(){
     //TODO: sendEmail function
    };
});