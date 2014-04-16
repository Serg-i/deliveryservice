'use strict';

app.constant('EVENTS', {
    notFound: 'not-found',
    internalServerError: 'internal-server-error',
    loginSuccess: 'auth-login-success',
    loginFailed: 'auth-login-failed',
    logoutSuccess: 'auth-logout-success',
    sessionTimeout: 'auth-session-timeout',
    notAuthenticated: 'auth-not-authenticated',
    notAuthorized: 'auth-not-authorized',
    isAuthorized: 'auth-authorized'
});