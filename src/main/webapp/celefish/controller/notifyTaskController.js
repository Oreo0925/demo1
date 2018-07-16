var notifyTaskApp = angular.module('notifyTaskApp', ['deviceService', 'notifyTaskService']);

notifyTaskApp.controller('notifyTaskCtrl', function($scope, device, notifyEvent, rootScope, $rootScope) {
	//get event number
	//var mem = $rootScope.member.id;
	//$scope.member = mem;
	var mem = 1;
	device.getDeviceById(1).then(
		function(answer) {
			//set data to parent which is listening($on) via the $rootScope broadcast
			rootScope.setRootScope('deviceData', 'device', answer.data);	//monitor.js is listening
			if (answer.data.parentGroupList) {
				rootScope.setRootScope('deviceGroupData', 'deviceGroup', answer.data.parentGroupList[0]);
			}
		}, 
		function(error){
			console.error('Error.');
		}
	);
	
    getUnresolvedEventNumByMem = function(isCallFirst) {
    	notifyEvent.getUnresolvedEventNumByMem(mem, isCallFirst? 0 : new Date().getTime()).then(
			function(answer) {
				$scope.unresolvedEventNum = answer.data;
				getUnresolvedEventNumByMem(false);
			}, 
			function(error){
				console.error('Error.');
			}
		);
    };
    getUnresolvedEventNumByMem(true);
    getResolvedEventNumByMem = function(isCallFirst) {
    	notifyEvent.getResolvedEventNumByMem(mem, isCallFirst? 0 : new Date().getTime()).then(
			function(answer) {
				$scope.resolvedEventNum = answer.data;
				getResolvedEventNumByMem(false);
			}, 
			function(error){
				console.error('Error.');
			}
    	);
    };
    getResolvedEventNumByMem(true);
	
    //get the last unresolved event by member
	getLastUnresolvedEventByMem = function(isCallFirst) {
    	notifyEvent.getLastUnresolvedEventByMem(mem, isCallFirst? 0 : new Date().getTime()).then(
			function(answer) {
				if ("0" != answer.message) {
					$scope.eventList = answer.data;
					if ($scope.eventList.length > 0) {
						rootScope.setRootScope('notifyEventData', 'selectEvent', $scope.eventList[0]);
					}
				}
				getLastUnresolvedEventByMem(false);
			}, 
			function(error){
				console.error('Error.');
			}
		);
    };
    
    getAllUnresolvedEventByMem = function(isCallFirst) {
    	notifyEvent.getAllUnresolvedEventByMem(mem, isCallFirst? 0 : new Date().getTime()).then(
			function(answer) {
				if ("0" != answer.message) {
					$scope.allEventList = answer.data;
					if ($scope.eventList.length == 0 && $scope.allEventList.length > 0) {
						rootScope.setRootScope('notifyEventData', 'selectEvent', $scope.allEventList[0]);
					}
				}
				getAllUnresolvedEventByMem(false);
			}, 
			function(error){
				console.error('Error.');
			}
		);
    };
    
    getLastUnresolvedEventByMem(true);	//page reload switch to unresolved event
    getAllUnresolvedEventByMem(true);
    
    
    //click event
    $scope.selectEvent = function(event) {
    	//set data to parent which is listening($on) via the $rootScope broadcast
    	rootScope.setRootScope('notifyEventData', 'selectEvent', event);
    };
    
    //click call phone in unresolving event
    $scope.notifyPhone = function() {
		alert('call phone');
    };
});

notifyTaskApp.controller('unresolvedEventByMemCtrl', function($scope) {
	$scope.selectRow = function(rowIndex){
    	$scope.selectedRow = rowIndex;
	};
});

notifyTaskApp.controller('unresolvedEventCtrl', function($scope) {
	$scope.selectRow = function(rowIndex){
    	$scope.selectedRow = rowIndex;
	};
});