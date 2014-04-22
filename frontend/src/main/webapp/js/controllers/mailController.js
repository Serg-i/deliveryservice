'use strict';

app.controller('MailCtrl',
    function ($scope, $state, SendLetterREST, NewLetterREST, MailTemplateREST, CheckedContacts) {

        $scope.letter = {
            contactToId:[],
            template: 0,
            subject: '',
            text:''
        };

        $scope.mails = {};

        $scope.toContacts = function () {
            $state.go("contacts", {page: 1});
        };

        $scope.sendMail = function (letter) {
            SendLetterREST.send(letter);
            $state.go("contacts", {page: 1});
        };

        $scope.getTemplate = function (id) {
            MailTemplateREST.getTemplate(id);
        };

        $scope.$on('$viewContentLoaded', function () {
            loadData();
        });

        var loadData = function() {
            $scope.letter.contactToId = CheckedContacts.ids;
            $scope.letter.template = 0;
            var mailArr = [];

            NewLetterREST.getMails($scope.letter.contactToId).$promise.then(
                function (result) {
                    mailArr = result;
                    createMailList(mailArr.mails);
            });
        };

        var createMailList = function(mails) {
         var mailList = '';

         for(var i = 0; i< mails.length; i++ ){
             mailList+= mails[i]+', ';
         }
            $scope.mails = mailList ;
        };
    });