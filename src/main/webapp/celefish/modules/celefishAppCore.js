'use strict';
angular.module('celefishApp.core', ['toaster', 'pascalprecht.translate'])
	.config(['$translateProvider', function($translateProvider) {
     $translateProvider.useSanitizeValueStrategy(null);
	 $translateProvider.useStaticFilesLoader({
        prefix: '../celefish/i18n/',
        suffix: '.json'
    });
}]);