package study.jpa.entity5;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

//@Entity
@Data
public class Child {
	@Id
	@GeneratedValue
	@Column(name = "child_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Parent parent;
}
