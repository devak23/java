/**
 *
 * JS file used exclusively by the Edit Address Information composition component.
 * Its puppose is to provide 'onblur' event handling for the country drop down selection.
 * When the country selection is changed and focus is lost from the country drop down to
 * somewhere else on the page, the state, postal code and in some case country phone fields
 * and label are updated dependig on the country selected.
 * @author Fahim Ishtiaque
 * @version $Revision: #6 $ submitted $DateTime: 2011/06/30 12:00:56 $ by $Author: vIshtfa $
 * @since 2.3.1108
 */

var labelwarningStyleClass = "advisory_label";
var fieldwarningStyleClass = "advisory_field";
var $$ = jQuery.noConflict();
var countriesObj;
var shouldPrevalidate;
var i18nOptionalLabel;
var i18nSelectOneLabel;
var timer;
var triggeredComponentId;
var ajaxServletUrl;
var localeInSession;

// objects below are placeholders to support the cases when there are
// multiple address components on the same page.
var ctrDropDwnFieldArray = new Array();
var selectedCtrCodeArray = new Array();
var stateLabelElArray = new Array();
var stateFieldElArray = new Array();
var stateOptLabelElArray = new Array();
var postalLabelElArray = new Array();
var postalCodeElArray = new Array();
var postalOptLabelElArray = new Array();
var phoneCodeElArray = new Array();
var stateFieldRowElArray = new Array();
var stateErrorLabelIdArray = new Array();
var postalErrorLabelIdArray = new Array();
var currentFieldsOrderObjArray = new Array();

/**
 * Initializes the following the objects.
 * i) countriesObj : A object is constructed from the JSON String which contains all the countries along with
 *  				    each of their respective states and postal code related information.This is expected to be
 *						present on the page with the 'id' specified by 'ctryListId' parameter of this function.
 * ii) Elements - state label, state input field, state optional label, postal code label, postal code input field,
 *                postal code optional label.
 * ii) state field table row element - Table row element that represent the state related field and labels.This is needed
 *                                     to hide and unhide the entire row in case there are no states for the country selected.
 * @param componentId - The id of the 'EditAddressComponet.xhtml' component.
 * @param ctryListId - The id of <h:outputText> component that should be on the parent page of the component and that
 *                     <h:outputText> component contains the countries list in JSON format.
 * @param optLabel - The i18N version of '(Option One)' String.
 * @param selectOneLabel - The i18N version of 'Select one...' String.
 * @param prevalidate - a String - 'true' if page is prevalidating and highlighting the missing required fields and labels upon first time
 *  				    page load.
 *
 */
function initialize(componentId, ctryListId, optLabel, selectOneLabel,prevalidate){
// TODO clean up optLabel, or leave it in incase we ever go back to that.
	if(countriesObj == null){
	  var countryListElement = document.getElementById(ctryListId);
	  // If there are more than one component on a page then
	  // this would be already initialized by the first component.
	  var countriesJSON = countryListElement.innerHTML;
	  countriesObj = eval('(' + countriesJSON + ')');
	}

	if(i18nSelectOneLabel == null){
		i18nSelectOneLabel = selectOneLabel;
	}

	if(shouldPrevalidate == null){
		shouldPrevalidate = prevalidate;
	}

	var ctrDropDwnId = componentId + '_editAddressGridCOUNTRY';
	var stateLabelId = componentId + '_editAddressGridSTATElabel';
	var stateFieldId = componentId + '_editAddressGridSTATE';
	var postalLabelId = componentId + '_editAddressGridPOSTAL_CODElabel';
	var postalFieldId = componentId + '_editAddressGridPOSTAL_CODE';
	var phoneCodeId = componentId + '_editAddressGridTELEPHONE_COUNTRY_CODE';
	var ctrDropDwnEl = document.getElementById(ctrDropDwnId);

	ctrDropDwnFieldArray[componentId] = ctrDropDwnEl;
	stateLabelElArray[componentId] = document.getElementById(stateLabelId);
	stateErrorLabelIdArray[componentId] = componentId + '_editAddressGridSTATEmessage';
	postalErrorLabelIdArray[componentId]	= componentId + '_editAddressGridPOSTAL_CODEmessage';
	var stateFieldEl = document.getElementById(stateFieldId);
	stateFieldElArray[componentId] = stateFieldEl;


	postalLabelElArray[componentId] = document.getElementById(postalLabelId);

	var postalCodeEl = document.getElementById(postalFieldId);
	postalCodeElArray[componentId] = postalCodeEl;
    selectedCtrCodeArray[componentId] = ctrDropDwnEl.value;

	phoneCodeElArray[componentId] = document.getElementById(phoneCodeId);
    var currentCountryVal = ctrDropDwnEl.value;
	var stateLabelEl = stateLabelElArray[componentId];
	stateFieldRowElArray[componentId] = stateLabelEl.parentNode.parentNode;
	var currCtryObj = getCurrCountryObj(componentId);
	var hasStates = hasMultipleStates(currCtryObj);
    //log.debug("hasStates " + hasStates);
	if (!hasStates){
		hideStateRow(componentId);
	}



    var ajaxUrlCompId = 'ajaxServletUrl_' + componentId;
    var ajaxUrlComp = document.getElementById(ajaxUrlCompId);
    ajaxServletUrl = ajaxUrlComp.innerHTML;

    var localeCompId = 'locale_' + componentId;
    var localeComp = document.getElementById(localeCompId);
    localeInSession = localeComp.innerHTML;

    var currentCtryCode = selectedCtrCodeArray[componentId];
    var ajaxUrlWithParams = ajaxServletUrl + '&countryCode=' + currentCtryCode + '&localeInSession=' + localeInSession;

    reorderTableRows(ajaxUrlWithParams,componentId,false);


}

function initializeReadOnly(componentId){

    //log.debug("componentId : " + componentId);

    var currentCountryCode = '';

    var ctrCodeCompId = 'countryCode_' + componentId;
    var ctrCodeComp = document.getElementById(ctrCodeCompId);

    //log.debug("ctrCodeComp : " + ctrCodeComp);

    if(ctrCodeComp != null && ctrCodeComp != undefined ){
        currentCountryCode = ctrCodeComp.innerHTML;
    }

    //log.debug("currentCountryCode : " + currentCountryCode);

    if(currentCountryCode != ''){

        var jsonCompId = 'labelOrdersMap_' + componentId;
        var jsonComp = document.getElementById(jsonCompId);

        if(jsonComp != null && jsonComp != undefined){

            var jsonData = jsonComp.innerHTML;
            var currentFieldsOrder = $$.parseJSON(jsonData);

            //log.debug("jsonData : " + currentFieldsOrder);

            var noOfFields = currentFieldsOrder.fieldOrders.length;

            if(noOfFields == 0){
                return false;
            }

            var newOrder = new Array();
            var newLabelValues = new Array();

            for(var i = 0; i < noOfFields; i++){
                var fieldType  = currentFieldsOrder.fieldOrders[i].labelIdKey;
                newOrder[i] = fieldType;
                newLabelValues[fieldType] = currentFieldsOrder.fieldOrders[i].labelValue;
            }
        }
        updateFieldLabels(componentId,newOrder,newLabelValues,true);

    }

}



function reorderTableRows(ajaxUrlWithParams, componentId, readOnly){

    //log.debug("ajaxUrlWithParams:  " + ajaxUrlWithParams);

    if (jQuery.fn.jquery != JQUERY_BASE_VERSION()) {
        $$.ajax({
            url:ajaxUrlWithParams,
            success:function(jsonData, textStatus, jqXHR){
                // On page load we only want to have this initialized once, if there are
                // more than one address component on the page.
                currentFieldsOrderObjArray[componentId] = jsonData; //plainObject is jsonData
                updateFieldsOrder(componentId,readOnly);
            },
            async:false, cache:false
            });
    } else {
        $$.ajax({
            url:ajaxUrlWithParams,
            success:function(jsonData, textStatus, jqXHR){
                // On page load we only want to have this initialized once, if there are
                // more than one address component on the page.
                currentFieldsOrderObjArray[componentId] = eval('(' + jsonData + ')');
                updateFieldsOrder(componentId,readOnly);
            },
            async:false, cache:false
        });
    }
}



function updateFieldsOrder(componentId,readOnly){

    var currentFieldsOrder = currentFieldsOrderObjArray[componentId];
    var noOfFields = currentFieldsOrder.fieldOrders.length;

    if(noOfFields == 0){
        return false;
    }

    var newOrder = new Array();
    var newLabelValues = new Array();

    for(var i = 0; i < noOfFields; i++){
        var fieldType  = currentFieldsOrder.fieldOrders[i].labelIdKey;
        newOrder[i] = fieldType;
        newLabelValues[fieldType] = currentFieldsOrder.fieldOrders[i].labelValue;
    }

    var tableId = componentId + '_editAddressGrid';
    var tableElem = document.getElementById(tableId);
    //log.debug("tableId : " + tableId);
    var totalRows = tableElem.childNodes[0].childNodes.length;

    var tableRowArrayObj = {};
    var existingTableRowOrder = new Array();

    var m = 0;

    for(var j = 0; j < totalRows; j++){

        var nodeName = tableElem.childNodes[0].childNodes[j].nodeName;

        if(nodeName == 'TR'){
            var rowElem = tableElem.childNodes[0].childNodes[j];
            var labelId = rowElem.childNodes[0].childNodes[0].id;
            var index = tableId.length;
            var subStrLabelId = labelId.substring(index);
            var subStrLabelIdLength = subStrLabelId.length;
            var firstIndex = subStrLabelIdLength - 5;
            var labelIdKeyInTable = subStrLabelId.substring(0,firstIndex);
            tableRowArrayObj[labelIdKeyInTable] = rowElem;
            existingTableRowOrder[m] = labelIdKeyInTable;
            m++;
        }
    }



    //log.debug("origLabelIdArray : " + existingTableRowOrder);
    //log.debug("newOrder : " + newOrder);

    var updatedTableRowOrder= getUpdatedTableRowOrder(existingTableRowOrder,newOrder);

    //log.debug("updatedTableRowOrder : " + updatedTableRowOrder);

    reshuffleTableRows(componentId,updatedTableRowOrder,tableRowArrayObj);
    updateFieldLabels(componentId, newOrder,newLabelValues,readOnly);
}

function updateFieldLabels(componentId, fieldTypes, labelValues,readOnly){

    var idPrefix =  componentId + '_editAddressGrid';

   $$(fieldTypes).each(function(n, value){

        var label = labelValues[value];
        var spanId = idPrefix + value + 'labelText';

        //log.debug("value : " + value + "  and label : " + label);

        var spanElem = document.getElementById(spanId);

        if(spanElem != null && spanElem != undefined){
            var isRequired = false;

            if(!readOnly){
                var spanElemTxt = spanElem.innerHTML;
                if(spanElemTxt.indexOf('*') >= 0){
                    isRequired = true;
                }
            }
           //log.debug("isRequired : " + isRequired);

           if(!readOnly && isRequired){
               spanElem.innerHTML = requiredIndicator + label + ':';
           } else {
               spanElem.innerHTML = label + ':';
           }
       }
   });

}


function getUpdatedTableRowOrder(exisitngTableRowOder, newOrder){

    var newLength = newOrder.length;
    var tableLength = exisitngTableRowOder.length;

    for(var i = 0 ; i < newLength ; i++){

        var val = newOrder[i];
        var index = exisitngTableRowOder.indexOf(val);
        var m = 0;

        for(var j = 0; j < tableLength; j++){

            var tableVal = exisitngTableRowOder[j];

            var indexInFormat1 = newOrder.indexOf(tableVal);

            if(indexInFormat1 == -1){
                continue;
            }

            if(tableVal == val){
                break;
            }

            if(indexInFormat1 > i){
                var indexOfVal = exisitngTableRowOder.indexOf(val);
                exisitngTableRowOder.splice(j,0,val);
                if(indexOfVal != -1){
                    var removeIndex = indexOfVal+1;
                    exisitngTableRowOder.splice(removeIndex,1);
                }
                break;
            }
        }
    }

    return exisitngTableRowOder;
}

if (!Array.prototype.indexOf) {
    Array.prototype.indexOf = function(obj, start) {
         for (var i = (start || 0), j = this.length; i < j; i++) {
             if (this[i] === obj) { return i; }
         }
         return -1;
    }

}


function reshuffleTableRows(componentId,updatedTableRowOrder,tableRowArrayObj ){

    var tableId = componentId + '_editAddressGrid';
    var tableElem = document.getElementById(tableId);
    var totalRows = updatedTableRowOrder.length;
    var rowArray = {};

    $$('#' + tableId).each(function(n){
        rowArray[n] = $$('tr',this);
        $$('tr',this).remove();
    });

    var rowArrJqObj = $$.makeArray(rowArray);

    var tableObj = $$('#' + tableId);

    $$(updatedTableRowOrder).each(function(n, value){
        //log.debug("Value = " + value);
        var rowObj = tableRowArrayObj[value];
        //log.debug("row Obj : " + rowObj);
        var tableRow = $$(rowObj);
        tableObj.append(tableRow);
    });

}

/**
 * Sets a time delay before calling update() method so as to prevent race condition of onblur and onclick at the same time.
 * @param componentId - The id of the 'EditAddressComponet.xhtml' component.
 */
function updateCountryDependentElements(componentId){

	triggeredComponentId = componentId;
	// needs this 250 millisecond delay so that when the user after changing the country
	// clicks the 'Next' or 'Continue' button before clicking anywhere else,
	// the form submits. Otherwise the form submission does not happen
	// after the button click.
	timer = window.setTimeout("update()", 250);
}


/**
 * Updates the elements like state, postal code and phone code (if necessary) that are dependent on the country field value.
 * Also hide or hide or unhide the state field table row depending on the country selected.
 */
function update(){

    var componentId = triggeredComponentId;
	var countryEl = ctrDropDwnFieldArray[triggeredComponentId];
	var currentVal = countryEl.value;
	var oldValue = selectedCtrCodeArray[componentId];

	// The function below needs to be called so that onblur we loose focus on the entire row of the country drop down.
	// This function is defined in the WebContent\templates\private\testtaker\Template.xhtml file.
	doFocusMultiple(countryEl, 0);

	if (currentVal != oldValue){
        selectedCtrCodeArray[componentId] = currentVal;
        if (currentVal == ""){
			hideStateRow(componentId);
		} else {
            var countryList = countriesObj.countries;
			var currCtry = getCurrCountryObj(componentId);
			var stateReq = isStateRequired(currCtry,componentId);
			var postalReq = isPostalRequired(currCtry,componentId);
			var currPhoneCode = currCtry.phCode;
            var hasStates = hasMultipleStates(currCtry);
            var stateLabelEl = stateLabelElArray[componentId];
			var stateFieldEl = stateFieldElArray[componentId];
			var phoneCodeEl = phoneCodeElArray[componentId];
            var stateCode = stateFieldEl.value;
			if (phoneCodeEl.value == ""){
				phoneCodeEl.value = currPhoneCode;
			}

			if (hasStates){

               	showStateRow(componentId);
				populateStates(currCtry, componentId);
				removeFieldLevelErrorMessage(componentId);
                clearFieldStyles(stateLabelEl,stateFieldEl);
				if (stateReq == "true"){
                    stateLabelEl.innerHTML = addRequiredIndicator(stateLabelEl);
                    if (shouldPrevalidate == "true") {
                     	$$(stateLabelEl).addClass("advisory_label");
						$$(stateFieldEl).addClass("advisory_field");
                    }
				} else {
                    stateLabelEl.innerHTML = removeRequiredIndicator(stateLabelEl);
				}
			} else {
				stateFieldEl.value = "";
				hideStateRow(componentId);
			}

            var postalLabelEl = postalLabelElArray[componentId];
			var postalCodeEl = postalCodeElArray[componentId];
			var postalCode = postalCodeEl.value;
            if (postalReq  == "true") {
                postalLabelEl.innerHTML = addRequiredIndicator(postalLabelEl);
            } else {
                postalLabelEl.innerHTML = removeRequiredIndicator(postalLabelEl);
            }
            clearFieldStyles(postalLabelEl,postalCodeEl);
			if (postalCode == "" && postalReq  == "true" && shouldPrevalidate == "true"){
				$$(postalLabelEl).addClass("advisory_label");
				$$(postalCodeEl).addClass("advisory_field");
			}
		}

        var ajaxUrlWithParams = ajaxServletUrl + '&countryCode=' + currentVal + '&localeInSession=' + localeInSession;
        reorderTableRows(ajaxUrlWithParams,componentId);
        // reset the focus on country row.
        countryEl.focus();
        doFocusMultiple(countryEl, 1);
	}

}

function clearFieldStyles(labelEl,fieldEl) {
    $$(labelEl).removeClass("advisory_label");
    $$(labelEl).removeClass("warning_label");
    $$(fieldEl).removeClass("advisory_field");
    $$(fieldEl).removeClass("error");
    labelEl.style.cssText = "";
    fieldEl.style.cssText = "";
}

// Note: Any change to this requiredIndicator must be reflected in
 // <------ Please sync with WebResourceUtil.requiredIndicatorSpan
var requiredIndicator = '<span class="required">*</span>';
var browserName  = navigator.appName;
// The required indicator span was rendiring as '<SPAN class=required>*</SPAN>' on IE Browsers
if (browserName == 'Microsoft Internet Explorer') {
    var requiredIndicator = '<SPAN class=required>*</SPAN>';
}
// todo: Possibly send in the required indicator on init?
function addRequiredIndicator(theComponentLabel) {

    if (requiredIndicatorExists(theComponentLabel)) {
        return theComponentLabel.innerHTML;
    } else {
        return requiredIndicator + theComponentLabel.innerHTML;
    }
}

function removeRequiredIndicator(theComponentLabel) {
    if (requiredIndicatorExists(theComponentLabel)) {
        return theComponentLabel.innerHTML.replace(requiredIndicator, "");
    } else {
        return theComponentLabel.innerHTML;
    }
}

function requiredIndicatorExists(theComponentLabel) {
    return theComponentLabel.innerHTML.indexOf(requiredIndicator) >= 0;
}


/**
 * If necessary removes any previous field level validation error messages arising from
 * state and postal code field.
 * @param componentId - The id of the 'EditAddressComponet.xhtml' component.
 */
function removeFieldLevelErrorMessage(componentId){

	var stErrorMsgId= stateErrorLabelIdArray[componentId];
	var stateErrorLabel = document.getElementById(stErrorMsgId);

	var postalErrorMsgId= postalErrorLabelIdArray[componentId];
	var postalErrorLabel = document.getElementById(postalErrorMsgId);

	if (stateErrorLabel != undefined && stateErrorLabel != null){
		stateErrorLabel.innerHTML = "";
	}
	if (postalErrorLabel != undefined && postalErrorLabel != null){
		postalErrorLabel.innerHTML = "";
	}
}


/**
 * Depending the currently selected country in the country drop down list, it retrieves the specific country
 * object from the list of 'countries' objects. The country object holds the following information:
 * country name, country code, state requiredness, postal code requiredness, state lists with the state names and state codes.
 * @param componentId - The id of the 'EditAddressComponet.xhtml' component.
 * @return currCtryObj - A specific country object containing all of its states information.
 */
function getCurrCountryObj(componentId){

	var currCtryObj;
	var countryList = countriesObj.countries;
	var currentCtryCode = selectedCtrCodeArray[componentId];
	if(currentCtryCode == ""){
		return null;
	}

	for(var i = 0; i < countryList.length; i++) {
		var currCtrCode = countriesObj.countries[i].ctrCode;
		if(currCtrCode == currentCtryCode){
			currCtryObj = countriesObj.countries[i];
			break;
		}
	}
	return currCtryObj;
}


/**
 * Populates the state drop down list given the specific country object.
 * @param currCtryObj - A specific country object containing all of its states information.
 * @param componentId - The id of the 'EditAddressComponet.xhtml' component.
 */
function populateStates(currCtryObj,componentId){

	var states = currCtryObj.states;
	var stateFieldEl = stateFieldElArray[componentId];
	//clear all existing values in the drop down in case the user switches between
	//two countries that both have states.
	stateFieldEl.innerHTML = "";
	var firstOptEl = document.createElement("option")
	firstOptEl.setAttribute("value","");
	firstOptEl.innerHTML = i18nSelectOneLabel;
	stateFieldEl.appendChild(firstOptEl);
	for(var j = 0; j < states.length; j++ ){
	  var optEl = document.createElement("option")
	  var val = states[j].stCode;
	  var name = states[j].stName;
	  optEl.setAttribute("value",val);
	  optEl.innerHTML = name;
	  stateFieldEl.appendChild(optEl);
	}
}

/**
 * Hides the table row that represents the state label, state drop down field and state optional label.
 * @param componentId - The id of the 'EditAddressComponet.xhtml' component.
 */
function hideStateRow(componentId){
	var stateFieldRowEl = stateFieldRowElArray[componentId];
	stateFieldRowEl.style.display = "none";
}


/**
 * Unhide the table row that represents the state label, state drop down field and state optional label.
 * @param componentId - The id of the 'EditAddressComponet.xhtml' component.
 */
function showStateRow(componentId){

	var stateFieldRowEl = stateFieldRowElArray[componentId];
	stateFieldRowEl.style.display = "";
}


/**
 * Determine if the state is required given the specific country object.
 * @param currCtryObj - A specific country object containing all of its states information.
 * @param componentId - The id of the 'EditAddressComponet.xhtml' component.
 * @return String - 'true' if state is requried, false otherwise.
 */
function isStateRequired(currCtryObj,componentId){
	var currCtry = currCtryObj == null ? getCurrCountryObj(componentId) : currCtryObj;
	var stateReq = currCtry == null ? "false" : currCtry.stReq;
	return stateReq;
}

/**
 * Determine if the postal code is required given the specific country object.
 * @param currCtryObj - A specific country object containing all of its states information.
 * @param componentId - The id of the 'EditAddressComponet.xhtml' component.
 * @return String - 'true' if state is requried, false otherwise.
 */
function isPostalRequired(currCtryObj,componentId){

    var currCtry = currCtryObj == null ? getCurrCountryObj(componentId) : currCtryObj;
    var postalReq = currCtry == null ? "false" : currCtry.zipReq;
    return postalReq;
}


/**
 * Determine if a specific country has multiple states defined given the specific country object.
 * @param currCtryObj - A specific country object containing all of its states information.
 * @param componentId - The id of the 'EditAddressComponet.xhtml' component.
 * @return String - 'true' if a specific country has multiple states defined, false otherwise.
 */
function hasMultipleStates(currCtryObj){

	if (currCtryObj == null){
		return false;
	}
	var states = currCtryObj.states;
   return states.length > 0 ;
}

