//<![CDATA[

var DeliveryBrowserStrategyChain = function() {};
var deliveryBrowserStrategies = [];
var addBrowserStrategy = function (namespace, browserName) {
	if (namespace && namespace[browserName]) {
		deliveryBrowserStrategies.push(namespace[browserName]);
	}
};

addBrowserStrategy(VUE, 'BrowserLock');
addBrowserStrategy(ITS, 'iPad');
addBrowserStrategy(ITS, 'SecureBrowser');

DeliveryBrowserStrategyChain.prototype.isBrowserRunning = function() {
	for (var i=0;i<deliveryBrowserStrategies.length;i++) {
		try {
			if (deliveryBrowserStrategies[i].isSecureBrowser()) {
				return true;
			}
		}
		catch(err) {
		}
	}
	return false;
};
DeliveryBrowserStrategyChain.prototype.isPurchaseProhibited = function() {
	for (var i = 0; i < deliveryBrowserStrategies.length; i++) {
		try {
			if (deliveryBrowserStrategies[i].isSecureBrowser() && deliveryBrowserStrategies[i].isPurchaseProhibited()) {
				return true;
			}
		}
		catch(err) {
		}
	}
	return false;
};
DeliveryBrowserStrategyChain.prototype.disableSecurity = function() {
	for (var i=0;i<deliveryBrowserStrategies.length;i++) {
		try {
			if (deliveryBrowserStrategies[i].isSecureBrowser()) {
				deliveryBrowserStrategies[i].setSecurityEnabled(false);
			}
		}
		catch(err) {
		}
	}
};
DeliveryBrowserStrategyChain.prototype.enableSecurity = function() {
	for (var i=0;i<deliveryBrowserStrategies.length;i++) {
		try {
			if (deliveryBrowserStrategies[i].isSecureBrowser()) {
				deliveryBrowserStrategies[i].setSecurityEnabled(true);
			}
		}
		catch(err) {
		}
	}
};
DeliveryBrowserStrategyChain.prototype.closeWindow = function() {
	for (var i=0;i<deliveryBrowserStrategies.length;i++) {
		try {
			if (deliveryBrowserStrategies[i].isSecureBrowser()) {
				deliveryBrowserStrategies[i].closeWindow();
			}
		}
		catch(err) {
		}
	}
};
DeliveryBrowserStrategyChain.prototype.getAppName = function() {
	for (var i=0;i<deliveryBrowserStrategies.length;i++) {
		try {
			if (deliveryBrowserStrategies[i].isSecureBrowser()) {
				var appName = deliveryBrowserStrategies[i].getAppName();
				if (appName) {
					return appName;
				}
			}
		}
		catch(err) {
		}
	}
}
DeliveryBrowserStrategyChain.prototype.getAppVersion = function() {
	for (var i=0;i<deliveryBrowserStrategies.length;i++) {
		try {
			if (deliveryBrowserStrategies[i].isSecureBrowser()) {
				var appVersion = deliveryBrowserStrategies[i].getAppVersion();
				if (appVersion) {
					return appVersion;
				}
			}
		}
		catch(err) {
		}
	}
}



/* ]]> */