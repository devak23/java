var signInRedirect = {
	performSignInRedirect: function(referrerValue, clientCode, urlOverride) {
		var cookieName = "PVueRedirectPrevention";
		if (this.getCookieValue(cookieName)) {
			this.createCookie(cookieName, "ON", 1)
		} else {
			if (referrerValue !== "" && document.referrer.indexOf(referrerValue) > -1) {	//checks whether document.referrer contains referrerValue
				this.createCookie(cookieName, "ON", 1)
			} else {
				var timeValue = this.getParameterByName("REDIRECT_TIMESTAMP");
				if (timeValue && this.isWithinTimeWindow(timeValue)) {
					this.createCookie(cookieName, "ON", 1);
				} else {
					if (navigator.cookieEnabled) {
						this.createCookie(cookieName, "OFF", 1);
						var status = "";
						var that = this;
						this.checkServerStatus(
							referrerValue,
							function() {
								if (!status) {
									status = "success";
									if (urlOverride) {
										window.location.replace(urlOverride);
									} else {
										window.location.replace('/testtaker/common/SignInRedirect.htm?clientCode=' + clientCode);
									}
								}
							},
							function() {
								if (!status) {
									status = "fail";
									that.createCookie(cookieName, "ON", 1);
								}
							}
						);

					}
					else {
						window.location.replace('/legal/privacy/#cookies');
					}

				}

			}
		}
	},

	checkServerStatus: function(url, successFunction, failFunction) {
		var script = document.createElement("script");

		script.onload = successFunction;
		script.onerror = failFunction;
		setTimeout(failFunction, 5000);

		script.src = url;

		document.body.appendChild(script);

	},

	createCookie: function(name, value, expirationDays) {
		var date = new Date();
		date.setTime(date.getTime() + (expirationDays*24*60*60*1000));
		document.cookie = name + "=" + value + ";domain=.pearsonvue.com;path=/";
	},

	isWithinTimeWindow: function(milliseconds) {
		var thirtyMinutesInMillis = 1800000;
		var now = new Date();
		return now.getTime() - thirtyMinutesInMillis < milliseconds
			&& milliseconds < now.getTime() + thirtyMinutesInMillis;
	},

	getCookieValue: function(name) {
		var dc = document.cookie;
		var prefix = name + "=";
		var begin = dc.indexOf("; " + prefix);
		if (begin == -1) {
			begin = dc.indexOf(prefix);
			if (begin != 0) return null;
		}
		else {
			begin += 2;
			var end = document.cookie.indexOf(";", begin);
			if (end == -1) {
				end = dc.length;
			}
		}

		var cookie = unescape(dc.substring(begin + prefix.length, end));

		if (cookie === null) {
			return false;
		} else {
			var split = cookie.split(';');
			var value = split[0];
			return value;
		}
	},

	getParameterByName: function(name) {
		name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
		var regex = new RegExp("[\\?&]" + name + "=([^&#]*)");
		var results = regex.exec(location.search);
		return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
	}
}
