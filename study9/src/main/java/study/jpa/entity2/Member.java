package study.jpa.entity2;

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
	
	private Address homeAddress;
	private PhoneNumber phoneNumber;
}
