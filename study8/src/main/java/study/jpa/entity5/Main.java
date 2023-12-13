package study.jpa.entity5;

import java.util.function.Consumer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("studyjpa");
	
	public static void main(String[] args) {
		saveNoCascade();
//		saveCascade();
		removeCascade();
		emf.close();
	}




	private static void removeCascade() {
		logic(em -> {		
			Parent p1 = em.find(Parent.class, 1L);
			em.remove(p1);
			
		});
	}




	private static void saveNoCascade() {
		logic(em -> {
Parent p1 = new Parent();
em.persist(p1);

Child c1 = new Child();
c1.setParent(p1);       // 자식 -> 부모 연관관계
p1.getChilds().add(c1); // 부모 -> 자식 그래프 탐색용
em.persist(c1);

Child c2 = new Child();
c2.setParent(p1);       // 자식 -> 부모 연관관계
p1.getChilds().add(c2); // 부모 -> 자식 그래프 탐색용
em.persist(c2);
		});
	}
	private static void saveCascade() {
		logic(em -> {
Parent p1 = new Parent();
em.persist(p1);

Child c1 = new Child();
c1.setParent(p1);       // 자식 -> 부모 연관관계
p1.getChilds().add(c1); // 부모 -> 자식 그래프 탐색용

Child c2 = new Child();
c2.setParent(p1);       // 자식 -> 부모 연관관계
p1.getChilds().add(c2); // 부모 -> 자식 그래프 탐색용
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
