package study.jpa.entity4;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Address {
	private String city;
	private String strret;
	private String zipcode;
}