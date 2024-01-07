package study.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;
import lombok.Data;

//@Entity
@Data
public class CollectionTestEntity {
	@Id
	@GeneratedValue
	private Long id;
	@OneToMany
	@OrderColumn(name = "position")
	@JoinColumn(name = "id")
	List<Dummy> dummies = new ArrayList<Dummy>();
//	@OneToMany
//	@JoinColumn(name = "id")
//	Set<Dummy> dummies = new HashSet<Dummy>();
}
