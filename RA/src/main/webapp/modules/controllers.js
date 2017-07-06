/**
 * Controller definitions of the application
 *
 * Created by abhaykulkarni on 03/12/16.
 */
(function() {
  "use strict";

  angular.module('RegistrationAssistant')
    .controller('HomeController', HomeController)
    .controller('SignUpController', SignUpController)
    .controller('ContactController', ContactController)
    .controller('AdditionalInfoController', AdditionalInfoController)
    .controller('CreateAccountController', CreateAccountController)
    .controller('TempController', TempController)
    .controller('MyHomeController', MyHomeController);

  // --------------- HomeController ----------------------------
  HomeController.$inject = ['$scope', 'RegistrationService', '$location', '$routeParams','UIConstants'];
  function HomeController($scope, RegistrationService, $location, $routeParams,UIConstants) {
    var rs = RegistrationService;
    rs.initializeUserSelection();
    rs.userSelection.client = $routeParams.client;
    rs.userSelection.sponsor = UIConstants[rs.userSelection.client];
    console.debug(rs.userSelection.sponsor);

    $location.path('/signUp');
  }

  // --------------- SignUpController ----------------------------
  SignUpController.$inject = ['$scope', 'RegistrationService', 'MessageService', '$location'];
  function SignUpController($scope, RegistrationService, MessageService, $location) {
    var rs = RegistrationService;
    $scope.userSelection = rs.userSelection;

    $scope.interacted = function (element, form) {
      if (!element) return form.$submitted;
      return form.$submitted || element.$dirty;
    };

    $scope.loadStep2 = function(form, url) {
      if (form.$valid) {
        $location.path(url);
      } else {
        MessageService.error("Please fix the errors on the page and try again");
      }
    }
  }

  // --------------- ContactController ----------------------------
  ContactController.$inject = ['$scope', 'RegistrationService', '$location'];
  function ContactController($scope, RegistrationService, $location) {
    var rs = RegistrationService;
    $scope.userSelection = rs.userSelection;
    if (!rs.userSelection) {
      $location.url('/')
    }
  }

  // --------------- AdditionalInfoController ----------------------------
  AdditionalInfoController.$inject = ['$scope', 'RegistrationService', '$location'];
  function AdditionalInfoController($scope, RegistrationService, $location) {
    var rs = RegistrationService;
    $scope.userSelection = rs.userSelection;
    if (!rs.userSelection) {
      $location.url('/')
    }
  }

  // --------------- CreateAccountController ----------------------------
  CreateAccountController.$inject = ['$scope', 'RegistrationService','MessageService','$location'];
  function CreateAccountController($scope, RegistrationService,MessageService,$location) {
    var rs = RegistrationService;
    $scope.userSelection = rs.userSelection;

    if (!rs.userSelection) {
      $location.url('/')
    }


    $scope.registerUser = function() {
      var username = rs.userSelection.applicant.person.firstName
        + ' '
        + rs.userSelection.applicant.person.middleName
        + ' '
        + rs.userSelection.applicant.person.lastName;

      var promise = rs.registerUser();
      promise.then(function (data) {
        MessageService.success("User " + username +  " has been registered successfully.");
        rs.userSelection.profile = {};
        rs.userSelection.profile.username = username;
        rs.userSelection.profile.uniqueId = data.uniqueId;
        console.log(JSON.stringify(rs.userSelection.profile));
        $location.url('myHome');
      });
    }
  }

  // --------------- MyHomeController ----------------------------
  MyHomeController.$inject = ['$scope', 'RegistrationService'];
  function MyHomeController($scope, RegistrationService) {
    console.log('MyHomeController called');
    var rs = RegistrationService;
    $scope.userSelection = rs.userSelection;
    console.log("unique Id = " + $scope.userSelection.profile.uniqueId);
  }

  // --------------- TempController ----------------------------
  function TempController() {
    console.log('TempController called');
  }
})();