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

///**
// * @ngdoc directive
// * @name cipDateRangeSelector
// * @restrict E
// * @description
// * The 'cipDateRangeSelector' is derived from DateRangePicker of Dan Grossman for CIP.
// * Reference {@link https://github.com/dangrossman/bootstrap-daterangepicker}.
// *
// */
(function () {
    "use strict";
    angular
        .module("ngBootstrap", [])
        .directive('cipDateRangeSelector', ['_', 'moment', '$i18next', '$parse',
            function (_, moment, $i18next, $parse) {
                'use strict';
                return {
                    restrict: 'E',
                    scope: {
                        startDate: '=',
                        endDate: '=',
                        selectedDateRange: '=',
                        ranges: '=',
                        minDate: '=',
                        maxDate: '=',
                        initTime: '='
                    },
                    template: '<div id="reportrange" class="pull-right w-100 date-box">' +
                    '<div class="date-range" ">' +
                    '<i class="glyphicon glyphicon-calendar fa fa-calendar pull-left" style="margin-left: 2px;"></i>' +
                    '<span style="margin-left: 4px;"></span></div></div>',
                    replace: true,
                    link: function (scope, element, attrs) {
                        var options, dtpRanges, dtpLocale, initTime;
                        if (scope.ranges) {
                            dtpRanges = scope.ranges;
                            initTime = scope.initTime || moment();
                        }
                        else {
                            dtpRanges = {
                                'Today_Full': [moment().startOf('day'), moment()],
                                'Yesterday_Full': [moment().startOf('day').subtract(1, 'days'), moment().startOf('day')],
                                'Last_2_Days_Full': [moment().startOf('day').subtract(1, 'days'), moment()],
                                'This_Week_Full': [moment({
                                    hour: 0,
                                    minute: 0
                                }).startOf('week').subtract(1, 'days'), moment()],
                                'This_Month_Full': [moment().startOf('month'), moment().endOf('month')]
                            };
                        }

                        for (var predefinedRange in dtpRanges) {
                            if (dtpRanges.hasOwnProperty(predefinedRange)) {
                                var translatedText = $i18next(predefinedRange);
                                dtpRanges[$i18next(predefinedRange)] = dtpRanges[predefinedRange];
                                if (translatedText !== predefinedRange) {
                                    delete dtpRanges[predefinedRange];
                                }
                            }
                        }
                        initTime = moment();

                        dtpLocale = {
                            applyLabel: $i18next('Ok_Full'),
                            cancelLabel: $i18next('Cancel_Full'),
                            fromLabel: $i18next('From_Full'),
                            toLabel: $i18next('To_Full'),
                            customRangeLabel: $i18next('Custom_Range_Full'),
                            daysOfWeek: moment()._locale._weekdaysMin.slice(),
                            monthNames: moment()._locale._monthsShort.slice(),
                            firstDay: 0
                        };

                        options = {
                            minDate: scope.minDate && moment(scope.minDate),
                            maxDate: scope.maxDate && moment(scope.maxDate),
                            format: attrs.format || "L",
                            customRangeFormat: attrs.customFormat || 'YYYY-MM-DD',
                            separator: attrs.dateSeparator || ' - ',
                            dateLimit: attrs.dateLimit &&
                            moment.duration.apply(this, attrs.dateLimit.split(' ').map(function (elem, index) {
                                return index === 0 && parseInt(elem, 10) || elem;
                            })),
                            ranges: dtpRanges,
                            locale: dtpLocale,
                            opens: attrs.opens || "right",
                            singleDatePicker: attrs.singleDatePicker || $parse(attrs.singleDatePicker)(scope),
                            linkedCalendars: attrs.linkedCalendars || $parse(attrs.linkedCalendars)(scope)
                        };
                        if (attrs.singleDatePicker === 'true') {
                            options.singleDatePicker = true;
                            angular.extend(options, $parse(attrs.singleDatePicker)(scope));
                        }
                        if (attrs.linkedCalendars === 'true') {
                            options.linkedCalendars = true;
                            angular.extend(options, $parse(attrs.linkedCalendars)(scope));
                        }

                        scope.$watch('selectedDateRange', function (rangeText) {
                            if (rangeText) {
                                var dateRangeObj = element.data().daterangepicker;
                                if (dtpRanges[rangeText]) {
                                    dateRangeObj.startDate = dtpRanges[rangeText][0];
                                    dateRangeObj.endDate = dtpRanges[rangeText][1];
                                    dateRangeObj.updateView();
                                    dateRangeObj.updateCalendars();
                                    //dateRangeObj.updateInputText();
                                    scope.startDate = dtpRanges[rangeText][0];
                                    scope.endDate = dtpRanges[rangeText][1];
                                    element.find('span').html(" " + scope.selectedDateRange);
                                }
                                else {
                                    if (rangeText === dtpLocale.customRangeLabel) {
                                        element.find('span').html(" " + moment(scope.startDate).format(options.customRangeFormat) + options.separator + moment(scope.endDate).format(options.customRangeFormat));
                                    }
                                }
                            }

                        });

                        element.daterangepicker(options);
                        element.find('span').html(" " + scope.selectedDateRange);

                        element.on('apply.daterangepicker', function (event, picker) {
                            scope.selectedDateRange = picker.chosenLabel;

                            if (scope.selectedDateRange !== dtpLocale.customRangeLabel) {
                                element.find('span').html(" " + scope.selectedDateRange);
                                if (dtpRanges[scope.selectedDateRange]) {
                                    scope.startDate = dtpRanges[scope.selectedDateRange][0];
                                    scope.endDate = dtpRanges[scope.selectedDateRange][1];
                                }
                            }
                            else {
                                scope.startDate = picker.startDate;
                                scope.endDate = picker.endDate;
                                element.find('span').html(" " + scope.startDate.format(options.customRangeFormat) + options.separator + scope.endDate.format(options.customRangeFormat));
                            }

                            scope.$apply();
                        });

                    }
                };
            }]);
})();
