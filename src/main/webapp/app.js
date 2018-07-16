//var cnApp = angular.module('cnApp', ['ngRoute', 'loginStoreCtrls', 'device', 'deviceProperties']);
var cnApp = angular.module('cnApp', ['ngRoute', 'rootScopeService', 'monitorApp', 'accountApp', 'deviceApp', 'groupApp']);
cnApp.config(function($routeProvider){
	$routeProvider.when('/login', {
        templateUrl: 'tpls/login/login.html',
        controller: 'loginCtrl'
    }).when('/register', {
        //controller: 'RegisterController',
        templateUrl: 'tpls/register/register.html'
    }).when('/deviceList', {
        templateUrl: 'tpls/device/deviceList.html',
        controller: 'deviceListCtrl'
    }).when('/deviceProperties', {
        templateUrl: 'tpls/device/deviceProperties.html',
        //controller: 'devicePropertiesCtrl'
    }).when('/groupList', {
        templateUrl: 'tpls/group/groupList.html',
        controller: 'groupCtrl'
    }).when('/monitor', {
        templateUrl: 'monitor.html',
        controller: 'monitorCtrl'
    }).otherwise({
        redirectTo: '/login'
    })
});