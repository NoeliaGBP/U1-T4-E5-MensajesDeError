app.config(function ($httpProvider) {
    $httpProvider.interceptors.push('myHttpInterceptor');
});

app.config(function ($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: '/views/login.html',
        controller: 'login'
    }).when('/users', {
        templateUrl: '/views/user.html',
        controller: 'user'
    })
});