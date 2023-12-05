package study.jpa.entity3;

import java.util.function.Consumer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("studyjpa");
	
	public static void main(String[] args) {
		save();
		emf.close();
	}
	
	private static void save() {
		logic(em -> {
			Member m1 = new Member();
			m1.setUsername("m1");
			Member m2 = new Member();
			m2.setUsername("m2");
			
			Team t1 = new Team();
			t1.getMembers().add(m1);
			t1.getMembers().add(m2);
			
			em.persist(m1);
			em.persist(m2);
			em.persist(t1); // 문제 지점
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
