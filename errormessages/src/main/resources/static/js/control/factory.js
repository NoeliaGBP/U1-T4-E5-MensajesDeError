app.factory('Main', function ($http) {
    let protocol = location.protocol;
    let host = location.host;
    var baseUrl = `http://192.168.20.127:8082`;

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
        put: function (service,data, success, error) {
            return $http({
                method: 'PUT',
                url: baseUrl + service,
                withCredentials: false,
                data: JSON.stringify(data)
            }).then(success, error);
        }
    };
});
