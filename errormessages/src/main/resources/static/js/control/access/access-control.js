app.controller('login', function ($rootScope, $scope, Main, $http, $location, $localStorage) {
    $scope.login = () => {
        $scope.data = {
            "username": $scope.username,
            "password": $scope.password
        }
        Main.post('/auth/login', $scope.data, (success) => {
            $localStorage.sesion = success.data;
            $localStorage.logged = true;
            $location.path('/users');
        }, (error) => {
            $rootScope.showToastr(error.data.type, error.data.text);
        });
    }
});