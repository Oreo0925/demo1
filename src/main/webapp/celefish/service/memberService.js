angular.module('celefishApp.services').factory('memberServiceImpl', ['$http', function ($http) {
	
	var memberServiceImpl = {};
	
    memberServiceImpl.getMemberById = function (id) {
    	return $http.get("/user/id/" + id);
    },
    memberServiceImpl.getMemberByParentId = function (parentId) {
    	return $http.get("/users/parentId/" + parentId);
    },
    memberServiceImpl.getMemberListByRoleId = function (roleId) {
    	return $http.get("/users/roleId/" + roleId);
    },
    memberServiceImpl.getNoRoleMemberByParentId = function (roleId, parentId) {
    	return $http.get("/users/roleId/" + roleId + "/parentId/" + parentId);
    },
    memberServiceImpl.getRoleByCreator = function (creator) {
    	return $http.get("/role/creator/" + creator);
    },
    memberServiceImpl.batchInsertMemberRoleList = function (memberRoleList) {
    	return $http.post("/user/roles/", memberRoleList);
    },
    memberServiceImpl.batchDeleteMemberRoleList = function (memberRoleList) {
    	return $http.delete("/user/roles/", {"data":memberRoleList, "headers": {'Content-type': 'application/json;charset=utf-8'}});
    },
    memberServiceImpl.insertRole = function (role) {
    	return $http.post("/role/", role);
    },
    memberServiceImpl.updateRole = function (role) {
    	return $http.put("/role/", role);
    },
    memberServiceImpl.deleteRole = function (roleId) {
    	return $http.delete("/role/" + roleId, {"headers": {'Content-type': 'application/json;charset=utf-8'}});
    },
    memberServiceImpl.getRoleByName = function (name) {
    	return $http.get("/role/name/" + name);
    }
    
    return memberServiceImpl;
}]);



