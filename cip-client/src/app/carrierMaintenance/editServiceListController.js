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
        .module('cip.carrierMaintenance')
        .controller('editServiceListController', editServiceListController);

    editServiceListController.$inject = ['$state', 'carrierMaintenanceService'];

    /*@ngInject*/
    function editServiceListController($state, carrierMaintenanceService) {

        var vm = this;
        vm.saveUpdatedServiceList = saveUpdatedServiceList;
        vm.cancelUpdatedServiceList = cancelUpdatedServiceList;


        function saveUpdatedServiceList() {
            /**
             * Code implement for saving updated service list data
             */
            var payload = {
                Scac: vm.SCAC,
                labelUrl: vm.labelURL,
                maximumLength: vm.maxLength,
                maximumWidth: vm.maxWidth,
                maximumHeight: vm.maxHeight,
                selectedDocumentFormatOption: vm.selectedDocumentFormatOption.name,
                selectedDocumentTypeOption: vm.selectedDocumentTypeOption.name,
                selectedLabelFormatOption: vm.selectedLabelFormatOption.name,
                weightLbs: vm.wtLbs,
                weightOz: vm.wtOz
            };
            console.log(payload);
            carrierMaintenanceService
                .saveServiceTypeDetails({SCACId: vm.SCAC}, payload)
                .$promise.then(savedServiceListDetailsSuccess, displayError);
        }

        function savedServiceListDetailsSuccess() {
            console.log('Details saved successfully!!');
        }

        function displayError(reason) {
            if (reason.data && reason.data.status.errors) {
                toastr.error(reason.data.status.errors[0].errorDescription);
            }
            else {
                toastr.error('Application has encountered an error! Please contact Administrator.');
            }
        }

        function cancelUpdatedServiceList() {
            $state.go('carrierList');
        }
    }
})();