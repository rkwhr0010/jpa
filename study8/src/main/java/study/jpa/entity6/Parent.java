package study.jpa.entity6;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

//@Entity
@Data
public class Parent {
	@Id
	@GeneratedValue
	@Column(name = "parent_id")
	private Long id;
	
	@OneToMany(mappedBy = "parent", orphanRemoval = true, cascade = CascadeType.PERSIST)
	private List<Child> childs = new ArrayList<>();
}
