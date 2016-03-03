'use strict';

angular.module('MicroServicesViewer').config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider
            .when('/service', {
                templateUrl: 'app/features/service/serviceView.html',
                controller: 'ServiceController',
                title: 'GLOBAL.SERVICE.TITLE',
                resolve: {
                    translationPart: ['TranslationService', function (TranslationService) {
                        return TranslationService('service');
                    }]
                }
            }).otherwise({
            redirectTo: '/service'
        });
    }]);