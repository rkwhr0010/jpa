package study.jpa.entity2;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

//@Entity
@Data
public class Member {
	@Id
	@GeneratedValue
	@Column(name = "member_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)//필수적 관계 내부 조인
	@JoinColumn(name = "team_id", nullable = true) //필수적 관계 내부 조인
//	@ManyToOne(fetch = FetchType.EAGER)//선택적 관계 외부 조인
//	@JoinColumn(name = "team_id") //선택적 관계 외부 조인
	private Team team;
	private String name;
}
