'use strict';

var app = angular.module('myApp.backgroundDirective', []);


    app.directive('backImg',function() {
    return function(scope, element, attrs) {
        var url = attrs.backImg;
        element.css({
        'background': 'url(' + url +')' +' no-repeat center center fixed',
        '-webkit-background-size': 'cover',
        '-moz-background-size': 'cover',
        '-o-background-size': 'cover',
        'background-size': 'cover'
        });
    };
  });