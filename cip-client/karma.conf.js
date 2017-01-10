// Karma configuration
// Generated on Tue Oct 04 2016 15:40:10 GMT-0500 (Central Daylight Time)

module.exports = function (config) {

    config.set({

        // base path that will be used to resolve all patterns (eg. files, exclude)
        basePath: '',


        // frameworks to use
        // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
        frameworks: ['jasmine'],


        // list of files / patterns to load in the browser
        files: [
            //'./bower_components/angular/angular.js',
            //'./bower_components/angular-ui-router/release/angular-ui-router.min.js',
            //'./bower_components/angular-mocks/angular-mocks.js',
            //  'src/**/*.spec.js',
            //'src/app/carrierMaintenance/carrierListController.js'

            "bower_components/jquery/dist/jquery.min.js",
            "bower_components/bootstrap/dist/js/bootstrap.min.js",
            "bower_components/moment/moment.js",
            "bower_components/bootstrap-daterangepicker/daterangepicker.js",
            "bower_components/angular/angular.min.js",
            "bower_components/angular-moment/angular-moment.min.js",
            "bower_components/angular-animate/angular-animate.min.js",
            "bower_components/angular-bootstrap/ui-bootstrap-tpls.min.js",
            "bower_components/angular-sanitize/angular-sanitize.min.js",
            "bower_components/angular-resource/angular-resource.min.js",
            "bower_components/i18next/i18next.min.js",
            "bower_components/ng-i18next/dist/ng-i18next.min.js",
            "bower_components/angular-material/angular-material.min.js",
            "bower_components/angular-ui-router/release/angular-ui-router.min.js",
            'bower_components/angular-mocks/angular-mocks.js',
            "bower_components/angular-ui-grid/ui-grid.min.js",
            "bower_components/toastr/toastr.min.js",
            "bower_components/lodash/dist/lodash.min.js",
            "bower_components/angular-ui-switch/angular-ui-switch.min.js",
            "bower_components/spin.js/spin.js",
            "bower_components/angular-spinner/angular-spinner.min.js",
            "bower_components/angular-loading-spinner/angular-loading-spinner.js",
            "bower_components/ng-bs-daterangepicker/src/ng-bs-daterangepicker.js",
            "bower_components/d3/d3.min.js",
            "bower_components/nvd3/build/nv.d3.min.js",
            'src/app/**/*.spec.js',
            'src/common/**/*.js',
            'src/app/**/*.module.js',
            'src/app/**/*.js',
            "app.js"

        ],


        // list of files to exclude
        exclude: [],


        // preprocess matching files before serving them to the browser
        // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
        preprocessors: {},


        // test results reporter to use
        // possible values: 'dots', 'progress'
        // available reporters: https://npmjs.org/browse/keyword/karma-reporter
        reporters: ['progress'],


        // web server port
        port: 9876,


        // enable / disable colors in the output (reporters and logs)
        colors: true,


        // level of logging
        // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
        logLevel: config.LOG_INFO,


        // enable / disable watching file and executing tests whenever any file changes
        autoWatch: true,


        // start these browsers
        // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
        browsers: ['Chrome'],


        // Continuous Integration mode
        // if true, Karma captures browsers, runs the tests and exits
        singleRun: false
    })
};
