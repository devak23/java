//<![CDATA[

var $j = jQuery.noConflict();
jQuery(document).ready(function() {
    var warningOrError = $j(".warning");
    var alerts =  $j(".alert").not(".hidden");
    var updatedMessages = $j(".updated");
	var capvaModal = $j("#privacyOptInDialog");
	
    if (warningOrError != undefined && warningOrError.length > 0) {
        focusOnMessage(warningOrError[0]);
    } else if (alerts != undefined && alerts.length > 0) {
        focusOnMessage(alerts[0]);
    } else if (updatedMessages != undefined && updatedMessages.length > 0) {
        focusOnMessage(updatedMessages[0]);
    // if other pages feature a modal upon initial load, then this line can either be duplicated or made more generic (currently used for the first page of CAPVA)
	} else if (capvaModal.length !== 0 && capvaModal.dialog("isOpen")===true) {
		focusOnFirstModalInput("#privacyOptInDialog");
	}
});

function focusOnMessage(message, infoOnly) {
    message.setAttribute("tabIndex", "-1");
    if (navigator.appVersion.indexOf("MSIE") == -1) {
        if(!infoOnly) {
            message.setAttribute("role","alert");
        }

    }
    message.focus();
}

function focusOnFirstModalInput(m) {
	var firstInputElement = $j(m + " :input:not(input[type=hidden]):not(input[type=submit]):first");
    var firstSelectElement = $j(m + " select:first");
    var positionInputTop = firstInputElement.position() == undefined ? 99999999 : firstInputElement.position().top;
    var positionSelectTop = firstSelectElement.position() == undefined ? 99999999 : firstSelectElement.position().top;
    var positionInputLeft = firstInputElement.position() == undefined ? 99999999 : firstInputElement.position().left;
    var positionSelectLeft = firstSelectElement.position() == undefined ? 99999999 : firstSelectElement.position().left;

    var firstInputField = firstInputElement;
    if (positionInputTop == positionSelectTop) {
        if (positionInputLeft <= positionSelectLeft) {
            firstInputField = firstInputElement
        } else {
            firstInputField = firstSelectElement;
        }
    } else {
        if (positionInputTop <= positionSelectTop) {
            firstInputField = firstInputElement;
        } else {
            firstInputField = firstSelectElement;
        }
    }

    // this doesn't seem to work without a brief pause to let the modal render
	setTimeout(function(){firstInputField.focus();},50);
}

/* ]]> */