package study.jpa.entity5;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	@Id
	@GeneratedValue
	@Column(name = "product_id")
	private Long id;
	private String name;
	
	@ManyToMany(mappedBy = "products")
	@ToString.Exclude // toString 무한 루프 방지
	private List<Member> members;
}
