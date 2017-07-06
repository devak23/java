/* Pearson websites, Privacy and cookies - NF 042012, Digital Marketing LTG */
var domainSplit = document.domain.split(".");
var periodCount = domainSplit.length - 1;
var topleveldomain;
var privacyCookieName;
initTopLevelDomain();
var privacyNoticeDivId = "cookiemsgbox";
var defaultPrivacyMessageText = '<span class="cookieMessageTitle">Privacy and Cookies</span><p>This website stores cookies on your computer which help us make the website work better for you.</p><input type="button" class="cookieMessageLearnMoreButton" name="Learn more" value="Learn more" onclick="javascript: window.open(&#39;/legal/privacy/#cookies&#39;, &#39;_blank&#39;, &#39;&#39;)"/><input type="button" class="cookieMessageCloseMessageButton" name="Close this message" value="Close this message" onclick="javascript:createPrivacyCookie()"/>';

function initTopLevelDomain() {
    if (periodCount < 3) {
        topleveldomain = domainSplit.slice(periodCount - 1).join(".");
    } else {
        topleveldomain=domainSplit.slice(1).join(".");
    }

    privacyCookieName = "ctbar:"+topleveldomain;
}

function createPrivacyCookie() {
	hidePrivacyNotice();
    var date = new Date();
	date.setMonth(date.getMonth()+3);
	var expires = "expires="+date.toGMTString()+"; ";
	document.cookie = privacyCookieName+"="+"active; "+expires+" path=/; domain="+topleveldomain;
}

function hidePrivacyNotice(){
    document.getElementById(privacyNoticeDivId).style.display = "none";
}

function checkPrivacyCookieDefault() {
	if (isPrivacyCookieNotSet()){
		showPrivacyNotice(defaultPrivacyMessageText);
	}
}

function checkPrivacyCookie(privacyMessageText){
    if (isPrivacyCookieNotSet()){
		showPrivacyNotice(privacyMessageText);
	}
}

function isPrivacyCookieNotSet() {
	var ca = document.cookie.split(';');
	for(var i=0;i < ca.length;i++) {
		var c = ca[i];
        c=jQuery.trim(c);
		if (c.indexOf(privacyCookieName) == 0) {
            return false;
        }
	}
	return true;
}

function showPrivacyNotice(noticeContent) {
    document.write('<div style="display:block;" id="'+privacyNoticeDivId+'">'+noticeContent+'</div>');
}