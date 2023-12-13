package study.jpa.entity2;

import java.util.function.Consumer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("studyjpa");
	
	public static void main(String[] args) {
		save();
		find();
		emf.close();
	}


	private static void find() {
		logic(em -> {
			Member member = em.find(Member.class, 1L);
			System.out.println("#########################################");
			System.out.println(member.getClass().getSuperclass());
			System.out.println(member.getClass());
			
		});
	}


	private static void save() {
		logic(em -> {
			
			Team team = new Team();
			team.setName("팀");
			em.persist(team);
			
			Member member = new Member();
			member.setName("멤버");
			member.setTeam(team);
			em.persist(member);
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
