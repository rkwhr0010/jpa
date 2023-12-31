package study.jpa.practice;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class Member extends BaseEntity{
	@Id
	@GeneratedValue
	@Column(name = "member_id")
	private Long id;
	
	private String name;
	@Embedded
	private Address address;

	@OneToMany(mappedBy = "member")
	@ToString.Exclude
	private List<Order> orders = new ArrayList<>();
}
