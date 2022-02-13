app.config(function ($routeProvider,$locationProvider) {
    $routeProvider.
        when('/', {
            templateUrl: '/views/access/index.html',
            controller: 'login'
        })
});