/**
 * A Patch to resolve the issues in standard mode for IE 9 and above.
 *
 */

var $$ = jQuery.noConflict();

	$$(document).ready(function() {
        //https://community.jboss.org/message/638508#638508
        try {
			if (jQuery.browser.msie && jQuery.browser.version >= 9) {
					window.XMLSerializer = function () {
			};
			window.XMLSerializer.prototype.serializeToString = function (oNode) {
				return oNode.xml;
			};
		}
		} catch (exception) {
		}

		//http://salesforce.stackexchange.com/questions/7917/visualforce-major-grief-with-ie9-ajax-refresh-rerender-issues
		try {
			if (jQuery.browser.msie && jQuery.browser.version >= 9) {
				A4J.AJAX.XMLHttpRequest.prototype._copyAttribute = function (src, dst, attr) {
					var value = src.getAttribute(attr);
					if (value) {
						try {
							dst.setAttribute(attr, value);
						} catch (err) {
						}
					}
				};
			}
		} catch (exception) { 
		}
	});
				