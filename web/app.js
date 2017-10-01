var endpoint = window.location.protocol + "//" + window.location.hostname + ":8989/api/v1/";

angular.module('testApp', []).controller('TestController', function($http, $scope) {
    $http.get(endpoint + 'clients').then(
        function(response) {
            console.log(response.data);
            $scope.clients = response.data;
        },
        function(response) {
            console.log(response.data);
            alert('Request failed');
        }
    );
});