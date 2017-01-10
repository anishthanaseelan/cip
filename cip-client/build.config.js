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

//This file contains the configuration for build
module.exports = {

    build_dir: 'build',
    package_dir: 'bin',

    app_files: {
        js: ['src/app/**/*.js', '!*.module.js'],
        ajs: ['app.js'],
        cjs: ['src/common/**/*.js'],
        modules: ['src/app/**/*.module.js'],

        ahtml: ['src/app/**/*.html'],
        homePage: ['index.html'],

        acss: ['app.css'],
        ccss: ['src/common/js/nv.d3.min.css'],

        javaConfigFile: "appConfig.js"
    },

    vendor_files: {
        js: [
            'bower_components/jquery/dist/jquery.min.js',
            'bower_components/bootstrap/dist/js/bootstrap.min.js',
            'bower_components/moment/moment.js',
            'bower_components/bootstrap-daterangepicker/daterangepicker.js',
            'bower_components/angular/angular.min.js',
            'bower_components/angular-moment/angular-moment.min.js',
            'bower_components/angular-animate/angular-animate.min.js',
            'bower_components/angular-bootstrap/ui-bootstrap-tpls.min.js',
            'bower_components/angular-sanitize/angular-sanitize.min.js',
            'bower_components/angular-resource/angular-resource.min.js',
            'bower_components/i18next/i18next.min.js',
            'bower_components/ng-i18next/dist/ng-i18next.min.js',
            'bower_components/angular-material/angular-material.min.js',
            'bower_components/angular-ui-router/release/angular-ui-router.min.js',
            'bower_components/angular-ui-grid/ui-grid.min.js',
            'bower_components/toastr/toastr.min.js',
            'bower_components/lodash/dist/lodash.min.js',
            'bower_components/angular-ui-switch/angular-ui-switch.min.js',
            'bower_components/spin.js/spin.js',
            'bower_components/angular-spinner/angular-spinner.min.js',
            'bower_components/angular-loading-spinner/angular-loading-spinner.js',
            'bower_components/ng-bs-daterangepicker/src/ng-bs-daterangepicker.js',
            'bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js',
            'bower_components/d3/d3.min.js',
            'bower_components/nvd3/build/nv.d3.min.js'

        ],
        css: [
            'bower_components/bootstrap/dist/css/bootstrap.min.css',
            'bower_components/angular-bootstrap/ui-bootstrap-csp.css',
            'bower_components/font-awesome/css/font-awesome.min.css',
            'bower_components/toastr/toastr.min.css',
            'bower_components/angular-material/angular-material.min.css',
            'bower_components/angular-ui-grid/ui-grid.min.css',
            'bower_components/angular-ui-switch/angular-ui-switch.min.css',
            'bower_components/bootstrap-daterangepicker/daterangepicker-bs3.css',
            'bower_components/bootstrap-daterangepicker/daterangepicker.css',
            'bower_components/nvd3/build/nv.d3.min.css',
            'bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css'

        ],
        fonts: [
            'bower_components/bootstrap/dist/fonts/glyphicons-halflings-regular.woff2',
            'bower_components/bootstrap/dist/fonts/glyphicons-halflings-regular.woff',
            'bower_components/bootstrap/dist/fonts/glyphicons-halflings-regular.ttf',
            'bower_components/font-awesome/fonts/fontawesome-webfont.ttf',
            'bower_components/font-awesome/fonts/fontawesome-webfont.woff',
            'bower_components/font-awesome/fonts/fontawesome-webfont.woff2',
            'bower_components/angular-ui-grid/ui-grid.woff',
            'bower_components/angular-ui-grid/ui-grid.ttf'
        ]
    }
};
