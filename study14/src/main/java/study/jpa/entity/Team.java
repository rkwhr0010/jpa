package study.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.Data;
import lombok.ToString;

//@Entity
@Data
@ToString(exclude = {"members"})
public class Team {
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	@OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("username")
	private List<Member> members = new ArrayList<>();
	
	public void addMember(Member member) {
		members.add(member);
		member.setTeam(this);
	}
}
