package study.jpa.practice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ORDER_ITEM")
@Data
public class OrderItem {
	
	@Id
	@GeneratedValue
	@Column(name = "ORDER_ITEM_ID")
	private Long id;
	
	@Column(name = "ITEM_ID")
	private Long itemId;
	@Column(name = "ORDER_ID")
	private Long orderId;
	
	private int orderPrice;
	private int count;
}
