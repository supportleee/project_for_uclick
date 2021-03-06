package kr.co.uclick.entity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="phone")
@TableGenerator(name="phone", initialValue=0, allocationSize=1) // auto_increment를 위한 설정
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE) // L2 cache 적용
public class Phone {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="phone") // id값 지정 방법으로 hibernate_sequences 사용
	@Column(name="id")
	private Long id;
	
	// 1:N 매핑중 N부분
	@ManyToOne(optional=false)
	@JoinColumn(name="userid")
	@JsonBackReference // Json으로 리턴할 때 무한루프 빠지는 것 방지
	private User user;
	
	@Column(name="type", length=10, nullable=false)
	private String type;
	
	@Column(name="tel", length=13, unique=true, nullable=false)
	private String tel;
	
	public Phone() {}
	
	public Phone(Long id, User user, String type, String tel) {
		super();
		if (id != null) {
			this.id = id;
		}
		this.user = user;
		this.type = type;
		this.tel = tel;
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
