angular.module('celefishApp.directive').directive('icheck', function($timeout) {
	return {
        require: 'ngModel',
        link: function ($scope, element, $attrs, ngModel)
        {
            return $timeout(function () {
                var value = $attrs['value'];
                if ($attrs['disabled'])
                    $(element).iCheck($attrs['disabled'] ? 'disable' : 'enable');

                $scope.$watch(function () { return ngModel.$modelValue; }, function (newValue) {
                    if (newValue != undefined) {
                        $(element).iCheck('update');
                    }
                });
                $scope.$watch($attrs['ngDisabled'], function (newValue) {
                    if (newValue != undefined) {
                        $(element).iCheck(newValue ? 'disable' : 'enable');
                        $(element).iCheck('update');
                    }
                });
                return $(element).iCheck({
                    checkboxClass: $attrs['checkboxClass'] ? $attrs['checkboxClass'] : 'icheckbox_square-green',
                    radioClass: $attrs['radioClass'] ? $attrs['radioClass'] : 'iradio_square-green'

                }).on('ifChanged', function (event) {
                    if ($(element).attr('type') === 'checkbox' && $attrs['ngModel']) {
                        ngModel.$setViewValue(event.target.checked);
                    }
                }).on('ifClicked', function (event) {
                	if ($(element).attr('type') === 'radio' && $attrs['ngModel']) {
//                    	console.log('ifClicked===============' + $(element).attr('value'))
                    	$scope.roleName = $(element).attr('value').name;
                    	if (ngModel.$viewValue != value) {
                            $(element).iCheck('check');
                            ngModel.$setViewValue(value);
                        } else {
                            $(element).iCheck('uncheck');
                            ngModel.$setViewValue(undefined);
                        }
                        ngModel.$render();
                	}
                });
            });
        }
    };
});
