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
    angular.module('cip.shipments').controller('loadDetailsController', loadDetailsController);

    loadDetailsController.$inject = ['$scope', '$i18next', '$stateParams', '$state', 'shipmentsService', 'uiGridConstants',
        'alertService', 'staticDataService', '$filter'];

    function loadDetailsController($scope, $i18next, $stateParams, $state, shipmentsService, uiGridConstants,
                                   alertService, staticDataService, $filter) {

        var vm = this;
        vm.showGrid = false;
        $scope.maxPages = 5;
        var paginationOptions = {
            pageNumber: 1,
            pageSize: 10,
            sort: null
        };
        vm.status = $stateParams.status ? $stateParams.status : null;
        vm.searchLoadInformation = searchLoadInformation;
        vm.selectLoadStatus = selectLoadStatus;
        alertService.clear();
        vm.searchLoadInformation();

        vm.gridOptions = {
            paginationPageSizes: [10, 20, 30],
            paginationPageSize: 10,
            enableColumnResizing: false,
            enableCellEdit: false,
            treeRowHeaderAlwaysVisible: true,
            enableFiltering: false,
            enablePagination: true,
            enablePaginationControls: false,
            useExternalPagination: true,
            enableExpandableRowHeader: false,
            expandableRowTemplate: 'src/app/templates/viewXmlTemplate.html',
            useExternalSorting: true,
            rowHeight: 45,
            enableVerticalScrollbar: uiGridConstants.scrollbars.NEVER,
            enableHorizontalScrollbar: uiGridConstants.scrollbars.NEVER,
            enableRowHeaderSelection: false,
            enableRowSelection: true,
            multiSelect: true,
            columnDefs: [
                {
                    field: 'loadReferenceId',
                    displayName: $i18next('Load_Id_Full'),
                    width: '30%',
                    enableColumnMenu: false,
                    cellClass: 'alignLeft',
                    cellTemplate: '<div class="ui-grid-cell-contents"><a style="cursor:pointer;" ng-click="grid.appScope.navigateToShipments(row);">{{row.entity.loadReferenceId}}</a></div> '
                },
                {
                    field: 'loadState',
                    displayName: $i18next('Load_Status_Full'),
                    width: '30%',
                    enableColumnMenu: false,
                    cellClass: 'alignLeft'
                },
                {
                    field: 'totalUnitCount',
                    displayName: $i18next('Total_Units_Full'),
                    width: '15%',
                    enableColumnMenu: false,
                    enableSorting: false,
                    cellClass: 'alignLeft'
                },
                {
                    field: 'view',
                    displayName: $i18next('View_Xml_Full'),
                    width: "25%",
                    enableColumnMenu: false,
                    enableSorting: false,
                    headerCellClass: 'alignCenter',
                    enableFiltering: false,
                    cellTemplate: "<div class=\"ui-grid-cell-contents\">" +
                    "<a><span ng-click=\"grid.api.expandable.toggleRowExpansion(row.entity); grid.appScope.toggleClass(row.isExpanded)\"" +
                    "ng-class=\"{ 'glyphicon glyphicon-chevron-down' : !row.isExpanded, 'glyphicon glyphicon-chevron-up' : row.isExpanded }\"></span></a></div>"
                }

            ],
            onRegisterApi: function (gridApi) {
                vm.gridApi = gridApi;
                vm.gridApi.pagination.on.paginationChanged($scope, function (newPage, pageSize) {
                    paginationOptions.pageNumber = newPage;
                    paginationOptions.pageSize = pageSize;
                    vm.searchLoadInformation();
                });

                vm.gridApi.core.on.sortChanged($scope, function (grid, sortColumns) {
                    if (sortColumns.length == 0) {
                        paginationOptions.sort = null;
                    } else {
                        paginationOptions.sort = sortColumns[0].sort.direction;
                        paginationOptions.sortByColumn = sortColumns[0].field;
                    }
                    vm.searchLoadInformation();
                });

                vm.gridApi.expandable.on.rowExpandedStateChanged($scope, function (row) {
                    if (row.isExpanded) {
                        row.entity.subGridOptions = {};
                        alertService.clear();
                        shipmentsService
                            .getXMLDocumentDetails({refId: row.entity.loadReferenceId}, {})
                            .$promise.then(function (response) {
                                _.each(response.data.transactionLogList, function (transactionLog) {
                                    if (transactionLog.eventOrigin === 'Load') {
                                        transactionLog.requestFileName = (transactionLog.requestFileUrl.split('-'))[6];
                                        transactionLog.responseFileName = (transactionLog.responseFileUrl.split('-'))[6];
                                        transactionLog.eventName = transactionLog.eventType + " " + transactionLog.eventOrigin;
                                    }
                                });
                                row.entity.subGridOptions.data = response.data.transactionLogList;
                            });

                        row.entity.subGridOptions = {
                            enableHorizontalScrollbar: 0,
                            enableVerticalScrollbar: 0,
                            rowHeight: 40,
                            columnDefs: [
                                {
                                    displayName: $i18next('Event_Type_Full'),
                                    field: 'eventType',
                                    width: "25%",
                                    cellClass: 'alignLeft',
                                    enableColumnMenu: false,
                                    headerCellClass: 'subGridCellHeader'
                                },
                                {
                                    displayName: $i18next('Request_File_Full'),
                                    field: 'requestFileName',
                                    width: "25%",
                                    cellClass: 'alignLeft',
                                    enableColumnMenu: false,
                                    headerCellClass: 'subGridCellHeader',
                                    cellTemplate: '<div class="ui-grid-cell-contents"><a style="cursor:pointer;" href="{{row.entity.requestFileUrl}}" target="_blank">{{row.entity.requestFileName}}</a></div>'
                                },
                                {
                                    displayName: $i18next('Response_File_Full'),
                                    field: 'responseFileName',
                                    width: "25%",
                                    cellClass: 'alignLeft',
                                    enableColumnMenu: false,
                                    headerCellClass: 'subGridCellHeader',
                                    cellTemplate: '<div class="ui-grid-cell-contents"><a style="cursor:pointer;" href="{{row.entity.responseFileUrl}}" target="_blank">{{row.entity.responseFileName}}</a></div>'
                                },
                                {
                                    displayName: $i18next('Time_Stamp_Full'),
                                    field: 'timeStamp',
                                    width: "25%",
                                    cellClass: 'alignLeft',
                                    enableColumnMenu: false,
                                    headerCellClass: 'subGridCellHeader'
                                }
                            ]
                        };
                    }
                });
            }

        };
        vm.gridOptions.totalItems = 0;

        populateLoadStatus();

        function populateLoadStatus() {
            vm.loadStatusList = staticDataService.getLoadStatus();
        }

        function selectLoadStatus(value) {
            vm.selectedLoadStatus = value;
        }

        $scope.navigateToShipments = function (row) {
            $state.go("shipments",
                {
                    loadID: row.entity.loadReferenceId,
                });
        };

        function searchLoadInformation() {
            alertService.clear();
            var searchFields = {//any new filters introduced in future gets in here
                loadReferenceId: vm.loadId,
                loadState: vm.selectedLoadStatus ? vm.selectedLoadStatus.desc : vm.status ? vm.status : ''
            }, key;
            var filterCriteriaDetails;

            for (key in searchFields) {
                if (searchFields.hasOwnProperty(key) && searchFields[key]) {
                    var searchCriteria = {
                        fieldName: 'Load.' + key,
                        fieldValue: searchFields[key].replace(/ /g, "_")
                    };
                    filterCriteriaDetails = filterCriteriaDetails ? filterCriteriaDetails : [];
                    filterCriteriaDetails.push(searchCriteria);
                }
            }

            var payLoad = {
                pageNumber: paginationOptions.pageNumber,
                pageSize: paginationOptions.pageSize,
                sortField: paginationOptions.sortByColumn ? 'Load.' + paginationOptions.sortByColumn : 'Load.loadReferenceId',
                sortDirection: paginationOptions.sort ? paginationOptions.sort : 'desc',
                filterCriteria: filterCriteriaDetails
            };
            shipmentsService.getAllLoads({}, payLoad)
                .$promise.then(populateLoadInfo, displayError);
        }

        function populateLoadInfo(response) {
            if (response.status === 204) {//handle 'No Content' response
                vm.showGrid = false;
                vm.gridOptions.data = [];
                vm.gridOptions.totalItems = 0;
            }
            else {
                vm.showGrid = true;
                vm.gridOptions.totalItems = response.headers.totalrecords;
                var loadInfos = [];
                _.each(response.data, function (loadInfo) {
                    var loadInformation = {
                        id: loadInfo.loadDetails.id,
                        loadReferenceId: loadInfo.loadDetails.loadReferenceId,
                        loadState: loadInfo.loadDetails.loadState ? $filter('capitalize')(loadInfo.loadDetails.loadState.replace(/_/g, " ")) : loadInfo.loadDetails.loadState,
                        totalUnitCount: loadInfo.loadDetails.totalUnitCount
                    };
                    loadInfos.push(loadInformation);
                });
                vm.gridOptions.data = loadInfos;
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

        $scope.toggleClass = function (isExpanded) {
            $scope.show = isExpanded;
        }
    }
})();