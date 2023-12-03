package study.jpa.entity3;

import java.util.function.Consumer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("studyjpa");
	
	public static void main(String[] args) {
//		save();
//		save2();
//		save3();
//		save4();
		save5();
	}
	
	
	private static void find() {
		logic(em -> {
			Team t1 = em.find(Team.class, "team1");
			for (Member m : t1.getMembers()) {
				System.out.println(m.getUsername());
//				System.out.println(m);
			}
		});
	}


	private static void save() {
		logic(em -> {
			Team t1 = new Team("team1", "팀1");
			em.persist(t1);
			
			Member m1 = new Member("member1", "멤버1");
			m1.setTeam(t1);
			em.persist(m1);
			
			Member m2 = new Member("member2", "멤버2");
			m2.setTeam(t1);
			em.persist(m2);
		});
	}
	private static void save2() {
		logic(em -> {
			Member m1 = new Member("member1", "멤버1");
			em.persist(m1);
			
			Member m2 = new Member("member2", "멤버2");
			em.persist(m2);
			//연관관계의 주인이 아닌곳에서 저장
			Team t1 = new Team("team1", "팀1");
			t1.getMembers().add(m1);
			t1.getMembers().add(m2);
			em.persist(t1);
		});
	}
	
	private static void save3() {
		logic(em -> {
			Team t1 = new Team("team1", "팀1");
			em.persist(t1);
			
			Member m1 = new Member("member1", "멤버1");
			// 연관관계의 주인, 저장 시 사용된다.
			m1.setTeam(t1);
			// 저장 시에는 사용되지 않으나 애플리케이션에선 사용된다.
			t1.getMembers().add(m1);
			em.persist(m1);
			
			Member m2 = new Member("member2", "멤버2");
			// 연관관계의 주인, 저장 시 사용된다.
			m2.setTeam(t1);
			// 저장 시에는 사용되지 않으나 애플리케이션에선 사용된다.
			t1.getMembers().add(m2); 
			em.persist(m2);
		});
	}
	
	// 연관관계 편의 메서드 리팩터링 저장
	private static void save4() {
		logic(em -> {
			Team t1 = new Team("team1", "팀1");
			em.persist(t1);
			
			Member m1 = new Member("member1", "멤버1");
			// 연관관계의 주인, 저장 시 사용된다.
			m1.setTeam(t1);
			// 저장 시에는 사용되지 않으나 애플리케이션에선 사용된다.
			// t1.getMembers().add(m1);
			em.persist(m1);
			
			Member m2 = new Member("member2", "멤버2");
			// 연관관계의 주인, 저장 시 사용된다.
			m2.setTeam(t1);
			// 저장 시에는 사용되지 않으나 애플리케이션에선 사용된다.
			// t1.getMembers().add(m2); 
			em.persist(m2);
		});
	}
	
	// 연관관계 편의 메서드 주의사항
	private static void save5() {
		logic(em -> {
			Team t1 = new Team("team1", "팀1");
			em.persist(t1);
			Team t2 = new Team("team2", "팀2");
			em.persist(t2);
			
			Member m1 = new Member("member1", "멤버1");
			m1.setTeam(t1);
			m1.setTeam(t2);
			em.persist(m1);
			
			// t1 은 여전히 멤버1을 가지고 있다. 물론 
			// 연관관계의 주인이 아니라 DB에 영향은 없지만, 
			// 문제가 있다.
			System.out.println("##################");
			t1.getMembers().forEach(m -> {
				System.out.println(m.getUsername());
			});
			
		});
	}


	static void logic(Consumer<EntityManager> logic) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			logic.accept(em);
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
}
