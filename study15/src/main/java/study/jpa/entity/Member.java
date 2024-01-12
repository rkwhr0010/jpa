package study.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
	@Id
	private Integer id;
	private String username;
	@OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
	@ToString.Exclude
	private List<Order> orders = new ArrayList<>();
	
	public Member(Integer id, String username) {
		this.id = id;
		this.username = username;
	}
}
