var fishRuleLogApp = angular.module('fishRuleLogApp', ['fishRuleLogService', 'agentflowService']);

fishRuleLogApp.controller('questionnaireCtrl', function($scope, questionnaire, afProcess) {
    $scope.submitQuestionnaireForm = function(submitType) {
    	if (!$scope.questionnaire.memo) {
    		$scope.questionnaire.memo = '';
    	}
    	
    	if (submitType == 1 || submitType == 2) {
    		if (submitType == 1) {
        		$scope.questionnaire.afProId = '';
        	} else if (submitType == 2) {
        		$scope.questionnaire.afProId = 'PRO00011527756600946';
        	}
    		$scope.questionnaire.aeratorOpen = Number($scope.questionnaire.aeratorOpen);
    		$scope.questionnaire.aeratorNormal = Number($scope.questionnaire.aeratorNormal);
    		$scope.questionnaire.waterQuality = Number($scope.questionnaire.waterQuality);
    		$scope.questionnaire.usedDrug = Number($scope.questionnaire.usedDrug);
    		$scope.questionnaire.aeratorOpen = Number($scope.questionnaire.aeratorOpen);
    		$scope.questionnaire.aeratorOpen = Number($scope.questionnaire.aeratorOpen);
    		$scope.questionnaire.aeratorOpen = Number($scope.questionnaire.aeratorOpen);
    		$scope.questionnaire.closed = submitType;
    		
    		questionnaire.closeEvent($scope.selectEvent.id, $scope.questionnaire).then(
				function(answer) {
					if (submitType == 1) {
						alert('事件已結束');
					} else if (submitType == 2) {
						afProcess.createProcess('http://localhost:8580/WebAgenda/createPro.jsp?UserID=MEM00041091074401237&processID=' + $scope.questionnaire.afProId).then(
							function(answer) {
								alert('事件已结束并派工');
							}, 
							function(error){
								console.error('Error.');
							}
						);
					}
				}, 
				function(error){
					console.error('Error.');
				}
			);
    	} else if (submitType == 3) {
    		questionnaire.callLater($scope.selectEvent.id, $scope.selectEvent.groupId).then(
				function(answer) {
					alert('事件已重派');
				}, 
				function(error){
					console.error('Error.');
				}
			);
    	}
    };
});

fishRuleLogApp.controller('historyDeviceNotifyCtrl', function($scope, historyDeviceNotify) {
	$scope.$on("notifyEventData", function (event, args) {
		getResolvedList(true);
	});
	
	getResolvedList = function(isCallFirst) {
		historyDeviceNotify.getResolvedList($scope.selectEvent.deviceId, isCallFirst? 0 : new Date().getTime()).then(
			function(answer) {
				$scope.historyDeviceLogData = answer.data;
				getResolvedList(false);
			}, 
			function(error){
				console.error('Error.');
			}
		);
	}
});