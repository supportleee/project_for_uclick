package kr.co.uclick.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import kr.co.uclick.entity.Phone;
import kr.co.uclick.entity.User;

// 전화기 유효성 검사를 위한 DTO
public class PhoneDto {
	private Long id;
	
	private User user;
	
	@NotBlank(message = "종류를 선택하세요.")
	private String type;
	
	@Pattern(regexp = "^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?[0-9]{3,4}-?[0-9]{4}$", message="전화번호 형식을 확인하세요.")
	private String tel;
	
	public PhoneDto() {}
	
	public Phone toEntity() {
		return new Phone(id, user, type, tel);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	
}
