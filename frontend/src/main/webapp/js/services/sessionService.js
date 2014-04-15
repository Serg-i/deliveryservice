'use strict';

app.service('Session', function () {
    this.create = function (name, role, isAuthorized) {
        this.username = name;
        this.userRole = role;
        this.isAuthorized =isAuthorized;
    };
    this.destroy = function () {
        this.username = 'GUEST';
        this.userRole = 'GUEST';
        this.isAuthorized = 'auth-not-authorized';
    };
    return this;
});