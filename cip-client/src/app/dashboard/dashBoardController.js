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
        .module('cip.dashBoard')
        .controller('dashBoardController', dashBoardController);

    dashBoardController.$inject = ['$scope', '$i18next', 'dashBoardService',
        'carrierMaintenanceService', 'nodeMaintenanceService', 'alertService', 'moment', 'shipmentsService', '$state', '$filter'
    ];

    /*@ngInject*/
    function dashBoardController($scope, $i18next, dashBoardService, carrierMaintenanceService,
                                 nodeMaintenanceService, alertService, moment, shipmentsService, $state, $filter) {

        var vm = this;
        vm.loadInfos = [];
        vm.manifestInfos = [];
        vm.selectedNode = -1;
        vm.selectedServiceTypeOption = -1;
        vm.selectedCarrierOption = -1;
        vm.shipmentInfos = [];
        vm.showDetails = false;
        vm.requestCount = 0;
        vm.completedManifestCount = 0;
        vm.errorCount = 0;
        vm.cancelledCount = 0;
        vm.reprintCount = 0;
        vm.dateComponent = {
            caption: "Today",
            range: {
                fromDate: moment().startOf('day').toDate(),
                toDate: moment().endOf('day').toDate()
            }
        };
        vm.ranges = {
            'Today_Full': [moment(), moment()],
            'Yesterday_Full': [moment().subtract('days', 1), moment().subtract('days', 1)],
            'Last_7_Days_Full': [moment().subtract('days', 7), moment()],
            'Last_30_Days_Full': [moment().subtract('days', 30), moment()],
            'This_Month_Full': [moment().startOf('month'), moment().endOf('month')]
        };
        vm.getPendingWorkLoadSummary = getPendingWorkLoadSummary;
        vm.getPendingWorkLoadSummaryByTime = getPendingWorkLoadSummaryByTime;
        vm.getTotalWorkLoadSummary = getTotalWorkLoadSummary;
        vm.populateDashboard = populateDashboard;

        alertService.clear();

        getNodeList();

        function getNodeList() {
            nodeMaintenanceService.getNodeList({}, {})
                .$promise.then(processNodeList, displayError);
        }

        function processNodeList(response) {
            vm.nodeList = response.data;
            vm.selectedNode = -1; //Select 'ALL' by default
            vm.selectedCarrierOption = -1;//Select 'ALL' by default
            vm.selectedServiceTypeOption = -1; //Select 'ALL' by default
        }

        function displayError(reason) {
            alertService.clear();
            if (reason.data && reason.data.status.errors) {
                alertService.add("danger", reason.data.status.errors[0].errorDescription);
            } else {
                alertService.add("danger", $i18next('Application_Error_Full'));
            }
        }

        vm.selectNode = function (value) {
            vm.selectedNode = value;
            if (vm.selectedNode) {
                populateCarriers();
            } else {
                vm.carrierOptions.length = 0;
                vm.serviceTypeOptions = 0;
            }
        };

        function populateCarriers() {
            carrierMaintenanceService
                .getAllCarriers({}, {})
                .$promise.then(processCarrierList, displayError);
        }

        function processCarrierList(response) {
            vm.carrierOptions = response.data;
            vm.selectedCarrierId = vm.carrierOptions[0].id;
        }

        vm.selectCarrier = function (value) {
            vm.selectedCarrierOption = value;
            if (vm.selectedCarrierOption) {
                vm.selectedCarrierId = vm.selectedCarrierOption ? vm.selectedCarrierOption.id : vm.carrierOptions[0].id;
                populateServiceTypes();
            } else {
                vm.serviceTypeOptions.length = 0;
            }
        };

        function populateServiceTypes() {
            carrierMaintenanceService
                .viewCarrier({carrierId: vm.selectedCarrierId}, {})
                .$promise.then(processServiceTypeList);
        }

        function processServiceTypeList(response) {
            vm.serviceTypeOptions = response.data.carrierServiceInfoList;
        }

        vm.selectServiceType = function (value) {
            vm.selectedServiceTypeOption = value;
        };

        vm.populateDashboard();

        //Issues request to all the services to populate the dashboard data
        function populateDashboard() {
            vm.getTotalWorkLoadSummary();
            vm.getPendingWorkLoadSummary();
            vm.getPendingWorkLoadSummaryByTime();
            getLatestLoadInfo();
            getLatestManifestInfo();
        }

        //Overall summary. This is affected by the search fields
        function getTotalWorkLoadSummary() {
            var payload = prepareRequestPayload('O');
            dashBoardService.getShipmentSummary(payload)
                .$promise.then(populateTotalWorkloadBlocks, displayError);
        }

        function prepareRequestPayload(requestIdentifier) {
            var nodeId, carrierOptionId, carrierServiceId;
            var businessUnitListValue = 'All';
            nodeId = vm.selectedNode !== -1 ? vm.selectedNode.id : 'All';
            carrierOptionId = vm.selectedCarrierOption !== -1 ? vm.selectedCarrierOption.id : 'All';
            carrierServiceId = vm.selectedServiceTypeOption !== -1 ? vm.selectedServiceTypeOption.id : 'All';

            // O-Overall Summary, P-Pending Summary, T-Summary By Time, C-Summary by Carrier, N-Summary by Node & S-Summary by Ship via
            var payload = {
                fromDate: (requestIdentifier === 'T' || requestIdentifier === 'P') ?
                    moment(new Date()).format('YYYY-MM-DD') : moment(vm.dateComponent.range.fromDate).format('YYYY-MM-DD'),
                toDate: (requestIdentifier === 'T' || requestIdentifier === 'P') ?
                    moment(new Date()).format('YYYY-MM-DD') : moment(vm.dateComponent.range.toDate).format('YYYY-MM-DD'),
                businessUnitList: businessUnitListValue,
                nodeList: nodeId,
                carrierList: carrierOptionId,
                carrierServiceList: carrierServiceId,
                splitSize: 0,
                overallSummary: requestIdentifier === 'O' ? true : false,
                pendingSummary: requestIdentifier === 'P' ? true : false,
                pendingSummaryByTime: requestIdentifier === 'T' ? true : false,
                summaryByCarrier: requestIdentifier === 'C' ? true : false,
                summaryByNode: requestIdentifier === 'N' ? true : false,
                summaryByCarrierService: requestIdentifier === 'S' ? true : false,
                summaryByTime: false,
                splitByHour: false
            };

            return payload;
        }

        function populateTotalWorkloadBlocks(response) {
            if (response.data && response.data.summary && response.data.summary.overallSummary) {
                vm.requestCount = response.data.summary.overallSummary.requestCount;
                vm.completedManifestCount = response.data.summary.overallSummary.manifestCount;
                vm.errorCount = response.data.summary.overallSummary.errorCount;
                vm.cancelledCount = response.data.summary.overallSummary.cancelCount;
                vm.reprintCount = response.data.summary.overallSummary.reprintCount;
            }
        }

        //Pending Workload Summary by Carrier - as of current time
        function getPendingWorkLoadSummary() {
            vm.pendingSummaryLoading = true;
            var payload = prepareRequestPayload('P');
            dashBoardService.getShipmentSummary(payload)
                .$promise.then(populatePendingWorkLoadSummary, displayError);
            d3.select('#pendingBarChart svg').selectAll('*').remove();
        }

        function populatePendingWorkLoadSummary(response) {
            var pendingLabelCount, pendingManifestCount, pendingLoadCount, pendingSummaryByCarrier = [];
            if (response.data && response.data.summary && response.data.summary.pendingShipmentSummary) {
                pendingLabelCount = response.data.summary.pendingShipmentSummary.pendingLabelCount;
                pendingManifestCount = response.data.summary.pendingShipmentSummary.pendingManifestCount;
                pendingLoadCount = response.data.summary.pendingShipmentSummary.pendingLoadCount;

                if (pendingLabelCount || pendingLoadCount || pendingManifestCount) {
                    pendingSummaryByCarrier = [{
                        key: 'Pending Workload by Carrier',
                        values: [{
                            label: $i18next('Labels_Full'),
                            value: parseInt(pendingLabelCount),
                            color: 'rgb(31, 119, 180)'
                        },
                            {label: $i18next('Loads_Full'), value: parseInt(pendingLoadCount), color: '#008000'},
                            {
                                label: $i18next('Manifests_Full'),
                                value: parseInt(pendingManifestCount),
                                color: 'rgb(139,0,139)'
                            }
                        ]
                    }];
                }
            }
            getDateAndTime(new Date());
            plotBarChart(pendingSummaryByCarrier);
        }

        //gets date and time formatted
        function getDateAndTime(currentDate) {
            var year, month, date, hours, minutes, format;
            year = currentDate.getFullYear();
            month = currentDate.getMonth() + 1;
            date = currentDate.getDate();
            hours = currentDate.getHours();
            minutes = currentDate.getMinutes();

            format = d3.time.format('%Y,%m,%d,%H,%M,%S');
            var timeFormat = d3.time.format('%H:%M %p');
            var numberToFormat = new Date(year, month, date, hours, minutes, 0);
            vm.currentTime = timeFormat(numberToFormat);
        }

        //Plots bar chart
        function plotBarChart(data) {
            nv.addGraph(function () {
                var chart = nv.models.discreteBarChart()
                    .x(function (d) {
                        return d.label
                    })
                    .y(function (d) {
                        return d.value;
                    });
                chart.showValues(true);
                chart.showYAxis(false);
                chart.valueFormat(d3.format('d'));
                chart.duration(500);

                d3.select('#pendingBarChart svg')
                    .datum(data)
                    .transition().duration(500)
                    .call(chart)
                ;

                nv.utils.windowResize(chart.update);

                return chart;
            });


        }

        //Pending Workload by Time - last 12 hours
        function getPendingWorkLoadSummaryByTime() {
            d3.select('#pendingLineChart svg').selectAll('*').remove();
            var payload = prepareRequestPayload('T');
            dashBoardService.getShipmentSummary(payload)
                .$promise.then(populatePendingWorkloadByTime, displayError);
        }

        (function () {
            if (typeof Object.defineProperty === 'function') {
                try {
                    Object.defineProperty(Array.prototype, 'sortBy', {value: sb});
                } catch (e) {
                }
            }
            if (!Array.prototype.sortBy) Array.prototype.sortBy = sb;

            function sb(f) {
                for (var i = this.length; i;) {
                    var o = this[--i];
                    this[i] = [].concat(f.call(o, o, i), o);
                }
                this.sort(function (a, b) {
                    for (var i = 0, len = a.length; i < len; ++i) {
                        if (a[i] != b[i]) return a[i] < b[i] ? -1 : 1;
                    }
                    return 0;
                });
                for (var i = this.length; i;) {
                    this[--i] = this[i][this[i].length - 1];
                }
                return this;
            }
        })();

        function populatePendingWorkloadByTime(response) {
            var pendingSummaryByTime = [];
            if (response.data) {
                var pendingLabels = [];
                var pendingLoads = [];
                var pendingManifests = [];

                //sort the array by date & time failing which ends up in malfunctioning of tooltips
                response.data.summary.pendingSummaryByTime.sortBy(function (o) {
                    return getDate(o.label)
                });

                _.each(response.data.summary.pendingSummaryByTime, function (pendingSummary) {
                    //_.each(response.summary.pendingSummaryByTime, function (pendingSummary) {
                    pendingLabels.push({
                        x: getDate(pendingSummary.label),
                        y: pendingSummary.pendingLabelCount,
                    });
                    pendingLoads.push(
                        {
                            x: getDate(pendingSummary.label),
                            y: pendingSummary.pendingLoadCount,
                        });
                    pendingManifests.push(
                        {
                            x: getDate(pendingSummary.label),
                            y: pendingSummary.pendingManifestCount,
                        });
                });

                pendingSummaryByTime = [
                    {
                        values: pendingLabels,      //values - represents the array of {x,y} data points
                        key: $i18next('Pending_Labels_Full'), //key  - the name of the series.
                        color: '#7777ff'  //color - optional: choose your own line color.
                    },
                    {
                        values: pendingLoads,
                        key: $i18next('Pending_Loads_Full'),
                        color: '#008000'
                    },
                    {
                        values: pendingManifests,
                        key: $i18next('Pending_Manifests_Full'),
                        color: 'rgb(139,0,139)',
                        area: false      //area - set to true if you want this line to turn into a filled area chart.
                    }
                ];
            }
            plotLineChart(pendingSummaryByTime);
        }

        function getDate(date) {
            var strDate = new String(date);

            var year = strDate.substr(0, 4);
            var month = strDate.substr(5, 2);
            var date = strDate.substr(8, 2);
            var hours = strDate.substr(11, 2);

            return new Date(year, month, date, hours, 0, 0, 0);
        }

        function plotLineChart(data) {
            nv.addGraph(function () {
                var chart = nv.models.lineChart()
                    .margin({left: 100})  //Adjust chart margins to give the x-axis some breathing room.
                    .useInteractiveGuideline(true)  //We want nice looking tooltips and a guideline!
                    .showLegend(true)       //Show the legend, allowing users to turn on/off line series.
                    .showYAxis(true)        //Show the y-axis
                    .showXAxis(true);     //Show the x-axis

                chart.xAxis     //Chart x-axis settings
                    .axisLabel('Time (hr)')
                    .rotateLabels(-30)
                    .ticks(12)
                    .tickFormat(function (d) {
                        return d3.time.format('%d%b-%H%p')(new Date(d));
                    });

                chart.yAxis     //Chart y-axis settings
                    .axisLabel('Pending Count')
                    .tickFormat(d3.format('r'));

                d3.select('#pendingLineChart svg')    //Select the <svg> element you want to render the chart in.
                    .datum(data)         //Populate the <svg> element with chart data...
                    .call(chart);          //Finally, render the chart!

                //Update the chart when window resizes.
                nv.utils.windowResize(function () {
                    chart.update()
                });
                return chart;
            });
        }

        function getLatestLoadInfo() {
            var payLoad = {
                pageNumber: 1,
                pageSize: 5,
                sortField: 'Load.loadReferenceId',
                sortDirection: 'desc',
                filterCriteria: [{
                    fieldName: 'Load.loadState',
                    fieldValue: 'Loading'
                }]
            };
            //service call should be made as query string to fetch the information based on filter
            shipmentsService
                .getAllLoads({}, payLoad)
                .$promise.then(processLoadResult, displayError);
        }

        function processLoadResult(response) {
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
            vm.loadInfos = loadInfos;
        }

        function getLatestManifestInfo() {
            var payLoad = {
                pageNumber: 1,
                pageSize: 5,
                sortField: 'Load.loadReferenceId',
                sortDirection: 'desc',
                filterCriteria: [{
                    fieldName: 'Load.loadState',
                    fieldValue: 'Manifested'
                }]
            };
            //service call should be made as query string to fetch the information based on filter
            shipmentsService
                .getAllLoads({}, payLoad)
                .$promise.then(processManifestResult, displayError);
        }

        function processManifestResult(response) {
            var manifestInfos = [];
            _.each(response.data, function (manifestInfo) {
                var manifestInformation = {
                    id: manifestInfo.loadDetails.id,
                    loadReferenceId: manifestInfo.loadDetails.loadReferenceId,
                    loadState: manifestInfo.loadDetails.loadState ? $filter('capitalize')(manifestInfo.loadDetails.loadState.replace(/_/g, " ")) : manifestInfo.loadDetails.loadState,
                    totalUnitCount: manifestInfo.loadDetails.totalUnitCount
                };
                manifestInfos.push(manifestInformation);
            });
            vm.manifestInfos = manifestInfos;
        }

        $scope.navigateToLoadInfo = function (identifier) {
            if (identifier == 'M') {
                $state.go("loadDetails", {
                    status: 'Manifested'
                });
            } else {
                $state.go('loadDetails', {status: 'Loading'});
            }
        };
    }
})();
