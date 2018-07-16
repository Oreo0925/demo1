angular.module('celefishApp.core').controller('AddRoleCtrl', ['$scope', '$route', '$element', 'title', 'close', 'variableService', 'memberServiceImpl', 'tableHeader', 'toaster',
		function($scope, $route, $element, title, close, variableService, memberServiceImpl, tableHeader, toaster) {

   $scope.name = null;
   $scope.age = null;
   $scope.title = title;
   $scope.headName = tableHeader.headName;
   $scope.headDescription = tableHeader.headDescription;
   $scope.headPriority = tableHeader.headPriority;
   $scope.confirm = function() {
 	close({
      name: $scope.name,
      description: $scope.description,
      priority: $scope.priority
    }, 500);
 	//insert role.
 	memberServiceImpl.insertRole({"name": $scope.name, "description": $scope.description, "creater": variableService.id, "priority": $scope.priority})
 		.then(function(response) {
 			$route.reload();
 			toaster.success('success!');
		}, function (error) {
	 });
   };
   $scope.cancel = function() {
     $element.modal('hide');
     close({
	      name: $scope.name,
	      description: $scope.description,
	      priority: $scope.priority
 	 }, 500); // close, but give 500ms for bootstrap to animate
   };

}]);

angular.module('celefishApp.core').controller('ModifyRoleCtrl', ['$scope', '$route', '$element', 'title', 'close', 'variableService', 'role', 'memberServiceImpl', 'tableHeader', 'toaster',
	function($scope, $route, $element, title, close, variableService, role, memberServiceImpl, tableHeader, toaster) {
	$scope.title = title;
	$scope.name = role.name;
	$scope.description = role.description;
	$scope.priority = role.priority;
    $scope.headName = tableHeader.headName;
    $scope.headDescription = tableHeader.headDescription;
    $scope.headPriority = tableHeader.headPriority;
	$scope.confirm = function() {
		close({
		  name: $scope.name,
		  description: $scope.description,
		  priority: $scope.priority
		}, 500);
		memberServiceImpl.updateRole({"id": role.id, "name": $scope.name, "description": $scope.description, "creater": variableService.id, "priority": $scope.priority})
			.then(function(response) {
				$route.reload();
				toaster.success('success!');
			}, function (error) {
	    });
	};
	$scope.cancel = function() {
		$element.modal('hide');
		close({
		  name: $scope.name,
		  description: $scope.description,
		  priority: $scope.priority
		}, 500); 
	};
}]);


angular.module('celefishApp.core').controller('AddMemberRoleCtrl', ['$scope', '$route', '$element', 'title', 'tableHeader', 'close', 'variableService', 'memberServiceImpl', 'toaster',
	function($scope, $route, $element, title, tableHeader, close, variableService, memberServiceImpl, toaster) {
	
	$scope.title = title;
	$scope.serialNumber = tableHeader.serialNumber;
	$scope.memberId = tableHeader.memberId;
	$scope.memberName = tableHeader.memberName;
	$scope.memberAccount = tableHeader.memberAccount;
	
	memberServiceImpl.getNoRoleMemberByParentId(JSON.parse($('input[name*=roles]:checked').val()).id, variableService.id)
		.then(function(response) {
			$scope.members = response.data.data;
		}, function (error) {
	});
	
	$scope.confirm = function() {
		var memberRoleList = [];
		angular.forEach($scope.members, function(member) {
			if (member.selected) {
				var memberRole = {"memberId":0, "roleId":JSON.parse($('input[name*=roles]:checked').val()).id};
				memberRole.memberId = member.id;
				memberRoleList.push(memberRole);
			}
		});
		memberServiceImpl.batchInsertMemberRoleList(memberRoleList)
			.then(function(response) {
				if (!response.data.success) {
					toaster.error('已有人員有該權限!');
				} else {
					$route.reload();
					toaster.success('Success!');
				}
			}, function (error) {
		});
	};
	
	$scope.cancel = function() {
		$element.modal('hide');
	};
	
}]);

angular.module('celefishApp.core').controller('AddRoleGroupCtrl', ['$scope', '$route', '$element', 'title', 'tableHeader', 'close', 'variableService', 'groupServiceImpl', 'memberServiceImpl', 'toaster',
	function($scope, $route, $element, title, tableHeader, close, variableService, groupServiceImpl, memberServiceImpl, toaster) {
	
	$scope.title = title;
	$scope.serialNumber = tableHeader.serialNumber;
	$scope.roleOfName = tableHeader.roleOfName;
	$scope.roleDescription = tableHeader.roleDescription;
	$scope.rolePriority = tableHeader.rolePriority;
	
	//show role list on modal.
	groupServiceImpl.getNoGroupRoleByCreator(JSON.parse($('input[name*=groups]:checked').val()).id, variableService.id)
		.then(function(response) {
			$scope.roles = response.data.data;
		}, function (error) {
	});
	
	$scope.confirm = function() {
		var roleGroupList = [];
		angular.forEach($scope.roles, function(role) {
			if (role.selected) {
				var roleGroup = {"roleId":0, "groupId":JSON.parse($('input[name*=groups]:checked').val()).id};
				roleGroup.roleId = role.id;
				roleGroupList.push(roleGroup);
			}
		});
		groupServiceImpl.batchInsertRoleGroupList(roleGroupList)
			.then(function(response) {
				if (!response.data.success) {
					toaster.error('角色已有該區域管轄權!');
				} else {
					toaster.success('Success!');
					$route.reload();
				}
			}, function (error) {
		});
	};
	
	$scope.cancel = function() {
		$element.modal('hide');
	};
	
}]);

angular.module('celefishApp.core').controller('AddGroupCtrl', ['$scope', '$route', '$element', 'title', 'close', 'variableService', 'groupServiceImpl', 'tableHeader', 'groups', 'toaster',
	function($scope, $route, $element, title, close, variableService, groupServiceImpl, tableHeader, groups, toaster) {

	$scope.name = null;
	$scope.title = title;
	$scope.groups = groups;
	
	$scope.headGroupName = tableHeader.headGroupName;
	$scope.headGroupManager = tableHeader.headGroupManager;
	$scope.headGroupLocation = tableHeader.headGroupLocation;
	$scope.headGroupBelong = tableHeader.headGroupBelong;
	$scope.headGroupDescription = tableHeader.headGroupDescription;
	$scope.headGroupLongitude = tableHeader.headGroupLongitude;
	$scope.headGroupLatitude = tableHeader.headGroupLatitude;
	$scope.headGroupOwner = tableHeader.headGroupOwner;
	$scope.confirm = function() {
		close({
		  name: $scope.name,
		}, 500);
		if ( $scope.selecteParentGroup == null || undefined) {
			$scope.selecteParentGroup = {"id":0};//Set parentId default is 0.
		}
		groupServiceImpl.insertGroup({
			"parentId": $scope.selecteParentGroup.id,
			"name": $scope.name, 
			"location":$scope.location, 
			"description": $scope.description, 
			"creater": variableService.id, 
			"deleted": false,
			"groupPropertiesList": [
				{"englishName":"ownerManager", "name":$scope.headGroupManager, "value":$scope.manager, "deleted":false},
				{"englishName":"owner", "name":$scope.headGroupOwner, "value":$scope.owner, "deleted":false},
				{"englishName":"longitude", "name":$scope.headGroupLongitude, "value":$scope.longitude, "deleted":false},
				{"englishName":"latitude", "name":$scope.headGroupLatitude, "value":$scope.latitude, "deleted":false}
			]
		}).then(function(response) {
 				$route.reload();
 				toaster.success('success!');
		  	}, function (error) {
	    });
	};
	
	$scope.cancel = function() {
	 $element.modal('hide');
	 close({
	      name: $scope.name
		 }, 500); // close, but give 500ms for bootstrap to animate
	};

}]);

angular.module('celefishApp.core').controller('ModifyGroupCtrl', ['$scope', '$route', '$element', 'title', 'close', 'variableService', 'groupServiceImpl', 'tableHeader', 'groups', 'group', 'toaster', '$interval',
	function($scope, $route, $element, title, close, variableService, groupServiceImpl, tableHeader, groups, group, toaster, $interval) {
	$scope.title = title;
	$scope.groups = groups;
	//show data
	$scope.name = group.name;
	$scope.location = group.location;
	$scope.selecteParentGroup = {"id":group.parentId};
	$scope.description = group.description;
	if (group.propMap != null) {
		$scope.manager = group.propMap.ownerManager.value;
		$scope.longitude = group.propMap.longitude.value;
		$scope.latitude = group.propMap.latitude.value;
		$scope.owner = group.propMap.owner.value;
	}
	
	$scope.headGroupName = tableHeader.headGroupName;
	$scope.headGroupManager = tableHeader.headGroupManager;
	$scope.headGroupLocation = tableHeader.headGroupLocation;
	$scope.headGroupBelong = tableHeader.headGroupBelong;
	$scope.headGroupDescription = tableHeader.headGroupDescription;
	$scope.headGroupLongitude = tableHeader.headGroupLongitude;
	$scope.headGroupLatitude = tableHeader.headGroupLatitude;
	$scope.headGroupOwner = tableHeader.headGroupOwner;
	$scope.confirm = function() {
		close({
		  name: $scope.name,
		  description: $scope.description,
		  priority: $scope.priority
		}, 500);
		
		groupServiceImpl.updateGroup({
			"id":group.id,
			"parentId": $scope.selecteParentGroup.id,
			"name": $scope.name, 
			"location":$scope.location, 
			"description": $scope.description, 
			"creater": variableService.id, 
			"deleted": false,
			"groupPropertiesList": [
				{"id": group.propMap.ownerManager.id, "groupId": group.id,"englishName":"ownerManager", "name":$scope.headGroupManager, "value":$scope.manager, "deleted":false},
				{"id": group.propMap.owner.id, "groupId": group.id, "englishName":"owner", "name":$scope.headGroupOwner, "value":$scope.owner, "deleted":false},
				{"id": group.propMap.longitude.id, "groupId": group.id, "englishName":"longitude", "name":$scope.headGroupLongitude, "value":$scope.longitude, "deleted":false},
				{"id": group.propMap.latitude.id, "groupId": group.id, "englishName":"latitude", "name":$scope.headGroupLatitude, "value":$scope.latitude, "deleted":false}
			]
		}).then(function(response) {
				$route.reload();
				toaster.success('success!');
			}, function (error) {
		});
	};
	
	$scope.cancel = function() {
	 $element.modal('hide');
	 close({
	      name: $scope.name,
	      description: $scope.description,
	      priority: $scope.priority
		 }, 500); // close, but give 500ms for bootstrap to animate
	};

}]);

