app.config(function ($routeProvider,$locationProvider) {
    $routeProvider.
        when('/', {
            templateUrl: '/views/login.html',
            controller: 'login'
        })
});