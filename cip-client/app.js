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
    angular
        .module('cip', [
            'ngResource',
            'ui.router',
            'ui.grid',
            'ui.grid.edit',
            'ui.bootstrap',
            'ngAnimate',
            'jm.i18next',
            'ui.grid.pagination',
            'ui.grid.resizeColumns',
            'ui.grid.moveColumns',
            'ui.grid.autoResize',
            'ui.grid.grouping',
            'ui.grid.selection',
            'ui.grid.expandable',
            'uiSwitch',
            'ngBootstrap',
            'ui.bootstrap',
            'ngLoadingSpinner',
            'cip.commonUtils',
            'cip.customDirectives',
            'cip.carrierMaintenance',
            'cip.nodeMaintenance',
            'cip.shipments',
            'cip.dashBoard',
            'angularMoment'
        ])
        //.value('toastr', toastr)

        .controller('navigationController', ['$scope', '$location', '$rootScope', 'modalFactory',
            function navigationController($scope, $location, $rootScope, modalFactory) {

            $scope.isActive = function (viewLocation) {
                return viewLocation === $location.path();
            };

            $rootScope.$on('noRecords', function () {
                modalFactory.open('md', 'src/app/templates/popUpModal.html', null);
            });
        }]);
})();