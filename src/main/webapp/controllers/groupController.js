var groupApp = angular.module('groupApp', ['groupService']);

groupApp.controller('groupCtrl', function($scope, groupList) {
	
	groupList.getAllGroup().then(
		function(answer) {
			$scope.allGroupData = [];
			var data;
			answer.data.forEach(function(item, index, array) {
				data = {};
				data.name = item.name;
				data.description = item.description;
				$scope.allGroupData.push(data);
			});
			$scope.groupName = [];
			$scope.groupKey = Object.keys($scope.allGroupData[0]);
			$scope.groupKey.forEach(function(item, index, array) {
				switch (item) {
					case "name":
						$scope.groupName.push("名稱");
				        break;
					case "description":
						$scope.groupName.push("描述");
				        break;
				}
			});
		}, 
		function(error){
			console.error('Error.');
		}
	);
});