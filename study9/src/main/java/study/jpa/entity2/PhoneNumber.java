package study.jpa.entity2;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class PhoneNumber {
	private String areaCode;
	private String localNumber;
	
	@ManyToOne
	@JoinColumn
	private PhoneServiceProvider provider;
}
