toastr.options = { "positionClass": "toast-top-center", };
const app = angular.module('error-message', ['ngRoute', 'ngStorage', 'siTable']);
app.run(function ($rootScope,$location,$localStorage) {

    $rootScope.showToastr = (type,message)=>{
        if(type ==='success'){
            toastr.success('', message);
        }
        if(type ==='error'){
            toastr.error('', message);
        }
        if(type ==='warning'){
            toastr.warning('', message);
        }
    }

});