/**
 * Configuration du projet.
 */
var pkg = require('./package.json');

module.exports = {
    /**
     * Header de la distribution.
     */
    banner:
    '/*!\n' +
    ' * Copyright 2015 itesoft.\n' +
    ' * http://itesoft.com/\n' +
    ' *\n' +
    ' * <%= pkg.name %>, v<%= pkg.version %>\n' +
    ' * View Services information registered.*/\n' ,

    closureStart: '(function() {var RELEASE_VERSION="1.0.0";\n',
    closureEnd: '\n})();',
    
    distFolder:'dist',
    srcFolder:'main',
    testFolder: 'test',

    /**
     * Variables pour gérer le deploiement de l'ihm dans un war
     */
    distOutputCopyDestinationName:'mircoservices-viewer',
    distOutputCopyDestination:'./../microservices-register-springboot/src/main/webapp',


    /**
     * Liste des fichiers JS de l'application qui seront minifier pour la prod.
     */
    appFiles: [
        '!main/app/**/*Test.js', // Exclude test files
        '!main/app/**/*.demo.js', // Exclude demo files
        'main/app/app.module.js',
        'main/app/app.route.js',
        'main/app/app.translation.js',
        'main/app/**/*.js'
    ],
    excludeFromAppDist: {

        unitTest: [
            'test/unit/**/*.js'
        ],
        e2e: [
            'test/e2e/**/configurationTest.js'
        ],
        unminifiedDistFiles: [
            'main/app/**/app.constant.js'
        ]
    },

    excludedFilePattern: function () {
        var result = [];
        for (var key  in this.excludeFromAppDist) {
            for(var i = 0; i < this.excludeFromAppDist[key].length;i++){
                result.push('!' + this.excludeFromAppDist[key][i]);
            }
        }
        return result;
    },

    /**
     * Liste des librairies minifié à utiliser en prod
     */
    vendorJavascriptFiles: [
        'main/assets/lib/angular-common/dist/assets/lib/vendor.min.js',
        'main/assets/lib/angular-common/dist/app/itesoft.min.js',
        'main/assets/lib/angular-translate-loader-partial/angular-translate-loader-partial.min.js',
        'main/assets/lib/angular-translate-loader-static-files/angular-translate-loader-static-files.min.js',
        'main/assets/lib/angular-dynamic-locale/dist/tmhDynamicLocale.js',
        'main/assets/lib/angular-local-storage/dist/angular-local-storage.min.js',
        'main/assets/lib/angular-strap/dist/angular-strap.min.js',
        'main/assets/lib/angular-strap/dist/angular-strap.tpl.min.js',
        'main/assets/lib/angular-tree-control/angular-tree-control.js',
        'main/assets/lib/angular-tree-dnd/dist/ng-tree-dnd.js'
    ],
    /**
     *
     * Fichiers de locales pour les formats, les monnaies, les jours, mois et autres.
     * A ne PAS minifier pour l'utilisation d'Angular Dynamic Locale
     *
     */
    localeJsFiles: [
        'main/assets/lib/angular-i18n/angular-locale_fr.js',
        'main/assets/lib/angular-i18n/angular-locale_en.js',
        'main/assets/lib/angular-i18n/angular-locale_de.js'
    ],
    vendorCssFiles: [
        'main/assets/lib/font-awesome/css/font-awesome.min.css',
        'main/assets/lib/angular-common/dist/assets/fonts/itesoft-bundle.min.css',
        'main/assets/lib/angular-tree-control/css/tree-control-attribute.css',
        'main/assets/lib/angular-tree-dnd/dist/ng-tree-dnd.css'
    ]
};
