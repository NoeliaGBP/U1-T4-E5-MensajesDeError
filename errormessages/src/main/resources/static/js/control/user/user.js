app.controller('user', function ($rootScope, $scope, Main, $http, $location, $localStorage) {


    $scope.users = [];
    $scope.updateUser = {};
    $scope.disableUser = {};
    $scope.user = {};

    let us = '/api/user';

    $scope.init = () => {
        Main.get(us,
            (success) => {
                $scope.users = success.data.result;
            },
            (error) => {
                $rootScope.showToastr(error.data.type, error.data.text);
            });
    }

    $scope.showModalUpdate = (user) => {
        angular.copy(user, $scope.updateUser);
    }

    $scope.showModalDisable = (user) => {
        angular.copy(user, $scope.disableUser);
    }

    $scope.save = () => {
        Main.post(us, $scope.user,
            (success) => {
                $rootScope.showToastr("success", "Registro exitoso");
                $scope.users.push(success.data.result);
                $scope.user = {};
            }, (error) => {
                $rootScope.showToastr("error", "No se ha podido realizar el registro");
                $scope.clear2 = {};
                $scope.user = angular.copy($scope.clear2);
                document.getElementById('createForm').reset();
            })
    }


    $scope.update = () => {
        Main.put(us, $scope.updateUser,
            (success) => {
                $rootScope.showToastr("success", "Modificación exitosa");
                let result = success.data.result;
                let index = $scope.users.findIndex(x => x.id === result.id);
                $scope.users.splice(index, 1, result);
            },
            (error) => {
                $rootScope.showToastr("error", "No se ha podido realizar la modificación");
            });
    }


    $scope.disable = () => {
        Main.put(us + '/' + $scope.disableUser.id,null,
            (success) => {
                $rootScope.showToastr(success.data.type, success.data.text);
                let result = success.data.result;
                let index = $scope.users.findIndex(x => x.id === result.id);
                $scope.users.splice(index, 1, result);
            },
            (error) => {
                $("#switch" + $scope.disableUser.id).prop('checked', !$("#switch" + $scope.disableUser.id).prop('checked'));
                $rootScope.showToastr(error.data.type, error.data.text);
            });
    }

    $scope.cancelDisabled = () => {
        $("#switch" + $scope.disableUser.id).prop('checked', !$("#switch" + $scope.disableUser.id).prop('checked'));
    }

    $scope.clear = () => {
        $scope.clear2 = {};
        $scope.user = angular.copy($scope.clear2);
        document.getElementById('createForm').reset();
    }

});