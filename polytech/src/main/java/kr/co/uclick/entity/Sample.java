package kr.co.uclick.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity // 이 클래스가 Entity 계층임을 명시
public class Sample {

	@Id // 이 변수가 Primary Key임을 명시
	@TableGenerator(name = "sample") // sample이라는 주키 생성자 만듦
	// 키 생성 전용 테이블을 하나 만들고, 여기에 이름과 값으로 사용할 컬럼을 만든다.
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sample") 
	private Long id; // PK로 쓰일 id값

	private String name; // 이름

	private int number; // 번호

	// getter, setter
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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}
