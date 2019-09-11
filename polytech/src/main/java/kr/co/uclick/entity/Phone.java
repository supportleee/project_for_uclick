package kr.co.uclick.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="phone")
@TableGenerator(name="phone", initialValue=0, allocationSize=1)
public class Phone {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="phone")
	@Column(name="id")
	private Long id;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="type", length=10, nullable=false)
	private String type;
	
	@Column(name="phone_number", length=13, unique=true, nullable=false)
	private String phone_number;

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

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}	
	
}
