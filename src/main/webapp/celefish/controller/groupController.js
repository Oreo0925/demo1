var lang = navigator.language;

angular.module('celefishApp.core').controller('GroupCtrl', ['variableService', '$routeParams', function(variableService, $routeParams) {
	variableService.id = $routeParams.groupId;
}]);

angular.module('celefishApp.core').controller('GroupsCtrl', ['$scope', 'groupServiceImpl', 'variableService', 'ModalService', '$route', 'toaster', '$translate', '$timeout',
	function($scope, groupServiceImpl, variableService, ModalService, $route, toaster, $translate, $timeout) {
	
	//i18n
	$translate.use(lang).then(function() {
        $scope.serialNumber = $translate.instant('serialNumber');
        $scope.groupList = $translate.instant('group.list');
        $scope.groupName = $translate.instant('group.name');
        $scope.groupLocation = $translate.instant('group.location');
        $scope.groupBelong = $translate.instant('group.belong');
        $scope.groupDescription = $translate.instant('group.description');
        $scope.groupManager = $translate.instant('group.manager');
        $scope.groupLongitude = $translate.instant('group.longitude');
        $scope.groupLatitude = $translate.instant('group.latitude');
        $scope.groupOwner = $translate.instant('group.owner');
        $scope.groupRules = $translate.instant('group.rules');
        $scope.groupChildDevNum = $translate.instant('group.childDevNum');
        $scope.groupPlaceHolder = $translate.instant('group.placeHolder');
        $scope.authorizeRole = $translate.instant('group.authorizeRole');
        $scope.removeRole = $translate.instant('group.removeRole');
        $scope.groupRoleList = $translate.instant('group.roleList');
        
        $scope.roleOfName = $translate.instant('role.name');
        $scope.roleDescription = $translate.instant('role.description');
        $scope.rolePriority = $translate.instant('role.priority');
        
        $scope.add = $translate.instant('add');
        $scope.update = $translate.instant('update');
        $scope.search = $translate.instant('search');
        $scope.groupDelete = $translate.instant('delete');
    });
	
	groupServiceImpl.getGroupByCreator(variableService.id)
		.then(function(response) {
			$scope.groups = response.data.data;
		}, function (error) {
	});
	
	//Change the view of Group detail.
	$scope.getGroup = function(group) {
		if (group !== undefined) {
			$scope.regionName = group.name;
			groupServiceImpl.getGroupById(group.id)
				.then(function(response) {
					$scope.roles = response.data.data.roleList;
				}, function (error) {
			});
		} else {
			$scope.regionName = undefined;
		}
	};
	
	//Delete Group
	$scope.deleteGroup = function(group) {
		groupServiceImpl.deleteGroupById(group.id)
			.then(function(response) {
				if (!response.data.success) {
					toaster.error(response.data.message);
				} else {
					$route.reload();
					toaster.success('Success!');
				}
			}, function (error) {
		});
	};
	
	//add group modal
	$scope.roleResult = null;
	$scope.addGroupModal = function() {
		var tableHeader = {
		   headGroupName: $scope.groupName,
		   headGroupManager: $scope.groupManager,
		   headGroupLocation: $scope.groupLocation,
		   headGroupBelong: $scope.groupBelong,
		   headGroupDescription: $scope.groupDescription,
		   headGroupLongitude: $scope.groupLongitude,
		   headGroupLatitude: $scope.groupLatitude,
		   headGroupOwner: $scope.groupOwner
		};
	    ModalService.showModal({
	      templateUrl: "tpls/group/GroupModal.html",
	      controller: "AddGroupCtrl",
	      preClose: (modal) => { modal.element.modal('hide'); },
	      inputs: {
	        title: "新增區域",
	        groups: $scope.groups,
	        tableHeader: tableHeader
	      }
	    }).then(function(modal) {
	    	modal.element.modal();
	    	modal.close.then(function(result) {
	    	  if (!result) {
		          $scope.roleResult = "Modal forcibly closed..."
		      } else {
		          $scope.roleResult  = "Name: " + result.name + ", description: " + result.description + ", priority: " + result.priority;
		      }
	       });
	    });
    };
    
    //update group modal
    $scope.modifyGroupModal = function() {
    	if ($('input[name*=groups]:checked').val() !== undefined) {
    		var tableHeader = {
			   headGroupName: $scope.groupName,
			   headGroupManager: $scope.groupManager,
			   headGroupLocation: $scope.groupLocation,
			   headGroupBelong: $scope.groupBelong,
			   headGroupDescription: $scope.groupDescription,
			   headGroupLongitude: $scope.groupLongitude,
			   headGroupLatitude: $scope.groupLatitude,
			   headGroupOwner: $scope.groupOwner
			};
    		$scope.group = JSON.parse($('input[name*=groups]:checked').val());
			ModalService.showModal({
			    templateUrl: "tpls/group/GroupModal.html",
			    controller: "ModifyGroupCtrl",
			    preClose: (modal) => { modal.element.modal('hide'); },
			    inputs: {
			       title: "修改區域",
			       group:$scope.group,
			       groups: $scope.groups,
			       tableHeader: tableHeader
			    }
		    }).then(function(modal) {
		    	modal.element.modal();
		    	modal.close.then(function(result) {
		    	  if (!result) {
			          $scope.roleResult = "Modal forcibly closed..."
			      } else {
			          $scope.roleResult  = "Name: " + result.name + ", description: " + '' + ", priority: " + '';
			      }
		       });
		    });
    	} else {
    		$translate.use(lang).then(function() {
                $scope.selectOneRule = $translate.instant('toaster.selectOneGroup');
                toaster.error($scope.selectOneRule);
            });
    	}
    };
    
   //Search Role by Name
    $scope.getGroupByName = function() {
    	groupServiceImpl.getGroupByName($scope.inputName)
	    	.then(function(response) {
				$scope.groups = response.data.data;
			}, function (error) {
		});
    };
    
}]);

angular.module('celefishApp.core').controller('ManGroupCtrl', ['$scope', 'groupServiceImpl', 'ModalService', '$route', 'toaster', '$translate', function($scope, groupServiceImpl, ModalService, $route, toaster, $translate) {

	$scope.addRoleGroup = function() {
		var tableHeader = {
		   serialNumber: $scope.serialNumber,
		   roleOfName: $scope.roleOfName,
		   roleDescription: $scope.roleDescription,
		   rolePriority: $scope.rolePriority
		};
		if ($('input[name*=groups]:checked').val() !== undefined) {
		   ModalService.showModal({
		      templateUrl: "tpls/group/RoleListModal.html",
		      controller: "AddRoleGroupCtrl",
		      preClose: (modal) => { modal.element.modal('hide'); },
		      inputs: {
		        title: "指派角色",
		        tableHeader: tableHeader
		      }
		    }).then(function(modal) {
		    	modal.element.modal();
		   });
		} else {
			$translate.use(lang).then(function() {
                $scope.selectOneGroup = $translate.instant('toaster.selectOneGroup');
                toaster.error($scope.selectOneGroup);
            });
		}
	};
	
	$scope.deleteRoleGroup = function() {
		var roleGroupList = [];
		angular.forEach($scope.roles, function(role) {
			if (role.selected) {
				var roleGroup = {"roleId":0, "groupId":JSON.parse($('input[name*=groups]:checked').val()).id};
				roleGroup.roleId = role.id;
				roleGroupList.push(roleGroup);
			}
		});
		groupServiceImpl.batchDeleteRoleGroupList(roleGroupList)
			.then(function(response) {
				if (!response.data.success) {
					toaster.error('未選擇角色或角色未管轄該區域!');
				} else {
					$route.reload();
					toaster.success('Success!');
				}
			}, function (error) {
		});
	};
	
}]);

