package kr.co.uclick.dto;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import kr.co.uclick.entity.User;

public class UserDto {
	private Long id;

	@Pattern(regexp = "^[가-힣]{2,6}$", message="이름은 한글만 입력할 수 있습니다.")
	private String name;
	
	@NotBlank(message="부서를 선택하세요.")
	private String department;
	
	@NotBlank(message="팀을 선택하세요.")
	private String team;
	
	@NotBlank(message="직위를 선택하세요.")
	private String rank;
	
	@NotNull(message = "나이를 입력하세요.")
	@DecimalMin(value="0", message="나이는 0이상 입력하세요.")
	@DecimalMax(value="100", message="나이는 100이하 입력하세요.")
	private int age;
	
	@NotBlank(message="메일을 입력하세요.")
	@Email(message="이메일 형식에 맞춰 입력하세요.")
	private String email;
	
	public UserDto() {}
	
	public User toEntity() {
		return new User(id, name, department, team, rank, age, email);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
}
