package study.jpa.entity2;

import jakarta.persistence.Column;
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
	@Column(name = "MEMBER_ID")
	private String id;
	private String username;
	
	//연관관계 매핑
	@ManyToOne
	@JoinColumn(name = "TEAM_ID")
	private Team team;

	public Member(String id, String username) {
		this.id = id;
		this.username = username;
	}
}
