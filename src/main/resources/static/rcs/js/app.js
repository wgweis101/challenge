var app = angular.module("wsApp", []);

app.controller("wsController", function($scope, $http) {

    $scope.reductionData = '[[94133,94133],[94200,94299],[94226,94399]]';
    $scope.resultDataElements = {};
    $scope.errors = {};
    $scope.warnings = {};
    $scope.informationals = {};

    $scope.submitData =
        function() {
            var parameters = {
                data: $scope.reductionData
            };
            console.log('clicked and sending ', parameters);

            $http.get("rest/reducer", {params: {data: $scope.reductionData}})
                .success(function(data, status, headers, config) {
                    $scope.setMessages(data);
                    $scope.resultDataElements = data.rangeElements;
                })
                .error(function(data, status, headers, config, statusText) {
                    $scope.setMessages(data);
                    console.log('Get request for data range reduction returned with an error status of ' + status + ' (' + statusText + ').');
                });
        }

   $scope.setMessages = function(data) {
       $scope.errors = data.errors;
       $scope.warnings = data.warnings;
       $scope.informationals = data.informationals;

       if ($scope.informationals.length > 0) {
           // because for some reason I can't toggle ng-show.
           angular.element(informationals).css({display: 'block'});
       }

       if ($scope.warnings.length > 0) {
           // because for some reason I can't toggle ng-show .
           angular.element(warnings).css({display: 'block'});
       }

       if ($scope.errors.length > 0) {
           angular.element(errors).css({display: 'block'});
       }
   }
});