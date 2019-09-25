// 전화번호에 하이픈(-) 추가
function replacePhoneNum(obj) {
	// 입력값 검증하여 하이픈 추가
	var phoneNum = obj.value.replace(
			/(01[016789]{1}|02|0[3-9]{1}[0-9]{1})([0-9]{3,4})([0-9]{4})/, "$1-$2-$3");
	// input element의 값을 설정한 string으로 지정
	obj.value = phoneNum;
}

// 전화번호 입력란에 숫자만 입력받기
function checkNum(event) {
	// 입력된 키코드 값
	var keyVal = event.keyCode;

	// 8 : backspace
	// 46 : delete
	// 48~57 : 숫자 키
	// 96~105 : 숫자키패드 숫자키
	if (keyVal == 8 || keyVal == 46 || (keyVal >= 48 && keyVal <= 57)
			|| (keyVal >= 96 && keyVal <= 105)) {
		console.log("number O");
		return;
	} else {
		console.log("number X");
		// 입력 막기. 아래 둘 다 해줘야 함
		event.preventDefault();
		event.stopPropagation();
	}
}


//사용자 validation
function validateUser(url) {
	$('span').text(''); // error 메시지 입력 란 공백으로 세팅
	// form 값을 post방식으로 user_validation으로 전달 후 값 받아오기
	if(url == 'user_list') {
		url = '/user_validation';
	} else if(url == 'user_view') {
		url = '../user_validation';
	}
 	$.ajax({
		url : url,
		method : 'post',
		data : $('#userForm').serialize(),
		success : function(res) { // 성공적으로 받아왔을 때
			if (res.validated) { // 검증 결과 문제 없으면
				window.location.reload();
			} else { // 검증 결과 유효하지 않을 경우 error 메시지 출력
				$.each(res.errorMessages, function(key, value) {
					var id = key + '_error';
					var tag = document.getElementById(id);
					tag.innerHTML = value;
				});
			}
		}
	});
	return false;
}

// 전화기 validation
function validatePhone(user_id){
	$('span').text(''); // error 메시지 입력 란 공백으로 세팅
	// form 값을 post방식으로 phone_validation으로 전달 후 값 받아오기
	$.ajax({
		url : '../phone_validation/'+user_id,
		method : 'post',
		data : $('#phoneForm').serialize(),
		success : function(res) { // 성공적으로 받아왔을 때
			if (res.validated) { // 검증 결과 문제 없으면
				if(res.notDuplicatePhone) { // 전화번호가 중복되지 않은 경우
					location.href='../user_view/'+user_id; // 새로고침
				} else { // 전화번호가 중복된 경우
					// error 메시지 출력
					var tag = document.getElementById('tel_error');
					tag.innerHTML = '이미 사용중인 전화번호입니다.';
				}
				
			} else { // 검증 결과 유효하지 않을 경우 error 메시지 출력
				$.each(res.errorMessages, function(key, value) {
					var id = key + '_error';
					var tag = document.getElementById(id);
					tag.innerHTML = value;
				});
			}
		}
	});
	return false;
}