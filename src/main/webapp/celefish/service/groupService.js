angular.module('celefishApp.services').factory('groupServiceImpl', ['$http', function($http) {
	var groupServiceImpl = {};
	
	groupServiceImpl.getGroupByCreator = function (creator) {
    	return $http.get("/groups/creator/" + creator);
    },
    groupServiceImpl.getNoGroupRoleByCreator = function (groupId, creator) {
    	return $http.get("/role/groupId/" + groupId + "/creator/" + creator);
    },
	groupServiceImpl.getGroupById = function (groupId) {
    	return $http.get("/group/" + groupId);
    },
    groupServiceImpl.getGroupByName = function (groupName) {
    	return $http.get("/group/name/" + groupName);
    },
    groupServiceImpl.insertGroup = function (group) {
    	return $http.post("/group/", group);
    },
    groupServiceImpl.updateGroup = function (group) {
    	return $http.put("/group/", group);
    },
    groupServiceImpl.deleteGroupById = function (id) {
    	return $http.delete("/group/" + id, {"headers": {'Content-type': 'application/json;charset=utf-8'}});
    },
    groupServiceImpl.batchInsertRoleGroupList = function (roleGroupList) {
    	return $http.post("/role/groups/", roleGroupList);
    },
    groupServiceImpl.batchDeleteRoleGroupList = function (roleGroupList) {
    	return $http.delete("/role/groups/", {"data":roleGroupList, "headers": {'Content-type': 'application/json;charset=utf-8'}});
    }
	
    return groupServiceImpl;
}]);

