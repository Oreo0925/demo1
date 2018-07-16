var fishRuleLogService = angular.module('fishRuleLogService', []);

fishRuleLogService.service('questionnaire', function($http, $q) {
	return {
		callLater: function(fishRuleLogId, groupId) {
			var deferred = $q.defer();
			var promise = $http.put("/fish/rule/log/" + fishRuleLogId + "/replayLater", {'groupId': groupId});
			promise.then(function(answer) {
				answer.status = true;
				deferred.resolve(answer.data);
			}, function(error) {
				error.status = false;
				deferred.reject(error);
			});
			return deferred.promise;
		},
		
		closeEvent: function(fishRuleLogId, data) {
			var deferred = $q.defer();
			var promise = $http.put("/fish/rule/log/" + fishRuleLogId + "/afProId/closed", data);
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

fishRuleLogService.service('historyDeviceNotify', function($http, $q) {
	return {
		getResolvedList: function(deviceId, lastUpdate) {
			var deferred = $q.defer();
			var promise = $http.get("/fish/rule/logs/device/" + deviceId + "/resolvedlist?lastUpdate=" + lastUpdate);
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