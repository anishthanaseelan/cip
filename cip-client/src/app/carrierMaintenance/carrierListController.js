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
        .controller('carrierListController', carrierListController);

    carrierListController.$inject = ['carrierMaintenanceService', '$scope', '$state',
        '$i18next', 'alertService'];

    /*@ngInject*/
    function carrierListController(carrierMaintenanceService, $scope, $state, $i18next, alertService) {
        var vm = this;
        var paginationOptions = {
            pageNumber: 1,
            pageSize: 1000,
            sort: null
        };
        vm.carrierDetails = {};
        $scope.saveCarrierStatus = saveCarrierStatus;
        alertService.clear();

        vm.gridOptions = {
            enableCellEdit: false,
            enableColumnResizing: false,
            enableExpandableRowHeader: false,
            enableFiltering: false,
            enableFullRowSelection: true,
            enablePagination: false,
            enablePaginationControls: false,
            enableHorizontalScrollbar: 0,
            enableRowHeaderSelection: false,
            enableRowSelection: false,
            enableVerticalScrollbar: 0,
            expandableRowHeight: 150,
            expandableRowTemplate: 'src/app/templates/expandableRowTemplate.html',
            multiSelect: false,
            paginationPageSizes: [10, 20, 30],
            paginationPageSize: 50,
            rowWidth: "100%",
            useExternalSorting: true,

            columnDefs: [
                {
                    field: 'iconUrl',
                    displayName: $i18next('Carrier_Name_Full'),
                    width: "15%",
                    enableColumnMenu: false,
                    enableSorting: false,
                    cellClass: 'alignLeft',
                    cellTemplate: "<img width=\"30px\" height=\"30px\" ng-src=\"{{grid.getCellValue(row, col)}}\" " +
                    "style=\"position: relative; max-width: 50px; float: left; margin-right: 15px; border-radius: 18%; background-clip: padding-box;\" lazy-src>"
                },
                {
                    field: 'id',
                    displayName: $i18next('Carrier_ID_Full'),
                    cellClass: 'alignLeft',
                    width: "15%",
                    enableColumnMenu: false
                },
                {
                    field: 'description',
                    displayName: $i18next('Description_Full'),
                    width: "30%",
                    enableColumnMenu: false,
                    cellClass: 'alignLeft',
                },
                {
                    field: 'view',
                    displayName: $i18next('view_Full'),
                    width: "15%",
                    enableColumnMenu: false,
                    enableSorting: false,
                    headerCellClass: 'alignCenter',
                    enableFiltering: false,
                    cellTemplate: "<div class=\"ui-grid-cell-contents\">" +
                    "<a><span ng-click=\"grid.api.expandable.toggleRowExpansion(row.entity)\"" +
                    "ng-class=\"{ 'glyphicon glyphicon-chevron-down' : !row.isExpanded, 'glyphicon glyphicon-chevron-up' : row.isExpanded }\"></span></a></div>"
                },
                {
                    field: 'edit',
                    displayName: $i18next('edit_Full'),
                    width: "13%",
                    enableColumnMenu: false,
                    enableSorting: false,
                    enableFiltering: false,
                    headerCellClass: 'alignCenter',
                    cellTemplate: '<div class="ui-grid-cell-contents" ng-click="grid.appScope.editCarrier(row);">' +
                    '<a><span class="glyphicon glyphicon-edit grow" style="cursor: pointer;"></span></a>' +
                    '</div>'
                },
                {
                    field: 'active',
                    displayName: $i18next('active_Full'),
                    width: "13%",
                    enableColumnMenu: false,
                    enableSorting: false,
                    enableFiltering: false,
                    enableCellEdit: false,
                    headerCellClass: 'alignCenter',
                    cellTemplate: '<switch id="carrierStatus" name="carrierStatus" ng-change="grid.appScope.updateCarrierStatus(row)" ng-model="row.entity.active" class="green" size="medium"></switch>'
                }
            ],

            onRegisterApi: function (gridApi) {
                vm.gridApi = gridApi;
                vm.gridApi.core.on.sortChanged($scope, function (grid, sortColumns) {
                    if (sortColumns.length == 0) {
                        paginationOptions.sort = null;
                    } else {
                        paginationOptions.sort = sortColumns[0].sort.direction;
                    }
                    getCarrierList();
                });
                gridApi.pagination.on.paginationChanged($scope, function (newPage, pageSize) {
                    paginationOptions.pageNumber = newPage;
                    paginationOptions.pageSize = pageSize;
                    getCarrierList();
                });

                gridApi.expandable.on.rowExpandedStateChanged($scope, function (row) {
                    if (row.isExpanded) {
                        row.entity.subGridOptions = {};
                        row.entity.subGridOptions.variable = '';

                        alertService.clear();

                        carrierMaintenanceService
                            .viewCarrier({carrierId: row.entity.id}, {})
                            .$promise.then(function (response) {
                                vm.serviceDetails = response.data;
                                row.entity.subGridOptions.data = vm.serviceDetails.carrierServiceInfoList;

                                var carrier = {
                                    name: vm.serviceDetails.locationInfo.primaryContactName,
                                    activeServices: vm.serviceDetails.totalServices,
                                    inactiveServices: vm.serviceDetails.totalInactiveServices,
                                    totalServices: vm.serviceDetails.totalActiveServices,
                                    email: vm.serviceDetails.locationInfo.primaryContactEmail,
                                    telephone: vm.serviceDetails.locationInfo.primaryContactNumber
                                };
                                row.entity.subData = carrier;
                            }, displayError);

                        row.entity.subGridOptions = {
                            enableHorizontalScrollbar: 0,
                            enableVerticalScrollbar: 0,
                            columnDefs: [
                                {
                                    displayName: $i18next('Service_List_Full'),
                                    field: 'name',
                                    width: "23%",
                                    enableColumnMenu: false,
                                    cellClass: 'alignLeft'
                                },
                                {
                                    displayName: $i18next('Service_Type_Full'),
                                    field: 'serviceType',
                                    width: "22%",
                                    enableColumnMenu: false,
                                    cellClass: 'alignLeft'
                                },
                                {
                                    displayName: $i18next('SCAC_Full'),
                                    field: 'scac',
                                    width: "15%",
                                    enableColumnMenu: false,
                                    cellClass: 'alignLeft'
                                },
                                {
                                    displayName: $i18next('Service_Level_Full'),
                                    field: 'serviceLevel',
                                    width: "20%",
                                    enableColumnMenu: false,
                                    cellClass: 'alignLeft'
                                },
                                {
                                    displayName: '     ',
                                    field: 'editField',
                                    width: "10%",
                                    enableColumnMenu: false,
                                    cellTemplate: '<div class="ui-grid-cell-contents" ng-click="grid.appScope.gridSubEditCarrierServiceList(row);">' +
                                    '<span  style="color:blue; cursor: pointer;">Edit</span>' +
                                    '</div>'
                                },
                                {
                                    displayName: '        ',
                                    field: 'active',
                                    width: "10%",
                                    enableColumnMenu: false,
                                    cellTemplate: '<switch id="carrierStatus" name="carrierStatus" ng-change="grid.appScope.updateCarrierSummaryStatus(row)" ng-model="row.entity.active" class="green" size="small"></switch>'
                                }
                            ]
                        };
                    }
                });
            }
        };

        $scope.gridSubEditCarrier = function (row) {
            var carrierServices = [];
            alertService.clear();
            carrierMaintenanceService
                .viewCarrier({carrierId: row.grid.api.grid.parentRow.entity.id}, {})
                .$promise.then(function (response) {
                    vm.serviceListDetails = response.data;
                    _.each(vm.serviceListDetails.carrierServiceInfoList, function (carrierService) {
                        if (carrierService.code === row.entity.code) {
                            carrierService.active = row.entity.active;
                            carrierServices.push(carrierService);
                        } else {
                            carrierServices.push(carrierService);
                        }
                    });
                    vm.serviceListDetails.carrierServiceInfoList = carrierServices;
                    saveCarrierStatus(row.grid.api.grid.parentRow.entity.id, vm.serviceListDetails);
                }, displayError);
        };

        $scope.gridSubEditCarrierServiceList = function (row) {
            alertService.clear();
            carrierMaintenanceService
                .viewCarrier({carrierId: row.grid.api.grid.parentRow.entity.id}, {})
                .$promise.then(function (response) {
                    vm.serviceListDetails = [response.data];
                }, displayError);
            $state.go("editCarrier",
                {
                    CarrierID: row.grid.api.grid.parentRow.entity.id
                });
        };

        var editCarrier = true;
        var payload = {};
        $scope.editCarrier = function (row) {
            alertService.clear();
            carrierMaintenanceService
                .viewCarrier({carrierId: row.entity.id}, {})
                .$promise.then(function (response) {
                    vm.serviceListDetails = [response.data];
                    if (!editCarrier) {
                        payload = response.data;
                        payload.active = row.entity.active;
                        saveCarrierStatus(row.entity.id, payload);
                    }
                }, displayError);

            //execute only for edit carrier
            if (editCarrier) {
                $state.go("editCarrier",
                    {
                        CarrierID: row.entity.id
                    });
            }
        };

        $scope.updateCarrierStatus = function (row) {
            editCarrier = false;
            $scope.editCarrier(row);
        };

        $scope.updateCarrierSummaryStatus = function (row) {
            $scope.gridSubEditCarrier(row);
        };

        function saveCarrierStatus(carrierId, payload) {
            alertService.clear();
            carrierMaintenanceService.saveCarrierInfo({
                carrierId: carrierId
            }, payload).$promise.then(editCarrierSuccess, displayError);
        }

        function editCarrierSuccess() {
            alertService.clear();
            editCarrier = true;
            alertService.add("success", $i18next('Carrier_Status_updated_successfully_Full'));
            $state.go('carrierList');
        }

        getCarrierList();

        function getCarrierList() {
            alertService.clear();
            carrierMaintenanceService.searchCarrier({
                offset: paginationOptions.pageNumber,
                limit: 50,
                sortField: 'scac',
                sortDirection: paginationOptions.sort ? paginationOptions.sort : 'desc'
            }, {})
                .$promise.then(processCarrierList, displayError);
        }

        function processCarrierList(response) {
            vm.gridOptions.totalItems = response.data.length;
            var firstRow = (paginationOptions.pageNumber - 1) * paginationOptions.pageSize;
            vm.gridOptions.data = response.data.slice(firstRow, firstRow + paginationOptions.pageSize);
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


