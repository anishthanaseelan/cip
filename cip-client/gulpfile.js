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
var gulp = require('gulp'),
    gutil = require('gulp-util'),
    uglify = require('gulp-uglify'),
    del = require('del'),
    changed = require('gulp-changed'),
    rename = require('gulp-rename'),
    war = require('gulp-war'),
    inject = require('gulp-inject'),
    concat = require('gulp-concat'),
    zip = require('gulp-zip'),
    bump = require('gulp-bump'),
    runSequence = require('run-sequence'),
    sourcemaps = require("gulp-sourcemaps");

var config = require('./build.config.js');

gulp.task('copyIndex', function () {
    gulp.src(config.app_files.homePage)
        .pipe(gulp.dest(config.build_dir))
});

gulp.task('minifyAndCopyAppFiles', function () {
    gulp.src(config.app_files.js, {base: '.'})
        .pipe(gulp.dest(config.build_dir))
});

gulp.task('copyAppJsFile', function () {
    gulp.src(config.app_files.ajs)
        .pipe(gulp.dest(config.build_dir))
});

gulp.task('copyCommonFiles', function () {
    gulp.src('src/common/**/*')
        .pipe(gulp.dest('build/src/common'))
});

gulp.task("copyVendorFiles", function () {
    return gulp.src(config.vendor_files.js
        .concat(config.vendor_files.fonts)
        .concat(config.vendor_files.css), {base: "."})
        .pipe(changed(config.build_dir))
        .pipe(gulp.dest(config.build_dir, {cwd: '.'}));
});

gulp.task('copyAppCssFile', function () {
    gulp.src(config.app_files.acss)
        .pipe(gulp.dest(config.build_dir))
});

gulp.task('copyAppHtmls', function () {
    gulp.src(config.app_files.ahtml, {base: '.'})
        .pipe(gulp.dest(config.build_dir))
});

gulp.task('copyConfig', function () {
    gulp.src(config.app_files.javaConfigFile)
        .pipe(gulp.dest(config.build_dir))
});

gulp.task('copyStates', function () {
    gulp.src('states.txt')
        .pipe(gulp.dest(config.build_dir))
});

gulp.task('clean', function clean(done) {
    return del([config.build_dir, config.package_dir], done);
});

gulp.task('buildIndexFile', function () {
    gulp.src(config.app_files.homePage)
        .pipe(inject(gulp.src(config.vendor_files.js.concat('app.js').concat(config.vendor_files.css).
                concat(config.app_files.ccss).concat(config.app_files.acss).concat(config.app_files.javaConfigFile),
            {read: false}), {relative: true}))
        .pipe(gulp.dest(config.build_dir))
});

gulp.task('copyJson', function () {
    gulp.src('src/common/**/*.json')
        .pipe(gulp.dest('build/src/common'))
});

gulp.task('build', function (cb) {
    runSequence('clean', ['copyStates', 'copyConfig', 'copyAppCssFile', 'copyVendorFiles', 'copyCommonFiles',
        'copyAppJsFile', 'compile-app-lib', 'copyAppHtmls', 'copyJson', 'buildIndexFile'], cb)
});

gulp.task('war', function () {
    gulp.src([config.build_dir + '/*'])
        .pipe(war({
            welcome: 'index.html',
            displayName: 'CIP'
        }))
        .pipe(gulp.dest('cip-client.war'))
});

gulp.task('compress', function () {
    return gulp.src('build/**/*')
        .pipe(zip('cip-client.zip'))
        .pipe(gulp.dest(config.package_dir))
});

gulp.task('package', function (cb) {
    runSequence('clean', ['build'], ['compress'], cb);
});

gulp.task('bump-patch', function(){
   gulp.src(['package.json', 'bower.json'])
       .pipe(bump({type: 'patch'}))
       .pipe(gulp.dest('./'))
});

gulp.task('bump-minor', function(){
    gulp.src(['package.json', 'bower.json'])
        .pipe(bump({type: 'minor'}))
        .pipe(gulp.dest('./'))
});

gulp.task('bump-major', function(){
    gulp.src(['package.json', 'bower.json'])
        .pipe(bump({type: 'major'}))
        .pipe(gulp.dest('./'))
});

gulp.task('default', ['build']);

gulp.task("compile-app-lib", function () {
    return gulp.src(config.app_files.cjs.concat(config.app_files.ajs).concat(config.app_files.modules).concat(config.app_files.js))
        .pipe(sourcemaps.init())
        .pipe(concat('app.js'))
        .pipe(rename('app.js'))
        .pipe(uglify())
        .pipe(sourcemaps.write("./"))
        .pipe(gulp.dest(config.build_dir));
});