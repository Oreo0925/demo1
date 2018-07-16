var rootScopeService = angular.module('rootScopeService', []);

rootScopeService.service('rootScope', function($rootScope) {
	return {
		setRootScope : function(key, type, data) {
            this[type] = data;
            $rootScope.$broadcast(key, {
                type: type, data: data 
            });
        }
	}
});