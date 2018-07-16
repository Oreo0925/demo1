/*angular.module('celefishApp.services').provider('variableService',  function () {
    var data = {id:0, roleId: 0, isInit:false};
    var varivale = function (id, roleId, isInit) {
        if (id != 0) {
           data.id= id;
           data.roleId = roleId;
           data.isInit = isInit;
        }
        return data;
    };
    this.$get = function () {
        return varivale;
    };
    variableService($routeParams.parentId, true); //Set up memberId to variable; false is initial the variable.
    var data = variableService(0, false);//Get memberId from variable.
});*/

angular.module('celefishApp.core').value('variableService', 
	{
		'id':0, 
		'name':""
	}
);