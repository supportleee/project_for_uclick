package kr.co.uclick.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@DynamicInsert
@TableGenerator(name="user", initialValue=1, allocationSize=1)
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="user")
	@Column(name="id")
	private Long id;
	
	@Column(name="name", length=20, nullable=false)
	private String name;
	
	@Column(name="company", length=30, nullable=false)
	private String company;
	
	@Column(name="department", length=20)
	private String department;
	
	@Column(name="team", length=20)
	private String team;
	
	@Column(name="rank", length=10)
	private String rank;
	
	@Column(name="reg_date", nullable=false)
	@CreationTimestamp
	private Date reg_date;	

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

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
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

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
}
