package study.jpa.entity2;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team {
	@Id
	@Column(name = "TEAM_ID")
	private String id;
	private String name;
}
