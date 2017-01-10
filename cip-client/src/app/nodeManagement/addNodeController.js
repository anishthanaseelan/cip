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
        .controller('addNodeController', addNodeController);

    addNodeController.$inject = ['$scope', 'toastr', '$state', '$i18next', 'addNodeService', 'alertService'];

    /*@ngInject*/
    function addNodeController($scope, toastr, $state, $i18next, addNodeService, alertService) {

        var vm = this;
        vm.addCarrierData = [];
        vm.addCarrierData.carrierServiceInfoList = [];
        vm.carrierList = [];
        vm.shipViaDataOrig = [];
        vm.shipViaData = [];
        vm.addNode = addNode;
        vm.cancel = cancel;
        vm.getCarrierDetails = getCarrierDetails;
        vm.genCarrierShipViaJson = genCarrierShipViaJson;
        vm.initialiseVariables = initialiseVariables;
        vm.saveServiceSuccess = saveServiceSuccess;
        vm.setCarrierFalse = setCarrierFalse;
        vm.setShipViaFalse = setShipViaFalse;

        initialiseVariables();

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
            alertService.clear();
        }

        vm.CountryOptions = [{name: "United States", id: 1}];
        vm.selectedCountry = vm.CountryOptions[0];

        vm.selectCountry = function (value) {
            vm.selectedCountry = value;
        };

        function cancel() {
            initialiseVariables();
            $state.go('searchNode');
        }

        vm.toggleDetail = function (shipvia, $index) {
            vm.activePosition = vm.activePosition == $index ? -1 : $index;
            vm.activeshipVia = vm.activeshipVia == shipvia ? -1 : shipvia;
        };

        function saveServiceSuccess() {
            alertService.clear();
            alertService.add("success", $i18next('Service_Management_Details_Updated_Successfully_Full!'));
            vm.toggleDetail();
        }

        getCarrierDetails();

        function getCarrierDetails() {
            addNodeService.getCarrier({}, {})
                .$promise.then(populateShipVias, displayError);

            function populateShipVias(response) {
                vm.carrierData = response.data;
                angular.forEach(vm.carrierData, function (obj) {
                    addNodeService.getShipVia({carrierid: obj.id}, {})
                        .$promise.then(getShipViaDetails, displayError);
                });
            }

            function getShipViaDetails(response) {
                vm.shipViaDataOrig.push(response.data);
                if (vm.shipViaDataOrig.length == vm.carrierData.length) {
                    setShipViaFalse();
                }
            }
        }

        function setCarrierFalse() {
            for (var i = 0; i < vm.carrierData.length; i++) {
                vm.carrierData[i].active = false;
            }
        }

        function setShipViaFalse() {
            setCarrierFalse();
            vm.shipViaData = vm.shipViaDataOrig.reverse();
            for (var i = 0; i < vm.shipViaData.length; i++) {
                vm.shipViaData[i].active = false;
                vm.shipViaData[i].ruleInfos = [];
                for (var j = 0; j < vm.shipViaData[i].carrierServiceInfoList.length; j++) {
                    vm.shipViaData[i].carrierServiceInfoList[j].locationPrimaryContactName = '';
                    vm.shipViaData[i].carrierServiceInfoList[j].locationPrimaryContactNumber = '';
                    vm.shipViaData[i].carrierServiceInfoList[j].locationPrimaryContactEmail = '';
                    vm.shipViaData[i].carrierServiceInfoList[j].meterNumber = '';
                    vm.shipViaData[i].carrierServiceInfoList[j].accountNumber = '';
                    vm.shipViaData[i].carrierServiceInfoList[j].active = false;
                }
            }
        }

        function genCarrierShipViaJson() {
            vm.addNodeTemp = [];
            vm.carrierServiceTemp = [];

            for (var i = 0; i < vm.shipViaData.length; i++) {
                vm.carrierServiceTemp = [];
                vm.carrierSerTemp = vm.shipViaData[i].carrierServiceInfoList;

                for (var j = 0; j < vm.carrierSerTemp.length; j++) {
                    vm.carrierServiceTemp.push({
                        "accountNumber": vm.carrierSerTemp[j].accountNumber,
                        "active": vm.carrierSerTemp[j].active,
                        "carrierServiceId": vm.carrierSerTemp[j].code,
                        "id": vm.id,
                        "locationAddressLine1": vm.locationAddressLine1,
                        "locationAddressLine2": vm.locationAddressLine2,
                        "locationCity": vm.shipViaData[i].locationInfo.city,
                        "locationId": vm.shipViaData[i].locationInfo.id,
                        "locationPrimaryContactEmail": vm.carrierSerTemp[j].locationPrimaryContactEmail,
                        "locationPrimaryContactName": vm.carrierSerTemp[j].locationPrimaryContactName,
                        "locationPrimaryContactNumber": vm.carrierSerTemp[j].locationPrimaryContactNumber,
                        "locationStateCode": vm.shipViaData[i].locationInfo.stateCode,
                        "locationZipcode": vm.shipViaData[i].locationInfo.zipCode,
                        "meterNumber": vm.carrierSerTemp[j].meterNumber,
                        "name": vm.carrierSerTemp[j].name
                    });
                }

                vm.addNodeTemp.push({
                    "carrierName": vm.shipViaData[i].name,
                    "isActive": vm.shipViaData[i].active,
                    "iconUrl": vm.shipViaData[i].iconUrl,
                    "id": vm.shipViaData[i].id,
                    "active": vm.shipViaData[i].active,
                    "carrierServiceInfoList": vm.carrierServiceTemp
                });
            }

            var input = {
                "nodeCarrierInfoList": vm.addNodeTemp,
                "id": vm.id, "locationAddressLine1": vm.locationAddressLine1,
                "locationAddressLine2": vm.locationAddressLine2,
                "locationCity": vm.locationCity,
                "locationId": "1",
                "locationPrimaryContactEmail": vm.locationPrimaryContactEmail,
                "locationPrimaryContactName": vm.locationPrimaryContactName,
                "locationPrimaryContactNumber": vm.locationPrimaryContactNumber,
                "locationStateCode": vm.locationStateCode,
                "locationZipcode": vm.locationZipcode,
                "name": vm.name,
                "nodeStatusActive": true
            };
        }

        function addNode() {
            genCarrierShipViaJson();
            var payload = {
                "nodeCarrierInfoList": vm.addNodeTemp,
                "id": vm.id,
                "locationAddressLine1": vm.locationAddressLine1,
                "locationAddressLine2": vm.locationAddressLine2,
                "locationCity": vm.locationCity, "locationId": "1",
                "locationPrimaryContactEmail": vm.locationPrimaryContactEmail,
                "locationPrimaryContactName": vm.locationPrimaryContactName,
                "locationPrimaryContactNumber": vm.locationPrimaryContactNumber,
                "locationStateCode": vm.locationStateCode,
                "locationZipcode": vm.locationZipcode,
                "name": vm.name,
                "nodeStatusActive": true,
                //"businessUnitId": "3" //To Do - add a dropdown input and get the value
            };

            addNodeService
                .addNode({nodeid: vm.id}, payload)
                .$promise.then(addNodeSuccess, displayError);

            function addNodeSuccess() {
                alertService.clear();
                alertService.add("success", $i18next('Node_has_been_added_successfully_Full!'));
                $state.go('searchNode');
            }
        }
        
        function displayError(reason) {
            alertService.clear();
            if (reason.data && reason.data.status.errors) {
                alertService.add("danger", reason.data.status.errors[0].errorDescription);
            }
            else {
                alertService.add("danger", $i18next('Application_Error_Full!'));
            }
        }
    }
})();