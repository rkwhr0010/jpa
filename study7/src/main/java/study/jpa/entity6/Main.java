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
			Master.MasterId masterId = new Master.MasterId(1L, 2L);
			em.find(Master.class, masterId);
			em.find(Detail.class, 1L);
		});
	}


	private static void save() {
		logic(em -> {
			Master master = new Master();
			master.setId(new Master.MasterId(1L, 2L));
			master.setName("마스터");
			
			em.persist(master);
			
			Detail detail = new Detail();
			detail.setMaster(master);
			
			em.persist(detail);
			
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
