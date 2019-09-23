package kr.co.uclick.response;

import java.util.Map;

// 유효성 검사 결과용 객체
public class Response {
	private boolean validated; // 유효성 검사 결과값
	private Map<String, String> errorMessages; // error massage 매핑
	private boolean notDuplicatePhone; // 전화기 중복 검사 결과값

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	public void setErrorMessages(Map<String, String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public Map<String, String> getErrorMessages() {
		return errorMessages;
	}

	public boolean isNotDuplicatePhone() {
		return notDuplicatePhone;
	}

	public void setNotDuplicatePhone(boolean notDuplicatePhone) {
		this.notDuplicatePhone = notDuplicatePhone;
	}



}
