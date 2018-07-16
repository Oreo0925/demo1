var baiduMapDirective = angular.module('baiduMapDirective', []);
baiduMapDirective.directive('setLocation', function() {
    return {
        restrict: 'AE',
        replace: true,
        template: '<div class="drawBaiduMap"></div>',
        link: function(scope, element, attrs) {
        	if (scope.directiveAccessor) {
				scope.directiveAccessor.drawBaiduMap = function() {
					var map = new BMap.Map(element[0]);
		            map.enableScrollWheelZoom();
		            
		            if (scope.device && scope.device.deviceTempPropDataMap && scope.device.deviceTempPropDataMap.longitude && scope.device.deviceTempPropDataMap.latitude) {
		            	var point = new BMap.Point(scope.device.deviceTempPropDataMap.longitude.value, scope.device.deviceTempPropDataMap.latitude.value);
		            	map.centerAndZoom(point, 12);
		            	var marker = new BMap.Marker(point);
		            	marker.addEventListener("click", function() {
		            		alert(scope.device.deviceTempPropDataMap.longitude.name + ': ' + scope.device.deviceTempPropDataMap.longitude.value + ", " + scope.device.deviceTempPropDataMap.latitude.name + ': ' + scope.device.deviceTempPropDataMap.latitude.value);
		            	});
		            	map.addOverlay(marker);
		            }
				}
        	}
        	scope.directiveAccessor.drawBaiduMap();
        }
    };
})