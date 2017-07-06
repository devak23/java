

function VueAnalyticsFacade() {
    return this;
}

VueAnalyticsFacade.prototype = {
	$: jQuery.noConflict(),

	analyticsToPost: {},

	populateAnalytics: function(vendorToValueMap) {
        var that = this;
        var getType = {};
        this.$.each(vendorToValueMap, function(key, value) {
            if (value && getType.toString.call(value) === '[object Function]') {
                // the value is a function so we use the return value
                that.analyticsToPost[key] = value();
            }/* else if (this[value] && getType.toString.call(this[value]) === '[object Function]') {
                // the value is a VueAnalyticsFacade method so we use the return value
                that.analyticsToPost[key] = that[value]();
            }*/ else {
                // the value is just a string, so use the string
                that.analyticsToPost[key] = value;
            }
        });
    },

    populateCustomFields: function(customFieldPrefix, vueToVendorMap) {
        var that = this;
        //loop through all meta tags that start with the custom prefix
        this.$('meta[name^=' + customFieldPrefix +']').each(function(index, element) {
            var attrName = that.$(element).attr('name');
            //remove the custom prefix before posting
            attrName = attrName.replace(customFieldPrefix, '');
            var firstChar = attrName.charAt(0);
            if (firstChar === '.' || firstChar === '-' || firstChar === '_') {
                attrName = attrName.substring(1, attrName.length);
            }

            if (vueToVendorMap && vueToVendorMap[attrName]) {
                attrName = vueToVendorMap[attrName];
            }

            if (that.$(element).attr('content')== "" || that.$(element).attr('content')==undefined) {
            }
            else {
            that.analyticsToPost[attrName] = that.$(element).attr('content');
            }

        });
    },

    post: function(url) {
            var xmlhttp;
        if (window.XDomainRequest)
        {
            xmlhttp = new XDomainRequest();
            xmlhttp.open("POST",url + '?' + this.$.param(this.analyticsToPost),true);
            xmlhttp.onprogress = function(){ };
            xmlhttp.ontimeout = function(){ };
            xmlhttp.onerror = function () { };
            setTimeout(function(){
                xmlhttp.send();
            }, 0);
        }
        else {
            this.$.post(url, this.analyticsToPost);
        }



    },

    documentLocationField: function() {
    hName=window.location.hostname;
    pName=window.location.pathname;
    var es=hName+pName;
    return es;
    },

    documentTitleField: function() {
    return document.title;
    },

    documentPathField: function() {
    return window.location.href;
    },

    screenResolutionField: function() {
    if (typeof(screen)=="object"){
        var sr=screen.width+"x"+screen.height;
    }
    return sr;
    },

    flashVersionField: function() {
    var fv=(function(){
        var i,flash;
        if (window.ActiveXObject){
            for(i=15;i>0;i--){
                try{
                    flash=new ActiveXObject("ShockwaveFlash.ShockwaveFlash."+i);
                    return i+".0";
                }
                catch(e){
                }
            }
        }
        else if (navigator.plugins&&navigator.plugins.length){
            for (i=0;i<navigator.plugins.length;i++){
                if (navigator.plugins[i].name.indexOf('Shockwave Flash')!=-1){
                    return navigator.plugins[i].description.split(" ")[2];
                }
            }
        }
        return "Not enabled";
    })();
    return fv;

    },

    userLanguageField: function() {
    return navigator.appName=="Netscape"?navigator.language:navigator.userLanguage;
    },

    dontPostParams: function()  {

    },

    colorDepthField: function() {
    if (typeof(screen)=="object"){
        var bit=navigator.appName=="Netscape"?screen.pixelDepth:screen.colorDepth;
        var cd = bit + "-bit";
    }
    return cd;
    },

    javaEnabledField: function() {
    if (typeof(navigator.javaEnabled())=="boolean"){
        var je=navigator.javaEnabled()?"1":"0";

    }
    return je;
    },

    browserSizeField: function() {
    if (document.all){
        var bs=document.body?document.body.offsetWidth+"x"+document.body.offsetHeight:"unknown";
    }
    else{
        bs=window.innerWidth+"x"+window.innerHeight;
    }     return bs;
    },

    documentEncodingField: function() {
    return "UTF-8";
    },

    getCustomerId: function(){
    var gaFacade = new GoogleAnalyticsFacade();
    var customerCookie = gaFacade.getCookie("_ga");
    if (customerCookie==null) {
        //return null;
    }
    else {
        var split = customerCookie.split(";");
        var cid = split[0];
        var sub = cid.substring(6);
        return sub;
    }
    },

    getCampaignTracker:function() {
        var $ = jQuery.noConflict();
        return $('meta[name="WT.mc_id"]').attr('content');
    }

}
