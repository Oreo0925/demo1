var app = angular.module('area_fishPond', ['groupService']);

app.controller('area_fishPondCtrl', function($scope, groupList) {
	
	groupList.getAllGroup().then(
		function(answer) {
			$scope.fishArea = answer.data;
			$scope.selectedFishArea = $scope.fishArea[0];
		}, 
		function(error){
			console.error('Error.');
		}
	);
});