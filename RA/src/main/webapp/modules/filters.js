/**
 * Created by abhaykulkarni on 10/12/16.
 */
(function() {
  "use strict";
  angular.module('RegistrationAssistant')
    .filter('capitalize', CapitalizeFilter);

  function CapitalizeFilter() {
    return function (input) {
      return (!!input) ? input.charAt(0).toUpperCase() + input.substr(1).toLowerCase() : '';
    }
  }
})();
