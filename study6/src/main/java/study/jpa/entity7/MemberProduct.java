package study.jpa.entity7;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@IdClass(MemberProductId.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberProduct {
	
	@Id
	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	private int orderAmount;
	private LocalDateTime orderDate;
}
