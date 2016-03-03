'use strict';

angular.module('MicroServicesViewer').factory('Service',
    [
        '$resource',
        'CONFIG',
        function ($resources, CONFIG) {
            return $resources(CONFIG.URL+'/apps', {},
                {
                    getAll: {method: 'GET', params: {}, isArray: true}
                }
            );
        }]);