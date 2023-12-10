package study.jpa.entity10;

import java.util.function.Consumer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import study.jpa.entity8.B.BId;
import study.jpa.entity8.C.CId;

public class Main {
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("studyjpa");
	
	public static void main(String[] args) {
		save();
		find();
		emf.close();
	}


	private static void find() {
		logic(em -> {
		});
	}


	private static void save() {
		logic(em -> {
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
