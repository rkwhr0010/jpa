package study.jpa.entity11;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToOne;
import lombok.Data;

//@Entity
@Data
public class Parent {
	@Id
	@GeneratedValue
	@Column(name = "parent_id")
	private Long id;
	private String name;
	
	@OneToOne
	@JoinTable(name = "parent_child",
			joinColumns = @JoinColumn(name = "parent_id"),
			inverseJoinColumns = @JoinColumn(name = "child_id", unique = true)
	)
	private Child child;
}
