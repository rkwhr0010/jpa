package study.jpa.entity6;

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
			Member m1 = em.find(Member.class, 1L);
			System.out.println(m1);
		});
	}

	private static void save() {
		logic(em -> {
			Product p1 = new Product();
			p1.setName("제품1");
			em.persist(p1);
			Product p2 = new Product();
			p2.setName("제품2");
			em.persist(p2);
			
			Member m1 = new Member();
			m1.setUsername("멤버1");
			m1.getProducts().add(p1);
			m1.getProducts().add(p2);
			em.persist(m1);
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
