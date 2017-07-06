
 function GoogleAnalyticsFacade() {
     return this;
}

 GoogleAnalyticsFacade.prototype = {
     //post: function(account,customMetaPrefix,campaignMetaPrefix,displayFeature) {
     post: function(account,customMetaPrefix,displayFeature) {
        var gaFacade = new GoogleAnalyticsFacade();
        var gaCookie = gaFacade.getCookie("_ga");
        if (gaCookie == null) {
            gaFacade.generateCookie();
        }
         var myCookie = gaFacade.getCookie("optOut");
         var gatCookie = gaFacade.getCookie("_gat");
         var uDc = gaFacade.genAlpha(displayFeature);
         var jidDc = gaFacade.genID(displayFeature);

        // check to do cookie doesn't exist stuff;
        if (myCookie == null) {
        var analytics = new VueAnalyticsFacade();
		analytics.populateAnalytics({
			v: '1',
            _s: '1',
            _v:'j31',
			tid: account,
			t: 'pageview',
			cid: analytics.getCustomerId,
			dt:  analytics.documentTitleField,
            ul:  analytics.userLanguageField,
            je:  analytics.javaEnabledField,
            sd:  analytics.colorDepthField,
            sr:  analytics.screenResolutionField,
            fl:  analytics.flashVersionField,
            vp:  analytics.browserSizeField,
            dl : analytics.documentPathField,
            de : analytics.documentEncodingField,
            _u : uDc,
            jid : jidDc,
            cs: analytics.getCampaignTracker,
            cm: "Web",
            cn: analytics.getCampaignTracker
        });

            if (customMetaPrefix != null){
                analytics.populateCustomFields(customMetaPrefix,{
                    "appUserId": "cd1",
                    "appUserLocale": "cd2",
                    "CLIENT_CODE": "cd3",
                    "VUE_PAGE_VIEW":"cd4",
                    "SCORE_REPORT_CLIENT_CODE":"cd5",
                    "errorFields":"cd6"

        });
     }


        /*analytics.populateCustomFields(campaignMetaPrefix,{
            "mc_id": "cn"
        });*/
        analytics.post('https://ssl.google-analytics.com/collect');

        if (displayFeature) {
                var analytics = new VueAnalyticsFacade();
                analytics.populateAnalytics({
                    v: '1',
                    aip: '1',
                    _v:'j31',
                    tid: account,
                    t: 'dc',
                    cid: analytics.getCustomerId,
                    _u : uDc,
                    jid : jidDc,
                    ul: analytics.dontPostParams,
                    _s: analytics.dontPostParams,
                    dt: analytics.dontPostParams,
                    je: analytics.dontPostParams,
                    sd: analytics.dontPostParams,
                    sr: analytics.dontPostParams,
                    fl: analytics.dontPostParams,
                    vp: analytics.dontPostParams,
                    dl: analytics.dontPostParams,
                    de: analytics.dontPostParams,
                    cd1:analytics.dontPostParams,
                    cd2:analytics.dontPostParams,
                    cd3:analytics.dontPostParams,
                    cd4:analytics.dontPostParams,
                    cd5:analytics.dontPostParams,
                    cd6:analytics.dontPostParams,
                    cs:analytics.dontPostParams,
                    cm:analytics.dontPostParams,
                    cn:analytics.dontPostParams

                });

                if (gatCookie==null)  {
                    var date = new Date();
                    date.setTime(date.getTime()+(10*60*1000));
                    var expires = "; expires="+date.toGMTString();
                    document.cookie = "_gat=1" +expires+"; domain=.pearsonvue.com; path=/";
                    document.cookie = "_gat=1" +expires+"; domain=.mba.com; path=/";
                    analytics.post('https://stats.g.doubleclick.net/collect');
                }

            }

    }
},

     postEvent: function(account,customMetaPrefix,eventfields) {
         var gaFacade = new GoogleAnalyticsFacade();
         var gaCookie = gaFacade.getCookie("_ga");
         if (gaCookie == null) {
             gaFacade.generateCookie();
         }
         var myCookie = gaFacade.getCookie("optOut");

         // check to do cookie doesn't exist stuff;
         if (myCookie == null) {
             var analytics = new VueAnalyticsFacade();
             analytics.populateAnalytics({
                 v: '1',
                 _s: '2',
                 _v:'j32',
                 tid: account,
                 t: 'event',
                 cid: analytics.getCustomerId,
                 dt:  analytics.documentTitleField,
                 ul:  analytics.userLanguageField,
                 je:  analytics.javaEnabledField,
                 sd:  analytics.colorDepthField,
                 sr:  analytics.screenResolutionField,
                 fl:  analytics.flashVersionField,
                 vp:  analytics.browserSizeField,
                 dl : analytics.documentPathField,
                 de : analytics.documentEncodingField,
                 cs: analytics.getCampaignTracker,
                 cm: "Web",
                 cn: analytics.getCampaignTracker,
                 el: eventfields[0],
                 ec: eventfields[1]
             });

             if (customMetaPrefix != null){
                 analytics.populateCustomFields(customMetaPrefix,{
                     "appUserId": "cd1",
                     "appUserLocale": "cd2",
                     "CLIENT_CODE": "cd3",
                     "VUE_PAGE_VIEW":"cd4",
                     "SCORE_REPORT_CLIENT_CODE":"cd5",
                     "errorFields":"cd6"

                 });
             }

             analytics.post('https://ssl.google-analytics.com/collect');
         }
     },

     gaOptout : function() {
     document.cookie = "optOut=GoogleAnalyticsOptOut; expires=Thu, 31 Dec 2070 23:59:59 UTC; domain=.pearsonvue.com; path=/";
     document.cookie = "optOut=GoogleAnalyticsOptOut; expires=Thu, 31 Dec 2070 23:59:59 UTC; domain=.mba.com; path=/";
},

     gaOptoutNo:function() {
     document.cookie = "optOut=; expires=Thu, 31 Dec 1970 23:59:59 UTC; domain=.pearsonvue.com; path=/";
     document.cookie = "optOut=; expires=Thu, 31 Dec 1970 23:59:59 UTC; domain=.mba.com; path=/";
},

 //function to check cookie Exists
    getCookie:function(name) {
     var dc = document.cookie;
     var prefix = name + "=";
     var begin = dc.indexOf("; " + prefix);
     if (begin == -1) {
         begin = dc.indexOf(prefix);
         if (begin != 0) return null;
     }
     else
     {
         begin += 2;
         var end = document.cookie.indexOf(";", begin);
         if (end == -1) {
             end = dc.length;
         }
     }
     return unescape(dc.substring(begin + prefix.length, end));
},

 // _jid
     genID:function(displayFeature) {
     if(displayFeature) {
         var jid = Math.floor(Math.random() * 1000000000);
         return jid;     }
     else {

     }
 },

 //_u
     genAlpha:function(displayFeature) {
     if(displayFeature) {
         var u = Math.random().toString(36).substr(2) + "~";
         return u;       }
     else {

     }
},


     genRandomID:function() {
     var id = Math.floor(Math.random() * 1000000000);
     return id;
},

     genRandomID2:function() {
     var id2 = Math.floor(Math.random() * 1000000000);
     return id2;
},

     generateCookie:function(){
     var gaFacade = new GoogleAnalyticsFacade();
     var n = gaFacade.genRandomID();
     var n2 = gaFacade.genRandomID2();
     var current_date = new Date;
     var cookie_year = current_date.getFullYear ( ) + 2;
     var cookie_month = current_date.getMonth ( );
     var cookie_day = current_date.getDate ( );
     var expireDate = cookie_day + cookie_month + cookie_year + current_date.getHours() + ":" + current_date.getMinutes() + ":" + current_date.getSeconds();
     var domain = gaFacade.initTopLevelDomain();
     document.cookie = "_ga=GA1.2." + n + "." +n2+ "; expires=" + expireDate +"; domain=" + domain + "; path=/";
},

     initTopLevelDomain:function() {
     var domainSplit = document.domain.split(".");
     var periodCount = domainSplit.length - 1;
     var topleveldomain;
     if (periodCount < 3) {
         topleveldomain = domainSplit.slice(periodCount - 1).join(".");
     } else {
         topleveldomain=domainSplit.slice(1).join(".");
     }

     return  topleveldomain;
    }
}


