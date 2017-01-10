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
    angular.module('cip.shipments')
        .controller('shipmentsController', shipmentsController);

    shipmentsController.$inject = ['shipmentsService', '$i18next', '$stateParams', 'uiGridConstants', '$scope', 'JavaConfig',
        'carrierMaintenanceService', 'nodeMaintenanceService', 'moment', 'staticDataService', 'alertService', '$filter', '$timeout'];


    function shipmentsController(shipmentsService, $i18next, $stateParams, uiGridConstants, $scope, JavaConfig,
                                 carrierMaintenanceService, nodeMaintenanceService, moment, staticDataService, alertService, $filter, $timeout) {
        var vm = this;
        var paginationOptions = {
            pageNumber: 1,
            pageSize: 10,
            currentPage: 1,
            sort: null
        };
        vm.modifySearchVisible = false;
        vm.clear = clear;
        vm.ranges = {
            'Today_Full': [moment(), moment()],
            'Yesterday_Full': [moment().subtract('days', 1), moment().subtract('days', 1)],
            'Last_7_Days_Full': [moment().subtract('days', 7), moment()],
            'Last_30_Days_Full': [moment().subtract('days', 30), moment()],
            'This_Month_Full': [moment().startOf('month'), moment().endOf('month')]
        };
        vm.displayAdditionalSearchFields = displayAdditionalSearchFields;
        vm.getLoadIdDetails = getLoadIdDetails;
        vm.getSearchShipmentList = getSearchShipmentList;
        vm.init = init;
        vm.populateCarrierValues = populateCarrierValues;
        vm.populateLoadStatus = populateLoadStatus;
        vm.populateNodeValues = populateNodeValues;
        vm.populateShipmentStatus = populateShipmentStatus;
        vm.selectCarrier = selectCarrier;
        vm.selectCarrierService = selectCarrierService;
        vm.selectLoadStatus = selectLoadStatus;
        vm.selectNode = selectNode;
        vm.selectShipmentStatus = selectShipmentStatus;

        init();

        function init() {
            vm.searchFields = {};
            vm.searchFields.dateRange = {
                caption: "Today",
                fromDate: moment(new Date()),
                toDate: moment(new Date())
            };
            vm.searchFields.loadReference = $stateParams.loadID ? $stateParams.loadID : null;
            vm.showGrid = false;
            alertService.clear();
            vm.populateNodeValues();
            vm.populateCarrierValues();
            vm.populateShipmentStatus();
            vm.populateLoadStatus();
            getSearchShipmentList();
        }

        function populateNodeValues() {
            nodeMaintenanceService.getNodeList({}, {})
                .$promise.then(processNodeList, displayError);
        }

        function processNodeList(response) {
            if (response && response.data) {
                vm.nodeList = response.data;
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

        function populateCarrierValues() {
            carrierMaintenanceService
                .getAllCarriers({}, {})
                .$promise.then(processCarrierList, displayError);
        }

        function processCarrierList(response) {
            if (response && response.data) {
                vm.carrierList = response.data;
                vm.selectedCarrierId = vm.carrierList[0].id;
            }
        }

        function populateShipmentStatus() {
            vm.shipmentStatusList = staticDataService.getShipmentStatus();
        }

        function populateLoadStatus() {
            vm.loadStatusList = staticDataService.getLoadStatus();
        }

        function selectNode(value) {
            vm.searchFields.selectedNode = value;
        }

        function selectCarrier(value) {
            vm.searchFields.selectedCarrier = value;
            vm.selectedCarrierId = vm.searchFields.selectedCarrier ? vm.searchFields.selectedCarrier.id : vm.carrierList[0].id;
            populateServiceTypes();
        }

        function populateServiceTypes() {
            carrierMaintenanceService
                .viewCarrier({carrierId: vm.selectedCarrierId}, {})
                .$promise.then(processServiceTypeList, displayError);
        }

        function processServiceTypeList(response) {
            vm.carrierServiceInfoList = response.data.carrierServiceInfoList;
        }

        function selectCarrierService(value) {
            vm.searchFields.selectedCarrierService = value;
        }

        function selectShipmentStatus(value) {
            vm.searchFields.selectedShipmentStatus = value;
        }

        function selectLoadStatus(value) {
            vm.searchFields.selectedLoadStatus = value;
        }

        $scope.maxSize = 5;
        vm.gridOptions = {
            paginationCurrentPage: 1,
            enablePaginationControls: false,
            paginationPageSizes: [10, 20, 30],
            paginationPageSize: 10,
            enableColumnResizing: false,
            enableCellEdit: false,
            treeRowHeaderAlwaysVisible: true,
            enableFiltering: false,
            enablePagination: true,
            useExternalPagination: true,
            enableExternalSorting: true,
            rowHeight: 45,
            enableExpandableRowHeader: false,
            expandableRowTemplate: 'src/app/templates/viewXmlTemplate.html',
            expandableRowHeight: 100,
            enableVerticalScrollbar: uiGridConstants.scrollbars.NEVER,
            enableHorizontalScrollbar: uiGridConstants.scrollbars.NEVER,
            enableRowHeaderSelection: false,
            enableRowSelection: true,
            multiSelect: true,
            columnDefs: [
                {
                    field: 'shipReferenceId',
                    displayName: $i18next('Shipment_Ref_ID_Full'),
                    width: '15%',
                    enableColumnMenu: false,
                    cellClass: 'alignLeft'
                },
                {
                    field: 'carrierServiceType',
                    displayName: $i18next('Carrier_Service_Type_Full'),
                    width: '15%',
                    enableColumnMenu: false,
                    cellClass: 'alignLeft'
                },
                {
                    field: 'shipmentStatus',
                    displayName: $i18next('Status_Full'),
                    width: '13%',
                    enableColumnMenu: false,
                    cellClass: 'alignLeft',
                },
                {
                    field: 'trackingNumber',
                    displayName: $i18next('Tracking_Number_Full'),
                    width: '14%',
                    enableColumnMenu: false,
                    cellClass: 'alignLeft',
                    cellTemplate: '<div class="ui-grid-cell-contents"><a href={{row.entity.labelUrl}} target="_blank">{{row.entity.trackingNumber}}</a></div>'
                },
                {
                    field: 'lastUpdatedTmstmp',
                    displayName: $i18next('Last_Update_TS_Full'),
                    width: '14%',
                    enableColumnMenu: false,
                    cellClass: 'alignLeft'
                },
                {
                    field: 'loadReferenceId',
                    displayName: $i18next('Load_Reference_Full'),
                    width: '10%',
                    enableColumnMenu: false,
                    cellClass: 'alignLeft',
                    cellTemplate: '<div class="ui-grid-cell-contents"><a style="cursor:pointer;" ng-click="grid.appScope.getShipmentDetailsByLoadId(row);">{{row.entity.loadReferenceId}}</a></div>'
                },
                {
                    field: 'loadStatus',
                    displayName: $i18next('Load_Status_Full'),
                    width: '10%',
                    enableColumnMenu: false,
                    cellClass: 'alignLeft'
                },
                {
                    field: 'view',
                    displayName: $i18next('View_Xml_Full'),
                    width: "9%",
                    enableColumnMenu: false,
                    enableSorting: false,
                    enableFiltering: false,
                    cellTemplate: "<div class=\"ui-grid-cell-contents\">" +
                    "<a><span ng-click=\"grid.api.expandable.toggleRowExpansion(row.entity); grid.appScope.toggleClass(row.isExpanded)\"" +
                    "ng-class=\"{ 'glyphicon glyphicon-chevron-down' : !row.isExpanded, 'glyphicon glyphicon-chevron-up' : row.isExpanded }\"></span></a></div>"
                }
            ],

            onRegisterApi: function (gridApi) {
                vm.gridApi = gridApi;
                vm.gridApi.pagination.on.paginationChanged($scope, function (currentPage, pageSize) {
                    paginationOptions.pageNumber = currentPage;
                    paginationOptions.pageSize = pageSize;
                    getSearchShipmentList();
                });
                vm.gridApi.core.on.sortChanged($scope, function (grid, sortColumns) {
                    if (sortColumns.length == 0) {
                        paginationOptions.sort = null;
                    } else {
                        paginationOptions.sort = sortColumns[0].sort.direction;
                        paginationOptions.sortByColumn = sortColumns[0].field;
                    }
                    getSearchShipmentList();
                });

                vm.gridApi.expandable.on.rowExpandedStateChanged($scope, function (row) {
                    if (row.isExpanded) {
                        row.entity.subGridOptions = {};
                        shipmentsService
                            .getXMLDocumentDetails({refId: row.entity.shipReferenceId}, {})
                            .$promise.then(function (response) {
                                _.each(response.data.transactionLogList, function (transactionLog) {
                                    if (transactionLog.eventOrigin === 'Shipment') {
                                        transactionLog.requestFileName = ((transactionLog.requestFileUrl.split('/'))[6]).split('-')[4];
                                        transactionLog.responseFileName = ((transactionLog.responseFileUrl.split('/'))[6]).split('-')[4];
                                        transactionLog.eventName = transactionLog.eventType + " " + transactionLog.eventOrigin;
                                    }
                                });
                                row.entity.subGridOptions.data = response.data.transactionLogList;
                            });

                        row.entity.subGridOptions = {
                            enableHorizontalScrollbar: 0,
                            enableVerticalScrollbar: 0,
                            columnDefs: [
                                {
                                    displayName: $i18next('Event_Type_Full'),
                                    field: 'eventType',
                                    width: "25%",
                                    enableColumnMenu: false,
                                    cellClass: 'alignLeft',
                                },
                                {
                                    displayName: $i18next('Request_File_Full'),
                                    field: 'requestFileName',
                                    width: "25%",
                                    enableColumnMenu: false,
                                    cellClass: 'alignLeft',
                                    cellTemplate: '<div class="ui-grid-cell-contents"><a style="cursor:pointer;" href="{{row.entity.requestFileUrl}}" target="_blank">{{row.entity.requestFileName}}</a></div>'
                                },
                                {
                                    displayName: $i18next('Response_File_Full'),
                                    field: 'responseFileName',
                                    width: "25%",
                                    enableColumnMenu: false,
                                    cellClass: 'alignLeft',
                                    cellTemplate: '<div class="ui-grid-cell-contents"><a style="cursor:pointer;" href="{{row.entity.responseFileUrl}}" target="_blank">{{row.entity.responseFileName}}</a></div>'
                                },
                                {
                                    displayName: $i18next('Time_Stamp_Full'),
                                    field: 'timeStamp',
                                    width: "25%",
                                    enableColumnMenu: false,
                                    cellClass: 'alignLeft'
                                }
                            ]
                        };
                    }
                });
            }
        };
        vm.gridOptions.totalItems = 0;

        function getLoadIdDetails(loadId) {
            vm.searchFields.loadReference = loadId;
            shipmentsService
                .getLoadById({id: loadId})
                .$promise.then(populateShipmentsForLoad, displayError);
        }

        function populateShipmentsForLoad(response) {
            paginationOptions.sort = null;
            paginationOptions.sortByColumn = null;
            if (response.status === 204) {
                vm.gridOptions.data = [];
                vm.gridOptions.totalItems = 0;
                vm.showGrid = false;
            }
            else {
                var loadInfos = [];
                vm.showGrid = true;
                vm.gridOptions.totalItems = response.data.loadBase.totalUnitCount; //totalrecords to be exposed by the service

                _.each(response.data.shipmentLoadInfoList, function (shipmentLoadInfo) {
                    var shipmentInfo = {};
                    shipmentInfo = {
                        shipReferenceId: shipmentLoadInfo.shipReferenceId,
                        carrierServiceType: shipmentLoadInfo.carrierServiceType,
                        loadReferenceId: shipmentLoadInfo.loadReferenceId,
                        loadStatus: shipmentLoadInfo.loadStatus =
                            shipmentLoadInfo.loadStatus ? $filter('capitalize')(shipmentLoadInfo.loadStatus.replace(/_/g, " ")) : shipmentLoadInfo.loadStatus,
                        shipmentStatus: shipmentLoadInfo.shipmentStatus ? $filter('capitalize')(shipmentLoadInfo.shipmentStatus) : shipmentLoadInfo.shipmentStatus,
                        trackingNumber: shipmentLoadInfo.trackingNumber,
                        labelUrl: shipmentLoadInfo.shipmentStatus == 'FAILED' ? "" : JavaConfig.shipmentTrackingServiceUrl + shipmentLoadInfo.shipReferenceId + '_INVOICE_WITH_LABEL.PDF',
                        lastUpdatedTmstmp: shipmentLoadInfo.lastUpdatedTmstmp
                    };
                    loadInfos.push(shipmentInfo);
                });
                vm.gridOptions.data = loadInfos;
            }
        }

        function processShipmentsList(response) {
            paginationOptions.sort = null;
            paginationOptions.sortByColumn = null;
            if (response.status === 204) {
                vm.showGrid = false;
                vm.gridOptions.data = [];
                vm.gridOptions.totalItems = 0;
            }
            else {
                vm.showGrid = true;
                vm.gridOptions.totalItems = response.headers.totalrecords;
                vm.gridOptions.data = _.each(response.data.shippingUnits.shippingUnit, function (shippingUnit) {
                    shippingUnit.labelUrl = JavaConfig.shipmentTrackingServiceUrl + shippingUnit.shipReferenceId + '_INVOICE_WITH_LABEL.PDF';
                });
            }
        }

        $scope.getShipmentDetailsByLoadId = function (row) {
            vm.searchFields = {};
            var payLoad = {};
            var loadId = row.entity.loadReferenceId;
            vm.searchFields.loadReference = row.entity.loadReferenceId;
            payLoad = {
                pageNumber: paginationOptions.pageNumber,
                pageSize: paginationOptions.pageSize,
                sortField: paginationOptions.sortByColumn ? paginationOptions.sortByColumn : 'lastUpdatedTmstmp',
                sortDirection: paginationOptions.sort ? paginationOptions.sort : 'desc'
            };

            shipmentsService
                .getLoadById({id: loadId}, payLoad)
                .$promise.then(populateShipmentsForLoad, displayError);
        };

        /**
         * Getting Shipments based on the search criteria
         */
        function getSearchShipmentList() {
            var fromDate = vm.searchFields.dateRange ? moment(vm.searchFields.dateRange.fromDate).format('YYYY-MM-DD') + ' 00:00:00' : '';
            var toDate = vm.searchFields.dateRange ? moment(vm.searchFields.dateRange.toDate).format('YYYY-MM-DD') + ' 23:59:59' : '';
            var lastUpdatedDateRange = $stateParams.loadID ? '' : fromDate && toDate ? fromDate + ',' + toDate : '';
            var availableSearchOptions = {
                carrierServiceType: vm.searchFields.selectedCarrierService ? vm.searchFields.selectedCarrierService.name : '',
                lastUpdatedTmstmp: lastUpdatedDateRange,
                loadReferenceId: vm.searchFields.loadReference,
                loadStatus: vm.searchFields.selectedLoadStatus ? vm.searchFields.selectedLoadStatus.desc : '',
                carrierName: vm.searchFields.selectedCarrier ? vm.searchFields.selectedCarrier.name : '',
                nodeName: vm.searchFields.selectedNode ? vm.searchFields.selectedNode.id : '',
                shipReferenceId: vm.searchFields.shipmentReferenceId,
                shipmentStatus: vm.searchFields.selectedShipmentStatus ? vm.searchFields.selectedShipmentStatus.desc : '',
                trackingNumber: vm.searchFields.trackingNumber
            }, key;
            var filterCriteriaDetails;

            for (key in availableSearchOptions) {
                if (availableSearchOptions.hasOwnProperty(key) && availableSearchOptions[key]) {
                    var searchCriteria = {
                        fieldName: 'Shipment.' + key,
                        fieldValue: key === 'shipmentStatus' || key === 'loadStatus' ?
                            availableSearchOptions[key].replace(/ /g, "_") : availableSearchOptions[key]
                    };
                    filterCriteriaDetails = filterCriteriaDetails ? filterCriteriaDetails : [];
                    filterCriteriaDetails.push(searchCriteria);
                }
            }

            var payLoad = {
                pageNumber: paginationOptions.pageNumber,
                pageSize: paginationOptions.pageSize,
                sortField: paginationOptions.sortByColumn ? 'Shipment.' + paginationOptions.sortByColumn : 'Shipment.lastUpdatedTmstmp',
                sortDirection: paginationOptions.sort ? paginationOptions.sort : 'desc',
                filterCriteria: filterCriteriaDetails
            };
            //service call should be made as query string to fetch the information based on filter
            shipmentsService
                .searchShipments({}, payLoad)
                .$promise.then(displaySearchResult, displayError);

            $stateParams.loadID = '';
        }

        function displaySearchResult(response) {
            paginationOptions.sort = null;
            paginationOptions.sortByColumn = null;
            if (response.status === 204) {
                vm.showGrid = false;
                vm.gridOptions.data = [];
                vm.gridOptions.totalItems = 0;
            }
            else if (response.data) {
                vm.showGrid = true;
                vm.gridOptions.data = [];
                vm.gridOptions.totalItems = response.headers.totalrecords;
                vm.shipments = response.data;
                vm.gridOptions.data = _.each(response.data, function (shippingUnit) {
                    shippingUnit.labelUrl = shippingUnit.shipmentStatus == 'FAILED' ? "src/app/templates/fileNotFound.html" :
                    JavaConfig.shipmentTrackingServiceUrl + shippingUnit.shipReferenceId + '_INVOICE_WITH_LABEL.PDF';
                    shippingUnit.shipmentStatus =
                        shippingUnit.shipmentStatus ? $filter('capitalize')(shippingUnit.shipmentStatus.replace(/_/g, " ")) : shippingUnit.shipmentStatus;
                    shippingUnit.loadStatus =
                        shippingUnit.loadStatus ? $filter('capitalize')(shippingUnit.loadStatus.replace(/_/g, " ")) : shippingUnit.loadStatus;
                });
            }
        }

        function displayAdditionalSearchFields() {
            vm.modifySearchVisible = !vm.modifySearchVisible;
        }

        $scope.toggleClass = function (isExpanded) {
            $scope.show = isExpanded;
        };

        function clear() {
            vm.searchFields.dateRange = {caption: 'Today'};
        }
    }
})();