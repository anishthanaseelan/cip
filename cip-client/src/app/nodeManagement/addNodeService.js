/*
 * (C) Copyright 2016 Cognizant Worldwide Limited (fka, CTS UK Ltd) (�CWW�)
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
 *     Cognizant Worldwide Limited (fka, CTS UK Ltd) (�CWW�)
 */

(function () {
    angular
        .module('cip.addNodeService', [])
        .factory('addNodeService', addNodeService);

    addNodeService.$inject = ['$resource', 'JavaConfig', 'linksMapper'];

    /*@ngInject*/
    function addNodeService($resource, JavaConfig, linksMapper) {
        var lm = linksMapper.$new();

        var service = $resource(JavaConfig.setupServiceUrl, null,
            {
                addNode: {
                    method: 'POST',
                    url: JavaConfig.setupServiceUrl + '/node/:nodeid',
                    interceptor: {
                        response: lm.responseInterceptor,
                        responseError: lm.responseErrorInterceptor
                    },
                    isArray: false
                },

                getCarrier: {
                    method: 'GET',
                    url: JavaConfig.setupServiceUrl + '/carrier/',
                    interceptor: {
                        response: lm.responseInterceptor,
                        responseError: lm.responseErrorInterceptor
                    },
                    isArray: true
                },

                getShipVia: {
                    method: 'GET',
                    url: JavaConfig.setupServiceUrl + '/carrier/:carrierid',
                    interceptor: {
                        response: lm.responseInterceptor,
                        responseError: lm.responseErrorInterceptor
                    },
                    isArray: false
                }

            });

        return service;
    }

})();
