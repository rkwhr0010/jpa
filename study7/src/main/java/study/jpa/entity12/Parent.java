package study.jpa.entity12;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import lombok.Data;

//@Entity
@Data
public class Parent {
	@Id
	@GeneratedValue
	@Column(name = "parent_id")
	private Long id;
	private String name;
	
	@OneToMany
	@JoinTable(name = "parent_child",
		joinColumns = @JoinColumn(name = "parent_id"),
		inverseJoinColumns = @JoinColumn(name = "child_id")
	)
	private List<Child> childs = new ArrayList<>();
}
