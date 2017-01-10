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
        .module('cip.shipmentsService', [])
        .factory('shipmentsService', shipmentsService);

    shipmentsService.$inject = ['$resource', 'JavaConfig', 'linksMapper'];

    function shipmentsService($resource, JavaConfig, linksMapper) {
        var lm = linksMapper.$new();

        var service = $resource(JavaConfig.setupServiceUrl, null,
            {
                getAllShipments: {
                    method: 'GET',
                    url: JavaConfig.kpiServiceUrl + '/shippingUnits?offset=:offset&limit=:limit',
                    interceptor: {
                        response: lm.responseInterceptor,
                        responseError: lm.responseErrorInterceptor
                    },
                    isArray: false
                },

                searchShipments: {
                    method: 'POST',
                    url: JavaConfig.kpiServiceUrl + '/shippingUnits',
                    interceptor: {
                        response: lm.responseInterceptor,
                        responseError: lm.responseErrorInterceptor
                    },
                    isArray: true
                },

                getAllLoads: {
                    method: 'POST',
                    url: JavaConfig.coreServiceUrl + '/load/loadedShipUnits',
                    interceptor: {
                        response: lm.responseInterceptor,
                        responseError: lm.responseErrorInterceptor
                    },
                    isArray: true
                },

                getLoadById: {
                    method: 'GET',
                    url: JavaConfig.coreServiceUrl + '/load/:id',
                    interceptor: {
                        response: lm.responseInterceptor,
                        responseError: lm.responseErrorInterceptor
                    },
                    isArray: false
                },

                getXMLDocumentDetails: {
                    method: 'GET',
                    url: JavaConfig.coreServiceUrl + '/common/:refId',
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
