app.controller('login', function ($rootScope, $scope, Main, $http, $location, $localStorage) {
    $scope.login = () => {
        $scope.data = {
            "username": $scope.username,
            "password": $scope.password
        }

        Main.post('/api/user/login', $scope.data, (success) => {
            $localStorage.logged = true;
            $location.path('/users');
        }, (error) => {
            $rootScope.showToastr("error", "No se ha podido iniciar sesión");
        });
    }

});