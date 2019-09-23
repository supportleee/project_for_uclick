// 삭제버튼 클릭 시 확인 창 띄우기
function deleteConfirm() {
	if(confirm("정말 삭제하시겠습니까?") == true) { // 확인 눌렀을 때
		return true;
	} else { // 취소 눌렀을 때
		return false;
	}
}