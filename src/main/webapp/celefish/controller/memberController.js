var lang = navigator.language;

angular.module('celefishApp.core').controller('MemberRoleCtrl', ['variableService', '$routeParams', function(variableService, $routeParams) {
	variableService.id = $routeParams.parentId;
}]);

angular.module('celefishApp.core').controller('RoleCtrl', ['$scope', 'memberServiceImpl', 'variableService', 'ModalService', '$route', 'toaster', '$translate', function($scope, memberServiceImpl, variableService, ModalService, $route, toaster, $translate) {
	
	//i18n
	$translate.use(lang).then(function() {
        $scope.roleLlist = $translate.instant('role.list');
        $scope.serialNumber = $translate.instant('serialNumber');
        $scope.roleOfName = $translate.instant('role.name');
        $scope.roleDescription = $translate.instant('role.description');
        $scope.rolePriority = $translate.instant('role.priority');
        $scope.rolePlaceHolder = $translate.instant('role.placeHolder');
        
        $scope.memberList = $translate.instant('member.list');
        $scope.memberAdd = $translate.instant('member.add');
        $scope.memberRemove = $translate.instant('member.remove');
        $scope.memberId = $translate.instant('member.id');
        $scope.memberName = $translate.instant('member.name');
        $scope.memberAccount = $translate.instant('member.account');
        $scope.memberAuthority = $translate.instant('member.authority');
        
        $scope.add = $translate.instant('add');
        $scope.update = $translate.instant('update');
        $scope.search = $translate.instant('search');
        $scope.roleDelete = $translate.instant('delete');
    });
	
	memberServiceImpl.getRoleByCreator(variableService.id)
		.then(function(response) {
			$scope.roles = response.data.data;
		}, function (error) {
	});
	
	//Change the view of Role detail.
	$scope.getRole =  function(role) {
		if (role !== undefined) {
			$scope.roleName = role.name;
			memberServiceImpl.getMemberListByRoleId(role.id)
				.then(function(response) {
					$scope.members = response.data.data;
				}, function (error) {
			});
		} else {
			$scope.roleName = undefined;
		}
	};
	
	//Delete Role
	$scope.deleteRole = function(role) {
		memberServiceImpl.deleteRole(role.id)
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
	
	//add role modal
	$scope.roleResult = null;
	$scope.addRoleModal = function() {
		var tableHeader = {
		   headName: $scope.roleOfName,
		   headDescription: $scope.roleDescription,
		   headPriority: $scope.rolePriority
		};
	    ModalService.showModal({
	      templateUrl: "tpls/member/RoleModal.html",
	      controller: "AddRoleCtrl",
	      preClose: (modal) => { modal.element.modal('hide'); },
	      inputs: {
	        title: "新增角色",
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
    
   //update role modal
    $scope.modifyRoleModal = function() {
    	var tableHeader = {
		   headName: $scope.roleOfName,
		   headDescription: $scope.roleDescription,
		   headPriority: $scope.rolePriority
		};
    	if ($('input[name*=roles]:checked').val() !== undefined) {
    		$scope.role = JSON.parse($('input[name*=roles]:checked').val());
			ModalService.showModal({
			    templateUrl: "tpls/member/RoleModal.html",
			    controller: "ModifyRoleCtrl",
			    preClose: (modal) => { modal.element.modal('hide'); },
			    inputs: {
			       title: "修改角色",
			       role:$scope.role,
			       tableHeader:tableHeader
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
    	} else {
    		$translate.use(lang).then(function() {
                $scope.selectOneRule = $translate.instant('toaster.selectOneRule');
                toaster.error($scope.selectOneRule);
            });
    	}
    };
    
    //Search Role by Name
    $scope.getRoleByName = function() {
    	memberServiceImpl.getRoleByName($scope.inputName)
	    	.then(function(response) {
				$scope.roles = response.data.data;
			}, function (error) {
		});
    };
}]);

angular.module('celefishApp.core').controller('MemberCtrl', ['$scope', 'memberServiceImpl', 'ModalService', '$route', 'toaster', '$translate', function($scope, memberServiceImpl, ModalService, $route, toaster, $translate) {
	
	$scope.addMemberRole = function() {
		var tableHeader = {
		   serialNumber: $scope.serialNumber,
		   memberId: $scope.memberId,
		   memberName: $scope.memberName,
		   memberAccount: $scope.memberAccount
		};
		if ($('input[name*=roles]:checked').val() !== undefined) {
		   ModalService.showModal({
		      templateUrl: "tpls/member/MemberListModal.html",
		      controller: "AddMemberRoleCtrl",
		      preClose: (modal) => { modal.element.modal('hide'); },
		      inputs: {
		        title: "加入人員",
		        tableHeader: tableHeader
		      }
		    }).then(function(modal) {
		    	modal.element.modal();
		   });
		} else {
			$translate.use(lang).then(function() {
                $scope.selectOneRule = $translate.instant('toaster.selectOneRule');
                toaster.error($scope.selectOneRule);
            });
		}
	};
	
	$scope.deleteMemberRole = function() {
		var memberRoleList = [];
		
		angular.forEach($scope.members, function(member) {
			if (member.selected) {
				var memberRole = {"memberId":0, "roleId":JSON.parse($('input[name*=roles]:checked').val()).id};
				memberRole.memberId = member.id;
				memberRoleList.push(memberRole);
			}
		});
		memberServiceImpl.batchDeleteMemberRoleList(memberRoleList)
			.then(function(response) {
				if (!response.data.success) {
					toaster.error('未選擇人員或人員無該權限!');
				} else {
					$route.reload();
					toaster.success('Success!');
				}
			}, function (error) {
		});
	};
	
}]);
