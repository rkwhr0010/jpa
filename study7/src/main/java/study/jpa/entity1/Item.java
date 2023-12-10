package study.jpa.entity1;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;

//@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE") // 기본 값이 DTYPE이다.
@Data
public abstract class Item {
	@Id
	@GeneratedValue
	@Column(name = "item_id")
	private Long id;
	
	private String name;
	private int price;
}
