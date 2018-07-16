angular.module('celefishApp.services').factory('opDaoImpl', ['$http', function($http) {
	var opDaoImpl = {};
	
	opDaoImpl.update = function (data) {
    	return $http.put("/device/" + data.id, data);
	},
	opDaoImpl.getDeviceById = function (deviceId) {
    	return $http.get("/device/" + deviceId);
    },
    opDaoImpl.getDevicesByParentId = function (parentId) {
    	return $http.get("/devices/parentId/" + parentId);
    },
    opDaoImpl.controlDevices = function (deviceId, data) {
    	return $http.put("/fish/device/" + deviceId +  "/control/", data);
    }
    return opDaoImpl;
}]);

