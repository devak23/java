/* override the generation of all modal dialogs, allowing us to dynamically
    resize them to fit 90% of the available real estate if the screen is
    smaller than the specified dimensions */
(function($) {
    var open = $.ui.dialog.prototype.open;

    $.ui.dialog.prototype.open = function() {
        this.options.width = getModalWidth(this.options.width);
        this.options.height = getModalHeight(this.options.height);

        open.apply(this, arguments);
    };
})(jQuery);

/* determine the appropriate width/height if we cannot accomodate one
    or more of the specified dimensions */
function getModalWidth(defaultWidth) {
    var dev = new DeviceFactory().getInstance(new DeviceDetector().getDeviceWidth());
    var widthStrategy = new ModalWidthStrategy().getInstance(dev);
    return widthStrategy(defaultWidth);
}

function getModalHeight(defaultHeight) {
    var dev = new DeviceFactory().getInstance(new DeviceDetector().getDeviceWidth());
    var heightStrategy = new ModalHeightStrategy().getInstance(dev);
    return heightStrategy(defaultHeight);
}

var DEVICE = {
    PHONE_PORTRAIT : {code: "PHONE_PORTRAIT", minWidth: 0, maxWidth: 480, name: "Phone (Portrait)"},
    PHONE_LANDSCAPE : {code: "PHONE_LANDSCAPE", minWidth: 481, maxWidth: 800, name: "Phone (Landscape)"},
    DESKTOP : {code: "DESKTOP", minWidth: 721, maxWidth: 9999, name: "Desktop"}
};

var DeviceFactory = function() {
    return {
        getInstance: function(deviceWidth) {
            for (var d in DEVICE) {
                var device = DEVICE[d];
                if (device.minWidth <= deviceWidth && device.maxWidth >= deviceWidth) {
                    return device;
                    break;
                }
            }
            return DEVICE.DESKTOP;
        }
    };
};

var DeviceDetector = function() {
    return {
        getDeviceWidth: function() {
            var $$ = jQuery.noConflict();
            return $$('body').innerWidth();
        },
        getDeviceHeight: function() {
            var $$ = jQuery.noConflict();
            //return $$('body').innerHeight();
            return $$(window).height();
        }
    };
};

var ModalWidthStrategy = function() {
    return {
        getInstance: function(device) {
            var responsiveModalWidth = new DeviceDetector().getDeviceWidth() * 0.9;
            return function(defaultWidth) {
                return (responsiveModalWidth < defaultWidth) ? responsiveModalWidth : defaultWidth;
            }
            /*if ($$.inArray(device, new Array(DEVICE.PHONE_PORTRAIT, DEVICE.PHONE_LANDSCAPE)) > -1) {
                return function(defaultWidth) {
                    return new DeviceDetector().getDeviceWidth() * 0.9;
                }
            } else {
                return function(defaultWidth) {
                    return defaultWidth;
                }
            } */
        }
    }
};

var ModalHeightStrategy = function() {
    return {
        getInstance: function(device) {
            var responsiveModalHeight = new DeviceDetector().getDeviceHeight() * 0.9;
            return function(defaultHeight) {
                return (responsiveModalHeight < defaultHeight) ? responsiveModalHeight : defaultHeight;
            }
            /*return function(defaultHeight) {
                var height = new DeviceDetector().getDeviceHeight();
                if (defaultHeight > height) {
                    return (height * 0.9);
                } else {
                    return defaultHeight;
                }
            } */
        }
    }
};