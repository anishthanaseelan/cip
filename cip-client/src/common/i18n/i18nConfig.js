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

angular.module('jm.i18next').config(['$i18nextProvider', function (i18nextProvider) {
    /*.config(['$i18nextProvider', 'localeDetectorProvider', function($i18nextProvider, localeDetectorProvider){
        var lang = localeDetectorProvider.$get().getLocaleInfo().scrubbedLocale;*/
    //Retrieves language code from the browser
    var lang = navigator.languages ? navigator.languages[0] : (navigator.language || navigator.userLanguage);

    i18n.pluralExtensions.addRule("en", {
        name: "English",
        numbers: [0, 1, 2],
        plurals: function(n){
            return Number(n === 0 ? 0 : n > 1 ? -1 : n);
        }
    });

    i18nextProvider.options = {
        lng: lang,
        useCookie: false,
        useLocalStorage: false,
        useDataAttrOptions: true,
        fallbackLng: "en-US",
        resGetPath: "src/common/i18n/locales/__ns__.__lng__.json",
        load: 'current',
        getAsync: false
    };
} ]);

