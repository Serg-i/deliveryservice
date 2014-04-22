'use strict';

app.controller('PhoneCtrl',  function ($scope, $state, $modal, PhoneREST) {

    var action = [];
    $scope.$on('getPhones', function(e) {
        PhoneREST.getAll({
            cid: $scope.$parent.contact.id
        },function (data) {
            $scope.phones = data;
            for (var i = 0; i < $scope.phones.length; i++) {
                action.push('no');
                $scope.phones[i].checked = false;
                $scope.phones[i].deleted = false;
            }
        });
    });
    $scope.$on('savePhones', function() {
        for (var i = 0; i < $scope.phones.length; i++) {
            var dto = jQuery.extend({}, $scope.phones[i]);
            delete dto.checked;
            delete dto.deleted;
            switch(action[i]) {
                case 'delete':
                        PhoneREST.delete({cid: $scope.$parent.contact.id, pid: $scope.phones[i].id});
                    break;
                case 'update':
                       var rest = new PhoneREST(dto);
                       rest.$update({cid: $scope.$parent.contact.id, pid: $scope.phones[i].id});
                    break;
                case 'create':
                       var rest = new PhoneREST(dto);
                       rest.$create({cid: $scope.$parent.contact.id, pid: $scope.phones[i].id});
                    break;
            }
        }
    });
    $scope.newPhone = function () {
        var modalInstance = $modal.open({
            templateUrl: 'phone-edit-modal.html',
            controller: PhoneModalInstanceCtrl,
            resolve: {
                phone: function () {
                    return null;
                }
            }
        });
        modalInstance.result.then(function (newPhone) {
        newPhone.checked = false;
        newPhone.deleted = false;
            $scope.phones.push(newPhone);
            action.push('create');
        }, function () {
        });
    };
    $scope.editPhone = function (index) {
        var modalInstance = $modal.open({
            templateUrl: 'phone-edit-modal.html',
            controller: PhoneModalInstanceCtrl,
            resolve: {
                phone: function () {
                    return jQuery.extend({}, $scope.phones[index]);
                }
            }
        });
        modalInstance.result.then(function (newPhone) {
            $scope.phones[index] = newPhone;
            if (action[index] == 'no')
                action[index] = 'update';
        }, function () {
        });
    };
    $scope.deletePhones = function() {
        for (var i = 0; i < $scope.phones.length; i++)
            if ($scope.phones[i].checked) {
                if (action[i] == 'no' || action[i] == 'update') {
                    $scope.phones[i].deleted = true;
                    $scope.phones[i].checked = false;
                    action[i] = 'delete';
                }
                else {
                    $scope.phones.splice(i, 1);
                    action.splice(i, 1);
                }
            }
    };
    $scope.toLocalType = function(type) {
    if (type == 'HOME')
         return "Домашний";
    else
         return "Мобильный";
    }
});

var PhoneModalInstanceCtrl =  function ($scope, $modalInstance, phone) {

    if (phone == null) {
        $scope.phone = {};
        $scope.modalTitle = "Новый телефон";
    } else {
        $scope.phone = phone;
        $scope.modalTitle = "Изменить телефон";
    }
    $scope.ok = function () {
        $modalInstance.close($scope.phone);
    };
    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
};
