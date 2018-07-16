var deviceDataService = angular.module('deviceDataService', []);

deviceDataService.service('deviceData', function($http, $q) {
	class Adapter {
		async getInfluxData(url, db, sql) {
			var link = url + '/query?db=' + db + '&epoch=ms&q=' + sql;
			console.log('111');
		    const response = await fetch(link);
		    console.log('222');
		    return await response.json();
		}
	}
	
	return {
		getInfluxData : function(deviceId, columns, conditions) {
			var url = '/v1/device/' + deviceId + '/rawdata';
			if (columns || conditions) {
				url = url + '?';
				if (columns) {
					url = url + 'columns=' + columns;
				}
				if (conditions) {
					if (columns) {
						url = url + '&';
					}
					url = url + 'conditions=' + conditions;
				}
			}
			
			var deferred = $q.defer();
			var promise = $http.get(url);
			promise.then(function(answer) {
				answer.status = true;
				deferred.resolve(answer.data);
			}, function(error) {
				error.status = false;
				deferred.reject(error);
			});
			return deferred.promise;
		},
		
		getInfluxData2 : function(url, db, sql) {
			var link = url + '/query?db=' + db + '&epoch=ms&q=' + sql;
			var deferred = $q.defer();
			var promise = $http.get(link);
			promise.then(function(answer) {
				answer.status = true;
				deferred.resolve(answer.data);
			}, function(error) {
				error.status = false;
				deferred.reject(error);
			});
			return deferred.promise;
		},
		
		getInfluxData3: function(url, db, sql) {
			(async function iife2() {
				console.log('before showGitHubUser');
				var adapter = new Adapter();
				var response =  adapter.getInfluxData(url, db, sql);
				console.log('after showGitHubUser');
			})();
		}
	}
});