package study.jpa.practice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class Order extends BaseEntity{
	@Id
	@GeneratedValue
	@Column(name = "order_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
	@OneToMany(mappedBy = "order")
	private List<OrderItem> orderItems = new ArrayList<>();
	
	@OneToOne
	@JoinColumn(name = "delivery_id")
	private Delivery delivery;
	
	private LocalDateTime orderDate;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	// 연관관계 편의 메소드
	public void setMember(Member member) {
		if (this.member != null) {
			this.member.getOrders().remove(this);
		}
		this.member = member;
		member.getOrders().add(this);
	}
	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}
	
	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
		delivery.setOrder(this);
	}
}