app.config(function ($httpProvider) {
    $httpProvider.interceptors.push('myHttpInterceptor');
});

app.config(function ($routeProvider, $locationProvider) {
    $routeProvider.when('/', {
        templateUrl: '/views/login.html',
        controller: 'login'
    }).when('/users', {
        templateUrl: '/views/user.html',
        controller: 'user'
    }).otherwise(
        {redirectTo: '/'}
    )

});