package study.jpa.practice;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class Member {
	@Id
	@GeneratedValue
	@Column(name = "member_id")
	private Long id;
	
	private String name;
	private String city;
	private String strret;
	private String zipcode;
	
	@OneToMany(mappedBy = "member")
	@ToString.Exclude
	private List<Order> orders = new ArrayList<>();
}
