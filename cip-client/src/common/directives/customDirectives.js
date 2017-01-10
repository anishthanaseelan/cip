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
        .module("cip.customDirectives", [
            'ui.grid'
        ])

    /**
     * @ngdoc directive
     * @ngModule customDirectives
     * @name sdAllowPatern
     * @element ANY
     * @description
     *
     * sdAllowPattern is a custom directive used to enter only alphabets, numeric, alpha numeric based on the pattern given in the input text field
     *
     * @usage
     * ALPHA ONLY - `<input type="text" sd-allow-pattern="[a-z]" />`
     * NUMBER ONLY - `<input type="text" sd-allow-pattern="\d" />`
     * ALPHANUMERIC ONLY - `<input type="text" sd-allow-pattern="(\d|[a-z])" />`
     * WHITESPACE CHARACTERS ONLY - `<input type="text" sd-allow-pattern="\W" />`
     * ABCDEFG ONLY - `<input type="text" sd-allow-pattern="[ABCDEFG]" />`
     */
        .directive('sdAllowPattern', function allowPatternDirective() {
            return {
                restrict: "A",
                compile: function (tElement, tAttrs) {
                    return function (scope, element, attrs) {
                        element.bind("keypress", function (event) {
                            var keyCode = event.which || event.keyCode;
                            var keyCodeChar = String.fromCharCode(keyCode);
                            if (!keyCodeChar.match(new RegExp(attrs.sdAllowPattern, "i"))) {
                                event.preventDefault();
                                return false;
                            }
                        });
                    };
                }
            };
        })
    /**
     * @ngdoc directive
     * @ngModule customDirectives
     * @name isActiveNav
     * @element ANY
     * @description
     *
     * isActiveNav is a custom directive used for activating the current tab in the navigation bar
     *
     * @usage
     * `<li><a is-active-nav href="#/pathToNavigateName">pathNameToBeVisibleInHtmlPage</a></li>`
     */
        .directive('isActiveNav', ['$location', function ($location) {
            return {
                restrict: 'A',
                link: function (scope, element) {
                    scope.location = $location;
                    scope.$watch('location.path()', function (currentPath) {
                        if ('#' + currentPath === element[0].attributes['href'].value) {
                            element.parent().addClass('active');
                            element.parent().parent().addClass('active');
                            element.parent().parent().parent().addClass('active');
                        } else {
                            element.parent().removeClass('active');
                        }
                    });
                }
            };
        }])
    /**
     * @ngdoc directive
     * @ngModule customDirectives
     * @name uploadFile
     * @element ANY
     * @description
     *
     * uploadFile is a custom directive used for binding the element during uploading and saving the image URL from the current location
     * to temporary location when changeEvent occurs at every time
     */
        .directive("uploadFile", [function () {
            return {
                scope: {
                    uploadFile: "="
                },
                link: function (scope, element, attributes) {
                    element.bind("change", function (changeEvent) {
                        scope.$apply(function () {
                            scope.uploadFileName = changeEvent.target.files[0].name;
                            console.log(scope.uploadFileName);
                        });
                    });
                }
            }
        }])
    /**
     * @ngdoc directive
     * @ngModule customDirectives
     * @name fileModel
     * @element ANY
     * @description
     *
     * fileModel is a custom directive used for binding the element during uploading when changeEvent occurs at every time
     */
        .directive('fileModel', ['$parse', function ($parse) {
            return {
                restrict: 'A',
                link: function (scope, element, attrs) {
                    var model = $parse(attrs.fileModel);
                    var modelSetter = model.assign;

                    element.bind('change', function () {
                        scope.$apply(function () {
                            modelSetter(scope, element[0].files[0]);
                        });
                    });
                }
            };
        }])

    /**
     * @ngdoc directive
     * @ngModule customDirectives
     * @name validFile
     * @element ANY
     * @description
     *
     * validFile is a custom directive used for validating the file extension
     */
        .directive('validFile', ['$timeout', function validFile($timeout) {
            var validFormats = ['jpg', 'gif', 'png', 'jpeg'];
            return {
                require: 'ngModel',
                link: function (scope, elem, attrs, ctrl) {
                    ctrl.$validators.validFile = function () {
                        elem.on('change', function () {
                            var value = elem.val(),
                                ext = value.substring(value.lastIndexOf('.') + 1).toLowerCase();
                            $timeout(ctrl.$setValidity('validFile', validFormats.indexOf(ext) !== -1));
                        });
                    };
                }
            };
        }])

    /**
     * @ngdoc directive
     * @ngModule customDirectives
     * @name limitTo
     * @element ANY
     * @description
     *
     * limitTo directive is a custom directive to restrict the number of characters entered in input boxes
     *
     * @usage
     * `<input type="text" name="ZipCode" class="form-control" limit-to="10" />`
     */
        .directive("limitTo", [function () {
            return {
                restrict: "A",
                link: function (scope, elem, attrs) {
                    var limit = parseInt(attrs.limitTo);
                    angular.element(elem).on("keypress", function (e) {
                        if (this.value.length == limit) e.preventDefault();
                    });
                }
            }
        }])

    /**
     * @ngdoc directive
     * @ngModule customDirectives
     * @name removeNotifierMessage
     * @element ANY
     * @description
     *
     * removeNotifierMessage directive is a custom directive to remove the notifier message when clicking close button on div
     */
        .directive("removeNotifierMessage", function ($rootScope) {
            return {
                link: function (scope, element, attrs) {
                    element.bind("click", function () {
                        element.parent().remove();
                    });
                }
            }
        })

    /**
     * @ngdoc directive
     * @ngModule customDirectives
     * @name sdOnlyDigits
     * @element ANY
     * @description
     *
     * sdOnlyDigits directive is a custom directive used to allow digits and only special charecter(.) in the input field
     *
     * @usage
     * `<input type="text" name="weight" class="form-control" sd-only-digits />`
     */
        .directive('sdOnlyDigits', function () {
            return {
                require: 'ngModel',
                restrict: 'A',
                link: function (scope, element, attr, ctrl) {
                    function inputValue(val) {
                        if (val) {
                            var digits = val.replace(/[^0-9.]/g, '');
                            if (digits !== val) {
                                ctrl.$setViewValue(digits);
                                ctrl.$render();
                            }
                            return parseFloat(digits);
                        }
                        return undefined;
                    }

                    ctrl.$parsers.push(inputValue);
                }
            };
        })

        .directive('dateLowerThan', ["$filter", function ($filter) {
            return {
                require: 'ngModel',
                link: function (scope, elm, attrs, ctrl) {
                    var validateDateRange = function (inputValue) {
                        var fromDate = $filter('date')(inputValue, 'short');
                        var toDate = $filter('date')(attrs.dateLowerThan, 'short');
                        var isValid = isValidDateRange(fromDate, toDate);
                        ctrl.$setValidity('dateLowerThan', isValid);
                        return inputValue;
                    };

                    ctrl.$parsers.unshift(validateDateRange);
                    ctrl.$formatters.push(validateDateRange);
                    attrs.$observe('dateLowerThan', function () {
                        validateDateRange(ctrl.$viewValue);
                    });
                }
            };
        }])

        .directive('fillOutDate', ['$filter', function ($filter) {
            return {
                require: 'ngModel',
                link: function (scope, elm, attrs, ctrl) {
                    var ValidateForEmptyDate = function (inputValue) {
                        var fromDate = $filter('date')(inputValue, 'short');
                        var toDate = $filter('date')(attrs.fillOutDate, 'short');
                        var isValid = isEmpty(fromDate, toDate);
                        ctrl.$setValidity('fillOutDate', isValid);
                        return inputValue;
                    };

                    ctrl.$parsers.unshift(ValidateForEmptyDate);
                    ctrl.$formatters.push(ValidateForEmptyDate);
                    attrs.$observe('fillOutDate', function () {
                        ValidateForEmptyDate(ctrl.$viewValue);
                    });
                }
            };
        }]);

    var isValidDate = function (dateStr) {
        if (dateStr == undefined)
            return false;
        var dateTime = Date.parse(dateStr);

        if (isNaN(dateTime)) {
            return false;
        }
        return true;
    };

    var getDateDifference = function (fromDate, toDate) {
        return Date.parse(toDate) - Date.parse(fromDate);
    };

    var isValidDateRange = function (fromDate, toDate) {
        if (fromDate == "" || toDate == "")
            return true;
        if (isValidDate(fromDate) == false) {
            return false;
        }
        if (isValidDate(toDate) == true) {
            var days = getDateDifference(fromDate, toDate);
            if (days < 0) {
                return false;
            }
        }
        return true;
    };

    var isEmpty = function (fromDate, toDate) {
        if (fromDate || toDate) {
            //return true;
            if (isValidDate(fromDate) == false || isValidDate(toDate) == false) {
                return false;
            }
        }
        return true;
    };
})();