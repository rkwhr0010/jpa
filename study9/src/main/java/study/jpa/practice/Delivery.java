package study.jpa.practice;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Delivery extends BaseEntity{
	@Id
	@GeneratedValue
	@Column(name = "delivery_id")
	private Long id;
	
	@OneToOne(mappedBy = "delivery")
	private Order order;
	@Embedded
	private Address address;
	@Enumerated(EnumType.STRING)
	private DeliveryStatus status;
	
}
