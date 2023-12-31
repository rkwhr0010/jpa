package study.jpa.entity6;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressEntity {
	@Id
	@GeneratedValue
	@Column(name = "address_id")
	private Long id;
	
	private String city;
	private String street;
	private String zipcode;
	
	public AddressEntity(String city, String street, String zipcode) {
		this.city = city;
		this.street = street;
		this.zipcode = zipcode;
	}
}