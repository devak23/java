//<![CDATA[

var $j = jQuery.noConflict();
jQuery(document).ready(function() {
    var warningOrError = $j(".warning");
    var alerts =  $j(".alert").not(".hidden");
    var updatedMessages = $j(".updated");
	var capvaModal = $j("#privacyOptInDialog");

    if ((warningOrError != undefined && warningOrError.length > 0)
        || (alerts != undefined && alerts.length > 0)
        || (updatedMessages != undefined && updatedMessages.length > 0)
        || (capvaModal.length !== 0 && capvaModal.dialog("isOpen")===true)) {
        //handled in firstMessageFocus
	} else {
        focsOnFirstInput();
    }
});

function focsOnFirstInput() {
    var elements = $j(":input:not(input[type=hidden]):not(input[type=submit])");
    var firstInputElement = $j(elements).filter(function() {
               return !($j(this).css("visibility") == "hidden" || $j(this).css('display') == "none");
            }).first();
    var firstSelectElement = $j("select:first");
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

    firstInputField.focus();
}

/* ]]> */