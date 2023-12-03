package study.jpa.entity1;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.*;

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
//		name = "MEMBER_SEQ_GENERATOR",
//		table = "SEQ_TABLE",
//		pkColumnValue = "MEMBER_SEQ",
//		allocationSize = 1
//		)
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY
			)
	@Column(name = "MEMBER_ID")
	private Long id;
	
	private String username;
	
	private Integer age;
	
	private LocalDateTime createdDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
}
