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
        .module('cip.carrierMaintenance')
        .controller('editCarrierController', editCarrierController);

    editCarrierController.$inject = ['$scope', '$stateParams', '$state', '$i18next', 'carrierMaintenanceService',
        '_', 'alertService'];

    /*@ngInject*/
    function editCarrierController($scope, $stateParams, $state, $i18next, carrierMaintenanceService, _, alertService) {
        var vm = this;
        vm.carrierId = $stateParams.CarrierID;
        vm.cancelEditedCarrier = cancelEditedCarrier;
        vm.checkFormValidate = checkFormValidate;
        vm.retrieveCarrierDetails = retrieveCarrierDetails;
        vm.saveEditedCarrier = saveEditedCarrier;
        vm.uploadImageFile = uploadImageFile;

        alertService.clear();

        function checkFormValidate(form) {
            if (form.$invalid) {
                $scope.accordion.editCarrierDetailsOpen = true;
            }
        }

        retrieveCarrierDetails();

        function retrieveCarrierDetails() {
            carrierMaintenanceService
                .viewCarrier({carrierId: vm.carrierId}, {})
                .$promise.then(function (response) {
                    vm.serviceListDetails = response.data;
                    vm.gridOptions.data = vm.serviceListDetails.carrierServiceInfoList;
                }, displayError);

            vm.gridOptions = {
                enableColumnResizing: false,
                enableCellEdit: false,
                enableExpandableRowHeader: false,
                enableFiltering: false,
                enableFullRowSelection: true,
                enablePagination: false,
                enablePaginationControls: false,
                enableRowSelection: false,
                enableHorizontalScrollbar: 0,
                enableVerticalScrollbar: 0,
                expandableRowHeight: 250,
                expandableRowTemplate: 'src/app/templates/editServiceListTemplate.html',
                multiSelect: false,
                rowHeight: 50,
                rowWidth: "100%",
                showHeader: false,
                useExternalSorting: true,
                columnDefs: [
                    {
                        field: 'name',
                        displayName: $i18next('Service_List_Full'),
                        width: "50%",
                        enableColumnMenu: false,
                        cellClass: 'editCarrierGridCellDiv'
                    },
                    {
                        field: 'editCarrieListItems',
                        displayName: $i18next('Edit_Carrier_List_Items_Full'),
                        width: "25%",
                        enableColumnMenu: false,
                        enableSorting: false,
                        enableFiltering: false,
                        cellClass: 'editCarrierGridCellDiv',
                        cellTemplate: "<div class=\"ui-grid-cell-contents\">" + "<a ng-click=\"grid.api.expandable.toggleRowExpansion(row.entity)\"" + "ng-class=\"{ 'glyphicon glyphicon-edit' : !row.isExpanded, 'glyphicon carrier-edit' : row.isExpanded }\"></a></div>"
                    },
                    {
                        field: 'active',
                        displayName: $i18next('active_Full'),
                        width: "25%",
                        enableColumnMenu: false,
                        enableSorting: false,
                        enableFiltering: false,
                        cellTemplate: '<switch id="carrierStatus" name="carrierStatus" ng-change="" ng-model="row.entity.active" class="green" size="medium"></switch>'
                    }
                ],

                onRegisterApi: function (gridApi) {
                    vm.gridApi = gridApi;
                    gridApi.expandable.on
                        .rowExpandedStateChanged($scope, function (row) {
                            if (row.isExpanded) {
                                row.entity.subGridOptions = {};
                                var maxWeight, maxWidth, maxLength, maxHeight, scac, serviceCode, locationPrimaryContactName,
                                    locationPrimaryContactNumber, locationPrimaryContactEmail, rules = [];

                                _.each(vm.serviceListDetails.carrierServiceInfoList, function (carrierServiceInfo) {
                                    if (carrierServiceInfo.code === row.entity.code) {
                                        scac = carrierServiceInfo.scac;
                                        serviceCode = carrierServiceInfo.code;
                                        locationPrimaryContactName = carrierServiceInfo.locationPrimaryContactName;
                                        locationPrimaryContactNumber = carrierServiceInfo.locationPrimaryContactNumber;
                                        locationPrimaryContactEmail = carrierServiceInfo.locationPrimaryContactEmail;
                                        rules = carrierServiceInfo.ruleInfos;
                                    }
                                });

                                vm.serviceRules = {
                                    scac: scac,
                                    maxWeight: maxWeight,
                                    maxWidth: maxWidth,
                                    maxLength: maxLength,
                                    maxHeight: maxHeight,
                                    code: serviceCode,
                                    locationPrimaryContactName: locationPrimaryContactName,
                                    locationPrimaryContactNumber: locationPrimaryContactNumber,
                                    locationPrimaryContactEmail: locationPrimaryContactEmail,
                                    ruleDefinitions: rules
                                };
                                row.entity.subGridOptions = {
                                    enableHorizontalScrollbar: 0,
                                    enableVerticalScrollbar: 0,
                                    colDefs: []
                                };
                                row.entity.subData = vm.serviceRules;
                            }
                        }
                    );
                }
            }
        }

        function uploadImageFile() {
            if ($scope.myFile != null) {
                var file = $scope.myFile;
                $scope.uploadedFile = URL.createObjectURL(file);
                var reader = new FileReader();
                reader.readAsDataURL(file);
                reader.onload = function (encodedFile) {
                    var base64EncodedImage = encodedFile.srcElement.result.split(",")[1];
                    console.log(base64EncodedImage);
                    var payloadForImageUpload = {
                        fileName: file.name,
                        base64Content: base64EncodedImage
                    };
                    vm.fileName = file.name;
                    carrierMaintenanceService
                        .uploadImage({carrierID: vm.carrierId}, payloadForImageUpload)
                        .$promise.then(retrieveCarrierDetails, displayError);
                };
            } else {
                alertService.clear();
                alertService.add("danger", $i18next('Select_Image_To_Upload_Full'));
            }
        }

        function saveEditedCarrier(gridSubData) {
            var carrierServices = [];
            var dimensionDetails = gridSubData;
            var ruleInfos = [];
            var rule;
            var ruleDefinition;
            var service;
            if (gridSubData) { //carrier service level Save
                _.each(vm.serviceListDetails.carrierServiceInfoList, function (carrierService) {
                    if (carrierService.code === gridSubData.code) {
                        ruleInfos = gridSubData.ruleDefinitions;
                        service = {
                            active: carrierService.active,
                            code: carrierService.code,
                            id: carrierService.id,
                            name: carrierService.name,
                            ruleInfos: ruleInfos,
                            scac: dimensionDetails.scac,
                            locationPrimaryContactName: dimensionDetails.locationPrimaryContactName,
                            locationPrimaryContactNumber: dimensionDetails.locationPrimaryContactNumber,
                            locationPrimaryContactEmail: dimensionDetails.locationPrimaryContactEmail,
                            serviceLevel: carrierService.serviceLevel,
                            serviceType: carrierService.serviceType
                        };
                        carrierServices.push(service);
                    }
                    else {
                        carrierServices.push(carrierService);
                    }
                });
            }
            else //page level save
            {
                _.each(vm.gridApi.grid.rows[0].grid.rows, function (row) {
                    _.each(row.entity.ruleInfos, function (ruleInfo) {

                        if (row.entity.subData) {
                            rule = row.entity.subData.ruleDefinitions;
                        } //subData
                        else {
                            rule = row.entity.ruleInfos
                        }
                        ruleInfos = rule;
                    }); //ruleInfos
                    service = {
                        active: row.entity.active,
                        code: row.entity.code,
                        id: row.entity.id,
                        name: row.entity.name,
                        ruleInfos: ruleInfos,
                        scac: row.entity.subData ? row.entity.subData.scac : row.entity.scac,
                        locationPrimaryContactName: row.entity.subData ? row.entity.subData.locationPrimaryContactName : row.entity.locationPrimaryContactName,
                        locationPrimaryContactNumber: row.entity.subData ? row.entity.subData.locationPrimaryContactNumber : row.entity.locationPrimaryContactNumber,
                        locationPrimaryContactEmail: row.entity.subData ? row.entity.subData.locationPrimaryContactEmail : row.entity.locationPrimaryContactEmail
                    };

                    carrierServices.push(service); //carrierServiceInfoList
                    ruleInfos = [];

                }); //row
            } //else

            var payload = {
                "carrierServiceInfoList": carrierServices ? carrierServices : vm.serviceListDetails.carrierServiceInfoList,
                "active": vm.serviceListDetails.active,
                "description": vm.serviceListDetails.description,
                "iconUrl": vm.serviceListDetails.iconUrl,
                "id": vm.serviceListDetails.id,
                "locationInfo": {
                    "city": vm.serviceListDetails.locationInfo.city,
                    "countryCode": vm.serviceListDetails.locationInfo.countryCode,
                    "id": vm.serviceListDetails.locationInfo.id,
                    "name": vm.serviceListDetails.locationInfo.name,
                    "primaryContactName": vm.serviceListDetails.locationInfo.primaryContactName,
                    "primaryContactNumber": vm.serviceListDetails.locationInfo.primaryContactNumber,
                    "stateCode": vm.serviceListDetails.locationInfo.stateCode,
                    "zipCode": vm.serviceListDetails.locationInfo.zipCode,
                    "primaryContactEmail": vm.serviceListDetails.locationInfo.primaryContactEmail,
                    addressLine1: vm.serviceListDetails.locationInfo.addressLine1,
                    addressLine2: vm.serviceListDetails.locationInfo.addressLine2
                },
                "name": vm.serviceListDetails.name,
                "scac": vm.serviceListDetails.scac
            };
            carrierMaintenanceService
                .saveCarrierInfo({carrierId: vm.carrierId}, payload)
                .$promise.then(editCarrierSuccess, displayError);
        }

        function editCarrierSuccess() {
            alertService.clear();
            alertService.add("success", $i18next('Carrier_Details_modified_successfully_Full'));
            $state.go('carrierList');
        }

        function displayError(reason) {
            alertService.clear();
            if (reason.data && reason.data.errorCode) {
                _.each(reason.data.errorList, function (error) {
                    alertService.add("danger", error.errorMessage);
                });
            }
            else {
                alertService.add("danger", $i18next('Application_Error_Full'));
            }
        }

        function cancelEditedCarrier() {
            $state.go('carrierList');
        }
    }
})();