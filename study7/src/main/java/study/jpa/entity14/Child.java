package study.jpa.entity14;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
	
	@ManyToMany(mappedBy = "childs")
	private List<Parent> parent = new ArrayList<>();;
}
