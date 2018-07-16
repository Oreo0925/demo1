var accountApp = angular.module('accountApp', ['accountService']);

accountApp.controller('loginCtrl', function($scope, $window, $location, account, rootScope, $rootScope) {
	$scope.doLogin = function() {
		//console.log($scope.username + ":" + $scope.password);
		account.doLogin({'loginId': $scope.username, 'password': $scope.password}).then(
			function(answer) {
				if (answer.success) {
					rootScope.setRootScope('memberData', 'member', answer.data);
					$rootScope.member = answer.data;
					//$window.location = "http://localhost:8080/monitor.html";
					$location.path('/monitor');
				} else {
					alert(answer.message);
				}
			}, 
			function(error){
				console.error('Error.');
			}
		);
    };
});
