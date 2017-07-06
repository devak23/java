/**
 * Entry point into the application.
 *
 * Created by abhaykulkarni on 03/12/16.
 */
(function() {
  "use strict";

  angular.module('RegistrationAssistant',[
    'ngRoute',
    'toaster',
    'ngMessages'
  ])
    .constant("UIConstants",{
      'apple': {
        name: 'apple',
        shortName: 'Apple',
        companyName: 'Apple Inc.',
        header: 'Apple',
        logoUrl: 'images/apple-testing.png'
      },
      'oracle': {
        name: 'oracle',
        shortName: 'Oracle',
        companyName: 'Oracle Corporation',
        header: 'Oracle Certification Program',
        logoUrl: 'images/oracle-testing.png'
      },
      'cisco': {
        name: 'cisco',
        shortName: 'Cisco',
        companyName: 'Cisco Systems, Inc.',
        header: 'Cisco Systems',
        logoUrl: 'images/cisco-testing.png'
      }
    })
})();