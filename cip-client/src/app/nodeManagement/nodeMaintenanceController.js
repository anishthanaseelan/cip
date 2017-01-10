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
    angular.module('cip.nodeMaintenance')
        .controller('nodeMaintenanceController', nodeMaintenanceController);

    nodeMaintenanceController.$inject = ['nodeMaintenanceService', 'uiGridConstants', '$scope', '$state',
        '$i18next', 'editNodeService', 'alertService'];

    /*@ngInject*/
    function nodeMaintenanceController(nodeMaintenanceService, uiGridConstants, $scope, $state,
                                       $i18next, editNodeService, alertService) {

        var vm = this;
        vm.addNode = addNode;
        vm.getNodeList = getNodeList;
        vm.showNodeGrid = false;
        alertService.clear();

        var paginationOptions = {
            pageNumber: 1,
            pageSize: 10,
            sort: null
        };

        $scope.maxSize = 5;

        vm.gridOptions = {
            paginationPageSizes: [10, 20, 30],
            paginationPageSize: 10,
            enableColumnResizing: false,
            enableCellEdit: false,
            rowSelection: 'single',
            data: vm.myData,
            treeRowHeaderAlwaysVisible: true,
            enableRowReordering: true,
            multiSelect: false,
            enableFullRowSelection: true,
            enableFiltering: false,
            useExternalPagination: true,
            enablePaginationControls: false,
            expandableRowTemplate: 'src/app/templates/expandableRowTemplate.html',
            expandableRowHeight: 150,
            enableExpandableRowHeader: false,
            rowHeight: 45,
            enableVerticalScrollbar: uiGridConstants.scrollbars.NEVER,
            enableHorizontalScrollbar: uiGridConstants.scrollbars.NEVER,
            enableRowHeaderSelection: false,
            columnDefs: [
                {
                    field: 'name',
                    displayName: $i18next('Node_Name_Full'),
                    width: '20%',
                    enableColumnMenu: false,
                    enableSorting: true,
                    cellClass: 'alignLeft'
                },
                {
                    field: 'id',
                    displayName: $i18next('Node_ID_Full'),
                    width: '10%',
                    enableColumnMenu: false,
                    enableSorting: true,
                    cellClass: 'alignLeft'
                },
                {
                    field: 'gln',
                    displayName: $i18next('GLN_Full'),
                    width: '20%',
                    enableColumnMenu: false,
                    enableSorting: true,
                    cellClass: 'alignLeft'
                },
                {
                    field: 'locationAddressLine1',
                    displayName: $i18next('Address_Full'),
                    width: '30%',
                    enableColumnMenu: false,
                    cellClass: 'alignLeft',
                    enableSorting: false
                },
                {
                    field: 'edit',
                    displayName: $i18next('edit_Full'),
                    width: '10%',
                    enableColumnMenu: false,
                    enableSorting: false,
                    enableFiltering: false,
                    headerCellClass: 'alignCenter',
                    cellTemplate: '<div class="grow ui-grid-cell-contents" ng-click="grid.appScope.editNode(row);"><a><span class="glyphicon glyphicon-edit"></span></a></div>'
                },
                {
                    field: 'active',
                    displayName: $i18next('active_Full'),
                    width: '10%',
                    enableColumnMenu: false,
                    enableSorting: false,
                    enableFiltering: false,
                    headerCellClass: 'alignCenter',
                    cellTemplate: '<switch id="active" name="active" ng-change="grid.appScope.updateNodeStatus(row)" ng-model="row.entity.active" class="green" size="medium" ></switch>'
                }
            ],
            onRegisterApi: function (gridApi) {
                vm.gridApi = gridApi;
                vm.gridApi.core.on.sortChanged($scope, function (grid, sortColumns) {
                    if (sortColumns.length == 0) {
                        paginationOptions.sort = null;
                    } else {
                        paginationOptions.sort = sortColumns[0].sort.direction;
                        paginationOptions.sortByColumn = sortColumns[0].field;
                    }
                    vm.getNodeList();
                });

                gridApi.pagination.on.paginationChanged($scope, function (newPage, pageSize) {
                    paginationOptions.pageNumber = newPage;
                    paginationOptions.pageSize = pageSize;
                    vm.getNodeList();
                });
            }
        };

        vm.getNodeList();

        function getNodeList() {
            vm.showNodeGrid = true;
            var payload = {
                searchCriteria: {
                    pageNumber: paginationOptions.pageNumber,
                    pageSize: paginationOptions.pageSize,
                    sortField: paginationOptions.sortByColumn ? paginationOptions.sortByColumn : 'id',
                    sortDirection: paginationOptions.sort ? paginationOptions.sort : 'asc'
                },
                nodeId: vm.nodeId,
                nodeName: vm.nodename,
                gln: vm.gln
            };
            nodeMaintenanceService
                .getNodeList({}, payload)
                .$promise.then(processNodeList, displayError);
        }

        function processNodeList(response) {
            searchByParam = false;
            if (response.status === 204) {//handle 'No Content' response
                vm.gridOptions.data = [];
            }
            else {
                vm.gridOptions.totalItems = response.headers.totalrecords;
                vm.gridOptions.data = response.data;
            }
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

        function addNode() {
            $state.go('addNode');
        }

        $scope.editNode = function (row) {
            $state.go('editNode', {nodeId: row.entity.id});
        };

        $scope.updateNodeStatus = function (row) {
            editNodeService
                .getNode({nodeId: row.entity.id}, {})
                .$promise.then(function (response) {
                    if (response.status === 204) {//handle 'No Content' response
                        vm.gridOptions.data = [];
                    }
                    else {
                        var payload = response.data;
                        payload.nodeStatusActive = row.entity.active;
                        invokeSaveNode(row.entity.id, payload)
                    }

                }, displayError);
        };

        function invokeSaveNode(nodeId, payload) {
            editNodeService
                .editNode({nodeId: nodeId}, payload)
                .$promise.then(editNodeSuccess, displayError);
        }

        function editNodeSuccess() {
            alertService.clear();
            alertService.add("success", $i18next('Node_Status_updated_successfully_Full'));
            $state.go('searchNode');
        }
    }
})();

