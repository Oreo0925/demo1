var deviceService = angular.module('deviceService', []);

deviceService.service('device', function($http, $q) {
	return {
		getAllDevice : function() {
			var deferred = $q.defer();
			var promise = $http.get("/devices");
			promise.then(function(answer) {
				answer.status = true;
				deferred.resolve(answer.data);
			}, function(error) {
				error.status = false;
				deferred.reject(error);
			});
			return deferred.promise;
		},
		
		getDeviceById : function(id) {
			var deferred = $q.defer();
			var promise = $http.get("/device/" + id);
			promise.then(function(answer) {
				deferred.resolve(answer.data);
			}, function(error) {
				deferred.reject(error);
			});
			return deferred.promise;
		}
	}
});