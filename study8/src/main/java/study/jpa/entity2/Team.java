package study.jpa.entity2;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

//@Entity
@Data
public class Team {
	@Id
	@GeneratedValue
	@Column(name = "team_id")
	private Long id;
	private String name;
}
