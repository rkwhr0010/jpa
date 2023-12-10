package study.jpa.entity8;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

//@Entity
@Data
public class A {
	@Id
	@Column(name = "a_id")
	private String id;
	private String name;;
}
