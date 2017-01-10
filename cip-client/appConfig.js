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
        .module("cip.commonUtils")
        .constant("JavaConfig", {
            setupServiceUrl: "http://40.83.13.183:8600/cip-master/service",
            coreServiceUrl: "http://40.83.13.183:8500/cip-core",
            kpiServiceUrl: "http://40.83.13.183:8700/cip-kpi/kpi",
            shipmentTrackingServiceUrl: "http://40.83.13.183:8500/documents/invoice/"
        });
})();
