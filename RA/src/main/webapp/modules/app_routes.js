/**
 * Route provider in the application. Defines the routes, templates and
 * controllers to be used.
 *
 * Created by abhaykulkarni on 03/12/16.
 */
(function() {
  "use strict";

  angular.module('RegistrationAssistant')
    .config(Router);

  Router.$inject = ['$routeProvider'];
  function Router($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'partials/temp.html',
        controller: 'TempController'
      })
      .when('/register/:client?', {
        templateUrl: 'partials/temp.html',
        controller: 'HomeController'
      })
      .when('/signUp', {
        templateUrl: 'partials/generic_signup.html',
        controller: 'SignUpController'
      })
      .when('/contactInfo', {
        templateUrl: 'partials/generic_contactInfo.html',
        controller: 'ContactController'
      })
      .when('/additionalInfo', {
        templateUrl: 'partials/generic_addnlInfo.html',
        controller: 'AdditionalInfoController'
      })
      .when('/createAcct', {
        templateUrl: 'partials/generic_createAcct.html',
        controller: 'CreateAccountController'
      })
      .when('/myHome', {
        templateUrl: 'partials/generic_home.html',
        controller: 'MyHomeController'
      })
  }
})();