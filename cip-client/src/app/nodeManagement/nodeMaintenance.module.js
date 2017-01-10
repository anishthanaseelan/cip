/*
 * (C) Copyright 2016 Cognizant Worldwide Limited (fka, CTS UK Ltd) (“CWW”)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Cognizant Worldwide Limited (fka, CTS UK Ltd) (“CWW”)
 */

(function () {
    'use strict';
    angular
        .module('cip.nodeMaintenance',
            [
                'ngResource',
                'cip.nodeMaintenanceService',
                'cip.editNodeService',
                'cip.addNodeService'
            ]
        )
        .value('_', _)
        .constant('toastr', toastr)
        .config(['$httpProvider', '$stateProvider', '$urlRouterProvider',
            function ($httpProvider, $stateProvider, $urlRouterProvider) {

                $urlRouterProvider.otherwise('/dashBoard');

                $stateProvider
                    .state('searchNode', {
                        url: '/searchNode',
                        templateUrl: 'src/app/nodeManagement/nodeMaintenance.html',
                        controller: 'nodeMaintenanceController as nodeMaintCtrl'
                    })
                    .state('addNode', {
                        url: '/addNode',
                        templateUrl: 'src/app/nodeManagement/addNode.html',
                        controller: 'addNodeController as addNodeCtrl'
                    })
                    .state('editNode', {
                        url: '/editNode/:nodeId',
                        templateUrl: 'src/app/nodeManagement/editNode.html',
                        controller: 'editNodeController as editNodeCtrl'
                    });
            }]
        );
})();
