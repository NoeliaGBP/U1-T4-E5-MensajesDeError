app.controller('user', function ($rootScope, $scope, Main) {
    var $ = jQuery;
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
                $('#modalForm').modal('toggle');
            }, (error) => {
                $rootScope.showToastr(error.data.type, error.data.text);
            })
    }


    $scope.update = () => {
        Main.put(us, $scope.updateUser,
            (success) => {
                $rootScope.showToastr("success", "Modificación exitosa");
                let result = success.data.result;
                let index = $scope.users.findIndex(x => x.id === result.id);
                $scope.users.splice(index, 1, result);
                $('#modal-confirmMod').modal('toggle');
                $('#modal-modify').modal('hide');
            },
            (error) => {
                $('#modal-confirmMod').modal('toggle');
                $('#modal-modify').modal('show');
                $rootScope.showToastr(error.data.type, error.data.text);
            });
    }


    $scope.disable = () => {
        Main.put(us + '/' + $scope.disableUser.id, null,
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
});