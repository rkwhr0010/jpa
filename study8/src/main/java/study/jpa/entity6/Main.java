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
		remove();
		emf.close();
	}




	private static void remove() {
		logic(em -> {		
			
Parent p1 = em.find(Parent.class, 1L);
p1.getChilds().clear();
		});
	}




	private static void save() {
		logic(em -> {
			
Parent p1 = new Parent();
em.persist(p1);

for (int i = 0; i < 10; i++) {
	Child c1 = new Child();
	c1.setParent(p1);       // 자식 -> 부모 연관관계
	p1.getChilds().add(c1); // 부모 -> 자식 그래프 탐색용
	em.persist(c1);
}
		});
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
