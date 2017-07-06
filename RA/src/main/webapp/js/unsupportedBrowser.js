(function (Modernizr) {

    var domainSplit = document.domain.split("."); //Finds the current domain of the webpage and then split using . as
                                                  // the delimiter
    var periodCount = domainSplit.length - 1; //Number of periods in the array is the number of words - 1
    var topleveldomain; //Root of the page
    var unsupportedBrowserCookieName; //Name of the cookie
    var unsupportedBrowserBodyClass = "unsupported-browser";
    var unSupportedBrowserPolicyId = "acceptUnSupportedBrowserPolicyId";
    var supportedFeatures = ["multiplebgs", "boxshadow", "mediaqueries", "borderradius", "hsla", "canvas", "eventlistener", "video", "fontface"];

    var deliveryBrowser = new DeliveryBrowserStrategyChain()

    function init() {
        initTopLevelDomain(); //Initializer function

        //checks if the browser supports the features and if the cookie has been set
        //we also check if Secure Browser is running because we don't want this notification displaying when using it
        if (!doesBrowserSupport(supportedFeatures) && isUnsupportedBrowserCookieNotSet() && !deliveryBrowser.isBrowserRunning()) {
            showUnsupportedBrowserNotification();
        }

        document.getElementById(unSupportedBrowserPolicyId).onclick = dismissUnsupportedBrowserNotification;
    }

    function initTopLevelDomain() {
        if (periodCount < 3) {
            topleveldomain = domainSplit.slice(periodCount - 1).join(".");
        } else {
            topleveldomain = domainSplit.slice(1).join(".");
        }
        unsupportedBrowserCookieName = "unsupportedbrowser:" + topleveldomain;
    }

    function showUnsupportedBrowserNotification() {
        classListWrapper.add(document.body, unsupportedBrowserBodyClass);
    }

    function hideUnsupportedBrowserNotification() {
        classListWrapper.remove(document.body, unsupportedBrowserBodyClass);
    }

    function dismissUnsupportedBrowserNotification() {
        hideUnsupportedBrowserNotification();
        var date = new Date();
        date.setDate(date.getDate() + 7);
        var expires = "expires=" + date.toGMTString() + "; ";
        document.cookie = unsupportedBrowserCookieName + "=" + "active; " + expires + " path=/; domain=" + topleveldomain;
    }

    function isUnsupportedBrowserCookieNotSet() {
        var ca = document.cookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i];
            c = c.trim();
            if (c.indexOf(unsupportedBrowserCookieName) == 0) {
                return false;
            }
        }
        return true;
    }

    function doesBrowserSupport(features) {
        for (i = 0; i < features.length; ++i) {
            if (!Modernizr[features[i]]) {
                return false;
            }
        }
        return true;
    }

    if (typeof String.prototype.trim !== 'function') {
        String.prototype.trim = function () {
            return this.replace(/^\s+|\s+$/g, '');
        }
    }
    var classListWrapper = (function () {
        if ("classList" in document.createElement("div")) {
            return {
                add: function (el, classToAdd) {
                    el.classList.add(classToAdd);
                },
                remove: function (el, classToAdd) {
                    el.classList.remove(classToAdd);
                }
            };
        } else {
            return {
                add: function (el, classToAdd) {
                    el.className += ' ' + classToAdd;
                },
                remove: function (el, classToAdd) {
                    el.className = (' ' + el.className + ' ').replace(' ' + classToAdd + ' ', ' ');
                }
                // etc.
            };
        }
    })();
    init();

})(Modernizr);