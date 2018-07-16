	angular.module('celefishApp.core').controller('fishParamCtrl', function($scope, $http) {
		var scope = $scope;
		$scope.animationsEnabled = true;
		$scope.openModal = function () {};
		this.$onInit = function() {};
		$scope.setParam = {};
		
		$scope.oxyLimit = function($event) {
			if(scope.identifier == ""){
				alert("請輸入設備ID");
				clearData();
			}else{
				id = scope.device_id || 0;
				if(id == 0){
					alert("請輸入設備ID後，並查詢。")
					clearData();
				}else{
					if(checkOxyLimit()){
						$http({
				            method : "PUT",
				            url : "/device/datas/tempId/1/deviceId/"+id,
				            data:[
				              	{
				                    "deviceId": 2,
				                    "tempId": 2,
				                    "englishName": "oxy_limit_up",
				                    "name": "溶氧警告上限",
				                    "value": $scope.setParam.oxy_limit_up_set || "",
				                    "deleted": false,
				                    "fix": false
				                },
				                {
				                    "deviceId": 2,
				                    "tempId": 2,
				                    "englishName": "oxy_limit_down1",
				                    "name": "溶氧警告下限1",
				                    "value": $scope.setParam.oxy_limit_down1_set || "",
				                    "deleted": false,
				                    "fix": false
				                },
				                {
				                    "deviceId": 2,
				                    "tempId": 2,
				                    "englishName": "oxy_limit_down2",
				                    "name": "溶氧警告下限2",
				                    "value": $scope.setParam.oxy_limit_down2_set || "",
				                    "deleted": false,
				                    "fix": false
				                },
				                {
				                    "deviceId": 2,
				                    "tempId": 2,
				                    "englishName": "salinity",
				                    "name": "鹽度",
				                    "value": $scope.setParam.salinity_set || "",
				                    "deleted": false,
				                    "fix": false
				                }
				            ]
				        }).then(
							function(response) {
								$http({
									method : "PUT",
									url : "/fish/device/" + id + "/setting",
									data:[
										{
											"oxyLimitUp" :  $scope.setParam.oxy_limit_up_set || "",
											"oxyLimitDown1" : $scope.setParam.oxy_limit_down1_set || "",
											"oxyLimitDown2" : $scope.setParam.oxy_limit_down2_set || "",
											"salinity" : $scope.setParam.salinity_set || ""
										}
										]
								}).then(
									function(response) {
										//console.log("success");
									}, function(response) {
										alert("error" + response.data.status + " " + response.data.message);
								});
								
								$scope.oxy_limit_up = $scope.setParam.oxy_limit_up_set || "";
						        $scope.oxy_limit_down1 = $scope.setParam.oxy_limit_down1_set || "";
						        $scope.oxy_limit_down2 = $scope.setParam.oxy_limit_down2_set || "";
						        $scope.salinity = $scope.setParam.salinity_set || "";
						        $scope.setParam.oxy_limit_up_set = "";
						        $scope.setParam.oxy_limit_down1_set = "";
						        $scope.setParam.oxy_limit_down2_set = "";
						        $scope.setParam.salinity_set = "";
							}, function(response) {
								alert("error" + response.data.status + " " + response.data.message);
						});
					};
				}
			}
		};
		

	    
		
		
		
		
		$scope.alertLine = function($event) {
			if(scope.identifier == ""){
				alert("請輸入設備ID");	
				clearData();
			}else{
				id = scope.device_id || 0;
				if(id == 0){
					alert("請輸入設備ID後，並查詢。");
					clearData();
				}else{
					if(checkAlertLine()){
						$http({
				            method : "PUT",
				            url : "/device/datas/tempId/1/deviceId/"+id,
				            data:[
				  				{
						            "deviceId": 2,
						            "tempId": 2,
						            "englishName": "alertline1",
						            "name": "報警線1",
						            "value": $scope.setParam.alertline1_set || "",
						            "deleted": false,
						            "fix": false
						        },
						        {
						            "deviceId": 2,
						            "tempId": 2,
						            "englishName": "alertline2",
						            "name": "報警線2",
						            "value": $scope.setParam.alertline2_set || "",
						            "deleted": false,
						            "fix": false
						        }
							]
				        }).then(
							function(response) {
								$scope.alertline1 = $scope.setParam.alertline1_set || "";
						        $scope.alertline2 = $scope.setParam.alertline2_set || "";
						        $scope.setParam.alertline1_set = "";
						        $scope.setParam.alertline2_set = "";
							}, function(response) {
								alert("error" + response.data.status + " " + response.data.message);
						});
					};
				}
			}
		};
		
		
		
		
		
		
	    
		$scope.search = function() {
			identifier = scope.identifier || 0;
			$http.get("/device/identifier/" + identifier + "/customer/Celefish").then(
					function(response) {
						if(response.data.code == 204){
							clearData();
							alert(response.data.message);
						}else{
							device = response.data.data;
				        	deviceProp = device.deviceTempPropDataMap;
				        	group = device.parentGroupList[0];
				        	$scope.device_type_name = device.deviceType.name;
				        	$scope.device_full_id = device.fullId;
				        	$scope.device_id = device.id;
				        	$scope.identifier = device.identifier;
				        	$scope.device_name = device.name;
				        	$scope.owner_manager = group.propMap.ownerManager.value;
				        	$scope.group_name = group.name;
				        	$scope.dissolver_value = deviceProp.dissolverValue.value;
				        	$scope.oxy_limit_up = deviceProp.oxy_limit_up.value;
				        	$scope.oxy_limit_down1 = deviceProp.oxy_limit_down1.value;
				        	$scope.oxy_limit_down2 = deviceProp.oxy_limit_down2.value;
				        	$scope.salinity = deviceProp.salinity.value;
				        	$scope.alertline1 = deviceProp.alertline1.value;
				        	$scope.alertline2 = deviceProp.alertline2.value;
						}
					}, function(response) {
						alert(response.data.status + " " + response.data.message);
					});
			};
			
			
			function checkOxyLimit(){
				pass = true;
				regNumber = /^[1-9]d*$/;
				regBouble = /^[-+]?[0-9]\d*[.][0-9]+$/;
				
				if(!regNumber.test($scope.setParam.oxy_limit_up_set)&&!regBouble.test($scope.setParam.oxy_limit_up_set)){
					pass = false;
				}
				
				if(!regNumber.test($scope.setParam.oxy_limit_down1_set)&&!regBouble.test($scope.setParam.oxy_limit_down1_set)){
					pass = false;
				}
				if(!regNumber.test($scope.setParam.oxy_limit_down2_set)&&!regBouble.test($scope.setParam.oxy_limit_down2_set)){
					pass = false;
				}
				if(!regNumber.test($scope.setParam.salinity_set)&&!regBouble.test($scope.setParam.salinity_set)){
					pass = false;
				}
				if(!pass){
					alert("請輸入數值");
				}
				return pass;
			}
			
			function checkAlertLine(){
				pass = true;
				regNumber = /^[1-9]d*$/;
				regBouble = /^[-+]?[0-9]\d*[.][0-9]+$/;
				if(!regNumber.test($scope.setParam.alertline1_set)&&!regBouble.test($scope.setParam.alertline1_set)){
					pass = false;
				}
				if(!regNumber.test($scope.setParam.alertline2_set)&&!regBouble.test($scope.setParam.alertline2_set)){
					pass = false;
				}

				if(!pass){
					alert("請輸入數值");
				}
				return pass;
			}
			
			function clearData(){
				$scope.identifier = '';
				$scope.device_id = '';
				$scope.device_type_name = '';
	        	$scope.device_full_id = '';
	        	$scope.device_name = '';
	        	$scope.owner_manager = '';
	        	$scope.group_name = '';
	        	
	        	$scope.dissolver_value = '';
	        	$scope.oxy_limit_up = '';
	        	$scope.oxy_limit_down1 = '';
	        	$scope.oxy_limit_down2 = '';
	        	$scope.salinity = '';
	        	$scope.alertline1 = '';
	        	$scope.alertline2 = '';	
	        	
	        	$scope.setParam.oxy_limit_up_set = '';
	        	$scope.setParam.oxy_limit_down1_set = '';
	        	$scope.setParam.oxy_limit_down2_set = '';
	        	$scope.setParam.salinity_set = '';
	        	$scope.setParam.alertline1_set = '';
	        	$scope.setParam.alertline2_set = '';
			}
	});