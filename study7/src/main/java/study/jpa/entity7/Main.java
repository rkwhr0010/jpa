package study.jpa.entity7;

import java.util.function.Consumer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import study.jpa.entity7.B.BId;
import study.jpa.entity7.C.CId;

public class Main {
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("studyjpa");
	
	public static void main(String[] args) {
		save();
		find();
		emf.close();
	}


	private static void find() {
		logic(em -> {
			A a = em.find(A.class, "A_ID");
			
			BId bId = new B.BId();
			bId.setA("A_ID");
			bId.setId("B_ID");
			
			B b = em.find(B.class, bId);
			
			CId cId = new C.CId();
			cId.setB(bId);
			cId.setId("C_ID");
			
			C c = em.find(C.class, cId);
		});
	}


	private static void save() {
		logic(em -> {
			A a = new A();
			a.setId("A_ID");
			a.setName("A");
			em.persist(a);
			
			B b = new B();
			b.setA(a);
			b.setId("B_ID");
			b.setName("B");
			em.persist(b);
			
			C c = new C();
			c.setB(b);
			c.setId("C_ID");
			c.setName("C");
			em.persist(c);
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
