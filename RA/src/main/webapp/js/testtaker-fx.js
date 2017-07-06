$.noConflict();

jQuery(function($){
    // draw dialog divs
    var bodyTag = $("body");
    bodyTag.append('<div id="progressDialog"/>');
    bodyTag.append('<div id="messageDialog"/>');

    if (document.getElementById('confirmDialog') == null){
        bodyTag.append('<div id="confirmDialog" role="alertdialog" aria-describedby="confirmDialog"/>');
    }
});

function JQUERY_BASE_VERSION() {
    return "1.3.2";
}

function progressDialog(message, title, timeout, cssStyle) {
    var jQueryVersion = jQuery.fn.jquery;
    var progressDialog = $$('#progressDialog');
    var theMessage = message ? message : 'Please Wait...';
    var theTitle = title ? title : 'Processing Request';
    var theTimeout = timeout ? timeout : 60000;
    var direction = $$('#container').css('direction');
    var theStyle = cssStyle ? cssStyle : 'padding: 35px 20px 0px 20px; text-align:center; line-height:21px;';
    // set the direction of the span with the message to the same value as the container tag
    theStyle += 'direction: ' + direction + ';';    
    progressDialog.html('<span><img src="/webresources/testtaker/images/ajax-loader.gif" alt=""/>&nbsp;&nbsp;&nbsp;' + theMessage + '</span>');
    progressDialog.attr('title', theTitle);
    $$('#ui-dialog-title-progressDialog').html(theTitle);
    $$('.ui-dialog-titlebar').css('direction', direction);
	if(direction == 'rtl'){
		$$('.ui-dialog-title').css('float', 'right');
	}
	progressDialog.attr('style', theStyle);
    setTimeout(function() {progressDialog.dialog('close')}, theTimeout);
    // initialize progress dialog
    progressDialog.dialog({
            autoOpen: false,
            bgiframe: true,
            modal: true,
            closeOnEscape: false,
            resizable: false,
            draggable: false
        });
        // remove the 'x' close button so the user can't close the progress dialog
        progressDialog.dialog().parents('.ui-dialog').find('.ui-dialog-titlebar-close').remove();
        progressDialog.dialog('open');

}


/*
Closes the jQuery dialog after the specified delay.  An exception is thrown
when a negative delay is passed.
*/

function closeDialog(id, delay) {
    if (delay < 0) throw 'Negative: delay';
    var closeDialog = function() {
        $$(id).dialog('close');
    };
    if (delay == 0) {
        closeDialog.call();
    } else {
        setTimeout(closeDialog, delay);
    }
}

function messageDialog(message, title, buttonLabel, cssStyle, focusReturnElement) {
    var messageDialog = $$('#messageDialog');
    var theTitle = title ? title : 'Message';
    // initialize message dialog
    messageDialog.dialog({
        title: theTitle,
        autoOpen: false,
        bgiframe: true,
        modal: true
    });

    var theButtonLabel = buttonLabel ? buttonLabel : 'OK';
    var buttons = {};
    buttons[theButtonLabel] = function() {
        $$(this).dialog('close');
        if (focusReturnElement) {
            focusReturnElement.focus();
        }
    }

    messageDialog.html('<p>' + message + '</p>');
    messageDialog.attr('title', theTitle);
    $$('#ui-dialog-title-messageDialog').html(theTitle);
    messageDialog.attr('style', cssStyle);
    messageDialog.dialog('option', 'buttons', buttons);
    messageDialog.dialog('open');
    return false; // return to eat form submits
}


/**
 * Enable modal confirmation dialog on an clickable element
 *
 * onMouseUp, onMouseDown and onClick will be intercepted and only get called
 * if user confirm
 *
 * Example usage in html
 *
 * $(document).ready(function(){
 *    enableConfirmDialog("#removeButton", "My Message");
 *    enableConfirmDialog("#removeButton", "My Message", function(){ return doSomething();});
 *    enableConfirmDialog("#removeButton", "My Message", "", "Yes", "No", "My Title");
 * });
 *
 * @param elementSelector jquery element selector - required
 * @param message confirmation message - optional, default to "Are you sure?"
 * @param preconditionCallback a js function to be called before triggering the modal dialog
 *        the function should return true to enable dialog
 * @param okButtonLabel label for the Ok button - optional, default to "Ok"
 * @param cancelButtonLabel label for the Cancel button - optional, default to "Cancel"
 * @param title the title - optional, default to "Confirm"
 */
function enableConfirmDialog(elementSelector, message, preconditionCallback, okButtonLabel, cancelButtonLabel, title, progressMsg, progressTitle){
    var $$ = jQuery.noConflict();

    // FF: onclick=function onclick(event) { alert("inline onclick"); return false; }
    //     onClick=alert('inline onclick'); return false;
    //
    // IE8:onclick=function onclick() { ... }
    //     onClick=alert('inline onclick'); return false;
    //
    // IE6 & 7 : onclick or onClick = function anonymous() { ... }

    $$(elementSelector).each(function() {
        var myElement = $$(this);
        // FF js firing order is up, click, down
        var js = {};
         var jQueryVersion = jQuery.fn.jquery;
        if (jQueryVersion === JQUERY_BASE_VERSION()) {
            js['onMouseUp'] = myElement.attr("onmouseup");
            js['onClick'] = myElement.attr("onclick");
            js['onMouseDown'] = myElement.attr("onmousedown");
        } else {
            js['onMouseUp'] = myElement.prop("onmouseup");
            js['onClick'] = myElement.prop("onclick");
            js['onMouseDown'] = myElement.prop("onmousedown");
        }

        // have to do both case for IE 6 & 7, otherwise the js will get call
        // before the modal dialog
        myElement.removeAttr("onMouseDown");
        myElement.removeAttr("onmousedown");
        myElement.removeAttr("onMouseUp");
        myElement.removeAttr("onmouseup");
        myElement.removeAttr("onClick");
        myElement.removeAttr("onclick");

        // default, ok, cancel and title if user didn't pass in one
        var myOkButtonLabel = okButtonLabel ? okButtonLabel : 'Ok';
        var myCancelButtonLabel = cancelButtonLabel ? cancelButtonLabel : 'Cancel';
        var myTitle = title ? title : 'Confirm';
        var myMessage = message ? message : 'Are you sure?';
        
        myElement.click(function(e){
            _confirmDialog(e, myMessage, preconditionCallback, myOkButtonLabel, myCancelButtonLabel, myTitle, js, progressMsg, progressTitle);
        });
    });
}

/**
 * This should be a private method.
 *
 * @param e
 * @param message
 * @param preconditionCallback
 * @param okButtonLabel
 * @param cancelButtonLabel
 * @param title
 * @param js
 */
function _confirmDialog(e, message, preconditionCallback, okButtonLabel, cancelButtonLabel, title, js, progressMsg, progressTitle){
	var $$ = jQuery.noConflict();
    var jQueryVersion = jQuery.fn.jquery;
    var confirmDialog = $$('#confirmDialog');
    var displayModal = true;
    // initialize progress dialog
    confirmDialog.dialog({
                autoOpen: false,
		        width: 400,
		        resizable: true,
		        modal: true,
		        bgiframe: true
     });

    // call the precondition callback, if it does not return true, don't display the modal
    if ((preconditionCallback) && $$.isFunction(preconditionCallback)){
        displayModal = !!preconditionCallback.apply();
    }

    if (displayModal){
        // setting up the buttons
        var myButtons = {};

        myButtons[cancelButtonLabel] = function() {
            $$(this).dialog('close');
        };

        myButtons[okButtonLabel] = function() {
            var myTarget = $$(e.currentTarget);

            // onClick returning false, just close the modal dialog
            if (_processJsCallback(js) === false){
                $$(this).dialog('close');
            }
            // else, unbind and let button/link go through
            else{
                $$(this).dialog('close');
                myTarget.unbind('click');

                // myTarget.click() doesn't work for link, work fine on button
                if (e.currentTarget.nodeName === 'A'){
                    window.location.href = e.currentTarget;
                }
                else{
                    myTarget.click();
                }
				progressDialog(progressMsg, progressTitle);
			}
        };



       confirmDialog.dialog('option', {buttons:myButtons});

        // setting the buttons, label, and message
       confirmDialog.dialog('option', {title:title});
       confirmDialog.html(message);

    	var direction = $$('#container').css('direction');		

		if(direction == 'rtl'){
			$$('.ui-dialog-title').css('float', 'right');
			$$('.ui-dialog-titlebar-close').css('right','93%');
		}

		$$('.ui-dialog-buttonpane').find('button:contains('+ okButtonLabel +')').addClass('modalButtonPrimary');
        $$('.ui-dialog-buttonpane').find('button:contains('+ cancelButtonLabel +')').addClass('modalButtonSecondary');

		// stop the button or link from it normal action
        e.preventDefault();
        confirmDialog.dialog('open');
    }
    else {
        // hey give the user back his javascript call
        if (_processJsCallback(js) === false){
            e.preventDefault();
        }
    }
}

/**
 * This should be a private method
 * 
 * apply javascript call and return the return value of onClick.  This value
 * will be used to determine to let the button/link normal event go through
 * 
 * @param js
 */
function _processJsCallback(js){
    var $$ = jQuery.noConflict();

    var returnVal;

    for (var key in js) {
       if ($$.isFunction(js[key])){
            var tmpVal = js[key].apply();
             if ((key == 'onClick')){
                    returnVal = tmpVal;
                }
            }
    }

    return returnVal;
}


/*
 * open #confirmDialog
 * @deprecated use enableConfirmDialog instead
 */
function modalConfirm(e, message, preCallback, yesCallback, noCallback){
	var $$ = jQuery.noConflict();

	var myButtons = {};
	myButtons[noButtonLabel] = function() {
        if ((noCallback != null) && $$.isFunction(noCallback)){
            noCallback.apply();
        }

		$$(this).dialog('close');
	};
	myButtons[yesButtonLabel] = function() {
        // there's a callback for javascript call on the event
        if ((yesCallback != null) && $$.isFunction(yesCallback)){
            yesCallback.apply();
        }

        $$(e.currentTarget).unbind('click');
    	$$(e.currentTarget).click();

        // incase the button have onClick="return false;"
        $$(this).dialog('close');
	};

	$$("#confirmDialog").dialog('option', {buttons:myButtons});
	$$("#confirmDialog").html(message);


    var keepGoing = true;
    if ((preCallback != null) && $$.isFunction(preCallback)){
        keepGoing = preCallback.apply();
    }

    if (keepGoing){
        e.preventDefault();
    	$$("#confirmDialog").dialog('open');
    }
}
function toggle(targetId) {
    target = document.getElementById(targetId);
    if (target.style.display == "none") {
        target.style.display = "";
    } else {
        target.style.display = "none";
    }
}


/**
 * Sets the focus on an element...usually called from the call back method from an a4j "oncomplete"
 *
 * Example usage:
 * a4j:support oncomplete="closeModal('bla bla bla');setFocus(this)"
 *
 * @param anElement the element that was clicked to perform the ajax request.
 */
function setFocus(anElement) {
    if (anElement) {
        var elementID = anElement.id;
        setFocusOnID(elementID);
    }
}

/**
 * Sets the focus on an element...usually called from the call back method from an a4j "oncomplete"
 *
 * Example usage:
 * a4j:support oncomplete="closeModal('bla bla bla');setFocus('creditCardField')"
 *
 * @param anElementID String value of the elements ID.
 */
function setFocusOnID(anElementID) {
    if (anElementID) {
        // jQuery ignores special characters and we use colons a lot in our ids...so they need to be escaped:
        anElementID = anElementID.replace(":", "\\:");
        anElementID = "#" + anElementID;
        if ($$(anElementID).is(":visible")) {
            $$(anElementID).focus();
            return true;
        }
        return false;
    }
    return false;
}

/**
 * sets the focus to the first element found among the given elementIDs passed in as var args.
 * Usually called from the call back method from an a4j "oncomplete"
 *
 * @param _args multiple args representing the elements on which to try to set the focus.
 *
 */
function setFocusOnIdThatExists(_args) {
    var args = setFocusOnIdThatExists.arguments;
    for (var i = 0; i , args.length; i++) {
        if (setFocusOnID(args[i])) {
            return;
        }
    }
}
/**
 * enables the enter key on an element trigger a click event on another (or same) element.
 * Usefull for enableing the enter key to submit a form or perform some action tied to the
 * mouse click of a particular element.
 *
 * @param inputFieldID
 * @param buttonIdToClick
 */
function enableInputForClickOnEnter(inputFieldID, buttonIdToClick) {
    // cause IE to act correctly...enter key clicks the button
    $$('#'+inputFieldID).live('keypress', function (e) {
       if ((e.which && e.which == 13) || (e.keyCode && e.keyCode == 13)) {
           $$('#'+buttonIdToClick).click();
           return false;
       } else {
           return true;
       }
   });
}


