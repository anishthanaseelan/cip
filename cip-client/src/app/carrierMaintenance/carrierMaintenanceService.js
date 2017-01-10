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
        .module('cip.carrierMaintenanceService', [])
        .factory('carrierMaintenanceService', carrierMaintenanceService);

    carrierMaintenanceService.$inject = ['$resource', 'JavaConfig', 'linksMapper'];

    /*@ngInject*/
    function carrierMaintenanceService($resource, JavaConfig, linksMapper) {
        var lm = linksMapper.$new();

        var service = $resource(JavaConfig.setupServiceUrl, null,
            {
                searchCarrier: {
                    method: 'GET',
                    url: JavaConfig.setupServiceUrl + '/carrier?offset=:offset&limit=:limit&sortField=:sortField&sortDirection=:sortDirection',
                    interceptor: {
                        response: lm.responseInterceptor,
                        responseError: lm.responseErrorInterceptor
                    },
                    isArray: true
                },

                viewCarrier: {
                    method: 'GET',
                    url: JavaConfig.setupServiceUrl + '/carrier/:carrierId',
                    interceptor: {
                        response: lm.responseInterceptor,
                        responseError: lm.responseErrorInterceptor
                    },
                    isArray: false
                },

                saveCarrierInfo: {
                    method: 'PUT',
                    url: JavaConfig.setupServiceUrl + '/carrier/:carrierId',
                    interceptor: {
                        response: lm.responseInterceptor,
                        responseError: lm.responseErrorInterceptor
                    },
                    isArray: false
                },

                uploadImage: {
                    method: 'PUT',
                    url: JavaConfig.setupServiceUrl + '/image/carrier/:carrierID',
                    interceptor: {
                        response: lm.responseInterceptor,
                        responseError: lm.responseErrorInterceptor
                    },
                    isArray: false
                },

                saveServiceTypeDetails: {
                    method: 'POST',
                    url: JavaConfig.setupServiceUrl + '/serviceRuleDetails/:SCACId',
                    interceptor: {
                        response: lm.responseInterceptor,
                        responseError: lm.responseErrorInterceptor
                    },
                    isArray: false
                },

                viewServiceRulesDetails: {
                    method: 'GET',
                    url: JavaConfig.setupServiceUrl + '/serviceRules/:ServiceRuleName',
                    interceptor: {
                        response: lm.responseInterceptor,
                        responseError: lm.responseErrorInterceptor
                    },
                    isArray: false
                },

                getAllCarriers: {
                    method: 'GET',
                    url: JavaConfig.setupServiceUrl + '/carrier',
                    interceptor: {
                        response: lm.responseInterceptor,
                        responseError: lm.responseErrorInterceptor
                    },
                    isArray: true
                }

            });
        return service;
    }
})();
