package study.jpa.entity1;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

//@Entity
//@Table(name = "MEMBER", uniqueConstraints = {
//		@UniqueConstraint(name = "NAME_AGE_UNIQUE", columnNames = {"NAME", "AGE"})
//		})
//시퀀스 전략
//@SequenceGenerator(
//		name = "MEMBER_SEQ_GENERATOR",
//		sequenceName = "MEMBER_SEQ",
//		initialValue = 1,
//		allocationSize = 50
//		)
//테이블 전략
//@TableGenerator(
//		name = "BOARD_SEQ_GENERATOR",
//		table = "SEQ_TABLE",
//		pkColumnValue = "BOARD_SEQ",
//		allocationSize = 1
//		)
public class Board {
	@Id
	@GeneratedValue
	@Column(name = "BOARD_ID")
	private Long id;
	private Integer age;
	
	@Enumerated(EnumType.STRING)
	private RoleType roleType;
	
	private LocalDateTime localDateTime1;
	private LocalDate localDate1;
	
	private String firstName;
	private String lastName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public RoleType getRoleType() {
		return roleType;
	}
	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}
	public LocalDateTime getLocalDateTime1() {
		return localDateTime1;
	}
	public void setLocalDateTime1(LocalDateTime localDateTime1) {
		this.localDateTime1 = localDateTime1;
	}
	public LocalDate getLocalDate1() {
		return localDate1;
	}
	public void setLocalDate1(LocalDate localDate1) {
		this.localDate1 = localDate1;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}
