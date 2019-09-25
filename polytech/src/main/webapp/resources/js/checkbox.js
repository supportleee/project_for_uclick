// 체크박스 전체 선택 함수
function allChk(obj) {
	// 'userIds'라는 name 속성을 가진 값들을  모두 가져옴
	var chkObj = document.getElementsByName("userIds"); 
	var rowCnt = chkObj.length; // 가져온 값들의 개수 체크 
	var check = obj.checked; // check 상태 여부 가져옴
	
	if(check) { // 전체선택 체크박스 선택 시
		// 가져온 chkObj들의 checked 속성을 true로 바꿔줌(전체 선택)
		for(var i=0; i<rowCnt; i++) {
			if(chkObj[i].type == "checkbox")
				chkObj[i].checked = true;
		}
	} else { // 전체선택 체크박스 선택 해제 시
		// 가져온 chkObj들의 checked 속성을 false로 바꿔줌(전체 선택 해제)
		for(var i=0; i<rowCnt; i++) {
			if(chkObj[i].type == "checkbox") {
				chkObj[i].checked = false;
			}
		}
	}
}
