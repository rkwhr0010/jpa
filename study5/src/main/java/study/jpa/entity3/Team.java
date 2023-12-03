package study.jpa.entity3;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Team {

	@Id
	@Column(name = "TEAM_ID")
	private String id;
	private String name;
	
	@OneToMany(mappedBy = "team")
	@ToString.Exclude
	private List<Member> members = new ArrayList<>();

	public Team(String id, String name) {
		this.id = id;
		this.name = name;
	}
}
