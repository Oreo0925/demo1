var monitorDirective = angular.module('monitorDirective', []);
monitorDirective.directive('phoneDiretive', function() {
	return {
		restrict : 'AE',
		template : '<div class="hover">123</div>',
		replace : true,
		link : function(scope, element, attrs) {
			var myChart = echarts.init(element[0]);
			/*
			 * if (scope.directiveAccessor) {
			 * scope.directiveAccessor.drawEchartsMap = function() {
			 * myChart.setOption(scope.option); } }
			 */

				console.log("123");
				new jBox('Tooltip', {
					attach : '.hover'
				});
			
		}
	};
})