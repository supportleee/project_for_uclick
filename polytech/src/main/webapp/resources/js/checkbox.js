/**
 * 
 */
function allChk(obj) {
	var chkObj = document.getElementsByName("userIds");
	var rowCnt = chkObj.length;
	var check = obj.checked;
	
	if(check) {
		for(var i=0; i<rowCnt; i++) {
			if(chkObj[i].type == "checkbox")
				chkObj[i].checked = true;
		}
	} else {
		for(var i=0; i<rowCnt; i++) {
			if(chkObj[i].type == "checkbox") {
				chkObj[i].checked = false;
			}
		}
	}
}