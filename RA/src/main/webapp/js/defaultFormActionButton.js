var $$ = jQuery.noConflict();

// this is the script the attempts to find each nextButton and make it the default action
// on the form by cloning it and putting it a the top of the form.
function enableDefaultButton() {
	$$('form').each(function () {
		var thisForm = $$(this);
		// select to look like this: "form#formID  div.forward > :submit" to get only
		// 	forward and previous buttons for this form:
		var forwardButtons = $$("form#" + $$(thisForm).attr('id') + " div.forward > :submit");
		var previousButtons = $$("form#" + $$(thisForm).attr('id') + " div.previous > :submit");
		var theDefaultButton = undefined;
		// if there is ONE previous and ONE forward button, then default the forward button:
		if (forwardButtons && forwardButtons.length === 1 && previousButtons && previousButtons.length === 1) {
			theDefaultButton = forwardButtons.first();
		}

		// inspired from: http://stackoverflow.com/questions/1963245/multiple-submit-buttons-on-html-form-designate-one-button-as-default
		if (typeof theDefaultButton !== 'undefined') {
			//var theDefaultButtonJQueryObj = $$(theDefaultButton);

			if (theDefaultButton && theDefaultButton.clone) {
				thisForm.prepend(theDefaultButton.clone()
					.css({position: 'absolute', left: '-999px', top: '-999px', height: 0, width: 0 })
					.attr("aria-hidden", "true")
					.attr("id", "defaultButton-" + $$(thisForm).attr('id'))
					.attr("tabindex", "-1")
				);
			}
		}
	});
}