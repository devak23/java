/**
 * @version $Revision: #29 $ submitted $DateTime: 2012/05/22 12:44:49 $ by $Author: ugordda $
 *
 */

var $$ = jQuery.noConflict();

var timeoutInMilliseconds = timeoutInSeconds * 1000;

var thirtySecondsInMillis = 30 * 1000;
var fiveSecondsInMillis = 5 * 1000;

var timeoutBuffer = thirtySecondsInMillis + thirtySecondsInMillis;

timeoutInMilliseconds = timeoutInMilliseconds - timeoutBuffer;

var maxTimeoutExtensionReached = false;

var warnPeriodSeconds = warnPeriodMinutes * 60;
var warnPeriodMilliseconds = warnPeriodSeconds * 1000;

var pageLoadTime = new Date();
var sessionTimeoutTime = new Date();
sessionTimeoutTime.setSeconds(sessionTimeoutTime.getSeconds() + timeoutInSeconds);
// get back to a regular date:

sessionTimeoutTime = new Date(sessionTimeoutTime);

// create the warning dialog:
var buttonOpts = {};
buttonOpts[timeoutWarningOKButtonText] = function() {
    warnOfPendingTimeoutOkClick();
};
$$(document).ready(function() {
    $$("#warnOfSessionExpiring").dialog({
        title: sessionWarningModalTitle,
        width: 300,
        height: 190,
        position: 'center',
        resizable: true,
        autoOpen: false,
        bgiframe: true,
        fontSize:15,
        modal: true,
        closeOnEscape: false,
        beforeclose: function(event, ui) {
            beforeWarnOfPendingTimeoutClose();
        },
        buttons: buttonOpts
    });
});

var sessionWarningTimer;
var sessionTimeoutTimer;
// start when the page is ready:
$$(function() {
    startSessionTimer();
});

function startSessionTimer() {
    startSessionTimerConditional(true);
}
function getWarningPeriod(_timeoutInMilliseconds, _warnPeriodMilliseconds) {
    var warningPeriod = _timeoutInMilliseconds - _warnPeriodMilliseconds;
    if (warningPeriod >= _timeoutInMilliseconds || warningPeriod <= 0) {
        // just incase...never let the warning period be greater than the timeout itself...
        // so set the warning period to 3/4 of the timeout:
        warningPeriod = _timeoutInMilliseconds * .75;
    }
    return warningPeriod;
}

function startSessionTimerConditional(startWarningTimerAlso) {
    sessionTimeoutTime = new Date();
    sessionTimeoutTime.setSeconds(sessionTimeoutTime.getSeconds() + timeoutInSeconds);
    // get back to a regular date:
    sessionTimeoutTime = new Date(sessionTimeoutTime);

    if (startWarningTimerAlso) {
        var warningPeriod = getWarningPeriod(timeoutInMilliseconds, warnPeriodMilliseconds);
        sessionWarningTimer = setTimeout(warnOfPendingTimeout, warningPeriod);
    }
    sessionTimeoutTimer = setTimeout(sessionTimeoutAction, timeoutInMilliseconds);
}

function clearSessionTimer() {
    clearTimeout(sessionWarningTimer);
    clearTimeout(sessionTimeoutTimer);
}

function warnOfPendingTimeout() {
    if (maxTimeoutExtensionReached) {
        // don't warn, just let the page timeout.
        closeSessionWarnModal();
        return;
    }
    if (isSessionExpired()) {
        sessionTimeoutAction();
    } else {
        openSessionWarnModal();
    }
}



function beforeWarnOfPendingTimeoutClose() {
}


function warnOfPendingTimeoutOkClick() {
    // user wants to extend their session:
    if (isSessionExpired()) {
        //alert ("Session has expired");
        sessionTimeoutAction();
    } else {
        $$.ajax({
            type: "POST",
            dataType: "json",
            url: "/servlet/SessionKeepAlive",
            success: handleTimeoutExtension,
            error: handleTimeoutExtensionError
        });
    }
    closeSessionWarnModal();
}


function warnOfPendingTimeoutCancelClick() {
    closeSessionWarnModal();
}

function handleTimeoutExtension(data, textStatus, jqXHR) {
    maxTimeoutExtensionReached = data.maxTimeoutExtensionReached;
    clearSessionTimer();
    if (maxTimeoutExtensionReached) {
        // don't offer to extend the timeout anymore...
        startSessionTimerConditional(false);
    } else {
        startSessionTimer();
    }
    closeSessionWarnModal();  // just in case?
}

function handleTimeoutExtensionError(xhr, ajaxOptions, thrownError) {
    if (xhr.status == 412) {
        // unable to extend timeout...do nothing.
    }
    closeSessionWarnModal();  // just in case?
}


function openSessionWarnModal() {
    $$("#warnOfSessionExpiring").dialog('open');
}


function closeSessionWarnModal() {
    $$("#warnOfSessionExpiring").dialog('close');
}


function isSessionExpired() {
    var now = new Date();
    var sessionExpired = now >= sessionTimeoutTime;
    return sessionExpired;
}


function sessionTimeoutAction() {
    // session has timed out...load timeout page:
    window.location.replace(sessionTimeoutPage);
}