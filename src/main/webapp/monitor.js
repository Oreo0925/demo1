var monitorApp = angular.module('monitorApp', ['notifyTaskApp', 'dashboardApp', 'fishRuleLogApp', 'baiduMapDirective', 'echartsMapDirective', 'rootScopeService', 'monitorDirective']);

monitorApp.controller('monitorCtrl', function($scope) {
	
	// listen 'deviceGroupData' data which is broadcasting by $rootScope in notifyTaskController.js
	$scope.$on("notifyEventData", function (event, args) {
        $scope[args.type] = args.data;	//set selectEvent data in $scope
        $scope.device = args.data.device;
        $scope.deviceGroup = args.data.device.parentGroupList[0];
    });
	
	$scope.$on("memberData", function (event, args) {
        $scope[args.type] = args.data;	//set selectEvent data in $scope
    });
	/*$scope.$on("deviceData", function (event, args) {
        $scope[args.type] = args.data;	//set device data in $scope
    });
	$scope.$on("deviceGroupData", function (event, args) {
        $scope[args.type] = args.data;	//set deviceGroup data in $scope
    });*/
	
	//理應搬到 baiduMap.html 專屬 controller
	$scope.directiveAccessor = {};
	var watch = $scope.$watch('device', function(newValue, oldValue, scope) {
		if ($scope.directiveAccessor.drawBaiduMap) {
			$scope.directiveAccessor.drawBaiduMap();
		}
    });
	
	
	$scope.fishRuleTabFlag = {'questionnaire': true, 'historyDevice': false, 'maintainRecord': false};
	$scope.showFishRuleTab = function(tab) {
		for (tabKey in $scope.fishRuleTabFlag) {
			if (tab == tabKey) {
				$scope.fishRuleTabFlag[tabKey] = true;
			} else {
				$scope.fishRuleTabFlag[tabKey] = false;
			}
		}
    };
    
    $scope.notifyEnable = true;
    $scope.notifyEnableFun = function() {
    	$scope.notifyEnable = !$scope.notifyEnable;
    }
});
