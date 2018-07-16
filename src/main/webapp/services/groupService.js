var groupService = angular.module('groupService', []);

groupService.service('groupList', function($http, $q) {
	return {
		getAllGroup : function() {
			var deferred = $q.defer();
			var promise = $http.get("/groups");
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