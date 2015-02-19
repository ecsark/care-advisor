
var app = angular.module('caregiver', ['ngRoute', 'angular-loading-bar', 'ngAnimate', 'ngMaterial', 'ng-mfb']);
//, 'ui.bootstrap'
//This configures the routes and associates each route with a view and a controller
app.config(function ($routeProvider) {
    $routeProvider
        .when('/ask',
            {
                controller: 'MedicalAsk',
                templateUrl:'../assets/app/partials/ask.html'
            })
        //Define a route that has a route parameter in it (:customerID)
        .otherwise({ redirectTo: '/ask' });
});

app.config(function($mdThemingProvider) {
  $mdThemingProvider.theme('default')
    .backgroundPalette('grey');
});