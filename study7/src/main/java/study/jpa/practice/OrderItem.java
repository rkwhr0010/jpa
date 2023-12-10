package study.jpa.practice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import study.jpa.practice.item.Item;

@Entity
@Table(name = "order_item")
@Data
public class OrderItem extends BaseEntity {
	
	@Id
	@GeneratedValue
	@Column(name = "order_item_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
	
	private int orderPrice;
	private int count;
	
	public void setOrder(Order order) {
		if (this.order != null) {
			this.order.getOrderItems().remove(this);
		}
		this.order = order;
		order.getOrderItems().add(this);
	}
}