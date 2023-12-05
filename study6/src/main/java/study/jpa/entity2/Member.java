package study.jpa.entity2;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
	@Id
	@GeneratedValue
	@Column(name = "member_id")
	private Long id;
	private String username;
	
	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;
	
	public void setTeam(Team team) {
		// 최초 값 할당 시 null 체크
		if (this.team != null) {
			team.getMembers().remove(this);
		}
		this.team = team;
		// 내가 이미 저장되어 있는지 (루프 체크)
		if (!team.getMembers().contains(this)) {
			team.getMembers().add(this);
		}
	}
}
