package study.jpa.entity6;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Address {
	@Column(name = "city")// 컬럼이라고 생각하고 사용가능
	private String city;
	private String street;
	private String zipcode;
}