/*
**    Original File: pwd_meter.js
**    Created by: Jeff Todnem (http://www.todnem.com/)
**    Created on: 2007-08-14
**    Last modified: 2007-08-30
**
**    License Information:
**    -------------------------------------------------------------------------
**    Copyright (C) 2007 Jeff Todnem
**
**    This program is free software; you can redistribute it and/or modify it
**    under the terms of the GNU General Public License as published by the
**    Free Software Foundation; either version 2 of the License, or (at your
**    option) any later version.
**    
**    This program is distributed in the hope that it will be useful, but
**    WITHOUT ANY WARRANTY; without even the implied warranty of
**    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
**    General Public License for more details.
**    
**    You should have received a copy of the GNU General Public License along
**    with this program; if not, write to the Free Software Foundation, Inc.,
**    59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
**    
**    
*/

String.prototype.strReverse = function() {
	var newstring = "";
	for (var s=0; s < this.length; s++) {
		newstring = this.charAt(s) + newstring;
	}
	return newstring;
};


/*
**    function: chkPass
**    purpose: Updates the password scorebar (background color and complexity text)
**              based upon how a password scores for overall security strength.
**    parameters:
**      pwd: The password string to evaluate
**      targetID: The id of the password scorebar to update
**      saStrength: A string array of different password strengths (not necessarily english)
**
*/

function chkPass(pwd, targetID, saStrength) {
    var index = { TOOSHORT:0, WEAK:1, MODERATE:2, STRONG:3 };
    var oScorebar = document.getElementById(targetID);
	var nScore = 0;
	var nLength = 0;
	var nAlphaUC = 0;
	var nAlphaLC = 0;
	var nNumber = 0;
	var nSymbol = 0;
	var nMidChar = 0;
	var nRequirements = 0;
	var nAlphasOnly = 0;
	var nNumbersOnly = 0;
	var nRepChar = 0;
	var nConsecAlphaUC = 0;
	var nConsecAlphaLC = 0;
	var nConsecNumber = 0;
	var nConsecSymbol = 0;
	var nConsecCharType = 0;
	var nSeqAlpha = 0;
	var nSeqNumber = 0;
	var nSeqChar = 0;
	var nMultLength = 4;
	var nMultAlphaUC = 2;
	var nMultAlphaLC = 2;
	var nMultNumber = 4;
	var nMultSymbol = 6;
	var nMultMidChar = 2;
	var nMultRequirements = 2;
	var nMultConsecAlphaUC = 2;
	var nMultConsecAlphaLC = 2;
	var nMultConsecNumber = 2;
	var nMultSeqAlpha = 3;
	var nMultSeqNumber = 3;
	var nTmpAlphaUC = "";
	var nTmpAlphaLC = "";
	var nTmpNumber = "";
	var nTmpSymbol = "";
	var sAlphas = "abcdefghijklmnopqrstuvwxyz";
	var sNumerics = "01234567890";
	var sComplexity = saStrength[index.TOOSHORT];
    var sColor = "#eee";
    var nMinPwdLen = 8;
	if (pwd) {
		nScore = parseInt(pwd.length * nMultLength);
		nLength = pwd.length;
		var arrPwd = pwd.replace (/\s+/g,"").split(/\s*/);
		var arrPwdLen = arrPwd.length;
		
		/* Loop through password to check for Symbol, Numeric, Lowercase and Uppercase pattern matches */
		for (var a=0; a < arrPwdLen; a++) {
			if (arrPwd[a].match(new RegExp(/[A-Z]/g))) {
				if (nTmpAlphaUC !== "") { if ((nTmpAlphaUC + 1) == a) { nConsecAlphaUC++; nConsecCharType++; } }
				nTmpAlphaUC = a;
				nAlphaUC++;
			}
			else if (arrPwd[a].match(new RegExp(/[a-z]/g))) { 
				if (nTmpAlphaLC !== "") { if ((nTmpAlphaLC + 1) == a) { nConsecAlphaLC++; nConsecCharType++; } }
				nTmpAlphaLC = a;
				nAlphaLC++;
			}
			else if (arrPwd[a].match(new RegExp(/[0-9]/g))) { 
				if (a > 0 && a < (arrPwdLen - 1)) { nMidChar++; }
				if (nTmpNumber !== "") { if ((nTmpNumber + 1) == a) { nConsecNumber++; nConsecCharType++; } }
				nTmpNumber = a;
				nNumber++;
			}
			else if (arrPwd[a].match(new RegExp(/[^a-zA-Z0-9_]/g))) { 
				if (a > 0 && a < (arrPwdLen - 1)) { nMidChar++; }
				if (nTmpSymbol !== "") { if ((nTmpSymbol + 1) == a) { nConsecSymbol++; nConsecCharType++; } }
				nTmpSymbol = a;
				nSymbol++;
			}
			/* Internal loop through password to check for repeated characters */
			for (var b=0; b < arrPwdLen; b++) {
				if (arrPwd[a].toLowerCase() == arrPwd[b].toLowerCase() && a != b) { nRepChar++; }
			}
		}
		
		/* Check for sequential alpha string patterns (forward and reverse) */
		for (var s=0; s < 23; s++) {
			var sFwd = sAlphas.substring(s,parseInt(s+3));
			var sRev = sFwd.strReverse();
			if (pwd.toLowerCase().indexOf(sFwd) != -1 || pwd.toLowerCase().indexOf(sRev) != -1) { nSeqAlpha++; nSeqChar++;}
		}
		
		/* Check for sequential numeric string patterns (forward and reverse) */
		for (var s=0; s < 8; s++) {
			var sFwd = sNumerics.substring(s,parseInt(s+3));
			var sRev = sFwd.strReverse();
			if (pwd.toLowerCase().indexOf(sFwd) != -1 || pwd.toLowerCase().indexOf(sRev) != -1) { nSeqNumber++; nSeqChar++;}
		}
		
	/* Modify overall score value based on usage vs requirements */

		/* General point assignment */
		if (nAlphaUC > 0 && nAlphaUC < nLength) {
			nScore = parseInt(nScore + ((nLength - nAlphaUC) * nMultAlphaUC));
		}
		if (nAlphaLC > 0 && nAlphaLC < nLength) {	
			nScore = parseInt(nScore + ((nLength - nAlphaLC) * nMultAlphaLC));
		}
		if (nNumber > 0 && nNumber < nLength) {	
			nScore = parseInt(nScore + (nNumber * nMultNumber));
		}
		if (nSymbol > 0) {	
			nScore = parseInt(nScore + (nSymbol * nMultSymbol));
		}
		if (nMidChar > 0) {	
			nScore = parseInt(nScore + (nMidChar * nMultMidChar));
		}

		/* Point deductions for poor practices */
		if ((nAlphaLC > 0 || nAlphaUC > 0) && nSymbol === 0 && nNumber === 0) {  // Only Letters
            nAlphasOnly = nLength;
			nScore = parseInt(nScore - nAlphasOnly);
		}
		if (nAlphaLC === 0 && nAlphaUC === 0 && nSymbol === 0 && nNumber > 0) {  // Only Numbers
            nNumbersOnly = nLength;
			nScore = parseInt(nScore - nNumbersOnly);
		}
		if (nRepChar > 0) {  // Same character exists more than once
			nScore = parseInt(nScore - nRepChar);
		}
		if (nConsecAlphaUC > 0) {  // Consecutive Uppercase Letters exist
			nScore = parseInt(nScore - (nConsecAlphaUC * nMultConsecAlphaUC)); 
		}
		if (nConsecAlphaLC > 0) {  // Consecutive Lowercase Letters exist
			nScore = parseInt(nScore - (nConsecAlphaLC * nMultConsecAlphaLC)); 
		}
		if (nConsecNumber > 0) {  // Consecutive Numbers exist
			nScore = parseInt(nScore - (nConsecNumber * nMultConsecNumber));  
		}
		if (nSeqAlpha > 0) {  // Sequential alpha strings exist (3 characters or more)
			nScore = parseInt(nScore - (nSeqAlpha * nMultSeqAlpha)); 
		}
		if (nSeqNumber > 0) {  // Sequential numeric strings exist (3 characters or more)
			nScore = parseInt(nScore - (nSeqNumber * nMultSeqNumber)); 
		}

		/* Determine if mandatory requirements have been met */
        if (nLength >= nMinPwdLen) {
            nRequirements++;
            var nMinReqChars = 3;
            if (nAlphaUC >= 1) { nRequirements++; }
            if (nAlphaLC >= 1) { nRequirements++; }
            if (nNumber >= 1) { nRequirements++; }
            if (nSymbol >= 1) { nRequirements++; }
            if (nRequirements > nMinReqChars) {
                nScore = parseInt(nScore + (nRequirements * nMultRequirements));
            }
        }

		/* Determine complexity based on overall score */
        if (nLength < 2) {
            sComplexity = saStrength[index.TOOSHORT];
            sColor = "#eee"; //light gray
        }
        else if (nScore < 40) {
            sComplexity = saStrength[index.WEAK];
            sColor = "#f00"; //red
        }
		else if (nScore < 60) {
            sComplexity = saStrength[index.MODERATE];
            sColor = "#ff0"; //yellow
        }
		else {
            sComplexity = saStrength[index.STRONG];
            sColor = "#0f0"; //green
        }
	}
    oScorebar.innerHTML = sComplexity;
    oScorebar.style.backgroundColor = sColor;
}