package study.jpa.entity1;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

//@Entity
@Data
public class Member {
	@Id
	@GeneratedValue
	@Column(name = "member_id")
	private Long id;
	private String name;
	
	@Embedded
	private Period workPeriod;
	@Embedded
	private Address homeAddress;
}
