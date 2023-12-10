package study.jpa.entity13;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import lombok.Data;

//@Entity
@Data
public class Child {
	@Id
	@GeneratedValue
	@Column(name = "child_id")
	private Long id;
	private String name;
	
	@ManyToOne(optional = false)
	@JoinTable(name = "parent_child",
		joinColumns = @JoinColumn(name = "child_id"),
		inverseJoinColumns = @JoinColumn(name = "parent_id")
	)
	private Parent parent;
}
