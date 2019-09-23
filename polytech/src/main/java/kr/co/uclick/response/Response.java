package kr.co.uclick.response;

import java.util.Map;

public class Response {
	private boolean validated;
	private Map<String, String> errorMessages;
	private boolean notDuplicatePhone;

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
