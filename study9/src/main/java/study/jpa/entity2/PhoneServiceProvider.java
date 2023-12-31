package study.jpa.entity2;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

//@Entity
public class PhoneServiceProvider {
	@Id
	@GeneratedValue
	private Long id;
}
