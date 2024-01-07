package study.jpa.entity3;

import java.util.function.Consumer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("studyjpa");
	
	public static void main(String[] args) {
		entityEvent();
		entityEvent2();
		entityEvent3();
		
		emf.close();
	}



	private static void entityEvent() {
		logic(em->{
			Duck duck = new Duck();
			duck.setName("청둥오리");
			em.persist(duck);
		});
	}
	private static void entityEvent2() {
		logic(em->{
			Duck duck = em.find(Duck.class, 1L);
			duck.setName("기계오리");
		});
	}
	private static void entityEvent3() {
		logic(em->{
			em.remove(em.find(Duck.class, 1L));
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
