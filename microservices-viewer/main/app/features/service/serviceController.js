'use strict';

angular.module('MicroServicesViewer')
    .controller('ServiceController', [
        '$scope',
        'Service',
        '$TreeDnDConvert',
        function ($scope, Service, $TreeDnDConvert) {
            //$scope.treeOptions = {
            //    nodeChildren: "children",
            //    dirSelectable: true,
            //    injectClasses: {
            //        ul: "a1",
            //        li: "a2",
            //        liSelected: "a7",
            //        iExpanded: "a3",
            //        iCollapsed: "a4",
            //        iLeaf: "a5",
            //        label: "a6",
            //        labelSelected: "a8"
            //    }
            //}

            var tree;
            $scope.tree_data = {};
            $scope.my_tree = tree = {};

            $scope.my_tree.addFunction = function (node) {
                console.log(node);
                alert('Function added in Controller "App.js"');
            }

            $scope.expanding_property = {
                field: "Name",
                titleClass: 'text-center',
                cellClass: 'v-middle',
                displayName: 'Name'
            };
            $scope.col_defs = [
                {
                    field: "Name"
                }, {
                    field: "Description",
                    titleStyle: {
                        'width': '80pt'
                    },
                    titleClass: 'text-center',
                    cellClass: 'v-middle text-center',
                    displayName: 'Description',
                    cellTemplate: "<i class=\"fa {{ !node.Description ? 'fa-times text-danger-lter' : 'fa-check text-success' }} text\"></i>"
                }, {
                    displayName: 'Function',
                    cellTemplate: '<button ng-click="tree.addFunction(node)" class="btn btn-default btn-sm">Added Controller!</button>'
                }, {
                    displayName: 'Remove',
                    cellTemplate: '<button ng-click="tree.remove_node(node)" class="btn btn-default btn-sm">Remove</button>'
                }];




            //$scope.showSelected = function (node) {
            //    console.log(node);
            //}

            //$scope.services = Service.getAll();
            $scope.services =[
                {
                    "id": "world",
                    "path": "world/rest",
                    "parentApp": null,
                    "hostName": "127.0.0.1",
                    "status": null,
                    "port": 8081,
                    "instanceId": "1",
                    "endPoints": [
                        {
                            "path": "validate",
                            "method": "POST"
                        },
                        {
                            "path": "stopChildren",
                            "method": "POST"
                        },
                        {
                            "path": "stopAll",
                            "method": "POST"
                        },
                        {
                            "path": "errorOnChild",
                            "method": "POST"
                        }
                    ],
                    "lastUpdate": 1457027465868,
                    "priority": 100.0,
                    "children": [

                        {
                            "id": "eu",
                            "path": "eu/rest",
                            "parentApp": "world",
                            "hostName": "127.0.0.1",
                            "status": null,
                            "port": 8093,
                            "instanceId": "1",
                            "endPoints": [{"path": "validate", "method": "POST"}, {
                                "path": "stopChildren",
                                "method": "POST"
                            }, {"path": "stopAll", "method": "POST"}, {
                                "path": "errorOnChild",
                                "method": "POST"
                            }],
                            "lastUpdate": 1457027750764,
                            "priority": 100.0,
                            "children": [
                                {
                                    "id": "fr",
                                    "path": "fr/rest",
                                    "parentApp": "eu",
                                    "hostName": "127.0.0.1",
                                    "status": null,
                                    "port": 8094,
                                    "instanceId": "1",
                                    "endPoints": [{
                                        "path": "validate",
                                        "method": "POST"
                                    }, {"path": "stopChildren", "method": "POST"}, {
                                        "path": "stopAll",
                                        "method": "POST"
                                    }, {"path": "errorOnChild", "method": "POST"}],
                                    "lastUpdate": 1457027912368,
                                    "priority": 100.0,
                                    "children": []
                                }

                            ]
                        }

                    ]
                }
            ]
            ;
            // DataDemo.getDatas() can see in 'Custom Option' -> Tab 'Data Demo'
            $scope.tree_data = $TreeDnDConvert.tree2tree($scope.services, 'children');

        }]);
