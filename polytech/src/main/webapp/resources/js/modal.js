// phoneModal이 열릴 때 세팅하는 함수
$(document).on('show.bs.modal', '#phoneModal', function(event) {
	console.log("open modal");
	var button = $(event.relatedTarget); // 모달을 연 버튼 값 가져오기
	var type = button.data('type'); // 가져온 버튼의 속성 중 data-type의 값 가져오기
	var phone_id = button.data('whatever'); // phone id 가져오기
	
	var modal = $(this); // 나타난 모달 값 지정
	// modal 내에서 특정 클래스를 가진 값들 변경하기 (전화기 추가/수정)
	modal.find('.modal-title').text("전화기 " + type);
	modal.find('.btn-primary').text(type);

	if (type == "수정") { // type이 수정일 경우
		modal.find('#phone_id').val(phone_id); // modal 내의 id값이 phone_id인 태그에 phone id 지정
		$('#phone_id').attr('name','id'); // name 속성 지정
	}
	if(type=="추가") { // type이 추가일 경우
		$('#phone_id').removeAttr('name'); // 추가일 경우 id값을 전달하지 않으므로 name 속성 삭제
	}
	console.log(phone_id);
	
	// modal 창이 열렸을 때 값 세팅해주기
	$.ajax({
		url : '/json_phone_search',
		method : 'post',
		data : {
			phone_id : phone_id
		},
		success : function(phone) {
			// json으로 가져온 값으로 modal 값 세팅
			$('#type').val(phone.type);
			$('#tel').val(phone.tel);

		},
		error : function(xhr, ajaxSettings, thrownError) {
		}
	})
});

//userModal이 열릴 때 세팅하는 함수
$(document).on('show.bs.modal', '#userModal', function(event) {
	console.log("open modal");
	var button = $(event.relatedTarget); // 모달을 연 버튼 값 가져오기
	var type = button.data('type'); // 가져온 버튼의 속성 중 data-type의 값 가져오기
	
	var modal = $(this); // 나타난 모달 값 지정
	// modal 내에서 특정 클래스를 가진 값들 변경하기 (전화기 추가/수정)
	modal.find('.modal-title').text("사용자 " + type);
	modal.find('.btn-primary').text(type);
});

// phoneModal이 닫힐 때 써있던 값 지우기
$(document).on('hide.bs.modal', '#phoneModal', function(event) {
	$('#type').val('');
	$('#tel').val('');
	$('span').text('');
});

//userModal이 닫힐 때 써있던 값 지우기
$(document).on('hide.bs.modal', '#userModal', function(event) {
	$('#name').val('');
	$('#department').val('');
	$('#team').val('');
	$('#rank').val('');
	$('#age').val('');
	$('#email').val('');
	$('span').text('');
});