app.factory('myHttpInterceptor', function ($q, $rootScope, $location, $localStorage) {

    return {
        request: function (config) {
            if (config.url.indexOf("/api/") !== -1) {
                isLogged = $localStorage.logged;
                if (isLogged && isLogged != undefined) {
                    $location.path('/users');
                }
            }
            return config || $q.when(config);
        },
        responseError: function (response) {
            // Unauthorized
            if (response.status == 400) {
                if (response.data.errors) {
                    console.log(response.data.errors);
                    $rootScope.getErrors(response.data.errors);
                }
            }
            if (response.status == 401) {
                // Cerramos sesión
                $rootScope.logout();
            }
            if (response.status == 403) {
                $rootScope.showToastr('warning', 'No se cuentan con los permisos');
            }
            if (response.status == 404) {
                $rootScope.showToastr('error', 'Página no encontrada');
            }
            if (response.status == 500) {
                $rootScope.showToastr('error', 'Se ha presentado un inconveniente intentalo mas tarde');
            }
            if (response.status == -1) {
                $rootScope.showToastr('error', 'No se obtuvo respuesta del servidor');
            }

            return $q.reject(response);
        }
    }
});

app.factory('Main', function ($http) {
    let protocol = location.protocol;
    let host = location.host;
    var baseUrl = `http://192.168.52.81:8080`;

    return {
        post: function (service, data, success, error) {
            return $http({
                method: 'POST',
                url: baseUrl + service,
                withCredentials: false,
                data: JSON.stringify(data)
            }).then(success, error);
        },
        get: function (service, success, error) {
            return $http({
                method: 'GET',
                url: baseUrl + service,
                withCredentials: false,
            }).then(success, error);
        },
        delete: function (service, success, error) {
            return $http({
                method: 'DELETE',
                url: baseUrl + service,
                withCredentials: false,
            }).then(success, error);
        },
        put: function (service, data, success, error) {
            return $http({
                method: 'PUT',
                url: baseUrl + service,
                withCredentials: false,
                data: JSON.stringify(data)
            }).then(success, error);
        }
    };
});
