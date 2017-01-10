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
    angular
        .module('cip.dashBoardService', [])
        .factory('dashBoardService', dashBoardService);

    dashBoardService.$inject = ['$resource', 'JavaConfig'];

    /*@ngInject*/
    function dashBoardService($resource, JavaConfig) {
        var service = $resource(JavaConfig.setupServiceUrl, null,
            {
                getShipmentSummary: {
                    method: 'POST',
                    url: JavaConfig.kpiServiceUrl + '/summary',
                    interceptor: {
                        response: function (response) {
                            return response;
                        },
                        responseError: function (response) {
                            return response;
                        }
                    },
                    isArray: false
                }

            });

        return service;
    }
})();
