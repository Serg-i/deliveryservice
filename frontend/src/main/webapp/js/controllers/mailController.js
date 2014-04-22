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
        $scope.templates = [];

        $scope.toContacts = function () {
            $state.go("contacts", {page: 1});
        };

        $scope.sendMail = function (letter) {
            SendLetterREST.send(letter);
            $state.go("contacts", {page: 1});
        };

        $scope.getTemplate = function (id) {
            MailTemplateREST.getTemplate({tempId: id}).$promise.then(
                function (result) {
                    $scope.letter.text = result.templateText;
                });
        };

        $scope.$on('$viewContentLoaded', function () {
            loadData();
        });

        var loadData = function() {
            $scope.letter.contactToId = CheckedContacts.ids;
            $scope.letter.template = 0;

            var res = null;
            NewLetterREST.getMails($scope.letter.contactToId).$promise.then(
                function (result) {
                    res = result;
                    createTemplateList(res.templateNames);
                    createMailList(res.mails);
            });
        };

        var createMailList = function(mails) {
             var mailList = '';

             for(var i = 0; i< mails.length; i++ ){
                 mailList+= mails[i]+', ';
             }
                $scope.mails = mailList ;
        };
        var createTemplateList = function(tempNames) {
            for(var i = 0; i< tempNames.length; i++ ){
                var template = {
                    name: tempNames[i],
                    id: i
                }
                $scope.templates.push(template);
            }
        };
    });