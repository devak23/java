var DefaultStrategy = function() {};
DefaultStrategy.prototype.isSecureBrowser = function() {
	return false;
};

DefaultStrategy.prototype.closeWindow = function() {
	//do nothing
};

DefaultStrategy.prototype.getAppName = function() {
	//returns undefined
};

DefaultStrategy.prototype.getAppVersion = function() {
	//returns undefined
};

DefaultStrategy.prototype.isPurchaseProhibited = function() {
	return false;
};

DefaultStrategy.prototype.setSecurityEnabled = function(enabled) {
	//do nothing
};

if (!VUE) {
	var VUE = {};
}
if (!client) {
  var client = undefined;
}
if (!client) {
  VUE.BrowserLock = new DefaultStrategy();
} else {
  var ABEBrowserStrategy = function() {};
	ABEBrowserStrategy.prototype.isSecureBrowser = function() {
		if (client.isSecureBrowser) {
			return client.isSecureBrowser();
		} else {
			//for backward compatibility
			return client.isSecureRenderingEngine === true;
		}
	};

	ABEBrowserStrategy.prototype.closeWindow = client.closeWindow;
	ABEBrowserStrategy.prototype.getAppName = client.getAppName;
	ABEBrowserStrategy.prototype.getAppVersion = client.getAppVersion;
	ABEBrowserStrategy.prototype.isPurchaseProhibited = client.isPurchaseProhibited;
	ABEBrowserStrategy.prototype.setSecurityEnabled = client.setSecurityEnabled;

	VUE.BrowserLock = new ABEBrowserStrategy();
}