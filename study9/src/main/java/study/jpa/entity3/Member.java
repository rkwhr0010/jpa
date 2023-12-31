package study.jpa.entity3;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
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
	private Address homeAddress;
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "city", column = @Column(name = "company_city")),
		@AttributeOverride(name = "street", column = @Column(name = "company_street")),
		@AttributeOverride(name = "zipcode", column = @Column(name = "company_zipcode")),
	})
	private Address companyAddress;
}
