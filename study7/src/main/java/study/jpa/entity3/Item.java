package study.jpa.entity3;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;

//@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
public abstract class Item {
	@Id
	@GeneratedValue
	@Column(name = "item_id")
	private Long id;
	
	private String name;
	private int price;
}
