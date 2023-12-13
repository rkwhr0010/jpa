package study.jpa.entity7;

import java.util.function.Consumer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("studyjpa");
	
	public static void main(String[] args) {
		logic(save());
		logic(remove());
		emf.close();
	}

	private static Consumer<EntityManager> remove() {
		return em -> {		
			Parent p1 = em.find(Parent.class, 1L);
			p1.getChilds().clear();
		};
	}

	private static Consumer<EntityManager> save() {
		return em -> {
			
			Parent p1 = new Parent();
			
			for (int i = 0; i < 10; i++) {
				Child c1 = new Child();
				c1.setParent(p1);      
				p1.getChilds().add(c1);
			}
			em.persist(p1);
		};
	}

	static void logic(Consumer<EntityManager> logic) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			logic.accept(em);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}
}
