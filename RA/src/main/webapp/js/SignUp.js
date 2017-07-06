/**
 * Holds the javascript fuctions that are used only in
 * /testtaker/profile/create/SignUp.xhtml
 *
 * @author Fahim Ishtiaque
 * @since 2.3.1003
 */

function CapvaSignUpPage() {

    this.focusBackgroundColor = "#ececec";
    this.blurBackgroundColor = "#FFFFFF";
	this.firstRadioButtonId = "candidateIdOptions:0";
	this.secondRadioButtonId = "candidateIdOptions:1";
	this.thirdRadioButtonId = "candidateIdOptions:2";	
	this.ccidInputId = "candidateId";
	this.showModalDialog = false;

	/**
     * The initialization code is used to set up the methods for the
     * CapvaSignUpPage on the first instance. This follows the dynamic
     * prototyping methodology for defining a class object in JavaScript.
     */
    if (typeof CapvaSignUpPage._init === "undefined")
    {				
		CapvaSignUpPage.prototype.doFocusMultipleCCID = function (element,y)
        {
			var rowElement = element.type == "text" ?
							element.parentNode.parentNode
							: element.parentNode.parentNode.parentNode;
			if(rowElement && this.firstRadioButtonEl){
				rowElement.style.background = (y) ? this.focusBackgroundColor : this.blurBackgroundColor;
			}
			return false;
        }; // end doFocusMultipleCCID()

		CapvaSignUpPage.prototype.doFocusCandidateId = function (candidateId, radioButtons)
        {
                radioButtons[0].checked = true;
                this.handleRadioClick(radioButtons[0]);
                this.doFocusMultipleCCID(candidateId,1);
        }

		CapvaSignUpPage.prototype.handleRadioClick = function (element,vueGeneratesCCID){
			 if(element){
				var enable = element.id === this.firstRadioButtonId;				
				this.enableDisableCCIDInput(!enable);
				if(!this.hasLiveAuth && this.ccidInputEl){
					var ccid = this.ccidInputEl.value;
					this.showModalDialog  = ccid == "" && !enable;
				}

                 if(!enable && vueGeneratesCCID != 'true'){
					this.ccidInputEl.value = "";
					var modalFlagEl = element.id === this.secondRadioButtonId ? this.firstModalDisplayedFlagEl : this.secondModalDisplayedFlagEl;
					var showModal = modalFlagEl.value == 'false';
					if(showModal){
						var modalTitle = this.modalTitle;
						var okLabel = this.okLabel;
						var message = element.id === this.secondRadioButtonId ? this.modalMessage1 : this.modalMessage2;
						modalFlagEl.value = true;
						var theStyle = 'padding: 10px 0px 0px 10px';
						return messageDialog(message, modalTitle, okLabel, theStyle, element);
                    }
				}
			 }
		}


		CapvaSignUpPage.prototype.enableDisableCCIDInput = function (shouldDisable)
        {			
			if(this.ccidInputEl){
				this.ccidInputEl.readOnly = shouldDisable;
			}

			if(!shouldDisable){
                this.ccidInputEl.readOnly = false;
				this.ccidInputEl.focus();
			}else{
                this.ccidInputEl.value = "";
                this.ccidInputEl.readOnly = true;
            }

			return false;
        }; // end enableDisableCCIDInput()

		
		CapvaSignUpPage.prototype.shouldShowDialog = function ()
        {
			return this.showModalDialog;
		}
		
		
		CapvaSignUpPage.prototype.performOnLoadEvent = function (modalTitle,modalMessage1,modalMessage2, okLabel)
        {
			if (typeof this.ccidInputEl === "undefined")
            {
                this.ccidInputEl = document.getElementById(this.ccidInputId);
			}

			if (typeof this.firstModalDisplayedFlagEl === "undefined")
			{
				this.firstModalDisplayedFlagEl = document.getElementById("firstModalDisplayedFlag");
			}
			if (typeof this.secondModalDisplayedFlagEl === "undefined")
			{
				this.secondModalDisplayedFlagEl = document.getElementById("secondModalDisplayedFlag");
			}

			if (typeof this.firstRadioButtonEl === "undefined")
			{
				this.firstRadioButtonEl = document.getElementById(this.firstRadioButtonId);
			}
			if (typeof this.secondRadioButtonEl === "undefined")
			{
				this.secondRadioButtonEl = document.getElementById(this.secondRadioButtonId);
			}
			if (typeof this.thirdRadioButtonEl === "undefined")
			{
				this.thirdRadioButtonEl = document.getElementById(this.thirdRadioButtonId);
			}

			if (typeof this.hasLiveAuth === "undefined")
			{
				var liveAuthEl = document.getElementById("liveAuthorized");
				this.hasLiveAuth = liveAuthEl && liveAuthEl.innerHTML == "true";					
			}

			if (typeof this.okLabel === "undefined") {this.okLabel = okLabel;}
			if (typeof this.modalTitle === "undefined")	{this.modalTitle = modalTitle;}
			if (typeof this.modalMessage1 === "undefined"){this.modalMessage1 = modalMessage1;}
			if (typeof this.modalMessage2 === "undefined"){	this.modalMessage2 = modalMessage2;}
			
			if(this.firstRadioButtonEl && !this.firstRadioButtonEl.checked){
				this.enableDisableCCIDInput(true)
			}
			
			if(this.javaScriptEnabledEl){
				this.javaScriptEnabledEl.value = true;
			}

			if(this.ccidInputEl){
				var ccid = this.ccidInputEl.value;
				this.showModalDialog  = ccid == "" && !this.firstRadioButtonEl.checked;
			}

		}; // end showModalDialogSignUp()

		// Set "class" flag that indicates the method prototypes have already
        // been established -- this only has to be done for the first object
        // instance.
        CapvaSignUpPage._init = true;
    }// dynamic prototyping section

} // class CapvaSignUpPage()
var capvaSignUp = new CapvaSignUpPage();

