var lang = navigator.language;

angular.module('celefishApp.core').factory('DeviceService', [function() {
	var device = {
	    id: 0,
	    childDeviceOne: {},
	    childDeviceTwo: {},
	    childDeviceThree: {},
	    isDevOneEnabled:false,
	    count:0
	};
	return device;
}]);

angular.module('celefishApp.core').controller('ParentDeviceCtrl', ['$scope', 'DeviceService', 'opDaoImpl', '$routeParams', '$translate', function($scope, DeviceService, opDaoImpl, $routeParams, $translate) {
	DeviceService.id = $routeParams.deviceId; //Set deviceId to DeviceService.
	
	$translate.use(lang).then(function() {
        $scope.deviceControlTitle = $translate.instant('deviceControl.title');
        $scope.serialNumber = $translate.instant('deviceControl.serialNumber');
        $scope.manualAuto = $translate.instant('deviceControl.manualAuto');
        $scope.stopRun = $translate.instant('deviceControl.stopRun');
        $scope.currentStatus = $translate.instant('deviceControl.currentStatus');
        $scope.description = $translate.instant('deviceControl.description');
    });
	
	opDaoImpl.getDeviceById($routeParams.deviceId)
		.then(function(response) {
			$scope.device = response.data.data;
            $scope.isAutomatic = $scope.device.automatic;
            $scope.identifier = $scope.device.identifier;
            $scope.deviceName = $scope.device.name;
            $scope.owner = $scope.device.parentGroupList[0].propMap.owner.value;
            $scope.ownerManager = $scope.device.parentGroupList[0].propMap.ownerManager.value;
            $scope.parentGroupListName = $scope.device.parentGroupList[0].name;
            var d1 = new Date($scope.device.deviceTempPropDataMap.effectivedate.value);
            var d2 = new Date($scope.device.deviceTempPropDataMap.enddate.value);
            $scope.remainDay = parseInt(d2 - d1) / (24 * 3600 * 1000);
            
            $scope.dissolver = $scope.device.deviceTempPropDataMap.dissolverValue.name;
            $scope.dissolverValue = $scope.device.deviceTempPropDataMap.dissolverValue.value;
            if ($scope.dissolverValue > 15) {
            	 $scope.dissolverColor = 1;
            } else {
            	$scope.dissolverColor = 0;
            }
            $scope.temperature = $scope.device.deviceTempPropDataMap.temperature.name;
            $scope.temperatureValue = $scope.device.deviceTempPropDataMap.temperature.value;
            if ($scope.temperatureValue > 15) {
           	 $scope.temperatureColor = 1;
            } else {
        	   $scope.temperatureColor = 0;
            }
            
            $scope.ph = $scope.device.deviceTempPropDataMap.ph.name;
            $scope.phValue = $scope.device.deviceTempPropDataMap.ph.value;
            if ($scope.phValue > 6 || $scope.phValue < 3) {
           	 $scope.phColor = 1;
            } else {
        	   $scope.phColor = 0;
            }
		}, function (error) {
	 });
}]);

angular.module('celefishApp.core').controller('ChildDevicesCtrl', ['$scope', 'DeviceService', 'opDaoImpl', function($scope, DeviceService, opDaoImpl) {
	opDaoImpl.getDevicesByParentId(DeviceService.id)
 		.then(function(response) {
 		   $scope.devices = response.data.data;
           $scope.automaticOne = $scope.devices[0].automatic;//DO1 and DO2
           $scope.automaticTwo = $scope.devices[2].automatic;//DO3
           $scope.enableOne = $scope.devices[0].enabled;//DO1
           $scope.enableTwo = $scope.devices[1].enabled;//DO2
           
           if ($scope.enableOne == true) {
        	   $("#btn3").css('backgroundColor', '#7FFF00');  
        	   $scope.isActiveOne = false;
        	   DeviceService.isDevOneEnabled = true;
           } 
           if ($scope.enableTwo == true) {
        	   $("#btn4").css('backgroundColor', '#7FFF00'); 
           } 
           
           //for showing status data.
           $scope.descOne = $scope.devices[0].description;
           $scope.descTwo = $scope.devices[1].description;
           $scope.descThree = $scope.devices[2].description;
           
           $scope.identifierOne = $scope.devices[0].identifier;
           $scope.identifierTwo = $scope.devices[1].identifier;
           $scope.identifierThree = $scope.devices[2].identifier;
           
           $scope.statusOne = $scope.devices[0].status;
           $scope.statusTwo = $scope.devices[1].status;
           $scope.statusThree = $scope.devices[2].status;
           
           $scope.enabled = $scope.devices[2].enabled;
           
           //switch settings.
           $scope.onText = '自動';
           $scope.offText = '手動';
           $scope.onText2 = '轉';
           $scope.offText2 = '停';
           $scope.isActiveOne = true;
           $scope.isActiveTwo = true;
           $scope.isActiveThree = true;
           $scope.size = 'small';
           $scope.animate = true;
           $scope.radioOff = true;
           $scope.handleWidth = "auto";
           $scope.labelWidth = "10";
           $scope.inverse = false;
           
           //Set disable if automatic is true.
           if($scope.automaticOne == true) {
        	   $scope.isActiveOne = false;
           }
           if($scope.automaticTwo == true) {
        	   $scope.isActiveTwo = false;
           }
           
           //Reserve three devices data to DeviceService.
           DeviceService.childDeviceOne = $scope.devices[0];
           DeviceService.childDeviceTwo = $scope.devices[1];
           DeviceService.childDeviceThree = $scope.devices[2];
           
 		}, function (error) {
	 });
	
	 $scope.switchOne = function() {
	     //true is automatic control.
	     if ($scope.automaticOne == true) {
	    	 //powerControl 0:close, 1:open, 2:automatic.
		     opDaoImpl.controlDevices(DeviceService.childDeviceOne.id, {"deviceAddress":1, "powerControl":2});
		     $scope.isActiveOne = false;
		     DeviceService.childDeviceOne.automatic = $scope.automaticOne;// Renew automatic of device's property.
		     DeviceService.childDeviceTwo.automatic = $scope.automaticOne;
		     //update db
		     opDaoImpl.update(DeviceService.childDeviceOne);
		     opDaoImpl.update(DeviceService.childDeviceTwo);
	     }
     }; 
     
	$scope.ctrlOne = function() {
		 DeviceService.count = 0;
		 $scope.automaticOne = false;
		 $scope.isActiveOne = true;
		 $("#btn3").css('backgroundColor', '#DCDCDC');  
	     $("#btn4").css('backgroundColor', '#DCDCDC'); 
	     //powerControl 0:close, 1:open, 2:automatic.
	     opDaoImpl.controlDevices(DeviceService.childDeviceOne.id, {"deviceAddress":1, "powerControl":0});
	    
	     DeviceService.isDevOneEnabled = false;
		 DeviceService.childDeviceOne.enabled = false;
		 DeviceService.childDeviceTwo.enabled = false;
		 DeviceService.childDeviceOne.automatic = $scope.automaticOne;
	     DeviceService.childDeviceTwo.automatic = $scope.automaticOne;
		 opDaoImpl.update(DeviceService.childDeviceOne);
		 opDaoImpl.update(DeviceService.childDeviceTwo);
    };
    
    $scope.ctrlTwo = function() {
    	 $scope.automaticOne = false;
    	 $scope.isActiveOne = true;
    	 if (DeviceService.count % 2 == 0 && DeviceService.isDevOneEnabled == false) {
			 $("#btn3").css('backgroundColor', '#7FFF00'); 
			 //true is enable the device.
			 DeviceService.childDeviceOne.enabled = true;
			 DeviceService.childDeviceOne.automatic = $scope.automaticOne;
			 DeviceService.isDevOneEnabled = true;
			 opDaoImpl.update(DeviceService.childDeviceOne);
		 } else {
			 $("#btn4").css('backgroundColor', '#7FFF00'); 
			 DeviceService.childDeviceTwo.enabled = true;
			 DeviceService.childDeviceTwo.automatic = $scope.automaticOne;
			 opDaoImpl.update(DeviceService.childDeviceTwo);
		 }
	     //powerControl 0:close, 1:open, 2:automatic.
	     opDaoImpl.controlDevices(DeviceService.childDeviceOne.id, {"deviceAddress":1, "powerControl":1});
	     DeviceService.count += 1;
    };
    
    $scope.switchTwo = function() {
    	//true is automatic control.
	    if ($scope.automaticTwo == true) {
	       //powerControl 0:close, 1:open, 2:automatic.
		   opDaoImpl.controlDevices(DeviceService.childDeviceOne.id, {"deviceAddress":2, "powerControl":2});
		   $scope.isActiveTwo = false;
		   DeviceService.childDeviceThree.automatic = $scope.automaticTwo;
		   opDaoImpl.update(DeviceService.childDeviceThree);
	    }
    }; 
    
    $scope.switchThree = function() {
   	 	$scope.automaticTwo = false;
   	 	$scope.isActiveTwo = true;
   	 	//powerControl 0:close, 1:open, 2:automatic.
  	  	if ($scope.enabled == true) {
  	  	   opDaoImpl.controlDevices(DeviceService.childDeviceOne.id, {"deviceAddress":2, "powerControl":0}); 
  	  	} else {
  	  	   opDaoImpl.controlDevices(DeviceService.childDeviceOne.id, {"deviceAddress":2, "powerControl":1});
  	  	}
  	  	DeviceService.childDeviceThree.automatic = $scope.automaticTwo;
        DeviceService.childDeviceThree.enabled = $scope.enabled;
        opDaoImpl.update(DeviceService.childDeviceThree);
    }; 
    
}]);

