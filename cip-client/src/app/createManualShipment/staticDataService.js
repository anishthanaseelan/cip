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
    angular.module('cip.shipmentsService')
        .factory('staticDataService', staticDataService);

    function staticDataService() {
        var service = {
            getLoadStatus: getLoadStatus,
            getShipmentStatus: getShipmentStatus
        };

        return service;

        function getLoadStatus() {
            var loadStatusList = [{desc: 'Cancelled'},
                {desc: 'Created'},
                {desc: 'Loading'},
                {desc: 'Manifested'},
                {desc: 'Partial Manifest'},
                {desc: 'Failed Manifest'}];

            return loadStatusList;
        }

        function getShipmentStatus() {
            var shipmentStatusList = [{desc: 'Cancelled'},
                {desc: 'Confirmed'},
                {desc: 'Exception'},
                {desc: 'Failed'},
                {desc: 'Initiated'},
                {desc: 'Label Created'},
                {desc: 'TrackingNumber Generated'},
                {desc: 'Validated'}
            ];

            return shipmentStatusList;
        }
    }
})();

