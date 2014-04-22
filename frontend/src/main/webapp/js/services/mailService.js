'use strict';

app.factory("SendLetterREST", function($resource) {
    return $resource("/backend/api/email/send", {}, {
        send: {
            method:'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        }
    });
});

app.factory("NewLetterREST", function($resource) {
    return $resource("/backend/api/email/new", {}, {
        getMails: {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        }
    });
});

app.factory("MailTemplateREST", function($resource) {
    return $resource("/backend/api/email/template/:tempId", {}, {
        getTemplate: {
            method: 'GET',
            params: {tempId: '@tempId'},
            headers: {
                'Content-Type': 'application/json'
            }
        }
    });
});
