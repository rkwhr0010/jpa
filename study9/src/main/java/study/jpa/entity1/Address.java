package study.jpa.entity1;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Address {
	@Column(name = "city")// 컬럼이라고 생각하고 사용가능
	private String city;
	private String street;
	private String zipcode;
}