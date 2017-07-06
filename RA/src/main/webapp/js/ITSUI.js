function ITSUI() {
    // Add properties here
}

ITSUI.prototype.showButtonBar = function (options) {
    if (!options) options = {};
    cordova.exec("ITSUI.showButtonBar", options);
};

ITSUI.prototype.hideButtonBar = function (animate) {
    if (animate == undefined || animate == null)
        animate = true;
    cordova.exec("ITSUI.hideButtonBar", { animate: animate });
};

ITSUI.prototype.updateButton = function (name, options) {
    if (!options) options = {};
    cordova.exec("ITSUI.updateButton", name, options);
};

ITSUI.prototype.showButtons = function () {
    var parameters = ["ITSUI.showButtons"];
    for (var i = 0; i < arguments.length; i++) {
        parameters.push(arguments[i]);
    }
    cordova.exec.apply(this, parameters);
};

ITSUI.prototype.getSelectedButton = function () {
    return this.selectedButton;
};

ITSUI.prototype.selectButton = function (tab) {
    cordova.exec("ITSUI.selectButton", tab);
};


ITSUI.prototype.showInfoPanel = function (options) {
    if (!options) options = {};
    cordova.exec("ITSUI.showInfoPanel", options);
};

ITSUI.prototype.hideInfoPanel = function (options) {
    if (!options) options = {};
    cordova.exec("ITSUI.hideInfoPanel", options);
};

ITSUI.prototype.updateLabel = function (name, options) {
    if (!options) options = {};
    cordova.exec("ITSUI.updateLabel", name, options);
};

ITSUI.prototype.showLabel = function (name) {
    var options = {};
    cordova.exec("ITSUI.showLabel", name, options);
};

ITSUI.prototype.hideLabel = function (name) {
    var options = {};
    cordova.exec("ITSUI.hideLabel", name);
};

ITSUI.prototype.closeBrowser = function (options) {
    if (!options) options = {};
    cordova.exec("ITSUI.closeBrowser", options);
};

if (!window.plugins)
    window.plugins = {};

window.plugins.itsUI = new ITSUI();

