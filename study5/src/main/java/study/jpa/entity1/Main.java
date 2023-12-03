package study.jpa.entity1;

public class Main {
	public static void main(String[] args) {
		Member member = new Member();
		member.setId("m1");
		member.setUsername("회원1");
		
		Team team = new Team();
		team.setId("t1");
		team.setName("팀1");
		
		member.setTeam(team);
	}
}
