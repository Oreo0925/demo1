var agentflowService = angular.module('agentflowService', []);

agentflowService.service('afProcess', function($http, $q) {
	return {
		createProcess : function(ip) {
			var deferred = $q.defer();
			var promise = $http.get(ip);
			promise.then(function(answer) {
				answer.status = true;
				deferred.resolve(answer.data);
			}, function(error) {
				error.status = false;
				deferred.reject(error);
			});
			return deferred.promise;
		}
	}
});