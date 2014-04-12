'use strict';

app.service('Session', function () {
    this.create = function (name, role) {
        this.username = name;
        this.userRole = role;
    };
    this.destroy = function () {
        this.username = null;
        this.userRole = null;
    };
    return this;
});