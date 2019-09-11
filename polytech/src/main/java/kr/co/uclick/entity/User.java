package kr.co.uclick.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="user")
@TableGenerator(name="user", initialValue=0, allocationSize=1)
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="user")
	@Column(name="id")
	private Long id;
	
	@Column(name="name", length=20, nullable=false)
	private String name;
	
	@Column(name="department", length=20, nullable=false)
	private String department;
	
	@Column(name="team", length=20, nullable=false)
	private String team;
	
	@Column(name="rank", length=10, nullable=false)
	private String rank;
	
	@Column(name="age", nullable=false)
	private int age;
	
	@Column(name="email", nullable=false)
	private String email;
	
	@Column(name="reg_date")
	@CreationTimestamp // insert시 default 값으로 timestamp 찍음
	private Date reg_date;
	
	@Column(name="modified_date")
	@UpdateTimestamp
	private Date modified_date;
	
	@OneToMany(mappedBy = "user")
	private Collection<Phone> phone;

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

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

	public Date getModified_date() {
		return modified_date;
	}

	public void setModified_date(Date modified_date) {
		this.modified_date = modified_date;
	}

	public Collection<Phone> getPhone() {
		if(phone == null) {
			phone = new ArrayList<Phone>();
		}
		return phone;
	}

	public void setPhone(Collection<Phone> phone) {
		this.phone = phone;
	}
	
	public void addPhone(Phone p) {
		Collection<Phone> phone = getPhone();
		phone.add(p);
	}
	
}
