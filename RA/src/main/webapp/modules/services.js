/**
 * Service definitions of the application
 *
 * Created by abhaykulkarni on 03/12/16.
 */
(function () {
  "use strict";

  angular.module('RegistrationAssistant')
    .service('RegistrationService', RegistrationService)
    .service('MessageService', MessageService);

  //---------------------- RegistrationService -------------------------
  RegistrationService.$inject = ['$http'];
  function RegistrationService($http) {
    var that = this;
    this.userSelection = this.userSelection || null;
    var applicant = {
      person: {
        id: '',
        title: '',
        firstName: '',
        lastName: '',
        middleName: '',
        suffix: '',
        email: '',
        confirmEmail: ''
      },
      additionalInfo: {
        employerName: '',
        workForPartnerOrSeller: '',
        takenExamBefore: '',
        employee: '',
        employeeNumber: '',
        networkingAcademyId: '',
        networkingAcademyUsername: ''
      },
      addresses: [],
      credential: {
        username: '',
        password: '',
        confirmPassword: '',
        secQ1: '',
        secAns1: '',
        secQ2: '',
        secAns2: ''
      },
      languageCode: ''
    };
    var address = {
      addressType: '',
      line1: '',
      line2: '',
      line3: '',
      company: '',
      city: '',
      country: '',
      zipcode: '',
      telephoneCode: '',
      telephone:'',
      extension: '',
      mobileCode:'',
      mobile:'',
      primary: ''
    };

    /**
     * initializes the userSelection. Should be invoked only
     * from the SignupController.
     */
    this.initializeUserSelection = function () {
      if (!this.userSelection) {
        applicant.addresses.push(address);
        this.userSelection = {};
        this.userSelection.applicant = applicant;
      }
    };

    /**
     * Resets all the information present in the userSelection. Should be
     * invoked from the CreateAccountController.
     */
    this.resetUserSelection = function() {
      this.userSelection = null;
    };

    /**
     * Registers the candidate data.
     */
    this.registerUser = function() {
      return $http({
        method: 'POST',
        url: 'http://localhost:8080/RA/api/assistant/register',
        data: that.userSelection,
        headers : {
          'Content-Type' : 'application/json'
        }
      })
        .then(function (response) {
          return response.data;
        });
    }
  }

// ------------------------ MessageService ------------------------------
  MessageService.$inject = [ 'toaster' ];
  function MessageService(toaster) {
    var defaults = {
      "title" : 'Information',
      "body" : '',
      "type" : 'info',
      "timeout" : 5000,
      "bodyOutputType" : 'trustedHtml',
      "animation-class" : 'toast-top-right'
    };

    this.success = function(msg, msgTitle) {
      var custom = angular.extend(defaults, {
        "type" : 'success',
        "title" : msgTitle,
        "body" : msg
      });
      toaster.pop(custom);
    };

    this.error = function(msg, msgTitle) {
      var custom = angular.extend(defaults, {
        "type" : 'error',
        "title" : msgTitle,
        "body" : msg,
        "tap-to-dismiss": true,
      });
      toaster.pop(custom);
    };

    this.warning = function(msg, msgTitle) {
      var custom = angular.extend(defaults, {
        "type" : 'warning',
        "title" : msgTitle,
        "body" : msg
      });
      toaster.pop(custom);
    };

    this.info = function (msg, msgTitle) {
      var custom = angular.extend(defaults, {
        "title" : msgTitle,
        "body" : msg,
        "tap-to-dismiss": true,
      });
      toaster.pop(custom);
    }
  }
})();