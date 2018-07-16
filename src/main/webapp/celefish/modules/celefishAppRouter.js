'use strict';
angular
	.module('celefishApp.routes', ['ngRoute'])
	.config(function($routeProvider) {
		$routeProvider.when('/operation/:deviceId', {
			templateUrl : 'tpls/operation/operation.html',
			controller : 'ParentDeviceCtrl'
		}).when('/param', {
			templateUrl : 'tpls/param/param.html',
		}).when('/member/:parentId', {
			templateUrl : 'tpls/member/MemberRole.html',
			controller : 'MemberRoleCtrl'
		}).when('/group/:groupId', {
			templateUrl : 'tpls/group/Group.html',
			controller : 'GroupCtrl'
		}).otherwise({
			redirectTo : '/param'
		})
	});
