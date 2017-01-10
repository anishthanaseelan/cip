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
    "use strict";
    angular
        .module("cip.commonUtils", [])
        .service("linksMapper", LinksMapperService);

    LinksMapperService.$inject = ["$resource", "$q", "_", "$rootScope"];

    function LinksMapperService($resource, $q, _, $rootScope) {
        var self = this;

        function LinksMapper(options) {
            var self = this;
            self.decorators = {};
            self.data = (options) ? options.data : undefined;
            self.returnFullResponse = undefined;
            self.options = options;
            self.responseWithHeaders = {};

            self.responseInterceptor = function responseInterceptor(response, headers) {
                if (!response.data) {
                    if (response.status === 204) {
                        $rootScope.$broadcast('noRecords');
                    }
                    return response;
                }

                if (self.data && !_.isFunction(self.data) && !response.data[self.data]) {
                    return response;
                }

                function linkToFunctionMap(data) {
                    return _.map(data.links, function (link) {
                        var nestedLm;
                        var relSpecificOptions = getRelOptions(self.options, link);
                        if (relSpecificOptions) {
                            nestedLm = new LinksMapper(relSpecificOptions);
                        } else {
                            nestedLm = new LinksMapper(self.options);
                        }
                        var responseInterceptor = null;
                        if (link.responseInterceptor && link.responseInterceptor != null) {
                            responseInterceptor = link.responseInterceptor;
                        }
                        else {
                            responseInterceptor = nestedLm.responseInterceptor;
                        }
                        var errorInterceptor = null;
                        if (link.responseErrorInterceptor && link.responseErrorInterceptor != null) {
                            errorInterceptor = link.responseErrorInterceptor;
                        }
                        else {
                            errorInterceptor = nestedLm.responseErrorInterceptor;
                        }
                        var customActions = {}, hyperlinkFn, decorator;
                        customActions[link.rel] = {
                            method: link.method,
                            interceptor: {response: responseInterceptor, responseError: errorInterceptor},
                            isArray: (link.rel === "itemDetails" || link.rel === 'members' || link.rel === 'capabilities' || link.rel === 'destinations' || link.isArray) ? true : false
                        };
                        setPassThroughHeadersToActions(customActions, link);

                        decorator = self.decorators[link.rel];
                        hyperlinkFn = $resource(link.href, [], customActions)[link.rel];
                        data[link.rel] = decorator ? decorator(hyperlinkFn) : hyperlinkFn;
                    });
                }

                function setPassThroughHeadersToActions(customActions, link) {
                    /**
                     * Reads the response header value for provided headerName and sets it on custom action link query param
                     */
                    function setHeaderFromResponseToActionParams(headerName) {
                        if (!customActions[link.rel].params) {
                            customActions[link.rel].params = {};
                        }
                        if (response.headers && response.headers(headerName)) {
                            customActions[link.rel].params[headerName.toLowerCase()] = response.headers(headerName);
                        }
                    }
                }

                var data = findLinks(response);
                var response1 = {};
                if (angular.isArray(data)) {
                    _.map(data, function (item) {
                        linkToFunctionMap(item);
                    });
                } else {
                    linkToFunctionMap(data);
                }

                if (self.returnFullResponse) {

                    response1.data = response.data;
                    response1.headers = response.headers();
                    return response1;
                } else {
                    response1.data = data;
                    response1.headers = response.headers();
                    return response1;
                }
            };

            self.responseErrorInterceptor = function responseErrorInterceptor(response) {
                if (response.status === 204) {
                    $rootScope.$broadcast('noRecords');
                }

                return $q.reject(response);
            };

            function findLinks(response) {
                if (_.isFunction(self.data)) {
                    //NOTE: This all works b/c Javascript is pass by reference.  Keeping that in mind,
                    //helps understand how it works and might be used.
                    return self.data(response.data);
                }

                return (self.data) ? response.data[self.data] : response.data;
            }

            function getRelOptions(options, link) {
                /*if(options && options.relOptions && options.relOptions[link.rel]) {
                 return options.relOptions[link.rel];
                 }*/
            }
        }

        self.$new = function (options) {
            return new LinksMapper(options);
        };
    }
})();

/**
 * @ngdoc service
 * @ngModule cip.commonUtils
 * @name localeDetector
 * @author CognizantTechnologySolutions
 * @requires $window
 *
 * @description
 *
 * used for internationalization(i18n) services
 */

(function () {
    "use strict";
    angular
        .module("cip.commonUtils")
        .factory('localeDetector', localeDetector);

    localeDetector.$inject = ['$window'];

    /*@ngInject*/
    function localeDetector($window) {

        function scrubLocale(loc) {
            if (typeof (loc) !== 'undefined' && typeof (loc) === 'string') {
                if (loc !== "en-US") {
                    if (loc.length === 2) {
                        switch (loc) {
                            case "en":
                                loc = "en-US";
                                break;
                            case "es":
                                loc = "es_MX";
                                break;
                            case "fr":
                                loc = "fr_CA";
                                break;
                            case "pt":
                                loc = "pt_BR";
                                break;
                            case "ja":
                                loc = "ja_JP";
                                break;
                            case "zh":
                                loc = "zh_CN";
                                break;
                        }
                    } else {
                        loc = loc.replace(/-/g, "_");
                    }
                }
            } else {
                loc = "";
            }
            return loc;
        }

        function getLocaleInfo() {
            var loc, localeInfo = {};
            var user;
            if (typeof user !== 'undefined' && user.locale !== null) {
            } else {
                loc = $window.navigator.languages ?
                    $window.navigator.languages[0] :
                    ($window.navigator.language || $window.navigator.userLanguage);
            }
            localeInfo.rawLocale = loc;
            localeInfo.scrubbedLocale = scrubLocale(loc);
            return localeInfo;
        }

        return {getLocaleInfo: getLocaleInfo};
    }
})();

/**
 * @ngdoc service
 * @ngModule cip.commonUtils
 * @name capitalize
 * @author CognizantTechnologySolutions
 *
 * @description
 *
 * used to capitalize first letter of each word.
 */

(function () {
    angular.module('cip.commonUtils')
        .filter('capitalize', function () {
            return function (input) {
                if (input.indexOf(' ') !== -1) {
                    var inputPieces, i;

                    input = input.toLowerCase();
                    inputPieces = input.split(' ');

                    for (i = 0; i < inputPieces.length; i++) {
                        inputPieces[i] = capitalizeString(inputPieces[i]);
                    }

                    return inputPieces.toString().replace(/,/g, ' ');
                }
                else {
                    input = input.toLowerCase();
                    return capitalizeString(input);
                }

                function capitalizeString(inputString) {
                    return inputString.substring(0, 1).toUpperCase() + inputString.substring(1);
                }
            };
        });
})();

var appServices = angular.module('cip.commonUtils');

appServices.factory('alertService', [
    '$rootScope', '$timeout', function ($rootScope, $timeout) {
        var alertService;
        //var timeout = 5000;
        $rootScope.alerts = [];
        return alertService = {
            add: function (type, msg) {

                //$timeout(function(){
                //    alertService.closeAlert(this);
                //    //console.log('from time out!');
                //}, 10000);

                return $rootScope.alerts.push({
                    type: type,
                    msg: msg,
                    close: function () {
                        return alertService.closeAlert(this);
                    }
                });

            },
            closeAlert: function (alert) {
                return this.closeAlertIdx($rootScope.alerts.indexOf(alert));
            },
            closeAlertIdx: function (index) {
                return $rootScope.alerts.splice(index, 1);
            },
            clear: function () {
                return $rootScope.alerts = [];
            }
        };
    }
]);


