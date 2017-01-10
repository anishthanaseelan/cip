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
        .module('cip.nodeMaintenance')
        .controller('editNodeController', editNodeController);

    editNodeController.$inject = ['$rootScope', '$scope', '$i18next', '$state', '$stateParams', 'editNodeService', 'alertService'];

    /*@ngInject*/
    function editNodeController($rootScope, $scope, $i18next, $state, $stateParams, editNodeService, alertService) {

        var vm = this;
        var nodeId = $stateParams.nodeId;
        vm.cancel = cancel;
        vm.checkFormValidate = checkFormValidate;
        vm.editNode = editNode;
        vm.getNodeDetails = getNodeDetails;
        vm.logDetails = logDetails;
        vm.selectCountry = selectCountry;
        initialiseVariables();

        function checkFormValidate(form) {
            if (form.$invalid) {
                $scope.accordion.editNodeDetailsOpen = true;
            }
        }

        function initialiseVariables() {
            vm.id = '';
            vm.name = '';
            vm.locationAddressLine1 = '';
            vm.locationAddressLine2 = '';
            vm.locationCity = '';
            vm.locationStateCode = '';
            vm.locationZipcode = '';
            vm.locationCountryCode = '';
            vm.locationPrimaryContactName = '';
            vm.locationPrimaryContactNumber = '';
            vm.locationPrimaryContactEmail = '';
            vm.isActive = false;
            alertService.clear();
        }

        vm.countryData = [
            {code: 'US', name: "United States"}
        ];

        function logDetails() {
            for (var i = 0; i < vm.carrierData.length; i++) {
                var cnt = 0;
                var shipViaData = vm.carrierData[i].carrierServiceInfoList;
                for (var j = 0; j < shipViaData.length; j++) {
                    if (shipViaData[j].active == false) {
                        cnt++;
                    }

                    if (cnt == shipViaData.length) {
                        vm.carrierData[i].isActive = false
                    }
                }
            }
        }

        vm.toggleDetail = function (shipvia, $index) {
            vm.activePosition = vm.activePosition == $index ? -1 : $index;
            vm.activeshipVia = vm.activeshipVia == shipvia ? -1 : shipvia;
        };

        function cancel() {
            initialiseVariables();
            $state.go('searchNode');
        }

        getNodeDetails();

        function getNodeDetails() {
            editNodeService.getNode({nodeId: nodeId}, {})
                .$promise.then(successcallback, displayError);
        }

        function successcallback(response) {
            vm.nodeData = response.data;
            vm.carrierData = response.data.nodeCarrierInfoList;
        }

        function selectCountry(value) {
            vm.nodeData.locationCountryCode = value;
        }

        function editNode() {
            var payload = {
                "nodeCarrierInfoList": vm.carrierData,
                "id": vm.nodeData.id,
                "locationAddressLine1": vm.nodeData.locationAddressLine1,
                "locationAddressLine2": vm.nodeData.locationAddressLine2,
                "locationCity": vm.nodeData.locationCity,
                "locationId": "1",
                "locationPrimaryContactEmail": vm.nodeData.locationPrimaryContactEmail,
                "locationPrimaryContactName": vm.nodeData.locationPrimaryContactName,
                "locationPrimaryContactNumber": vm.nodeData.locationPrimaryContactNumber,
                "locationStateCode": vm.nodeData.locationStateCode,
                "locationZipcode": vm.nodeData.locationZipcode,
                "locationCountryCode": vm.nodeData.locationCountryCode,
                "name": vm.nodeData.name,
                "nodeStatusActive": vm.nodeData.nodeStatusActive,
                "businessUnitId": vm.nodeData.businessUnitId
            };

            console.log(payload);

            editNodeService
                .editNode({nodeId: nodeId}, payload)
                .$promise.then(editNodeSuccess, displayError);
        }

        function editNodeSuccess() {
            initialiseVariables();
            alertService.clear();
            //alertService.add("success", $i18next('Node_details_have_been_updated_successfully_Full!'));
            $state.go('searchNode');
            $rootScope.$broadcast('fetchUpdatednodeList');
        }

        function displayError(reason) {
            alertService.clear();
            if (reason.data && reason.data.status.errors) {
                alertService.add("danger", reason.data.status.errors[0].errorDescription);
            }
            else {
                alertService.add("danger", $i18next('Application_Error_Full'));
            }
        }
    }
})();