var notifyTaskService = angular.module('notifyTaskService', []);

notifyTaskService.service('notifyEvent', function($http, $q) {
	return {
		//get not complete(0)
		getNotNotifyEvent: function() {
			var deferred = $q.defer();
			var promise = $http.get("/fish/rule/logs/closed/0");
			promise.then(function(answer) {
				answer.status = true;
				deferred.resolve(answer.data);
			}, function(error) {
				error.status = false;
				deferred.reject(error);
			});
			return deferred.promise;
		},
		
		//get complete(1) and dispath(2)
		getAllResolvedEvent: function() {
			var deferred = $q.defer();
			var promise = $http.get("/fish/rule/logs/closed/3");
			promise.then(function(answer) {
				answer.status = true;
				deferred.resolve(answer.data);
			}, function(error) {
				error.status = false;
				deferred.reject(error);
			});
			return deferred.promise;
		},
		
		getUnresolvedEventNumByMem: function(memId, lastUpdate) {
			var deferred = $q.defer();
			var promise = $http.get("fish/rule/logs/user/" + memId + "/unresolved?lastUpdate=" + lastUpdate);
			promise.then(function(answer) {
				answer.status = true;
				deferred.resolve(answer.data);
			}, function(error) {
				error.status = false;
				deferred.reject(error);
			});
			return deferred.promise;
		},

		getResolvedEventNumByMem: function(memId, lastUpdate) {
			var deferred = $q.defer();
			var promise = $http.get("fish/rule/logs/user/" + memId + "/resolved?lastUpdate=" + lastUpdate);
			promise.then(function(answer) {
				answer.status = true;
				deferred.resolve(answer.data);
			}, function(error) {
				error.status = false;
				deferred.reject(error);
			});
			return deferred.promise;
		},
		
		getLastUnresolvedEventByMem: function(memId, lastUpdate) {
			var deferred = $q.defer();
			var promise = $http.get("fish/rule/logs/user/" + memId + "?lastUpdate=" + lastUpdate);
			promise.then(function(answer) {
				answer.status = true;
				deferred.resolve(answer.data);
			}, function(error) {
				error.status = false;
				deferred.reject(error);
			});
			return deferred.promise;
		},
		
		getAllUnresolvedEventByMem: function(memId, lastUpdate) {
			var deferred = $q.defer();
			var promise = $http.get("fish/rule/logs/user/" + memId + "/unassigned?lastUpdate=" + lastUpdate);
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