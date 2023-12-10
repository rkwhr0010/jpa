package study.jpa.practice.item;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import study.jpa.practice.BaseEntity;
import study.jpa.practice.Category;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item extends BaseEntity{
	@Id
	@GeneratedValue
	@Column(name = "item_id")
	private Long id;
	
	private String name;
	private int price;
	private int stockQuantity;
	
	@ManyToMany(mappedBy = "items")
	private List<Category> categories = new ArrayList<>();
}