'use strict';

angular.module('MicroServicesViewer')
    .controller('LoginController', [
        '$scope',
        'AuthService',
        function ($scope, AuthService) {

            $scope.authService = AuthService;

        }]);
