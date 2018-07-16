var dashboardApp = angular.module('dashboardApp', ['deviceDataService']);

dashboardApp.controller('dashboardCtrl', function($scope, deviceData, $q) {
	$scope.dashboardTabFlag = {'alert': true, 'realTime': false};
	$scope.showDashboardTab = function(tab) {
		for (tabKey in $scope.dashboardTabFlag) {
			if (tab == tabKey) {
				$scope.dashboardTabFlag[tabKey] = true;
			} else {
				$scope.dashboardTabFlag[tabKey] = false;
			}
		}
		selectDataType(tab);
    };
	
	function findAlertData() {
		var selectEvent = $scope.selectEvent;
		var device = selectEvent.device;
	
		var date = new Date(selectEvent.alarmTime);
		$scope.dissolvedOxygen = selectEvent.dissolvedOxygen;
		$scope.temperature = selectEvent.temperature;
		$scope.ph = selectEvent.ph;
		//$scope.controlCode = selectEvent.controlCode;
		//$scope.statusCode = selectEvent.statusCode;
		
		var date1 = new Date(date.getFullYear() + "-" + (date.getMonth()+1) + "-" + (date.getDate())).getTime();
		var date2 = new Date(date.getFullYear() + "-" + (date.getMonth()+1) + "-" + (date.getDate()+1)).getTime();
		
		var url = 'http://139.224.237.0:8086';
		var db = 'celefish';
		var sql = "SELECT dissolvedOxygen, temperature FROM celefish WHERE identify='" + device.identifier + "' AND time >= " + date1 + 'ms and time < ' + date2 + 'ms';
		deviceData.getInfluxData2(url, db, sql).then(
			function(answer) {
				//console.log(answer.results[0].series[0].values);
				if (answer.results[0].series) {
					var values = answer.results[0].series[0].values;
					var time, xAxisData = [], seriesData1 = [], seriesData2 = [];
					for (var i = 0; i < values.length; i++) {
						time = new Date(values[i][0]);
						xAxisData.push((time.getHours() < 10 ? '0' + time.getHours() : time.getHours()) + ":" + (time.getMinutes() < 10 ? '0' + time.getMinutes() : time.getMinutes()));
						seriesData1.push(values[i][1]);
						seriesData2.push(values[i][2]);
					}
					
					$scope.alertOption = {
		                title: {
		                    text: '溶氧值温度告警数据 (mg/L)',
		                    textStyle: {
		                    	fontSize: 12
		                    }
		                },
		                tooltip: {
		                    trigger: 'axis'
		                },
		                legend: {
		                    data: ['溶氧值', '温度'],
		                    top: 20, 
		                    textStyle: {
		                    	fontSize: 10
		                    }
		                },
		                grid: {
		                    top: 100
		                },
		                xAxis: [{
		                	show: true,
		                    type: 'category',
		                    data: xAxisData
		                }],
		                yAxis: [{
		                    type: 'value',
		                    name: '溶氧值',
		                    min: 0,
		                    max: 20,
		                    position: 'left'
		                },
		                {
		                    type: 'value',
		                    name: '温度',
		                    min: 0,
		                    max: 40,
		                    position: 'right'
		                }],
		                dataZoom: [
							{
							    type: 'inside',
							    start: 0,
							    end: 100 
							}
		                ],
		                series: [{
		                    name: '溶氧值',
		                    type: 'line',
		                    data: seriesData1
		                },
		                {
		                    name: '温度',
		                    type: 'line',
		                    yAxisIndex: 1,
		                    data: seriesData2
		                }]
		            };
				} else {
					$scope.alertOption = null;
				}
				if ('alert' == $scope.dataType) {
					$scope.option = $scope.alertOption;
					$scope.trigger = $scope.trigger ? $scope.trigger + 1 : 1;
				}
			}, 
			function(error){
				console.log('Connect error.');
			}
		);
	}
    
    function findRealTimeData() {
		var selectEvent = $scope.selectEvent;
		var device = selectEvent.device;
		
		var date  = new Date();
		date = new Date(date.getFullYear() + "-" + (date.getMonth()+1) + "-" + (date.getDate()))
		var date1 = new Date(date.getTime() - 4*24*60*60*1000).getTime();
		var date2 = new Date(date.getTime() + 24*60*60*1000).getTime();
		
		var url = 'http://139.224.237.0:8086';
		var db = 'celefish';
		var sql = "SELECT dissolvedOxygen, temperature,controlCode, statusCode FROM celefish WHERE identify='" + device.identifier + "' AND time >= " + date1 + 'ms and time < ' + date2 + 'ms';
		deviceData.getInfluxData2(url, db, sql).then(
			function(answer) {
				//console.log(answer.results[0].series[0].values);
				if (answer.results[0].series) {
					var values = answer.results[0].series[0].values;
					$scope.realTimeData = values[values.length - 1];
					
					//one day inital data
					var seriesDataMap = {};
					for (var i = 0; i < 24; i++) {
					    for (var j = 0; j < 60; j+=5) {
					    	seriesDataMap[(i < 10 ? '0' + i : i) + ":" + (j < 10 ? '0' + j : j)] = null;
					    }
					}
					
					//copy inital data to 5 days's map obj
					var seriesDatasObj = {};
					for (var i = 0; i < 5; i++) {
						if (i > 0) {
							date.setDate(date.getDate() - 1);
						}
						seriesDatasObj[(date.getMonth() + 1) + '/' + date.getDate()] = Object.assign({}, seriesDataMap);
					}
					
					var time, key, duringMin;
					for (var i = 0; i < values.length; i++) {
						time = new Date(values[i][0]);
						duringMin = parseInt(time.getMinutes() / 5) * 5;
						key = (time.getHours() < 10 ? '0' + time.getHours() : time.getHours()) + ":" + (duringMin < 10 ? '0' + duringMin : duringMin)
						if (!seriesDatasObj[(time.getMonth() + 1) + '/' + time.getDate()][key]) {
							seriesDatasObj[(time.getMonth() + 1) + '/' + time.getDate()][key] = values[i][1];
						}
					}
					
					var xAxisData = Object.keys(seriesDataMap);
					var seriesNames = Object.keys(seriesDatasObj);
					var seriesDatas = Object.values(seriesDatasObj);
					
					/*var tempData = 0;
					for (var i = 0; i < seriesDatas.length; i++) {
						tempData = 0;
						for (var timeKey in seriesDatas[i]) {
							if (seriesDatas[i][timeKey] != 0) {
								tempData = seriesDatas[i][timeKey];
							} else {
								seriesDatas[i][timeKey] = tempData;
							}
						}
					}*/
					
					$scope.realTimeOption = {
		                title: {
		                    text: '鱼塘溶氧值数据 (mg/L)',
		                    textStyle: {
		                    	fontSize: 12
		                    }
		                },
		                tooltip: {
		                    trigger: 'axis'
		                },
		                legend: {
		                    data: ['上限', '下限1', '下限2', '警报限1', '警报限2', seriesNames[0], seriesNames[1], seriesNames[2], seriesNames[3], seriesNames[4]],
		                    top: 20, 
		                    textStyle: {
		                    	fontSize: 10
		                    }
		                },
		                grid: {
		                    top: 100
		                },
		                xAxis: [{
		                	show: true,
		                    type: 'category',
		                    data: xAxisData
		                }],
		                yAxis: [{
		                    type: 'value'
		                }],
		                dataZoom: [
							{
							    type: 'inside',
							    start: 0,
							    end: 100 
							}
		                ],
		                series: [{
		                    name: '上限',
		                    type: 'line',
		                    markLine: {
		                        silent: true,
		                        data: [{
		                            yAxis: device.deviceTempPropDataMap.oxy_limit_up.value
		                        }]
		                    }
		                },
		                {
		                    name: '下限1',
		                    type: 'line',
		                    markLine: {
		                        silent: true,
		                        data: [{
		                            yAxis: device.deviceTempPropDataMap.oxy_limit_down1.value
		                        }]
		                    }
		                },
		                {
		                    name: '下限2',
		                    type: 'line',
		                    markLine: {
		                        silent: true,
		                        data: [{
		                            yAxis: device.deviceTempPropDataMap.oxy_limit_down2.value
		                        }]
		                    }
		                },
		                {
		                    name: '警报限1',
		                    type: 'line',
		                    markLine: {
		                        silent: true,
		                        data: [{
		                            yAxis: device.deviceTempPropDataMap.alertline1.value
		                        }]
		                    }
		                },
		                {
		                    name: '警报限2',
		                    type: 'line',
		                    markLine: {
		                        silent: true,
		                        data: [{
		                            yAxis: device.deviceTempPropDataMap.alertline2.value
		                        }]
		                    }
		                },
		                {
		                    name: seriesNames[0],
		                    type: 'line',
		                    data: Object.values(seriesDatas[0])
		                },
		                {
		                    name: seriesNames[1],
		                    type: 'line',
		                    data: Object.values(seriesDatas[1])
		                },
		                {
		                    name: seriesNames[2],
		                    type: 'line',
		                    data: Object.values(seriesDatas[2])
		                },
		                {
		                    name: seriesNames[3],
		                    type: 'line',
		                    data: Object.values(seriesDatas[3])
		                },
		                {
		                    name: seriesNames[4],
		                    type: 'line',
		                    data: Object.values(seriesDatas[4])
		                }]
		            };
				} else {
					$scope.realTimeOption = null;
				}
				if ('realTime' == $scope.dataType) {
					$scope.option = $scope.realTimeOption;
					$scope.trigger = $scope.trigger ? $scope.trigger + 1 : 1;
				}
			}, 
			function(error){
				console.log('Connect error.');
			}
		);
	}
    
    
    function findData() {
    	findAlertData();
    	findRealTimeData();
    }
    
    function selectDataType(type) {
    	$scope.dataType = type;
    	if ("alert" == type) {
    		$scope.option = $scope.alertOption;
    	} else if ("realTime" == type) {
    		$scope.option = $scope.realTimeOption;
    	}
    	if ($scope.option) {
    		$scope.trigger = $scope.trigger ? $scope.trigger + 1 : 1;
    	} else {
    		$scope.trigger = null;
    	}
    }
    
    $scope.$on("notifyEventData", function (event, args) {
    	findData();
	});
    $scope.dataType = 'alert';
    findData();
});


dashboardApp.controller('dissolvedOxygenCtrl1', function($scope) {
	$scope.option = {
        title: {
            text: '1號開關',
            textStyle: {
            	fontSize: 12
            }
        },
        tooltip: {
            trigger: 'axis'
        },
        xAxis: [{
        	show: true,
            type: 'category',
            boundaryGap: false,
            data: ['00:00', '03:00', '06:00', '09:00', '12:00', '15:00', '18:00', '21:00', '24:00']
        }],
        yAxis: [{
        	max: 1,
        	 minInterval: 1,
            type: 'value'
        }],
        series: [{
            type: 'line',
            data:[1, 1, 0, 0, 1, 1, 0, 0, 1]
        }]
    };
	$scope.trigger = true;
});

dashboardApp.controller('dissolvedOxygenCtrl2', function($scope) {
	$scope.option = {
        title: {
            text: '2號開關',
            textStyle: {
            	fontSize: 12
            }
        },
        tooltip: {
            trigger: 'axis'
        },
        xAxis: [{
        	show: true,
            type: 'category',
            boundaryGap: false,
            data: ['00:00', '03:00', '06:00', '09:00', '12:00', '15:00', '18:00', '21:00', '24:00']
        }],
        yAxis: [{
        	max: 1,
        	 minInterval: 1,
            type: 'value'
        }],
        series: [{
            type: 'line',
            data:[0, 1, 1, 0, 0, 1, 1, 0, 1]
        }]
    };
	$scope.trigger = true;
});
