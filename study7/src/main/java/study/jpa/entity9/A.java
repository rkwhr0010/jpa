package study.jpa.entity9;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

//@Entity
@Data
public class A {
	@Id
	@GeneratedValue
	@Column(name = "a_id")
	private Long id;
	private String name;;
}
