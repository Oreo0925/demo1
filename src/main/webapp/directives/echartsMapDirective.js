var chartMapDirective = angular.module('echartsMapDirective', []);
chartMapDirective.directive('echartsLineChart', function() {
	return {
        restrict: 'AE',
        template: '<div></div>',
        replace: true,
        link: function(scope, element, attrs) {
        	var myChart = echarts.init(element[0]);
        	/*if (scope.directiveAccessor) {
				scope.directiveAccessor.drawEchartsMap = function() {
		            myChart.setOption(scope.option);
				}
			}*/
	        scope.$watch("trigger",function(newValue,oldValue) {
	        	if (scope.option) {
	        		/*if (myChart.isDisposed()) {
	        			myChart = echarts.init(element[0]);
	        		}*/
	        		myChart.dispose();
	        		myChart = echarts.init(element[0]);
	        		myChart.setOption(scope.option);
	        	} else {
	        		myChart.dispose();
	        	}
        	});
        }
    };
})