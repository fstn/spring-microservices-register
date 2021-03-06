/**
 * Les dépendences du builder
 */
var pkg = require('./package.json');
var gulp = require('gulp');
var bower = require('bower');
var concat = require('gulp-concat');
var sass = require('gulp-sass');
var minifyCss = require('gulp-minify-css');
var rename = require('gulp-rename');
var runSequence = require('run-sequence');
var uglify = require('gulp-uglify');
var header = require('gulp-header');
var footer = require('gulp-footer');
var clean = require('gulp-clean');
var webserver = require('gulp-webserver');
var flatten = require('gulp-flatten');
var buildConfig = require('./build.config.js');
var sh = require('shelljs');
var dedupe = require('gulp-dedupe');
var karma = require('gulp-karma');
var protractor = require('gulp-protractor').protractor;
var webdriver_standalone = require('gulp-protractor').webdriver_standalone;
var webdriver_update = require('gulp-protractor').webdriver_update;
var chmod = require('gulp-chmod');
var removeHtmlComments = require('gulp-remove-html-comments');

/**
 * Execute les actions de build dans l'ordre
 */
gulp.task('build', function (callback) {
    runSequence('clean',
        'build-trad',
        'sass',
        'vendor-js',
        'app-js',
        'unminified-files',
        'html',
        'fonts',
        'vendor-css',
        'json-assets',
        'assets-js',
        'app-css',
        'images',
        'locales',
        'angular-locales',
        'favicon',
        'copy-dist-to-webapp',
        callback);
});

/**
 *
 * Supression des fichiers du precedent build
 *
 */
gulp.task('clean', function () {
    return gulp.src([buildConfig.distFolder + '/assets', buildConfig.distFolder + '/app', buildConfig.distFolder + '/favicon.ico'], {
            force: true
        })
        .pipe(clean());
});

/**
 * Compile les fichier scss en css et les dépose dans le répertoire /main/assets/css
 */
gulp.task('sass', function (done) {
    gulp.src(buildConfig.srcFolder + '/assets/scss/**/*.scss')
        .pipe(sass({
            errLogToConsole: true
        }))
        .pipe(chmod(755))
        .pipe(gulp.dest(buildConfig.srcFolder + '/assets/css'))
        .on('end', done);
});

/**
 * Minifie les fichiers css vendor
 */
gulp.task('vendor-css', function (done) {
    gulp.src(buildConfig.vendorCssFiles)
        .pipe(concat('vendor.css'))
        .pipe(minifyCss({
            keepSpecialComments: 0
        }))
        .pipe(rename({
            extname: '.min.css'
        }))
        .pipe(chmod(755))
        .pipe(gulp.dest(buildConfig.distFolder + '/assets/fonts'))
        .on('end', done);
});

/**
 * Minifie les fichiers css applicatifs
 */
gulp.task('app-css', function (done) {
    gulp.src(buildConfig.srcFolder + '/assets/css/*.css')
        .pipe(concat('app.css'))
        .pipe(minifyCss({
            keepSpecialComments: 0
        }))
        .pipe(rename({
            extname: '.min.css'
        }))
        .pipe(chmod(755))
        .pipe(gulp.dest(buildConfig.distFolder + '/assets/css'))
        .on('end', done);
});

/**
 * Concat, minifie et uglut le Javascript applicatif
 */
gulp.task('app-js', function () {
    console.log(buildConfig.excludedFilePattern().concat(buildConfig.appFiles));
    return gulp.src(buildConfig.excludedFilePattern().concat(buildConfig.appFiles))
        .pipe(concat('app.min.js'))
        .pipe(header(buildConfig.closureStart))
        .pipe(footer(buildConfig.closureEnd))
        .pipe(uglify())
        .pipe(header(buildConfig.banner, {
            pkg: pkg
        }))
        .pipe(chmod(755))
        .pipe(gulp.dest(buildConfig.distFolder + '/app'));
});

/**
 * Concat, minifie et uglut le Javascript applicatif
 */
gulp.task('unminified-files', function () {
    return gulp.src(buildConfig.excludeFromAppDist.unminifiedDistFiles)
        .pipe(chmod(755))
        .pipe(gulp.dest(buildConfig.distFolder));
});

/**
 * Concat et Minifie le Javascript des librairies vendor utilisés.
 */
gulp.task('vendor-js', function () {
    return gulp.src(buildConfig.vendorJavascriptFiles)
        .pipe(concat('vendor.min.js'))
        .pipe(chmod(755))
        .pipe(gulp.dest(buildConfig.distFolder + '/assets/lib'));
});


/**
 * Concat et Minifie les datas.
 */
gulp.task('assets-js', function () {
    return gulp.src(buildConfig.srcFolder+'/assets/js/')
        .pipe(chmod(755))
        .pipe(gulp.dest(buildConfig.distFolder+'/assets/js/'));
});


/**
 * Copie des fichiers html de l'application
 *
 */
gulp.task('html', function () {
    gulp.src(buildConfig.srcFolder + '/app/**/*.html')
        // And put it in the dist folder
        .pipe(removeHtmlComments())
        .pipe(chmod(755))
        .pipe(gulp.dest(buildConfig.distFolder + '/app'));
});

/**
 * Copie des fonts présentes dans les librairies
 */
gulp.task('fonts', function () {
    gulp.src(buildConfig.srcFolder + '/assets/lib/**/*.{eot,svg,ttf,otf,woff,woff2}')
        .pipe(flatten())
        .pipe(dedupe({
            same: false
        }))
        .pipe(chmod(755))
        .pipe(gulp.dest(buildConfig.distFolder + '/assets/fonts'));
});

/**
 * Copie des images
 */
gulp.task('images', function () {
    gulp.src(buildConfig.srcFolder + '/assets/img/**/*')
        .pipe(chmod(755))
        .pipe(gulp.dest(buildConfig.distFolder + '/assets/img/'));
});

/**
 * Copie du favicon
 */
gulp.task('favicon', function () {
    gulp.src(buildConfig.srcFolder +'/favicon.ico')
        .pipe(gulp.dest(buildConfig.distFolder));
});

/**
 * Copie des langues
 */
gulp.task('locales', function () {
    gulp.src(buildConfig.srcFolder + '/assets/locale/**/*.json')
        .pipe(chmod(755))
        .pipe(gulp.dest(buildConfig.distFolder + '/assets/locale/'));
});

/**
 *
 * Copie des locales pour angular $locale
 *
 */
gulp.task('angular-locales', function () {
    gulp.src(buildConfig.localeJsFiles)
        .pipe(chmod(755))
        .pipe(gulp.dest(buildConfig.distFolder +'/assets/lib/angular-i18n'));
});



gulp.task('serve', function () {
    runSequence('serve-src',
        'serve-dist');
});

gulp.task('serve-src', function(){
    gulp.src(buildConfig.srcFolder)
        .pipe(webserver({
            port: 3000
        }));
});

gulp.task('serve-dist', function(){
    gulp.src(buildConfig.distFolder)
        .pipe(webserver({
            port: 3001
        }));
});



/**
 * Obsérve les modification des scss et compile en css
 */
gulp.task('watch', function () {
    gulp.watch(buildConfig.srcFolder + '/assets/scss/**/*.scss', ['sass']);
});

/**
 *
 * Supression des traductions fr
 *
 */
gulp.task('clean-trad-fr', function () {
    return gulp.src(buildConfig.srcFolder + '/assets/locale/fr/**.json', {
            force: true
        })
        .pipe(clean());
});

/**
 *
 * Supression des traductions en
 *
 */
gulp.task('clean-trad-en', function () {
    return gulp.src(buildConfig.srcFolder + '/assets/locale/en/**.json', {
            force: true
        })
        .pipe(clean());
});

/**
 * Method to copy json file from assets
 */
gulp.task('json-assets',function(){
    return gulp.src(buildConfig.srcFolder + '/assets/json/**/*.json')
        .pipe(gulp.dest(buildConfig.distFolder + '/assets/json'))
});

/**
 * Génération des traductions fr (sans nettoyage)
 *
 */
gulp.task('build-trad-fr-only', function () {
    return gulp.src(['!'+buildConfig.srcFolder +
        '/assets/locale/{**-fr,**-en.json,**-de.json,fr.json,en.json,de.json}.json',
            buildConfig.srcFolder + '/assets/locale/**.json'])
        .pipe(rename(function (path) {
            path.basename += "-fr";
        }))
        .pipe(gulp.dest(buildConfig.srcFolder + '/assets/locale/fr'));
});

/**
 *
 * Génération des traductions en (sans nettoyage)
 *
 */
gulp.task('build-trad-en-only', function () {
    return gulp.src(['!'+buildConfig.srcFolder +
        '/assets/locale/{**-fr,**-en.json,**-de.json,fr.json,en.json,de.json}.json',
            buildConfig.srcFolder + '/assets/locale/**.json'])
        .pipe(rename(function (path) {
            path.basename += "-en";
        }))
        .pipe(gulp.dest(buildConfig.srcFolder + '/assets/locale/en'));
});

/**
 * Génération des trads fr (avec nettoyage)
 */
gulp.task('build-trad-fr', function (callback) {
    runSequence('clean-trad-fr',
        'build-trad-fr-only',
        callback);
});

/**
 * Génération des trads en (avec nettoyage)
 */
gulp.task('build-trad-en', function (callback) {
    runSequence('clean-trad-en',
        'build-trad-en-only',
        callback);
});

/**
 * Test unitaire jasmine
 */
gulp.task('test', function () {

    /**Ajout des fihcier de test **/
    var allVendorFiles = buildConfig.vendorJavascriptFiles.slice();
    allVendorFiles.push(buildConfig.srcFolder + '/assets/lib/angular-mocks/angular-mocks.js');
    var allAppFiles = buildConfig.appFiles.slice();
    allAppFiles = allAppFiles.concat(buildConfig.excludeFromAppDist.unitTest);
    allAppFiles = allAppFiles.concat(buildConfig.excludeFromAppDist.unminifiedDistFiles);
    var testFiles = allVendorFiles.concat(allAppFiles);

    return gulp.src(testFiles)
        .pipe(karma({
            configFile: 'karma.conf.js',
            action: 'run'
        }))
        .on('error', function (err) {
            console.log(err);
            this.emit('end');
        });
});


/**
 * Génération des trads fr + en
 */
gulp.task('build-trad', function (callback) {
    runSequence('build-trad-fr',
        'build-trad-en',
        callback);
});

/**
 * Copy generated dist to output folder application
 */
gulp.task('copy-dist-to-webapp', function() {
    var distFolderPattern = buildConfig.distFolder+"/**/*";
    var copyDestination = buildConfig.distOutputCopyDestination+"/" +buildConfig.distOutputCopyDestinationName;
    return gulp.src(distFolderPattern)
        .pipe(gulp.dest(copyDestination));
});

/**
 * Simple function to remove item from array by value.
 * @param array
 * @returns array without removed items.
 * @private
 */
function _removeValueFromArray(arr) {
    var what, a = arguments,
        L = a.length,
        ax;
    while (L > 1 && arr.length) {
        what = a[--L];
        while ((ax = arr.indexOf(what)) !== -1) {
            arr.splice(ax, 1);
        }
    }
    return arr;
}

gulp.task('webdriver_update', webdriver_update);

gulp.task('webdriver_standalone', webdriver_standalone);

gulp.task('serverhttp',function(){
    var stream = gulp.src(buildConfig.srcFolder)
        .pipe(webserver({
            port: 4000
        }));
});

/**
 * Execute l'action de test e2e
 */
gulp.task('e2e', ['webdriver_update'], function (callback) {

    var stream = gulp.src([buildConfig.distFolder, buildConfig.testFolder])
        .pipe(webserver({
            port: 4000
        }));

    var fileStream = gulp.src(buildConfig.excludeFromAppDist.e2e);
    fileStream.pipe(protractor({
        configFile: "protractor.conf.js",
        args: ['--baseUrl', 'http://127.0.0.1:4000']
    })).on('error', function (e) {
        stream.emit('kill');
        throw (e);
    }).on('end', function () {
        stream.emit('kill');
    });
});
