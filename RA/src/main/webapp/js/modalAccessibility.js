(function($$) {
	/**
	 * Extend ui.dialog widget for accessibility
	 * ---------------------------------------------
	 * On modal open:
	 * 1. Add aria-described by attribute to modals so that modal content is read when the modal opens
	 * 2. Set tabindex="0" for modal description. This will allow keyboard only users to scroll the modal content.
	 * 3. Set aria-hidden background element containers, so that they are not visible to the screen reader in the
	 *    virtual buffer.
	 *
	 * On modal close:
	 * 1. Remove aria-hidden from background containers.
	 * 2. Set focus back to the button/link that triggered modal open.
	 */
	$$.widget('ui.dialog', $$.ui.dialog, {
		open: function() {
			this.opener = $$(this.document[0].activeElement);
			this._super();
			this.element.attr('tabindex', 0);
			this.uiDialog.attr('aria-describedby', this.element.attr('id'));
			$$('#container, #copyright').attr('aria-hidden', true);
		},
		close: function() {
			$$('#container, #copyright').removeAttr('aria-hidden');
			this._super();
			// don't return focus for the progressDialog (ref D-20775)
			if (!(this.element.attr('id') === 'progressDialog') && !this.opener.filter(":focusable").focus().length) {
				// Hiding a focused element doesn't trigger blur in WebKit
				// so in case we have nothing to focus on, explicitly blur the active element
				// https://bugs.webkit.org/show_bug.cgi?id=47182
				$$(this.document[0].activeElement).blur();
			}
		}
	});
})(jQuery.noConflict());
