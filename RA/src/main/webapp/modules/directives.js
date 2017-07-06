/**
 * Created by abhaykulkarni on 12/12/16.
 */
(function () {
  "use strict";
  angular.module('RegistrationAssistant')
    .directive('sectionTitle', SectionTitleDirective);

  function SectionTitleDirective() {
    console.log('in the directive');
    return {
      restrict: 'EA',
      scope: {
        text: '@',
        userSelection: '='
      },
      templateUrl: "partials/section-header.html",
      link: function (scope, element, attrs) {
        console.log('In the directive: ', scope.userSelection, scope.text);

      }
    }
  }
})();