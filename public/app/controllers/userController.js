app.controller('UserController', function ($scope, $http, authService) {

    init();

    function init() {
        $scope.login = {};
    }

    $scope.loginSubmit = function () {
        $http.post('http://192.168.1.114:9000/users/auth', $scope.login).success(onResponse);
    };

    function onResponse(response) {
        authService.loginConfirmed();
    }
});

app.directive('integratedContainer', function() {
    return {
        restrict: 'C',
        link: function ($scope, elem, attrs) {
            var loginDiv = elem.find('#user');
            var mainDiv = elem.find('#main');

            loginDiv.hide();

            $scope.$on('event:auth-loginRequired', function() {
                loginDiv.slideDown('slow', function() {
                    mainDiv.hide();
                });
            });

            $scope.$on('event:auth-loginConfirmed', function() {
                mainDiv.show();
                loginDiv.hide();
            });
        }
    }
});