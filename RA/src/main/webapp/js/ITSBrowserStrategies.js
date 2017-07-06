var ITSBrowserStrategy = function() {};

ITSBrowserStrategy.prototype.isSecureBrowser = function() {
	try {
		return window.external.IsHybridSecureBrowser || window.external.IsSecureBrowser;
	} catch (e) {}
	return false;
};

ITSBrowserStrategy.prototype.setSecurityEnabled = function(enabled) {
	if (enabled) {
		try {
			window.external.EnableSecurity();
		} catch (e) {}
	} else {
		try {
			window.external.DisableSecurity();
		} catch (e) {}
	}
};

ITSBrowserStrategy.prototype.closeWindow = function() {
	try {
		window.external.CloseBrowser();
	} catch (e) {}
};

ITSBrowserStrategy.prototype.getAppName = function() {
	return "ITS Secure Browser";
};

if (!ITS) {
	var ITS = {};
}


ITS.SecureBrowser = new ITSBrowserStrategy();

var ITSiPadAppStrategy = function() {};

ITSiPadAppStrategy.prototype.isSecureBrowser = function() {
	try {
		return (navigator.userAgent.toLowerCase().match("itsmobile"));
	} catch (e) {}
	return false;
};

ITSiPadAppStrategy.prototype.setSecurityEnabled = function(enabled) {};

ITSiPadAppStrategy.prototype.closeWindow = function() {
	try {
		var itsiPadApp;
		if (typeof (window.plugins) != "undefined" && typeof (window.plugins.itsUI) != "undefined") {
			itsiPadApp = window.plugins.itsUI;
		}
		if (itsiPadApp != null) {
			try {
				itsiPadApp.closeBrowser();
			}
			catch (e) {}
		}
	} catch (e) {}
};

ITSiPadAppStrategy.prototype.getAppName = function() {
	return "ITS iPad Secure Browser";
};


ITSiPadAppStrategy.prototype.isPurchaseProhibited = function() {
	return true;
};

ITS.iPad = new ITSiPadAppStrategy();