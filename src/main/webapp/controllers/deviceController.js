var deviceApp = angular.module('deviceApp', ['angularModalService', 'frapontillo.bootstrap-switch', 'deviceService']);

deviceApp.controller('deviceListCtrl', function($scope, ModalService, device) {
	
	device.getAllDevice().then(
		function(answer) {
			$scope.allDeviceData = [];
			var data;
			answer.data.forEach(function(item, index, array) {
				data = {};
				data.name = item.name;
				data.identifier = item.identifier;
				data.description = item.description;
				$scope.allDeviceData.push(data);
			});
			$scope.deviceName = [];
			$scope.deviceKey = Object.keys($scope.allDeviceData[0]);
			$scope.deviceKey.forEach(function(item, index, array) {
				switch (item) {
					case "name":
						$scope.deviceName.push("名稱");
				        break;
					case "identifier":
						$scope.deviceName.push("識別碼");
				        break;
					case "description":
						$scope.deviceName.push("描述");
				        break;
				}
			});
		}, 
		function(error){
			console.error('Error.');
		}
	);
	
	$scope.insertDeviceModal = function () {
		ModalService.showModal({
			templateUrl: "/tpls/device/insertDevice.html",
			controller: "insertDeviceCtrl",
			preClose: (modal) => { modal.element.modal('hide'); }
		}).then(function(modal) {
			modal.element.modal();
			modal.close.then(function(result) {
				//$scope.yesNoResult = result ? "You said Yes" : "You didn't say Yes";
			});
		});
	}
});

deviceApp.controller('insertDeviceCtrl', ['$scope', 'close', function($scope, close) {
	$scope.close = function(result) {
		close(result, 500); // close, but give 500ms for bootstrap to animate
	};
	
	$scope.isSelected = 'nope';

}]);
