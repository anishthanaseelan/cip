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
        .module('cip.carrierMaintenance', [
            'ngResource',
            'cip.carrierMaintenanceService'
        ])
        .value('_', _)
        .config(['$httpProvider', '$stateProvider', '$urlRouterProvider',
            function ($httpProvider, $stateProvider, $urlRouterProvider) {

                $urlRouterProvider.otherwise('/dashBoard');

                delete $httpProvider.defaults.headers.common['X-Requested-With'];
                $httpProvider.defaults.headers.get = {'Content-Type': 'application/json'};

                $stateProvider
                    .state('carrierList', {
                        url: '/carrierList',
                        templateUrl: 'src/app/carrierMaintenance/carrierList.html',
                        controller: 'carrierListController as carrierListCtrl'
                    })
                    .state('editCarrier', {
                        url: '/editCarrier/carrierIDDetails/:CarrierID',
                        templateUrl: 'src/app/carrierMaintenance/editCarrier.html',
                        controller: 'editCarrierController as editCarrierCtrl'
                    })
                    .state('welcome', {
                        url: '/welcome',
                        templateUrl: 'Welcome.html'
                    });
            }]
    );

})();
