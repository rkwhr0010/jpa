package study.jpa.entity9;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

//@Entity
@Data
public class B {
	@Id
	@GeneratedValue
	@Column(name = "b_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "a_id")
	private A a;
	
	private String name;;
}
