var accountService = angular.module('accountService', []);

accountService.service('account', function($http, $q) {
	return {
		doLogin : function(data) {
			var deferred = $q.defer();
			var promise = $http.post("/doLogin", data);
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